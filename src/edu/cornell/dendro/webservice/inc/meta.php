<?php
/**
 * *******************************************************************
 * PHP Corina Middleware
 * E-Mail: p.brewer@cornell.edu
 * Requirements : PHP >= 5.2
 * 
 * @author Peter Brewer
 * @license http://opensource.org/licenses/gpl-license.php GPL
 * @package CorinaWS
 * *******************************************************************
 */

require_once("dbhelper.php");
$myMetaHeader = new meta();


class meta
{
  var $securityUserID = NULL;
  var $username = NULL;
  var $firstname = NULL;
  var $lastname = NULL;
  var $wsversion = NULL;
  var $clientversion = NULL;
  var $requestdate = NULL;
  var $startTimestamp = NULL;
  var $requesturl = NULL;
  var $requesttype = "Read";
  var $status = "OK";
  var $messages = array();
  var $nonce = NULL;
  var $seq = NULL;
  var $timing = array();

  function meta($theRequestType="")
  {
    global $wsversion;
    $this->startTimestamp = microtime(true);
    $this->requestdate= date(DATE_ISO8601);
    $this->requesturl= dbHelper::escapeXMLChars($_SERVER['REQUEST_URI']);
    $this->clientversion= dbHelper::escapeXMLChars($_SERVER['HTTP_USER_AGENT']);
    if($theRequestType)  $this->requesttype= $theRequestType;
    $this->wsversion = $wsversion;
  }
  function setRequestType($theRequestType)
  {
    $this->requesttype= $theRequestType;
  }

  function setUser($theUsername, $theFirstname, $theLastname, $theSecurityUserID)
  {
    // Setter for user details
    $this->username = $theUsername;
    $this->firstname = $theFirstname;
    $this->lastname = $theLastname;
    $this->securityUserID = $theSecurityUserID;
  }
  
  function setMessage($theCode, $theMessage, $theStatus="Error")
  {
    // Setter for error and warning messages

    $message = array($theCode => $theMessage);
    array_push($this->messages, $message);

    // Set corresponding status 
    if($this->status=="Error" || $theStatus=="Error") 
    {
        $this->status="Error";
    }
    elseif($this->status=="Warning" || $theStatus=="Warning")
    {
        $this->status="Warning";
    }
    else
    {
        $this->status="OK";
    }
  }

  function setTiming($theLabel)
  {
    $message = array($theLabel => round(((microtime(true)*1000)-($this->startTimestamp*1000)), 0));
    array_push($this->timing, $message);
  }

  function requestLogin($nonce, $seq, $messageType="Error")
  {
      $this->seq=$seq;
      $this->nonce= $nonce;
      if(!($messageType=="OK"))
      {
          $this->setMessage("102", "You must login to run this query.", $messageType);
      }
  }

  function getObjectName()
  {
      // Trim off all after the ?
      $string = explode("?", $this->requesturl);
      // Remove the first '/'
      $string = substr($string[0], 1);
      // Remove the .php
      $string = substr($string, 0, -4);
      // Uppercase the first letter
      $string = ucfirst($string);
      return $string;
  }

  function getIsLoginRequired()
  {
     foreach($this->messages as $message)
     {
        if (isset($message[102]))
        {
           return True;
        }
     }
     return False;
  }

  /**
   * Search the complete HTTP_USER_AGENT for identifiers of known client programs and return the associated version number.  
   * If no known clients are identifier then returns false
   *
   * @return Mixed
   */
  function getClientVersion()
  {
  	global $corinaClientIdentifiers;
  	foreach($corinaClientIdentifiers as $app)
  	{ 	
	  	if (strstr($this->clientversion, $app['name']))
	  	{
	  		return (float) substr(strstr($this->clientversion, $app['name']), strlen($app['name'])+1);
	  		break;
	  	}	
  	}	
  	return false;
  }
  
  /**
   * Is the version of the client being used valid?
   *
   * @return Boolean
   */
  function isClientVersionValid()
  {
  	global $corinaClientIdentifiers;
	global $onlyAllowKnownClients;
	
	
  	foreach($corinaClientIdentifiers as $app)
  	{ 	
	  	if (strstr($this->clientversion, $app['name']))
	  	{
	  		$minVersion =  $app['minVersion'];
	  		if($this->getClientVersion()>=$minVersion) 
	  		{
	  			return true;
	  		}
	  		else
	  		{
	  			return false;
	  		}
	  	}	
  	}

  	// Return either false or null depending on if strict client checking is enabled
  	if($onlyAllowKnownClients===TRUE)
  	{ 
  		return false;
  	}
  	else
  	{
  		return null;
  	}
  }
  
  
  function getClientName()
  {
  	global $corinaClientIdentifiers;	
  	
  	foreach($corinaClientIdentifiers as $app)
  	{ 	
	  	if (strstr($this->clientversion, $app['name']))
	  	{
	  		return $app['name'];
	  	}	
  	}	
  	return false;
  }
  
  function getMinRequiredClientVersion()
  {
  	global $corinaClientIdentifiers;	
  	
  	foreach($corinaClientIdentifiers as $app)
  	{ 	
	  	if (strstr($this->clientversion, $app['name']))
	  	{
	  		return $app['minVersion'];
	  	}	
  	}	
  	return false;
  }
  
    
  function asXML()
  {
    // Get class as XML 
    $xml="<header>\n";
    if (!($this->username==NULL))
    {
        $xml.="<user id=\"".$this->securityUserID."\" username=\"".$this->username."\" firstname=\"".$this->firstname."\" lastname=\"".$this->lastname."\" />\n";
    }
    $xml.="<wsversion>".$this->wsversion."</wsversion>\n";
    $xml.="<clientversion>".$this->clientversion."</clientversion>\n";
    $xml.="<requestdate>".$this->requestdate."</requestdate>\n";
    $xml.="<queryTime unit=\"seconds\">".round((microtime(true)-$this->startTimestamp), 2)."</queryTime>\n";
    $xml.="<requesturl>".$this->requesturl."</requesturl>\n";
    $xml.="<requesttype>".$this->requesttype."</requesttype>\n";
    $xml.="<status>".$this->status."</status>\n";

    // Remove duplicate messages
    $this->messages = array_unique($this->messages);

    foreach($this->messages as $item)
    {
        foreach($item as $code => $message)
        {
          $xml.="<message code=\"".$code."\">".$message."</message>\n";
        }
    }

    foreach($this->timing as $item)
    {
        foreach($item as $code => $message)
        {
          $xml.="<timing label=\"".$code."\">".$message."</timing>\n";
        }
    }

    if ($this->nonce)
    {
        $xml.="<nonce seq=\"".$this->seq."\">".$this->nonce."</nonce>\n";
    }

    $xml.="</header>\n";
    return $xml;
  }
} 
?>
