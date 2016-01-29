# Patches for handling ODK Forms

ALTER TABLE tblsecurityuser ADD COLUMN odkpassword varchar;


CREATE OR REPLACE FUNCTION "update_odkformdef-versionincrement"()
  RETURNS trigger AS
$BODY$BEGIN
NEW.version = OLD.version+1;
RETURN NEW;
END;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION "update_odkformdef-versionincrement"()
  OWNER TO tellervo;

CREATE TABLE tblodkdefinition
(
  odkdefinitionid uuid NOT NULL DEFAULT uuid_generate_v1mc(),
  name character varying NOT NULL,
  definition character varying NOT NULL,
  ownerid uuid NOT NULL,
  ispublic boolean DEFAULT false,
  version integer NOT NULL DEFAULT 1,
  CONSTRAINT pkey_odkdefinition PRIMARY KEY (odkdefinitionid),
  CONSTRAINT "fkey_odkdefinition-securityuser" FOREIGN KEY (ownerid)
      REFERENCES tblsecurityuser (securityuserid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

-- Trigger: trigger_updateodkformversion-increment on tblodkdefinition

-- DROP TRIGGER "trigger_updateodkformversion-increment" ON tblodkdefinition;

CREATE TRIGGER "trigger_updateodkformversion-increment"
  BEFORE UPDATE
  ON tblodkdefinition
  FOR EACH ROW
  EXECUTE PROCEDURE "update_odkformdef-versionincrement"();

