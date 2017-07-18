package trb.tii.efe.tools;

/**
 * Created by IntelliJ IDEA.
 * User: mreule
 * Date: May 25, 2006
 * Time: 2:50:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class MarketRecord {

    // Table FeedConfig
    protected String feedID = "";
    private String market = "";
    private String feedName = "";

    // Table Publication
    private String pubName = "";


    public String getFeedID() {
        return feedID;
    }

    public void setFeedID(String feedID) {
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

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }
}
