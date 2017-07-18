package trb.tii.efe.tools;

import java.util.HashMap;


/**
 * CCI_TransAd object extends CCI_Ad.  This object stores and can retrieve all DB related values for
 * CCI Trasport Classified Ads.
 */
public class CCI_TransAd extends CCI_Ad {

    private String type = "";
    private String itemZip = "";
    private String length = "";
    private String make = "";
    private String model = "";
    private String year = "";
    private String mileage = "";
    private String price = "";
    private String bodyStyle = "";
    private String exteriorColor = "";
    private String vehicleZip = "";
    private String interiorColor = "";
    private String condition = "";
    private String transmission = "";
    private String trim = "";
    private String engine = "";
    private String contactName = "";
    private Integer doors = null;
    private Integer oBO = null;
    private String driveTrain = "";
    private Integer dealerID = null;
    private String stockNumber = "";
    private String vin = "";
    private String licensePlateNumber = "";
    private String webID = "";
    private String currency = "";
    private Integer acFront = null;
    private Integer acRear = null;
    private Integer cruiseControl  = null;
    private Integer navigation  = null;
    private Integer powerLocks  = null;
    private Integer powerSteering = null;
    private Integer keylessEntry = null;
    private Integer tvVcrDvd = null;
    private Integer bucketSeats = null;
    private Integer leatherInterior = null;
    private Integer memorySeats = null;
    private Integer powerSeats = null;
    private Integer driverAirbag = null;
    private Integer passengerAirbag = null;
    private Integer sideAirbag = null;
    private Integer alarm = null;
    private Integer antiLockBrakes = null;
    private Integer foglights = null;
    private Integer cassettePlayer = null;
    private Integer cdChanger = null;
    private Integer cdPlayer = null;
    private Integer premiumSound = null;
    private Integer powerWindows = null;
    private Integer rearWinDefogger = null;
    private Integer rearWinWiper = null;
    private Integer tintedGlass = null;
    private Integer alloyWheels = null;
    private Integer sunroof = null;
    private Integer moonroof = null;
    private Integer thirdRowSeats = null;
    private Integer towPackage = null;

    public static HashMap nullFieldsMap = new HashMap();

    public CCI_TransAd() {

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
        nullFieldsMap.put("BODYSTYLE", new Boolean(true));
        nullFieldsMap.put("CONDITION", new Boolean(true));
        nullFieldsMap.put("CONTACTEMAIL", new Boolean(true));
        nullFieldsMap.put("CONTACTNAME", new Boolean(true));
        nullFieldsMap.put("CONTACTPHONE", new Boolean(true));
        nullFieldsMap.put("CUSTEMAIL", new Boolean(true));
        nullFieldsMap.put("DATERECEIVED", new Boolean(true));
        nullFieldsMap.put("DATEUPDATED", new Boolean(true));
        nullFieldsMap.put("DEALERID", new Boolean(true));
        nullFieldsMap.put("DOORS", new Boolean(true));
        nullFieldsMap.put("DRIVETRAIN", new Boolean(true));
        nullFieldsMap.put("ENGINE", new Boolean(true));
        nullFieldsMap.put("ENHANCEDDESC", new Boolean(true));
        nullFieldsMap.put("ENHANCEEMAIL", new Boolean(true));
        nullFieldsMap.put("EXTERIORCOLOR", new Boolean(true));
        nullFieldsMap.put("FIRSTRECEIVED", new Boolean(true));
        nullFieldsMap.put("INTERIORCOLOR", new Boolean(true));
        nullFieldsMap.put("ITEMZIP", new Boolean(true));
        nullFieldsMap.put("LENGTH", new Boolean(true));
        nullFieldsMap.put("LICENSEPLATENUMBER", new Boolean(true));
        nullFieldsMap.put("MAKE", new Boolean(true));
        nullFieldsMap.put("MILEAGE", new Boolean(true));
        nullFieldsMap.put("MODEL", new Boolean(true));
        nullFieldsMap.put("OBO", new Boolean(true));
        nullFieldsMap.put("PHOTOREF", new Boolean(true));
        nullFieldsMap.put("PRICE", new Boolean(true));
        nullFieldsMap.put("STOCKNUMBER", new Boolean(true));
        nullFieldsMap.put("TRANSMISSION", new Boolean(true));
        nullFieldsMap.put("TRIM", new Boolean(true));
        nullFieldsMap.put("TYPE", new Boolean(true));
        nullFieldsMap.put("VEHICLEZIP", new Boolean(true));
        nullFieldsMap.put("VIN", new Boolean(true));
        nullFieldsMap.put("WEBID", new Boolean(true));
        nullFieldsMap.put("YEAR", new Boolean(true));
        nullFieldsMap.put("CURRENCY", new Boolean(true));
        nullFieldsMap.put("AC_FRONT", new Boolean(true));
        nullFieldsMap.put("AC_REAR", new Boolean(true));
        nullFieldsMap.put("CRUISE_CONTROL", new Boolean(true));
        nullFieldsMap.put("NAVIGATION", new Boolean(true));
        nullFieldsMap.put("POWER_LOCKS", new Boolean(true));
        nullFieldsMap.put("POWER_STEERING", new Boolean(true));
        nullFieldsMap.put("KEYLESS_ENTRY", new Boolean(true));
        nullFieldsMap.put("TV_VCR_DVD", new Boolean(true));
        nullFieldsMap.put("BUCKET_SEATS", new Boolean(true));
        nullFieldsMap.put("LEATHER_INTERIOR", new Boolean(true));
        nullFieldsMap.put("MEMORY_SEATS", new Boolean(true));
        nullFieldsMap.put("POWER_SEATS", new Boolean(true));
        nullFieldsMap.put("DRIVER_AIRBAG", new Boolean(true));
        nullFieldsMap.put("PASSENGER_AIRBAG", new Boolean(true));
        nullFieldsMap.put("SIDE_AIRBAG", new Boolean(true));
        nullFieldsMap.put("ALARM", new Boolean(true));
        nullFieldsMap.put("ANTI_LOCK_BRAKES", new Boolean(true));
        nullFieldsMap.put("FOGLIGHTS", new Boolean(true));
        nullFieldsMap.put("CASSETTE_PLAYER", new Boolean(true));
        nullFieldsMap.put("CD_CHANGER", new Boolean(true));
        nullFieldsMap.put("CD_PLAYER", new Boolean(true));
        nullFieldsMap.put("PREMIUM_SOUND", new Boolean(true));
        nullFieldsMap.put("POWER_WINDOWS", new Boolean(true));
        nullFieldsMap.put("REAR_WIN_DEFOGGER", new Boolean(true));
        nullFieldsMap.put("REAR_WIN_WIPER", new Boolean(true));
        nullFieldsMap.put("TINTED_GLASS", new Boolean(true));
        nullFieldsMap.put("ALLOY_WHEELS", new Boolean(true));
        nullFieldsMap.put("SUNROOF", new Boolean(true));
        nullFieldsMap.put("MOONROOF", new Boolean(true));
        nullFieldsMap.put("THIRD_ROW_SEATS", new Boolean(true));
        nullFieldsMap.put("TOW_PACKAGE", new Boolean(true));
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemZip() {
        return itemZip;
    }

    public void setItemZip(String itemZip) {
        this.itemZip = itemZip;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public String getExteriorColor() {
        return exteriorColor;
    }

    public void setExteriorColor(String exteriorColor) {
        this.exteriorColor = exteriorColor;
    }

    public String getVehicleZip() {
        return vehicleZip;
    }

    public void setVehicleZip(String vehicleZip) {
        this.vehicleZip = vehicleZip;
    }

    public String getInteriorColor() {
        return interiorColor;
    }

    public void setInteriorColor(String interiorColor) {
        this.interiorColor = interiorColor;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public Integer getoBO() {
        return oBO;
    }

    public void setoBO(Integer oBO) {
        this.oBO = oBO;
    }

    public String getDriveTrain() {
        return driveTrain;
    }

    public void setDriveTrain(String driveTrain) {
        this.driveTrain = driveTrain;
    }

    public Integer getDealerID() {
        return dealerID;
    }

    public void setDealerID(Integer dealerID) {
        this.dealerID = dealerID;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getWebID() {
        return webID;
    }

    public void setWebID(String webID) {
        this.webID = webID;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAcFront() {
        return acFront;
    }

    public void setAcFront(Integer acFront) {
        this.acFront = acFront;
    }

    public Integer getAcRear() {
        return acRear;
    }

    public void setAcRear(Integer acRear) {
        this.acRear = acRear;
    }

    public Integer getCruiseControl() {
        return cruiseControl;
    }

    public void setCruiseControl(Integer cruiseControl) {
        this.cruiseControl = cruiseControl;
    }

    public Integer getNavigation() {
        return navigation;
    }

    public void setNavigation(Integer navigation) {
        this.navigation = navigation;
    }

    public Integer getPowerLocks() {
        return powerLocks;
    }

    public void setPowerLocks(Integer powerLocks) {
        this.powerLocks = powerLocks;
    }

    public Integer getPowerSteering() {
        return powerSteering;
    }

    public void setPowerSteering(Integer powerSteering) {
        this.powerSteering = powerSteering;
    }

    public Integer getKeylessEntry() {
        return keylessEntry;
    }

    public void setKeylessEntry(Integer keylessEntry) {
        this.keylessEntry = keylessEntry;
    }

    public Integer getTvVcrDvd() {
        return tvVcrDvd;
    }

    public void setTvVcrDvd(Integer tvVcrDvd) {
        this.tvVcrDvd = tvVcrDvd;
    }

    public Integer getBucketSeats() {
        return bucketSeats;
    }

    public void setBucketSeats(Integer bucketSeats) {
        this.bucketSeats = bucketSeats;
    }

    public Integer getLeatherInterior() {
        return leatherInterior;
    }

    public void setLeatherInterior(Integer leatherInterior) {
        this.leatherInterior = leatherInterior;
    }

    public Integer getMemorySeats() {
        return memorySeats;
    }

    public void setMemorySeats(Integer memorySeats) {
        this.memorySeats = memorySeats;
    }

    public Integer getPowerSeats() {
        return powerSeats;
    }

    public void setPowerSeats(Integer powerSeats) {
        this.powerSeats = powerSeats;
    }

    public Integer getDriverAirbag() {
        return driverAirbag;
    }

    public void setDriverAirbag(Integer driverAirbag) {
        this.driverAirbag = driverAirbag;
    }

    public Integer getPassengerAirbag() {
        return passengerAirbag;
    }

    public void setPassengerAirbag(Integer passengerAirbag) {
        this.passengerAirbag = passengerAirbag;
    }

    public Integer getSideAirbag() {
        return sideAirbag;
    }

    public void setSideAirbag(Integer sideAirbag) {
        this.sideAirbag = sideAirbag;
    }

    public Integer getAlarm() {
        return alarm;
    }

    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
    }

    public Integer getAntiLockBrakes() {
        return antiLockBrakes;
    }

    public void setAntiLockBrakes(Integer antiLockBrakes) {
        this.antiLockBrakes = antiLockBrakes;
    }

    public Integer getFoglights() {
        return foglights;
    }

    public void setFoglights(Integer foglights) {
        this.foglights = foglights;
    }

    public Integer getCassettePlayer() {
        return cassettePlayer;
    }

    public void setCassettePlayer(Integer cassettePlayer) {
        this.cassettePlayer = cassettePlayer;
    }

    public Integer getCdChanger() {
        return cdChanger;
    }

    public void setCdChanger(Integer cdChanger) {
        this.cdChanger = cdChanger;
    }

    public Integer getCdPlayer() {
        return cdPlayer;
    }

    public void setCdPlayer(Integer cdPlayer) {
        this.cdPlayer = cdPlayer;
    }

    public Integer getPremiumSound() {
        return premiumSound;
    }

    public void setPremiumSound(Integer premiumSound) {
        this.premiumSound = premiumSound;
    }

    public Integer getPowerWindows() {
        return powerWindows;
    }

    public void setPowerWindows(Integer powerWindows) {
        this.powerWindows = powerWindows;
    }

    public Integer getRearWinDefogger() {
        return rearWinDefogger;
    }

    public void setRearWinDefogger(Integer rearWinDefogger) {
        this.rearWinDefogger = rearWinDefogger;
    }

    public Integer getRearWinWiper() {
        return rearWinWiper;
    }

    public void setRearWinWiper(Integer rearWinWiper) {
        this.rearWinWiper = rearWinWiper;
    }

    public Integer getTintedGlass() {
        return tintedGlass;
    }

    public void setTintedGlass(Integer tintedGlass) {
        this.tintedGlass = tintedGlass;
    }

    public Integer getAlloyWheels() {
        return alloyWheels;
    }

    public void setAlloyWheels(Integer alloyWheels) {
        this.alloyWheels = alloyWheels;
    }

    public Integer getSunroof() {
        return sunroof;
    }

    public void setSunroof(Integer sunroof) {
        this.sunroof = sunroof;
    }

    public Integer getMoonroof() {
        return moonroof;
    }

    public void setMoonroof(Integer moonroof) {
        this.moonroof = moonroof;
    }

    public Integer getThirdRowSeats() {
        return thirdRowSeats;
    }

    public void setThirdRowSeats(Integer thirdRowSeats) {
        this.thirdRowSeats = thirdRowSeats;
    }

    public Integer getTowPackage() {
        return towPackage;
    }

    public void setTowPackage(Integer towPackage) {
        this.towPackage = towPackage;
    }


}
