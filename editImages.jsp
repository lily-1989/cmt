<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*,
                                                             java.io.File"%>

<%
  CCI_Ad ad = (CCI_Ad)session.getAttribute("ad");
  String admissionEnv = (String)session.getAttribute("admissionEnv");
  String tableName = ad.getTableName();
%>

<html>

<head>
    <title>Edit Images</title>
    <link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<script type = "text/javascript">
<!--

function confirmDelete() {
    var myConfirm = confirm("Sure you want to Delete?");
    return myConfirm
}

-->
</script>
</head>


<body bgcolor="#ffffff" text="#000000" link="#003366" vlink="#336699" alink="#990000" leftmargin="0" topmargin="2" marginwidth="0" marginheight="2">
  <table width="760" border="0" cellspacing="0" cellpadding="0">
    <font class="text">
    <%@include file="include/topbar.html"%>
    <tr>
      <%@include file="include/leftnav.html"%>
      <td bgcolor="#ffffff" align="left" width="5%"> </td>
      <td valign="top">
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tr>
            <td><img src="images/clear.gif" height="1" width="12"></td>
            <td width="100%">
              <table cellpadding="4" cellspacing="0" border="0" bgcolor="#cccccc">
                <tr><br>
                  <td><font class="page-header">Edit Images</font></td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td><img src="images/clear.gif" height="1" width="12"></td>
           <!-- Note: there should be no whitespace around the img tag, to prevent the cell
           from containing any text, which causes the bar to be thicker than a hairline -->
            <td bgcolor="#000000"><img src="images/clear.gif" height="1" width="100%"></td>
          </tr>

          <!--main table-->
          <table width="610" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
            <tr>
              <td rowspan="24" width="12" bgcolor="#ffffff"><img src="images/clear.gif" width="1" height="1" alt=""></td>
              <td>

                <!--beginning of edit ad table -->
                <table border = "0" cellspacing ="0" cellpadding = "4" align="center" width="100%">
                    <tr>
                        <td colspan="2">
                            <span class="edit-label"><a href = "editAd?adid=<%=ad.getAdID()%>&feedid=<%=ad.getFeedID()%>&table=<%=ad.getTableName()%>&market=<%= ad.getMarket() %>">Edit Ad</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <span class="edit-label">Edit Images</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <span class="edit-label"><a href="events?adid=<%= ad.getAdID() %>&feedid=<%= ad.getFeedID() %>&table=<%= ad.getTableName() %>&market=<%= ad.getMarket()%>">Events</a></span>
                        </td>
                    </tr>

               <%  if (ad.getDataResponseMessage() != null) { %>
               <tr>
                  <td colspan="2"><span class="edit-label">
                   <%      if (ad.getDataResponseMessage().indexOf("failed") > -1) { %>
                    <font color="red">
                   <%      } %>
                    <%= ad.getDataResponseMessage() %>
                   <%      if (ad.getDataResponseMessage().indexOf("failed") > -1) { %>
                    </font>
                  <%      } %>
                  </span></td>
               </tr>
               <% } %>

                    <!-- Ad Information Section -->
                    <input type = "hidden" name = "publication" value = "<%= ad.getPublication() %>"/>
                    <input type = "hidden" name = "feedID" value = "<%= ad.getFeedID() %>"/>
                    <input type = "hidden" name = "adID" value = "<%= ad.getAdID() %>"/>
                    <input type = "hidden" name = "tableName" value = "<%= ad.getTableName() %>"/>
                    <input type = "hidden" name = "market" value = "<%= ad.getMarket() %>"/>
                    <input type = "hidden" name = "vertical" value = "<%= ad.getVertical()%>" />
                    <input type = "hidden" name = "insertionID" value = "<%= ad.getInsertionID() %>"/>
                    <input type = "hidden" name = "firstReceived" value = "<%= ad.getFirstReceived() == null ? "" : ad.getDateAsLongString(ad.getFirstReceived()) %>"/>
                    <input type = "hidden" name = "dateReceived" value = "<%= ad.getDateReceived() == null ? "" : ad.getDateAsLongString(ad.getDateReceived()) %>"/>
                    <input type = "hidden" name = "dateUpdated" value = "<%= ad.getDateUpdated() == null ? "" : ad.getDateAsLongString(ad.getDateUpdated()) %>"/>
                    <tr>
                      <th class="td-title-bg" colspan="2" align="left"><span class="table-header">Ad Summary</span></th>
                    </tr>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Ad ID:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getAdID() %></span></td>
                    </tr>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Publication:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getPublication() %> - <%= ad.getFeedID() %></span></td>
                    </tr>
                    <% if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(tableName)) { %>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Vertical:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getVertical() %> (<%= ad.getTableName() %>)</span></td>
                    </tr>
                    <tr>
                      <th class="tdL1" colspan="2"><hr color="white"></th>
                    </tr>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">First Received:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getFirstReceived() == null ? "N/A" : ad.getDateAsLongString(ad.getFirstReceived()) %></span></td>
                    </tr>
                    <% } %>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Date Received:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getDateReceived() == null ? "N/A" : ad.getDateAsLongString(ad.getDateReceived()) %></span></td>
                    </tr>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Date Updated:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getDateUpdated() == null ? "N/A" : ad.getDateAsLongString(ad.getDateUpdated()) %></span></td>
                    </tr>
                    <tr>
                      <th colspan="2">&nbsp;</th>
                    </tr>
                    <tr>
                      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Image Details</span></th>
                    </tr>
<% if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(tableName)) { %>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Online Photo Count:</span></th>
<%
    if ("ORL".equalsIgnoreCase(ad.getMarket()) || "LAT".equalsIgnoreCase(ad.getMarket())){
%>
                      <form name ="photoCountSubmit" method = "post" action = "updatePhotoCount">
                      <input type = "hidden" name = "imageField" value = "photo_count">
                      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "2" name = "photoCount" maxlength = "2" value = "<%= ad.getPhotoCount() %>"/></span><br>
                      <input type = "submit" value = "Update" /><br><br>


<%
        // Need to look into putting this into a look up table
        String ipixMarket = "";
        String ipixSubDomain = "";

        if ("ORL".equalsIgnoreCase(ad.getMarket())){
            if ("test".equalsIgnoreCase(admissionEnv)){
                ipixMarket = "OSCTEST";
                ipixSubDomain = "view-preprod";
            }
            else {
                ipixMarket = "OSCPROD";
                ipixSubDomain = "view";
            }
        }
        else if  ("LAT".equalsIgnoreCase(ad.getMarket())){
            if ("test".equalsIgnoreCase(admissionEnv)){
                ipixMarket = "LATIMESTEST";
                ipixSubDomain = "view-preprod";
            }
            else {
                ipixMarket = "LATIMESPROD";
                ipixSubDomain = "view";
            }
        }


        int count = 1;
        while(count <= ad.getPhotoCount().intValue()) {
%>
                	  <a href="http://<%=ipixSubDomain%>.admission.net/abc/woe/_<%=ipixMarket%>_<%=ad.getAdID()%>/JPG?ifm=primaryimage,<%=count%>"><img src="http://<%=ipixSubDomain%>.admission.net/abc/woe/_<%=ipixMarket%>_<%=ad.getAdID()%>/JPG?ifm=primaryimage,<%=count%>&t=tr/w:220/h:120/m:FitPad"/></a><br>
                      <span class="edit-imagetext">Admission Photo #<%=count%></span><br><br>
<%
            count++;
        }
%>
                      </form>
<%
    } else {
%>
                      <td><span class="edit-text"><%=ad.getPublication()%> is currently not set up for Online Photos</span><br><br>
<%
    }
%>
                      </td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Photos:</span></th>
                      <td class="edit-bg2" align = "left" valign="top">

                      <table class="upload">
                        <form name="uploadPhoto" method="post" enctype="multipart/form-data" action="uploadImage">
                        <input type = "hidden" name = "imageField" value = "photo_ref">
                          <tr>
                            <th>Upload Photo</th>
                          </tr>
                          <tr>
                            <td><input type="file" name="photo"></td>
                          </tr>
                          <tr>
                            <td align="right"><input type="submit" value="Upload   "></td>
                          </tr>
                        </form>
                      </table><br><br>
<%
                      if (!ad.getPhotoRef().equals("")) {

                          String photoString = ad.getPhotoRef();
                          String photoName = "";

                          // If contains only 1 image
                          if ((photoString.indexOf(" ") == -1)) {
                              photoName = photoString;
%>
                        <form name ="photoSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "photo_ref"/>
                            <input type = "hidden" name = "imageValue" value = "<%= photoName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=photoName%>"><br>
                            <span class="edit-imagetext"><%=photoName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                          }
                          // Otherwise handle mutliple images
                          else {
                              photoName = photoString.substring(0, photoString.indexOf(" "));
%>
                        <form name ="photoSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "photo_ref"/>
                            <input type = "hidden" name = "imageValue" value = "<%= photoName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=photoName%>"><br>
                            <span class="edit-imagetext"><%=photoName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                              while (photoString.indexOf(" ") > 0) {

                                  photoString = photoString.substring(photoString.indexOf(" ") + 1);
                                  if (photoString.indexOf(" ") > 0) {
                                      photoName = photoString.substring(0, photoString.indexOf(" "));
%>
                        <form name ="photoSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "photo_ref"/>
                            <input type = "hidden" name = "imageValue" value = "<%= photoName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=photoName%>"><br>
                            <span class="edit-imagetext"><%=photoName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                                  }
                                  else {
                                      photoName = photoString;
%>
                        <form name ="photoSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "photo_ref"/>
                            <input type = "hidden" name = "imageValue" value = "<%= photoName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=photoName%>"><br>
                            <span class="edit-imagetext"><%=photoName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                                  }
                              }
                          }
                        } %>
                      </td>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Logos:</span></th>
                      <td class="edit-bg1" align = "left" valign="top">

                      <table class="upload">
                        <form name="uploadLogo" method="post" enctype="multipart/form-data" action="uploadImage">
                        <input type = "hidden" name = "imageField" value = "logo_ref">
                          <tr>
                            <th>Upload Logo</th>
                          </tr>
                          <tr>
                            <td><input type="file" name="logo" ></td>
                          </tr>
                          <tr>
                            <td align="right"><input type="submit" value="Upload   "></td>
                          </tr>
                        </form>
                      </table><br><br>
<%
                      if (!ad.getLogoRef().equals("")) {

                          String logoString = ad.getLogoRef();
                          String logoName = "";

                          // If contains only 1 image
                          if ((logoString.indexOf(" ") == -1)) {
                              logoName = logoString;
%>
                        <form name ="logoSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "logo_ref"/>
                            <input type = "hidden" name = "imageValue" value = "<%= logoName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=logoName%>"><br>
                            <span class="edit-imagetext"><%=logoName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                          }
                          // Otherwise handle mutliple images
                          else {
                              logoName = logoString.substring(0, logoString.indexOf(" "));
%>
                        <form name ="logoSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "logo_ref"/>
                            <input type = "hidden" name = "imageValue" value = "<%= logoName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=logoName%>"><br>
                            <span class="edit-imagetext"><%=logoName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                              while (logoString.indexOf(" ") > 0) {

                                  logoString = logoString.substring(logoString.indexOf(" ") + 1);
                                  if (logoString.indexOf(" ") > 0) {
                                      logoName = logoString.substring(0, logoString.indexOf(" "));
%>
                        <form name ="logoSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "logo_ref"/>
                            <input type = "hidden" name = "imageValue" value = "<%= logoName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=logoName%>"><br>
                            <span class="edit-imagetext"><%=logoName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                                  }
                                  else {
                                      logoName = logoString;
%>
                        <form name ="logoSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "logo_ref"/>
                            <input type = "hidden" name = "imageValue" value = "<%= logoName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=logoName%>"><br>
                            <span class="edit-imagetext"><%=logoName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                                  }
                              }
                          }
                        } %>
                      </td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Print Ad Image:</span></th>
                      <td class="edit-bg2" align = "left" valign="top">

                      <table class="upload">
                        <form name="uploadPrintAd" method="post" enctype="multipart/form-data" action="uploadImage">
                        <input type = "hidden" name = "imageField" value = "printad_img">
                          <tr>
                            <th>Upload Print Ad Image</th>
                          </tr>
                          <tr>
                            <td><input type="file" name="printAd"></td>
                          </tr>
                          <tr>
                            <td align="right"><input type="submit" value="Upload   "></td>
                          </tr>
                        </form>
                      </table><br><br>
<%
                      if (!ad.getPrintAdImage().equals("")) {

                          String printAdString = ad.getPrintAdImage();
                          String printAdName = "";

                          // If contains only 1 image
                          if ((printAdString.indexOf(" ") == -1)) {
                              printAdName = printAdString;
%>
                        <form name ="printAdSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "printad_img"/>
                            <input type = "hidden" name = "imageValue" value = "<%= printAdName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=printAdName%>"><br>
                            <span class="edit-imagetext"><%=printAdName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                          }
                          // Otherwise handle mutliple images
                          else {
                              printAdName = printAdString.substring(0, printAdString.indexOf(" "));
%>
                        <form name ="printAdSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "printad_img"/>
                            <input type = "hidden" name = "imageValue" value = "<%= printAdName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=printAdName%>"><br>
                            <span class="edit-imagetext"><%=printAdName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                              while (printAdString.indexOf(" ") > 0) {

                                  printAdString = printAdString.substring(printAdString.indexOf(" ") + 1);
                                  if (printAdString.indexOf(" ") > 0) {
                                      printAdName = printAdString.substring(0, printAdString.indexOf(" "));
%>
                        <form name ="printAdSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "printad_img"/>
                            <input type = "hidden" name = "imageValue" value = "<%= printAdName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=printAdName%>"><br>
                            <span class="edit-imagetext"><%=printAdName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                                  }
                                  else {
                                      printAdName = printAdString;
%>
                        <form name ="printAdSubmit" method = "post" action = "deleteImage" onsubmit = "return confirmDelete()">
                            <input type = "hidden" name = "imageField" value = "printad_img"/>
                            <input type = "hidden" name = "imageValue" value = "<%= printAdName %>"/>
                            <img src="/feeddrop/<%=ad.getMarket()%>/classified/<%=ad.getFeedName()%>/image/<%=printAdName%>"><br>
                            <span class="edit-imagetext"><%=printAdName%></span><br>
                            <input type = "submit" value = "Delete" /><br><br>
                        </form>
<%
                                  }
                              }
                          }
                        } %>
                      </td>
                    </tr>
                    <% } else { %>
                    <tr>
                       <td colspan="2"><span class="edit-text"><%=ad.getPublication()%> is currently not set up for Edit Images</span></td>
                    </tr>
                    <% } %>
                </table>

              </td>
            </tr>
          <!-- end main table -->
          </table>

          <br><br><br>
          <tr>
            <td colspan="3" bgcolor="#000000" width="100%"><img src="images/clear.gif" height="1" width="100%"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</body>
</html>



