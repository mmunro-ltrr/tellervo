//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-793 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.29 at 01:41:52 PM PDT 
//


package edu.cornell.dendro.corina.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for searchReturnObject.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="searchReturnObject">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="sample"/>
 *     &lt;enumeration value="object"/>
 *     &lt;enumeration value="element"/>
 *     &lt;enumeration value="radius"/>
 *     &lt;enumeration value="taxon"/>
 *     &lt;enumeration value="measurementSeries"/>
 *     &lt;enumeration value="derivedSeries"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "searchReturnObject")
@XmlEnum
public enum SearchReturnObject {

    @XmlEnumValue("sample")
    SAMPLE("sample"),
    @XmlEnumValue("object")
    OBJECT("object"),
    @XmlEnumValue("element")
    ELEMENT("element"),
    @XmlEnumValue("radius")
    RADIUS("radius"),
    @XmlEnumValue("taxon")
    TAXON("taxon"),
    @XmlEnumValue("measurementSeries")
    MEASUREMENT_SERIES("measurementSeries"),
    @XmlEnumValue("derivedSeries")
    DERIVED_SERIES("derivedSeries");
    private final String value;

    SearchReturnObject(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SearchReturnObject fromValue(String v) {
        for (SearchReturnObject c: SearchReturnObject.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
