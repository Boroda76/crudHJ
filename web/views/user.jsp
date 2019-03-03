<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: unmor
  Date: 27.02.2019
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User page</title>
</head>
<body>
<h1>Hello <c:out value="${user.login}"/>!</h1>
<c:choose>
    <c:when test="${sessionScope.user.getRole().equals('admin')}">
        <a href="/admin"><button>USERS LIST</button></a><br><br>
    </c:when>
</c:choose>
<a href="/logout"><button>LOGOUT</button></a>
</body>
</html>
