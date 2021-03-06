
-- 
-- Alterations required to make securityuserid a uuid rather than a standard serial
--

-- Add new uuid field to tblsecurityuser.  UUIDs will be autogenerated
ALTER TABLE tblsecurityuser ADD COLUMN securityuseruuid uuid NOT NULL DEFAULT uuid_generate_v4();

-- Fix curation trigger function before continuing
CREATE OR REPLACE FUNCTION check_tblcuration_loanid_is_not_null_when_loaned()
  RETURNS trigger AS
$BODY$DECLARE
BEGIN
IF NEW.curationstatusid=2 THEN
  IF NEW.loanid IS NULL THEN
RAISE EXCEPTION 'Loan information not specified for loan curation record';
RETURN NULL;
  END IF;
ELSE
  IF NEW.loanid IS NOT NULL THEN
RAISE EXCEPTION 'Loan information can only be provided when loaning a sample';
RETURN NULL;
  END IF;
END IF;
RETURN NEW;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION check_tblcuration_loanid_is_not_null_when_loaned()
  OWNER TO tellervo;

-- Add coresponding uuid columns in all tables that reference tblsecurityuser
ALTER TABLE tblsecurityusermembership ADD COLUMN securityuseruuid uuid;
ALTER TABLE tblvmeasurement ADD COLUMN owneruseruuid uuid;
ALTER TABLE tblmeasurement ADD COLUMN measuredbyuuid uuid;
ALTER TABLE tblmeasurement ADD COLUMN supervisedbyuuid uuid;
ALTER TABLE tblrequestlog ADD COLUMN securityuseruuid uuid;
ALTER TABLE tbliptracking ADD COLUMN securityuseruuid uuid;
ALTER TABLE tblcuration ADD COLUMN curatoruuid uuid;

-- Populate new uuid columns in foreign table
UPDATE tblsecurityusermembership SET securityuseruuid = (SELECT securityuseruuid FROM tblsecurityuser WHERE tblsecurityuser.securityuserid = tblsecurityusermembership.securityuserid);
UPDATE tblvmeasurement SET owneruseruuid = (SELECT securityuseruuid FROM tblsecurityuser WHERE tblsecurityuser.securityuserid = tblvmeasurement.owneruserid);
UPDATE tblmeasurement SET measuredbyuuid = (SELECT securityuseruuid FROM tblsecurityuser WHERE tblsecurityuser.securityuserid = tblmeasurement.measuredbyid);
UPDATE tblmeasurement SET supervisedbyuuid = (SELECT securityuseruuid FROM tblsecurityuser WHERE tblsecurityuser.securityuserid = tblmeasurement.supervisedbyid);
UPDATE tblrequestlog SET securityuseruuid = (SELECT securityuseruuid FROM tblsecurityuser WHERE tblsecurityuser.securityuserid = tblrequestlog.securityuserid);
UPDATE tbliptracking SET securityuseruuid = (SELECT securityuseruuid FROM tblsecurityuser WHERE tblsecurityuser.securityuserid = tbliptracking.securityuserid);
UPDATE tblcuration SET curatoruuid = (SELECT securityuseruuid FROM tblsecurityuser WHERE tblsecurityuser.securityuserid = tblcuration.curatorid);

-- Drop fkey constraints from tables that reference tblsecurityuser
ALTER TABLE tblsecurityusermembership DROP CONSTRAINT "fkey_securityusermembership-securitygroup";
ALTER TABLE tblvmeasurement DROP CONSTRAINT "fkey_vmeasurement-securityuser";
ALTER TABLE tblmeasurement DROP CONSTRAINT "fkey_measurement-securityuser";
ALTER TABLE tblmeasurement DROP CONSTRAINT "fkey_measurement-supervisedby";
ALTER TABLE tblrequestlog DROP CONSTRAINT "fkey_requestlog-securityuser";
ALTER TABLE tbliptracking DROP CONSTRAINT "fkey_iptracking_securityuser";
ALTER TABLE tblcuration DROP CONSTRAINT "fkey_tblcuration-tblsecurityuser";

-- Drop all views that reference tblsecurityuser
DROP VIEW vwcomprehensivevm;
DROP VIEW vwcomprehensivevm2;
DROP VIEW vw_bigbrothertracking;
DROP VIEW vw_requestlogsummary;
DROP VIEW vwcountperpersonperobject;
DROP VIEW vwleaderboard;
DROP VIEW vwringsleaderboard;
DROP VIEW vwringwidthleaderboard;
DROP VIEW vwsampleleaderboard;
DROP VIEW vwtblcuration;
DROP VIEW vwtblsample;
DROP VIEW vwtblcurationmostrecent;
DROP VIEW vwtblmeasurement;
DROP VIEW vwtblvmeasurement;

-- Drop old serial fkey fields
ALTER TABLE tblsecurityusermembership DROP COLUMN securityuserid;
ALTER TABLE tblvmeasurement DROP COLUMN owneruserid;
ALTER TABLE tblmeasurement DROP COLUMN measuredbyid;
ALTER TABLE tblmeasurement DROP COLUMN supervisedbyid;
ALTER TABLE tblrequestlog DROP COLUMN securityuserid;
ALTER TABLE tbliptracking DROP COLUMN securityuserid;
ALTER TABLE tblcuration DROP COLUMN curatorid;

-- Rename uuid fkey fields to the same as the original serial field names
ALTER TABLE tblsecurityusermembership RENAME COLUMN securityuseruuid TO securityuserid;
ALTER TABLE tblvmeasurement RENAME COLUMN owneruseruuid TO owneruserid;
ALTER TABLE tblmeasurement RENAME COLUMN measuredbyuuid TO measuredbyid;
ALTER TABLE tblmeasurement RENAME COLUMN supervisedbyuuid TO supervisedbyid;
ALTER TABLE tblrequestlog RENAME COLUMN securityuseruuid TO securityuserid;
ALTER TABLE tbliptracking RENAME COLUMN securityuseruuid TO securityuserid;
ALTER TABLE tblcuration RENAME COLUMN curatoruuid TO curatorid;



-- Drop the pkey from the tblsecurityuser table 
ALTER TABLE tblsecurityuser DROP CONSTRAINT pkey_securityuser;
ALTER TABLE tblsecurityuser DROP COLUMN securityuserid;

-- Rename tblsecurityuser uuid field to securityuserid
ALTER TABLE tblsecurityuser RENAME COLUMN securityuseruuid TO securityuserid;

-- Create a new pkey on the uuid field
ALTER TABLE tblsecurityuser ADD CONSTRAINT pkey_securityuser PRIMARY KEY (securityuserid);

-- Recreate fkeys of tables that reference tblsecurityuser
ALTER TABLE tblsecurityusermembership ADD CONSTRAINT "fkey_securityusermembership-securitygroup" FOREIGN KEY (securitygroupid)
      REFERENCES tblsecuritygroup (securitygroupid) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE tblvmeasurement ADD CONSTRAINT "fkey_vmeasurement-securityuser" FOREIGN KEY (owneruserid)
      REFERENCES tblsecurityuser (securityuserid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tblmeasurement ADD CONSTRAINT "fkey_measurement-securityuser" FOREIGN KEY (measuredbyid)
      REFERENCES tblsecurityuser (securityuserid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tblmeasurement ADD CONSTRAINT "fkey_measurement-supervisedby" FOREIGN KEY (supervisedbyid)
      REFERENCES tblsecurityuser (securityuserid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tblrequestlog ADD CONSTRAINT "fkey_requestlog-securityuser" FOREIGN KEY (securityuserid)
      REFERENCES tblsecurityuser (securityuserid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tbliptracking ADD CONSTRAINT "fkey_iptracking_securityuser" FOREIGN KEY (securityuserid)
      REFERENCES tblsecurityuser (securityuserid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE tblcuration ADD CONSTRAINT "fkey_tblcuration-tblsecurityuser" FOREIGN KEY (curatorid)
      REFERENCES tblsecurityuser (securityuserid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

  
  
  
  




  