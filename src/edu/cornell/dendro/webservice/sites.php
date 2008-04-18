<?php
//*******************************************************************
////// PHP Corina Middleware
////// License: GPL
////// Author: Peter Brewer
////// E-Mail: p.brewer@cornell.edu
//////
////// Requirements : PHP >= 5.0
//////*******************************************************************

require_once("inc/dbsetup.php");
require_once("config.php");
require_once("inc/meta.php");
require_once("inc/errors.php");
require_once("inc/auth.php");
require_once("inc/request.php");
require_once("inc/output.php");

require_once("inc/site.php");

// Create Authentication, Request and Header objects
$myAuth         = new auth();
$myRequest      = new siteRequest($myMetaHeader, $myAuth);

// Set user details
if($myAuth->isLoggedIn())
{
    $myMetaHeader->setUser($myAuth->getUsername(), $myAuth->getFirstname(), $myAuth->getLastname());
}

// **************
// GET PARAMETERS
// **************
if(isset($_POST['xmlrequest']))
{
    // Extract parameters from XML request POST
    $myRequest->getXMLParams();
}
else
{
    // Extract parameters from get request and ensure no SQL has been injected
    $myRequest->getGetParams();
}

// ****************
// CHECK PARAMETERS 
// ****************
switch($myRequest->mode)
{
    case "read":
        $myMetaHeader->setRequestType("read");
        if($myAuth->isLoggedIn())
        {
            if(!(gettype($myRequest->id)=="integer") && !($myRequest->id==NULL)) trigger_error("901"."Invalid parameter - 'id' field must be an integer.");
            if(!($myRequest->id>0) && !($myRequest->id==NULL)) trigger_error("901"."Invalid parameter - 'id' field must be a valid positive integer.");
            break;
        }
        else
        {
            $myMetaHeader->requestLogin($myAuth->nonce());
            break;
        }
 
    case "update":
        $myMetaHeader->setRequestType("update");
        if($myAuth->isLoggedIn())
        {
            if($myRequest->id == NULL) trigger_error("902"."Missing parameter - 'id' field is required.");
            if(($myRequest->name == NULL) && ($myRequest->code==NULL)) trigger_error("902"."Missing parameter - either 'name' or 'code' fields (or both) must be specified.");
            break;
        }
        else
        {
            $myMetaHeader->requestLogin($myAuth->nonce());
            break;
        }

    case "delete":
        $myMetaHeader->setRequestType("delete");
        if($myAuth->isLoggedIn())
        {
            if($myRequest->id == NULL) trigger_error("902"."Missing parameter - 'id' field is required.");
            break;
        }
        else
        {
            $myMetaHeader->requestLogin($myAuth->nonce());
            break;
        }


    case "create":
        $myMetaHeader->setRequestType("create");
        $myRequest->id == NULL;
        if($myAuth->isLoggedIn())
        {
            if($myRequest->name == NULL) trigger_error("902"."Missing parameter - 'name' field is required.");
            if($myRequest->code == NULL) trigger_error("902"."Missing parameter - 'code' field is required.");
            break;
        }
        else
        {
            $myMetaHeader->requestLogin($myAuth->nonce());
            break;
        }

    case "failed":
        $myMetaHeader->setRequestType("help");

    default:
        $myMetaHeader->setRequestType("help");
        // Output the resulting XML
        $xmldata ="Details of how to use this web service will be added here later!";
        writeHelpOutput($myMetaHeader);
        die;
}

// *************
// PERFORM QUERY
// *************
$xmldata = "";
//Only attempt to run SQL if there are no errors so far
if(!($myMetaHeader->status == "Error"))
{
    // Create site object 
    $mySite = new site();
    $parentTagBegin = $mySite->getParentTagBegin();
    $parentTagEnd = $mySite->getParentTagEnd();

    // Set existing parameters if updating or deleting from database
    if($myRequest->mode=='update' || $myRequest->mode=='delete') 
    {
        $success = $mySite->setParamsFromDB($myRequest->id);
        if(!$success) 
        {
            trigger_error($mySite->getLastErrorCode().$mySite->getLastErrorMessage());
        }
    }

    // Update parameters in object if updating or creating an object 
    if($myRequest->mode=='update' || $myRequest->mode=='create')
    {
        if (isset($myRequest->name)) $mySite->setName($myRequest->name);
        if (isset($myRequest->code)) $mySite->setCode($myRequest->code);

        // If creating a new record include initial extent if latlong specified
        if($myRequest->mode=='create')
        {
            if ((isset($myRequest->latitude))&& (isset($myRequest->longitude))) $mySite->setInitialExtent($myRequest->latitude, $myRequest->longitude);
        }

        if( (($myRequest->mode=='update') && ($myAuth->sitePermission($myRequest->id, "update")))  || 
            (($myRequest->mode=='create') && ($myAuth->sitePermission($myRequest->id, "create")))    )
        {
            // Write object to database
            $success = $mySite->writeToDB();
            if($success)
            {
                $xmldata=$mySite->asXML();
            }
            else
            {
                trigger_error($mySite->getLastErrorCode().$mySite->getLastErrorMessage());
            }
        }
        else
        {
            trigger_error("103"."Permission denied on siteid". $myRequest->id);
        }
    }

    // Delete record from db if requested
    if($myRequest->mode=='delete')
    {
        if($myAuth->sitePermission($myRequest->id, "delete"))
        {
            // Write to Database
            $success = $mySite->deleteFromDB();
            if($success)
            {
                $xmldata=$mySite->asXML();
            }
            else
            {
                trigger_error($mySite->getLastErrorCode().$mySite->getLastErrorMessage());
            }
        }
        else
        {
            trigger_error("103"."Permission denied on siteid ". $myRequest->id);
        }
    }

    if($myRequest->mode=='read')
    {
        $dbconnstatus = pg_connection_status($dbconn);
        if ($dbconnstatus ===PGSQL_CONNECTION_OK)
        {
            // DB connection ok
            // Build filter conditions
            $filter = "";
            if(isset($myRequest->id))
            {
                $filter="where siteid=".$myRequest->id;
            }
            if(isset($myRequest->name)) 
            {
                if ($filter) {$filter.=" and ";} else {$filter.="where ";}
                $filter.= "name ilike '%".$myRequest->name."%'";
            }
            if(isset($myRequest->code)) 
            {
                if ($filter) {$filter.=" and ";} else {$filter.="where ";}
                $filter.= "code='".$myRequest->code."'";
            }

            // Construct SQL statement
            $sql = "select siteid from tblsite $filter order by siteid";
            
            // Run SQL
            $result = pg_query($dbconn, $sql);
            while ($row = pg_fetch_array($result))
            {
                // Check user has permission to read tree
                if($myAuth->sitePermission($row['siteid'], "read"))
                {
                    $mySite = new site();
                    $success = $mySite->setParamsFromDB($row['siteid']);
                    $success2 = $mySite->setChildParamsFromDB();
                    //$success2 = true;

                    if($success && $success2)
                    {
                        if ($myRequest->format=='kml')
                        {
                            $xmldata .= $mySite->asKML();      
                        }
                        else
                        {
                            $xmldata .= $mySite->asXML();
                        }
                    }
                    else
                    {
                        trigger_error($mySite->getLastErrorCode.$mySite->getLastErrorMessage);
                    }
                }
                else
                {
                    trigger_error("103"."Permission denied on site id ".$row['siteid'], E_USER_WARNING);
                }
            } 
        }
        else
        {
            // Connection bad
            trigger_error("001"."Error connecting to database");
        }
    }
}

// ***********
// OUTPUT DATA
// ***********
switch ($myRequest->format)
{
    case "kml":
        writeKMLOutput($xmldata);
        break;
    case "data":
        writeOutput($myMetaHeader, $xmldata);
        break;
    case "map":
        writeGMapOutput(createOutput($myMetaHeader, $xmldata), $myRequest);
        break;
    default:
        writeOutput($myMetaHeader, $xmldata);
}
