<%@ page language="java" errorPage="errorPage.jsp" import="java.util.*, java.sql.*, trb.tii.efe.tools.ClassifiedToolUser"%>

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
    String feedid = request.getParameter("feedid");
    // multi-value parameter
    String[] send_id = request.getParameterValues("send_id");
    
    int totalcount = 0;
    if (send_id != null) {
        String dataUser = (String)session.getAttribute("dataUser");
        String dataPass = (String)session.getAttribute("dataPass");
        String dataHost = (String)session.getAttribute("dataHost");
        
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection conn = DriverManager.getConnection(dataHost, dataUser, dataPass);
        
        PreparedStatement st_dateupdate = conn.prepareStatement(
                "update cars " +
                "set dateupdated = sysdate " +
                "where feedid = ? " +
                "and adid = ?" );
        
        conn.setAutoCommit(false);
        for (int i=0; i<send_id.length; i++) {
            String adid = send_id[i];
            st_dateupdate.setString(1, feedid);
            st_dateupdate.setString(2, adid);
            totalcount += st_dateupdate.executeUpdate();
        }
        conn.commit();
        conn.close();
    }
%>
<meta http-equiv="refresh" content="5; URL=<%= request.getHeader("REFERER") %>">
<body bgcolor="#FFFFFF">
<%= totalcount %> ads have been marked.<br>
You will be returned to the previous page in a few seconds.<br>
If nothing happens, click <a href="<%= request.getHeader("REFERER") %>">here</a>.
</body>
