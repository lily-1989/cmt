<%@ page language="java" errorPage="errorPage.jsp" import="java.util.*, java.text.*, trb.tii.efe.tools.*, com.oroinc.text.perl.*" %>

<%
  //check to make sure session
  if (session.getAttribute("user") == null)
  {
     response.sendRedirect("index.jsp");
  }

    String check = "";

    if ("false".equals(request.getParameter("load")))
    {
        check = request.getParameter("load");
    }

    List feedNames = (ArrayList) session.getAttribute("feedNames");

    //get the user object from the session object and then the market from the user object
    ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
    String market = user.getMarket();
    CCI_Ad ad = (CCI_Ad)session.getAttribute("ad");

    // Create request for tableName spring and default at CCI_Apts
    String tableName = (String)session.getAttribute("tableName");
    String vertName = (String)session.getAttribute("createAd_vertName");
    String feedId = (String)session.getAttribute("createAd_feedID");
    String pubName = (String)session.getAttribute("createAd_pubName");

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

    Hashtable visfields = new Hashtable(100);
    visfields = (Hashtable) session.getAttribute("visibleFields");

%>

<html>
<head>
    <title>Create Ad</title>
    <link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
    <SCRIPT LANGUAGE="JavaScript" SRC="include/calendarpopup/CalendarPopup.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">var cal = new CalendarPopup("caldiv");</SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
    <DIV ID="caldiv" STYLE="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></DIV>

    <script type="text/javascript">
    function setfocus() {
        document.forms[0].adID.focus()
    }
    </script>
</head>

<script language="javascript">

function adTypeOnChange() {
  document.adTypeForm.submit();
}

</script>

<body bgcolor="#ffffff" text="#000000" link="#003366" vlink="#336699" alink="#990000" leftmargin="0" topmargin="2" marginwidth="0" marginheight="2" onload=setfocus()>

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
              <td><font class="page-header">Create Ad</font></td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>

        <td><img src="images/clear.gif" height="1" width="12"></td>

        <!-- Note: there should be no whitespace around the img tag,

             to prevent the cell from containing any text, which causes the bar to be thicker than a hairline -->

        <td bgcolor="#000000"><img src="images/clear.gif" height="1" width="100%"></td>

      </tr>

<!--main table-->

<table width="610" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
<tr>
   <th bgcolor="#ffffff"><img src="images/clear.gif" height="4" width="100%"></th>
</tr>
<tr>

  <td rowspan="24" width="12" bgcolor="#ffffff"><img src="images/clear.gif" width="1" height="1" alt=""></td>

  <td>

 <% if (!"false".equals(check)) { %>

                 <!--create ad table-->
                <table border = "0" cellspacing = "0" cellpadding = "4" align="center" width="100%">

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

                <!-- Choose the feed form -->
                <form name="adTypeForm" method="post" action="createAdChangeAdType">
                <tr>
                   <th class="td-title-bg" colspan="2" align="left"><span class="table-header">Select Publication and Vertical</span></th>
                </tr>
                <tr>
                   <th class="tdL1" align = "left"><span class="edit-label">Publication:</span></th>
                   <td class="tdR1" >
                       <select name="feedID" size="1" onChange="adTypeOnChange()">
                       <%
                           Iterator feedListIterator = feedNames.iterator();
                           MarketRecord record;

                           while ( feedListIterator.hasNext() ) {

                               record = ( MarketRecord ) feedListIterator.next();
                       %>
                           <option  <%  if (record.getFeedID().equals(feedId)) { %>selected<% } %> value="<%= record.getFeedID() %>"><%= record.getPubName() %> - <%= record.getFeedID() %></option>
                       <%      }       %>
                       </select>
                   </td>
                </tr>
                <tr>
                   <th class="tdL1" align = "left"><span class="edit-label">Vertical:</span></th>
                   <td class="tdR1" >
                     <select type="select" size = "1" name="vertName" onChange="adTypeOnChange()">
                        <option <% if ("Apts".equals(vertName)) { %>selected<% } %>
                          value="<%= "Apts" %>" >Apartments</option>
                        <option <% if ("Jobs".equals(vertName)) { %>selected<% } %>
                          value = "<%= "Jobs" %>" >Jobs</option>
                        <option <% if ("Cars".equals(vertName)) { %>selected<% } %>
                          value = "<%= "Cars" %>" >Cars</option>
                        <option <% if ("Celebrations".equals(vertName)) { %>selected<% } %>
                          value = "<%= "Celebrations" %>" >Celebrations</option>
                        <option <% if ("Marine".equals(vertName)) { %>selected<% } %>
                          value = "<%= "Marine" %>" >Marine</option>
                        <option <% if ("MiscTrans".equals(vertName)) { %>selected<% } %>
                          value = "<%= "MiscTrans" %>" >Misc Transport</option>
                        <option <% if ("Merch".equals(vertName)) { %>selected<% } %>
                          value = "<%= "Merch" %>" >Merchandise</option>
                        <option <% if ("Garage".equals(vertName)) { %>selected<% } %>
                          value = "<%= "Garage" %>" >Garage Sales</option>
                        <option <% if ("Pets".equals(vertName)) { %>selected<% } %>
                          value = "<%= "Pets" %>" >Pets</option>
                        <option <% if ("RE".equals(vertName)) { %>selected<% } %>
                          value = "<%= "RE" %>" >Real Estate</option>
                        <option <% if ("CommRE".equals(vertName)) { %>selected<% } %>
                          value = "<%= "CommRE" %>" >Commercial Real Estate</option>
                        <option <% if ("Vital".equals(vertName)) { %>selected<% } %>
                          value = "<%= "Vital" %>" >Vital Notices</option>
                     </select>
                  </td>
                </tr>
               </form>

               <!-- Create ad and submit form -->
               <form name="adSubmit" method = "post" action = "createAdSubmit">
               <input type="hidden" name="tableName" value="<%= tableName %>"/>
               <input type="hidden" name="vertName" value="<%= vertName %>"/>
               <input type="hidden" name="insertionID" value="<%= ad.getInsertionID() %>"/>
               <input type="hidden" name="feedID" value="<%= feedId %>"/>
               <input type = "hidden" name = "publication" maxlength = "20" value = "<%= pubName %>"/>
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
                       <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Ad ID:</span><span class="edit-reqlabel">*</span></th>
                       <td class="edit-bg1"> <span class="edit-text"><input type = "text" size = "10" name = "adID" maxlength = "10" value = "<%= ad.getAdID() %>"/></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Class:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "8" name = "classID" maxlength = "8" value = "<%= ad.getClassID() %>"/></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Start Date:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "10" name = "startDate" maxlength = "10" value = "<%= ad.getStartDate() == null ? "" : ad.getDateAsShortString(ad.getStartDate()) %>"/></span>
                        <A HREF="#" onClick="cal.select(document.forms['adSubmit'].startDate,'anchor.startDate','yyyy/MM/dd'); return false;" NAME="anchor.startDate" ID="anchor.startDate"><img src = "images/cal.gif" border="0" alt="calendar"></A>
                        &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
                      </td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Stop Date:</span><span class="edit-reqlabel">*</span> </th>
                      <td class="edit-bg2"><span class="edit-text"><input type = "text" size = "10" name = "stopDate" maxlength = "10" value = "<%= ad.getStopDate() == null ? "" : ad.getDateAsShortString(ad.getStopDate()) %>"/></span>
                        <A HREF="#" onClick="cal.select(document.forms['adSubmit'].stopDate,'anchor.stopDate','yyyy/MM/dd'); return false;" NAME="anchor.stopDate" ID="anchor.stopDate"><img src = "images/cal.gif" border="0" alt="calendar"></A>
                        &nbsp<span class="edit-helptext">(yyyy/mm/dd)</span>
                      </td>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Commercial Release:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg1" class="tdR"><span class="edit-text">
                      True <input type = "radio" name="commercialRelease" value="true" <% if ("true".equals(ad.getCommercialRelease().toLowerCase())) { %>checked<% } %> />
                      False <input type="radio" name="commercialRelease" value="false" <% if ("false".equals(ad.getCommercialRelease().toLowerCase())) { %>checked<% } %>/></span>
                      <input type="radio" name="commercialRelease" value="" style="display:none;" <% if ("".equalsIgnoreCase(ad.getCommercialRelease())) { %>checked<% } %>/>
                      <br><span class="edit-reqhelptext">(Required to go online)</span>
                      </td>
                    </tr>
                    <tr>
                      <th class="edit-bg2" align = "left" valign="top"><span class="edit-label">Canceled:</span></th>
                      <td class="edit-bg2"><span class="edit-text">
                      Yes <input type = "radio" name="canceled" value="1" <% if (new Integer(1).equals(ad.getCanceled())) { %>checked<% } %> />
                      No <input type="radio" name="canceled" value="0" <% if (new Integer(0).equals(ad.getCanceled())) { %>checked<% } %>/></span></td>
                    </tr>
                    <tr>
                      <th class="edit-bg1" align = "left" valign="top"><span class="edit-label">Upsell:</span><span class="edit-reqlabel">*</span></th>
                      <td class="edit-bg1"><span class="edit-text"><input type = "text" size = "50" name = "upsell" maxlength = "50" value = "<%= ad.getUpsell() %>"/></span></td>
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

               <jsp:include page='<%= jspIncludeFileName %>' />

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
               <tr>
                  <td>&nbsp;</td>
                  <td valign = "middle" align = "left">
                     <input type = "submit" value = "Create"/>
                     <input type = "reset"/>
                  </td>
               </tr>
               </form>
           </table>


<!--end insert Ad table -->
<% } else { %>

     <tr>
       <td><span class = "edit-label">Your market is not set up for Create Ad.</span></td>
     </tr>
     <tr>
       <td><span class = "edit-label">&nbsp;</span></td>
     </tr>
     <tr>
       <td><span class = "edit-label">Please contact <a href="mailto:feedsupport@tribuneinteractive.com">feedsupport@tribuneinteractive.com</a> if you have any questions.</span></td>
     </tr>

<% } %>



  </td>
</tr>
</table>

<br><br><br>

<!--end main table -->

<tr><td colspan="3" bgcolor="#000000" width="100%"><img src="images/clear.gif" height="1" width="100%"></td></tr>

</table>

</td>

</tr>

</table>

</body>

</html>


