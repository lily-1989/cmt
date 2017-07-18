<%@ page language="java" errorPage="errorPage.jsp" import = "java.text.*, java.util.*, java.sql.*, trb.tii.efe.reversePublishing.*"%>

<%

try {

    //String market = (String)session.getValue("market");

    String market = request.getParameter("market");
    String parentMarket = market;
    session.setAttribute("parentMarket", parentMarket);
    String zip = request.getParameter("zip");

    //String feedid = request.getParameter("feedid");

  /*  String classnum = request.getParameter("class");
    String type = request.getParameter("type");   */

    //get and store return page to session object
    String returnPage = new String("searchAptsClasses.jsp" + "?market=" + parentMarket + "&zip=" + zip);
    session.setAttribute("returnPage", returnPage);

    DataField dfMarket = new DataField("market_String");
    DataField dfClass = new DataField("class_String");
    DataField dfType = new DataField("type_String");
    DataField dfZip = new DataField("zip_String");

    Hashtable dataRecord = new Hashtable();

    //populate dataRecord hashtable with datafield objects
    dataRecord.put("market", dfMarket);
    dataRecord.put("class", dfClass);
    dataRecord.put("type", dfType);
    dataRecord.put("zip", dfZip);

    session.setAttribute("dataRecord", dataRecord);

    //String host = (String)session.getValue("dataHost");
    String dataUser = (String)session.getAttribute("dataUser");
    String dataPass = (String)session.getAttribute("dataPass");
    String dataHost = (String)session.getAttribute("dataHost");
    System.out.println("dataHost : " + dataHost);

    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    Connection conn = DriverManager.getConnection(dataHost, dataUser, dataPass);

    PreparedStatement ps = conn.prepareStatement(
            "select * from feedeng.apts_classes where zip like ? and market in " +
			"(select submarket from feedeng.markets where market LIKE ?)");
    ps.setString(1, zip);
    ps.setString(2, market);
    ResultSet rs = ps.executeQuery();

%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
View Apartments Classes
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
              <td><font class="page-header">View Apartments Classes</font></td>
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


      <!-- begin category table -->
      <table border="0" width="100%">
      <tr>
      <td valign = "top">
	 <table width = "100%" border="0" cellspacing="1" cellpadding="1">
	   <tr>
	   <td colspan="7">
	   <table width="100%" border="0">
	   <tr>
	     <form action = "searchAptsClasses.jsp" method="get">
	     <input type="hidden" name="market" value="<%= market %>"/>
		 <td align = "right"><input type="submit" value="Search by Zip"></td>
	       <td><input type="text" name="zip" value="<%= zip %>" size="40"></td>
	     </form>
	   </tr>
	   </table>
	   </td>
	   </tr>
<tr>
    <td></td>
</tr>
<tr>
   <th width = "41" align = "left" class="td-title-bg"><span class="table-header">Market</span></th>
   <th width = "42" align = "left" class="td-title-bg"><span class="table-header">Class</span></th>
   <th width = "29" align = "left" class="td-title-bg"><span class="table-header">Type</span></th>
   <th width = "50" align = "left" class="td-title-bg"><span class="table-header">Zip</span></th>
   <td class="td-title-bg">&nbsp;</td>
   <td class="td-title-bg">&nbsp;</td>

</tr>

<%
    int tog = -1;
    //String[] st = {"","","",""};
    String classnum, type;

    while (rs.next()) {
        market = rs.getString(1);
        classnum = rs.getString(2);
        type = rs.getString(3);
        zip = rs.getString(4);
%>
<tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
   <td><span class="table-regtext"><%= market %></span></td>
   <td><span class="table-regtext"><%= classnum %></span></td>
   <td><span class="table-regtext"><%= type %></span></td>
   <td><span class="table-regtext"><%= zip %></span></td>


   <td><span class="table-regtext">
     <a href="updateAptsClasses.jsp?market=<%= market %>&classnum=<%= classnum %>&type=<%= type %>&zip=<%= zip %>">edit</a>
     </span>
   </td>
   <td><span class="table-regtext">
     <a href="deleteAptsClasses.jsp?market=<%= market %>&classnum=<%= classnum %>&type=<%= type %>&zip=<%= zip %>">delete</a>
     </span>
   </td>
</tr>
<%
}//end for loop
%>
<tr>
        <td>&nbsp;</td>

<td>

</td>

<%

   }//end try
     catch(Exception e)
     {
        ClassifiedToolException cte = new ClassifiedToolException("error getting categories" + e.toString());
        cte.setReturnLink("main.html");
        cte.setReturnLink(request.getHeader("REFERER"));
        session.setAttribute("classifiedException", cte);
        response.sendRedirect("classifiedRPError.jsp");
  }
%>
</tr>

  <tr>
     <form action="createAptsClass.jsp" method="get">
     <td></td>
     <td align="left" valign = "top">
     <input type="hidden" name="parentMarket" value="<%= session.getAttribute("parentMarket") %>">
     <!-- <input type="hidden" name="parentMarket" value="<%= session.getAttribute("market") %>">   -->
	 <input type="submit" value="Create New Apts Class"/>
     </td>
     </form>
  </tr>

       </table>
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
