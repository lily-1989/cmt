<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, trb.tii.efe.tools.*"%>

<%  String login = "";
    String errorMessage = "Username and Password do not match. Please try again.";

    if ("auth".equals(request.getParameter("login")))
    {
        login = request.getParameter("login");
    }

%>


<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Classified Management Tool
</title>

    <script type="text/javascript">
    function setfocus() {
        document.forms[0].username.focus()
    }
    </script>

</head>

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
              <td><font class="page-header">Login</font></td>
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
    <!--login starts here -->
      <table width="100%" border="0">
        <tr>

          <td align="left" valign = "top" width="73%">
               <FONT SIZE="-1" COLOR="#00638E" FACE="verdana,arial,helvetica,san-serif">
                    <b>Type in your username and password below.</b>
               </FONT><br>
               <FORM ACTION="login" METHOD="post">
               <TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0">
                   <TR>
                       <TD COLSPAN="3">
                          <FONT FACE="Arial" SIZE="2"><STRONG>Username:</STRONG></FONT></TD>
                   </TR>
                   <TR>
					 <TD COLSPAN="3">
					 <INPUT size="40" NAME="username"/>
					 </TD>
				  </TR>
				  <TR>
					 <TD COLSPAN="3">
					 <FONT FACE="Arial" SIZE="2">
					 <STRONG>Password:</STRONG>
					 </FONT>
					 </TD>
				  </TR>
				  <TR>
					 <TD COLSPAN="3">
					 <INPUT SIZE="40" NAME="password"  TYPE="password"/>
					 </TD>
				  </TR>
                  <% if (login.equals("auth")) { %>
                  <tr>
                    <td><span class="edit-reqlabel"><%=errorMessage %></span></td>
                  </tr>
                  <% } %>
				  <TR>
					 <TD COLSPAN="3">
					 <INPUT TYPE="submit" NAME="ACTION" VALUE="Login"/>
					 <INPUT TYPE="reset">
					 </TD>
				  </TR>


                  <tr>
                    <td>
                      <FONT SIZE="-2" COLOR="#00638E" FACE="verdana,arial,helvetica,san-serif">
                      <a href="mailto:feedsupport@tribuneinteractive.com">Forgot or need a password?</a></font>
                   </td>
                  </tr>
			   </TABLE>
			   </FORM>
          </td>
        </tr>



      </table>
      <!--login ends here-->
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
