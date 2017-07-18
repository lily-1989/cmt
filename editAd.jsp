<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%
  CCI_Ad ad = (CCI_Ad)session.getAttribute("ad");
  String tableName = ad.getTableName();
  String vertName = ad.getVertical();
  String jspIncludeFileName = "";

  if (tableName != null && tableName.equalsIgnoreCase(Constants.CCI_APARTMENTS_TABLE_NAME)) {
    jspIncludeFileName = "editAptsAd.jsp";
  }
  else if (tableName != null && tableName.equalsIgnoreCase(Constants.CCI_JOBS_TABLE_NAME)) {
    jspIncludeFileName = "editJobsAd.jsp";
  }
  else if (tableName != null && tableName.equalsIgnoreCase(Constants.CCI_REALESTATE_TABLE_NAME)) {
      if (vertName != null && vertName.equalsIgnoreCase("RE")) {
          jspIncludeFileName = "editREAd.jsp";
      }
      else if (vertName != null && vertName.equalsIgnoreCase("CommRE")) {
          jspIncludeFileName = "editCommREAd.jsp";
      }
  }
  else if (tableName != null && tableName.equalsIgnoreCase(Constants.CCI_TRANSPORT_TABLE_NAME)) {
      if (vertName != null && vertName.equalsIgnoreCase("Cars")) {
          jspIncludeFileName = "editCarsAd.jsp";
      }
      else if (vertName != null && vertName.equalsIgnoreCase("MiscTrans")) {
          jspIncludeFileName = "editMiscTransAd.jsp";
      }
      else if (vertName != null && vertName.equalsIgnoreCase("Marine")) {
          jspIncludeFileName = "editMarineAd.jsp";
      }
  }
  else if (tableName != null && tableName.equalsIgnoreCase(Constants.CCI_GENCLASSIFIED_TABLE_NAME)) {
      if (vertName != null && vertName.equalsIgnoreCase("Merch")) {
          jspIncludeFileName = "editMerchAd.jsp";
      }
      else if (vertName != null && vertName.equalsIgnoreCase("Pets")) {
          jspIncludeFileName = "editPetsAd.jsp";
      }
      else if (vertName != null && vertName.equalsIgnoreCase("Garage")) {
          jspIncludeFileName = "editGarageAd.jsp";
      }
      else if (vertName != null && vertName.equalsIgnoreCase("Celebrations")) {
          jspIncludeFileName = "editCelebrationsAd.jsp";
      }
  }
  else if (tableName != null && tableName.equalsIgnoreCase(Constants.CCI_VITALNOTICE_TABLE_NAME)) {
    jspIncludeFileName = "editVitalAd.jsp";
  }
  else if (tableName != null && tableName.equalsIgnoreCase(Constants.RECYCLER_TABLE_NAME)) {
    jspIncludeFileName = "editRecyclerAd.jsp";
  }

%>

<html>

<head>
    <title>Edit Ad</title>
    <link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
    <SCRIPT LANGUAGE="JavaScript" SRC="include/calendarpopup/CalendarPopup.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">var cal = new CalendarPopup("testdiv1");</SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
    <DIV ID="testdiv1" STYLE="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></DIV>

    <SCRIPT LANGUAGE="JavaScript">
    function checkDates() {

    var startDateVar = document.rerun.newStartDate;
    var stopDateVar = document.rerun.newStopDate;

    var startDateResult = true;
    var stopDateResult = true;

    var regex = /(19|20)\d\d[/](0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])/;


    if ((!startDateVar.value.match(regex))) {
        startDateResult = false;
    }

    if ((!stopDateVar.value.match(regex))) {
        stopDateResult = false;
    }


    if (!startDateResult && !stopDateResult) {
        alert ('Start Date and Stop Date must be in the format yyyy/mm/dd');
        startDateVar.focus();
        return false;
    }
    else if (!startDateResult && stopDateResult) {
        alert ('Start Date must be in the format yyyy/mm/dd');
        startDateVar.focus();
        return false;
    }
    else if (startDateResult && !stopDateResult) {
        alert ('Stop Date must be in the format yyyy/mm/dd');
        stopDateVar.focus();
        return false;
    }
    else {
        return true;
    }

    }
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
                  <td><font class="page-header">Edit Ad</font></td>
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
                            <span class="edit-label">Edit Ad</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <span class="edit-label"><a href="editImages?adid=<%=ad.getAdID()%>&feedid=<%=ad.getFeedID()%>&table=<%=ad.getTableName()%>&market=<%= ad.getMarket()%>">Edit Images</a></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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

                  <form name ="adSubmit" method = "post" action = "updateAd">

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
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Ad ID:</font></span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getAdID() %></span></td>
                    </tr>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Publication:</span></th>
                      <td class="td-on"><span class="edit-text"><%= ad.getPublication() %> - <%= ad.getFeedID() %></span></td>
                    </tr>
                    <% if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(tableName)) { %>
                    <tr>
                      <th class="td-on" align = "left" valign="top"><span class="edit-label">Vertical:</font></span></th>
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
                      <th colspan="2"><hr></th>
                    </tr>
                    <tr>
                       <td colspan="2"><span class="edit-label">Note: </span><span class="edit-reqtext">Required fields*</span></td>
                    </tr>
                    <tr>
                      <th colspan="2">&nbsp;</th>
                    </tr>
                    <tr>
                      <th class="edit-header-bg" colspan="2" align="left"><span class="table-header">Ad Information</span></th>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Class:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "8" name = "classID" maxlength = "8" value = "<%= ad.getClassID() %>"/></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Start Date:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "startDate" maxlength = "10" value = "<%= ad.getStartDate() == null ? "" : ad.getDateAsShortString(ad.getStartDate()) %>"/></span>
                        <A HREF="#" onClick="cal.select(document.forms['adSubmit'].startDate,'anchor.startDate','yyyy/MM/dd'); return false;" NAME="anchor.startDate" ID="anchor.startDate"><img src = "images/cal.gif" border="0" alt="calendar"></A>
                        &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
                      </td>
                    </tr>
                    <% if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(tableName)) { %>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Stop Date:</span><span class="edit-reqlabel">*</span> </th>
                      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "stopDate" maxlength = "10" value = "<%= ad.getStopDate() == null ? "" : ad.getDateAsShortString(ad.getStopDate()) %>"/></span>
                        <A HREF="#" onClick="cal.select(document.forms['adSubmit'].stopDate,'anchor.stopDate','yyyy/MM/dd'); return false;" NAME="anchor.stopDate" ID="anchor.stopDate"><img src = "images/cal.gif" border="0" alt="calendar"></A>
                        &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
                      </td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Commercial Release:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg2" class="tdR"><span class="edit-text">
                      True <input type = "radio" name="commercialRelease" value="true" <% if ("true".equals(ad.getCommercialRelease().toLowerCase())) { %>checked<% } %> />
                      False <input type="radio" name="commercialRelease" value="false" <% if ("false".equals(ad.getCommercialRelease().toLowerCase())) { %>checked<% } %>/></span>
                      <input type="radio" name="commercialRelease" value="" style="display:none;" <% if ("".equalsIgnoreCase(ad.getCommercialRelease())) { %>checked<% } %>/>
                      <br><span class="edit-reqhelptext">(Required to go online)</span>
                      </td>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Canceled:</span></th>
                      <td class="edit-bg1"><span class="edit-text">
                      Yes <input type = "radio" name="canceled" value="1" <% if (new Integer(1).equals(ad.getCanceled())) { %>checked<% } %> />
                      No <input type="radio" name="canceled" value="0" <% if (new Integer(0).equals(ad.getCanceled())) { %>checked<% } %>/></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Upsell:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "50" name = "upsell" maxlength = "50" value = "<%= ad.getUpsell() %>"/></span></td>
                    </tr>
                    <tr>
                      <th colspan="2">&nbsp;</th>
                    </tr>
                    <tr>
                      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Billing Information</span></th>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Customer City:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "customerCity" maxlength = "50" value = "<%= ad.getCustomerCity() %>"/></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Customer State:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "3" name = "customerState" maxlength = "3" value = "<%= ad.getCustomerState() %>"/></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Customer Zip:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "customerZip" maxlength = "10" value = "<%= ad.getCustomerZip() %>"/></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Customer Email:</span></th>
                      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "60" name = "customerEmail" maxlength = "100" value = "<%= ad.getCustomerEmail() %>"/></span></td>
                    </tr>
                      <input type = "hidden" name = "photoCount" value = "<%= ad.getPhotoCount() %>"/>
                      <input type = "hidden" name = "photoRef" value = "<%= ad.getPhotoRef() %>"/>
                      <input type = "hidden" name = "logoRef" value = "<%= ad.getLogoRef() %>"/>
                      <input type = "hidden" name = "printAdImage" value = "<%= ad.getPrintAdImage() %>"/>

                    <% } %>


                    <!-- Include specific ad type fields for editing -->
                    <jsp:include page='<%= jspIncludeFileName %>' />

                    <% if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(tableName)) { %>
                    <tr>
                      <th colspan="2">&nbsp;</th>
                    </tr>
                    <tr>
                      <th align = "left" class="edit-header-bg" colspan="2"><span class="edit-header">Add Additional Description</span></th>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Title:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "60" name = "title" maxlength = "300" value = "<%= ad.getTitle() %>"/></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Print Description:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg2"><span class="edit-text"><textarea name = "printDesc" rows = "15" cols = "40"><%= ad.getPrintDesc()%></textarea></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Enhanced Description:</span></th>
                      <td class="edit-bg1"><span class="edit-text"><textarea name = "enhancedDesc" rows = "15" cols = "40"><%= ad.getEnhancedDesc()%></textarea></span></td>
                    </tr>
                    <% }  %>

                    </tr>
                    <tr class="td-off">
                      <td>&nbsp;</td>
                      <td valign = "middle" align = "left"><input type = "submit" value = "Update" /> <input type = "reset"/></td>
                    </tr>
                    <tr>
                      <th colspan="2">&nbsp;</th>
                    </tr>
                  </form>

                  <!-- Re-run ad section -->
                  <% if (!Constants.RECYCLER_TABLE_NAME.equalsIgnoreCase(tableName)) { %>
                  <form name = "rerun" method = "post" action = "rerunAd" onsubmit = "return checkDates()">
                    <tr>
                      <th colspan = "2" align = "left" class="td-title-bg"><span class="table-header">Re-run this ad</span></th>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Start Date:</font></th>
                      <td class="edit-bg1">
                          <span class="edit-text"><input type = "text" size = "10" name = "newStartDate" maxlength = "10" value = "<%= ad.getDateAsShortString(new Date()) %>"/></span>
                          <A HREF="#" onClick="cal.select(document.forms['rerun'].newStartDate,'anchor.newStartDate','yyyy/MM/dd'); return false;" NAME="anchor.newStartDate" ID="anchor.newStartDate"><img src = "images/cal.gif" border="0" alt="calendar"></A>
                          &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
                      </td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Stop Date:</font></th>
                      <td class="edit-bg2">
                          <span class="edit-text"><input type = "text" size = "10" name = "newStopDate" maxlength = "10" value = "<%= ad.getDateAsShortString(new Date()) %>"/></span>
                          <A HREF="#" onClick="cal.select(document.forms['rerun'].newStopDate,'anchor.newStopDate','yyyy/MM/dd'); return false;" NAME="anchor.newStopDate" ID="anchor.newStopDate"><img src = "images/cal.gif" border="0" alt="calendar"></A>
                          &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
                      </td>
                    </tr>
                    <tr class="td-off">
                      <td>&nbsp;</td>
                      <td align = "left"><input type = "submit" value = "Re-run"/></td>
                    </tr>
                  </form>
                  <% } %>

                  <!-- Send ad section -->
                  <form name = "sendad" method = "post" action = "sendAd">
                    <tr>
                      <th colspan = "2" align = "left" class="td-title-bg"><span class="table-header">Send this ad</span></th>
                    </tr>
                    <tr class="td-off">
                      <td>&nbsp;</td>
                      <td align = "left"><input type = "submit" value = "Send-Ad"/></td>
                    </tr>
                  </form>

                 <!-- end edit ad table -->
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



