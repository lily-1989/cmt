package trb.tii.efe.tools;

import java.util.Date;
import java.util.HashMap;


/**
 * CCI_GenAd object extends CCI_Ad.  This object stores and can retrieve all DB related values for
 * CCI Gen Classified Ads.
 */
public class CCI_REAd extends CCI_Ad {

    private String saleLease = "";
    private String pricePerSqFt = "";
    private String propertyType = "";
    private String propertyCity = "";
    private String propertyState = "";
    private String propertyZip = "";
    private String price = "";
    private String bedrooms = "";
    private String baths = "";
    private String propertyAddress = "";
    private String neighborhood = "";
    private String country = "";
    private Date openHouseDate1 = null;
    private String openHouseStartTime1 = "";
    private String openHouseEndTime1 = "";
    private Date openHouseDate2 = null;
    private String openHouseStartTime2 = "";
    private String openHouseEndTime2 = "";
    private String mlsNumber = "";
    private String squareFeet = "";
    private String airCondition = "";
    private String basement = "";
    private String dishwasher = "";
    private String elevator = "";
    private String fireplace = "";
    private String fitnessRoom = "";
    private String diningRoom = "";
    private String familyRoom = "";
    private String laundry = "";
    private String otherParking = "";
    private String garageParking = "";
    private String pets = "";
    private String pool = "";
    private String security = "";
    private String webID = "";
    private String agentUrl = "";

    public static HashMap nullFieldsMap = new HashMap();

    public CCI_REAd() {

        nullFieldsMap.put("ADID", new Boolean(false));
        nullFieldsMap.put("CLASSID", new Boolean(false));
        nullFieldsMap.put("CUSTOMERCITY", new Boolean(false));
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
        nullFieldsMap.put("AGENTURL", new Boolean(true));
        nullFieldsMap.put("AIRCONDITION", new Boolean(true));
        nullFieldsMap.put("BASEMENT", new Boolean(true));
        nullFieldsMap.put("BATHS", new Boolean(true));
        nullFieldsMap.put("BEDROOMS", new Boolean(true));
        nullFieldsMap.put("CONTACTEMAIL", new Boolean(true));
        nullFieldsMap.put("CONTACTPHONE", new Boolean(true));
        nullFieldsMap.put("COUNTRY", new Boolean(true));
        nullFieldsMap.put("CUSTOMEREMAIL", new Boolean(true));
        nullFieldsMap.put("DATERECEIVED", new Boolean(true));
        nullFieldsMap.put("DATEUPDATED", new Boolean(true));
        nullFieldsMap.put("DININGROOM", new Boolean(true));
        nullFieldsMap.put("DISHWASHER", new Boolean(true));
        nullFieldsMap.put("ELEVATOR", new Boolean(true));
        nullFieldsMap.put("ENHANCEDDESC", new Boolean(true));
        nullFieldsMap.put("ENHANCEEMAIL", new Boolean(true));
        nullFieldsMap.put("FAMILYROOM", new Boolean(true));
        nullFieldsMap.put("FIREPLACE", new Boolean(true));
        nullFieldsMap.put("FIRSTRECEIVED", new Boolean(true));
        nullFieldsMap.put("FITNESSROOM", new Boolean(true));
        nullFieldsMap.put("GARAGEPARKING", new Boolean(true));
        nullFieldsMap.put("LAUNDRY", new Boolean(true));
        nullFieldsMap.put("MLSNUMBER", new Boolean(true));
        nullFieldsMap.put("NEIGHBORHOOD", new Boolean(true));
        nullFieldsMap.put("OPENHOUSEDATE1", new Boolean(true));
        nullFieldsMap.put("OPENHOUSEDATE2", new Boolean(true));
        nullFieldsMap.put("OPENHOUSEENDTIME1", new Boolean(true));
        nullFieldsMap.put("OPENHOUSEENDTIME2", new Boolean(true));
        nullFieldsMap.put("OPENHOUSESTARTTIME1", new Boolean(true));
        nullFieldsMap.put("OPENHOUSESTARTTIME2", new Boolean(true));
        nullFieldsMap.put("OTHERPARKING", new Boolean(true));
        nullFieldsMap.put("PETS", new Boolean(true));
        nullFieldsMap.put("PHOTOREF", new Boolean(true));
        nullFieldsMap.put("POOL", new Boolean(true));
        nullFieldsMap.put("PRICE", new Boolean(true));
        nullFieldsMap.put("PRICEPERSQFT", new Boolean(true));
        nullFieldsMap.put("PROPERTYADDRESS", new Boolean(true));
        nullFieldsMap.put("PROPERTYCITY", new Boolean(true));
        nullFieldsMap.put("PROPERTYSTATE", new Boolean(true));
        nullFieldsMap.put("PROPERTYTYPE", new Boolean(true));
        nullFieldsMap.put("PROPERTYZIP", new Boolean(true));
        nullFieldsMap.put("SALELEASE", new Boolean(true));
        nullFieldsMap.put("SECURITY", new Boolean(true));
        nullFieldsMap.put("SQUAREFEET", new Boolean(true));
        nullFieldsMap.put("WEBID", new Boolean(true));
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
     * Getter/Setter Methods for Apartment specific ads
     */
    public String getSaleLease() {
        return saleLease;
    }

    public void setSaleLease(String saleLease) {
        this.saleLease = saleLease;
    }

    public String getPricePerSqFt() {
        return pricePerSqFt;
    }

    public void setPricePerSqFt(String pricePerSqFt) {
        this.pricePerSqFt = pricePerSqFt;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyCity() {
        return propertyCity;
    }

    public void setPropertyCity(String propertyCity) {
        this.propertyCity = propertyCity;
    }

    public String getPropertyState() {
        return propertyState;
    }

    public void setPropertyState(String propertyState) {
        this.propertyState = propertyState;
    }

    public String getPropertyZip() {
        return propertyZip;
    }

    public void setPropertyZip(String propertyZip) {
        this.propertyZip = propertyZip;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBaths() {
        return baths;
    }

    public void setBaths(String baths) {
        this.baths = baths;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getOpenHouseDate1() {
        return openHouseDate1;
    }

    public void setOpenHouseDate1(Date openHouseDate1) {
        this.openHouseDate1 = openHouseDate1;
    }

    public String getOpenHouseStartTime1() {
        return openHouseStartTime1;
    }

    public void setOpenHouseStartTime1(String openHouseStartTime1) {
        this.openHouseStartTime1 = openHouseStartTime1;
    }

    public String getOpenHouseEndTime1() {
        return openHouseEndTime1;
    }

    public void setOpenHouseEndTime1(String openHouseEndTime1) {
        this.openHouseEndTime1 = openHouseEndTime1;
    }

    public Date getOpenHouseDate2() {
        return openHouseDate2;
    }

    public void setOpenHouseDate2(Date openHouseDate2) {
        this.openHouseDate2 = openHouseDate2;
    }

    public String getOpenHouseStartTime2() {
        return openHouseStartTime2;
    }

    public void setOpenHouseStartTime2(String openHouseStartTime2) {
        this.openHouseStartTime2 = openHouseStartTime2;
    }

    public String getOpenHouseEndTime2() {
        return openHouseEndTime2;
    }

    public void setOpenHouseEndTime2(String openHouseEndTime2) {
        this.openHouseEndTime2 = openHouseEndTime2;
    }

    public String getMlsNumber() {
        return mlsNumber;
    }

    public void setMlsNumber(String mlsNumber) {
        this.mlsNumber = mlsNumber;
    }

    public String getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(String squareFeet) {
        this.squareFeet = squareFeet;
    }

    public String getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(String airCondition) {
        this.airCondition = airCondition;
    }

    public String getBasement() {
        return basement;
    }

    public void setBasement(String basement) {
        this.basement = basement;
    }

    public String getDishwasher() {
        return dishwasher;
    }

    public void setDishwasher(String dishwasher) {
        this.dishwasher = dishwasher;
    }

    public String getElevator() {
        return elevator;
    }

    public void setElevator(String elevator) {
        this.elevator = elevator;
    }

    public String getFireplace() {
        return fireplace;
    }

    public void setFireplace(String fireplace) {
        this.fireplace = fireplace;
    }

    public String getFitnessRoom() {
        return fitnessRoom;
    }

    public void setFitnessRoom(String fitnessRoom) {
        this.fitnessRoom = fitnessRoom;
    }

    public String getDiningRoom() {
        return diningRoom;
    }

    public void setDiningRoom(String diningRoom) {
        this.diningRoom = diningRoom;
    }

    public String getFamilyRoom() {
        return familyRoom;
    }

    public void setFamilyRoom(String familyRoom) {
        this.familyRoom = familyRoom;
    }

    public String getLaundry() {
        return laundry;
    }

    public void setLaundry(String laundry) {
        this.laundry = laundry;
    }

    public String getOtherParking() {
        return otherParking;
    }

    public void setOtherParking(String otherParking) {
        this.otherParking = otherParking;
    }

    public String getGarageParking() {
        return garageParking;
    }

    public void setGarageParking(String garageParking) {
        this.garageParking = garageParking;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getWebID() {
        return webID;
    }

    public void setWebID(String webID) {
        this.webID = webID;
    }

    public String getAgentUrl() {
        return agentUrl;
    }

    public void setAgentUrl(String agentUrl) {
        this.agentUrl = agentUrl;
    }
}
