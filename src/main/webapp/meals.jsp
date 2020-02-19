<%--
  Created by IntelliJ IDEA.
  User: Алексей
  Date: 14.02.2020
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<table border="1">
    <thead style="background: gainsboro;">
    <tr>
        <th>№</th>
        <th>Дата и время</th>
        <th>Описание приема пищи</th>
        <th>Калорийность</th>
        <th colspan="2">Действие</th>
    </tr>
    </thead>
    <c:forEach items="${requestScope.mealToList}" var="mealTo">
        <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <c:if test="${mealTo.excess == true}"><tr style="color: red;"></c:if>
        <c:if test="${mealTo.excess == false}"><tr style="color: green;"></c:if>
        <td style="padding: 5px;">
            <c:out value="${mealTo.id}"/>
        </td>
        <td style="padding: 5px;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/>
        </td>
        <td style="padding: 5px;">
            <c:out value="${mealTo.description}"/>
        </td>
        <td style="padding: 5px;">
            <c:out value="${mealTo.calories}"/>
        </td>
        <td><a href="meals?action=edit&id=<c:out value="${mealTo.id}"/>">Редактировать</a></td>
        <td><a href="meals?action=delete&id=<c:out value="${mealTo.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=add">Добавить запись о приеме пищи</a></p>
</body>
</html>
