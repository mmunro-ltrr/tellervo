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
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.tridas.org/1.2>controlledVoc">
 *       &lt;attribute name="normalTridas" type="{http://www.tridas.org/1.2}normalTridasVariable" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "variable")
public class TridasVariable
    extends ControlledVoc
    implements Serializable
{

    private final static long serialVersionUID = 1001L;
    @XmlAttribute(name = "normalTridas")
    protected NormalTridasVariable normalTridas;

    /**
     * Gets the value of the normalTridas property.
     * 
     * @return
     *     possible object is
     *     {@link NormalTridasVariable }
     *     
     */
    public NormalTridasVariable getNormalTridas() {
        return normalTridas;
    }

    /**
     * Sets the value of the normalTridas property.
     * 
     * @param value
     *     allowed object is
     *     {@link NormalTridasVariable }
     *     
     */
    public void setNormalTridas(NormalTridasVariable value) {
        this.normalTridas = value;
    }

    public boolean isSetNormalTridas() {
        return (this.normalTridas!= null);
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
