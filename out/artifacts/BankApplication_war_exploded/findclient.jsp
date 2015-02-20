<html>
<head>
    <script src="./js/jquery-2.1.3.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="./stylesheets/findclient.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Поиск клиента</title>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page language="java" pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>

<form name="bankStat" action="/clients" accept-charset="UTF-8" method="get">
    <table name="findclient" class="find">
    <tr>
	    <td colspan=3 style="font-weight:bold;">Поиск клиента:</td>
	</tr>
    <tr>
        <td>Город: <input type="text" name="city" value="${requestScope.city}"></td>
		<td>Имя: <input type="text" name="clientName" value="${requestScope.clientName}"></td>
		<td><input type="submit" onclick="return checkForm();" value="Поиск"></td>
    </tr>
	</table>
</form>
<p></p>
    <table class="results">
        <tr class="storyline">
            <td> Имя клиента</td>
            <td> Город</td>
            <td> Баланс</td>
        </tr>
    <c:forEach var="client" items="${clients}">
        <tr>
            <td><a href="/client?clientName=${client.name}">${client.name}</a></td>
            <td>${client.city}</td>
            <td>${client.balance}</td>
        </tr>
    </c:forEach>
    </table>

</body>
</html>