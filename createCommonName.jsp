<%@ page language="java" errorPage="errorPage.jsp" import = "java.util.*, java.text.*, trb.tii.efe.tools.*"%>

<%
  //check to make sure session
  //file name: insertCommonNameForm.jsp
  
  if (session.getAttribute("user") == null)
  {
     response.sendRedirect("index.jsp");
  }

    Vector reConfigs = (Vector) session.getAttribute("reConfigs");

    String community = request.getParameter("community");
    int communityid = Integer.parseInt(request.getParameter("communityid"));
%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Add Commonly Used Names
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
              <td><font class="page-header">Add Commonly Used Names</font></td>
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


    <!--insert ad table-->
  <table width="100%" border="0">
  <tr> 
    <td width="100%" valign="top" align="left">
           <table border = "0" align="center" width="100%">
               <form method = "post" action = "insertCommonName">
		<input type="hidden" name="communityid" value="<%= communityid %>">
		<input type="hidden" name="community" value="<%= community %>">
                 <tr>
                   <td colspan="2"><span class="table-text"><font color="red">*</font> = required fields</font></span></td>
	         <tr>

                <tr>
                   <th colspan="2" align = "left" class="td-title-bg">
		       <span class="table-header">Fields</span>
		   </th>
                </tr>
                <tr class="td-on">
                  <th width="35%" align = "left"><span class="table-text">Market<font color="red">*</font></span></th>

                  <td>

                     <select type="select" size = "1" name="feedid">
<%
		
        for (int i=0; i<reConfigs.size(); i++)
        {
           REConfig recfg = (REConfig) reConfigs.elementAt(i);

%>					 

                        <option value = "<%= recfg.getFeedid() %>" selected = "true"><%= recfg.getMarket() %></option>

<%
}
%>
                      </select>
                   </td>
                </tr>

                <tr class="td-off">
                    <th align = "left"><span class="table-text">Common Names<font color="red">*</font></span></th>
                    <td><input type = "text" size="50" name = "name" maxlength = "100"/></td>
                </tr>

                <tr>
                    <td></td>
                    <td valign = "middle">
			<input type = "submit" value = "Insert"/>
			<input type = "Reset"/>
                    </td>
                </tr>
                </form>
           </table>
           </td>
           </tr>
       </table>



<!--end insert Ad table -->

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
