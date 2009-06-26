//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-793 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.26 at 04:06:24 PM PDT 
//


package org.tridas.schema;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tridas.org/1.2}missingHeartwoodRingsToPith" minOccurs="0"/>
 *         &lt;element ref="{http://www.tridas.org/1.2}missingHeartwoodRingsToPithFoundation" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="presence" use="required" type="{http://www.tridas.org/1.2}complexPresenceAbsence" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "missingHeartwoodRingsToPith",
    "missingHeartwoodRingsToPithFoundation"
})
@XmlRootElement(name = "heartwood")
public class TridasHeartwood
    implements Serializable
{

    private final static long serialVersionUID = 1001L;
    protected String missingHeartwoodRingsToPith;
    protected String missingHeartwoodRingsToPithFoundation;
    @XmlAttribute(name = "presence", required = true)
    protected ComplexPresenceAbsence presence;

    /**
     * Gets the value of the missingHeartwoodRingsToPith property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMissingHeartwoodRingsToPith() {
        return missingHeartwoodRingsToPith;
    }

    /**
     * Sets the value of the missingHeartwoodRingsToPith property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMissingHeartwoodRingsToPith(String value) {
        this.missingHeartwoodRingsToPith = value;
    }

    public boolean isSetMissingHeartwoodRingsToPith() {
        return (this.missingHeartwoodRingsToPith!= null);
    }

    /**
     * Gets the value of the missingHeartwoodRingsToPithFoundation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMissingHeartwoodRingsToPithFoundation() {
        return missingHeartwoodRingsToPithFoundation;
    }

    /**
     * Sets the value of the missingHeartwoodRingsToPithFoundation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMissingHeartwoodRingsToPithFoundation(String value) {
        this.missingHeartwoodRingsToPithFoundation = value;
    }

    public boolean isSetMissingHeartwoodRingsToPithFoundation() {
        return (this.missingHeartwoodRingsToPithFoundation!= null);
    }

    /**
     * Gets the value of the presence property.
     * 
     * @return
     *     possible object is
     *     {@link ComplexPresenceAbsence }
     *     
     */
    public ComplexPresenceAbsence getPresence() {
        return presence;
    }

    /**
     * Sets the value of the presence property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComplexPresenceAbsence }
     *     
     */
    public void setPresence(ComplexPresenceAbsence value) {
        this.presence = value;
    }

    public boolean isSetPresence() {
        return (this.presence!= null);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
