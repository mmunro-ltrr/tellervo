//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-793 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.25 at 01:30:38 AM PDT 
//


package net.opengis.gml.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *             A property that has a surface as its value domain shall contain 
 *             an appropriate geometry element encapsulated in an element
 *             of this type.
 *          
 * 
 * <p>Java class for SurfacePropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SurfacePropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}_Surface"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SurfacePropertyType", propOrder = {
    "surface"
})
public class SurfacePropertyType {

    @XmlElementRef(name = "_Surface", namespace = "http://www.opengis.net/gml", type = JAXBElement.class)
    protected JAXBElement<? extends AbstractSurfaceType> surface;

    /**
     * Gets the value of the _Surface property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link AbstractSurfaceType }{@code >}
     *     {@link JAXBElement }{@code <}{@link SurfaceType }{@code >}
     *     {@link JAXBElement }{@code <}{@link PolygonType }{@code >}
     *     
     */
    public JAXBElement<? extends AbstractSurfaceType> get_Surface() {
        return surface;
    }

    /**
     * Sets the value of the _Surface property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link AbstractSurfaceType }{@code >}
     *     {@link JAXBElement }{@code <}{@link SurfaceType }{@code >}
     *     {@link JAXBElement }{@code <}{@link PolygonType }{@code >}
     *     
     */
    public void set_Surface(JAXBElement<? extends AbstractSurfaceType> value) {
        this.surface = ((JAXBElement<? extends AbstractSurfaceType> ) value);
    }

    public boolean isSet_Surface() {
        return (this.surface!= null);
    }

}
