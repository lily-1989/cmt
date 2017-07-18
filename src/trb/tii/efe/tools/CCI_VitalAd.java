package trb.tii.efe.tools;

import java.util.HashMap;
import java.util.Date;


/**
 * CCI_VitalAd object extends CCI_Ad.  This object stores and can retrieve all DB related values for
 * CCI_Vital Notice Ads.
 */
public class CCI_VitalAd extends CCI_Ad {

    // Variables specific to CCI_VitalAd objects
    private String firstName = "";
    private String lastName = "";
    private String zip = "";
    private String age = "";
    private String city = "";
    private String neighborhood = "";
    private String state = "";
    private String dateDeath = "";
    private String dateBirth = "";
    private String funeralHome = "";

    public static HashMap nullFieldsMap = new HashMap();

    /**
     * Constructor method sets fields as true if they are allowed to be null.
     */
    public CCI_VitalAd() {
        nullFieldsMap.put("ADID", new Boolean(false));
        nullFieldsMap.put("CLASSID", new Boolean(false));
        nullFieldsMap.put("CUSTOMERCITY", new Boolean(false));
        nullFieldsMap.put("CUSTOMERSTATE", new Boolean(false));
        nullFieldsMap.put("CUSTOMERZIP", new Boolean(false));
        nullFieldsMap.put("FEEDID", new Boolean(false));
        nullFieldsMap.put("FIRSTNAME", new Boolean(false));
        nullFieldsMap.put("INSERTIONID", new Boolean(true));
        nullFieldsMap.put("LASTNAME", new Boolean(false));
        nullFieldsMap.put("PHOTOCOUNT", new Boolean(true));
        nullFieldsMap.put("PRINTDESC", new Boolean(false));
        nullFieldsMap.put("PUBLICATION", new Boolean(false));
        nullFieldsMap.put("STARTDATE", new Boolean(false));
        nullFieldsMap.put("STOPDATE", new Boolean(false));
        nullFieldsMap.put("TITLE", new Boolean(false));
        nullFieldsMap.put("UPSELL", new Boolean(false));
        nullFieldsMap.put("COMMERCIALRELEASE", new Boolean(false));
        nullFieldsMap.put("CONTACTEMAIL", new Boolean(true));
        nullFieldsMap.put("CUSTOMEREMAIL", new Boolean(true));
        nullFieldsMap.put("DATERECEIVED", new Boolean(true));
        nullFieldsMap.put("DATEUPDATED", new Boolean(true));
        nullFieldsMap.put("ENHANCEDDESC", new Boolean(true));
        nullFieldsMap.put("ENHANCEEMAIL", new Boolean(true));
        nullFieldsMap.put("FIRSTRECEIVED", new Boolean(true));
        nullFieldsMap.put("PHOTOREF", new Boolean(true));
        nullFieldsMap.put("ZIP", new Boolean(true));
        nullFieldsMap.put("AGE", new Boolean(true));
        nullFieldsMap.put("CITY", new Boolean(true));
        nullFieldsMap.put("NEIGHBOORHOOD", new Boolean(true));
        nullFieldsMap.put("STATE", new Boolean(true));
        nullFieldsMap.put("DATE_DEATH", new Boolean(true));
        nullFieldsMap.put("DATE_BIRTH", new Boolean(true));
        nullFieldsMap.put("FUNERAL_HOME", new Boolean(true));
        nullFieldsMap.put("CONTACT_PHONE", new Boolean(true));
        nullFieldsMap.put("CANCELED", new Boolean(true));
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
     * Getter/Setter Methods for Vital Notice specific ads
     */
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateDeath() {
        return dateDeath;
    }

    public void setDateDeath(String dateDeath) {
        this.dateDeath = dateDeath;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getFuneralHome() {
        return funeralHome;
    }

    public void setFuneralHome(String funeralHome) {
        this.funeralHome = funeralHome;
    }
}
