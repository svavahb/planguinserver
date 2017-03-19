<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>


<html lang="">

    <head>
        <%@ page contentType="text/html; charset=UTF-8"%>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"

              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link href="../css/stylesheet.css" rel="stylesheet" type="text/css">
        <title>Login page</title>
    </head>

    <jsp:include page="Header.jsp"/>

    <body>

    <main>

        <sf:form class="form-group col-md-6" method="POST" commandName="LogIn" action ="/login">
            <label>Username:</label>
            <sf:input class="form-control" path ="username" type="text" placeholder="Enter username"/>

            <label>Password:</label>
            <sf:input class="form-control" path ="password" type="password" placeholder="Enter password"/>

            <button type="submit" class="btn btn-primary" value="Log In!">Log In!</button>
            <c:if test="${loginfail}">
                <p class="alert alert-danger">Log-in failed. Please try again. If you are a new user, please <a href="/signup">sign up</a></p>
            </c:if>
        </sf:form>
    </main>

    </body>

</html>