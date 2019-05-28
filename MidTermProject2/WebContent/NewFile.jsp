<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="java.sql.*" %>
<%
Context ctx = new InitialContext();
DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/midtermproject");
Connection conn = ds.getConnection();
//JDBC
Statement stmt = conn.createStatement();
ResultSet rset = stmt.executeQuery("select * from midtermproject");
while(rset.next()) {
        String col1 = rset.getString(1);
        out.println(col1+"<Br>");
}
rset.close();
stmt.close();
conn.close();
%>
</body>
</html>