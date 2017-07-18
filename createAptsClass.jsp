<%@ page language="java" errorPage="errorPage.jsp" import = "java.sql.*, java.text.*, java.util.*, trb.tii.efe.reversePublishing.*"%>

<%
    try {
        //get and store insertTable to session object
        session.setAttribute("insertTable", new String("apts_classes"));

        String parentMarket = (String)session.getAttribute("parentMarket");

        String dataUser = (String)session.getAttribute("dataUser");
        String dataPass = (String)session.getAttribute("dataPass");
        String dataHost = (String)session.getAttribute("dataHost");
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

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Add Apartments Class
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
              <td><font class="page-header">Add Apartments Class</font></td>
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

<!-- add category table -->
<table width="100%">
  <tr>
     <td valign="top" align="left">
       <form action="insert.jsp" method="post">
<!--	 <input type="hidden" name="feedid" value="<%= request.getParameter("feedid") %>"/> -->

	<table width="100%">
	  <tr>
	     <th align="left" colspan="2" class="td-title-bg"><span class="table-header">Fields</span></th>
	  </tr>
          <tr class="td-on">
             <td align="left" width="35%"><span class="table-text">Market:</span></td>
	     <td align = "left">
            <select name = "market">

            <%
                while (rsMarket.next()) {
                    String disp_market = rsMarket.getString(1);
            %>
            <option value="<%= disp_market %>"><%= disp_market %></option>
            <%
                } //end while
            %>
            </select>
            </td>
          </tr>

          <tr class="td-off">
             <td align="left"><span class="table-text">Type:</span></td>
	     <td align = "left">
             <select name = "type">

             <%
                 while (rsType.next()) {
                     String disp_type = rsType.getString(1);
             %>
             <option value="<%= disp_type %>"><%= disp_type %></option>
             <%
                 } //end while
             %>
             </select>
             </td>
          </tr>
          <tr class="td-on">
             <td align="left"><span class="table-text">Class: (Required)</span></td>
	     <td align = "left"><input type="text" name = "class" size="10"/></td>
          </tr>
          <tr class="td-on">
             <td align="left"><span class="table-text">Zip: (Required)</span></td>
	      <td align = "left"><input type="text" name = "zip" size="10"/></td>
          </tr>

          <%
                rsType.close();
                rsMarket.close();
                psType.close();
                psMarket.close();
                conn.close();
          %>
          <tr>
             <td>&nbsp;</td>
             <td><input type="submit" name="insert" value="add"/> <input type="reset" value="clear"/></td>
          </tr>
       </table>

<%
     } catch(Exception e) {
            ClassifiedToolException cte = new ClassifiedToolException("error getting account details: " + e.toString());
            cte.setReturnLink("createAptsClass.jsp");
   	        session.setAttribute("classifiedException", cte);
   	        response.sendRedirect("classifiedRPError.jsp");
     }
%>

       </form>
    </td>
  </tr>
</table>
<!-- add category table -->

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
