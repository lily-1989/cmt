package trb.tii.efe.tools;

import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: ppayne
 * Date: May 5, 2006
 * Time: 10:02:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class CCI_Ad {
    protected Integer feedID = new Integer(0);
    protected String adID = "";
    protected Date dateReceived = null;
    protected Date firstReceived = null;
    protected Date dateUpdated = null;
    protected String publication = "";
    protected String commercialRelease = "";
    protected Integer canceled = null;
    protected String customerCity = "";
    protected String customerState = "";
    protected String customerZip = "";
    protected String customerEmail = "";
    protected String classID = "";
    protected Date startDate = null;
    protected Date stopDate = null;
    protected String title = "";
    protected String upsell = "||";
    protected String contactEmail = "";
    protected String enhanceEmail = "";
    protected Integer photoCount = new Integer(0);
    protected String photoRef = "";
    protected String logoRef = "";
    protected String printAdImage = "";
    protected String contactPhone = "";
    protected String printDesc = "";
    protected String enhancedDesc = "";
    protected String insertionID = "";
    protected String vertical = "";
    protected String tableName = "";
    protected String market = "";
    protected String feedName = "";
    // Recycler
    protected String content = "";
    protected String extraText = "";

    private SimpleDateFormat longSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
    private SimpleDateFormat shortSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private String dataResponseMessage = "";

    private static HashMap tableMap = new HashMap();

    public CCI_Ad() {
      tableMap.put(Constants.CCI_APARTMENTS_TABLE_NAME, CCI_AptAd.nullFieldsMap);
      tableMap.put(Constants.CCI_GENCLASSIFIED_TABLE_NAME, CCI_GenAd.nullFieldsMap);
      tableMap.put(Constants.CCI_JOBS_TABLE_NAME, CCI_JobAd.nullFieldsMap);
      tableMap.put(Constants.CCI_REALESTATE_TABLE_NAME, CCI_REAd.nullFieldsMap);
      tableMap.put(Constants.CCI_TRANSPORT_TABLE_NAME, CCI_TransAd.nullFieldsMap);
      tableMap.put(Constants.CCI_VITALNOTICE_TABLE_NAME, CCI_VitalAd.nullFieldsMap);
      tableMap.put(Constants.RECYCLER_TABLE_NAME, RecyclerAd.nullFieldsMap);
    }


    public String getTableName() {
      return tableName;
    }

    public void setTableName(String tableName) {
      this.tableName = tableName;
    }

    public String getMarket() {
      return market;
    }

    public void setMarket(String market) {
      this.market = market;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getVertical() {
      return vertical;
    }

    public void setVertical(String vertical) {
      this.vertical = vertical;
    }

    public Integer getFeedID() {
        return feedID;
    }

    public void setFeedID(Integer feedID) {
        this.feedID = feedID;
    }

    public String getAdID() {
        return adID;
    }

    public void setAdID(String adID) {
        this.adID = adID;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Date getFirstReceived() {
        return firstReceived;
    }

    public void setFirstReceived(Date firstReceived) {
        this.firstReceived = firstReceived;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getCommercialRelease() {
        return commercialRelease;
    }

    public void setCommercialRelease(String commercialRelease) {
        this.commercialRelease = commercialRelease;
    }

    public Integer getCanceled() {
        return canceled;
    }

    public void setCanceled(Integer canceled) {
        this.canceled = canceled;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerZip() {
        return customerZip;
    }

    public void setCustomerZip(String customerZip) {
        this.customerZip = customerZip;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpsell() {
        return upsell;
    }

    public void setUpsell(String upsell) {
        this.upsell = upsell;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getEnhanceEmail() {
        return enhanceEmail;
    }

    public void setEnhanceEmail(String enhanceEmail) {
        this.enhanceEmail = enhanceEmail;
    }

    public Integer getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(Integer photoCount) {
        this.photoCount = photoCount;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    public String getLogoRef() {
        return logoRef;
    }

    public void setLogoRef(String logoRef) {
        this.logoRef = logoRef;
    }

    public String getPrintAdImage() {
        return printAdImage;
    }

    public void setPrintAdImage(String printAdImage) {
        this.printAdImage = printAdImage;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPrintDesc() {
        return printDesc;
    }

    public void setPrintDesc(String printDesc) {
        this.printDesc = printDesc;
    }

    public String getEnhancedDesc() {
        return enhancedDesc;
    }

    public void setEnhancedDesc(String enhancedDesc) {
        this.enhancedDesc = enhancedDesc;
    }

    public String getInsertionID() {
        return insertionID;
    }

    public void setInsertionID(String insertionID) {
        this.insertionID = insertionID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtraText() {
        return extraText;
    }

    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }

    public String getDateAsLongString(Date date) {
      return longSimpleDateFormat.format(date);
    }

    public Date getLongDateFromString(String dateString) throws ParseException {
      return longSimpleDateFormat.parse(dateString);
    }

    public String getDateAsShortString(Date date) {
      return date == null ? "" : shortSimpleDateFormat.format(date);
    }

    public Date getShortDateFromString(String dateString) throws ParseException {
        if (!dateString.matches("(19|20)\\d\\d[/](0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])")) {
           throw new IllegalArgumentException();
        }

        return shortSimpleDateFormat.parse(dateString);
    }

    public boolean isFieldAllowedNull(String tableName, String fieldName) {
      boolean isAllowedNull = true;
      try {
        HashMap nullFieldsMap = ((HashMap)tableMap.get(tableName));
        isAllowedNull = ((Boolean)nullFieldsMap.get(fieldName)).booleanValue();
      }
      catch (Exception e) {}

      return isAllowedNull;
    }

    public String getDataResponseMessage() {
      return dataResponseMessage;
    }

    public void setDataResponseMessage(String dataResponseMessage) {
      this.dataResponseMessage = dataResponseMessage;
    }
}
