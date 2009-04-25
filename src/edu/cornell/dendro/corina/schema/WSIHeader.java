//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-793 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.25 at 01:30:38 AM PDT 
//


package edu.cornell.dendro.corina.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="user" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="username" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="firstname" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="lastname" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="wsVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="clientVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="queryTime">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>double">
 *                 &lt;attribute name="unit">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="seconds"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="requestUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="requestType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="search"/>
 *               &lt;enumeration value="create"/>
 *               &lt;enumeration value="read"/>
 *               &lt;enumeration value="update"/>
 *               &lt;enumeration value="delete"/>
 *               &lt;enumeration value="plainlogin"/>
 *               &lt;enumeration value="securelogin"/>
 *               &lt;enumeration value="nonce"/>
 *               &lt;enumeration value="logout"/>
 *               &lt;enumeration value="help"/>
 *               &lt;enumeration value="assign"/>
 *               &lt;enumeration value="unassign"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="status">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="OK"/>
 *               &lt;enumeration value="Warning"/>
 *               &lt;enumeration value="Notice"/>
 *               &lt;enumeration value="Error"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{http://dendro.cornell.edu/schema/corina/1.0}message" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="timing" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://dendro.cornell.edu/schema/corina/1.0}nonce" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "user",
    "wsVersion",
    "clientVersion",
    "requestDate",
    "queryTime",
    "requestUrl",
    "requestType",
    "status",
    "message",
    "timing",
    "nonce"
})
@XmlRootElement(name = "header")
public class WSIHeader {

    protected WSIHeader.User user;
    @XmlElement(required = true)
    protected String wsVersion;
    @XmlElement(required = true)
    protected String clientVersion;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestDate;
    @XmlElement(required = true)
    protected WSIHeader.QueryTime queryTime;
    @XmlElement(required = true)
    protected String requestUrl;
    @XmlElement(required = true)
    protected String requestType;
    @XmlElement(required = true)
    protected String status;
    protected List<WSIMessage> message;
    protected List<String> timing;
    protected WSINonce nonce;

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link WSIHeader.User }
     *     
     */
    public WSIHeader.User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSIHeader.User }
     *     
     */
    public void setUser(WSIHeader.User value) {
        this.user = value;
    }

    public boolean isSetUser() {
        return (this.user!= null);
    }

    /**
     * Gets the value of the wsVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWsVersion() {
        return wsVersion;
    }

    /**
     * Sets the value of the wsVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWsVersion(String value) {
        this.wsVersion = value;
    }

    public boolean isSetWsVersion() {
        return (this.wsVersion!= null);
    }

    /**
     * Gets the value of the clientVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientVersion() {
        return clientVersion;
    }

    /**
     * Sets the value of the clientVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientVersion(String value) {
        this.clientVersion = value;
    }

    public boolean isSetClientVersion() {
        return (this.clientVersion!= null);
    }

    /**
     * Gets the value of the requestDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestDate() {
        return requestDate;
    }

    /**
     * Sets the value of the requestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestDate(XMLGregorianCalendar value) {
        this.requestDate = value;
    }

    public boolean isSetRequestDate() {
        return (this.requestDate!= null);
    }

    /**
     * Gets the value of the queryTime property.
     * 
     * @return
     *     possible object is
     *     {@link WSIHeader.QueryTime }
     *     
     */
    public WSIHeader.QueryTime getQueryTime() {
        return queryTime;
    }

    /**
     * Sets the value of the queryTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSIHeader.QueryTime }
     *     
     */
    public void setQueryTime(WSIHeader.QueryTime value) {
        this.queryTime = value;
    }

    public boolean isSetQueryTime() {
        return (this.queryTime!= null);
    }

    /**
     * Gets the value of the requestUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * Sets the value of the requestUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestUrl(String value) {
        this.requestUrl = value;
    }

    public boolean isSetRequestUrl() {
        return (this.requestUrl!= null);
    }

    /**
     * Gets the value of the requestType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Sets the value of the requestType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestType(String value) {
        this.requestType = value;
    }

    public boolean isSetRequestType() {
        return (this.requestType!= null);
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    public boolean isSetStatus() {
        return (this.status!= null);
    }

    /**
     * Gets the value of the message property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the message property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WSIMessage }
     * 
     * 
     */
    public List<WSIMessage> getMessage() {
        if (message == null) {
            message = new ArrayList<WSIMessage>();
        }
        return this.message;
    }

    public boolean isSetMessage() {
        return ((this.message!= null)&&(!this.message.isEmpty()));
    }

    public void unsetMessage() {
        this.message = null;
    }

    /**
     * Gets the value of the timing property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the timing property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTiming().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTiming() {
        if (timing == null) {
            timing = new ArrayList<String>();
        }
        return this.timing;
    }

    public boolean isSetTiming() {
        return ((this.timing!= null)&&(!this.timing.isEmpty()));
    }

    public void unsetTiming() {
        this.timing = null;
    }

    /**
     * Gets the value of the nonce property.
     * 
     * @return
     *     possible object is
     *     {@link WSINonce }
     *     
     */
    public WSINonce getNonce() {
        return nonce;
    }

    /**
     * Sets the value of the nonce property.
     * 
     * @param value
     *     allowed object is
     *     {@link WSINonce }
     *     
     */
    public void setNonce(WSINonce value) {
        this.nonce = value;
    }

    public boolean isSetNonce() {
        return (this.nonce!= null);
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>double">
     *       &lt;attribute name="unit">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="seconds"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class QueryTime {

        @XmlValue
        protected double value;
        @XmlAttribute(name = "unit")
        protected String unit;

        /**
         * Gets the value of the value property.
         * 
         */
        public double getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         */
        public void setValue(double value) {
            this.value = value;
        }

        public boolean isSetValue() {
            return true;
        }

        /**
         * Gets the value of the unit property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnit() {
            return unit;
        }

        /**
         * Sets the value of the unit property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnit(String value) {
            this.unit = value;
        }

        public boolean isSetUnit() {
            return (this.unit!= null);
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="username" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="firstname" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="lastname" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class User {

        @XmlAttribute(name = "id")
        protected String id;
        @XmlAttribute(name = "username")
        protected String username;
        @XmlAttribute(name = "firstname")
        protected String firstname;
        @XmlAttribute(name = "lastname")
        protected String lastname;

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
        }

        public boolean isSetId() {
            return (this.id!= null);
        }

        /**
         * Gets the value of the username property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the value of the username property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUsername(String value) {
            this.username = value;
        }

        public boolean isSetUsername() {
            return (this.username!= null);
        }

        /**
         * Gets the value of the firstname property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFirstname() {
            return firstname;
        }

        /**
         * Sets the value of the firstname property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFirstname(String value) {
            this.firstname = value;
        }

        public boolean isSetFirstname() {
            return (this.firstname!= null);
        }

        /**
         * Gets the value of the lastname property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLastname() {
            return lastname;
        }

        /**
         * Sets the value of the lastname property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLastname(String value) {
            this.lastname = value;
        }

        public boolean isSetLastname() {
            return (this.lastname!= null);
        }

    }

}
