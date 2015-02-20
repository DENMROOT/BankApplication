<%@ page import="java.util.Enumeration" %>
<%--
  Created by IntelliJ IDEA.
  User: SCJP
  Date: 20.02.2015
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ page language="java" pageEncoding="utf-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>Информация о клиенте</title>

  <form id="form" action="saveClient" method="POST">
    <input type="hidden" name="id" value="${client.clientID}">
    Name: <input type="text" name="name" value="${client.name}"/><br>
    City: <input type="text" name="city" value="${client.city}"/><br>
    Gender:
    <input type="radio" name="gender" ${client.gender=="MALE"?"checked":""}/>Male
    <input type="radio" name="gender" ${client.gender=="FEMALE"?"checked":""}/>Female<br>
    Email: <input type="text" name="email" value="${client.email}"/><br>
    Phone: <input type="text" name="phone" value="${client.phone}"/><br>
    ActiveAccount ID: <input type="text" name="activeAccount" value="${client.activeAccount.accountId}"/><br>
    Balance: <input type="text" readonly name="balance" value="${client.balance}"/><br>
    <input type="submit"/>
  </form>
</head>
<body>

</body>
</html>
