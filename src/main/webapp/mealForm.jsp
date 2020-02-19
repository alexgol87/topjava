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
    <title>Meal Form</title>
</head>
<body>
<h3><a href="index.html">Home</a> -> <a href="meals">Meals</a></h3>
<form method="POST" action='meals' name="mealForm">
    <c:set value="${requestScope.meal}" var="meal"/>
    <c:if test="${param.id != null}">Номер записи : <input type="text" name="id" readonly="readonly"
                                                           value="<c:out value="${meal.id}" />"/> <br/></c:if>
    Дата и время : <input type="datetime-local" name="dateTime"
                          value="${meal.dateTime}"/> <br/>
    Описание приема пищи : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />"/> <br/>
    Калорийность : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <br/> <input
        type="submit" value="Отправить"/>
</form>
</body>
</html>
