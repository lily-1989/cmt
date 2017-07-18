<%@ page language="java" errorPage="errorPage.jsp" import = "java.lang.*, java.text.*, java.util.*, java.net.*, trb.tii.efe.reversePublishing.*, trb.tii.efe.tools.ClassifiedToolUser"%>

<%
  //verify session
  ClassifiedToolUser user = (ClassifiedToolUser)session.getAttribute("user");
  if (user == null)
  {
     response.sendRedirect("index.jsp");
  }
  //String market = user.getMarket();

try
{

String indexString = request.getParameter("index");
int index = Integer.parseInt(indexString);
//System.out.println("index = " + index);
String searchkey = request.getParameter("searchkey").toLowerCase();
String feedid = request.getParameter("feedid");

//DataField makeClause = new DataField("lower(make)_String", "%" + searchkey + "%");
//makeClause.setOperator("LIKE");
DataField modelClause = new DataField("lower(model)_String", "%" + searchkey + "%");
modelClause.setOperator("LIKE");

Hashtable selectClauses = new Hashtable();
//selectClauses.put("make", makeClause);
selectClauses.put("model", modelClause);
selectClauses.put("feedid", new DataField("feedid_Integer", request.getParameter("feedid")));
//get and store dataRecord to session object
Hashtable dataRecord = new Hashtable();

//populate dataRecord hashtable with datafield objects
dataRecord.put("feedid", new DataField("feedid_Integer"));
dataRecord.put("make", new DataField("make_String"));
dataRecord.put("model", new DataField("model_String"));
dataRecord.put("class", new DataField("class_String"));
dataRecord.put("isdefault", new DataField("isdefault_Integer"));

session.setAttribute("dataRecord", dataRecord);

//get and store return page to session object
String returnPage = "carsClassSearch.jsp" + "?index=" + index + "&searchkey=" + URLEncoder.encode(searchkey) + "&feedid=" + feedid;
session.setAttribute("returnPage", returnPage);
//System.out.println(returnPage);


String dataUser = (String)session.getAttribute("dataUser");
String dataPass = (String)session.getAttribute("dataPass");
String dataHost = (String)session.getAttribute("dataHost");
//System.out.println("dataHost : " + dataHost);

DataInteract di = new DataInteract(dataUser, dataPass, dataHost);
RecordSet records = new RecordSet();
records.bufferSize = 20;

records.setRecords(di.select(dataRecord, selectClauses, "feedeng.car_rp_vehicletype", "order by isdefault desc, lower(make), lower(model)"));
records.setIndex(index);

System.out.println(records.recordCount + " records returned.");

//put records into session element;
session.setAttribute("makemodel", records);

%>

<html>
<head>
<link rel="stylesheet" href="include/cmt_stylesheet.css" type="text/css">
<title>
Cars Reverse Publishing Classes
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
              <td><font class="page-header">Cars Reverse Publishing Classes</font></td>
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
	   <td colspan="6">
	   <table width="100%" border="0">
	   <tr>
	     <form action = "carsClassSearch.jsp" method="get">
	       <input type="hidden" name="index" value="0"/>
		<input type="hidden" name="feedid" value="<%=feedid%>"/>
		 <td align = "right"><input type="submit" value="Search for Model"></td>
	       <td><input type="text" name="searchkey" value="<%= searchkey%>" size="40"></td>
	     </form>
	   </tr>
	   </table>
	   </td>
	   </tr>
<tr>
<td colspan="6"><span class="table-regtext"><font color="red">NOTE:</font> Text replacements will happen before Make and Model matching. For example, if there is a replacement that shortens "Mercedes-Benz" to "Mercedes", make sure the Make in this tool is "Mercedes", i.e. the value after replacements.</span></td>
</tr>
<tr>
    <td></td>
    <td>
	<span class="table-regtext">
	<font color="red">Records <%= records.index + 1 %> through <%=records.last%> of <%=records.recordCount%></font>
	</span>
    </td>
</tr>
<tr>
   <th align="left" width="140" class="td-title-bg"><span class="table-header">Make</span></th>
   <th align="left" class="td-title-bg"><span class="table-header">Model</span></th>
   <th align="left" class="td-title-bg"><span class="table-header">Class ID</span></th>
   <th align="left" class="td-title-bg"><span class="table-header">Default?</span></th>
   <td class="td-title-bg">&nbsp;</td>
   <td class="td-title-bg">&nbsp;</td>

</tr>
          
<%
  int tog = -1;
  
  Hashtable record;
  for (int i = index; i < records.last; i++) {
    record = records.elementAt(i);

    DataField _make = (DataField)record.get("make");
    DataField _model = (DataField)record.get("model");
    DataField _class = (DataField)record.get("class");
    DataField _isdefault = (DataField)record.get("isdefault");
    String isdefaultString;
    isdefaultString = _isdefault.getValue().equals("1") ? "Y" : "N";
   
%>
<tr class="td-<%= (tog=(tog+1)%2)==0 ? "on" : "off" %>">
   <td><span class="table-regtext"><%= _make.getValue()%></span></td>
   <td><span class="table-regtext"><%= _model.getValue()%></span></td>
   <td><span class="table-regtext"><%= _class.getValue()%></span></td>
   <td><span class="table-regtext"><%= isdefaultString %></span></td>

   <td><span class="table-regtext">
     <a href="carsClassEdit.jsp?feedid=<%= feedid %>&make=<%= URLEncoder.encode(_make.getValue()) %>&model=<%= URLEncoder.encode(_model.getValue()) %>">edit</a>
     </span>
   </td>
   <td><span class="table-regtext">
     <a href="carsClassDelete.jsp?feedid=<%= feedid %>&make=<%= URLEncoder.encode(_make.getValue()) %>&model=<%= URLEncoder.encode(_model.getValue()) %>">delete</a>
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
	     <a href="carsClassSearch.jsp?feedid=<%=feedid%>&index=<%= i %>&searchkey=<%= URLEncoder.encode(searchkey) %>"><%= pageno %></a>
	   <% } else { %>
	     <%= pageno %>
	   <% } %>
	   <%
	        pageno++;
	      }
	   %>
	   </span>
	</td>
<td></td>
<td>  
	<span class="table-regtext">
<% if (records.hasPrevious) { %>
	  <a href="carsClassSearch.jsp?feedid=<%=feedid%>&index=<%= records.previous%>&searchkey=<%= URLEncoder.encode(searchkey) %>">
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
 	  <a href="carsClassSearch.jsp?feedid=<%=feedid%>&index=<%= records.next%>&searchkey=<%= URLEncoder.encode(searchkey) %>">
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
        ClassifiedToolException cte = new ClassifiedToolException("error getting cars classes" + e.toString());
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
     <form action="carsClassNew.jsp" method="get">
     <td></td>
     <td align="left" valign = "top">
	 <input type="hidden" name="feedid" value="<%= request.getParameter("feedid") %>">
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
