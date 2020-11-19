<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
 <%@page import = "data.BoardInfoBean" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%	BoardInfoBean[] bib = (BoardInfoBean[])request.getAttribute("BoardInfoBean");
	for(int i = 0; i < bib.length; i++){%>
		<%= bib[i].getBoardContents() %><br>
<%  }%>
</body>
</html>