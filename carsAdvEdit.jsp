<%@ page language="java" errorPage="errorPage.jsp" import="java.lang.*, java.text.*, java.util.*, trb.tii.efe.reversePublishing.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%
  //verify session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  //String market = user.getMarket();
%>
<%
    // retrieve zone/edition map (see carsReverseTools.jsp)
    Hashtable allZones = (Hashtable) session.getAttribute("allZones");
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Edit Advertiser
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
              <td><font class="page-header">Edit Advertiser</font></td>
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

<!-- edit class table -->
<% 

   try
   {   
	DataField _feedid = new DataField();
	DataField _dealerid = new DataField();
	DataField _advaccount = new DataField();
	DataField _billingphone = new DataField();
	DataField _advname = new DataField();
	DataField _advemail = new DataField();
	DataField _logocode = new DataField();
	DataField _iconcode = new DataField();
	DataField _status = new DataField();
	DataField _duration = new DataField();
	DataField _startday = new DataField();
	DataField _dayoffset = new DataField();
	DataField _maxcars = new DataField();
	DataField _ratecode = new DataField();
	DataField _edition = new DataField();

         String feedidValue = request.getParameter("feedid");
         String dealeridValue = request.getParameter("dealerid");
         
         RecordSet advertisers = (RecordSet)session.getAttribute("advertisers");
         
         //find the record''s place in the Vector
         System.out.println("getting record from advertisers");
         Hashtable clauses = new Hashtable();
         clauses.put("feedid", feedidValue);
         clauses.put("dealerid", dealeridValue);
         Hashtable record = advertisers.findRecord(clauses);

         if (record != null)
         {
	    _feedid = (DataField)record.get("feedid");
	    _dealerid = (DataField)record.get("dealerid");
	    _advaccount = (DataField)record.get("advaccount");
	    _billingphone = (DataField)record.get("billingphone");
	    _advname = (DataField)record.get("advname");
	    _advemail = (DataField)record.get("advemail");
	    _logocode = (DataField)record.get("logocode");
	    _iconcode = (DataField)record.get("iconcode");
	    _status = (DataField)record.get("status");
	    _duration = (DataField)record.get("duration");
	    _startday = (DataField)record.get("startday");
	    _dayoffset = (DataField)record.get("dayoffset");
	    _maxcars = (DataField)record.get("maxcars");
	    _ratecode = (DataField)record.get("ratecode");
	    _edition = (DataField)record.get("edition");

            //get and store updateClauses to session object
            //this is used to identify a unique row for update
	    Hashtable updateClauses = new Hashtable();
	    updateClauses.put("feedid", _feedid);
	    updateClauses.put("dealerid", _dealerid);
            session.setAttribute("updateClauses", updateClauses);
            
            //get and store updataTable to session object
            session.setAttribute("updateTable", "feedeng.car_rp_adv");
         }
%>

<script>
function validate(form) {
  var error = '';
  // map form input names to display names. for text fields only!
  fieldmap = {
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

<table border="0" width="100%">
        <tr>
          <td align = "left" valign = "top">

       <form action="updateRP.jsp" method="post" onSubmit="return validate(this)">
	 <table width = "100%" border="0" cellspacing="2" cellpadding="1">		
	  <tr>
	     <td colspan="2"><span class="table-text"><font color="red">*</font> = required fields</font></span></td></tr>
	  <tr>
          <tr>
            <th colspan="2" align="left" class="td-title-bg"><span class="table-header">Reverse Publishing Details</span></th>
          </tr>
<%
  int tog = -1;
%>
          <input type="hidden" name="feedid" value="<%= _feedid.getValue()%>"/>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Cars.com ID</span> <span class="table-helptext"></span></td>
	     <td align="left"><%= _dealerid.getValue() %><input type="hidden" name="dealerid" value="<%= _dealerid.getValue() %>"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Status<font color="red">*</font></span></td>
	     <td align="left">
	       <input type="radio" name="status" value="1" <%= _status.getValue().equals("1") ? "checked" : "" %>> Enabled
	       <input type="radio" name="status" value="0" <%= _status.getValue().equals("0") ? "checked" : "" %>> Disabled
	     </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Days in Print<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="duration" value="<%= _duration.getValue() %>" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Start Day<font color="red">*</font></span></td>
	     <td align="left">
	       <select name="startday">
	       <option value="1" <%= _startday.getValue().equals("1") ? "selected" : "" %>>Sunday</option>
	       <option value="2" <%= _startday.getValue().equals("2") ? "selected" : "" %>>Monday</option>
	       <option value="3" <%= _startday.getValue().equals("3") ? "selected" : "" %>>Tuesday</option>
	       <option value="4" <%= _startday.getValue().equals("4") ? "selected" : "" %>>Wednesday</option>
	       <option value="5" <%= _startday.getValue().equals("5") ? "selected" : "" %>>Thursday</option>
	       <option value="6" <%= _startday.getValue().equals("6") ? "selected" : "" %>>Friday</option>
	       <option value="7" <%= _startday.getValue().equals("7") ? "selected" : "" %>>Saturday</option>
	       </select>
	     </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Day Offset<font color="red">*</font></span> <span class="table-helptext">(type value)</span></td>
              <td align="left">
               <input type="text" name="dayoffset" value="<%= _dayoffset.getValue() %>" size="3"/>&nbsp; &nbsp;
             </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Max Cars</span> <span class="table-helptext">(leave blank for unlimited)</span></td>
	     <td align="left"><input type="text" name="maxcars" value="<%= _maxcars.isNull ? "" : _maxcars.getValue() %>" size="40"/></td>
          </tr>
	  <tr>
	     <th align="left" colspan="2" class="td-title-bg"><span class="table-header">AdStar Configuration Details</span></th>
	  </tr>

          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Advertiser Account #<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="advaccount" value="<%= _advaccount.getValue() %>" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Advertiser Name<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="advname" value="<%= _advname.getValue() %>" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Advertiser Phone<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="billingphone" value="<%= _billingphone.getValue() %>" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Advertiser Email<font color="red">*</font></span></td>
	     <td align="left"><input type="text" name="advemail" value="<%= _advemail.getValue() %>" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Edition<font color="red">*</font></span></td>
	     <td align="left">
		 <select name="edition">
<%
   String[][] zoneMap = (String[][])allZones.get(feedidValue);
   if (zoneMap != null) {
       for (int i=0; i<zoneMap.length; i++) {
           String zoneCode = zoneMap[i][0];
           String zoneName = zoneMap[i][1];
%>
                 <option value="<%= zoneCode %>" <%= _edition.getValue().equals(zoneCode) ? "selected" : "" %>><%= zoneCode %> - <%= zoneName %></option>
<%
       }
   }
%>
		 </select>
	     </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Rate Code</span></td>
	     <td align="left"><input type="text" name="ratecode" value="<%= _ratecode.isNull ? "" : _ratecode.getValue() %>" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Logo Code</span></td>
	     <td align="left"><input type="text" name="logocode" value="<%= _logocode.isNull ? "" : _logocode.getValue() %>" size="40"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Icon Code</span></td>
	     <td align="left"><input type="text" name="iconcode" value="<%= _iconcode.isNull ? "" : _iconcode.getValue() %>" size="40"/></td>
          </tr>

          <tr>
             <td>&nbsp;</td>
             <td><input type="submit" name="update" value="Update"/> <input type="reset"/></td>
          </tr>
       </table>

<%
     }//end try
     catch(Exception e)
     {
           ClassifiedToolException cte = new ClassifiedToolException("error getting account details: " + e.toString());
   	   cte.setReturnLink(request.getHeader("REFERER"));
   	   session.setAttribute("classifiedException", cte);
   	   response.sendRedirect("classifiedError.jsp");

     }
%>



       </form>
    </td>
  </tr>
  </table>

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
