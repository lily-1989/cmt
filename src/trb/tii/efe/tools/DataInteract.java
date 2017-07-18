package trb.tii.efe.tools;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Clob;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.Vector;
import java.util.StringTokenizer;

import oracle.sql.CLOB;

public class DataInteract
{
    public static final boolean DEBUG = false;
    protected Connection conn;
    protected String market;
    // private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


    /**
     * The DataInteract's constructor contains the connection from the Controller class
     */
    public DataInteract(Connection conn) {
      this.conn = conn;
    }


    /**
     * Parameters from search.jsp are passed through to results.jsp
     * The search results are displayed with listAds.jsp
     */
    public ArrayList searchAds(int adtype, CCI_Search searchBy) throws ClassifiedToolException {

        ArrayList searchList = new ArrayList();
        PreparedStatement getRecords;

        if (!searchBy.getAdID().equals("")) {
            searchBy.setAdID(searchBy.getAdID().trim());
            searchBy.setAdID("%" + searchBy.getAdID() + "%");
            String nothing = "nothing";
        }

        if (!searchBy.getClassID().equals("")) {
            searchBy.setClassID(searchBy.getClassID().trim());
            searchBy.setClassID("%" + searchBy.getClassID() + "%");
        }

        if (!searchBy.getContent().equals("")) {
            searchBy.setContent(searchBy.getContent().trim());
            searchBy.setContent("%" + searchBy.getContent() + "%");
        }

        if (!searchBy.getPhone().equals("")) {
            searchBy.setPhone(searchBy.getPhone().trim());
            searchBy.setPhone("%" + searchBy.getPhone() + "%");
        }

        if (!searchBy.getEmail().equals("")) {
            searchBy.setEmail(searchBy.getEmail().trim());
            searchBy.setEmail("%" + searchBy.getEmail() + "%");
        }

        try {
            String query = this.searchAdsQuery(adtype, searchBy);
            getRecords = conn.prepareStatement(query);

            // Set query values for searching across all tables
            if (adtype == 0) {

                // fields used
                int f = 1;
                // total fields used - default at 7 (7 tables * 1 where clause for market field)
                int t = 7;
                // position of active field after where clause
                // defualt at 1 because market is always used
                int p = 1;

                // check for number of fields used and update fields used count
                if (!searchBy.getAdID().equals("")) {
                    ++f;
                }
                if (!searchBy.getClassID().equals("")) {
                    ++f;
                }
                if (!searchBy.getContent().equals("")) {
                    ++f;
                }
                if (!searchBy.getPhone().equals("")) {
                    ++f;
                }
                if (!searchBy.getEmail().equals("")) {
                    ++f;
                }
                if (!searchBy.getDateReceived().equals("")) {
                    ++f;
                }
                if (!searchBy.getStartDate().equals("")) {
                    ++f;
                }

                // total fields = default table number * fields used
                t *= f;

                // if field is used, then set values for prepared statement and update position
                // repeat until all fields are checked...
                for (int i = p; i <= t; i += f) {
                    getRecords.setString(i, searchBy.getMarket());
                }
                ++p;
                if (!searchBy.getAdID().equals("")) {
                    for (int i = p; i <= t; i += f) {
                        getRecords.setString(i, searchBy.getAdID());
                    }
                    ++p;
                }
                if (!searchBy.getClassID().equals("")) {
                    for (int i = p; i <= t; i += f) {
                        getRecords.setString(i, searchBy.getClassID());
                    }
                    ++p;
                }
                if (!searchBy.getContent().equals("")) {
                    for (int i = p; i <= t; i += f) {
                        getRecords.setString(i, searchBy.getContent());
                    }
                    ++p;
                }
                if (!searchBy.getPhone().equals("")) {
                    for (int i = p; i <= t; i += f) {
                        getRecords.setString(i, searchBy.getPhone());
                    }
                    ++p;
                }
                if (!searchBy.getEmail().equals("")) {
                    for (int i = p; i <= t; i += f) {
                        getRecords.setString(i, searchBy.getEmail());
                    }
                    ++p;
                }
                if (!searchBy.getDateReceived().equals("")) {
                    for (int i = p; i <= t; i += f) {
                        getRecords.setString(i, searchBy.getDateReceived());
                    }
                    ++p;
                }

                if (!searchBy.getStartDate().equals("")) {
                    for (int i = p; i <= t; i += f) {
                        getRecords.setString(i, searchBy.getStartDate());
                    }
                    ++p;
                }
            }
            // Set query values for searching across individual tables
            else {
                int i = 0;

                getRecords.setString(++i, searchBy.getMarket());

                if (!searchBy.getAdID().equals("")) {
                    getRecords.setString(++i, searchBy.getAdID());
                }
                if (!searchBy.getClassID().equals("")) {
                    getRecords.setString(++i, searchBy.getClassID());
                }
                if (!searchBy.getContent().equals("")) {
                    getRecords.setString(++i, searchBy.getContent());
                }
                if (!searchBy.getPhone().equals("")) {
                    getRecords.setString(++i, searchBy.getPhone());
                }
                if (!searchBy.getEmail().equals("")) {
                    getRecords.setString(++i, searchBy.getEmail());
                }
                if (!searchBy.getDateReceived().equals("")) {
                    getRecords.setString(++i, searchBy.getDateReceived());
                }
                if (!searchBy.getStartDate().equals("")) {
                    getRecords.setString(++i, searchBy.getStartDate());
                }
            }

            ResultSet results = getRecords.executeQuery();
            searchList = populateSearchAds(results);
        }
        catch (Exception e) {
            Logger.log("Error in Searching ads " + e.toString());

            // throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in Searching ads " + e.toString());
            throw ctException;
        }
        return searchList;
    }


    /**
     * Builds SQL query for results.jsp based on criteria from search.jsp
     */
    private String searchAdsQuery (int adtype, CCI_Search searchBy){

        String[] tableName = new String[8];
        String query = "";

        /* Adtype 0 represents 'all' */
        tableName[1] = Constants.CCI_APARTMENTS_TABLE_NAME;
        tableName[2] = Constants.CCI_GENCLASSIFIED_TABLE_NAME;
        tableName[3] = Constants.CCI_JOBS_TABLE_NAME;
        tableName[4] = Constants.CCI_REALESTATE_TABLE_NAME;
        tableName[5] = Constants.CCI_TRANSPORT_TABLE_NAME;
        tableName[6] = Constants.CCI_VITALNOTICE_TABLE_NAME;
        /* Adtype 7 represents 'recycler' */

        String cciQuery1 =        "select\n" +
                                  "      fc.feedid, market, adid, class, publication,\n" +
                                  "      to_char(startdate, 'yyyy/mm/dd') as startdate,\n" +
                                  "      to_char(stopdate, 'yyyy/mm/dd') as stopdate,\n" +
                                  "      title, '";

        String cciQuery2 =        "' as tablename\n" +
                                  "from\n" +
                                  "      ";

        String cciQuery3=         ", feedconfig fc\n" +
                                  "where\n" +
                                  "      ";

        String where =            ".feedid= fc.feedid\n" +
                                  "      and market like ? \n";
        String whereAdID =        "      and adid like ? \n";
        String whereClassID =     "      and class like ? \n";
        String whereConPhone =    "      and contact_phone like ? \n";
        String whereConEmail =    "      and contact_email like ? \n";
        String wherePrintDesc =   "      and dbms_lob.substr(print_desc, 4000, 1) like ? \n";
        String whereDateRec =     "      and to_char(datereceived, 'yyyy/mm/dd') like ? \n";
        String whereStartDate =   "      and to_char(startdate, 'yyyy/mm/dd') like ? \n";

        String rcyQuery =         "select\n" +
                                  "      fc.feedid, market, adid, class, publication,\n" +
                                  "      to_char(startdate, 'yyyy/mm/dd') as startdate,\n" +
                                  "      to_char(enddate, 'yyyy/mm/dd') as stopdate,\n" +
                                  "      dbms_lob.substr(content, 200, 1) as title, 'Recycler' as tablename\n" +
                                  "from\n" +
                                  "      Recycler, Feedconfig fc\n" +
                                  "where\n" +
                                  "      Recycler";

        String wherePhone =       "      and phone like ? \n";
        String whereEmail =       "      and email like ? \n";
        String whereContent =     "      and (dbms_lob.substr(content, 4000, 1) like ?)\n";

        /* Case: Search ALL ads */
        if (adtype == 0) {

            for (int i = 1; i < 7; i += 1) {
                query += cciQuery1 + tableName[i] + cciQuery2 + tableName[i] +
                    cciQuery3 + tableName[i] + where;

                if (!searchBy.getAdID().equals("")) {
                    query += whereAdID;
                }
                if (!searchBy.getClassID().equals("")) {
                    query += whereClassID;
                }
                if (!searchBy.getContent().equals("")) {
                    query += wherePrintDesc;
                }
                if (!searchBy.getPhone().equals("")) {
                    query += whereConPhone;
                }
                if (!searchBy.getEmail().equals("")) {
                    query += whereConEmail;
                }
                if (!searchBy.getDateReceived().equals("")) {
                    query += whereDateRec;
                }
                if (!searchBy.getStartDate().equals("")) {
                    query += whereStartDate;
                }
                query += "union all\n";
            }

            query += rcyQuery + where;

            if (!searchBy.getAdID().equals("")) {
                query += whereAdID;
            }
            if (!searchBy.getClassID().equals("")) {
                query += whereClassID;
            }
            if (!searchBy.getContent().equals("")) {
                query += whereContent;
            }
            if (!searchBy.getPhone().equals("")) {
                query += wherePhone;
            }
            if (!searchBy.getEmail().equals("")) {
                query += whereEmail;
            }
            if (!searchBy.getDateReceived().equals("")) {
                query += whereDateRec;
            }
            if (!searchBy.getStartDate().equals("")) {
                query += whereStartDate;
            }
            query += "order by feedid, market, adid\n";
        }
        /* Case: Search RECYCLER ads */
        else if (adtype == 7) {
            query = rcyQuery + where;

            if (!searchBy.getAdID().equals("")) {
                query += whereAdID;
            }
            if (!searchBy.getClassID().equals("")) {
                query += whereClassID;
            }
            if (!searchBy.getContent().equals("")) {
                query += whereContent;
            }
            if (!searchBy.getPhone().equals("")) {
                query += wherePhone;
            }
            if (!searchBy.getEmail().equals("")) {
                query += whereEmail;
            }
            if (!searchBy.getDateReceived().equals("")) {
                query += whereDateRec;
            }
            if (!searchBy.getStartDate().equals("")) {
                query += whereStartDate;
            }

            query += "order by feedid, market, adid\n";
        }
        /* Case: Search CCI ads individually */
        else {
            query = cciQuery1 + tableName[adtype] + cciQuery2 + tableName[adtype] +
                    cciQuery3 + tableName[adtype] + where;

            if (!searchBy.getAdID().equals("")) {
                query += whereAdID;
            }
            if (!searchBy.getClassID().equals("")) {
                query += whereClassID;
            }
            if (!searchBy.getContent().equals("")) {
                query += wherePrintDesc;
            }
            if (!searchBy.getPhone().equals("")) {
                query += whereConPhone;
            }
            if (!searchBy.getEmail().equals("")) {
                query += whereConEmail;
            }
            if (!searchBy.getDateReceived().equals("")) {
                query += whereDateRec;
            }
            if (!searchBy.getStartDate().equals("")) {
                query += whereStartDate;
            }
            query += "order by feedid, market, adid\n";
        }

        //System.out.println(query);

        if (DEBUG)
            Logger.log("\nQuery:\n" + query + "\n");

        return query;
    }


    /**
     * Stores search results values into ArrayList of CCI_Search Objects
     */
    private ArrayList populateSearchAds (ResultSet results) throws ClassifiedToolException {

        ArrayList searchList = new ArrayList();

        try {
            while (results.next()) {

                CCI_Search search = new CCI_Search();

                if (results.getInt (1) != 0)
                    search.setFeedID( results.getInt ( 1 ));
                if (results.getString (2) != null)
                    search.setMarket( results.getString ( 2 ));
                if (results.getString (3) != null)
                    search.setAdID( results.getString ( 3 ));
                if (results.getString (4) != null)
                    search.setClassID( results.getString ( 4 ));
                if (results.getString (5) != null)
                    search.setPublication( results.getString ( 5 ));
                if (results.getString (6) != null)
                    search.setStartDate( results.getString ( 6 ));
                if (results.getString (7) != null)
                    search.setStopDate( results.getString ( 7 ));
                if (results.getString (8) != null)
                    search.setTitle( results.getString ( 8 ));
                if (results.getString (9) != null)
                    search.setTableName( results.getString ( 9 ));

                searchList.add(search);
            }
        }
        catch (Exception e) {
            Logger.log("Error populating ad search list " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error populating ad search list " + e.toString());
            throw ctException;
        }

        return searchList;
    }


    /**
     * Called from deleteAd.jsp
     * Contructs and executes a delete statement to delete the specified ad
     */
    public void deleteAd(int feedID, String adID, String tableName) throws ClassifiedToolException {

        try {
	        Statement statement = conn.createStatement();
	        String statementString =
                    "delete from " + tableName + " where feedid = " + feedID + "and adid = '" + adID + "'";
	        Logger.log(statementString);
	        statement.executeUpdate(statementString);

            Logger.log("Deletion of " + adID + " successful");
        }
	    catch(Exception e) {
            Logger.log("Error deleting ad " + e.toString());
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error deleting ad " + e.toString());
            throw ctException;
	    }
	}


    /**
     * Search for ad for editAd.jsp then populate the fields for the correct ad type
     */
    public CCI_Ad searchEditAds (String tableName, int feedID, String adID)
            throws ClassifiedToolException {

        CCI_Ad ad = new CCI_Ad();

        PreparedStatement getRecords;
        String query =
                "select * \n" +
                "from " + tableName + " cci, feedconfig f \n" +
                "where cci.feedid = ? and adid = ? and cci.feedid=f.feedid";

        try {
            getRecords = conn.prepareStatement(query);
            getRecords.setInt(1, feedID);
            getRecords.setString(2, adID);
            ResultSet results = getRecords.executeQuery();

            if (tableName.equalsIgnoreCase(Constants.CCI_APARTMENTS_TABLE_NAME)) {
                ad = populateAptAd (results);
            }
            else if (tableName.equalsIgnoreCase(Constants.CCI_GENCLASSIFIED_TABLE_NAME)) {
                ad = populateGenAd(results);
            }
            else if (tableName.equalsIgnoreCase(Constants.CCI_JOBS_TABLE_NAME)) {
                ad = populateJobAd(results);
            }
            else if (tableName.equalsIgnoreCase(Constants.CCI_REALESTATE_TABLE_NAME)) {
                ad = populateREAd(results);
            }
            else if (tableName.equalsIgnoreCase(Constants.CCI_TRANSPORT_TABLE_NAME)) {
                ad = populateTransAd(results);
            }
            else if (tableName.equalsIgnoreCase(Constants.CCI_VITALNOTICE_TABLE_NAME)) {
                ad = populateVitalAd(results);
            }
            else if (tableName.equalsIgnoreCase(Constants.RECYCLER_TABLE_NAME)) {
                ad = populateRecyclerAd(results);
            }
            else
                Logger.log("Error in searching edit ad");
        }
        catch(Exception e) {
            Logger.log("Error in searching edit ad " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in searching edit ad " + e.toString());
            throw ctException;
        }

        return ad;
    }

    /**
     * Stores edit search results values into ArrayList of CCI_AptAd Objects
     */
    private CCI_Ad populateAptAd (ResultSet results) throws ClassifiedToolException {

        CCI_AptAd aptAd = new CCI_AptAd();
        aptAd.setTableName(Constants.CCI_APARTMENTS_TABLE_NAME);

        try {
            results.next();

            if (results.getObject ("feedID") != null)
                aptAd.setFeedID (new Integer(results.getInt ("feedID")));
            if (results.getString ("FEEDNAME") != null)
                aptAd.setFeedName ( results.getString ("feedName"));
            if (results.getString ("adID") != null)
                aptAd.setAdID ( results.getString ("adID"));
            if (results.getDate ("dateReceived") != null)
                aptAd.setDateReceived ( results.getTimestamp ("dateReceived"));
            if (results.getDate ("firstReceived") != null)
                aptAd.setFirstReceived ( results.getTimestamp ("firstReceived"));
            if (results.getDate ("dateUpdated") != null)
                aptAd.setDateUpdated ( results.getTimestamp ("dateUpdated"));
            if (results.getString ("publication") != null)
                aptAd.setPublication ( results.getString ("publication"));
            if (results.getString ("commercialrelease") != null)
                aptAd.setCommercialRelease ( results.getString ("commercialrelease"));
            if (results.getObject ("canceled") != null)
                aptAd.setCanceled (new Integer(results.getInt ("canceled")));
            if (results.getString ("cust_city") != null)
                aptAd.setCustomerCity ( results.getString ("cust_city"));
            if (results.getString ("cust_state") != null)
                aptAd.setCustomerState ( results.getString ("cust_state"));
            if (results.getString ("cust_zip") != null)
                aptAd.setCustomerZip ( results.getString ("cust_zip"));
            if (results.getString ("cust_email") != null)
                aptAd.setCustomerEmail( results.getString ("cust_email"));
            if (results.getString ("class") != null)
                aptAd.setClassID ( results.getString ("class"));
            if (results.getDate ("startDate") != null)
                aptAd.setStartDate ( results.getDate ("startDate"));
            if (results.getDate ("stopDate") != null)
                aptAd.setStopDate ( results.getDate ("stopDate"));
            if (results.getString ("title") != null)
                aptAd.setTitle ( results.getString ("title"));
            if (results.getString ("upsell") != null)
                aptAd.setUpsell ( results.getString ("upsell"));
            if (results.getString ("contact_email") != null)
                aptAd.setContactEmail ( results.getString ("contact_email"));
            if (results.getString ("enhance_email") != null)
                aptAd.setEnhanceEmail ( results.getString ("enhance_email"));
            if (results.getObject ("photo_count") != null)
                aptAd.setPhotoCount (new Integer(results.getInt ("photo_count")));
            if (results.getString ("photo_ref") != null)
                aptAd.setPhotoRef ( results.getString ("photo_ref"));
            if (results.getString ("logo_ref") != null)
                aptAd.setLogoRef ( results.getString ("logo_ref"));
            if (results.getString ("printad_img") != null)
                aptAd.setPrintAdImage ( results.getString ("printad_img"));
            if (results.getString ("contact_phone") != null)
                aptAd.setContactPhone ( results.getString ("contact_phone"));
            if (results.getObject ("print_desc") != null)
                aptAd.setPrintDesc ( clobToString((oracle.sql.CLOB)results.getObject("print_desc")));
            if (results.getObject ("enhanced_desc") != null)
                aptAd.setEnhancedDesc ( clobToString((oracle.sql.CLOB)results.getObject("enhanced_desc")));
            if (results.getString ("insertionID") != null)
                aptAd.setInsertionID ( results.getString ("insertionID"));
            if (results.getString ("property_type") != null)
                aptAd.setPropertyType ( results.getString ("property_type"));
            if (results.getString ("property_city") != null)
                aptAd.setPropertyCity ( results.getString ("property_city"));
            if (results.getString ("property_state") != null)
                aptAd.setPropertyState ( results.getString ("property_state"));
            if (results.getString ("property_zip") != null)
                aptAd.setPropertyZip ( results.getString ("property_zip"));
            if (results.getString ("price") != null)
                aptAd.setPrice ( results.getString ("price"));
            if (results.getString ("bedrooms") != null)
                aptAd.setBedrooms ( results.getString ("bedrooms"));
            if (results.getString ("baths") != null)
                aptAd.setBaths ( results.getString ("baths"));
            if (results.getString ("property_address") != null)
                aptAd.setPropertyAddress ( results.getString ("property_address"));
            if (results.getString ("neighborhood") != null)
                aptAd.setNeighborhood ( results.getString ("neighborhood"));
            if (results.getString ("country") != null)
                aptAd.setCountry ( results.getString ("country"));
            if (results.getString ("webID") != null)
                aptAd.setWebID ( results.getString ("webID"));
            if (results.getDate("OPENHSE_DATE1") != null)
                aptAd.setOpenHouseDate1 ( results.getDate ("OPENHSE_DATE1"));
            if (results.getString ("OPENHSE_STARTTM1") != null)
                aptAd.setOpenHouseStartTime1 ( results.getString ("OPENHSE_STARTTM1"));
            if (results.getString ("OPENHSE_ENDTM1") != null)
                aptAd.setOpenHouseEndTime1 ( results.getString ("OPENHSE_ENDTM1"));
            if (results.getDate ("OPENHSE_DATE2") != null)
                aptAd.setOpenHouseDate2 ( results.getDate ("OPENHSE_DATE2"));
            if (results.getString ("OPENHSE_STARTTM2") != null)
                aptAd.setOpenHouseStartTime2 ( results.getString ("OPENHSE_STARTTM2"));
            if (results.getString ("OPENHSE_ENDTM2") != null)
                aptAd.setOpenHouseEndTime2 ( results.getString ("OPENHSE_ENDTM2"));
            if (results.getString ("MLS_NUMBER") != null)
                aptAd.setMlsNumber ( results.getString ("MLS_NUMBER"));
            if (results.getString ("SQ_FT") != null)
                aptAd.setSquareFeet ( results.getString ("SQ_FT"));
            if (results.getString ("AIR_CONDITION") != null)
                aptAd.setAirCondition ( results.getString ("AIR_CONDITION"));
            if (results.getString ("BASEMENT") != null)
                aptAd.setBasement ( results.getString ("BASEMENT"));
            if (results.getString ("DISHWASHER") != null)
                aptAd.setDishwasher ( results.getString ("DISHWASHER"));
            if (results.getString ("ELEVATOR") != null)
                aptAd.setElevator ( results.getString ("ELEVATOR"));
            if (results.getString ("FIREPLACE") != null)
                aptAd.setFireplace ( results.getString ("FIREPLACE"));
            if (results.getString ("FITNESS_ROOM") != null)
                aptAd.setFitnessRoom ( results.getString ("FITNESS_ROOM"));
            if (results.getString ("DINING_ROOM") != null)
                aptAd.setDiningRoom ( results.getString ("DINING_ROOM"));
            if (results.getString ("FAMILY_ROOM") != null)
                aptAd.setFamilyRoom ( results.getString ("FAMILY_ROOM"));
            if (results.getString ("LAUNDRY") != null)
                aptAd.setLaundry ( results.getString ("LAUNDRY"));
            if (results.getString ("OTHER_PARKING") != null)
                aptAd.setOtherParking ( results.getString ("OTHER_PARKING"));
            if (results.getString ("GARAGE_PARKING") != null)
                aptAd.setGarageParking ( results.getString ("GARAGE_PARKING"));
            if (results.getString ("PETS") != null)
                aptAd.setPets ( results.getString ("PETS"));
            if (results.getString ("POOL") != null)
                aptAd.setPool ( results.getString ("POOL"));
            if (results.getString ("SECURITY") != null)
                aptAd.setSecurity ( results.getString ("SECURITY"));
            if (results.getString ("WEBID") != null)
                aptAd.setWebID ( results.getString ("WEBID"));
            if (results.getString ("AGENT_URL") != null)
                aptAd.setAgentUrl ( results.getString ("AGENT_URL"));
        }
        catch (Exception e) {
            Logger.log("Error populating CCI Apt ad " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error populating CCI Apt ad " + e.toString());
            throw ctException;
        }

        return aptAd;
    }


    /**
     * Stores edit search results values into ArrayList of CCI_GenAd Objects
     */
    private CCI_Ad populateGenAd (ResultSet results) throws ClassifiedToolException {

        CCI_GenAd genAd = new CCI_GenAd();
        genAd.setTableName(Constants.CCI_GENCLASSIFIED_TABLE_NAME);

        try {
            results.next();

            if (results.getObject ("feedID") != null)
                genAd.setFeedID (new Integer(results.getInt ("feedID")));
            if (results.getString ("FEEDNAME") != null)
                genAd.setFeedName( results.getString ("feedName"));
            if (results.getString ("adID") != null)
                genAd.setAdID ( results.getString ("adID"));
            if (results.getDate ("dateReceived") != null)
                genAd.setDateReceived ( results.getTimestamp ("dateReceived"));
            if (results.getDate ("firstReceived") != null)
                genAd.setFirstReceived ( results.getTimestamp ("firstReceived"));
            if (results.getDate ("dateUpdated") != null)
                genAd.setDateUpdated ( results.getTimestamp ("dateUpdated"));
            if (results.getString ("publication") != null)
                genAd.setPublication ( results.getString ("publication"));
            if (results.getString ("commercialrelease") != null)
                genAd.setCommercialRelease ( results.getString ("commercialrelease"));
            if (results.getObject ("canceled") != null)
                genAd.setCanceled (new Integer(results.getInt ("canceled")));
            if (results.getString ("cust_city") != null)
                genAd.setCustomerCity ( results.getString ("cust_city"));
            if (results.getString ("cust_state") != null)
                genAd.setCustomerState ( results.getString ("cust_state"));
            if (results.getString ("cust_zip") != null)
                genAd.setCustomerZip ( results.getString ("cust_zip"));
            if (results.getString ("cust_email") != null)
                genAd.setCustomerEmail( results.getString ("cust_email"));
            if (results.getString ("class") != null)
                genAd.setClassID ( results.getString ("class"));
            if (results.getDate ("startDate") != null)
                genAd.setStartDate ( results.getDate ("startDate"));
            if (results.getDate ("stopDate") != null)
                genAd.setStopDate ( results.getDate ("stopDate"));
            if (results.getString ("title") != null)
                genAd.setTitle ( results.getString ("title"));
            if (results.getString ("upsell") != null)
                genAd.setUpsell ( results.getString ("upsell"));
            if (results.getString ("contact_email") != null)
                genAd.setContactEmail ( results.getString ("contact_email"));
            if (results.getString ("enhance_email") != null)
                genAd.setEnhanceEmail ( results.getString ("enhance_email"));
            if (results.getObject ("photo_count") != null)
                genAd.setPhotoCount (new Integer(results.getInt ("photo_count")));
            if (results.getString ("photo_ref") != null)
                genAd.setPhotoRef ( results.getString ("photo_ref"));
            if (results.getString ("logo_ref") != null)
                genAd.setLogoRef ( results.getString ("logo_ref"));
            if (results.getString ("printad_img") != null)
                genAd.setPrintAdImage ( results.getString ("printad_img"));
            if (results.getString ("contact_phone") != null)
                genAd.setContactPhone ( results.getString ("contact_phone"));
            if (results.getObject ("print_desc") != null)
                genAd.setPrintDesc ( clobToString((oracle.sql.CLOB)results.getObject("print_desc")));
            if (results.getObject ("enhanced_desc") != null)
                genAd.setEnhancedDesc ( clobToString((oracle.sql.CLOB)results.getObject("enhanced_desc")));
            if (results.getString ("insertionID") != null)
                genAd.setInsertionID ( results.getString ("insertionID"));
            if (results.getString ("type") != null)
                genAd.setType ( results.getString ("type"));
            if (results.getString ("zip") != null)
                genAd.setZip ( results.getString ("zip"));
            if (results.getString ("price") != null)
                genAd.setPrice ( results.getString ("price"));
            if (results.getString ("country") != null)
                genAd.setCountry ( results.getString ("country"));
            if (results.getObject ("OBO") != null)
                genAd.setoBO (new Integer(results.getInt ("OBO")));
            if (results.getString ("address") != null)
                genAd.setAddress ( results.getString ("address"));
            if (results.getString ("city") != null)
                genAd.setCity ( results.getString ("city"));
            if (results.getString ("state") != null)
                genAd.setState ( results.getString ("state"));
            if (results.getString ("breed") != null)
                genAd.setBreed ( results.getString ("breed"));
            if (results.getString ("advertiser_url") != null)
                genAd.setAdvertiserUrl ( results.getString ("advertiser_url"));
            if (results.getString ("EVENT_START_TIME") != null)
                genAd.setEventStartTime ( results.getString ("EVENT_START_TIME"));
            if (results.getString ("EVENT_END_TIME") != null)
                genAd.setEventEndTime ( results.getString ("EVENT_END_TIME"));
            if (results.getString ("EVENT_DATE") != null)
                genAd.setEventDate ( results.getString ("EVENT_DATE"));
            if (results.getString ("COLOR") != null)
                genAd.setColor ( results.getString ("COLOR"));
            if (results.getString ("GENDER") != null)
                genAd.setGender ( results.getString ("GENDER"));
            if (results.getString ("AGE") != null)
                genAd.setAge ( results.getString ("AGE"));
           if (results.getString ("FIRSTNAME1") != null)
                genAd.setFirstName1 ( results.getString ("FIRSTNAME1"));
           if (results.getString ("LASTNAME1") != null)
                genAd.setLastName1 ( results.getString ("LASTNAME1"));
           if (results.getString ("FIRSTNAME2") != null)
                genAd.setFirstName2 ( results.getString ("FIRSTNAME2"));
           if (results.getString ("LASTNAME2") != null)
                genAd.setLastName2 ( results.getString ("LASTNAME2"));
           if (results.getString ("CELEBRATION_TYPE") != null)
                genAd.setCelebrationType ( results.getString ("CELEBRATION_TYPE"));
        }
        catch (Exception e) {
            Logger.log("Error populating CCI GenClassified ad " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error populating CCI GenClassifed ad " + e.toString());
            throw ctException;
        }

        return genAd;
    }


    /**
     * Stores edit search results values into ArrayList of CCI_JobAd Objects
     */
    public CCI_Ad populateJobAd (ResultSet results) throws ClassifiedToolException {

        CCI_JobAd jobAd = new CCI_JobAd();
        jobAd.setTableName(Constants.CCI_JOBS_TABLE_NAME);

        try {
            results.next();

            if (results.getObject ("feedID") != null)
                jobAd.setFeedID (new Integer(results.getInt ("feedID")));
            if (results.getString ("FEEDNAME") != null)
                jobAd.setFeedName ( results.getString ("feedName"));
            if (results.getString ("adID") != null)
                jobAd.setAdID ( results.getString ("adID"));
            if (results.getDate ("dateReceived") != null)
                jobAd.setDateReceived ( results.getTimestamp ("dateReceived"));
            if (results.getDate ("firstReceived") != null)
                jobAd.setFirstReceived ( results.getTimestamp ("firstReceived"));
            if (results.getDate ("dateUpdated") != null)
                jobAd.setDateUpdated ( results.getTimestamp ("dateUpdated"));
            if (results.getString ("publication") != null)
                jobAd.setPublication ( results.getString ("publication"));
            if (results.getString ("commercialrelease") != null)
                jobAd.setCommercialRelease ( results.getString ("commercialrelease"));
            if (results.getObject ("canceled") != null)
                jobAd.setCanceled (new Integer(results.getInt ("canceled")));
            if (results.getString ("cust_city") != null)
                jobAd.setCustomerCity ( results.getString ("cust_city"));
            if (results.getString ("cust_state") != null)
                jobAd.setCustomerState ( results.getString ("cust_state"));
            if (results.getString ("cust_zip") != null)
                jobAd.setCustomerZip ( results.getString ("cust_zip"));
            if (results.getString ("cust_email") != null)
                jobAd.setCustomerEmail( results.getString ("cust_email"));
            if (results.getString ("class") != null)
                jobAd.setClassID ( results.getString ("class"));
            if (results.getDate ("startDate") != null)
                jobAd.setStartDate ( results.getDate ("startDate"));
            if (results.getDate ("stopDate") != null)
                jobAd.setStopDate ( results.getDate ("stopDate"));
            if (results.getString ("title") != null)
                jobAd.setTitle ( results.getString ("title"));
            if (results.getString ("upsell") != null)
                jobAd.setUpsell ( results.getString ("upsell"));
            if (results.getString ("contact_email") != null)
                jobAd.setContactEmail ( results.getString ("contact_email"));
            if (results.getString ("enhance_email") != null)
                jobAd.setEnhanceEmail ( results.getString ("enhance_email"));
            if (results.getObject ("photo_count") != null)
                jobAd.setPhotoCount (new Integer(results.getInt ("photo_count")));
            if (results.getString ("photo_ref") != null)
                jobAd.setPhotoRef ( results.getString ("photo_ref"));
            if (results.getString ("logo_ref") != null)
                jobAd.setLogoRef ( results.getString ("logo_ref"));
            if (results.getString ("printad_img") != null)
                jobAd.setPrintAdImage ( results.getString ("printad_img"));
            if (results.getString ("contact_phone") != null)
                jobAd.setContactPhone ( results.getString ("contact_phone"));
            if (results.getObject ("print_desc") != null)
                jobAd.setPrintDesc ( clobToString((oracle.sql.CLOB)results.getObject("print_desc")));
            if (results.getObject ("enhanced_desc") != null)
                jobAd.setEnhancedDesc ( clobToString((oracle.sql.CLOB)results.getObject("enhanced_desc")));
            if (results.getString ("insertionID") != null)
                jobAd.setInsertionID ( results.getString ("insertionID"));
            if (results.getString ("JOB_TITLE") != null)
                jobAd.setJobTitle ( results.getString ("JOB_TITLE"));
            if (results.getString ("JOB_TYPE_CODE") != null)
                jobAd.setJobTypeCode ( results.getString ("JOB_TYPE_CODE"));
            if (results.getString ("JOB_LOC_ZIP") != null)
                jobAd.setJobLocationZip ( results.getString ("JOB_LOC_ZIP"));
            if (results.getString ("WORK_TYPE") != null)
                jobAd.setWorkType ( results.getString ("WORK_TYPE"));
            if (results.getString ("COMPANY_NAME") != null)
                jobAd.setCompanyName ( results.getString ("COMPANY_NAME"));
            if (results.getString ("CONTACT_NAME") != null)
                jobAd.setContactName ( results.getString ("CONTACT_NAME"));
            if (results.getString ("CONTACT_FAX") != null)
                jobAd.setContactFax ( results.getString ("CONTACT_FAX"));
            if (results.getString ("JOB_LOC_CITY") != null)
                jobAd.setJobLocationCity ( results.getString ("JOB_LOC_CITY"));
            if (results.getString ("JOB_LOC_STATE") != null)
                jobAd.setJobLocationState ( results.getString ("JOB_LOC_STATE"));
            if (results.getString ("JOB_LOC_COUNTRY") != null)
                jobAd.setJobLocationCountry ( results.getString ("JOB_LOC_COUNTRY"));
            if (results.getString ("EDUCATION") != null)
                jobAd.setEducation ( results.getString ("EDUCATION"));
            if (results.getString ("EXPERIENCE") != null)
                jobAd.setExperience ( results.getString ("EXPERIENCE"));
            if (results.getString ("TRAVEL") != null)
                jobAd.setTravel ( results.getString ("TRAVEL"));
            if (results.getString ("BASE_PAY_PER") != null)
                jobAd.setBasePayPer ( results.getString ("BASE_PAY_PER"));
            if (results.getString ("BASE_PAY_LOW") != null)
                jobAd.setBasePayLow ( results.getString ("BASE_PAY_LOW"));
            if (results.getString ("BASE_PAY_HIGH") != null)
                jobAd.setBasePayHigh ( results.getString ("BASE_PAY_HIGH"));
            if (results.getString ("COMMISSION") != null)
                jobAd.setCommission ( results.getString ("COMMISSION"));
            if (results.getString ("BONUS") != null)
                jobAd.setBonus ( results.getString ("BONUS"));
            if (results.getString ("OTHER_PAY") != null)
                jobAd.setOtherPay ( results.getString ("OTHER_PAY"));
            if (results.getString ("COMPANY_URL") != null)
                jobAd.setCompanyUrl ( results.getString ("COMPANY_URL"));
            if (results.getString ("APPLY_URL") != null)
                jobAd.setApplyUrl ( results.getString ("APPLY_URL"));
            if (results.getString ("WEBID") != null)
                jobAd.setWebID ( results.getString ("WEBID"));
            if (results.getString ("INDUSTRY_CODE1") != null)
                jobAd.setIndustryCode1 ( results.getString ("INDUSTRY_CODE1"));
            if (results.getString ("INDUSTRY_CODE2") != null)
                jobAd.setIndustryCode2 ( results.getString ("INDUSTRY_CODE2"));
            if (results.getString ("INDUSTRY_CODE3") != null)
                jobAd.setIndustryCode3 ( results.getString ("INDUSTRY_CODE3"));
             if (results.getString ("ORIGINAL_AD_ID") != null)
                jobAd.setOriginalAdID ( results.getString ("ORIGINAL_AD_ID"));
            if (results.getString ("VOICE_TYPE") != null)
                jobAd.setVoiceType ( results.getString ("VOICE_TYPE"));
            if (results.getString ("COMPANY_VIDEO_PROVIDED") != null)
                jobAd.setCompanyVideoProvided ( results.getString ("COMPANY_VIDEO_PROVIDED"));
             if (results.getString ("MOREDETAILS_URL") != null)
                jobAd.setMoreDetailsURL ( results.getString ("MOREDETAILS_URL"));
            if (results.getString ("VIDEO_NOTES") != null)
                jobAd.setVideoNotes ( results.getString ("VIDEO_NOTES"));
             if (results.getString ("VIDEO_SEARCH_KEYWORDS") != null)
                jobAd.setVideoSearchKeywords ( results.getString ("VIDEO_SEARCH_KEYWORDS"));
        }
        catch (Exception e) {
            Logger.log("Error populating CCI Job ad " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error populating CCI Job ad " + e.toString());
            throw ctException;
        }

        return jobAd;
    }


    /**
     * Stores edit search results values into ArrayList of CCI_REAd Objects
     */
    public CCI_Ad populateREAd (ResultSet results) throws ClassifiedToolException {

        CCI_REAd reAd = new CCI_REAd();
        reAd.setTableName(Constants.CCI_REALESTATE_TABLE_NAME);

        try {
            results.next();

            if (results.getObject ("feedID") != null)
                reAd.setFeedID (new Integer(results.getInt ("feedID")));
            if (results.getString ("FEEDNAME") != null)
                reAd.setFeedName ( results.getString ("feedName"));
            if (results.getString ("adID") != null)
                reAd.setAdID ( results.getString ("adID"));
            if (results.getDate ("dateReceived") != null)
                reAd.setDateReceived ( results.getTimestamp ("dateReceived"));
            if (results.getDate ("firstReceived") != null)
                reAd.setFirstReceived ( results.getTimestamp ("firstReceived"));
            if (results.getDate ("dateUpdated") != null)
                reAd.setDateUpdated ( results.getTimestamp ("dateUpdated"));
            if (results.getString ("publication") != null)
                reAd.setPublication ( results.getString ("publication"));
            if (results.getString ("commercialrelease") != null)
                reAd.setCommercialRelease ( results.getString ("commercialrelease"));
            if (results.getObject ("canceled") != null)
                reAd.setCanceled (new Integer(results.getInt ("canceled")));
            if (results.getString ("cust_city") != null)
                reAd.setCustomerCity ( results.getString ("cust_city"));
            if (results.getString ("cust_state") != null)
                reAd.setCustomerState ( results.getString ("cust_state"));
            if (results.getString ("cust_zip") != null)
                reAd.setCustomerZip ( results.getString ("cust_zip"));
            if (results.getString ("cust_email") != null)
                reAd.setCustomerEmail( results.getString ("cust_email"));
            if (results.getString ("class") != null)
                reAd.setClassID ( results.getString ("class"));
            if (results.getDate ("startDate") != null)
                reAd.setStartDate ( results.getDate ("startDate"));
            if (results.getDate ("stopDate") != null)
                reAd.setStopDate ( results.getDate ("stopDate"));
            if (results.getString ("title") != null)
                reAd.setTitle ( results.getString ("title"));
            if (results.getString ("upsell") != null)
                reAd.setUpsell ( results.getString ("upsell"));
            if (results.getString ("contact_email") != null)
                reAd.setContactEmail ( results.getString ("contact_email"));
            if (results.getString ("enhance_email") != null)
                reAd.setEnhanceEmail ( results.getString ("enhance_email"));
            if (results.getObject ("photo_count") != null)
                reAd.setPhotoCount (new Integer(results.getInt ("photo_count")));
            if (results.getString ("photo_ref") != null)
                reAd.setPhotoRef ( results.getString ("photo_ref"));
            if (results.getString ("logo_ref") != null)
                reAd.setLogoRef ( results.getString ("logo_ref"));
            if (results.getString ("printad_img") != null)
                reAd.setPrintAdImage ( results.getString ("printad_img"));
            if (results.getString ("contact_phone") != null)
                reAd.setContactPhone ( results.getString ("contact_phone"));
            if (results.getObject ("print_desc") != null)
                reAd.setPrintDesc ( clobToString((oracle.sql.CLOB)results.getObject("print_desc")));
            if (results.getObject ("enhanced_desc") != null)
                reAd.setEnhancedDesc ( clobToString((oracle.sql.CLOB)results.getObject("enhanced_desc")));
            if (results.getString ("insertionID") != null)
                reAd.setInsertionID ( results.getString ("insertionID"));
            if (results.getString ("SALE_LEASE") != null)
                reAd.setSaleLease ( results.getString ("SALE_LEASE"));
            if (results.getString ("PRICE_PER_SQ_FT") != null)
                reAd.setPricePerSqFt (results.getString ("PRICE_PER_SQ_FT"));
            if (results.getString ("PROPERTY_TYPE") != null)
                reAd.setPropertyType ( results.getString ("PROPERTY_TYPE"));
            if (results.getString ("property_city") != null)
                reAd.setPropertyCity ( results.getString ("property_city"));
            if (results.getString ("PROPERTY_STATE") != null)
                reAd.setPropertyState ( results.getString ("PROPERTY_STATE"));
            if (results.getString ("PROPERTY_ZIP") != null)
                reAd.setPropertyZip ( results.getString ("PROPERTY_ZIP"));
            if (results.getString ("PRICE") != null)
                reAd.setPrice (results.getString ("PRICE"));
            if (results.getString ("BEDROOMS") != null)
                reAd.setBedrooms ( results.getString ("BEDROOMS"));
            if (results.getString ("BATHS") != null)
                reAd.setBaths ( results.getString ("BATHS"));
            if (results.getString ("PROPERTY_ADDRESS") != null)
                reAd.setPropertyAddress ( results.getString ("PROPERTY_ADDRESS"));
            if (results.getString ("NEIGHBORHOOD") != null)
                reAd.setNeighborhood ( results.getString ("NEIGHBORHOOD"));
            if (results.getString ("COUNTRY") != null)
                reAd.setCountry ( results.getString ("COUNTRY"));
            if (results.getDate("OPENHSE_DATE1") != null)
                reAd.setOpenHouseDate1 ( results.getDate ("OPENHSE_DATE1"));
            if (results.getString ("OPENHSE_STARTTM1") != null)
                reAd.setOpenHouseStartTime1 ( results.getString ("OPENHSE_STARTTM1"));
            if (results.getString ("OPENHSE_ENDTM1") != null)
                reAd.setOpenHouseEndTime1 ( results.getString ("OPENHSE_ENDTM1"));
            if (results.getDate ("OPENHSE_DATE2") != null)
                reAd.setOpenHouseDate2 ( results.getDate ("OPENHSE_DATE2"));
            if (results.getString ("OPENHSE_STARTTM2") != null)
                reAd.setOpenHouseStartTime2 ( results.getString ("OPENHSE_STARTTM2"));
            if (results.getString ("OPENHSE_ENDTM2") != null)
                reAd.setOpenHouseEndTime2 ( results.getString ("OPENHSE_ENDTM2"));
            if (results.getString ("MLS_NUMBER") != null)
                reAd.setMlsNumber ( results.getString ("MLS_NUMBER"));
            if (results.getString ("SQ_FT") != null)
                reAd.setSquareFeet ( results.getString ("SQ_FT"));
            if (results.getString ("AIR_CONDITION") != null)
                reAd.setAirCondition ( results.getString ("AIR_CONDITION"));
            if (results.getString ("BASEMENT") != null)
                reAd.setBasement ( results.getString ("BASEMENT"));
            if (results.getString ("DISHWASHER") != null)
                reAd.setDishwasher ( results.getString ("DISHWASHER"));
            if (results.getString ("ELEVATOR") != null)
                reAd.setElevator ( results.getString ("ELEVATOR"));
            if (results.getString ("FIREPLACE") != null)
                reAd.setFireplace ( results.getString ("FIREPLACE"));
            if (results.getString ("FITNESS_ROOM") != null)
                reAd.setFitnessRoom ( results.getString ("FITNESS_ROOM"));
            if (results.getString ("DINING_ROOM") != null)
                reAd.setDiningRoom ( results.getString ("DINING_ROOM"));
            if (results.getString ("FAMILY_ROOM") != null)
                reAd.setFamilyRoom ( results.getString ("FAMILY_ROOM"));
            if (results.getString ("LAUNDRY") != null)
                reAd.setLaundry ( results.getString ("LAUNDRY"));
            if (results.getString ("OTHER_PARKING") != null)
                reAd.setOtherParking ( results.getString ("OTHER_PARKING"));
            if (results.getString ("GARAGE_PARKING") != null)
                reAd.setGarageParking ( results.getString ("GARAGE_PARKING"));
            if (results.getString ("PETS") != null)
                reAd.setPets ( results.getString ("PETS"));
            if (results.getString ("POOL") != null)
                reAd.setPool ( results.getString ("POOL"));
            if (results.getString ("SECURITY") != null)
                reAd.setSecurity ( results.getString ("SECURITY"));
            if (results.getString ("WEBID") != null)
                reAd.setWebID ( results.getString ("WEBID"));
            if (results.getString ("AGENT_URL") != null)
                reAd.setAgentUrl ( results.getString ("AGENT_URL"));
        }
        catch (Exception e) {
            Logger.log("Error populating CCI Real Estate ad " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error populating CCI Real Estate ad " + e.toString());
            throw ctException;
        }

        return reAd;
    }


    /**
     * Stores edit search results values into ArrayList of CCI_TransAd Objects
     */
    public CCI_Ad populateTransAd (ResultSet results) throws ClassifiedToolException {

        CCI_TransAd transAd = new CCI_TransAd();
        transAd.setTableName(Constants.CCI_TRANSPORT_TABLE_NAME);

        try {
            results.next();

            if (results.getObject ("feedID") != null)
                transAd.setFeedID (new Integer(results.getInt ("feedID")));
            if (results.getString ("FEEDNAME") != null)
                transAd.setFeedName ( results.getString ("feedName"));
            if (results.getString ("adID") != null)
                transAd.setAdID ( results.getString ("adID"));
            if (results.getDate ("dateReceived") != null)
                transAd.setDateReceived ( results.getTimestamp ("dateReceived"));
            if (results.getDate ("firstReceived") != null)
                transAd.setFirstReceived ( results.getTimestamp ("firstReceived"));
            if (results.getDate ("dateUpdated") != null)
                transAd.setDateUpdated ( results.getTimestamp ("dateUpdated"));
            if (results.getString ("publication") != null)
                transAd.setPublication ( results.getString ("publication"));
            if (results.getString ("commercialrelease") != null)
                transAd.setCommercialRelease ( results.getString ("commercialrelease"));
            if (results.getObject ("canceled") != null)
                transAd.setCanceled (new Integer(results.getInt ("canceled")));
            if (results.getString ("cust_city") != null)
                transAd.setCustomerCity ( results.getString ("cust_city"));
            if (results.getString ("cust_state") != null)
                transAd.setCustomerState ( results.getString ("cust_state"));
            if (results.getString ("cust_zip") != null)
                transAd.setCustomerZip ( results.getString ("cust_zip"));
            if (results.getString ("cust_email") != null)
                transAd.setCustomerEmail( results.getString ("cust_email"));
            if (results.getString ("class") != null)
                transAd.setClassID ( results.getString ("class"));
            if (results.getDate ("startDate") != null)
                transAd.setStartDate ( results.getDate ("startDate"));
            if (results.getDate ("stopDate") != null)
                transAd.setStopDate ( results.getDate ("stopDate"));
            if (results.getString ("title") != null)
                transAd.setTitle ( results.getString ("title"));
            if (results.getString ("upsell") != null)
                transAd.setUpsell ( results.getString ("upsell"));
            if (results.getString ("contact_email") != null)
                transAd.setContactEmail ( results.getString ("contact_email"));
            if (results.getString ("enhance_email") != null)
                transAd.setEnhanceEmail ( results.getString ("enhance_email"));
            if (results.getObject ("photo_count") != null)
                transAd.setPhotoCount (new Integer(results.getInt ("photo_count")));
            if (results.getString ("photo_ref") != null)
                transAd.setPhotoRef ( results.getString ("photo_ref"));
            if (results.getString ("logo_ref") != null)
                transAd.setLogoRef ( results.getString ("logo_ref"));
            if (results.getString ("printad_img") != null)
                transAd.setPrintAdImage ( results.getString ("printad_img"));
            if (results.getString ("contact_phone") != null)
                transAd.setContactPhone ( results.getString ("contact_phone"));
            if (results.getObject ("print_desc") != null)
                transAd.setPrintDesc ( clobToString((oracle.sql.CLOB)results.getObject("print_desc")));
            if (results.getObject ("enhanced_desc") != null)
                transAd.setEnhancedDesc ( clobToString((oracle.sql.CLOB)results.getObject("enhanced_desc")));
            if (results.getString ("insertionID") != null)
                transAd.setInsertionID ( results.getString ("insertionID"));
            if (results.getString ("TYPE") != null)
                transAd.setType ( results.getString ("TYPE"));
            if (results.getString ("ITEM_ZIP") != null)
                transAd.setItemZip ( results.getString ("ITEM_ZIP"));
            if (results.getString ("LENGTH") != null)
                transAd.setLength ( results.getString ("LENGTH"));
            if (results.getString ("MAKE") != null)
                transAd.setMake ( results.getString ("MAKE"));
            if (results.getString ("MODEL") != null)
                transAd.setModel ( results.getString ("MODEL"));
            if (results.getString ("YEAR") != null)
                transAd.setYear (results.getString ("YEAR"));
            if (results.getString ("MILEAGE") != null)
                transAd.setMileage ( results.getString ("MILEAGE"));
            if (results.getString ("PRICE") != null)
                transAd.setPrice (results.getString ("PRICE"));
            if (results.getString ("BODY_STYLE") != null)
                transAd.setBodyStyle ( results.getString ("BODY_STYLE"));
            if (results.getString ("EXTERIOR_COLOR") != null)
                transAd.setExteriorColor ( results.getString ("EXTERIOR_COLOR"));
            if (results.getString ("VEHICLE_ZIP") != null)
                transAd.setVehicleZip ( results.getString ("VEHICLE_ZIP"));
            if (results.getString ("INTERIOR_COLOR") != null)
                transAd.setInteriorColor ( results.getString ("INTERIOR_COLOR"));
            if (results.getString ("CONDITION") != null)
                transAd.setCondition ( results.getString ("CONDITION"));
            if (results.getString ("TRANSMISSION") != null)
                transAd.setTransmission ( results.getString ("TRANSMISSION"));
            if (results.getString ("TRIM") != null)
                transAd.setTrim ( results.getString ("TRIM"));
            if (results.getString ("ENGINE") != null)
                transAd.setEngine ( results.getString ("ENGINE"));
            if (results.getString ("CONTACT_NAME") != null)
                transAd.setContactName ( results.getString ("CONTACT_NAME"));
            if (results.getObject ("DOORS") != null)
                transAd.setDoors (new Integer(results.getInt ("DOORS")));
            if (results.getString ("DRIVE_TRAIN") != null)
                transAd.setDriveTrain ( results.getString ("DRIVE_TRAIN"));
            if (results.getObject ("DEALER_ID") != null)
                transAd.setDealerID (new Integer(results.getInt ("DEALER_ID")));
            if (results.getString ("STOCK_NUMBER") != null)
                transAd.setStockNumber ( results.getString ("STOCK_NUMBER"));
            if (results.getString ("VIN") != null)
                transAd.setVin ( results.getString ("VIN"));
            if (results.getString ("LICENSE_PLATE_NO") != null)
                transAd.setLicensePlateNumber ( results.getString ("LICENSE_PLATE_NO"));
            if (results.getString ("WEBID") != null)
                transAd.setWebID ( results.getString ("WEBID"));
            if (results.getObject ("OBO") != null)
                transAd.setoBO (new Integer(results.getInt ("OBO")));
            if (results.getObject ("CURRENCY") != null)
                transAd.setCurrency ( results.getString ("CURRENCY"));
            if (results.getObject ("AC_FRONT") != null)
                transAd.setAcFront (new Integer(results.getInt ("AC_FRONT")));
            if (results.getObject ("AC_REAR") != null)
                transAd.setAcRear (new Integer(results.getInt ("AC_REAR")));
            if (results.getObject ("CRUISE_CONTROL") != null)
                transAd.setCruiseControl (new Integer(results.getInt ("CRUISE_CONTROL")));
            if (results.getObject ("NAVIGATION") != null)
                transAd.setNavigation (new Integer(results.getInt ("NAVIGATION")));
            if (results.getObject ("POWER_LOCKS") != null)
                transAd.setPowerLocks (new Integer(results.getInt ("POWER_LOCKS")));
            if (results.getObject ("POWER_STEERING") != null)
                transAd.setPowerSteering (new Integer(results.getInt ("POWER_STEERING")));
            if (results.getObject ("KEYLESS_ENTRY") != null)
                transAd.setKeylessEntry (new Integer(results.getInt ("KEYLESS_ENTRY")));
            if (results.getObject ("TV_VCR_DVD") != null)
                transAd.setTvVcrDvd (new Integer(results.getInt ("TV_VCR_DVD")));
            if (results.getObject ("BUCKET_SEATS") != null)
                transAd.setBucketSeats (new Integer(results.getInt ("BUCKET_SEATS")));
            if (results.getObject ("LEATHER_INTERIOR") != null)
                transAd.setLeatherInterior (new Integer(results.getInt ("LEATHER_INTERIOR")));
            if (results.getObject ("MEMORY_SEATS") != null)
                transAd.setMemorySeats(new Integer(results.getInt ("MEMORY_SEATS")));
            if (results.getObject ("POWER_SEATS") != null)
                transAd.setPowerSeats (new Integer(results.getInt ("POWER_SEATS")));
            if (results.getObject ("DRIVER_AIRBAG") != null)
                transAd.setDriverAirbag (new Integer(results.getInt ("DRIVER_AIRBAG")));
            if (results.getObject ("PASSENGER_AIRBAG") != null)
                transAd.setPassengerAirbag (new Integer(results.getInt ("PASSENGER_AIRBAG")));
            if (results.getObject ("SIDE_AIRBAG") != null)
                transAd.setSideAirbag (new Integer(results.getInt ("SIDE_AIRBAG")));
            if (results.getObject ("ALARM") != null)
                transAd.setAlarm (new Integer(results.getInt ("ALARM")));
            if (results.getObject ("ANTI_LOCK_BRAKES") != null)
                transAd.setAntiLockBrakes (new Integer(results.getInt ("ANTI_LOCK_BRAKES")));
            if (results.getObject ("FOGLIGHTS") != null)
                transAd.setFoglights (new Integer(results.getInt ("FOGLIGHTS")));
            if (results.getObject ("CASSETTE_PLAYER") != null)
                transAd.setCassettePlayer (new Integer(results.getInt ("CASSETTE_PLAYER")));
            if (results.getObject ("CD_CHANGER") != null)
                transAd.setCdChanger (new Integer(results.getInt ("CD_CHANGER")));
            if (results.getObject ("CD_PLAYER") != null)
                transAd.setCdPlayer (new Integer(results.getInt ("CD_PLAYER")));
            if (results.getObject ("PREMIUM_SOUND") != null)
                transAd.setPremiumSound (new Integer(results.getInt ("PREMIUM_SOUND")));
            if (results.getObject ("POWER_WINDOWS") != null)
                transAd.setPowerWindows (new Integer(results.getInt ("POWER_WINDOWS")));
            if (results.getObject ("REAR_WIN_DEFOGGER") != null)
                transAd.setRearWinDefogger (new Integer(results.getInt ("REAR_WIN_DEFOGGER")));
            if (results.getObject ("REAR_WIN_WIPER") != null)
                transAd.setRearWinWiper (new Integer(results.getInt ("REAR_WIN_WIPER")));
            if (results.getObject ("TINTED_GLASS") != null)
                transAd.setTintedGlass (new Integer(results.getInt ("TINTED_GLASS")));
            if (results.getObject ("ALLOY_WHEELS") != null)
                transAd.setAlloyWheels (new Integer(results.getInt ("ALLOY_WHEELS")));
            if (results.getObject ("SUNROOF") != null)
                transAd.setSunroof (new Integer(results.getInt ("SUNROOF")));
            if (results.getObject ("MOONROOF") != null)
                transAd.setMoonroof (new Integer(results.getInt ("MOONROOF")));
            if (results.getObject ("THIRD_ROW_SEATS") != null)
                transAd.setThirdRowSeats (new Integer(results.getInt ("THIRD_ROW_SEATS")));
            if (results.getObject ("TOW_PACKAGE") != null)
                transAd.setTowPackage (new Integer(results.getInt ("TOW_PACKAGE")));
        }
        catch (Exception e) {
            Logger.log("Error populating CCI Transport ad " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error populating CCI Transport ad " + e.toString());
            throw ctException;
        }

        return transAd;
    }


    /**
     * Stores edit search results values into ArrayList of CCI_VitalAd Objects
     */
    private CCI_Ad populateVitalAd (ResultSet results) throws ClassifiedToolException {

        CCI_VitalAd vitalAd = new CCI_VitalAd();
        vitalAd.setTableName(Constants.CCI_VITALNOTICE_TABLE_NAME);

        try {
            results.next();

            if (results.getInt ("feedID") != 0)
                vitalAd.setFeedID (new Integer(results.getInt ("feedID")));
            if (results.getString ("FEEDNAME") != null)
                vitalAd.setFeedName ( results.getString ("feedName"));
            if (results.getString ("adID") != null)
                vitalAd.setAdID ( results.getString ("adID"));
            if (results.getDate ("dateReceived") != null)
                vitalAd.setDateReceived ( results.getTimestamp ("dateReceived"));
            if (results.getDate ("firstReceived") != null)
                vitalAd.setFirstReceived ( results.getTimestamp ("firstReceived"));
            if (results.getDate ("dateUpdated") != null)
                vitalAd.setDateUpdated ( results.getTimestamp ("dateUpdated"));
            if (results.getString ("publication") != null)
                vitalAd.setPublication ( results.getString ("publication"));
            if (results.getString ("commercialrelease") != null)
                vitalAd.setCommercialRelease ( results.getString ("commercialrelease"));
            if (results.getObject ("canceled") != null)
                vitalAd.setCanceled (new Integer(results.getInt ("canceled")));
            if (results.getString ("cust_city") != null)
                vitalAd.setCustomerCity ( results.getString ("cust_city"));
            if (results.getString ("cust_state") != null)
                vitalAd.setCustomerState ( results.getString ("cust_state"));
            if (results.getString ("cust_zip") != null)
                vitalAd.setCustomerZip ( results.getString ("cust_zip"));
            if (results.getString ("cust_email") != null)
                vitalAd.setCustomerEmail( results.getString ("cust_email"));
            if (results.getString ("class") != null)
                vitalAd.setClassID ( results.getString ("class"));
            if (results.getDate ("startDate") != null)
                vitalAd.setStartDate ( results.getDate ("startDate"));
            if (results.getDate ("stopDate") != null)
                vitalAd.setStopDate ( results.getDate ("stopDate"));
            if (results.getString ("title") != null)
                vitalAd.setTitle ( results.getString ("title"));
            if (results.getString ("upsell") != null)
                vitalAd.setUpsell ( results.getString ("upsell"));
            if (results.getString ("contact_email") != null)
                vitalAd.setContactEmail ( results.getString ("contact_email"));
            if (results.getString ("enhance_email") != null)
                vitalAd.setEnhanceEmail ( results.getString ("enhance_email"));
            if (results.getString ("photo_count") != null)
                vitalAd.setPhotoCount (new Integer(results.getInt ("photo_count")));
            if (results.getString ("photo_ref") != null)
                vitalAd.setPhotoRef ( results.getString ("photo_ref"));
            if (results.getString ("logo_ref") != null)
                vitalAd.setLogoRef ( results.getString ("logo_ref"));
            if (results.getString ("printad_img") != null)
                vitalAd.setPrintAdImage ( results.getString ("printad_img"));
            if (results.getString ("contact_phone") != null)
                vitalAd.setContactPhone ( results.getString ("contact_phone"));
            if (results.getObject ("print_desc") != null)
                vitalAd.setPrintDesc ( clobToString((oracle.sql.CLOB)results.getObject("print_desc")));
            if (results.getObject ("enhanced_desc") != null)
                vitalAd.setEnhancedDesc ( clobToString((oracle.sql.CLOB)results.getObject("enhanced_desc")));
            if (results.getString ("insertionID") != null)
                vitalAd.setInsertionID ( results.getString ("insertionID"));
            if (results.getString ("FIRST_NAME") != null)
                vitalAd.setFirstName ( results.getString ("FIRST_NAME"));
            if (results.getString ("ZIP") != null)
                vitalAd.setZip ( results.getString ("ZIP"));
            if (results.getString ("LAST_NAME") != null)
                vitalAd.setLastName ( results.getString ("LAST_NAME"));
            if (results.getString ("AGE") != null)
                vitalAd.setAge ( results.getString ("AGE"));
            if (results.getString ("CITY") != null)
                vitalAd.setCity ( results.getString ("CITY"));
            if (results.getString ("NEIGHBORHOOD") != null)
                vitalAd.setNeighborhood ( results.getString ("NEIGHBORHOOD"));
            if (results.getString ("STATE") != null)
                vitalAd.setState ( results.getString ("STATE"));
            if (results.getString ("DATE_DEATH") != null)
                vitalAd.setDateDeath ( results.getString ("DATE_DEATH"));
            if (results.getString ("DATE_BIRTH") != null)
                vitalAd.setDateBirth ( results.getString ("DATE_BIRTH"));
            if (results.getString ("STATE") != null)
                vitalAd.setState ( results.getString ("STATE"));
            if (results.getString ("FUNERAL_HOME") != null)
                vitalAd.setFuneralHome ( results.getString ("FUNERAL_HOME"));
        }
        catch (Exception e) {
            Logger.log("Error populating CCI Vital Notice ad " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error populating CCI Vital Notice ad " + e.toString());
            throw ctException;
        }

        return vitalAd;
    }


    /**
     * Stores edit search results values into ArrayList of Recycler Objects
     */
    private RecyclerAd populateRecyclerAd (ResultSet results) throws ClassifiedToolException {

        RecyclerAd recyAd = new RecyclerAd();
        recyAd.setTableName(Constants.RECYCLER_TABLE_NAME);

        try {
            results.next();

            if (results.getObject("FEEDID") != null) {
                recyAd.setFeedID(new Integer(results.getInt("FEEDID")));
            }
            if (results.getString("ADID") != null) {
                recyAd.setAdID(results.getString("ADID"));
            }
            if (results.getString("CLASS") != null) {
                recyAd.setClassID(results.getString("CLASS"));
            }
            if (results.getString("PARENTCLASS") != null) {
                recyAd.setParentClass(results.getString("PARENTCLASS"));
            }
            if (results.getDate("STARTDATE") != null) {
                recyAd.setStartDate(results.getDate("STARTDATE"));
            }
            if (results.getDate("ENDDATE") != null) {
                recyAd.setEndDate(results.getDate("ENDDATE"));
            }
            if (results.getDate("DATERECEIVED") != null) {
                recyAd.setDateReceived(results.getTimestamp("DATERECEIVED"));
            }
            if (results.getDate("DATEUPDATED") != null) {
                recyAd.setDateUpdated(results.getTimestamp("DATEUPDATED"));
            }
            if (results.getObject("CUS_ID") != null) {
                recyAd.setCustomerID(new Integer(results.getInt("CUS_ID")));
            }
            if (results.getObject("COST") != null) {
                recyAd.setCost(new Double(results.getDouble("COST")));
            }
            if (results.getObject("IS_ONLINE") != null) {
                recyAd.setIsOnline(new Integer(results.getInt("IS_ONLINE")));
            }
            if (results.getString("AREACODE") != null) {
                recyAd.setAreaCode(results.getString("AREACODE"));
            }
            if (results.getString("PHONE") != null) {
                recyAd.setPhone(results.getString("PHONE"));
            }
            if (results.getString("LOCATION") != null) {
                recyAd.setLocation(results.getString("LOCATION"));
            }
            if (results.getString("EMAIL") != null) {
                recyAd.setEmail(results.getString("EMAIL"));
            }
            if (results.getString("URL") != null) {
                recyAd.setUrl(results.getString("URL"));
            }
            if (results.getObject("PRICE") != null) {
                recyAd.setPrice(new Double(results.getDouble("PRICE")));
            }
            if (results.getObject("YEAR") != null) {
                recyAd.setYear(new Integer(results.getInt("YEAR")));
            }
            if (results.getString("VIN") != null) {
                recyAd.setVin(results.getString("VIN"));
            }
            if (results.getObject("WANTED") != null) {
                recyAd.setWanted(new Integer(results.getInt("WANTED")));
            }
            if (results.getString("PUBLICATION") != null) {
                recyAd.setPublication(results.getString("PUBLICATION"));
            }
            if (results.getString("ZIP") != null) {
                recyAd.setZip(results.getString("ZIP"));
            }
            if (results.getString("SORTTEXT") != null) {
                recyAd.setSortText(results.getString("SORTTEXT"));
            }
            if (results.getString("PHOTONAME") != null) {
                recyAd.setPhotoName(results.getString("PHOTONAME"));
            }
            if (results.getString("PHOTONAME1") != null) {
                recyAd.setPhotoName1(results.getString("PHOTONAME1"));
            }
            if (results.getString("PHOTONAME2") != null) {
                recyAd.setPhotoName2(results.getString("PHOTONAME2"));
            }
            if (results.getString("PHOTONAME3") != null) {
                recyAd.setPhotoName3(results.getString("PHOTONAME3"));
            }
            if (results.getString("PHOTONAME4") != null) {
                recyAd.setPhotoName4(results.getString("PHOTONAME4"));
            }
            if (results.getString("PHOTONAME5") != null) {
                recyAd.setPhotoName5(results.getString("PHOTONAME5"));
            }
            if (results.getString("TITLE") != null) {
                recyAd.setTitle(results.getString("TITLE"));
            }
            if (results.getObject("CONTENT") != null) {
                recyAd.setContent(clobToString((oracle.sql.CLOB)results.getObject("CONTENT")));
            }
            if (results.getObject("EXTRATEXT") != null) {
                recyAd.setExtraText(clobToString((oracle.sql.CLOB)results.getObject("EXTRATEXT")));
            }
            if (results.getObject("TYPE") != null) {
                recyAd.setType(new Integer(results.getInt("TYPE")));
            }
            if (results.getObject("CONTACTNAME") != null) {
                recyAd.setContactName(results.getString("CONTACTNAME"));
            }
            if (results.getString("MAKE") != null) {
                recyAd.setMake(results.getString("MAKE"));
            }
            if (results.getString("MODEL") != null) {
                recyAd.setModel(results.getString("MODEL"));
            }
            if (results.getObject("INSERTIONS") != null) {
                recyAd.setInsertions(new Integer(results.getInt("INSERTIONS")));
            }
            if (results.getString("WEBPHONE1") != null) {
                recyAd.setWebPhone1(results.getString("WEBPHONE1"));
            }
            if (results.getString("WEBPHONE2") != null) {
                recyAd.setWebPhone2(results.getString("WEBPHONE2"));
            }
            if (results.getString("ZONES") != null) {
                recyAd.setZones(results.getString("ZONES"));
            }
            if (results.getObject("DURATION") != null) {
                recyAd.setDuration(new Integer(results.getString("DURATION")));
            }
            if (results.getString("TRIM") != null) {
                recyAd.setTrim(results.getString("TRIM"));
            }
            if (results.getString("MILEAGE") != null) {
                recyAd.setMileage(results.getString("MILEAGE"));
            }
            if (results.getString("ADFORMAT") != null) {
                recyAd.setAdFormat(results.getString("ADFORMAT"));
            }
            if (results.getString("ADLAYOUT") != null) {
                recyAd.setAdLayout(results.getString("ADLAYOUT"));
            }

        }
        catch (Exception e) {
            Logger.log("Error populating Recycler ad " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error populating Recycler ad " + e.toString());
            throw ctException;
        }

        return recyAd;
    }


    /**
     * Function used to convert CLOB values in Database to strings for CCI_Ad related objects
     */
    public static String clobToString(oracle.sql.CLOB cl) throws Exception {
        BufferedReader reader = null;
        int chars = 0;
        StringBuffer result = new StringBuffer();
        char[] buf = new char[cl.getChunkSize()];

        reader = new BufferedReader(cl.getCharacterStream());
        while ((chars = reader.read(buf)) != -1) {
            result.append(buf, 0, chars);
        }
        return result.toString();
    }



    /**
     * Called from the editAd.jsp page
     * Updates CCI/Recycler ad values
     */
    public int updateAd(CCI_Ad ad) {

        String successMessage = "The update was successful.";
        int count = 0;

        try {
            // Generate SQL Statement
            String sql = getUpdateStatement(ad.getTableName());
            sql += "where adid = '" + ad.getAdID() + "'\n";
            sql += "and feedid = '" + ad.getFeedID() + "'";

            // Set Values into Prepared Statement
            PreparedStatement updateStmt = conn.prepareStatement(sql);
            setUpdateStatementValuesFromAd(updateStmt, ad);

            // Turn off Auto Commit for CLOBs and Execute Update
            boolean autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            count = updateStmt.executeUpdate();

            Statement clobStmt = conn.createStatement();

            if (!Constants.RECYCLER_TABLE_NAME.equals(ad.getTableName())) {

                // Put PRINT_DESC, ENHANCED_DESC CLOB data into empty CLOB field
                String printDesc_stmt = "select print_desc from " + ad.getTableName() + " where adid = '" + ad.getAdID() + "' and FEEDID = " + ad.getFeedID();
                String enhancedDesc_stmt = "select enhanced_desc from " + ad.getTableName() + " where ADID = '" + ad.getAdID() + "' and FEEDID = " + ad.getFeedID();

                ResultSet rset = clobStmt.executeQuery(printDesc_stmt);
                while (rset.next()) {
                    CLOB c1 = (CLOB) rset.getObject(1);
                    c1.putString(1, ad.getPrintDesc());
                }
                rset.close();
                rset = clobStmt.executeQuery(enhancedDesc_stmt);
                while (rset.next()) {
                    CLOB c2 = (CLOB) rset.getObject(1);
                    c2.putString(1, ad.getEnhancedDesc());
                }
                rset.close();
            }
            else {
                // Put CONTENT, EXTRATEXT CLOB data into empty CLOB field
                String content_stmt = "select content from " + ad.getTableName() + " where adid = '" + ad.getAdID() + "' and FEEDID = " + ad.getFeedID();
                String extraText_stmt = "select extratext from " + ad.getTableName() + " where ADID = '" + ad.getAdID() + "' and FEEDID = " + ad.getFeedID();

                ResultSet rset = clobStmt.executeQuery(content_stmt);
                while (rset.next()) {
                    CLOB c3 = (CLOB) rset.getObject(1);
                    c3.putString(1, ad.getContent());
                }
                rset.close();
                rset = clobStmt.executeQuery(extraText_stmt);
                while (rset.next()) {
                    CLOB c4 = (CLOB) rset.getObject(1);
                    c4.putString(1, ad.getExtraText());
                }
                rset.close();
            }

            conn.commit();

            // Return Auto Commit back to original value
            conn.setAutoCommit(autocommit);

            Logger.log("Updated " + count + " record: Ad ID " + ad.getAdID());
        } catch (Exception e) {
            Logger.log("Error: Update of Ad ID " + ad.getAdID() + " failed: " + e);
            successMessage = "The update failed.<br>";
            successMessage += e;
        } finally {
            ad.setDataResponseMessage(successMessage);
        }

        if (successMessage.indexOf("failed") == -1) {
            //we already successfully set this to a new date in the prepared statement,
            //just need to add it into the model to pass back to the jsp view
            ad.setDateUpdated(new Date());
        }
        return count;
    }

    /**
     * Returns current update statements for all CCI and Recycler ad tables
     */
    private String getUpdateStatement(String tableName) {

        String updateStatement =
                "update " + tableName + "\n" +
                "set dateupdated = ?,\n" +
                "publication = ?,\n" +
                "commercialrelease = ?,\n" +
                "canceled = ?,\n" +
                "cust_city = ?,\n" +
                "cust_state = ?,\n" +
                "cust_zip = ?,\n" +
                "cust_email = ?,\n" +
                "class = ?,\n" +
                "startdate = ?,\n" +
                "stopdate = ?,\n" +
                "title = ?,\n" +
                "upsell = ?,\n" +
                "contact_email = ?,\n" +
                "enhance_email = ?,\n" +
                "photo_count = ?,\n" +
                "photo_ref = ?,\n" +
                "logo_ref = ?,\n" +
                "printad_img = ?,\n" +
                "print_desc = empty_clob(),\n" +
                "enhanced_desc = empty_clob(),\n" +
                "contact_phone = ?,\n";

        if (Constants.CCI_APARTMENTS_TABLE_NAME.equalsIgnoreCase(tableName)) {
            updateStatement +=
                    "property_type = ?,\n" +
                    "property_city = ?,\n" +
                    "property_state = ?,\n" +
                    "property_zip = ?,\n" +
                    "price = ?,\n" +
                    "bedrooms = ?,\n" +
                    "baths = ?,\n" +
                    "property_address = ?,\n" +
                    "neighborhood = ?,\n" +
                    "country = ?,\n" +
                    "webId = ?,\n" +
                    "openhse_date1 = ?,\n" +
                    "openhse_starttm1 = ?,\n" +
                    "openhse_endtm1 = ?,\n" +
                    "openhse_date2 = ?,\n" +
                    "openhse_starttm2 = ?,\n" +
                    "openhse_endtm2 = ?,\n" +
                    "mls_number = ?,\n" +
                    "sq_ft = ?,\n" +
                    "air_condition = ?,\n" +
                    "basement = ?,\n" +
                    "dishwasher = ?,\n" +
                    "elevator = ?,\n" +
                    "fireplace = ?,\n" +
                    "fitness_room = ?,\n" +
                    "dining_room = ?,\n" +
                    "family_room = ?,\n" +
                    "laundry = ?,\n" +
                    "other_parking = ?,\n" +
                    "garage_parking = ?,\n" +
                    "pets = ?,\n" +
                    "pool = ?,\n" +
                    "security = ?,\n" +
                    "agent_url = ?\n";
        }
        else if (Constants.CCI_GENCLASSIFIED_TABLE_NAME.equalsIgnoreCase(tableName)) {
            updateStatement +=
                    "type = ?,\n" +
                    "zip = ?,\n" +
                    "price = ?,\n" +
                    "country = ?,\n" +
                    "obo = ?,\n" +
                    "address = ?,\n" +
                    "city = ?,\n" +
                    "state = ?,\n" +
                    "breed = ?,\n" +
                    "advertiser_url = ?,\n" +
                    "event_start_time = ?,\n" +
                    "event_end_time = ?,\n" +
                    "event_date = ?,\n" +
                    "color = ?, \n" +
                    "gender = ?,\n" +
                    "age = ?,\n" +
                    "firstname1 = ?,\n" +
                    "lastname1 = ?,\n" +
                    "firstname2 = ?,\n" +
                    "lastname2 = ?,\n" +
                    "celebration_type = ?\n";
        }
        else if (Constants.CCI_JOBS_TABLE_NAME.equalsIgnoreCase(tableName)) {
            updateStatement +=
                    //"dupDate = ?,\n" +
                    "job_title = ?,\n" +
                    "job_type_code = ?,\n" +
                    "job_loc_zip = ?,\n" +
                    "work_type = ?,\n" +
                    "company_name = ?,\n" +
                    "contact_name = ?,\n" +
                    "contact_fax = ?,\n" +
                    "job_loc_city = ?,\n" +
                    "job_loc_state = ?,\n" +
                    "job_loc_country = ?,\n" +
                    "education = ?,\n" +
                    "experience = ?,\n" +
                    "travel = ?,\n" +
                    "base_pay_per = ?,\n" +
                    "base_pay_low = ?,\n" +
                    "base_pay_high = ?,\n" +
                    "commission = ?,\n" +
                    "bonus = ?,\n" +
                    "other_pay = ?,\n" +
                    "company_url = ?,\n" +
                    "apply_url = ?,\n" +
                    "industry_code1 = ?,\n" +
                    "industry_code2 = ?,\n" +
                    "industry_code3 = ?,\n" +
                    "webID = ?,\n" +
                    "original_ad_id = ?,\n" +
                    "voice_type = ?,\n" +
                    "company_video_provided = ?,\n " +
                    "moredetails_url = ?,\n" +
                    "video_notes = ?,\n" +
                    "video_search_keywords = ?";
        }
        else if (Constants.CCI_REALESTATE_TABLE_NAME.equalsIgnoreCase(tableName)) {
            updateStatement +=
                    "sale_lease = ?,\n" +
                    "price_per_sq_ft = ?,\n" +
                    "property_type = ?,\n" +
                    "property_city = ?,\n" +
                    "property_state = ?,\n" +
                    "property_zip = ?,\n" +
                    "price = ?,\n" +
                    "bedrooms = ?,\n" +
                    "baths = ?,\n" +
                    "property_address = ?,\n" +
                    "neighborhood = ?,\n" +
                    "country = ?,\n" +
                    "openhse_date1 = ?,\n" +
                    "openhse_starttm1 = ?,\n" +
                    "openhse_endtm1 = ?,\n" +
                    "openhse_date2 = ?,\n" +
                    "openhse_starttm2 = ?,\n" +
                    "openhse_endtm2 = ?,\n" +
                    "mls_number = ?,\n" +
                    "sq_ft = ?,\n" +
                    "air_condition = ?,\n" +
                    "basement = ?,\n" +
                    "dishwasher = ?,\n" +
                    "elevator = ?,\n" +
                    "fireplace = ?,\n" +
                    "fitness_room = ?,\n" +
                    "dining_room = ?,\n" +
                    "family_room = ?,\n" +
                    "laundry = ?,\n" +
                    "other_parking = ?,\n" +
                    "garage_parking = ?,\n" +
                    "pets = ?,\n" +
                    "pool = ?,\n" +
                    "security = ?,\n" +
                    "webID = ?,\n" +
                    "agent_url = ?\n";
        }
        else if (Constants.CCI_TRANSPORT_TABLE_NAME.equalsIgnoreCase(tableName)) {
            updateStatement +=
                    "type = ?,\n" +
                    "item_zip = ?,\n" +
                    "length = ?,\n" +
                    "make = ?,\n" +
                    "model = ?,\n" +
                    "year = ?,\n" +
                    "mileage = ?,\n" +
                    "price = ?,\n" +
                    "body_style = ?,\n" +
                    "exterior_color = ?,\n" +
                    "vehicle_zip = ?,\n" +
                    "interior_color = ?,\n" +
                    "condition = ?,\n" +
                    "transmission = ?,\n" +
                    "trim = ?,\n" +
                    "engine = ?,\n" +
                    "contact_name = ?,\n" +
                    "doors = ?,\n" +
                    "obo = ?,\n" +
                    "drive_train = ?,\n" +
                    "dealer_id = ?,\n" +
                    "stock_number = ?,\n" +
                    "vin = ?,\n" +
                    "license_plate_no = ?,\n" +
                    "webid = ?,\n" +
                    "CURRENCY = ?,\n" +
                    "AC_FRONT = ?,\n" +
                    "AC_REAR = ?,\n" +
                    "CRUISE_CONTROL = ?,\n" +
                    "NAVIGATION = ?,\n" +
                    "POWER_LOCKS = ?,\n" +
                    "POWER_STEERING = ?,\n" +
                    "KEYLESS_ENTRY = ?,\n" +
                    "TV_VCR_DVD = ?,\n" +
                    "BUCKET_SEATS = ?,\n" +
                    "LEATHER_INTERIOR = ?,\n" +
                    "MEMORY_SEATS = ?,\n" +
                    "POWER_SEATS = ?,\n" +
                    "DRIVER_AIRBAG = ?,\n" +
                    "PASSENGER_AIRBAG = ?,\n" +
                    "SIDE_AIRBAG = ?,\n" +
                    "ALARM = ?,\n" +
                    "ANTI_LOCK_BRAKES = ?,\n" +
                    "FOGLIGHTS = ?,\n" +
                    "CASSETTE_PLAYER = ?,\n" +
                    "CD_CHANGER = ?,\n" +
                    "CD_PLAYER = ?,\n" +
                    "PREMIUM_SOUND = ?,\n" +
                    "POWER_WINDOWS = ?,\n" +
                    "REAR_WIN_DEFOGGER = ?,\n" +
                    "REAR_WIN_WIPER = ?,\n" +
                    "TINTED_GLASS = ?,\n" +
                    "ALLOY_WHEELS = ?,\n" +
                    "SUNROOF = ?,\n" +
                    "MOONROOF = ?,\n" +
                    "THIRD_ROW_SEATS = ?,\n" +
                    "TOW_PACKAGE = ?";
        }
        else if (Constants.CCI_VITALNOTICE_TABLE_NAME.equalsIgnoreCase(tableName)) {
            updateStatement +=
                    "first_name = ?,\n" +
                    "last_name = ?,\n" +
                    "zip = ?,\n" +
                    "age = ?,\n" +
                    "city = ?,\n" +
                    "neighborhood = ?,\n" +
                    "state = ?,\n" +
                    "date_death = ?,\n" +
                    "date_birth = ?,\n" +
                    "funeral_home = ?\n";
        }
        else if (Constants.RECYCLER_TABLE_NAME.equals(tableName)) {
            updateStatement =
                    "update Recycler\n" +
                    "set class = ?,\n" +
                    "parentclass = ?,\n" +
                    "startdate = ?,\n" +
                    "enddate = ?,\n" +
                    "dateupdated = ?,\n" +
                    "cus_id = ?,\n" +
                    "cost = ?,\n" +
                    "is_online = ?,\n" +
                    "areacode = ?,\n" +
                    "phone = ?,\n" +
                    "location = ?,\n" +
                    "email = ?,\n" +
                    "url = ?,\n" +
                    "price = ?,\n" +
                    "year = ?,\n" +
                    "vin = ?,\n" +
                    "wanted = ?,\n" +
                    "publication = ?,\n" +
                    "zip = ?,\n" +
                    "sorttext = ?,\n" +
                    "photoname = ?,\n" +
                    "photoname1 = ?,\n" +
                    "photoname2 = ?,\n" +
                    "photoname3 = ?,\n" +
                    "photoname4 = ?,\n" +
                    "photoname5 = ?,\n" +
                    "title = ?,\n" +
                    "content = empty_clob(),\n" +
                    "extratext = empty_clob(),\n" +
                    "contactname = ?,\n" +
                    "make = ?,\n" +
                    "model = ?,\n" +
                    "insertions = ?,\n" +
                    "webphone1 = ?,\n" +
                    "webphone2 = ?,\n" +
                    "zones = ?,\n" +
                    "duration = ?,\n" +
                    "trim = ?,\n" +
                    "mileage = ?\n";
        }
        return updateStatement;
    }

    /**
     * Sets the values from CCI_Ad object into the update statement getUpdateStatement()
     */
    private void setUpdateStatementValuesFromAd(PreparedStatement preparedStmt,  CCI_Ad ad) {
        try {
            /* Added for CR29807 - Start*/
            /* The following code will change the format of contact phone number
            ** from xxx-xxx-xxxx to xxxxxxxxxx and store it in database only for
            ** Orlando Sentinel
            **/
            String ContactPhone = ad.getContactPhone();
            Integer  FeedID = ad.getFeedID();
            int ad_FeedID =  FeedID.intValue();
            if (ad_FeedID == 303)
            {
                 StringTokenizer Tok = new StringTokenizer(ContactPhone,"-");
                 ContactPhone ="";
                 while (Tok.hasMoreElements())
                 {
                     ContactPhone = ContactPhone + Tok.nextElement();
                 }
             }
            else
            {
                 ContactPhone = ad.getContactPhone();
            }
            /* Added for CR29807 - End */

            if (!Constants.RECYCLER_TABLE_NAME.equals(ad.getTableName())) {
                preparedStmt.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
                preparedStmt.setString(2, ad.getPublication());
                preparedStmt.setString(3, ad.getCommercialRelease());
                preparedStmt.setObject(4, ad.getCanceled(), java.sql.Types.INTEGER);
                preparedStmt.setString(5, ad.getCustomerCity());
                preparedStmt.setString(6, ad.getCustomerState());
                preparedStmt.setString(7, ad.getCustomerZip());
                preparedStmt.setString(8, ad.getCustomerEmail());
                preparedStmt.setString(9, ad.getClassID());
                preparedStmt.setDate(10, ad.getStartDate() == null ? null : new java.sql.Date(ad.getStartDate().getTime()));
                preparedStmt.setDate(11, ad.getStopDate() == null ? null : new java.sql.Date(ad.getStopDate().getTime()));
                preparedStmt.setString(12, ad.getTitle());
                preparedStmt.setString(13, ad.getUpsell());
                preparedStmt.setString(14, ad.getContactEmail());
                preparedStmt.setString(15, ad.getEnhanceEmail());
                preparedStmt.setObject(16, ad.getPhotoCount(), java.sql.Types.INTEGER);
                preparedStmt.setString(17, ad.getPhotoRef());
                preparedStmt.setString(18, ad.getLogoRef());
                preparedStmt.setString(19, ad.getPrintAdImage());
                preparedStmt.setString(20, ContactPhone);
            }
            if (Constants.CCI_APARTMENTS_TABLE_NAME.equalsIgnoreCase(ad.getTableName())) {
                CCI_AptAd aptAd = (CCI_AptAd)ad;

                // PROPERTY_TYPE
                preparedStmt.setString(21, aptAd.getPropertyType());
                // PROPERTY_CITY
                preparedStmt.setString(22, aptAd.getPropertyCity());
                // PROPERTY_STATE
                preparedStmt.setString(23, aptAd.getPropertyState());
                // PROPERTY_ZIP
                preparedStmt.setString(24, aptAd.getPropertyZip());
                // PRICE
                preparedStmt.setString(25, aptAd.getPrice());
                // BEDROOMS
                preparedStmt.setString(26, aptAd.getBedrooms());
                // BATHS
                preparedStmt.setString(27, aptAd.getBaths());
                // PROPERTY_ADDRESS
                preparedStmt.setString(28, aptAd.getPropertyAddress());
                // NEIGHBORHOOD
                preparedStmt.setString(29, aptAd.getNeighborhood());
                // COUNTRY
                preparedStmt.setString(30, aptAd.getCountry());
                // WEBID
                preparedStmt.setString(31, aptAd.getWebID());
                // OPENHSE_DATE1
                preparedStmt.setDate(32, aptAd.getOpenHouseDate1() == null ? null : new java.sql.Date(aptAd.getOpenHouseDate1().getTime()));
                // OPENHSE_STARTTM1
                preparedStmt.setString(33, aptAd.getOpenHouseStartTime1());
                // OPENHSE_ENDTM1
                preparedStmt.setString(34, aptAd.getOpenHouseEndTime1());
                // OPENHSE_DATE2
                preparedStmt.setDate(35, aptAd.getOpenHouseDate2() == null ? null : new java.sql.Date(aptAd.getOpenHouseDate2().getTime()));
                // OPENHSE_STARTTM2
                preparedStmt.setString(36, aptAd.getOpenHouseStartTime2());
                // OPENHSE_ENDTM2
                preparedStmt.setString(37, aptAd.getOpenHouseEndTime2());
                // MLS_NUMBER
                preparedStmt.setString(38, aptAd.getMlsNumber());
                // SQ_FT
                preparedStmt.setString(39, aptAd.getSquareFeet());
                // AIR_CONDITION
                preparedStmt.setString(40, aptAd.getAirCondition());
                // BASEMENT
                preparedStmt.setString(41, aptAd.getBasement());
                // DISHWASHER
                preparedStmt.setString(42, aptAd.getDishwasher());
                // ELEVATOR
                preparedStmt.setString(43, aptAd.getElevator());
                // FIREPLACE
                preparedStmt.setString(44, aptAd.getFireplace());
                // FITNESS_ROOM
                preparedStmt.setString(45, aptAd.getFitnessRoom());
                // DINING_ROOM
                preparedStmt.setString(46, aptAd.getDiningRoom());
                // FAMILY_ROOM
                preparedStmt.setString(47, aptAd.getFamilyRoom());
                // LAUNDRY
                preparedStmt.setString(48, aptAd.getLaundry());
                // OTHER_PARKING
                preparedStmt.setString(49, aptAd.getOtherParking());
                // GARAGE_PARKING
                preparedStmt.setString(50, aptAd.getGarageParking());
                // PETS
                preparedStmt.setString(51, aptAd.getPets());
                // POOL
                preparedStmt.setString(52, aptAd.getPool());
                // SECURITY
                preparedStmt.setString(53, aptAd.getSecurity());
                // AGENT_URL
                preparedStmt.setString(54, aptAd.getAgentUrl());
           }
            else if (Constants.CCI_GENCLASSIFIED_TABLE_NAME.equalsIgnoreCase(ad.getTableName())) {
                CCI_GenAd genAd = (CCI_GenAd)ad;

                // TYPE
                preparedStmt.setString(21, genAd.getType());
                // ZIP
                preparedStmt.setString(22, genAd.getZip());
                // PRICE
                preparedStmt.setString(23, genAd.getPrice());
                // COUNTRY
                preparedStmt.setString(24, genAd.getCountry());
                // OBO
                preparedStmt.setObject(25, genAd.getoBO(), java.sql.Types.INTEGER);
                // ADDRESS
                preparedStmt.setString(26, genAd.getAddress());
                // CITY
                preparedStmt.setString(27, genAd.getCity());
                // STATE
                preparedStmt.setString(28, genAd.getState());
                // BREED
                preparedStmt.setString(29, genAd.getBreed());
                // ADVERTISER_URL
                preparedStmt.setString(30, genAd.getAdvertiserUrl());
                // EVENT_START_TIME
                preparedStmt.setString(31, genAd.getEventStartTime());
                // EVENT_END_TIME
                preparedStmt.setString(32, genAd.getEventEndTime());
                // EVENT_DATE
                preparedStmt.setString(33, genAd.getEventDate());
                // COLOR
                preparedStmt.setString(34, genAd.getColor());
                // GENDER
                preparedStmt.setString(35, genAd.getGender());
                // AGE
                preparedStmt.setString(36, genAd.getAge());
                // FIRSTNAME1
                preparedStmt.setString(37, genAd.getFirstName1());
                // LASTNAME1
                preparedStmt.setString(38, genAd.getLastName1());
                // FIRSTNAME2
                preparedStmt.setString(39, genAd.getFirstName2());
                // LASTNAME2
                preparedStmt.setString(40, genAd.getLastName2());
                // CELEBRATION_TYPE
                preparedStmt.setString(41, genAd.getCelebrationType());
            }
            else if (Constants.CCI_JOBS_TABLE_NAME.equalsIgnoreCase(ad.getTableName())) {
                CCI_JobAd jobAd = (CCI_JobAd)ad;

                //JOB_TITLE
                preparedStmt.setString(21, jobAd.getJobTitle());
                //JOB_TYPE_CODE
                preparedStmt.setString(22, jobAd.getJobTypeCode());
                // JOB_LOC_ZIP
                preparedStmt.setString(23, jobAd.getJobLocationZip());
                // WORK_TYPE
                preparedStmt.setString(24, jobAd.getWorkType());
                // COMPANY_NAME
                preparedStmt.setString(25, jobAd.getCompanyName());
                // CONTACT_NAME
                preparedStmt.setString(26, jobAd.getContactName());
                // CONTACT_FAX
                preparedStmt.setString(27, jobAd.getContactFax());
                // JOB_LOC_CITY
                preparedStmt.setString(28, jobAd.getJobLocationCity());
                // JOB_LOC_STATE
                preparedStmt.setString(29, jobAd.getJobLocationState());
                // JOB_LOC_COUNTRY
                preparedStmt.setString(30, jobAd.getJobLocationCountry());
                // EDUCATION
                preparedStmt.setString(31, jobAd.getEducation());
                // EXPERIENCE
                preparedStmt.setString(32, jobAd.getExperience());
                // TRAVEL
                preparedStmt.setString(33, jobAd.getTravel());
                // BASE_PAY_PER
                preparedStmt.setString(34, jobAd.getBasePayPer());
                // BASE_PAY_LOW
                preparedStmt.setString(35, jobAd.getBasePayLow());
                // BASE_PAY_HIGHT
                preparedStmt.setString(36, jobAd.getBasePayHigh());
                // COMMISSION
                preparedStmt.setString(37, jobAd.getCommission());
                // BONUS
                preparedStmt.setString(38, jobAd.getBonus());
                // OTHER_PAY
                preparedStmt.setString(39, jobAd.getOtherPay());
                // COMPANY_URL
                preparedStmt.setString(40, jobAd.getCompanyUrl());
                // APPLY_URL
                preparedStmt.setString(41, jobAd.getApplyUrl());
                // INDUSTRY_CODE1
                preparedStmt.setString(42, jobAd.getIndustryCode1());
                // INDUSTRY_CODE2
                preparedStmt.setString(43, jobAd.getIndustryCode2());
                // INDUSTRY_CODE3
                preparedStmt.setString(44, jobAd.getIndustryCode3());
                // WEBID
                preparedStmt.setString(45, jobAd.getWebID());
                // ORIGINAL_AD_ID
                preparedStmt.setString(46, jobAd.getOriginalAdID());
                // VOICE TYPE
                preparedStmt.setString(47, jobAd.getVoiceType());
                // COMPANY VIDEO PROVIDED
                preparedStmt.setString(48, jobAd.getCompanyVideoProvided());
                 // MORE DETAILS URL
                preparedStmt.setString(49, jobAd.getMoreDetailsURL());
                // VIDEO NOTES
                preparedStmt.setString(50, jobAd.getVideoNotes());
                // VIDEO SEARCH KEYWORDS
                preparedStmt.setString(51, jobAd.getVideoSearchKeywords());
            }
            else if (Constants.CCI_REALESTATE_TABLE_NAME.equalsIgnoreCase(ad.getTableName())) {
                CCI_REAd reAd = (CCI_REAd)ad;

                // SALE_LEASE
                preparedStmt.setString(21, reAd.getSaleLease());
                // PRICE_PER_SQ_FT
                preparedStmt.setString(22, reAd.getPricePerSqFt());
                // PROPERTY_TYPE
                preparedStmt.setString(23, reAd.getPropertyType());
                // PROPERTY_CITY
                preparedStmt.setString(24, reAd.getPropertyCity());
                // PROPERTY_STATE
                preparedStmt.setString(25, reAd.getPropertyState());
                // PROPERTY_ZIP
                preparedStmt.setString(26, reAd.getPropertyZip());
                // PRICE
                preparedStmt.setString(27, reAd.getPrice());
                // BEDROOMS
                preparedStmt.setString(28, reAd.getBedrooms());
                // BATHS
                preparedStmt.setString(29, reAd.getBaths());
                // PROPERTY_ADDRESS
                preparedStmt.setString(30, reAd.getPropertyAddress());
                // NEIGHBORHOOD
                preparedStmt.setString(31, reAd.getNeighborhood());
                // COUNTRY
                preparedStmt.setString(32, reAd.getCountry());
                // OPENHSE_DATE1
                preparedStmt.setDate(33, reAd.getOpenHouseDate1() == null ? null : new java.sql.Date(reAd.getOpenHouseDate1().getTime()));
                // OPENHSE_STARTTM1
                preparedStmt.setString(34, reAd.getOpenHouseStartTime1());
                // OPENHSE_ENDTM1
                preparedStmt.setString(35, reAd.getOpenHouseEndTime1());
                // OPENHSE_DATE2
                preparedStmt.setDate(36, reAd.getOpenHouseDate2() == null ? null : new java.sql.Date(reAd.getOpenHouseDate2().getTime()));
                // OPENHSE_STARTTM2
                preparedStmt.setString(37, reAd.getOpenHouseStartTime2());
                // OPENHSE_ENDTM2
                preparedStmt.setString(38, reAd.getOpenHouseEndTime2());
                // MLS_NUMBER
                preparedStmt.setString(39, reAd.getMlsNumber());
                // SQ_FT
                preparedStmt.setString(40, reAd.getSquareFeet());
                // AIR_CONDITION
                preparedStmt.setString(41, reAd.getAirCondition());
                // BASEMENT
                preparedStmt.setString(42, reAd.getBasement());
                // DISHWASHER
                preparedStmt.setString(43, reAd.getDishwasher());
                // ELEVATOR
                preparedStmt.setString(44, reAd.getElevator());
                // FIREPLACE
                preparedStmt.setString(45, reAd.getFireplace());
                // FITNESS_ROOM
                preparedStmt.setString(46, reAd.getFitnessRoom());
                // DINING_ROOM
                preparedStmt.setString(47, reAd.getDiningRoom());
                // FAMILY_ROOM
                preparedStmt.setString(48, reAd.getFamilyRoom());
                // LAUNDRY
                preparedStmt.setString(49, reAd.getLaundry());
                // OTHER_PARKING
                preparedStmt.setString(50, reAd.getOtherParking());
                // GARAGE_PARKING
                preparedStmt.setString(51, reAd.getGarageParking());
                // PETS
                preparedStmt.setString(52, reAd.getPets());
                // POOL
                preparedStmt.setString(53, reAd.getPool());
                // SECURITY
                preparedStmt.setString(54, reAd.getSecurity());
                // WEBID
                preparedStmt.setString(55, reAd.getWebID());
                // AGENT_URL
                preparedStmt.setString(56, reAd.getAgentUrl());
            }
            else if (Constants.CCI_TRANSPORT_TABLE_NAME.equalsIgnoreCase(ad.getTableName())) {
                CCI_TransAd transAd = (CCI_TransAd)ad;

                // TYPE
                preparedStmt.setString(21, transAd.getType());
                // ITEM_ZIP
                preparedStmt.setString(22, transAd.getItemZip());
                // LENGTH
                preparedStmt.setString(23, transAd.getLength());
                // MAKE
                preparedStmt.setString(24, transAd.getMake());
                // MODEL
                preparedStmt.setString(25, transAd.getModel());
                // YEAR
                preparedStmt.setString(26, transAd.getYear());
                // MILEAGE
                preparedStmt.setString(27, transAd.getMileage());
                // PRICE
                preparedStmt.setString(28, transAd.getPrice());
                // BODY_STYLE
                preparedStmt.setString(29, transAd.getBodyStyle());
                // EXTERIOR_COLOR
                preparedStmt.setString(30, transAd.getExteriorColor());
                // VEHICLE_ZIP
                preparedStmt.setString(31, transAd.getVehicleZip());
                // INTERIOR_COLOR
                preparedStmt.setString(32, transAd.getInteriorColor());
                // CONDITION
                preparedStmt.setString(33, transAd.getCondition());
                // TRANSMISSION
                preparedStmt.setString(34, transAd.getTransmission());
                // TRIM
                preparedStmt.setString(35, transAd.getTrim());
                // ENGINE
                preparedStmt.setString(36, transAd.getEngine());
                // CONTACT_NAME
                preparedStmt.setString(37, transAd.getContactName());
                // DOORS
                preparedStmt.setObject(38, transAd.getDoors(), java.sql.Types.INTEGER);
                // OBO
                preparedStmt.setObject(39, transAd.getoBO(), java.sql.Types.INTEGER);
                // DRIVE_TRAIN
                preparedStmt.setString(40, transAd.getDriveTrain());
                // DEALER_ID
                preparedStmt.setObject(41, transAd.getDealerID(), java.sql.Types.INTEGER);
                // STOCK_NUMBER
                preparedStmt.setString(42, transAd.getStockNumber());
                // VIN
                preparedStmt.setString(43, transAd.getVin());
                // LICENSE_PLATE_NO
                preparedStmt.setString(44, transAd.getLicensePlateNumber());
                // WEBID
                preparedStmt.setString(45, transAd.getWebID());
                // CURRENCY
                preparedStmt.setString(46, transAd.getCurrency());
                // AC_FRONT
                preparedStmt.setObject(47, transAd.getAcFront(), java.sql.Types.INTEGER);
                // AC_REAR
                preparedStmt.setObject(48, transAd.getAcRear(), java.sql.Types.INTEGER);
                // CRUISE_CONTROL
                preparedStmt.setObject(49, transAd.getCruiseControl(), java.sql.Types.INTEGER);
                // NAVIGATION
                preparedStmt.setObject(50, transAd.getNavigation(), java.sql.Types.INTEGER);
                // POWER_LOCKS
                preparedStmt.setObject(51, transAd.getPowerLocks(), java.sql.Types.INTEGER);
                // POWER_STEERING
                preparedStmt.setObject(52, transAd.getPowerSteering(), java.sql.Types.INTEGER);
                // KEYLESS_ENTRY
                preparedStmt.setObject(53, transAd.getKeylessEntry(), java.sql.Types.INTEGER);
                // TV_VCR_DVD
                preparedStmt.setObject(54, transAd.getTvVcrDvd(), java.sql.Types.INTEGER);
                // BUCKET_SEATS
                preparedStmt.setObject(55, transAd.getBucketSeats(), java.sql.Types.INTEGER);
                // LEATHER_INTERIOR
                preparedStmt.setObject(56, transAd.getLeatherInterior(), java.sql.Types.INTEGER);
                // MEMORY_SEATS
                preparedStmt.setObject(57, transAd.getMemorySeats(), java.sql.Types.INTEGER);
                // POWER_SEATS
                preparedStmt.setObject(58, transAd.getPowerSeats(), java.sql.Types.INTEGER);
                // DRIVER_AIRBAG
                preparedStmt.setObject(59, transAd.getDriverAirbag(), java.sql.Types.INTEGER);
                // PASSENGER_AIRBAG
                preparedStmt.setObject(60, transAd.getPassengerAirbag(), java.sql.Types.INTEGER);
                // SIDE_AIRBAG
                preparedStmt.setObject(61, transAd.getSideAirbag(), java.sql.Types.INTEGER);
                // ALARM
                preparedStmt.setObject(62, transAd.getAlarm(), java.sql.Types.INTEGER);
                // ANTI_LOCK_BRAKES
                preparedStmt.setObject(63, transAd.getAntiLockBrakes(), java.sql.Types.INTEGER);
                // FOGLIGHTS
                preparedStmt.setObject(64, transAd.getFoglights(), java.sql.Types.INTEGER);
                // CASSETTE_PLAYER
                preparedStmt.setObject(65, transAd.getCassettePlayer(), java.sql.Types.INTEGER);
                // CD_CHANGER
                preparedStmt.setObject(66, transAd.getCdChanger(), java.sql.Types.INTEGER);
                // CD_PLAYER
                preparedStmt.setObject(67, transAd.getCdPlayer(), java.sql.Types.INTEGER);
                // PREMIUM_SOUND
                preparedStmt.setObject(68, transAd.getPremiumSound(), java.sql.Types.INTEGER);
                // POWER_WINDOWS
                preparedStmt.setObject(69, transAd.getPowerWindows(), java.sql.Types.INTEGER);
                // REAR_WIN_DEFOGGER
                preparedStmt.setObject(70, transAd.getRearWinDefogger(), java.sql.Types.INTEGER);
                // REAR_WIN_WIPER
                preparedStmt.setObject(71, transAd.getRearWinWiper(), java.sql.Types.INTEGER);
                // TINTED_GLASS
                preparedStmt.setObject(72, transAd.getTintedGlass(), java.sql.Types.INTEGER);
                // ALLOY_WHEELS
                preparedStmt.setObject(73, transAd.getAlloyWheels(), java.sql.Types.INTEGER);
                // SUNROOF
                preparedStmt.setObject(74, transAd.getSunroof(), java.sql.Types.INTEGER);
                // MOONROOF
                preparedStmt.setObject(75, transAd.getMoonroof(), java.sql.Types.INTEGER);
                // THIRD_ROW_SEATS
                preparedStmt.setObject(76, transAd.getThirdRowSeats(), java.sql.Types.INTEGER);
                // TOW_PACKAGE
                preparedStmt.setObject(77, transAd.getTowPackage(), java.sql.Types.INTEGER);
            }
            else if (Constants.CCI_VITALNOTICE_TABLE_NAME.equals(ad.getTableName())) {
                CCI_VitalAd vitalAd = (CCI_VitalAd)ad;

                //FIRST_NAME
                preparedStmt.setString(21, vitalAd.getFirstName());
                //LAST_NAME
                preparedStmt.setString(22, vitalAd.getLastName());
                //ZIP
                preparedStmt.setString(23, vitalAd.getZip());
                //AGE
                preparedStmt.setString(24, vitalAd.getAge());
                //CITY
                preparedStmt.setString(25, vitalAd.getCity());
                //NEIGHBORHOOD
                preparedStmt.setString(26, vitalAd.getNeighborhood());
                //STATE
                preparedStmt.setString(27, vitalAd.getState());
                //DATE_DEATH
                preparedStmt.setString(28, vitalAd.getDateDeath());
                //DATE_BIRTH
                preparedStmt.setString(29, vitalAd.getDateBirth());
                //FUNERAL_HOME
                preparedStmt.setString(30, vitalAd.getFuneralHome());
            }
            else if (Constants.RECYCLER_TABLE_NAME.equals(ad.getTableName())) {
                RecyclerAd recyclerAd = (RecyclerAd)ad;

                preparedStmt.setString(1, recyclerAd.getClassID());
                preparedStmt.setString(2, recyclerAd.getParentClass());
                preparedStmt.setDate(3, recyclerAd.getStartDate() == null ? null : new java.sql.Date(recyclerAd.getStartDate().getTime()));
                preparedStmt.setDate(4, recyclerAd.getEndDate() == null ? null : new java.sql.Date(recyclerAd.getEndDate().getTime()));
                preparedStmt.setTimestamp(5, new java.sql.Timestamp(new Date().getTime()));
                preparedStmt.setObject(6, recyclerAd.getCustomerID(), java.sql.Types.INTEGER);
                preparedStmt.setObject(7, recyclerAd.getCost(), java.sql.Types.DOUBLE);
                preparedStmt.setObject(8, recyclerAd.getIsOnline(), java.sql.Types.INTEGER);
                preparedStmt.setString(9, recyclerAd.getAreaCode());
                preparedStmt.setString(10, recyclerAd.getPhone());
                preparedStmt.setString(11, recyclerAd.getLocation());
                preparedStmt.setString(12, recyclerAd.getEmail());
                preparedStmt.setString(13, recyclerAd.getUrl());
                preparedStmt.setObject(14, recyclerAd.getPrice(), java.sql.Types.DOUBLE);
                preparedStmt.setObject(15, recyclerAd.getYear(), java.sql.Types.INTEGER);
                preparedStmt.setString(16, recyclerAd.getVin());
                preparedStmt.setObject(17, recyclerAd.getWanted(), java.sql.Types.INTEGER);
                preparedStmt.setString(18, recyclerAd.getPublication());
                preparedStmt.setString(19, recyclerAd.getZip());
                preparedStmt.setString(20, recyclerAd.getSortText());
                preparedStmt.setString(21, recyclerAd.getPhotoName());
                preparedStmt.setString(22, recyclerAd.getPhotoName1());
                preparedStmt.setString(23, recyclerAd.getPhotoName2());
                preparedStmt.setString(24, recyclerAd.getPhotoName3());
                preparedStmt.setString(25, recyclerAd.getPhotoName4());
                preparedStmt.setString(26, recyclerAd.getPhotoName5());
                preparedStmt.setString(27, recyclerAd.getTitle());
                preparedStmt.setString(28, recyclerAd.getContactName());
                preparedStmt.setString(29, recyclerAd.getMake());
                preparedStmt.setString(30, recyclerAd.getModel());
                preparedStmt.setObject(31, recyclerAd.getInsertions(), java.sql.Types.INTEGER);
                preparedStmt.setString(32, recyclerAd.getWebPhone1());
                preparedStmt.setString(33, recyclerAd.getWebPhone2());
                preparedStmt.setString(34, recyclerAd.getZones());
                preparedStmt.setObject(35, recyclerAd.getDuration(), java.sql.Types.INTEGER);
                preparedStmt.setString(36, recyclerAd.getTrim());
                preparedStmt.setString(37, recyclerAd.getMileage());

            }
        }
        catch (Exception e) {
            Logger.log("Error setting update statements for ad " + e);
        }
    }

    /**
     * Add transmit log info for Events page if user updates ad
     */
    public void updateAdTransmitLog (CCI_Ad ad, String username) {
        // INSERT a message into classified_transmit_log
	    String adid = ad.getAdID();

	    try {
            String sql =
                    "insert into classified_transmit_log \n" +
                    "(primaryid, secondaryid, logtime, config, filetransmitted, note) \n" +
                    "values (?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ad.getAdID());
            stmt.setString(2, String.valueOf(ad.getFeedID()));
	        stmt.setTimestamp(3, new Timestamp( (new java.util.Date()).getTime() ));
            stmt.setString(4, "N/A");
            stmt.setString(5, "N/A");
            stmt.setString(6, "Updated in CMT by user "+ username);
            int count = stmt.executeUpdate();
            Logger.log ("Transmit log: " + username + " updated " + count + " record: Adid " + adid);
        }
        catch (SQLException e) {
            Logger.log("Error adding transmit log for updating Adid " + adid + " by user " + username);
            Logger.log("Error: " + e);
        }
    }

     /**
     * Add transmit log info for Events page if user inserts ad
     */
    public void insertAdTransmitLog (CCI_Ad ad, String username) {
        // INSERT a message into classified_transmit_log
	    String adid = ad.getAdID();

	    try {
            String sql =
                    "insert into classified_transmit_log \n" +
                    "(primaryid, secondaryid, logtime, config, filetransmitted, note) \n" +
                    "values (?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ad.getAdID());
            stmt.setString(2, String.valueOf(ad.getFeedID()));
	        stmt.setTimestamp(3, new Timestamp( (new java.util.Date()).getTime() ));
            stmt.setString(4, "N/A");
            stmt.setString(5, "N/A");
            stmt.setString(6, "Created in CMT by user "+ username);
            int count = stmt.executeUpdate();
            Logger.log ("Transmit log: " + username + " inserted " + count + " record: Adid " + adid);
        }
        catch (SQLException e) {
            Logger.log("Error adding transmit log for inserting Adid " + adid + " by user " + username);
            Logger.log("Error: " + e);
        }
    }

     /**
     * Add transmit log info for Events page if user re-runs ad
     */
    public void rerunAdTransmitLog (CCI_Ad ad, String username) {
        // INSERT a message into classified_transmit_log
	    String adid = ad.getAdID();

	    try {
            String sql =
                    "insert into classified_transmit_log \n" +
                    "(primaryid, secondaryid, logtime, config, filetransmitted, note) \n" +
                    "values (?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ad.getAdID());
            stmt.setString(2, String.valueOf(ad.getFeedID()));
	        stmt.setTimestamp(3, new Timestamp( (new java.util.Date()).getTime() ));
            stmt.setString(4, "N/A");
            stmt.setString(5, "N/A");
            stmt.setString(6, "Re-ran in CMT by user "+ username);
            int count = stmt.executeUpdate();
            Logger.log ("Transmit log: " + username + " re-ran " + count + " record: Adid " + adid);
        }
        catch (SQLException e) {
            Logger.log("Error adding transmit log for re-runing Adid " + adid + " by user " + username);
            Logger.log("Error: " + e);
        }
    }


    /**
     * Called from the createAd.jsp page
     * Inserts CCI ad values
     */


    public int insertAd(CCI_Ad ad) {

        String adid = ad.getAdID();
        String successMessage = "The ad was created successfully.";
        int count = 0;

        try {
            // Generate SQL Statement
            String sql = getInsertStatement(ad.getTableName());

            // Set Values into Prepared Statement
            PreparedStatement insertStmt = conn.prepareStatement(sql);
            setInsertStatementValuesFromAd(insertStmt, ad);

            // Turn off Auto Commit for CLOBs and Execute Update
            boolean autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            count = insertStmt.executeUpdate();

            Statement clobStmt = conn.createStatement();

            // Put PRINT_DESC, ENHANCED_DESC CLOB data into empty CLOB field
            String printDesc_stmt = "select print_desc from " + ad.getTableName() + " where adid = '" + ad.getAdID() + "' and FEEDID = " + ad.getFeedID();
            String enhancedDesc_stmt = "select enhanced_desc from " + ad.getTableName() + " where ADID = '" + ad.getAdID() + "' and FEEDID = " + ad.getFeedID();

            ResultSet rset = clobStmt.executeQuery(printDesc_stmt);
            while (rset.next()) {
                CLOB c1 = (CLOB) rset.getObject(1);
                c1.putString(1, ad.getPrintDesc());
            }
            rset.close();
            rset = clobStmt.executeQuery(enhancedDesc_stmt);
            while (rset.next()) {
                CLOB c2 = (CLOB) rset.getObject(1);
                c2.putString(1, ad.getEnhancedDesc());
            }
            rset.close();

            conn.commit();

            // Return Auto Commit back to original value
            conn.setAutoCommit(autocommit);

            Logger.log("Inserted " + count + " record: Ad ID " + ad.getAdID());
        }
        catch (Exception e) {
            Logger.log("Error: Insert of Ad ID " + ad.getAdID() + " failed");
            Logger.log("Error:" + e);
            successMessage = "Create ad failed.<br>";
            if (e.toString().indexOf("_FDID_ADID) violated") > -1) {
                successMessage += "Adid " + adid + " already exists for this feed and class.";
            }
            else
                successMessage += e;
        }
        finally {
            ad.setDataResponseMessage(successMessage);

        }
        if (successMessage.indexOf("failed") == -1) {
            //we already successfully set this to a new date in the prepared statement,
            //just need to add it into the model to pass back to the jsp view
            ad.setDateReceived(new Date());
        }
        return count;
    }

    /**
     * The rerunAd method is used for a producer to specify that a particular classified ad should be rerun.
     * This method simply re-inserts a duplicate ad, specifying a new startDate, a new stopDate and then adding the letter
     * "R" to the beginning of the adid to make it unique
     */
    public int rerunAd(CCI_Ad ad, Date newStart, Date newStop) throws ClassifiedToolException {

        //set the new run date and kill dates
        Date startDate = ad.getStartDate();
        Date stopDate = ad.getStopDate();
        ad.setStartDate(newStart);
        ad.setStopDate(newStop);

        //get the adid and append the letter "R" to it
        String adid = ad.getAdID();
        String newAdid = "R" + adid;
        ad.setAdID(newAdid);

        int count = 0;

        String successMessage = "The ad was re-run successfully.";

        try {
            // Generate SQL Statement
            String sql = getInsertStatement(ad.getTableName());

            // Set Values into Prepared Statement
            PreparedStatement rerunStmt = conn.prepareStatement(sql);
            setInsertStatementValuesFromAd(rerunStmt, ad);

            // Turn off Auto Commit for CLOBs and Execute Update
            boolean autocommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            count = rerunStmt.executeUpdate();

            Statement clobStmt = conn.createStatement();

            // Put PRINT_DESC, ENHANCED_DESC CLOB data into empty CLOB field
            String printDesc_stmt = "select print_desc from " + ad.getTableName() + " where adid = '" + ad.getAdID() + "' and FEEDID = " + ad.getFeedID();
            String enhancedDesc_stmt = "select enhanced_desc from " + ad.getTableName() + " where ADID = '" + ad.getAdID() + "' and FEEDID = " + ad.getFeedID();

            ResultSet rset = clobStmt.executeQuery(printDesc_stmt);
            while (rset.next()) {
                CLOB c1 = (CLOB) rset.getObject(1);
                c1.putString(1, ad.getPrintDesc());
            }
            rset.close();
            rset = clobStmt.executeQuery(enhancedDesc_stmt);
            while (rset.next()) {
                CLOB c2 = (CLOB) rset.getObject(1);
                c2.putString(1, ad.getEnhancedDesc());
            }
            rset.close();

            conn.commit();

            // Return Auto Commit back to original value
            conn.setAutoCommit(autocommit);

            Logger.log("Re-ran " + count + " record: Ad ID " + ad.getAdID());
        }
        catch (Exception e) {
            Logger.log("Error: Re-run of Ad ID " + ad.getAdID() + " failed");
            Logger.log("Error:" + e);
            successMessage = "Re-run failed.<br>";
            if (e.toString().indexOf("_FDID_ADID) violated") > -1) {
                successMessage += "Adid " + adid + " has already been re-run.";
            }
            else if (newAdid.length() > 20){
                successMessage += "Adid " + adid + "'s maximum length of 20 characters has been reached.";
            }
            else if ((e.toString().indexOf("java.sql.SQLException: ORA-01400: cannot insert NULL into") > -1) &&
                    ((e.toString().indexOf("STARTDATE")) > -1)){
                successMessage += "Please correct the format of the Re-run Start Date field.";
            }
            else if ((e.toString().indexOf("java.sql.SQLException: ORA-01400: cannot insert NULL into") > -1) &&
                    ((e.toString().indexOf("STOPDATE")) > -1)){
                successMessage += "Please correct the format of the Re-run Stop Date field.";
            }
            else
                successMessage += e;
        }
        finally {
            ad.setDataResponseMessage(successMessage);
        }
        if (successMessage.indexOf("failed") == -1) {
            //we already successfully set this to a new date in the prepared statement,
            //just need to add it into the model to pass back to the jsp view
            ad.setFirstReceived(new Date());
            ad.setDateReceived(new Date());
            ad.setDateUpdated(null);
        }
        else {
            ad.setAdID(adid);
            ad.setStartDate(startDate);
            ad.setStopDate(stopDate);
        }

        return count;
    }
    
    /**
     * The sendAd method is used for a producer to specify that 
     * a particular classified ad should be re-send to vendor.
     * This method adjusts the config file so that only this particular
     * ad will be sent to the coresponding vendor.
     */
    public int sendAd(CCI_Ad ad, String rootDir, String binDir, String cfgDir, String exportCmd, String exportWorkDir) throws ClassifiedToolException {
               
        //get the ad info
        String adId = ad.getAdID();
        int feedId = ad.getFeedID().intValue();
        String tableName = ad.getTableName();
        
        String configName = "";
        String configText = "";
        InputStream inputStream = null;
        
        boolean validAd = false;
        int count = -1;
        String successMessage = "SendAd started. Please click 'Events' to check the status.";

        
        String sqlStmt = "select * from feedeng.cmt_export_config " +
                "where feedid = ? and tablename = ?";
        
        //get information from DB
        Logger.log("DI:sendAd - get config info from DB. ");
        try {
            PreparedStatement ps = conn.prepareStatement(sqlStmt);
            ps.setInt(1, feedId);
            ps.setString(2, tableName.toUpperCase());

            //execute statement
            ResultSet rs = ps.executeQuery();
            
           // if (rs != null) {
            if (rs.next()) {
                configName = rs.getString("CONFIGNAME");
                Clob clob = rs.getClob("CONFIGTEXT");

                //Materializing the Clob
                try {
                    inputStream = clob.getAsciiStream();

                    Logger.log("Config file name is: " + configName);
                    //this section convert the input stream to string
                    byte[] buffer = new byte[4096];  
                    OutputStream outputStream = new ByteArrayOutputStream(); 

                    //read in text from buffer
                    while (true) {
                        int read = inputStream.read(buffer);
                        if (read == -1) {
                            break;
                        }
                        outputStream.write(buffer, 0, read);
                    }
                    outputStream.close();
                    inputStream.close();
                    configText = outputStream.toString();

                } catch(SQLException ex) {
                    Logger.log("error processing Clob record " + ex.toString());
                } 
                validAd = true;
            } else {  //no record select, no more work
                rs.close();
                successMessage = "This ad can't be sent automatically.  Please use the 'Rerun' feature instead.";
                ad.setDataResponseMessage(successMessage);
            }
            
        } catch(Exception e) {
            Logger.log("error selecting record " + e.toString());

            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("error selecting record " + e.toString());
            throw ctException;
        } 
       
        //construct config file if valid ad found
        if (validAd) {
            Logger.log("get config info success.  prepare for modify the config.");
            FtpAd ftpAd = new FtpAd();

            Logger.log("DI:sendAd - constructConfigFile with adid=" + adId + " configName=" + configName);
            
            //String configFileName = ftpAd.constructConfigFile(adId, configText, configName, rootDir, cfgDir);
            String[] configInfo = ftpAd.constructConfigFile(adId, configText, configName, rootDir, cfgDir);
            
            //double check if the sql in the new config file can select the record
            boolean returnAd = false;
            configText = configInfo[0];
            if (configText.length()>0) {
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet rst = stmt.executeQuery(configText);
                    if (rst.next()) {
                        Logger.log("ad " + adId + "exist and ready to be sent.");
                        returnAd = true;
                    } else {
                        successMessage = "This ad can't be sent automatically.  Please use the 'Rerun' feature instead.";
                        ad.setDataResponseMessage(successMessage);
                        //return -1;
                    }
                } catch(SQLException ex) {
                    Logger.log("error processing Clob record " + ex.toString());
                }
                
            }
            
            
            String configFileName = configInfo[1];
            if (returnAd && configFileName != null && configFileName.length()>1) {
                configFileName = configFileName.substring(configFileName.lastIndexOf(File.separator)+1);

                Logger.log("DI:sendAd - send ad with exportCmd=" + exportCmd + " configFileName=" +configFileName);
                try {
                    count = ftpAd.Send(rootDir, binDir, exportCmd, configFileName, exportWorkDir);

                    if (count >= 0) {
                        Logger.log("Ad " + ad.getAdID() + "was sent.");
                    } else {
                        Logger.log("Error: send ad " + ad.getAdID() + " failed.");
                        successMessage = "Error: send ad " + ad.getAdID() + " failed.";
                    }
                } catch(Exception e) {
                    Logger.log("Error: send ad " + ad.getAdID() + " failed: " + e.toString());
                    successMessage = "The send action failed.<br>";
                    successMessage += e.toString();
                } finally {
                    ad.setDataResponseMessage(successMessage);
                }

            }
        }
        return count;
    }


    /**
     * Returns current insert statements for all CCI and Recycler ad tables
     */
    private String getInsertStatement(String tableName) {

        String insertStatement = "";

        if (Constants.CCI_APARTMENTS_TABLE_NAME.equalsIgnoreCase(tableName)) {
            insertStatement =
                  "insert into " + tableName + " \n" +
                    "(FEEDID, DATERECEIVED, FIRSTRECEIVED, DATEUPDATED, ADID, PUBLICATION, COMMERCIALRELEASE, CANCELED, CUST_CITY, CUST_STATE, CUST_ZIP, \n" +
                    "CUST_EMAIL, CLASS, STARTDATE, STOPDATE, TITLE, UPSELL, CONTACT_EMAIL, ENHANCE_EMAIL, PHOTO_COUNT, \n" +
                    "PHOTO_REF, LOGO_REF, PRINTAD_IMG, CONTACT_PHONE, PROPERTY_TYPE, PROPERTY_CITY, PROPERTY_STATE, PROPERTY_ZIP, PRICE, BEDROOMS, \n" +
                    "BATHS, PROPERTY_ADDRESS, NEIGHBORHOOD, COUNTRY, WEBID, PRINT_DESC, ENHANCED_DESC, INSERTIONID, \n" +
                    "OPENHSE_DATE1, OPENHSE_STARTTM1, OPENHSE_ENDTM1, OPENHSE_DATE2, OPENHSE_STARTTM2, OPENHSE_ENDTM2, \n" +
                    "MLS_NUMBER, SQ_FT, AIR_CONDITION, BASEMENT, DISHWASHER, ELEVATOR, FIREPLACE, FITNESS_ROOM, DINING_ROOM, \n" +
                    "FAMILY_ROOM, LAUNDRY, OTHER_PARKING, GARAGE_PARKING, PETS, POOL, SECURITY, AGENT_URL) \n" +
                  "values \n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, empty_clob(), empty_clob(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \n";
        }
        else if (Constants.CCI_GENCLASSIFIED_TABLE_NAME.equalsIgnoreCase(tableName)) {
            insertStatement =
                  "insert into " + tableName + " \n" +
                    "(FEEDID, DATERECEIVED, FIRSTRECEIVED, DATEUPDATED, ADID, PUBLICATION, COMMERCIALRELEASE, CANCELED, CUST_CITY, CUST_STATE, CUST_ZIP, \n" +
                    "CUST_EMAIL, CLASS, STARTDATE, STOPDATE, TITLE, UPSELL, CONTACT_EMAIL, ENHANCE_EMAIL, PHOTO_COUNT, \n" +
                    "PHOTO_REF, LOGO_REF, PRINTAD_IMG, TYPE, ZIP, PRICE, CONTACT_PHONE, COUNTRY, ADDRESS, CITY, STATE, BREED, ADVERTISER_URL, \n" +
                    "PRINT_DESC, ENHANCED_DESC, INSERTIONID, EVENT_START_TIME, EVENT_END_TIME, EVENT_DATE, COLOR, GENDER, AGE, OBO, FIRSTNAME1, LASTNAME1, \n" +
                    "FIRSTNAME2, LASTNAME2, CELEBRATION_TYPE) \n" +
                  "values \n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, empty_clob(), " +
                    "empty_clob(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) \n";
        }
        else if (Constants.CCI_JOBS_TABLE_NAME.equalsIgnoreCase(tableName)) {
            insertStatement =
                  "insert into " + tableName + " \n" +
                    "(FEEDID, DATERECEIVED, FIRSTRECEIVED, DATEUPDATED, ADID, PUBLICATION, COMMERCIALRELEASE, CANCELED, CUST_CITY, CUST_STATE, CUST_ZIP, \n" +
                    "CUST_EMAIL, CLASS, STARTDATE, STOPDATE, TITLE, UPSELL, CONTACT_EMAIL, ENHANCE_EMAIL, PHOTO_COUNT, \n" +
                    "PHOTO_REF, LOGO_REF, PRINTAD_IMG, JOB_TITLE, JOB_TYPE_CODE, INDUSTRY_CODE1, JOB_LOC_ZIP, WORK_TYPE, COMPANY_NAME, CONTACT_NAME, \n" +
                    "CONTACT_PHONE, CONTACT_FAX, JOB_LOC_CITY, JOB_LOC_STATE, JOB_LOC_COUNTRY, EDUCATION, EXPERIENCE, TRAVEL, \n" +
                    "BASE_PAY_PER, BASE_PAY_LOW, BASE_PAY_HIGH, COMMISSION, BONUS, OTHER_PAY, COMPANY_URL, APPLY_URL, WEBID, \n" +
                    "INDUSTRY_CODE2, INDUSTRY_CODE3, PRINT_DESC, ENHANCED_DESC, INSERTIONID, ORIGINAL_AD_ID, VOICE_TYPE, COMPANY_VIDEO_PROVIDED, \n" +
                    "MOREDETAILS_URL, VIDEO_NOTES, VIDEO_SEARCH_KEYWORDS) \n" +
                  "values \n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, empty_clob(), empty_clob(), ?, ?, ?, ?, ?, ?, ?) \n";
        }
        else if (Constants.CCI_REALESTATE_TABLE_NAME.equalsIgnoreCase(tableName)) {
            insertStatement =
                  "insert into " + tableName + " \n" +
                    "(FEEDID, DATERECEIVED, FIRSTRECEIVED, DATEUPDATED, ADID, PUBLICATION, COMMERCIALRELEASE, CANCELED, CUST_CITY, CUST_STATE, CUST_ZIP, \n" +
                    "CUST_EMAIL, CLASS, STARTDATE, STOPDATE, TITLE, UPSELL, CONTACT_EMAIL, ENHANCE_EMAIL, PHOTO_COUNT, \n" +
                    "PHOTO_REF, LOGO_REF, PRINTAD_IMG, SALE_LEASE, PRICE_PER_SQ_FT, CONTACT_PHONE, PROPERTY_TYPE, PROPERTY_CITY, PROPERTY_STATE, \n" +
                    "PROPERTY_ZIP, PRICE, BEDROOMS, BATHS, PROPERTY_ADDRESS, NEIGHBORHOOD, COUNTRY, OPENHSE_DATE1, \n" +
                    "OPENHSE_STARTTM1, OPENHSE_ENDTM1, OPENHSE_DATE2, OPENHSE_STARTTM2, OPENHSE_ENDTM2, MLS_NUMBER, SQ_FT, \n" +
                    "AIR_CONDITION, BASEMENT, DISHWASHER, ELEVATOR, FIREPLACE, FITNESS_ROOM, DINING_ROOM, FAMILY_ROOM, \n" +
                    "LAUNDRY, OTHER_PARKING, GARAGE_PARKING, PETS, POOL, SECURITY, WEBID, AGENT_URL, \n" +
                    "PRINT_DESC, ENHANCED_DESC, INSERTIONID) \n" +
                  "values \n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, empty_clob(), empty_clob(), ?) \n";
        }
        else if (Constants.CCI_TRANSPORT_TABLE_NAME.equalsIgnoreCase(tableName)) {
            insertStatement =
                  "insert into " + tableName + " \n" +
                    "(FEEDID, DATERECEIVED, FIRSTRECEIVED, DATEUPDATED, ADID, PUBLICATION, COMMERCIALRELEASE, CANCELED, CUST_CITY, CUST_STATE, CUST_ZIP, \n" +
                    "CUST_EMAIL, CLASS, STARTDATE, STOPDATE, TITLE, UPSELL, CONTACT_EMAIL, ENHANCE_EMAIL, PHOTO_COUNT, \n" +
                    "PHOTO_REF, LOGO_REF, PRINTAD_IMG, TYPE, ITEM_ZIP, LENGTH, MAKE, MODEL, YEAR, MILEAGE, PRICE, BODY_STYLE, EXTERIOR_COLOR, \n" +
                    "VEHICLE_ZIP, INTERIOR_COLOR, CONDITION, TRANSMISSION, TRIM, ENGINE, CONTACT_NAME, CONTACT_PHONE, DOORS, \n" +
                    "OBO, DRIVE_TRAIN, DEALER_ID, STOCK_NUMBER, VIN, LICENSE_PLATE_NO, WEBID, PRINT_DESC, ENHANCED_DESC, \n" +
                    "INSERTIONID, CURRENCY, AC_FRONT, AC_REAR, CRUISE_CONTROL, NAVIGATION, POWER_LOCKS, POWER_STEERING, \n" +
                    "KEYLESS_ENTRY, TV_VCR_DVD, BUCKET_SEATS, LEATHER_INTERIOR, MEMORY_SEATS, POWER_SEATS, DRIVER_AIRBAG, \n" +
                    "PASSENGER_AIRBAG, SIDE_AIRBAG, ALARM, ANTI_LOCK_BRAKES, FOGLIGHTS, CASSETTE_PLAYER, CD_CHANGER, \n" +
                    "CD_PLAYER, PREMIUM_SOUND, POWER_WINDOWS, REAR_WIN_DEFOGGER, REAR_WIN_WIPER, TINTED_GLASS, ALLOY_WHEELS, \n" +
                    "SUNROOF, MOONROOF, THIRD_ROW_SEATS, TOW_PACKAGE) \n" +
                  "values \n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, empty_clob(), empty_clob(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?) \n";

        }
        else if (Constants.CCI_VITALNOTICE_TABLE_NAME.equalsIgnoreCase(tableName)) {
            insertStatement =
                  "insert into " + tableName + " \n" +
                     "(FEEDID, DATERECEIVED, FIRSTRECEIVED, DATEUPDATED, ADID, PUBLICATION, COMMERCIALRELEASE, CANCELED, CUST_CITY, CUST_STATE, CUST_ZIP, \n" +
                     "CUST_EMAIL, CLASS, STARTDATE, STOPDATE, TITLE, UPSELL, CONTACT_EMAIL, ENHANCE_EMAIL, PHOTO_COUNT, \n" +
                     "PHOTO_REF, LOGO_REF, PRINTAD_IMG, FIRST_NAME, LAST_NAME, ZIP, PRINT_DESC, ENHANCED_DESC, INSERTIONID, AGE, CITY, NEIGHBORHOOD, \n" +
                     "STATE, DATE_DEATH, DATE_BIRTH, FUNERAL_HOME, CONTACT_PHONE) \n" +
                  "values \n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, empty_clob(), empty_clob(), ?, ?, ?, ?, ?, ?, ?, ?, ?) \n";

        }
        return insertStatement;
    }

    /**
     * Sets the values from CCI_Ad object into the insert statement getUpdateStatement()
     */
    private void setInsertStatementValuesFromAd(PreparedStatement preparedStmt, CCI_Ad theAd) throws SQLException {

        if (Constants.CCI_APARTMENTS_TABLE_NAME.equals(theAd.getTableName())) {
            CCI_AptAd ad = (CCI_AptAd)theAd;

            // FEEDID
            preparedStmt.setObject(1, ad.getFeedID(), java.sql.Types.INTEGER);
            // DATERECEIVED
            preparedStmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            // FIRSTRECEIVED
            preparedStmt.setDate(3, null);
            // DATEUPDATED
            preparedStmt.setDate(4, null);
            // ADID
            preparedStmt.setString(5, ad.getAdID());
            // PUBLICATION
            preparedStmt.setString(6, ad.getPublication());
            // COMMERCIALRELEASE
            preparedStmt.setString(7, ad.getCommercialRelease());
            // CANCELED
            preparedStmt.setObject(8, ad.getCanceled(), java.sql.Types.INTEGER);
            // CUST_CITY
            preparedStmt.setString(9, ad.getCustomerCity());
            // CUST_STATE
            preparedStmt.setString(10, ad.getCustomerState());
            // CUST_ZIP
            preparedStmt.setString(11, ad.getCustomerZip());
            // CUST_EMAIL
            preparedStmt.setString(12, ad.getCustomerEmail());
            // CLASS
            preparedStmt.setString(13, ad.getClassID());
            // STARTDATE
            preparedStmt.setDate(14, ad.getStartDate() == null ? null : new java.sql.Date(ad.getStartDate().getTime()));
            // STOPDATE
            preparedStmt.setDate(15, ad.getStopDate() == null ? null : new java.sql.Date(ad.getStopDate().getTime()));
            // TITLE
            preparedStmt.setString(16, ad.getTitle());
            // UPSELL
            preparedStmt.setString(17, ad.getUpsell());
            // CONTACT_EMAIL
            preparedStmt.setString(18, ad.getContactEmail());
            // ENHANCE_EMAIL
            preparedStmt.setString(19, ad.getEnhanceEmail());
            // PHOTO_COUNT
            preparedStmt.setObject(20, ad.getPhotoCount(), java.sql.Types.INTEGER);
            // PHOTO_REF
            preparedStmt.setString(21, ad.getPhotoRef());
            // LOGO_REF
            preparedStmt.setString(22, ad.getLogoRef());
            // PRINTAD_IMG
            preparedStmt.setString(23, ad.getPrintAdImage());
            // CONTACT_PHONE
            preparedStmt.setString(24, ad.getContactPhone());
            // PROPERTY_TYPE
            preparedStmt.setString(25, ad.getPropertyType());
            // PROPERTY_CITY
            preparedStmt.setString(26, ad.getPropertyCity());
            // PROPERTY_STATE
            preparedStmt.setString(27, ad.getPropertyState());
            // PROPERTY_ZIP
            preparedStmt.setString(28, ad.getPropertyZip());
            // PRICE
            preparedStmt.setString(29, ad.getPrice());
            // BEDROOMS
            preparedStmt.setString(30, ad.getBedrooms());
            // BATHS
            preparedStmt.setString(31, ad.getBaths());
            // PROPERTY_ADDRESS
            preparedStmt.setString(32, ad.getPropertyAddress());
            // NEIGHBORHOOD
            preparedStmt.setString(33, ad.getNeighborhood());
            // COUNTRY
            preparedStmt.setString(34, ad.getCountry());
            // WEBID
            preparedStmt.setString(35, ad.getWebID());
            // INSERTIONID
            preparedStmt.setString(36, "0");
            // OPENHSE_DATE1
            preparedStmt.setDate(37, ad.getOpenHouseDate1() == null ? null : new java.sql.Date(ad.getOpenHouseDate1().getTime()));
            // OPENHSE_STARTTM1
            preparedStmt.setString(38, ad.getOpenHouseStartTime1());
            // OPENHSE_ENDTM1
            preparedStmt.setString(39, ad.getOpenHouseEndTime1());
            // OPENHSE_DATE2
            preparedStmt.setDate(40, ad.getOpenHouseDate2() == null ? null : new java.sql.Date(ad.getOpenHouseDate2().getTime()));
            // OPENHSE_STARTTM2
            preparedStmt.setString(41, ad.getOpenHouseStartTime2());
            // OPENHSE_ENDTM2
            preparedStmt.setString(42, ad.getOpenHouseEndTime2());
            // MLS_NUMBER
            preparedStmt.setString(43, ad.getMlsNumber());
            // SQ_FT
            preparedStmt.setString(44, ad.getSquareFeet());
            // AIR_CONDITION
            preparedStmt.setString(45, ad.getAirCondition());
            // BASEMENT
            preparedStmt.setString(46, ad.getBasement());
            // DISHWASHER
            preparedStmt.setString(47, ad.getDishwasher());
            // ELEVATOR
            preparedStmt.setString(48, ad.getElevator());
            // FIREPLACE
            preparedStmt.setString(49, ad.getFireplace());
            // FITNESS_ROOM
            preparedStmt.setString(50, ad.getFitnessRoom());
            // DINING_ROOM
            preparedStmt.setString(51, ad.getDiningRoom());
            // FAMILY_ROOM
            preparedStmt.setString(52, ad.getFamilyRoom());
            // LAUNDRY
            preparedStmt.setString(53, ad.getLaundry());
            // OTHER_PARKING
            preparedStmt.setString(54, ad.getOtherParking());
            // GARAGE_PARKING
            preparedStmt.setString(55, ad.getGarageParking());
            // PETS
            preparedStmt.setString(56, ad.getPets());
            // POOL
            preparedStmt.setString(57, ad.getPool());
            // SECURITY
            preparedStmt.setString(58, ad.getSecurity());
            // AGENT_URL
            preparedStmt.setString(59, ad.getAgentUrl());
        }
        else if (Constants.CCI_GENCLASSIFIED_TABLE_NAME.equals(theAd.getTableName())) {
            CCI_GenAd ad = (CCI_GenAd)theAd;

            // FEEDID
            preparedStmt.setObject(1, ad.getFeedID(), java.sql.Types.INTEGER);
            // DATERECEIVED
            preparedStmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            // FIRSTRECEIVED
            preparedStmt.setDate(3, null);
            // DATEUPDATED
            preparedStmt.setDate(4, null);
            // ADID
            preparedStmt.setString(5, ad.getAdID());
            // PUBLICATION
            preparedStmt.setString(6, ad.getPublication());
            // COMMERCIALRELEASE
            preparedStmt.setString(7, ad.getCommercialRelease());
            // CANCELED
            preparedStmt.setObject(8, ad.getCanceled(), java.sql.Types.INTEGER);
            // CUST_CITY
            preparedStmt.setString(9, ad.getCustomerCity());
            // CUST_STATE
            preparedStmt.setString(10, ad.getCustomerState());
            // CUST_ZIP
            preparedStmt.setString(11, ad.getCustomerZip());
            // CUST_EMAIL
            preparedStmt.setString(12, ad.getCustomerEmail());
            // CLASS
            preparedStmt.setString(13, ad.getClassID());
            // STARTDATE
            preparedStmt.setDate(14, ad.getStartDate() == null ? null : new java.sql.Date(ad.getStartDate().getTime()));
            // STOPDATE
            preparedStmt.setDate(15, ad.getStopDate() == null ? null : new java.sql.Date(ad.getStopDate().getTime()));
            // TITLE
            preparedStmt.setString(16, ad.getTitle());
            // UPSELL
            preparedStmt.setString(17, ad.getUpsell());
            // CONTACT_EMAIL
            preparedStmt.setString(18, ad.getContactEmail());
            // ENHANCE_EMAIL
            preparedStmt.setString(19, ad.getEnhanceEmail());
            // PHOTO_COUNT
            preparedStmt.setObject(20, ad.getPhotoCount(), java.sql.Types.INTEGER);
            // PHOTO_REF
            preparedStmt.setString(21, ad.getPhotoRef());
            // LOGO_REF
            preparedStmt.setString(22, ad.getLogoRef());
            // PRINTAD_IMG
            preparedStmt.setString(23, ad.getPrintAdImage());
            // TYPE
            preparedStmt.setString(24, ad.getType());
            // ZIP
            preparedStmt.setString(25, ad.getZip());
            // PRICE
            preparedStmt.setString(26, ad.getPrice());
            // CONTACT_PHONE
            preparedStmt.setString(27, ad.getContactPhone());
            // COUNTRY
            preparedStmt.setString(28, ad.getCountry());
            // ADDRESS
            preparedStmt.setString(29, ad.getAddress());
            // CITY
            preparedStmt.setString(30, ad.getCity());
            // STATE
            preparedStmt.setString(31, ad.getState());
            // BREED
            preparedStmt.setString(32, ad.getBreed());
            // ADVERTISER_URL
            preparedStmt.setString(33, ad.getAdvertiserUrl());
            // INSERTIONID
            preparedStmt.setString(34, "0");
            // EVENT_START_TIME
            preparedStmt.setString(35, ad.getEventStartTime());
            // EVENT_END_TIME
            preparedStmt.setString(36, ad.getEventEndTime());
            // EVENT_DATE
            preparedStmt.setString(37, ad.getEventDate());
            // COLOR
            preparedStmt.setString(38, ad.getColor());
            // GENDER
            preparedStmt.setString(39, ad.getGender());
            // AGE
            preparedStmt.setString(40, ad.getAge());
            // OBO
            preparedStmt.setObject(41, ad.getoBO(), java.sql.Types.INTEGER);
            // FIRSTNAME1
            preparedStmt.setString(42, ad.getFirstName1());
            // LASTNAME1
            preparedStmt.setString(43, ad.getLastName1());
            // FIRSTNAME2
            preparedStmt.setString(44, ad.getFirstName2());
            // LASTNAME1
            preparedStmt.setString(45, ad.getLastName2());
            // CELEBRATION_TYPE
            preparedStmt.setString(46, ad.getCelebrationType());
        }
        else if (Constants.CCI_JOBS_TABLE_NAME.equals(theAd.getTableName())) {
            CCI_JobAd ad = (CCI_JobAd)theAd;

            // FEEDID
            preparedStmt.setObject(1, ad.getFeedID(), java.sql.Types.INTEGER);
            // DATERECEIVED
            preparedStmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            // FIRSTRECEIVED
            preparedStmt.setDate(3, null);
            // DATEUPDATED
            preparedStmt.setDate(4, null);
            // ADID
            preparedStmt.setString(5, ad.getAdID());
            // PUBLICATION
            preparedStmt.setString(6, ad.getPublication());
            // COMMERCIALRELEASE
            preparedStmt.setString(7, ad.getCommercialRelease());
            // CANCELED
            preparedStmt.setObject(8, ad.getCanceled(), java.sql.Types.INTEGER);
            // CUST_CITY
            preparedStmt.setString(9, ad.getCustomerCity());
            // CUST_STATE
            preparedStmt.setString(10, ad.getCustomerState());
            // CUST_ZIP
            preparedStmt.setString(11, ad.getCustomerZip());
            // CUST_EMAIL
            preparedStmt.setString(12, ad.getCustomerEmail());
            // CLASS
            preparedStmt.setString(13, ad.getClassID());
            // STARTDATE
            preparedStmt.setDate(14, ad.getStartDate() == null ? null : new java.sql.Date(ad.getStartDate().getTime()));
            // STOPDATE
            preparedStmt.setDate(15, ad.getStopDate() == null ? null : new java.sql.Date(ad.getStopDate().getTime()));
            // TITLE
            preparedStmt.setString(16, ad.getTitle());
            // UPSELL
            preparedStmt.setString(17, ad.getUpsell());
            // CONTACT_EMAIL
            preparedStmt.setString(18, ad.getContactEmail());
            // ENHANCE_EMAIL
            preparedStmt.setString(19, ad.getEnhanceEmail());
            // PHOTO_COUNT
            preparedStmt.setObject(20, ad.getPhotoCount(), java.sql.Types.INTEGER);
            // PHOTO_REF
            preparedStmt.setString(21, ad.getPhotoRef());
            // LOGO_REF
            preparedStmt.setString(22, ad.getLogoRef());
            // PRINTAD_IMG
            preparedStmt.setString(23, ad.getPrintAdImage());
            // JOB_TITLE
            preparedStmt.setString(24, ad.getJobTitle());
            // JOB_TYPE_CODE
            preparedStmt.setString(25, ad.getJobTypeCode());
            // INDUSTRY_CODE1
            preparedStmt.setString(26, ad.getIndustryCode1());
            // JOB_LOC_ZIP
            preparedStmt.setString(27, ad.getJobLocationZip());
            // WORK_TYPE
            preparedStmt.setString(28, ad.getWorkType());
            // COMPANY_NAME
            preparedStmt.setString(29, ad.getCompanyName());
            // CONTACT_NAME
            preparedStmt.setString(30, ad.getContactName());
            // CONTACT_PHONE
            preparedStmt.setString(31, ad.getContactPhone());
            // CONTACT_FAX
            preparedStmt.setString(32, ad.getContactFax());
            // JOB_LOC_CITY
            preparedStmt.setString(33, ad.getJobLocationCity());
            // JOB_LOC_STATE
            preparedStmt.setString(34, ad.getJobLocationState());
            // JOB_LOC_COUNTRY
            preparedStmt.setString(35, ad.getJobLocationCountry());
            // EDUCATION
            preparedStmt.setString(36, ad.getEducation());
            // EXPERIENCE
            preparedStmt.setString(37, ad.getExperience());
            // TRAVEL
            preparedStmt.setString(38, ad.getTravel());
            // BASE_PAY_PER
            preparedStmt.setString(39, ad.getBasePayPer());
            // BASE_PAY_LOW
            preparedStmt.setString(40, ad.getBasePayLow());
            // BASE_PAY_HIGH
            preparedStmt.setString(41, ad.getBasePayHigh());
            // COMMISSION
            preparedStmt.setString(42, ad.getCommission());
            // BONUS
            preparedStmt.setString(43, ad.getBonus());
            // OTHER_PAY
            preparedStmt.setString(44, ad.getOtherPay());
            // COMPANY_URL
            preparedStmt.setString(45, ad.getCompanyUrl());
            // APPLY_URL
            preparedStmt.setString(46, ad.getApplyUrl());
            // WEBID
            preparedStmt.setString(47, ad.getWebID());
            // INDUSTRY_CODE2
            preparedStmt.setString(48, ad.getIndustryCode2());
            // INDUSTRY_CODE3
            preparedStmt.setString(49, ad.getIndustryCode3());
            // INSERTIONID
            preparedStmt.setString(50, "0");
             // ORIGINAL_AD_ID
            preparedStmt.setString(51, ad.getOriginalAdID());
            // VOICE_TYPE
            preparedStmt.setString(52, ad.getVoiceType());
            // COMPANY_VIDEO_REQUIRED
            preparedStmt.setString(53, ad.getCompanyVideoProvided());
            // MOREDETAILS_URL
            preparedStmt.setString(54, ad.getMoreDetailsURL());
             // VIDEO_NOTES
            preparedStmt.setString(55, ad.getVideoNotes());
             // VIDEO_SEARCH_KEYWORD
            preparedStmt.setString(56, ad.getVideoSearchKeywords());
        }
        else if (Constants.CCI_REALESTATE_TABLE_NAME.equals(theAd.getTableName())) {
            CCI_REAd ad = (CCI_REAd)theAd;

            // FEEDID
            preparedStmt.setObject(1, ad.getFeedID(), java.sql.Types.INTEGER);
            // DATERECEIVED
            preparedStmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            // FIRSTRECEIVED
            preparedStmt.setDate(3, null);
            // DATEUPDATED
            preparedStmt.setDate(4, null);
            // ADID
            preparedStmt.setString(5, ad.getAdID());
            // PUBLICATION
            preparedStmt.setString(6, ad.getPublication());
            // COMMERCIALRELEASE
            preparedStmt.setString(7, ad.getCommercialRelease());
            // CANCELED
            preparedStmt.setObject(8, ad.getCanceled(), java.sql.Types.INTEGER);
            // CUST_CITY
            preparedStmt.setString(9, ad.getCustomerCity());
            // CUST_STATE
            preparedStmt.setString(10, ad.getCustomerState());
            // CUST_ZIP
            preparedStmt.setString(11, ad.getCustomerZip());
            // CUST_EMAIL
            preparedStmt.setString(12, ad.getCustomerEmail());
            // CLASS
            preparedStmt.setString(13, ad.getClassID());
            // STARTDATE
            preparedStmt.setDate(14, ad.getStartDate() == null ? null : new java.sql.Date(ad.getStartDate().getTime()));
            // STOPDATE
            preparedStmt.setDate(15, ad.getStopDate() == null ? null : new java.sql.Date(ad.getStopDate().getTime()));
            // TITLE
            preparedStmt.setString(16, ad.getTitle());
            // UPSELL
            preparedStmt.setString(17, ad.getUpsell());
            // CONTACT_EMAIL
            preparedStmt.setString(18, ad.getContactEmail());
            // ENHANCE_EMAIL
            preparedStmt.setString(19, ad.getEnhanceEmail());
            // PHOTO_COUNT
            preparedStmt.setObject(20, ad.getPhotoCount(), java.sql.Types.INTEGER);
            // PHOTO_REF
            preparedStmt.setString(21, ad.getPhotoRef());
            // LOGO_REF
            preparedStmt.setString(22, ad.getLogoRef());
            // PRINTAD_IMG
            preparedStmt.setString(23, ad.getPrintAdImage());
            // SALE_LEASE
            preparedStmt.setString(24, ad.getSaleLease());
            // PRICE_PER_SQ_FT
            preparedStmt.setString(25, ad.getPricePerSqFt());
            // CONTACT_PHONE
            preparedStmt.setString(26, ad.getContactPhone());
            // PROPERTY_TYPE
            preparedStmt.setString(27, ad.getPropertyType());
            // PROPERTY_CITY
            preparedStmt.setString(28, ad.getPropertyCity());
            // PROPERTY_STATE
            preparedStmt.setString(29, ad.getPropertyState());
            // PROPERTY_ZIP
            preparedStmt.setString(30, ad.getPropertyZip());
            // PRICE
            preparedStmt.setString(31, ad.getPrice());
            // BEDROOMS
            preparedStmt.setString(32, ad.getBedrooms());
            // BATHS
            preparedStmt.setString(33, ad.getBaths());
            // PROPERTY_ADDRESS
            preparedStmt.setString(34, ad.getPropertyAddress());
            // NEIGHBORHOOD
            preparedStmt.setString(35, ad.getNeighborhood());
            // COUNTRY
            preparedStmt.setString(36, ad.getCountry());
            // OPENHSE_DATE1
            preparedStmt.setDate(37, ad.getOpenHouseDate1() == null ? null : new java.sql.Date(ad.getOpenHouseDate1().getTime()));
            // OPENHSE_STARTTM1
            preparedStmt.setString(38, ad.getOpenHouseStartTime1());
            // OPENHSE_ENDTM1
            preparedStmt.setString(39, ad.getOpenHouseEndTime1());
            // OPENHSE_DATE2
            preparedStmt.setDate(40, ad.getOpenHouseDate2() == null ? null : new java.sql.Date(ad.getOpenHouseDate2().getTime()));
            // OPENHSE_STARTTM2
            preparedStmt.setString(41, ad.getOpenHouseStartTime2());
            // OPENHSE_ENDTM2
            preparedStmt.setString(42, ad.getOpenHouseEndTime2());
            // MLS_NUMBER
            preparedStmt.setString(43, ad.getMlsNumber());
            // SQ_FT
            preparedStmt.setString(44, ad.getSquareFeet());
            // AIR_CONDITION
            preparedStmt.setString(45, ad.getAirCondition());
            // BASEMENT
            preparedStmt.setString(46, ad.getBasement());
            // DISHWASHER
            preparedStmt.setString(47, ad.getDishwasher());
            // ELEVATOR
            preparedStmt.setString(48, ad.getElevator());
            // FIREPLACE
            preparedStmt.setString(49, ad.getFireplace());
            // FITNESS_ROOM
            preparedStmt.setString(50, ad.getFitnessRoom());
            // DINING_ROOM
            preparedStmt.setString(51, ad.getDiningRoom());
            // FAMILY_ROOM
            preparedStmt.setString(52, ad.getFamilyRoom());
            // LAUNDRY
            preparedStmt.setString(53, ad.getLaundry());
            // OTHER_PARKING
            preparedStmt.setString(54, ad.getOtherParking());
            // GARAGE_PARKING
            preparedStmt.setString(55, ad.getGarageParking());
            // PETS
            preparedStmt.setString(56, ad.getPets());
            // POOL
            preparedStmt.setString(57, ad.getPool());
            // SECURITY
            preparedStmt.setString(58, ad.getSecurity());
            // WEBID
            preparedStmt.setString(59, ad.getWebID());
            // AGENT_URL
            preparedStmt.setString(60, ad.getAgentUrl());
            // INSERTIONID
            preparedStmt.setString(61, "0");
        }
        else if (Constants.CCI_TRANSPORT_TABLE_NAME.equals(theAd.getTableName())) {
            CCI_TransAd ad = (CCI_TransAd)theAd;

            // FEEDID
            preparedStmt.setObject(1, ad.getFeedID(), java.sql.Types.INTEGER);
            // DATERECEIVED
            preparedStmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            // FIRSTRECEIVED
            preparedStmt.setDate(3, null);
            // DATEUPDATED
            preparedStmt.setDate(4, null);
            // ADID
            preparedStmt.setString(5, ad.getAdID());
            // PUBLICATION
            preparedStmt.setString(6, ad.getPublication());
            // COMMERCIALRELEASE
            preparedStmt.setString(7, ad.getCommercialRelease());
            // CANCELED
            preparedStmt.setObject(8, ad.getCanceled(), java.sql.Types.INTEGER);
            // CUST_CITY
            preparedStmt.setString(9, ad.getCustomerCity());
            // CUST_STATE
            preparedStmt.setString(10, ad.getCustomerState());
            // CUST_ZIP
            preparedStmt.setString(11, ad.getCustomerZip());
            // CUST_EMAIL
            preparedStmt.setString(12, ad.getCustomerEmail());
            // CLASS
            preparedStmt.setString(13, ad.getClassID());
            // STARTDATE
            preparedStmt.setDate(14, ad.getStartDate() == null ? null : new java.sql.Date(ad.getStartDate().getTime()));
            // STOPDATE
            preparedStmt.setDate(15, ad.getStopDate() == null ? null : new java.sql.Date(ad.getStopDate().getTime()));
            // TITLE
            preparedStmt.setString(16, ad.getTitle());
            // UPSELL
            preparedStmt.setString(17, ad.getUpsell());
            // CONTACT_EMAIL
            preparedStmt.setString(18, ad.getContactEmail());
            // ENHANCE_EMAIL
            preparedStmt.setString(19, ad.getEnhanceEmail());
            // PHOTO_COUNT
            preparedStmt.setObject(20, ad.getPhotoCount(), java.sql.Types.INTEGER);
            // PHOTO_REF
            preparedStmt.setString(21, ad.getPhotoRef());
            // LOGO_REF
            preparedStmt.setString(22, ad.getLogoRef());
            // PRINTAD_IMG
            preparedStmt.setString(23, ad.getPrintAdImage());
            // TYPE
            preparedStmt.setString(24, ad.getType());
            // ITEM_ZIP
            preparedStmt.setString(25, ad.getItemZip());
            // LENGTH
            preparedStmt.setString(26, ad.getLength());
            // MAKE
            preparedStmt.setString(27, ad.getMake());
            // MODEL
            preparedStmt.setString(28, ad.getModel());
            // YEAR
            preparedStmt.setString(29, ad.getYear());
            // MILEAGE
            preparedStmt.setString(30, ad.getMileage());
            // PRICE
            preparedStmt.setString(31, ad.getPrice());
            // BODY_STYLE
            preparedStmt.setString(32, ad.getBodyStyle());
            // EXTERIOR_COLOR
            preparedStmt.setString(33, ad.getExteriorColor());
            // VEHICLE_ZIP
            preparedStmt.setString(34, ad.getVehicleZip());
            // INTERIOR_COLOR
            preparedStmt.setString(35, ad.getInteriorColor());
            // CONDITION
            preparedStmt.setString(36, ad.getCondition());
            // TRANSMISSION
            preparedStmt.setString(37, ad.getTransmission());
            // TRIM
            preparedStmt.setString(38, ad.getTrim());
            // ENGINE
            preparedStmt.setString(39, ad.getEngine());
            // CONTACT_NAME
            preparedStmt.setString(40, ad.getContactName());
            // CONTACT_PHONE
            preparedStmt.setString(41, ad.getContactPhone());
            // DOORS
            preparedStmt.setObject(42, ad.getDoors(), java.sql.Types.INTEGER);
            // OBO
            preparedStmt.setObject(43, ad.getoBO(), java.sql.Types.INTEGER);
            // DRIVE_TRAIN
            preparedStmt.setString(44, ad.getDriveTrain());
            // DEALER_ID
            preparedStmt.setObject(45, ad.getDealerID(), java.sql.Types.INTEGER);
            // STOCK_NUMBER
            preparedStmt.setString(46, ad.getStockNumber());
            // VIN
            preparedStmt.setString(47, ad.getVin());
            // LICENSE_PLATE_NO
            preparedStmt.setString(48, ad.getLicensePlateNumber());
            // WEBID
            preparedStmt.setString(49, ad.getWebID());
            // INSERTIONID
            preparedStmt.setString(50, "0");
            // CURRENCY
            preparedStmt.setString(51, ad.getCurrency());
            // AC_FRONT
            preparedStmt.setObject(52, ad.getAcFront(), java.sql.Types.INTEGER);
            // AC_REAR
            preparedStmt.setObject(53, ad.getAcRear(), java.sql.Types.INTEGER);
            // CRUISE_CONTROL
            preparedStmt.setObject(54, ad.getCruiseControl(), java.sql.Types.INTEGER);
            // NAVIGATION
            preparedStmt.setObject(55, ad.getNavigation(), java.sql.Types.INTEGER);
            // POWER_LOCKS
            preparedStmt.setObject(56, ad.getPowerLocks(), java.sql.Types.INTEGER);
            // POWER_STEERING
            preparedStmt.setObject(57, ad.getPowerSteering(), java.sql.Types.INTEGER);
            // KEYLESS_ENTRY
            preparedStmt.setObject(58, ad.getKeylessEntry(), java.sql.Types.INTEGER);
            // TV_VCR_DVD
            preparedStmt.setObject(59, ad.getTvVcrDvd(), java.sql.Types.INTEGER);
            // BUCKET_SEATS
            preparedStmt.setObject(60, ad.getBucketSeats(), java.sql.Types.INTEGER);
            // LEATHER_INTERIOR
            preparedStmt.setObject(61, ad.getLeatherInterior(), java.sql.Types.INTEGER);
            // MEMORY_SEATS
            preparedStmt.setObject(62, ad.getMemorySeats(), java.sql.Types.INTEGER);
            // POWER_SEATS
            preparedStmt.setObject(63, ad.getPowerSeats(), java.sql.Types.INTEGER);
            // DRIVER_AIRBAG
            preparedStmt.setObject(64, ad.getDriverAirbag(), java.sql.Types.INTEGER);
            // PASSENGER_AIRBAG
            preparedStmt.setObject(65, ad.getPassengerAirbag(), java.sql.Types.INTEGER);
            // SIDE_AIRBAG
            preparedStmt.setObject(66, ad.getSideAirbag(), java.sql.Types.INTEGER);
            // ALARM
            preparedStmt.setObject(67, ad.getAlarm(), java.sql.Types.INTEGER);
            // ANTI_LOCK_BRAKES
            preparedStmt.setObject(68, ad.getAntiLockBrakes(), java.sql.Types.INTEGER);
            // FOGLIGHTS
            preparedStmt.setObject(69, ad.getFoglights(), java.sql.Types.INTEGER);
            // CASSETTE_PLAYER
            preparedStmt.setObject(70, ad.getCassettePlayer(), java.sql.Types.INTEGER);
            // CD_CHANGER
            preparedStmt.setObject(71, ad.getCdChanger(), java.sql.Types.INTEGER);
            // CD_PLAYER
            preparedStmt.setObject(72, ad.getCdPlayer(), java.sql.Types.INTEGER);
            // PREMIUM_SOUND
            preparedStmt.setObject(73, ad.getPremiumSound(), java.sql.Types.INTEGER);
            // POWER_WINDOWS
            preparedStmt.setObject(74, ad.getPowerWindows(), java.sql.Types.INTEGER);
            // REAR_WIN_DEFOGGER
            preparedStmt.setObject(75, ad.getRearWinDefogger(), java.sql.Types.INTEGER);
            // REAR_WIN_WIPER
            preparedStmt.setObject(76, ad.getRearWinWiper(), java.sql.Types.INTEGER);
            // TINTED_GLASS
            preparedStmt.setObject(77, ad.getTintedGlass(), java.sql.Types.INTEGER);
            // ALLOY_WHEELS
            preparedStmt.setObject(78, ad.getAlloyWheels(), java.sql.Types.INTEGER);
            // SUNROOF
            preparedStmt.setObject(79, ad.getSunroof(), java.sql.Types.INTEGER);
            // MOONROOF
            preparedStmt.setObject(80, ad.getMoonroof(), java.sql.Types.INTEGER);
            // THIRD_ROW_SEATS
            preparedStmt.setObject(81, ad.getThirdRowSeats(), java.sql.Types.INTEGER);
            // TOW_PACKAGE
            preparedStmt.setObject(82, ad.getTowPackage(), java.sql.Types.INTEGER);

        }
        else if (Constants.CCI_VITALNOTICE_TABLE_NAME.equals(theAd.getTableName())) {
            CCI_VitalAd ad = (CCI_VitalAd)theAd;

            // FEEDID
            preparedStmt.setObject(1, ad.getFeedID(), java.sql.Types.INTEGER);
            // DATERECEIVED
            preparedStmt.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
            // FIRSTRECEIVED
            preparedStmt.setDate(3, null);
            // DATEUPDATED
            preparedStmt.setDate(4, null);
            // ADID
            preparedStmt.setString(5, ad.getAdID());
            // PUBLICATION
            preparedStmt.setString(6, ad.getPublication());
            // COMMERCIALRELEASE
            preparedStmt.setString(7, ad.getCommercialRelease());
            // CANCELED
            preparedStmt.setObject(8, ad.getCanceled(), java.sql.Types.INTEGER);
            // CUST_CITY
            preparedStmt.setString(9, ad.getCustomerCity());
            // CUST_STATE
            preparedStmt.setString(10, ad.getCustomerState());
            // CUST_ZIP
            preparedStmt.setString(11, ad.getCustomerZip());
            // CUST_EMAIL
            preparedStmt.setString(12, ad.getCustomerEmail());
            // CLASS
            preparedStmt.setString(13, ad.getClassID());
            // STARTDATE
            preparedStmt.setDate(14, ad.getStartDate() == null ? null : new java.sql.Date(ad.getStartDate().getTime()));
            // STOPDATE
            preparedStmt.setDate(15, ad.getStopDate() == null ? null : new java.sql.Date(ad.getStopDate().getTime()));
            // TITLE
            preparedStmt.setString(16, ad.getTitle());
            // UPSELL
            preparedStmt.setString(17, ad.getUpsell());
            // CONTACT_EMAIL
            preparedStmt.setString(18, ad.getContactEmail());
            // ENHANCE_EMAIL
            preparedStmt.setString(19, ad.getEnhanceEmail());
            // PHOTO_COUNT
            preparedStmt.setObject(20, ad.getPhotoCount(), java.sql.Types.INTEGER);
            // PHOTO_REF
            preparedStmt.setString(21, ad.getPhotoRef());
            // LOGO_REF
            preparedStmt.setString(22, ad.getLogoRef());
            // PRINTAD_IMG
            preparedStmt.setString(23, ad.getPrintAdImage());
            // FIRST_NAME
            preparedStmt.setString(24, ad.getFirstName());
            // LAST_NAME
            preparedStmt.setString(25, ad.getLastName());
            // ZIP
            preparedStmt.setString(26, ad.getZip());
            // INSERTIONID
            preparedStmt.setString(27, "0");
            // AGE
            preparedStmt.setString(28, ad.getAge());
            // CITY
            preparedStmt.setString(29, ad.getCity());
            // NEIGHBORHOOD
            preparedStmt.setString(30, ad.getNeighborhood());
            // STATE
            preparedStmt.setString(31, ad.getState());
            // DATE_DEATH
            preparedStmt.setString(32, ad.getDateDeath());
            // DATE_BIRTH
            preparedStmt.setString(33, ad.getDateBirth());
            // FUNERAL_HOME
            preparedStmt.setString(34, ad.getFuneralHome());
            // CONTACT_PHONE
            preparedStmt.setString(35, ad.getContactPhone());
        }
    }

    /**
     * Gathers list of available CCI feeds for createAd.jsp
     */
    public ArrayList listCreateAdFeeds (String market) throws ClassifiedToolException {

        ArrayList feedList = new ArrayList();
        PreparedStatement getFeedData;

        try {

            getFeedData = conn.prepareStatement(
                    "select\n" +
                    "      distinct feedid,\n" +
                    "      market,\n" +
                    "      feedname,\n" +
                    "      pubname\n" +
                    "from\n" +
                    "      feedconfig,\n" +
                    "      publication\n" +
                    "where\n" +
                    "      upper(feedname) like upper('%ads_manual%')\n" +
                    "      and pubid=market\n" +
                    "      and market like ?");

            getFeedData.setString(1, market);
            ResultSet results = getFeedData.executeQuery();

            while (results.next()) {

                MarketRecord record = new MarketRecord();

                if (results.getInt (1) != 0)
                    record.setFeedID(results.getString ( 1 ));
                if (results.getString (2) != null)
                    record.setMarket( results.getString ( 2 ));
                if (results.getString (3) != null)
                    record.setFeedName( results.getString ( 3 ));
                if (results.getString (4) != null)
                    record.setPubName( results.getString ( 4 ));

                feedList.add(record);
            }
        }
        catch(Exception e) {
            Logger.log("Error in obtaining Market Record information " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in obtaining Market Record information " + e.toString());
            throw ctException;
        }

        return feedList;
    }



    public void updateImage (CCI_Ad ad, String imageField, String imageValue) throws ClassifiedToolException {

        PreparedStatement updateImageRecord;
        int count = 0;

        try {
            updateImageRecord = conn.prepareStatement(
                    "update\n" +
                    ad.getTableName() + "\n" +
                    "set\n" +
                    imageField + " = ?\n" +
                    "where\n" +
                    "adid = ? \n" +
                    "and feedid = ? \n");

            // "Set" clauses
            if ("photo_count".equals(imageValue)){
                updateImageRecord.setObject(1, imageValue, java.sql.Types.INTEGER);
            }
            else {
                updateImageRecord.setString(1, imageValue);
            }

            // "Where" clauses
            updateImageRecord.setString(2, ad.getAdID());
            updateImageRecord.setObject(3, ad.getFeedID(), java.sql.Types.INTEGER);

            count = updateImageRecord.executeUpdate();
            Logger.log("Updated " + count + " " + imageField + " for adid " + ad.getAdID());
        }
        catch(Exception e) {
            Logger.log("Error in Updating " + imageField + " " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in Updating " + imageField + " " + e.toString());
            throw ctException;
        }

    }


    /**
     * Gathers list of available CCI feeds for exportLookup.jsp
     */
    public ArrayList listExportFeeds (String market) throws ClassifiedToolException {

        ArrayList feedList = new ArrayList();
        PreparedStatement getFeedData;

        try {

            getFeedData = conn.prepareStatement(
                    "select\n" +
                    "      distinct feedid,\n" +
                    "      market,\n" +
                    "      feedname,\n" +
                    "      pubname\n" +
                    "from\n" +
                    "      feedconfig,\n" +
                    "      publication\n" +
                    "where\n" +
                    "      feedid > 300\n" +
                    "      and pubid=market\n" +
                    "      and market like ?");

            getFeedData.setString(1, market);
            ResultSet results = getFeedData.executeQuery();

            while (results.next()) {

                MarketRecord record = new MarketRecord();

                if (results.getInt (1) != 0)
                    record.setFeedID(results.getString ( 1 ));
                if (results.getString (2) != null)
                    record.setMarket( results.getString ( 2 ));
                if (results.getString (3) != null)
                    record.setFeedName( results.getString ( 3 ));
                if (results.getString (4) != null)
                    record.setPubName( results.getString ( 4 ));

                feedList.add(record);
            }
        }
        catch(Exception e) {
            Logger.log("Error in obtaining Market Record information " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in obtaining Market Record information " + e.toString());
            throw ctException;
        }

        return feedList;
    }


    /**
     * Gathers list of available Export Vendors for exportLookup.jsp
     */
    public ArrayList listExportVendors () throws ClassifiedToolException {

        ArrayList vendorList = new ArrayList();
        PreparedStatement getVendorData;

        try {

            getVendorData = conn.prepareStatement("select vendor_id, vendor_name from export_vendors");
            ResultSet results = getVendorData.executeQuery();

            while (results.next()) {

                ExportClassRecord ecl = new ExportClassRecord();

                if (results.getInt (1) != 0)
                    ecl.setVendorID( results.getString ( 1 ));
                if (results.getString (2) != null)
                    ecl.setVendorName( results.getString ( 2 ));

                vendorList.add(ecl);
            }
        }
        catch(Exception e) {
            Logger.log("Error in Listing Export Vendors " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in Listing Export Vendors " + e.toString());
            throw ctException;
        }

        return vendorList;
    }


    /**
     * Returns list of currently used Export Classes based on feed for listClasses.jsp
     */
    public ArrayList listExportClasses (Integer feedid, String classid, String ccicat, String ccidesc, String vendid)
            throws ClassifiedToolException {

        ArrayList searchExportList = new ArrayList();
        PreparedStatement getExportRecords;

        try {
            getExportRecords = conn.prepareStatement(
                    "select\n" +
                    "      fc.feedid, market, feedname,\n" +
                    "      class_id, cci_category, cci_description, recycler_cat,\n" +
                    "      ev.vendor_name, ev.vendor_id\n" +
                    "from\n" +
                    "      feedconfig fc,\n" +
                    "      export_class_lookup ecl,\n" +
                    "      export_vendors ev\n" +
                    "where\n" +
                    "      fc.feedid=ecl.feedid\n" +
                    "      and ev.vendor_id = ecl.vendor_id\n" +
                    "      and ecl.feedid = ?\n" +
                    "      and class_id like ?\n" +
                    "      and cci_category like ?\n" +
                    "      and cci_description like ?\n" +
                    "      and ev.vendor_id like ?\n" +
                    "order by\n" +
                    "      class_id, ecl.vendor_id");


            classid = "%" + classid + "%";
            ccicat = "%" + ccicat + "%";
            ccidesc = "%" + ccidesc + "%";
            //rcycat = "%" + rcycat + "%";

            // Populate search query
            getExportRecords.setInt(1, feedid.intValue());
            getExportRecords.setString(2, classid);
            getExportRecords.setString(3, ccicat);
            getExportRecords.setString(4, ccidesc);
            getExportRecords.setString(5, vendid);
            //getExportRecords.setString(6, rcycat);

            ResultSet results = getExportRecords.executeQuery();

            while (results.next()) {

                ExportClassRecord record = new ExportClassRecord();

                if (results.getInt ("feedid") != 0)
                    record.setFeedID ( results.getInt ("feedid"));
                if (results.getString ("market") != null)
                    record.setMarket ( results.getString ("market"));
                if (results.getString ("feedname") != null)
                    record.setFeedName ( results.getString ("feedname"));
                if (results.getString ("vendor_name") != null)
                    record.setVendorName ( results.getString ("vendor_name"));
                if (results.getString ("class_id") != null){
                    record.setClassID ( results.getString ("class_id"));
                    record.setOldClassID ( results.getString ("class_id"));
                }
                if (results.getString ("cci_category") != null)
                    record.setCciCategory ( results.getString ("cci_category"));
                if (results.getString ("cci_description") != null)
                    record.setCciDescription ( results.getString ("cci_description"));
                if (results.getString ("recycler_cat") != null)
                    record.setRecyclerCategory ( results.getString ("recycler_cat"));
                if (results.getString ("vendor_id") != null){
                    record.setVendorID ( results.getString ("vendor_id"));
                    record.setOldVendorID ( results.getString ("vendor_id"));
                }


                searchExportList.add(record);
            }
        }
        catch(Exception e) {
            Logger.log("Error in Searching Export Classes " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in Searching Export Classes " + e.toString());
            throw ctException;
        }

        return searchExportList;
    }


    /**
     * Inserts new  class in export_class_lookup table
     */
    public int insertExportClass (ExportClassRecord record) throws ClassifiedToolException {

        int count = 0;
        PreparedStatement insertExportRecord;

        try {
            insertExportRecord = conn.prepareStatement(
                    "insert into\n" +
                    "      export_class_lookup\n" +
                    "values (?, ?, ?, ?, ?, ?)");

            // "Values" clauses
            insertExportRecord.setInt(1, record.getFeedID());
            insertExportRecord.setString(2, record.getVendorID());
            insertExportRecord.setString(3, record.getClassID());
            insertExportRecord.setString(4, record.getCciCategory());
            insertExportRecord.setString(5, record.getCciDescription());
            insertExportRecord.setString(6, record.getRecyclerCategory());

            count = insertExportRecord.executeUpdate();
            Logger.log("Inserted " + count + " export class(s)");
        }
        catch(Exception e) {
            Logger.log("Error in Inserting Export Class Record " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in Inserting Export Class Record " + e.toString());
            throw ctException;
        }

        return count;
    }


    /**
     * Updates current class in export_class_lookup table to new values from editClass.jsp
     * feedid remains the same.
     */
    public int updateExportClass (ExportClassRecord record) throws ClassifiedToolException {

        int count = 0;
        PreparedStatement updateExportRecord;

        try {
            updateExportRecord = conn.prepareStatement(
                    "update\n" +
                    "      export_class_lookup\n" +
                    "set\n" +
                    "      vendor_id = ?,\n" +
                    "      class_id = ?,\n" +
                    "      cci_category = ?,\n" +
                    "      cci_description = ?,\n" +
                    "      recycler_cat = ?\n" +

                    "where\n" +
                    "      feedid = ? \n" +
                    "      and vendor_id = ?\n" +
                    "      and class_id = ?\n");


            // "Set" clauses
            updateExportRecord.setString(1, record.getVendorID());
            updateExportRecord.setString(2, record.getClassID());
            updateExportRecord.setString(3, record.getCciCategory());
            updateExportRecord.setString(4, record.getCciDescription());
            updateExportRecord.setString(5, record.getRecyclerCategory());

            // "Where" clauses
            updateExportRecord.setInt(6, record.getFeedID());
            updateExportRecord.setString(7, record.getOldVendorID());
            updateExportRecord.setString(8, record.getOldClassID());

            count = updateExportRecord.executeUpdate();
            Logger.log("Updated " + count + " export class(s)");
        }
        catch(Exception e) {
            Logger.log("Error in Updating Export Class Record " + e.toString());

            // throw ClassifiedToolException
	        ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in Updating Export Class Record " + e.toString());
            throw ctException;
        }

        return count;
    }


    /**
     * Delete class id from export_class_lookup on the listClasses.jsp page
     */
    public int deleteExportClass(int feedID, String vendorID, String classID) throws ClassifiedToolException {

        PreparedStatement deleteExportRecord;
        int count = 0;

        try {
	        deleteExportRecord = conn.prepareStatement(
                    "delete from export_class_lookup where feedid = ? and vendor_id = ? and class_id = ?");

            deleteExportRecord.setInt(1, feedID);
            deleteExportRecord.setString(2, vendorID);
            deleteExportRecord.setString(3, classID);

            count = deleteExportRecord.executeUpdate();

            Logger.log("Deleted " + count + " export class.");
            Logger.log("Deletion of " + classID + " successful");
        }
	    catch(Exception e) {
            Logger.log("Error deleting Export Class " + e.toString());

            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error deleting Export Class " + e.toString());
            throw ctException;
	    }

        return count;
    }


    /**
     * Returns a vector list of states for RE Communities
     */
    public Vector listCommStates() throws ClassifiedToolException {

        Vector values = new Vector();
        String state = "select distinct state from re_communities " +
                         "where length(trim(state)) > 0 order by state";
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(state);

            while (result.next()) {
                values.addElement(result.getString(1));
            }
        }
        catch(Exception e) {
            Logger.log("Error in displaying state listings " + e.toString());

            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in displaying state listings " + e.toString());
            throw ctException;
        }
        return values;
    }

    /**
     * Returns a vector list of community listings for RE Communities
     */
    public Vector searchCommunities(String state) throws ClassifiedToolException
    {
        Vector commList = new Vector();
        String listComm = "select * from re_communities where state = ? order by state, " +
                           "county, city, community, neighborhood";
        try {
            PreparedStatement stmt = conn.prepareStatement(listComm);
            stmt.setString(1, state.toUpperCase());
            ResultSet result = stmt.executeQuery();
            commList = populateCommunities(result);
        }
        catch(Exception e) {
            Logger.log("Error displaying community listings: " + e.toString());

            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error displaying community listings: " + e.toString());
            throw ctException;
        }
        return commList;
    }

    /**
     * Populates resultset community values into vector list
     */
    public Vector populateCommunities(ResultSet result) throws ClassifiedToolException {

        Vector commList = new Vector();
        Community comm;
        int row = 1;
        try {
            while (result.next()) {
                comm = new Community();

                if (result.getString("ID") != null)
                    comm.setId(result.getInt("ID"));
                if (result.getString("NEIGHBORHOOD") != null)
                    comm.setNeighborhood(result.getString("NEIGHBORHOOD"));
                if (result.getString("COMMUNITY") != null)
                    comm.setCommunity(result.getString("COMMUNITY"));
                if (result.getString("CITY") != null)
                    comm.setCity(result.getString("CITY"));
                if (result.getString("COUNTY") != null)
                    comm.setCounty(result.getString("COUNTY"));
                if (result.getString("STATE") != null)
                    comm.setState(result.getString("STATE"));

                //append cmt to the cmts vector
                commList.addElement(comm);
                row++;
            }
        }
        catch (Exception e) {
            Logger.log("Error retrieving community information " + e.toString());

            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error retrieiving community information " + e.toString());
            throw ctException;
        }
        return commList;
    }

    /**
     * Insert new RE community into the Database
     */
    public void insertCommunity(Community comm) throws ClassifiedToolException {

        try {
            String stmt = "insert into RE_COMMUNITIES (ID, NEIGHBORHOOD, COMMUNITY, CITY, " +
	                      "COUNTY, STATE) values (communities_seq.nextval, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(stmt);
            ps.setString(1, comm.getNeighborhood());
            ps.setString(2, comm.getCommunity());
            ps.setString(3, comm.getCity());
            ps.setString(4, comm.getCounty());
            ps.setString(5, (comm.getState()).toUpperCase());

            //execute insert statement
            int count = ps.executeUpdate();
        }
        catch(Exception e) {
            Logger.log("Error inserting RE Community " + e.toString());

            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error inserting RE Community " + e.toString());
            throw ctException;
	   }
	}


    /**
     * Returns a vector list of common names for RE Communities
     */
    public Vector listCommonNames(int commid, String mkt) throws ClassifiedToolException {

        Vector cmns = new Vector();
        String listComm = "select ID, NAME, r.FEEDID, COMMUNITYID, MARKET " +
    	                  "from RE_COMMONNAMES r, FEEDCONFIG f " +
                          "where r.FEEDID = f.feedid and COMMUNITYID = ? and MARKET like ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(listComm);
            stmt.setInt(1,commid);
            stmt.setString(2,mkt);
            ResultSet result = stmt.executeQuery();
            cmns = populateCommonNames(result);
        }
        catch(Exception e) {
            Logger.log("Error in displaying common names information: " + e.toString());

            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in displaying common names information: " + e.toString());
            throw ctException;
        }
        return cmns;
    }

    /**
     * Populates resultset Common Names values into vector list
     */
    public Vector populateCommonNames(ResultSet result) throws ClassifiedToolException {
        Vector cmns = new Vector();
        CommonName cmn;
        int row = 1;
        try {
            while (result.next()) {
                cmn = new CommonName();

                cmn.setId(result.getInt("ID"));

                if (result.getString("COMMUNITYID") != null)
                    cmn.setCommunityid(result.getInt("COMMUNITYID"));
                if (result.getString("FEEDID") != null)
                    cmn.setFeedid(result.getInt("FEEDID"));
                if (result.getString("NAME") != null)
                    cmn.setName(result.getString("NAME"));

                //append cmt to the cmts vector
                cmns.addElement(cmn);
                row++;
            }
        }
        catch (Exception e) {
            Logger.log("Error populating Common Names " + e.toString());

            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error populating Common Names " + e.toString());
            throw ctException;
            }
        return cmns;
    }

    /**
     * Return listings of available RE feeds into vector list
     */
    public Vector listREConfig(String mkt) throws ClassifiedToolException {
        Vector recfgs = new Vector();
        REConfig recfg;
        String strCfg = "select * from RE_CONFIG where market like ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(strCfg);
            stmt.setString(1,mkt);
            ResultSet result = stmt.executeQuery();

            int row = 1;

            while (result.next()) {

                recfg = new REConfig();
                recfg.setMarket(result.getString("MARKET"));
                recfg.setFeedid(result.getInt("FEEDID"));

                //append cmt to the cmts vector
                recfgs.addElement(recfg);
                row++;
            }
        }
        catch(Exception e) {
            Logger.log("Error in displaying market information: " + e.toString());

            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error in displaying market information: " + e.toString());
            throw ctException;
        }
        return recfgs;
    }

    /**
     * Insert new RE common name into the database
     */
    public void insertCommonName(CommonName cmn) throws ClassifiedToolException {

        try {
	        String stmt = "insert into RE_COMMONNAMES (ID, NAME, FEEDID, COMMUNITYID) " +
	                      "values (re_commonnames_seq_pk.nextval, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(stmt);

            ps.setString(1, cmn.getName());
            ps.setInt(2, cmn.getFeedid());
            ps.setInt(3, cmn.getCommunityid());

            //execute insert statement
            int count = ps.executeUpdate();
        }
        catch(Exception e) {
            Logger.log("error updating record " + e.toString());

            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("error updating record " + e.toString());
            throw ctException;
        }
    }

    /**
     * Delete RE common name from database
     */
    public void deleteCommonName(int delId, int delCommid) throws ClassifiedToolException {

        String delCommName = "delete from re_commonnames where id = " + delId + " and communityid = " + delCommid;
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(delCommName);
        }
        catch(Exception e) {
            Logger.log("Error deleting Common Name " + e.toString());

            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error deleting Common Name " + e.toString());
            throw ctException;
        }
    }

	// code added for cr 25118

      /**
      * Add transmit log info for Events page if user deletes the ad
      */
       public void deleteAdTransmitLog (String adid, int feedid, String username) {
           // INSERT a message into classified_transmit_log
           //String adid = ad.getAdID();
           try {
               String sql =
                       "insert into classified_transmit_log \n" +
                       "(primaryid, secondaryid, logtime, config, filetransmitted, note) \n" +
                       "values (?, ?, ?, ?, ?, ?)\n";

               PreparedStatement stmt = conn.prepareStatement(sql);
               stmt.setString(1, adid);
               stmt.setString(2, String.valueOf(feedid));
               stmt.setTimestamp(3, new Timestamp( (new java.util.Date()).getTime() ));
               stmt.setString(4, "N/A");
               stmt.setString(5, "N/A");
               stmt.setString(6, "Deleted in CMT by user "+ username);
               int count = stmt.executeUpdate();
               Logger.log ("Transmit log: " + username + " deleted the Adid " + adid);
           }
           catch (SQLException e) {
               Logger.log("Error adding transmit log for deleteing Adid " + adid + " by user " + username);
               Logger.log("Error: " + e);
           }
       }
    
    // code ended for cr 25118
       
       /**
        * list contents from CMT_EXPORT_CONFIG table
        */
 /*   public Vector getCmtExportConfig(String property) throws ClassifiedToolException {
        Vector cmtExportConfigs = new Vector();
        CmtExportConfig cmtExportConfig;
        String sql = "select * from feedeng.cmt_export_config " +
                "where property like ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, property);
            ResultSet result = stmt.executeQuery();

            int row = 1;

            while (result.next()) {
                cmtExportConfig = new CmtExportConfig();
                cmtExportConfig.setLabel(result.getString("LABEL"));
                cmtExportConfig.setConfigName(result.getString("CONFIGNAME"));
                cmtExportConfig.setConfigText(result.getClob("CONFIGTEXT").toString());

                //append cmt to the cmts vector
                cmtExportConfigs.addElement(cmtExportConfig);
                row++;
            }
        } catch(Exception e) {
            Logger.log("Error displayting labels " + e.toString());
           
            //throw ClassifiedToolException
            ClassifiedToolException ctException = new ClassifiedToolException();
            ctException.setDisplay("Error displayting labels " + e.toString());
            throw ctException;
        }
        return cmtExportConfigs;
    }  */
}