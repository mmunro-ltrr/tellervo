
package edu.cornell.dendro.corina.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for corinaRequestStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="corinaRequestStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="Warning"/>
 *     &lt;enumeration value="Notice"/>
 *     &lt;enumeration value="Error"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "corinaRequestStatus")
@XmlEnum
public enum CorinaRequestStatus {

    OK("OK"),
    @XmlEnumValue("Warning")
    WARNING("Warning"),
    @XmlEnumValue("Notice")
    NOTICE("Notice"),
    @XmlEnumValue("Error")
    ERROR("Error");
    private final String value;

    CorinaRequestStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CorinaRequestStatus fromValue(String v) {
        for (CorinaRequestStatus c: CorinaRequestStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}