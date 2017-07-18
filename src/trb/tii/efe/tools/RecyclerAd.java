package trb.tii.efe.tools;

import java.util.Date;
import java.util.HashMap;


public class RecyclerAd extends CCI_Ad {
// these are the only fields that CCI_Ad parent class will handle for this class...
//  private String adID = "";
//  private Integer feedID;
//  private Date startDate;
//  private String title = "";
//  private Date dateReceived;
//  private Date dateUpdated;
//  private String publication = "";
//  private String content = "";
//  private String extraText = "";

    private Date endDate;
    private Integer customerID;
    private Double cost;
    private Integer isOnline = null;
    private String phone = "";
    private String location = "";
    private String email = "";
    private String url = "";
    private Double price;
    private Integer year;
    private String vin = "";
    private Integer wanted;
    private String zip = "";
    private String sortText = "";
    private String photoName = "";
    private String photoName1 = "";
    private String photoName2 = "";
    private String photoName3 = "";
    private String photoName4 = "";
    private String photoName5 = "";
    private String areaCode = "";
    private String parentClass = "";
    private Integer type = null;
    private String contactName = "";
    private String make = "";
    private String model = "";
    private Integer insertions;
    private String webPhone1 = "";
    private String webPhone2 = "";
    private String zones = "";
    private Integer duration = null;
    private String trim = "";
    private String mileage = "";
    private String adFormat = "";
    private String adLayout = "";

    public static HashMap nullFieldsMap = new HashMap();

    public RecyclerAd() {

        nullFieldsMap.put("ADID", new Boolean(false));
        nullFieldsMap.put("FEEDID", new Boolean(false));
        nullFieldsMap.put("STARTDATE", new Boolean(false));
        nullFieldsMap.put("DATERECEIVED", new Boolean(false));
        nullFieldsMap.put("DATEUPDATED", new Boolean(true));
        nullFieldsMap.put("PUBLICATION", new Boolean(false));
        nullFieldsMap.put("CLASS", new Boolean(false));
        nullFieldsMap.put("CUSTOMERID", new Boolean(false));
        nullFieldsMap.put("WANTED", new Boolean(false));
        nullFieldsMap.put("TITLE", new Boolean(true));
        nullFieldsMap.put("ENDDATE", new Boolean(true));
        nullFieldsMap.put("RUNDATE", new Boolean(true));
        nullFieldsMap.put("COST", new Boolean(true));
        nullFieldsMap.put("ISONLINE", new Boolean(true));
        nullFieldsMap.put("PHONE", new Boolean(true));
        nullFieldsMap.put("EMAIL", new Boolean(true));
        nullFieldsMap.put("URL", new Boolean(true));
        nullFieldsMap.put("PRICE", new Boolean(true));
        nullFieldsMap.put("YEAR", new Boolean(true));
        nullFieldsMap.put("VIN", new Boolean(true));
        nullFieldsMap.put("ZIP", new Boolean(true));
        nullFieldsMap.put("SORTTEXT", new Boolean(true));
        nullFieldsMap.put("PHOTONAME", new Boolean(true));
        nullFieldsMap.put("PHOTONAME1", new Boolean(true));
        nullFieldsMap.put("PHOTONAME2", new Boolean(true));
        nullFieldsMap.put("PHOTONAME3", new Boolean(true));
        nullFieldsMap.put("PHOTONAME4", new Boolean(true));
        nullFieldsMap.put("PHOTONAME5", new Boolean(true));
        nullFieldsMap.put("CONTENT", new Boolean(true));
        nullFieldsMap.put("EXTRATEXT", new Boolean(true));
        nullFieldsMap.put("AREACODE", new Boolean(true));
        nullFieldsMap.put("PARENTCLASS", new Boolean(true));
        nullFieldsMap.put("TYPE", new Boolean(true));
        nullFieldsMap.put("CONTACTNAME", new Boolean(true));
        nullFieldsMap.put("MAKE", new Boolean(true));
        nullFieldsMap.put("MODEL", new Boolean(true));
        nullFieldsMap.put("INSERTIONS", new Boolean(true));
        nullFieldsMap.put("WEBPHONE1", new Boolean(true));
        nullFieldsMap.put("WEBPHONE2", new Boolean(true));
        nullFieldsMap.put("ZONES", new Boolean(true));
        nullFieldsMap.put("DURATION", new Boolean(true));
        nullFieldsMap.put("TRIM", new Boolean(true));
        nullFieldsMap.put("MILEAGE", new Boolean(true));
        nullFieldsMap.put("ADFORMAT", new Boolean(true));
        nullFieldsMap.put("ADLAYOUT", new Boolean(true));

    }

    public boolean isFieldAllowedNull(String fieldName) {
        boolean isAllowedNull = true;
        try {
            isAllowedNull = ((Boolean) nullFieldsMap.get(fieldName)).booleanValue();
        } catch (Exception e) {
        }

        return isAllowedNull;
    }


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getWanted() {
        return wanted;
    }

    public void setWanted(Integer wanted) {
        this.wanted = wanted;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSortText() {
        return sortText;
    }

    public void setSortText(String sortText) {
        this.sortText = sortText;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoName1() {
        return photoName1;
    }

    public void setPhotoName1(String photoName1) {
        this.photoName1 = photoName1;
    }

    public String getPhotoName2() {
        return photoName2;
    }

    public void setPhotoName2(String photoName2) {
        this.photoName2 = photoName2;
    }

    public String getPhotoName3() {
        return photoName3;
    }

    public void setPhotoName3(String photoName3) {
        this.photoName3 = photoName3;
    }

    public String getPhotoName4() {
        return photoName4;
    }

    public void setPhotoName4(String photoName4) {
        this.photoName4 = photoName4;
    }

    public String getPhotoName5() {
        return photoName5;
    }

    public void setPhotoName5(String photoName5) {
        this.photoName5 = photoName5;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getParentClass() {
        return parentClass;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getInsertions() {
        return insertions;
    }

    public void setInsertions(Integer insertions) {
        this.insertions = insertions;
    }

    public String getWebPhone1() {
        return webPhone1;
    }

    public void setWebPhone1(String webPhone1) {
        this.webPhone1 = webPhone1;
    }

    public String getWebPhone2() {
        return webPhone2;
    }

    public void setWebPhone2(String webPhone2) {
        this.webPhone2 = webPhone2;
    }

    public String getZones() {
        return zones;
    }

    public void setZones(String zones) {
        this.zones = zones;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getAdFormat() {
        return adFormat;
    }

    public void setAdFormat(String adFormat) {
        this.adFormat = adFormat;
    }

    public String getAdLayout() {
        return adLayout;
    }

    public void setAdLayout(String adLayout) {
        this.adLayout = adLayout;
    }

}