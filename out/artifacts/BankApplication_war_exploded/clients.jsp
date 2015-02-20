<%--
  Created by IntelliJ IDEA.
  User: SCJP
  Date: 20.02.2015
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
   <%@ page language="java" pageEncoding="utf-8"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>Поиск клиентов</title>
   <c:forEach var="client" items="${clients}">
     Name: <a href="/client?clientName=${client.name}"><c:out value="${client.name}"/></a>
     City: <c:out value="${client.city}"/>
     Balance: <c:out value="${client.balance}"/><br>
   </c:forEach>
</head>
<body>

</body>
</html>
