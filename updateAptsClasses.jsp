<%@ page language="java" errorPage="ErrorPage.jsp" import = "java.sql.*, java.text.*, java.util.*, trb.tii.efe.reversePublishing.*"%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Edit Apts_Classes
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
              <td><font class="page-header">Edit Apts_Classes</font></td>
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

<!-- edit apts_classes table -->
<%

   /**************************************************************************************************

    The redirectPage is update.jsp, the HTML table must be populated with the record information
    corresponding to the username.
   *************************************************************************************************/

   try {
       //create dataRecord to session object
       String sMarket = request.getParameter("market");
       String sClass = request.getParameter("classnum");
       String sType = request.getParameter("type");
       String sZip = request.getParameter("zip");

       session.setAttribute("updateTable", "apts_classes");

       //create updateClauses to session rec.

       DataField dM = new DataField("market_String", sMarket);
       DataField dC = new DataField("class_String", sClass);
       DataField dT = new DataField("type_String", sType);
       DataField dZ = new DataField("zip_String", sZip);

       Hashtable updateClauses = new Hashtable();

       updateClauses.put("market", dM);
       updateClauses.put("class", dC);
       updateClauses.put("type", dT);
       updateClauses.put("zip", dZ);

       session.setAttribute("updateClauses", updateClauses);

        String parentMarket = (String)session.getValue("parentMarket");

        String dataUser = (String)session.getValue("dataUser");
        String dataPass = (String)session.getValue("dataPass");
        String dataHost = (String)session.getValue("dataHost");
        System.out.println("dataHost : " + dataHost);

        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection conn = DriverManager.getConnection(dataHost, dataUser, dataPass);

        Statement psType = conn.createStatement();
        ResultSet rsType = psType.executeQuery("select unique type from feedeng.apts_classes");

        PreparedStatement psMarket = conn.prepareStatement(
                "select submarket from feedeng.markets where market like ?");
        psMarket.setString(1, parentMarket);
        ResultSet rsMarket = psMarket.executeQuery();

%>

<table border="0" width="100%">
        <tr>
          <td align = "left" valign = "top">

       <form action="updateRP.jsp" method="post">

	 <table width = "100%" border="0" cellspacing="2" cellpadding="1">
          <tr>
            <th colspan="2" align="left" class="td-title-bg"><span class="table-header">Fields</span></th>
          </tr>
<%
  int tog = -1;
%>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
           <td width="35%" align="left"><span class="table-text">Market:</span></td>
	  <!--   <td align = "left"><input type="text" name = "market" value = "<%= sMarket %>" size="20"/></td> -->
            <td align="left">
            <select name="market">
            <%
                while (rsMarket.next()) {
                    sMarket = rsMarket.getString(1);
            %>
            <option value="<%= sMarket %>"><%= sMarket %></option>
            <%
                }
            %>
            </select>
            </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Class Num:</span></td>
	     <td align = "left"><input type="text" name = "class" value = "<%= sClass %>" size="20"/></td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Type:</span></td>
	  <!--   <td align = "left"><input type="text" name = "type" value = "<%= sType %>" size="20"/></td> -->
            <td align="left">
            <select name="type">

            <%
                while (rsType.next()) {
                    sType = rsType.getString(1);
            %>
            <option value="<%= sType %>"><%= sType %></option>
            <%
                }
            %>
            </select>
            </td>
          </tr>
          <tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
             <td align="left"><span class="table-text">Zip:</span></td>
	     <td align = "left"><input type="text" name = "zip" value = "<%= sZip %>" size="20"/></td>
          </tr>
         </tr>
          <tr>
             <td>&nbsp;</td>
             <td><input type="submit" name="update" value="Update"/> <input type="reset"/></td>
          </tr>
       </table>

<%
     } catch(Exception e) {
       ClassifiedToolException cte = new ClassifiedToolException("error getting account details: " + e.toString());
   	   cte.setReturnLink("updateAptsClasses.jsp");
   	   session.setAttribute("classifiedException", cte);
   	   response.sendRedirect("classifiedRPError.jsp");
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
