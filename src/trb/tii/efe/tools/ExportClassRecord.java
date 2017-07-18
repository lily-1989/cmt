package trb.tii.efe.tools;

/**
 * Created by IntelliJ IDEA.
 * User: mreule
 * Date: May 15, 2006
 * Time: 1:37:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExportClassRecord {

    // Table FeedConfig
    private int feedID = 0;
    private String market = "";
    private String feedName = "";

    // Table Export_Class_Lookup
    private String vendorID = "";
    private String classID = "";
    private String cciCategory = "";
    private String cciDescription = "";
    private String recyclerCategory = "";

    //Table Export_Vendors
    private String vendorName = "";

    public String getOldVendorID() {
        return oldVendorID;
    }

    public void setOldVendorID(String oldVendorID) {
        this.oldVendorID = oldVendorID;
    }

    // Old Class ID for update
    private String oldVendorID = "";
    private String oldClassID = "";

    public int getFeedID() {
        return feedID;
    }

    public void setFeedID(int feedID) {
        this.feedID = feedID;
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

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getCciCategory() {
        return cciCategory;
    }

    public void setCciCategory(String cciCategory) {
        this.cciCategory = cciCategory;
    }

    public String getCciDescription() {
        return cciDescription;
    }

    public void setCciDescription(String cciDescription) {
        this.cciDescription = cciDescription;
    }

    public String getRecyclerCategory() {
        return recyclerCategory;
    }

    public void setRecyclerCategory(String recyclerCategory) {
        this.recyclerCategory = recyclerCategory;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getOldClassID() {
        return oldClassID;
    }

    public void setOldClassID(String oldClassID) {
        this.oldClassID = oldClassID;
    }


}
