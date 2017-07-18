<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, java.net.*, trb.tii.efe.reversePublishing.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%
  //verify session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  //String market = user.getMarket();

  String marketParam = request.getParameter("market");

try
{
String indexString = request.getParameter("index");
int index = Integer.parseInt(indexString);
//System.out.println("index = " + index);

//DataField origstringClause = new DataField("lower(origstring)_String", "%" + searchkey + "%");
//origstringClause.setOperator("LIKE");
DataField marketClause = new DataField("market_String", marketParam);

Hashtable selectClauses = new Hashtable();
selectClauses.put("market", marketClause);
//get and store dataRecord to session object
Hashtable dataRecord = new Hashtable();

//populate dataRecord hashtable with datafield objects
dataRecord.put("market", new DataField("market_String"));
dataRecord.put("class", new DataField("class_String"));
dataRecord.put("target_class", new DataField("target_class_String"));

session.setAttribute("dataRecord", dataRecord);

//get and store return page to session object
String returnPage = "classMapList.jsp" + "?index=" + index + "&market=" + marketParam;
session.setAttribute("returnPage", returnPage);
//System.out.println(returnPage);


String dataUser = (String)session.getAttribute("dataUser");
String dataPass = (String)session.getAttribute("dataPass");
String dataHost = (String)session.getAttribute("dataHost");
//System.out.println("dataHost : " + dataHost);

DataInteract di = new DataInteract(dataUser, dataPass, dataHost);
RecordSet records = new RecordSet();
records.bufferSize = 20;

records.setRecords(di.select(dataRecord, selectClauses, "feedeng.class_map", "order by class"));
records.setIndex(index);

System.out.println(records.recordCount + " records returned.");

//put records into session element;
session.setAttribute("class_map", records);

%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
View Recycler Class Mappings
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
              <td><font class="page-header">View Recycler Class Mappings</font></td>
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


      <!-- begin class table -->
      <table border="0" width="100%">
      <tr> 
      <td valign = "top">
	 <table width = "100%" border="0" cellspacing="1" cellpadding="1">		
<tr>
    <td></td>
    <td>
	<span class="table-regtext">
	<font color="red">Records <%= records.index + 1 %> through <%=records.last%> of <%=records.recordCount%></font>
	</span>
    </td>
</tr>
<tr>
   <th align="left" class="td-title-bg"><span class="table-header">Class</span></th>
   <th align="left" class="td-title-bg"><span class="table-header">Target Class</span></th>
   <td class="td-title-bg">&nbsp;</td>
   <td class="td-title-bg">&nbsp;</td>

</tr>
          
<%
  int tog = -1;
  
  Hashtable record;
  for (int i = index; i < records.last; i++) {
    record = records.elementAt(i);

    DataField _class = (DataField)record.get("class");
    DataField _target_class = (DataField)record.get("target_class");

%>
<tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
   <td><span class="table-regtext"><%= _class.getValue()%></span></td>
   <td><span class="table-regtext"><%= _target_class.getValue()%></span></td>

   <td><span class="table-regtext">
     <a href="classMapEdit.jsp?market=<%= marketParam %>&class=<%= _class.getValue() %>">edit</a>
     </span>
   </td>
   <td><span class="table-regtext">
     <a href="classMapDelete.jsp?market=<%= marketParam %>&class=<%= _class.getValue() %>">delete</a>
     </span>
   </td>
</tr>   
<%
}//end for loop
%>
<tr>
        <td>&nbsp;</td>
  	<td>
	   <span class="table-regtext">
	   <font color="red">Records <%= records.index + 1 %> through <%=records.last%> of <%=records.recordCount%></font>
	   </span>
	   <br><br>

	   <span class="table-regtext">
	   Page:
	   <% 
	      int pageno = 1;
	      for (int i=0; i<records.recordCount; i+=records.bufferSize) {
	   %>
	   <% if (i != records.index ) { %>
	     <a href="classMapList.jsp?market=<%= marketParam %>&index=<%= i %>"><%= pageno %></a>
	   <% } else { %>
	     <%= pageno %>
	   <% } %>
	   <%
	        pageno++;
	      }
	   %>
	   </span>
	</td>
<td>  
	<span class="table-regtext">
<% if (records.hasPrevious) { %>
	  <a href="classMapList.jsp?market=<%= marketParam %>&index=<%= records.previous%>">
<% } %>
	  prev <%= records.bufferSize %>
<% if (records.hasPrevious) { %>
	  </a>
<% } %>
	  </span>
</td>
<td>
 	<span class="table-regtext">
<% if (records.hasNext) { %>
 	  <a href="classMapList.jsp?market=<%= marketParam %>&index=<%= records.next%>">
<% } %>
 	  next <%= records.bufferSize %>
<% if (records.hasNext) { %> 	  
 	  </a>
<% } %>
 	  </span>
</td>
<%
   //System.out.println("feedid=" + feedid);

   }//end try
     catch(Exception e)
     {
        ClassifiedToolException cte = new ClassifiedToolException("error getting Class Map List" + e.toString());
        //cte.setReturnLink("main.html");
        cte.setReturnLink(request.getHeader("REFERER"));
        session.setAttribute("classifiedException", cte);
        response.sendRedirect("classifiedError.jsp");
  }
%>
</tr>
  <tr>
      <td>&nbsp;</td>
  </tr>
  <tr>
     <form action="classMapNew.jsp" method="get">
     <td></td>
     <td align="left" valign = "top">
	 <input type="hidden" name="market" value="<%= marketParam %>">
	 <input type="submit" value="Add New Mapping"/>
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
