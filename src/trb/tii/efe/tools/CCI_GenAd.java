package trb.tii.efe.tools;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;


/**
 * CCI_GenAd object extends CCI_Ad.  This object stores and can retrieve all DB related values for
 * CCI Gen Classified Ads.
 */
public class CCI_GenAd extends CCI_Ad {

    private String type = "";
    private String zip = "";
    private String price = "";
    private Integer oBO = null;
    private String country = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String breed = "";
    private String advertiserUrl = "";
    private String eventStartTime = "";
    private String eventEndTime = "";
    private String eventDate = "";
    private String color = "";
    private String gender = "";
    private String age = "";
    private String firstName1 = "";
    private String lastName1 = "";
    private String firstName2 = "";
    private String lastName2 = "";
    private String celebrationType = "";

    public static HashMap nullFieldsMap = new HashMap();

    public CCI_GenAd() {

        nullFieldsMap.put("ADID", new Boolean(false));
        nullFieldsMap.put("CLASSID", new Boolean(false));
        nullFieldsMap.put("CUSTOMERITY", new Boolean(false));
        nullFieldsMap.put("CUSTOMERSTATE", new Boolean(false));
        nullFieldsMap.put("CUSTOMERZIP", new Boolean(false));
        nullFieldsMap.put("FEEDID", new Boolean(false));
        nullFieldsMap.put("INSERTIONID", new Boolean(true));
        nullFieldsMap.put("PHOTOCOUNT", new Boolean(true));
        nullFieldsMap.put("PRINTDESC", new Boolean(false));
        nullFieldsMap.put("PUBLICATION", new Boolean(false));
        nullFieldsMap.put("STARTDATE", new Boolean(false));
        nullFieldsMap.put("STOPDATE", new Boolean(false));
        nullFieldsMap.put("TITLE", new Boolean(false));
        nullFieldsMap.put("UPSELL", new Boolean(false));
        nullFieldsMap.put("COMMERCIALRELEASE", new Boolean(false));
        nullFieldsMap.put("ADDRESS", new Boolean(true));
        nullFieldsMap.put("ADVERTISERURL", new Boolean(true));
        nullFieldsMap.put("BREED", new Boolean(true));
        nullFieldsMap.put("CITY", new Boolean(true));
        nullFieldsMap.put("CONTACTEMAIL", new Boolean(true));
        nullFieldsMap.put("CONTACTPHONE", new Boolean(true));
        nullFieldsMap.put("OBO", new Boolean(true));
        nullFieldsMap.put("COUNTRY", new Boolean(true));
        nullFieldsMap.put("CUSTEMAIL", new Boolean(true));
        nullFieldsMap.put("DATERECEIVED", new Boolean(true));
        nullFieldsMap.put("DATEUPDATED", new Boolean(true));
        nullFieldsMap.put("ENHANCEDDESC", new Boolean(true));
        nullFieldsMap.put("ENHANCEEMAIL", new Boolean(true));
        nullFieldsMap.put("FIRSTRECEIVED", new Boolean(true));
        nullFieldsMap.put("PHOTOREF", new Boolean(true));
        nullFieldsMap.put("PRICE", new Boolean(true));
        nullFieldsMap.put("STATE", new Boolean(true));
        nullFieldsMap.put("TYPE", new Boolean(true));
        nullFieldsMap.put("ZIP", new Boolean(true));
        nullFieldsMap.put("EVENTSTARTTIME", new Boolean(true));
        nullFieldsMap.put("EVENTENDTIME", new Boolean(true));
        nullFieldsMap.put("EVENTDATE", new Boolean(true));
        nullFieldsMap.put("COLOR", new Boolean(true));
        nullFieldsMap.put("GENDER", new Boolean(true));
        nullFieldsMap.put("AGE", new Boolean(true)); 
        nullFieldsMap.put("CANCELED", new Boolean(true));
        nullFieldsMap.put("FIRSTNAME1", new Boolean(true));
        nullFieldsMap.put("LASTNAME1", new Boolean(true));
        nullFieldsMap.put("FIRSTNAME2", new Boolean(true));
        nullFieldsMap.put("LASTNAME2", new Boolean(true));
        nullFieldsMap.put("CELEBRATIONTYPE", new Boolean(true));
    }

    public boolean isFieldAllowedNull(String fieldName) {
        boolean isAllowedNull = true;
        try {
            isAllowedNull = ((Boolean)nullFieldsMap.get(fieldName)).booleanValue();
        }
        catch (Exception e) {}

        return isAllowedNull;
    }


    /**
     * Getter/Setter Methods for Apartment specific ads
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
   
    public Integer getoBO() {
        return oBO;
    }

    public void setoBO(Integer oBO) {
        this.oBO = oBO;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAdvertiserUrl() {
        return advertiserUrl;
    }

    public void setAdvertiserUrl(String advertiserUrl) {
        this.advertiserUrl = advertiserUrl;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFirstName1() {
        return firstName1;
    }

    public void setFirstName1(String firstName1) {
        this.firstName1 = firstName1;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getFirstName2() {
        return firstName2;
    }

    public void setFirstName2(String firstName2) {
        this.firstName2 = firstName2;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getCelebrationType() {
        return celebrationType;
    }

    public void setCelebrationType(String celebrationType) {
        this.celebrationType = celebrationType;
    }
}
