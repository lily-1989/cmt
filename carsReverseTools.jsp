<%@ page language="java" errorPage="errorPage.jsp" import="java.util.*, java.sql.*, oracle.jdbc.driver.*, trb.tii.efe.tools.*" %>
<%
  //check to make sure session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  //String market = user.getMarket();
%>
<%
    // set up zone/edition map
    Hashtable allZones = new Hashtable();
    /*
     * structure of allZones hash:
     * key: feedid as String
     * value: 2-dimensional array of Strings
     *   - each element of array is a 2-element array
     *   - 1st element is edition code
     *   - 2nd element is edition name
     *   - on the select dropdown, both the code and name will appear separated by a hyphen
     * Any changes should be tested by accessing the Advertiser edit and add pages for that feedid
     */
    allZones.put("251",
            new String[][] {
                new String[] {"A", "ALL"},
                new String[] {"C", "Chicago"},
                new String[] {"S", "South"},
                new String[] {"L", "North (lakeshore)"},
                new String[] {"N", "Northwest"},
                new String[] {"W", "Near West"},
                new String[] {"D", "DuPage"}
            }
    );
    allZones.put("252",
            new String[][] {
                new String[] {"FULL", "FULL"},
                new String[] {"F", "San Fernando Valley"},
                new String[] {"FR", "San Fernando/Orange"},
                new String[] {"M", "All Five Zones"},
                new String[] {"OC", "Orange County"},
                new String[] {"R", "Orange"},
                new String[] {"SBSE", "South Bay/South East LA"},
                new String[] {"SFVN", "San Fernando Valley/Ventura"},
                new String[] {"SGIE", "San Gabriel Valley/Inland Empire"},
                new String[] {"WSCE", "West/Central LA"},
                new String[] {"XSI", "San Gabriel/Inland Empire"},
                new String[] {"XVV", "San Fernando Valley/Ventura"},
                new String[] {"XWC", "LA - Westside/South Bay/Southeast"}
            }
    );
    allZones.put("253",
	    new String[][] {
                new String[] {"FR", "Full Run"}
		}
    );
    allZones.put("254",
            new String[][] {
                new String[] {"FR", "Full Run"}
                }
    );
    allZones.put("255",
            new String[][] {
                new String[] {"LO", "Full Run"}
                }
    );
    allZones.put("256",
            new String[][] {
                new String[] {"C", "Full Run"}
                }
    );
    
    session.setAttribute("allZones", allZones);
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Cars Reverse Publishing Tools
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
              <td><font class="page-header">Cars Reverse Publishing Tools</font></td>
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


    <!--select Feed table-->

<table border="0" width="100%">
        <tr>
          <td align = "left" valign = "top">

<br>
Choose a tool:<br>
<ul>
<li><a href="carsClassSelectFeed.jsp">Make/Model to Class Mapping</a></li>
<li><a href="carsAdvSelectFeed.jsp">Advertiser Info</a></li>
<li><a href="carsReplaceSelectFeed.jsp">Text Replacements</a></li>
<li><a href="carsErrSelectFeed.jsp">Correction Tool</a></li>
</ul>

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
