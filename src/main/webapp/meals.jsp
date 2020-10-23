<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    		<style>
    			.normal {color:green}
    			.excessed {color:red}
    		</style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1" cellspacing="8" cellpadding="4">
  <thead>
  <tr>
      <td>Excess</td>
    <td>Date</td>
    <td>Description</td>
    <td>Calories</td>
  </tr>
  </thead>
    <c:forEach items="${mealsList}" var="meal">
    <jsp:useBean id="meal" scope = "page" type ="ru.javawebinar.topjava.model.MealTo"/>
          <tr class="${meal.excess ? 'normal' : 'excessed'}">
            <td>${meal.excess}</td>
            <td>
            <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
            <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />
            </td>
               <td>${meal.description}</td>
               <td>${meal.calories}</td>
          </tr>
    </c:forEach>
</table>
</body>
</html>