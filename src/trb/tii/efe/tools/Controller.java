package trb.tii.efe.tools;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.File;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Collections;
import java.lang.reflect.Method;
import java.text.ParseException;


public class Controller extends HttpServlet {
  //TODO test if session times out or if we need to add logic to the doProcess() to handle that
    // HTTP
    private HttpSession session = null;
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    // Data Interact
    private DataInteract data = null;
    // Vertical Fields
    private VerticalMap vertmap = null;
    // DB
    private Connection conn = null;
    protected String dataUser;
    protected String dataPass;
    protected String dataHost;
    // Logfile
    protected String logFile;
    // Send Mail
    private SendMail sendMail = null;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    doProcess(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    doProcess(request, response);
  }

  private void doProcess(HttpServletRequest request, HttpServletResponse response) {
    session = request.getSession();
    this.request = request;
    this.response = response;

    if (session.getAttribute("user") == null && request.getRequestURL().toString().indexOf("login") == -1) {
        redirect("index.jsp");
    }
    else {
      try {
        connect();
        Logger.log("DB connection started");

      } catch (Exception e) {
        Logger.log("Error starting DB connection " + e.toString());
      }

      data = new DataInteract(conn);

      String requestUrl = request.getRequestURL().toString();
      if (requestUrl.indexOf("login") > -1) {
        doLogin();
      }
      else if (requestUrl.indexOf("results") > -1) {
        doSearchForAds();
      }
      else if (requestUrl.indexOf("editAd") > -1) {
        doEditAd();
      }
      else if (requestUrl.indexOf("editImages") > -1) {
        doEditImages();
      }
      else if (requestUrl.indexOf("updatePhotoCount") > -1) {
          doUpdatePhotoCount();
      }
      else if (requestUrl.indexOf("uploadImage") > -1) {
        doUploadImage();
      }
      else if (requestUrl.indexOf("deleteImage") > -1) {
        doDeleteImage();
      }
      else if (requestUrl.indexOf("events") > -1) {
        doEvents();
      }
      else if (requestUrl.indexOf("updateAd") > -1) {
        doUpdateAd();
      }
      else if (requestUrl.indexOf("rerunAd") > -1) {
        doRerunAd();
      }
      else if (requestUrl.indexOf("sendAd") > -1) {
        doSendAd();
      }
      else if (requestUrl.indexOf("deleteAd") > -1) {
        doDeleteAd();
      }
      else if (requestUrl.indexOf("goCreateAd") > -1) {
        goToCreateAd();
      }
      else if (requestUrl.indexOf("createAdChangeAdType") > -1) {
        goToCreateAdAndChangeAdType();
      }
      else if (requestUrl.indexOf("createAdSubmit") > -1) {
        doCreateAd();
      }
      else if (requestUrl.indexOf("searchClasses") > -1) {
          doSearchExportClasses();
      }
      else if (requestUrl.indexOf("listClasses") > -1) {
          doListExportClasses();
      }
      else if (requestUrl.indexOf("insertClass") > -1) {
          doInsertExportClass();
      }
      else if (requestUrl.indexOf("editClass") > -1) {
          doEditExportClass();
      }
      else if (requestUrl.indexOf("updateClass") > -1) {
          doUpdateExportClass();
      }
      else if (requestUrl.indexOf("deleteClass") > -1) {
          doDeleteExportClass();
      }
      else if (requestUrl.indexOf("searchCommunities") > -1) {
          goSearchCommunities();
      }
      else if (requestUrl.indexOf("listCommunities") > -1) {
          golistCommunities();
      }
      else if (requestUrl.indexOf("listCommonNames") > -1) {
          golistCommonNames();
      }
      else if (requestUrl.indexOf("insertCommunity") > -1) {
          doInsertCommunity();
      }
      else if (requestUrl.indexOf("createCommonName") > -1) {
          goCreateCommonName();
      }
      else if (requestUrl.indexOf("insertCommonName") > -1) {
          doInsertCommonName();
      }
      else if (requestUrl.indexOf("deleteCommonName") > -1) {
          doDeleteCommonName();
      }

      disconnect();
    }
  }

  private void doLogin() {
    Logger.log("logging in...");

    try {
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      ClassifiedToolUser cmtUser = login(username, password);
      Logger.log(username);

      if (cmtUser.isUser()) {
        session.setAttribute("user", cmtUser);
        session.setAttribute("dataUser", dataUser);
        session.setAttribute("dataPass", dataPass);
        session.setAttribute("dataHost", dataHost);
        session.setAttribute("merchLogin", getServletContext().getInitParameter("merchLogin"));
        session.setAttribute("merchPass", getServletContext().getInitParameter("merchPass"));
        session.setAttribute("merchDB", getServletContext().getInitParameter("merchDB"));
        redirect("main.jsp");
      }
      else {
        redirect("index.jsp?login=auth");
      }
    }
    catch (Exception e) {
      ClassifiedToolException cte = new ClassifiedToolException();
      cte.setReturnLink("index.jsp");
      session.setAttribute("classifiedException", cte);
      try {
        response.sendRedirect("classifiedError.jsp");
      }
      catch (IOException ex) {
        Logger.log("IO Exception " + ex);
      }
    }
  }

    /*
    * Preprocessing for editAd.jsp
    * Populates editAd.jsp forms
    * Only displays fields that are active in normalization logic files based on Market/Vertical
    */
    private void doEditAd() {
        try {
            String table = request.getParameter("table");
            int feedid = Integer.valueOf(request.getParameter("feedid")).intValue();
            String adid = request.getParameter("adid");
            CCI_Ad ad = data.searchEditAds (table, feedid, adid);

            // Do market specific vertical fields only on CCI tables or set vertical to Recycler
            if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(table)){

                // Load Class logic and Norm files base on market code
                String vertMarket = request.getParameter("market");
                ad.setMarket(vertMarket);
                loadVerticalMapInfo(vertMarket);
                vertmap.loadConfigData();
                // Use class id to place ad into vertical
                String verticalName = vertmap.setClassification(ad.getClassID());
                ad.setVertical(verticalName);

                // Build hashtable that sets fields to null if they are not used by the vertical for the market
                Hashtable vis_data = vertmap.setNormalize();
                session.setAttribute("visibleFields", vis_data);
            }
            else
                ad.setVertical("Recycler");

            session.setAttribute("ad", ad);

            if (ad != null) {
                redirect("editAd.jsp");
            }
            else {
                Logger.log("editAds() returned a null ad.");
            }
        }
        catch (Exception e) {
            Logger.log("Error populating edit ad " + e.toString());
            session.setAttribute("classifiedException", e);
            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
    }


    /*
    * Preprocessing for editImages.jsp
    * Populates editImages.jsp forms
    * Only displays fields that are active in normalization logic files based on Market/Vertical
    */
    private void doEditImages() {
        try {
            String table = request.getParameter("table");
            int feedid = Integer.valueOf(request.getParameter("feedid")).intValue();
            String adid = request.getParameter("adid");
            CCI_Ad ad = data.searchEditAds (table, feedid, adid);
            String admissionEnv = getServletContext().getInitParameter("admission_env");

            // Do market specific vertical fields only on CCI tables or set vertical to Recycler
            if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(table)){

                // Load Class logic and Norm files base on market code
                String vertMarket = request.getParameter("market");
                ad.setMarket(vertMarket);
                loadVerticalMapInfo(vertMarket);
                vertmap.loadConfigData();
                // Use class id to place ad into vertical
                String verticalName = vertmap.setClassification(ad.getClassID());
                ad.setVertical(verticalName);

                // Build hashtable that sets fields to null if they are not used by the vertical for the market
                Hashtable vis_data = vertmap.setNormalize();
                session.setAttribute("visibleFields", vis_data);
            }
            else
                ad.setVertical("Recycler");

            session.setAttribute("admissionEnv", admissionEnv);
            session.setAttribute("ad", ad);

            if (ad != null) {
                redirect("editImages.jsp");
            }
            else {
                Logger.log("editImages() returned a null ad.");
            }
        }
        catch (Exception e) {
            Logger.log("Error populating edit images " + e.toString());
            session.setAttribute("classifiedException", e);
            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
    }


    /*
    * Updates PHOTO_COUNT, other other future image counters
    * An update call is run in the database to change Photo_Count
    */
    private void doUpdatePhotoCount() {
        String dataResponseMessage = "The ad was updated successfully.";

        try {
            // Get values from editImages.jsp
            CCI_Ad ad = (CCI_Ad) session.getAttribute("ad");
            String imageField = request.getParameter("imageField");
            String imageValue = request.getParameter("photoCount");

            // Update Database field with new image string
            // Catch NumberFormatException for error handling
            try {
                ad.setPhotoCount(new Integer(Integer.parseInt(imageValue)));
                data.updateImage (ad, imageField, imageValue);
            } catch (NumberFormatException nfe) {
                Logger.log("Error updating photo count " + nfe.toString());
                dataResponseMessage = "The last action failed.<br>";
                dataResponseMessage += "Please correct the format of the Online Photo Count field and resubmit the changes.<br>";
            }
            ad.setDataResponseMessage(dataResponseMessage);
            session.setAttribute("ad", ad);

            // Redirect
            if (ad != null) {
                redirect("editImages.jsp");
            } else {
                Logger.log("updatePhotoCount() returned a null ad.");
            }
        } catch (Exception e) {
            Logger.log("Error updating photo count " + e.toString());

            session.setAttribute("classifiedException", e);
            try {
                response.sendRedirect("classifiedError.jsp");
            } catch (Exception ex) {
                Logger.log("Error redirecting " + ex);
            }
        }
    }


    /*
    * Uploads image to server
    * Adds new image reference to either PHOTO_REF, IMAGE_REF, or PRINTAD_IMAGE
    * Image reference includes Adid at the begining of the ad
    * TODO: Error handling: If image too large
    */
    private void doUploadImage() {

        String dataResponseMessage = "The image was uploaded successfully.";

        try {
            // Get values from editImages.jsp
            CCI_Ad ad = (CCI_Ad) session.getAttribute("ad");

            DiskFileItemFactory factory = new DiskFileItemFactory();
            // maximum size that will be stored in memory
            factory.setSizeThreshold(4096);
            // the location for saving data that is larger than getSizeThreshold()
            String tempFileDir = getServletConfig().getInitParameter("temp_image_dir");

            factory.setRepository(new File(tempFileDir));

            ServletFileUpload upload = new ServletFileUpload(factory);
            // maximum size before a FileUploadException will be thrown
            // TODO: Catch FileUploadException
            upload.setSizeMax(1000000);

            List items = upload.parseRequest(request);

            String imageField = "";
            String imageRef = "";
            String newImageValue = "";

            // Process the uploaded items
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (item.isFormField()) {
                    imageField = item.getString();
                } else {

                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    boolean isInMemory = item.isInMemory();
                    long sizeInBytes = item.getSize();
                    String contentType = item.getContentType();

                    if (fileName != "") {
                        if ((contentType.indexOf("jpeg") > 0 || contentType.indexOf("gif") > 0)) {

                            fileName = FilenameUtils.getName(fileName);
                            fileName = fileName.replace(' ', '_');
                            fileName = ad.getAdID() + "_" + fileName;

                            // Process a file upload
                            String fileDir = getServletConfig().getInitParameter("image_dir");
                            String environment = getServletConfig().getInitParameter("environment");

                            if ("dos".equals(environment)) {
                                fileDir += ad.getMarket() + "\\classified\\" + ad.getFeedName() + "\\image\\";
                            } else {
                                fileDir += ad.getMarket() + "/classified/" + ad.getFeedName() + "/image/";
                            }
                            item.write(new File(fileDir, fileName));

                            // Add new image to Database field
                            if (imageField.equals("photo_ref")) {
                                imageRef = ad.getPhotoRef();
                                newImageValue = addImageValue(fileName, imageRef);
                                ad.setPhotoRef(newImageValue);
                            } else if (imageField.equals("logo_ref")) {
                                imageRef = ad.getLogoRef();
                                newImageValue = addImageValue(fileName, imageRef);
                                ad.setLogoRef(newImageValue);
                            } else if (imageField.equals("printad_img")) {
                                imageRef = ad.getPrintAdImage();
                                newImageValue = addImageValue(fileName, imageRef);
                                ad.setPrintAdImage(newImageValue);
                            }

                            // Update Database field with new image string
                            data.updateImage(ad, imageField, newImageValue);
                        } else {
                            Logger.log("Error: Cannot upload a non image file");
                            dataResponseMessage = "The last action failed.<br>";
                            dataResponseMessage += "Please check the file type.  Image uploads can only be JPEG or GIF.<br>";
                        }
                    } else {
                        Logger.log("Error: File is missing, cannot upload");
                        dataResponseMessage = "The last action failed.<br>";
                        dataResponseMessage += "Please make sure to select a file before uploading.<br>";
                    }

                    ad.setDataResponseMessage(dataResponseMessage);
                    session.setAttribute("ad", ad);

                    // Redirect
                    if (ad != null) {
                        redirect("editImages.jsp");
                    } else {
                        Logger.log("uploadImage() returned a null ad.");
                    }
                }
            }

        } catch (Exception e) {
            Logger.log("Error uploading image " + e.toString());
            session.setAttribute("classifiedException", e);
            try {
                response.sendRedirect("classifiedError.jsp");
            } catch (Exception ex) {
                Logger.log("Error redirecting " + ex);
            }
        }

    }

    // Adds image to Database field
    private String addImageValue(String imageValue, String imageRef) {

        String newImageValue = imageRef + " " + imageValue;

        if ("".equals(imageRef)) {
            newImageValue = imageValue;
        }
        return newImageValue;
    }


    /*
    * Deletes images from PHOTO_REF, LOGO_REF, or PRINTAD_IMAGE
    * An update call is run in the database to remove images from the space delimited fields
    */
    private void doDeleteImage() {

        String dataResponseMessage = "The image was deleted successfully.";

        try {
            // Get values from editImages.jsp
            CCI_Ad ad = (CCI_Ad) session.getAttribute("ad");
            String imageField = request.getParameter("imageField");
            String imageValue = request.getParameter("imageValue");
            String imageRef = "";
            String newImageValue = "";

            // Pull out deleted image from Database field
            if (imageField.equals("photo_ref")) {
                imageRef = ad.getPhotoRef();
                newImageValue = deleteImageValue(imageValue, imageRef);
                ad.setPhotoRef(newImageValue);
            } else if (imageField.equals("logo_ref")) {
                imageRef = ad.getLogoRef();
                newImageValue = deleteImageValue(imageValue, imageRef);
                ad.setLogoRef(newImageValue);
            } else if (imageField.equals("printad_img")) {
                imageRef = ad.getPrintAdImage();
                newImageValue = deleteImageValue(imageValue, imageRef);
                ad.setPrintAdImage(newImageValue);
            }

            // Update Database field with new image string
            data.updateImage (ad, imageField, newImageValue);
            ad.setDataResponseMessage(dataResponseMessage);
            session.setAttribute("ad", ad);

            // Redirect
            if (ad != null) {
                redirect("editImages.jsp");
            } else {
                Logger.log("deleteImage() returned a null ad.");
            }
        } catch (Exception e) {
            Logger.log("Error deleting images " + e.toString());
            session.setAttribute("classifiedException", e);
            try {
                response.sendRedirect("classifiedError.jsp");
            } catch (Exception ex) {
                Logger.log("Error redirecting " + ex);
            }
        }
    }

    // Pull out deleted image from Database field
    private String deleteImageValue(String imageValue, String imageRef) {

        int start = imageRef.indexOf(imageValue);
        int stop = imageRef.indexOf(imageValue) + imageValue.length();
        if (stop < imageRef.length()) {
            stop = stop + 1;
        }
        String firstHalf = imageRef.substring(0, start);
        String secondHalf = imageRef.substring(stop, imageRef.length());
        String newImageValue = firstHalf + secondHalf;
        newImageValue = newImageValue.trim();

        return newImageValue;
    }




    /*
    * Preprocessing for events.jsp
    * Populates events.jsp forms
    * Only displays fields that are active in normalization logic files based on Market/Vertical
    */
    private void doEvents() {
        try {
            String table = request.getParameter("table");
            int feedid = Integer.valueOf(request.getParameter("feedid")).intValue();
            String adid = request.getParameter("adid");
            CCI_Ad ad = data.searchEditAds (table, feedid, adid);

            // Do market specific vertical fields only on CCI tables or set vertical to Recycler
            if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(table)){

                // Load Class logic and Norm files base on market code
                String vertMarket = request.getParameter("market");
                ad.setMarket(vertMarket);
                loadVerticalMapInfo(vertMarket);
                vertmap.loadConfigData();
                // Use class id to place ad into vertical
                String verticalName = vertmap.setClassification(ad.getClassID());
                ad.setVertical(verticalName);

                // Build hashtable that sets fields to null if they are not used by the vertical for the market
                Hashtable vis_data = vertmap.setNormalize();
                session.setAttribute("visibleFields", vis_data);
            }
            else
                ad.setVertical("Recycler");

            session.setAttribute("ad", ad);

            if (ad != null) {
                redirect("events.jsp");
            }
            else {
                Logger.log("Events() returned a null ad.");
            }
        }
        catch (Exception e) {
            Logger.log("Error populating events " + e.toString());
            session.setAttribute("classifiedException", e);
            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
    }


    private void doSearchForAds() {
        try {
            List values = new ArrayList();

            int adtype = Integer.parseInt(request.getParameter("adtype"));
            CCI_Search searchBy = new CCI_Search();

            ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
            String market = user.getMarket();
            searchBy.setMarket(market);

            String adid = request.getParameter("adid");
            searchBy.setAdID(adid);

            String classid = request.getParameter("class");
            searchBy.setClassID(classid);

            String content = request.getParameter("content");
            searchBy.setContent(content);

            String phone = request.getParameter("phone");
            searchBy.setPhone(phone);

            String email = request.getParameter("email");
            searchBy.setEmail(email);

            String dateReceived = request.getParameter("dateReceived");
            searchBy.setDateReceived(dateReceived);

            String startDate = request.getParameter("startDate");
            searchBy.setStartDate(startDate);

            values = data.searchAds(adtype, searchBy);
            session.setAttribute("ads", values);

            redirect("listAds.jsp");
        }
        catch(ClassifiedToolException e) {
            e.setReturnLink("search.jsp");
            session.setAttribute("classifiedException", e);
            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
    }

  private CCI_Ad doValidateDataAndPopulateAd() {
    CCI_Ad ad = null;
    Class adClass = null;
    Enumeration requestParamNames = request.getParameterNames();
    ArrayList requestParamNameList = Collections.list(requestParamNames);
    String errorMessage = "";
    ArrayList errorFieldNames = new ArrayList();

    if (Constants.CCI_APARTMENTS_TABLE_NAME.equals(request.getParameter("tableName"))) {
      ad = new CCI_AptAd();
      adClass = CCI_AptAd.class;
    } else if (Constants.CCI_GENCLASSIFIED_TABLE_NAME.equals(request.getParameter("tableName"))) {
      ad = new CCI_GenAd();
      adClass = CCI_GenAd.class;
    } else if (Constants.CCI_JOBS_TABLE_NAME.equals(request.getParameter("tableName"))) {
      ad = new CCI_JobAd();
      adClass = CCI_JobAd.class;
    } else if (Constants.CCI_REALESTATE_TABLE_NAME.equals(request.getParameter("tableName"))) {
      ad = new CCI_REAd();
      adClass = CCI_REAd.class;
    } else if (Constants.CCI_TRANSPORT_TABLE_NAME.equals(request.getParameter("tableName"))) {
      ad = new CCI_TransAd();
      adClass = CCI_TransAd.class;
    } else if (Constants.CCI_VITALNOTICE_TABLE_NAME.equals(request.getParameter("tableName"))) {
      ad = new CCI_VitalAd();
      adClass = CCI_VitalAd.class;
    } else if (Constants.RECYCLER_TABLE_NAME.equals(request.getParameter("tableName"))) {
      ad = new RecyclerAd();
      adClass = RecyclerAd.class;
    }

    //set all the fields on the ad object that have the same name coming
    //in from the jsp in the request parameters by using reflection to find the set method...
    for (int i = 0; i < adClass.getMethods().length; i++) {
      Method method = adClass.getMethods()[i];
      String paramNameToSet = method.getName().substring(3,method.getName().length());
      //naming convention on the jsp must match the set method on the ad object and start with a lower case
      //example: ad.setAdID(String adID)  -> should be named on jsp like "adID"
      paramNameToSet = Character.toLowerCase(paramNameToSet.charAt(0)) + paramNameToSet.substring(1, paramNameToSet.length());
      Object theDataToSet = null;
      if (method.getName().startsWith("set") &&
        requestParamNameList.contains(paramNameToSet)) {
        Class dataTypeToSet = method.getParameterTypes()[0];//assuming a set method will only take one parameter
        try {//if any of the fields don't cast properly handle it in the catch
          if (dataTypeToSet.equals(Integer.class)) {
            theDataToSet = new Integer(request.getParameter(paramNameToSet));
          }
          else if (dataTypeToSet.equals(String.class)) {
            if ((request.getParameter(paramNameToSet) == null || "".equals(request.getParameter(paramNameToSet).trim()))
                && !ad.isFieldAllowedNull(request.getParameter("tableName"), paramNameToSet.toUpperCase())) {
              throw new Exception("Error, " + paramNameToSet + " may not be empty or null.");
            }
            else {
              theDataToSet = new String(request.getParameter(paramNameToSet));
            }
          }
          else if (dataTypeToSet.equals(Date.class)) {
            theDataToSet = doSetDateValue(request.getParameter(paramNameToSet), ad);
          }
          else if (dataTypeToSet.equals(Double.class)) {
            theDataToSet = new Double(request.getParameter(paramNameToSet));
          }
          else if (dataTypeToSet.equals(Boolean.class)) {
            theDataToSet = new Boolean(request.getParameter(paramNameToSet));
          }
          else if (dataTypeToSet.equals(Long.class)) {
            theDataToSet = new Long(request.getParameter(paramNameToSet));
          }
          Object[] setData = new Object[] {theDataToSet};
          try {
            //invoke the set method on the ad, passing in the setData as a param
            method.invoke(ad, setData);
          }
          catch (Exception e) {
            //we should never reach this line
            Logger.log("Error invoking set method on ad" + e);
          }
        }
        //next exception is for the current paramNameToSet, so add that param name to the
        //list of errors so we can send it back to the user for correction...
        catch (Exception e) {

          //if we could not cast the value to the expected data type,
          //check to see if a null value is allowed...
          //may want to enhance this part later to check for value ranges or other restrictions

          //if the field must be filled in by DB constraints
          if (!ad.isFieldAllowedNull(request.getParameter("tableName"), paramNameToSet.toUpperCase())) {
            errorFieldNames.add(paramNameToSet);
          }
          //else if it doesn't have to be filled in by DB constraints
          //ANNNNNNND they tried to fill something funky in...
          //let them know they didn't get the right format on that field
          else if (request.getParameter(paramNameToSet) != null && !"".equals(request.getParameter(paramNameToSet))) {
            errorFieldNames.add(paramNameToSet);
          }
        }
      }
    }

    String dataResponseMessage = "";
    for (int i = 0; i < errorFieldNames.size(); i++) {
      if (dataResponseMessage.length() == 0) {
        dataResponseMessage += "The last action failed.<br>";
        dataResponseMessage += "Please correct the format of the following fields and resubmit the changes:<br>";
        dataResponseMessage += "<ul>";//create an unordered html list
      }
      dataResponseMessage += "<li>" + errorFieldNames.get(i) + "</li>";//each field name is a list item
      if (i == errorFieldNames.size() -1) {
        dataResponseMessage += "</ul>";
      }
    }

    ad.setDataResponseMessage(dataResponseMessage);

    //return the values to the page that the user just entered regardless of update success
    session.setAttribute("ad", ad);
    return ad;
  }


    private void doUpdateAd() {

        CCI_Ad ad = doValidateDataAndPopulateAd();
        //if we parsed all the parameters and everything was ok
        //go ahead and update the ad...
        if (ad.getDataResponseMessage().length() == 0) {

            int count = data.updateAd(ad);
            // if ad update successful...
            if (count > 0){

                // update transmit log table
                ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
                String username = user.getUsername();
                data.updateAdTransmitLog(ad, username);

                // create sendMail instance, then send email to user
                sendMailInfo();
                try {
                    sendMail.sendUpdateMessage(ad);
                }
                catch (Exception e) {
                    Logger.log ("Error sending email " + e);
                }
            }

            // Do Market specific vertical fields only on CCI tables or set vertical to Recycler
            if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(ad.getTableName())){
                // Load Class logic and Norm files base on market code
                String vertMarket = request.getParameter("market");
                loadVerticalMapInfo(vertMarket);
                vertmap.loadConfigData();
                // Use class id to place ad into vertical
                String verticalName = vertmap.setClassification(ad.getClassID());
                ad.setVertical(verticalName);
                // Build hashtable that sets fields to null if they are not used by the vertical for the market
                Hashtable vis_data = vertmap.setNormalize();
                session.setAttribute("visibleFields", vis_data);
            }
            else
                ad.setVertical("Recycler");
            //  End Market specifiec vertical fields
        }
        redirect("editAd.jsp");
    }

  private Date doSetDateValue(String dateString, CCI_Ad ad) throws Exception {
    Date theDate = null;
    try {//try to parse it as a long format yyyy/MM/dd hh:mm:ss aa
    theDate = ad.getLongDateFromString(dateString);
    }
    catch (ParseException e) {
      try {//try to parse it as a short format yyyy/MM/dd
        theDate = ad.getShortDateFromString(dateString);
      }
      catch (ParseException ex) {
        throw new Exception(ex);//finally throw exception if it is in neither long or short format
      }
    }
    return theDate;
  }

    /**
     * Remove ad node from listAds.jsp of corresponding ad
     * Remove ad from database based on Feedid, Adid, and TableName
     */
    private void doDeleteAd() {
        try {
            // Extract all of the request parameters and assign appropriate objects to them
            List ads = (ArrayList)session.getAttribute("ads");
            String id = request.getParameter("adid");
            String table = request.getParameter("table");
            String publication = request.getParameter("publication");
            int feedid = Integer.parseInt(request.getParameter("feedid"));

            ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");

            // Retrieve appropriate ad from Session List "ads" and get table name
            int length = ads.size();
            int location = 0;
            CCI_Search ad = new CCI_Search();

            for (int i=0; i<length; i++)
            {
               CCI_Search tmp = (CCI_Search)ads.get(i);
               if (tmp.getAdID().equals(id) && tmp.getTableName().equals(table)
             && (tmp.getFeedID() == feedid) && tmp.getPublication().equals(publication))
               {
                  location = i;
                  ad = tmp;
               }
            }

            String tableName = ad.getTableName();

            // Remove entry from database and remove node from listAds.jsp
            data.deleteAd(ad.getFeedID(), ad.getAdID(), tableName);
			// added for cr 25118           
            String username = user.getUsername();
            data.deleteAdTransmitLog(id, feedid, username);
			// ended for cr 25118
            ads.remove(location);
            session.setAttribute("ads", ads);

            redirect("listAds.jsp");

        }
        catch(ClassifiedToolException e) {

            e.setReturnLink("search.jsp");
            session.setAttribute("classifiedException", e);

        try {
            response.sendRedirect("classifiedError.jsp");
        }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
        }
      }
    }

    /*
    * Preprocessing for createAd.jsp
    * Populates createAd.jsp forms
    * Only displays fields that are active in normalization logic files based on Market/Vertical
    */
    private void goToCreateAd() {
        try {
            CCI_Ad ad = new CCI_Ad();
            session.setAttribute("ad", ad);

            String newVertName = "Apts";
            ad.setVertical(newVertName);
            session.setAttribute("createAd_vertName", newVertName);

            ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
            String market = user.getMarket();
            session.setAttribute("market", market);

            // Generate List to hold all possible feed names
            List feedNames = new ArrayList();
            feedNames = data.listCreateAdFeeds(market);

            String feedId = "";
            String pubName = "";
            String vertMarket = "";

            Iterator feedListIterator = feedNames.iterator();
            MarketRecord record;

            if ( feedListIterator.hasNext() ) {
                record = ( MarketRecord ) feedListIterator.next();

                feedId = record.getFeedID();
                pubName = record.getPubName();
                vertMarket = record.getMarket();
            }
            session.setAttribute("feedNames", feedNames);
            session.setAttribute("createAd_feedID", feedId);
            session.setAttribute("createAd_pubName", pubName);

            // Load Class logic and Norm files base on market code
            loadVerticalMapInfo(vertMarket);
            Boolean loadCheck = vertmap.loadConfigData();

            if (loadCheck.equals(new Boolean(true))){
                // Build hashtable that sets fields to null if they are not used by the market's vertical
                Hashtable vis_data = vertmap.setNormalize(newVertName);
                String table = vertmap.setTableName(newVertName);

                session.setAttribute("visibleFields", vis_data);
                session.setAttribute("tableName", table);

                redirect("createAd.jsp");
            }
            else if (loadCheck.equals(new Boolean(false))){
                redirect("createAd.jsp?load=false");
            }

        }
        catch(ClassifiedToolException e) {
            e.setReturnLink("exportLookup.jsp");
            session.setAttribute("classifiedException", e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
    }



    private void doCreateAd() {
        CCI_Ad ad = doValidateDataAndPopulateAd();
        ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
        String username = user.getUsername();

        //if we parsed all the parameters and everything was ok
        //go ahead and update the ad...
        if (ad.getDataResponseMessage().length() == 0) {
            int count = data.insertAd(ad);
            if (count > 0){
                data.insertAdTransmitLog(ad, username);
            }
        }                                
        redirect("createAd.jsp");
    }

    /*
    * Preprocessing for createAd.jsp
    * Populates createAd.jsp forms
    * Only displays fields that are active in normalization logic files based on Market/Vertical
    */
    private void goToCreateAdAndChangeAdType() {

        String feedId = (String)request.getParameter("feedID");
        String pubName = (String)session.getAttribute("createAd_pubName");
        String oldVertName = (String)session.getAttribute("createAd_vertName");
        String newVertName = (String)request.getParameter("vertName");
        List feedNames = (ArrayList) session.getAttribute("feedNames");
        String vertMarket = "";
        CCI_Ad ad = new CCI_Ad();

        if (oldVertName != null && !oldVertName.equals(newVertName)) {
            session.setAttribute("ad", ad);
        }

        Iterator feedListIterator = feedNames.iterator();
        MarketRecord record;

        while ( feedListIterator.hasNext() ) {
            record = ( MarketRecord ) feedListIterator.next();

            if (feedId.equals(record.getFeedID())) {
                pubName = record.getPubName();
                vertMarket = record.getMarket();
            }
        }

        ad.setVertical(newVertName);

        session.setAttribute("createAd_feedID", feedId);
        session.setAttribute("createAd_pubName", pubName);
        session.setAttribute("createAd_vertName", newVertName);

        // Load Class logic and Norm files base on market code
        loadVerticalMapInfo(vertMarket);
        Boolean loadCheck = vertmap.loadConfigData();

        if (loadCheck.equals(new Boolean(true))){
            // Build hashtable that sets fields to null if they are not used by the market's vertical
            Hashtable vis_data = vertmap.setNormalize(newVertName);
            String table = vertmap.setTableName(newVertName);

            session.setAttribute("visibleFields", vis_data);
            session.setAttribute("tableName", table);

            redirect("createAd.jsp");
        }
        else if (loadCheck.equals(new Boolean(false))){
            redirect("createAd.jsp?load=false");
        }
    }


    private void doRerunAd() {

        //extract all of the request parameters and assign appropriate objects to them
        CCI_Ad ad = (CCI_Ad)session.getAttribute("ad");
        ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
        String username = user.getUsername();

        Date startDate = null;
        Date stopDate = null;

        try {
            // Extract parameters for date values
            try {
                startDate = ad.getShortDateFromString(request.getParameter("newStartDate"));
                stopDate = ad.getShortDateFromString(request.getParameter("newStopDate"));
            }
            catch (ParseException ex) {
                Logger.log ("Error extracting date values " + ex);
            }
            // Call rerunAd() in DateInteract and rerun current ad with new startDate and stopDate
            int count = data.rerunAd(ad, startDate, stopDate);
            // if ad update successful, update transmit log table
            if (count > 0){
                data.rerunAdTransmitLog(ad, username);
            }
        }
        catch (ClassifiedToolException e)
        {
            e.setReturnLink("editAd.jsp");

            session.setAttribute("classifiedException", e);
            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
        redirect("editAd.jsp");
    }

    private void doSearchExportClasses() {

        try {
            //get the user object from the session object and then the market from the user object
            ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
            String market = user.getMarket();

            List feedNames = new ArrayList();
            List vendorNames = new ArrayList();

            feedNames = data.listExportFeeds(market);
            vendorNames = data.listExportVendors();

            session.setAttribute("feedNames", feedNames);
            session.setAttribute("vendorNames", vendorNames);
            redirect("searchClasses.jsp");
        }
        catch(ClassifiedToolException e) {
            e.setReturnLink("exportLookup.jsp");
            session.setAttribute("classifiedException", e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
    }


    public void doListExportClasses() {

        try {
            List values = new ArrayList();

            //get the user object from the session object and then the market from the user object
            ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");

            Integer feedid = new Integer(Integer.parseInt(request.getParameter("feedID")));
            String classid = request.getParameter("classID");
            String ccicategory = request.getParameter("cciCategory");
            String ccidescription = request.getParameter("cciDescription");
            String vendorid = request.getParameter("vendorID");
            //String recyclercat = request.getParameter("recyclerCategory");

            if (classid.equals(""))
                classid = "%";
            else
                classid = classid.trim();

            if (ccicategory.equals(""))
                ccicategory = "%";

            if (ccidescription.equals(""))
                ccidescription = "%";

            if (ccicategory.equals(""))
                classid = "%";

            if (vendorid.equals(""))
                vendorid = "%";

            //if (recyclercat.equals(""))
            //    recyclercat = "%";

            values = data.listExportClasses(feedid, classid, ccicategory, ccidescription, vendorid);
            session.setAttribute("classRecords", values);
            session.setAttribute("exportClass_feedid", feedid);
            //session.setAttribute("exportClass_feedid", Integer.parseInt(feedid.intValue()));

            redirect("listClasses.jsp");
        }
        catch(ClassifiedToolException e) {
            e.setReturnLink("searchClasses.jsp");
            session.setAttribute("classifiedException", e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
    }


    private void doInsertExportClass() {

        try {

            // Extract all of the request parameters and assign appropriate objects to them
            List records = (ArrayList)session.getAttribute("classRecords");
            List vendorList = (ArrayList) session.getAttribute("vendorNames");

            int feedid = Integer.parseInt(request.getParameter("feedID"));
            String vendorid = request.getParameter("vendorID");
            String classid = request.getParameter("classID");
            String ccicategory = request.getParameter("cciCategory");
            String ccidescription = request.getParameter("cciDescription");
            String recyclercat = request.getParameter("recyclerCategory");

            // Trim classid
            classid = classid.trim();

            ExportClassRecord record = new ExportClassRecord();

            record.setFeedID(feedid);
            record.setVendorID(vendorid);
            record.setOldVendorID(vendorid);
            record.setClassID(classid);
            record.setOldClassID(classid);
            record.setCciCategory(ccicategory);
            record.setCciDescription(ccidescription);
            record.setRecyclerCategory(recyclercat);

            // TODO: Work out bug if class update fails and return page shows updated class
            int count = 0;
            count = data.insertExportClass(record);

            if (count > 0) {
                // Change Vendor name in record if Vendor ID is updated
                Iterator vendorListIterator = vendorList.iterator();
                ExportClassRecord vendor;

                while ( vendorListIterator.hasNext() ) {
                    vendor = ( ExportClassRecord ) vendorListIterator.next();

                    if (vendor.getVendorID().equals(record.getVendorID())) {
                        record.setVendorName(vendor.getVendorName());
                    }
                }
                records.add(record);
                // Todo: Add success message to session and post to listClasses.jsp
                // Todo: Example, Class #### and Vendor #### added successfuly

            }
            // Todo: Add else statement for success/error response on listClasses.jsp to notify if class inserted
            session.setAttribute("classRecords", records);

            redirect("listClasses.jsp");
        }
        catch(ClassifiedToolException e) {
            e.setReturnLink("createClass.jsp");
            session.setAttribute("classifiedException", e);
            Logger.log("Error inserting new Export Class: " + e);

        try {
            response.sendRedirect("classifiedError.jsp");
        }
        catch (Exception ex) {
            Logger.log("Error redirecting to classifiedError: " + ex);
        }
      }

    }


    private void doEditExportClass() {

        try {

            List records = (ArrayList)session.getAttribute("classRecords");

            int feedid = Integer.parseInt(request.getParameter("feedid"));
            String vendorid = request.getParameter("vendorid");
            String classid = request.getParameter("classid");


            // Retrieve appropriate record from Session List "records" and get table name
            int length = records.size();
            //int location = 0;
            ExportClassRecord record = new ExportClassRecord();

            for (int i=0; i<length; i++)
            {
               ExportClassRecord tmp = (ExportClassRecord) records.get(i);
               if (tmp.getFeedID() == feedid && tmp.getVendorID().equals(vendorid) && tmp.getClassID().equals(classid))
               {
                  //location = i;
                  record = tmp;
               }
            }
            session.setAttribute("classRecord", record);
            redirect("editClass.jsp");
        }
        catch (Exception e) {
            redirect("searchClasses.jsp");
            session.setAttribute("classifiedException", e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
    }


    public void doUpdateExportClass() {

        try {

            // Extract all of the request parameters and assign appropriate objects to them
            // List records = (ArrayList)session.getAttribute("classRecords");
            List vendorList = (ArrayList) session.getAttribute("vendorNames");

            //int feedid = Integer.parseInt(request.getParameter("feedID"));
            String vendorid = request.getParameter("vendorID");
            String classid = request.getParameter("classID");
            String ccicategory = request.getParameter("cciCategory");
            String ccidescription = request.getParameter("cciDescription");
            String recyclercat = request.getParameter("recyclerCategory");

            // Trim classid
            classid = classid.trim();

            ExportClassRecord record = new ExportClassRecord();
            ExportClassRecord oldrecord = (ExportClassRecord) session.getAttribute("classRecord");
            record = oldrecord;

            //record.setFeedID(feedid);
            record.setVendorID(vendorid);
            record.setClassID(classid);
            record.setCciCategory(ccicategory);
            record.setCciDescription(ccidescription);
            record.setRecyclerCategory(recyclercat);

            // Run updateExportClass on the record's new values.  If count > 1, then give revert to old record.
            int count = 0;
            count = data.updateExportClass(record);

            //if (count < 1 ) {
            //    record = oldrecord;
            //}

            // Change Vendor name in record if Vendor ID is updated
            Iterator vendorListIterator = vendorList.iterator();
            ExportClassRecord vendor;

            while ( vendorListIterator.hasNext() ) {
                vendor = ( ExportClassRecord ) vendorListIterator.next();

                if (vendor.getVendorID().equals(record.getVendorID())) {
                    record.setVendorName(vendor.getVendorName());
                }
            }

            redirect("listClasses.jsp");

        }
        catch(ClassifiedToolException e) {

            e.setReturnLink("searchClasses.jsp");
            session.setAttribute("classifiedException", e);

        try {
            response.sendRedirect("classifiedError.jsp");
        }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
        }
      }
    }


    private void doDeleteExportClass() {

        try {
            // Extract all of the request parameters and assign appropriate objects to them
            List records = (ArrayList)session.getAttribute("classRecords");
            int feedid = Integer.parseInt(request.getParameter("feedid"));
            String vendorid = request.getParameter("vendorid");
            String classid = request.getParameter("classid");

            // Retrieve appropriate record from Session List "classRecords" and get table name
            int length = records.size();
            int location = 0;
            ExportClassRecord record = new ExportClassRecord();

            for (int i=0; i<length; i++)
            {
               ExportClassRecord tmp = (ExportClassRecord) records.get(i);
               if (tmp.getFeedID() == feedid && tmp.getVendorID().equals(vendorid) && tmp.getClassID().equals(classid))
               {
                  location = i;
                  record = tmp;
               }
            }

            // Remove entry from database and remove node from listAds.jsp
            int test = 0;
            test = data.deleteExportClass(record.getFeedID(), record.getVendorID(), record.getClassID());
            records.remove(location);
            session.setAttribute("classRecords", records);

            redirect("listClasses.jsp");
        }
        catch(ClassifiedToolException e) {
            e.setReturnLink("search.jsp");
            session.setAttribute("classifiedException", e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        }
    }


    /**
     * Extract State Values for use in searchCommunities.jsp
     */
    public void goSearchCommunities() {

        try {
            Vector stateValues = data.listCommStates();
            session.setAttribute("commStateValues", stateValues);

            redirect("searchCommunities.jsp");
        }
        catch (ClassifiedToolException e) {
            e.setReturnLink("main.jsp");
            session.setAttribute("classifiedException", e);
            Logger.log("Error getting State Values for RE Communities: " + e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log("Error redirecting to classifiedError: " + ex);
            }
        }
    }

    /**
     * Return list of communities for use in listCommunities.jsp
     */
    public void golistCommunities() {

        try {
            String state = request.getParameter("state");

            Vector values = data.searchCommunities(state);
            session.setAttribute("listCommunitiesValues", values);

            redirect("listCommunities.jsp");
        }
        catch (ClassifiedToolException e) {
            e.setReturnLink("main.jsp");
            session.setAttribute("classifiedException", e);
            Logger.log("Error getting Community Listings for RE Communities: " + e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log("Error redirecting to classifiedError: " + ex);
            }
        }
    }

    /**
     * Return list of communities for use in listCommunities.jsp
     */
    public void golistCommonNames() {

        try {
            ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
            String market = user.getMarket();
            int communityid = Integer.parseInt(request.getParameter("communityid"));
            String community = request.getParameter("community");

            Vector values = data.listCommonNames(communityid, market);
            session.setAttribute("listCommonNamesValues", values);

            redirect("listCommonNames.jsp?communityid=" + communityid + "&community=" + community);
        }
        catch (ClassifiedToolException e) {
            e.setReturnLink("main.jsp");
            session.setAttribute("classifiedException", e);
            Logger.log("Error getting Community Listings for RE Communities: " + e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log("Error redirecting to classifiedError: " + ex);
            }
        }
    }

    /**
     * Insert new RE community into database from createCommunity.jsp
     */
    public void doInsertCommunity() {

        try {
            String neighborhood = request.getParameter("neighborhood");
            String community = request.getParameter("community");
            String city = request.getParameter("city");
            String county = request.getParameter("county");
            String state = request.getParameter("state");

            Community comm = new Community();
            comm.setNeighborhood(neighborhood);
            comm.setCommunity(community);
            comm.setCity(city);
            comm.setCounty(county);
            comm.setState(state);
            data.insertCommunity(comm);

            redirect("listCommunities?state=" + state);
        }
        catch (ClassifiedToolException e) {
            e.setReturnLink("createCommunity.jsp");
            session.setAttribute("classifiedException", e);
            Logger.log("Error inserting new Community: " + e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log("Error redirecting to classifiedError: " + ex);
            }
        }
    }

    /**
     * Insert new RE community into database from createCommunity.jsp
     */
    public void goCreateCommonName() {

        try {
            ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
            String market = user.getMarket();

            int communityid = Integer.parseInt(request.getParameter("communityid"));
            String community = request.getParameter("community");

            Vector reConfigs = data.listREConfig(market);
            session.setAttribute("reConfigs", reConfigs);

            redirect("createCommonName.jsp?communityid=" + communityid + "&community=" + community);
        }
        catch (ClassifiedToolException e) {
            e.setReturnLink("searchCommunities");
            session.setAttribute("classifiedException", e);
            Logger.log("Error populating RE config information: " + e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log("Error redirecting to classifiedError: " + ex);
            }
        }
    }

    /**
     * Insert new RE common name into database from createCommonName.jsp
     */
    public void doInsertCommonName() {

        try {
            int feedid = Integer.parseInt(request.getParameter("feedid"));
            String name = request.getParameter("name");

            int communityid = Integer.parseInt(request.getParameter("communityid"));
            String community = request.getParameter("community");

            CommonName cmn = new CommonName();
            cmn.setCommunityid(communityid);
            cmn.setFeedid(feedid);
            cmn.setName(name);

            data.insertCommonName(cmn);

            redirect("listCommonNames?communityid=" + communityid + "&community=" + community);
        }
        catch (ClassifiedToolException e) {
            e.setReturnLink("searchCommunities");
            session.setAttribute("classifiedException", e);
            Logger.log("Error inserting new Common Name: " + e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log("Error redirecting to classifiedError: " + ex);
            }
        }
    }

    /**
     * Delete RE common name from database
     */
    public void doDeleteCommonName() {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int communityid = Integer.parseInt(request.getParameter("communityid"));
            String community = request.getParameter("community");

            data.deleteCommonName(id, communityid);
            redirect("listCommonNames?communityid=" + communityid + "&community=" + community);

        }
        catch (ClassifiedToolException e) {
            e.setReturnLink("listCommonNames.jsp");
            session.setAttribute("classifiedException", e);
            Logger.log("Error deleting Common Name: " + e);

            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log("Error redirecting to classifiedError: " + ex);
            }
        }
    }

  public ClassifiedToolUser login(String username, String password) {
      String eol = System.getProperty("line.separator");
      Logger.log("user attempting to log in.");
      Logger.log("user : " + username);

      ClassifiedToolUser user = new ClassifiedToolUser();
      user.setUsername(username);
      user.setPassword(password);

      String queryString = new String("select username, password, market, email from classifiedtoolusers " +
        "where username = '" + username + "' and password = '" + password + "'");
      ResultSet result;
      Logger.log(queryString);

      try {
          PreparedStatement statement = conn.prepareStatement(queryString);
          result = statement.executeQuery();
          if (result.next()) {
            Logger.log("user successfully logging in...");
            user.setIsUser(true);

            if (result.getString("MARKET") != null)
              user.setMarket(result.getString("MARKET"));

            if (result.getString("EMAIL") != null)
              user.setEmail(result.getString("EMAIL"));
          }
          else {
            Logger.log("username and password not found in database");
          }
      }
      catch(Exception e) {
          Logger.log("error logging in " + e.toString());
      }

      return user;
  }

  private void connect() throws SQLException {
      logFile = getServletConfig().getInitParameter("log_filename");
      Logger.setLogDetails(logFile);
      Logger.log ("using logfile: " + logFile);

      // Establish database connection
      dataUser = getServletContext().getInitParameter("dataUserName");
      dataPass = getServletContext().getInitParameter("dataPassword");
      dataHost = getServletContext().getInitParameter("dataHost");

      Logger.log("connecting to database " + dataHost);
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      conn = DriverManager.getConnection(dataHost, dataUser, dataPass);
      Logger.log("successfully established connection");
  }

  private void redirect(String location) {
    try {
      response.sendRedirect(location);
      Logger.log("Redirecting to " + location);
    } catch (Exception e) {
      Logger.log("Error sending redirect " + e);
    }
  }

  private void disconnect() {
    try {
      conn.close();
      Logger.log("DB connection closed");
    } catch (Exception e) {
      Logger.log("Error closing DB connection " + e.toString());
    }
  }

    // Get send mail header info and load into SendMail
    private void sendMailInfo() {

        try {
            ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
            String mailTo = user.getEmail();
            String mailFrom = getServletContext().getInitParameter("from");
            String mailCC = getServletContext().getInitParameter("cc");
            String mailSTMP = getServletContext().getInitParameter("stmp");

            sendMail = new SendMail(mailTo, mailFrom, mailCC, mailSTMP);
        }
        catch (Exception e){
            Logger.log("Error getting send mail info: " + e);
        }
    }

    private void loadVerticalMapInfo(String marketStr) {

        try {
            String classDir = getServletConfig().getInitParameter("class_dir");
            String normDir = getServletConfig().getInitParameter("norm_dir");

            vertmap = new VerticalMap(marketStr, classDir, normDir);
        }
        catch (Exception e){
            Logger.log("Error getting vertical map info: " + e);
        }
    }
    
    //Send ad 
    private void doSendAd() {
 
        //extract all of the request parameters and assign appropriate objects to them
        CCI_Ad ad = (CCI_Ad)session.getAttribute("ad");
        ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
        String username = user.getUsername();
        
        String exportRootDir = getServletContext().getInitParameter("export_root_dir");
        String exportCfgDir = getServletContext().getInitParameter("export_cfg_dir");
        String exportCmd = getServletContext().getInitParameter("export_cmd");
        String exportWorkDir = getServletContext().getInitParameter("export_work_dir");
        String exportBinDir = getServletContext().getInitParameter("export_bin_dir");

        try {
            // Call sendAd() in DateInteract 
            //add log to test
            Logger.log ("starting the send ad process.");
            int count = data.sendAd(ad, exportRootDir, exportBinDir, exportCfgDir, exportCmd, exportWorkDir);
            // if ad update successful, update transmit log table
            if (count > 0) 
                data.rerunAdTransmitLog(ad, username);
        } catch  (ClassifiedToolException e) {
            e.setReturnLink("editAd.jsp");
            session.setAttribute("classifiedException", e);
            try {
                response.sendRedirect("classifiedError.jsp");
            }
            catch (Exception ex) {
                Logger.log ("Error redirecting " + ex);
            }
        } 
        
        redirect("editAd.jsp");
    }
    
}

