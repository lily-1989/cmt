package trb.tii.efe.tools;

import java.util.Date;
import java.util.HashMap;

/**
 * CCI_JobAd object extends CCI_Ad.  This object stores and can retrieve all DB related values for
 * CCI Job ads.
 */
public class CCI_JobAd extends CCI_Ad {

    private String jobTitle = "";
    private String jobTypeCode = "";
    private String jobLocationZip = "";
    private String workType = "";
    private String companyName = "";
    private String contactName = "";
    private String contactFax = "";
    private String jobLocationCity = "";
    private String jobLocationState = "";
    private String jobLocationCountry = "";
    private String education = "";
    private String experience = "";
    private String travel = "";
    private String basePayPer = "";
    private String basePayLow = "";
    private String basePayHigh = "";
    private String commission = "";
    private String bonus = "";
    private String otherPay = "";
    private String companyUrl = "";
    private String applyUrl = "";
    private String webID = "";
    private String industryCode1 = "";
    private String industryCode2 = "";
    private String industryCode3 = "";
    private String originalAdID = "";
    private String voiceType = "";
    private String companyVideoProvided = "";
    private String moreDetailsURL = "";
    private String videoNotes = "";
    private String videoSearchKeywords = "";


    public static HashMap nullFieldsMap = new HashMap();

    public CCI_JobAd() {

        nullFieldsMap.put("ADID", new Boolean(false));
        nullFieldsMap.put("CLASSID", new Boolean(false));
        nullFieldsMap.put("CUSTOMERCITY", new Boolean(false));
        nullFieldsMap.put("CUSTOMERSTATE", new Boolean(false));
        nullFieldsMap.put("CUSTOMERZIP", new Boolean(false));
        nullFieldsMap.put("FEEDID", new Boolean(false));
        nullFieldsMap.put("INDUSTRYCODE1", new Boolean(false));
        nullFieldsMap.put("INSERTIONID", new Boolean(true));
        nullFieldsMap.put("JOBLOCATIONZIP", new Boolean(false));
        nullFieldsMap.put("JOBTITLE", new Boolean(false));
        nullFieldsMap.put("JOBTYPECODE", new Boolean(false));
        nullFieldsMap.put("PHOTOCOUNT", new Boolean(true));
        nullFieldsMap.put("PRINTDESC", new Boolean(false));
        nullFieldsMap.put("PUBLICATION", new Boolean(false));
        nullFieldsMap.put("STARTDATE", new Boolean(false));
        nullFieldsMap.put("STOPDATE", new Boolean(false));
        nullFieldsMap.put("TITLE", new Boolean(false));
        nullFieldsMap.put("UPSELL", new Boolean(false));
        nullFieldsMap.put("COMMERCIALRELEASE", new Boolean(false));
        nullFieldsMap.put("APPLYURL", new Boolean(true));
        nullFieldsMap.put("BASEPAYHIGH", new Boolean(true));
        nullFieldsMap.put("BASEPAYLOW", new Boolean(true));
        nullFieldsMap.put("BASEPAYPER", new Boolean(true));
        nullFieldsMap.put("BONUS", new Boolean(true));
        nullFieldsMap.put("COMMISSION", new Boolean(true));
        nullFieldsMap.put("COMPANYNAME", new Boolean(true));
        nullFieldsMap.put("COMPANYURL", new Boolean(true));
        nullFieldsMap.put("CONTACTEMAIL", new Boolean(true));
        nullFieldsMap.put("CONTACTFAX", new Boolean(true));
        nullFieldsMap.put("CONTACTNAME", new Boolean(true));
        nullFieldsMap.put("CONTACTPHONE", new Boolean(true));
        nullFieldsMap.put("CUSTEMAIL", new Boolean(true));
        nullFieldsMap.put("DATERECEIVED", new Boolean(true));
        nullFieldsMap.put("DATEUPDATED", new Boolean(true));
        nullFieldsMap.put("EDUCATION", new Boolean(true));
        nullFieldsMap.put("ENHANCEDDESC", new Boolean(true));
        nullFieldsMap.put("ENHANCEEMAIL", new Boolean(true));
        nullFieldsMap.put("EXPERIENCE", new Boolean(true));
        nullFieldsMap.put("FIRSTRECEIVED", new Boolean(true));
        nullFieldsMap.put("INDUSTRYCODE2", new Boolean(true));
        nullFieldsMap.put("INDUSTRYCODE3", new Boolean(true));
        nullFieldsMap.put("JOBLOCATIONCITY", new Boolean(true));
        nullFieldsMap.put("JOBLOCATIONCOUNTRY", new Boolean(true));
        nullFieldsMap.put("JOBLOCATIONSTATE", new Boolean(true));
        nullFieldsMap.put("OTHERPAY", new Boolean(true));
        nullFieldsMap.put("PHOTOREF", new Boolean(true));
        nullFieldsMap.put("TRAVEL", new Boolean(true));
        nullFieldsMap.put("WEBID", new Boolean(true));
        nullFieldsMap.put("WORKTYPE", new Boolean(true));
        nullFieldsMap.put("CANCELED", new Boolean(true));
        nullFieldsMap.put("ORIGINAL_AD_ID", new Boolean(true));
        nullFieldsMap.put("VOICE_TYPE", new Boolean(true));
        nullFieldsMap.put("COMPANY_VIDEO_PROVIDED", new Boolean(true));
        nullFieldsMap.put("MOREDETAILS_URL", new Boolean(true));
        nullFieldsMap.put("VIDEO_NOTES", new Boolean(true));
        nullFieldsMap.put("VIDEO_SEARCH_KEYWORDS", new Boolean(true));
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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTypeCode() {
        return jobTypeCode;
    }

    public void setJobTypeCode(String jobTypeCode) {
        this.jobTypeCode = jobTypeCode;
    }

    public String getJobLocationZip() {
        return jobLocationZip;
    }

    public void setJobLocationZip(String jobLocationZip) {
        this.jobLocationZip = jobLocationZip;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactFax() {
        return contactFax;
    }

    public void setContactFax(String contactFax) {
        this.contactFax = contactFax;
    }

    public String getJobLocationCity() {
        return jobLocationCity;
    }

    public void setJobLocationCity(String jobLocationCity) {
        this.jobLocationCity = jobLocationCity;
    }

    public String getJobLocationState() {
        return jobLocationState;
    }

    public void setJobLocationState(String jobLocationState) {
        this.jobLocationState = jobLocationState;
    }

    public String getJobLocationCountry() {
        return jobLocationCountry;
    }

    public void setJobLocationCountry(String jobLocationCountry) {
        this.jobLocationCountry = jobLocationCountry;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getBasePayPer() {
        return basePayPer;
    }

    public void setBasePayPer(String basePayPer) {
        this.basePayPer = basePayPer;
    }

    public String getBasePayLow() {
        return basePayLow;
    }

    public void setBasePayLow(String basePayLow) {
        this.basePayLow = basePayLow;
    }

    public String getBasePayHigh() {
        return basePayHigh;
    }

    public void setBasePayHigh(String basePayHigh) {
        this.basePayHigh = basePayHigh;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getOtherPay() {
        return otherPay;
    }

    public void setOtherPay(String otherPay) {
        this.otherPay = otherPay;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }

    public String getWebID() {
        return webID;
    }

    public void setWebID(String webID) {
        this.webID = webID;
    }

    public String getIndustryCode1() {
        return industryCode1;
    }

    public void setIndustryCode1(String industryCode1) {
        this.industryCode1 = industryCode1;
    }

    public String getIndustryCode2() {
        return industryCode2;
    }

    public void setIndustryCode2(String industryCode2) {
        this.industryCode2 = industryCode2;
    }

    public String getIndustryCode3() {
        return industryCode3;
    }

    public void setIndustryCode3(String industryCode3) {
        this.industryCode3 = industryCode3;
    }

    public String getOriginalAdID() {
        return originalAdID;
    }

    public void setOriginalAdID(String originalAdID) {
        this.originalAdID = originalAdID;
    }

    public String getVoiceType() {
        return voiceType;
    }

    public void setVoiceType(String voiceType) {
        this.voiceType = voiceType;
    }

    public String getCompanyVideoProvided() {
        return companyVideoProvided;
    }

    public void setCompanyVideoProvided(String companyVideoProvided) {
        this.companyVideoProvided = companyVideoProvided;
    }

    public String getMoreDetailsURL() {
        return moreDetailsURL;
    }

    public void setMoreDetailsURL(String moreDetailsURL) {
        this.moreDetailsURL = moreDetailsURL;
    }

    public String getVideoNotes() {
        return videoNotes;
    }

    public void setVideoNotes(String videoNotes) {
        this.videoNotes = videoNotes;
    }

    public String getVideoSearchKeywords() {
        return videoSearchKeywords;
    }

    public void setVideoSearchKeywords(String videoSearchKeywords) {
        this.videoSearchKeywords = videoSearchKeywords;
    }
}
