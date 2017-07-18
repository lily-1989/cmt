<%@ page language="java" errorPage="errorPage.jsp" import="java.lang.*, java.text.*, java.util.*, trb.tii.efe.reversePublishing.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%     
  //verify session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  //String market = user.getMarket();

    //get and store insertTable to session object
    session.setAttribute("insertTable", "feedeng.car_rp_adv");
    
    // retrieve zone/edition map (see carsReverseTools.jsp)
    Hashtable allZones = (Hashtable)session.getAttribute("allZones");
    // get feedid
    String feedid = request.getParameter("feedid");
%>


<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Add Advertiser
</title>
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
              <td><font class="page-header">Add Advertiser</font></td>
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
  <td rowspan="24" width="12" bgcolor="#ffffff"><img src="images/clear.gif" width="1" height="1" alt=""></td>
  <td>

<script>
function validate(form) {
  var error = '';
  // map form input names to display names. for text fields only!
  fieldmap = {
    'dealerid'     : 'Cars.com ID'         ,
    'duration'     : 'Days in Print'       ,
    'dayoffset'    : 'Day Offset'          ,
    'advaccount'   : 'Advertiser Account #',
    'advname'      : 'Advertiser Name'     ,
    'billingphone' : 'Advertiser Phone'    ,
    'advemail'     : 'Advertiser Email'
  };
  for (var name in fieldmap) {
    if (form[name].value == '') {
      alert(fieldmap[name] + ' is required.');
      return false;
    }
  }
  return true;
}
</script>

<!-- add class table -->
<table width="100%">
  <tr>
     <td valign="top" align="left">
       <form action="insert.jsp" method="post" onSubmit="return validate(this)">
	 <input type="hidden" name="feedid" value="<%= request.getParameter("feedid") %>"/>

	<table width="100%">
	  <tr>
	     <td colspan="2"><span class="table-text"><font color="red">*</font> = required fields</font></span></td></tr>
	  <tr>
	  <tr>
	     <th align="left" colspan="2" class="td-title-bg"><span class="table-header">Reverse Publishing Details</span></th>
	  </tr>
<%
  int tog = -1;
%>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Cars.com ID<font color="red">*</font></span> <span class="table-helptext"></span></td>
	     <td align="left"><input type="text" name="dealerid" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Status<font color="red">*</font></span></td>
	     <td align="left">
	       <input type="radio" name="status" value="1" checked> Enabled
	       <input type="radio" name="status" value="0"> Disabled
	     </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Days in Print<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="duration" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Start Day<font color="red">*</font></span></td>
	     <td align="left">
	       <select name="startday">
	       <option value="1">Sunday</option>
	       <option value="2">Monday</option>
	       <option value="3">Tuesday</option>
	       <option value="4">Wednesday</option>
	       <option value="5">Thursday</option>
	       <option value="6">Friday</option>
	       <option value="7">Saturday</option>
	       </select>
	     </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Day Offset<font color="red">*</font></span> <span class="table-helptext">(type value)</span></td>
	     <td align="left">
	       <input type="text" name="dayoffset" size="3"/>&nbsp; &nbsp;
	     </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Max Cars</span> <span class="table-helptext">(leave blank for unlimited)</span></td>
	     <td align="left"><input type="text" name="maxcars" size="40"/></td>
          </tr>

	  <tr>
	     <th align="left" colspan="2" class="td-title-bg"><span class="table-header">AdStar Configuration Details</span></th>
	  </tr>

          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Advertiser Account #<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="advaccount" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Advertiser Name<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="advname" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Advertiser Phone<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="billingphone" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Advertiser Email<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="advemail" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Edition<font color="red">*</font></span></td>
	     <td align="left">
		 <select name="edition">
<%
   String[][] zoneMap = (String[][])allZones.get(feedid);
   if (zoneMap != null) {
       for (int i=0; i<zoneMap.length; i++) {
           String zoneCode = zoneMap[i][0];
           String zoneName = zoneMap[i][1];
%>
                 <option value="<%= zoneCode %>"><%= zoneCode %> - <%= zoneName %></option>
<%
       }
   }
%>
		 </select>
	     </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Rate Code</span></td>
	     <td align="left"><input type="text" name="ratecode" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Logo Code</span></td>
	     <td align="left"><input type="text" name="logocode" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Icon Code</span></td>
	     <td align="left"><input type="text" name="iconcode" size="40"/></td>
          </tr>
          <tr>
             <td>&nbsp;</td>
             <td><input type="submit" name="insert" value="add"/> <input type="reset" value="clear"/></td>
          </tr>
       </table>
       </form>
    </td>
  </tr>
</table>
<!-- add class table -->

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
