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
        <title>Sign Up Page</title>
    </head>

    <jsp:include page="Header.jsp"/>

    <body>

    <main>

        <sf:form class="form-group col-md-6" method="POST" commandName="SignUp" action ="/signup">
            <label> Username:</label>
            <sf:input class="form-control" path ="username" type="text" placeholder="Enter name"/>

            <label>Password:</label>
            <sf:input class="form-control" path="password" type="password" placeholder="Enter password"/>

            <label>Confirm password:</label>
            <sf:input class="form-control" path="passwordConfirm" type="password" placeholder="Enter password again"/></td>

            <label> School:</label>
            <sf:input class="form-control" path="school" type="text" placeholder="Enter school"/>

            <button type="submit" class="btn btn-primary" value="Sign up!">Sign Up!</button>

            <c:if test = "${hasErrors}">
                <c:forEach var="error" items="${errors}">
                    <p class="alert alert-danger">${error.getCode()}

                </c:forEach>
            </c:if>
        </sf:form>
    </main>

    </body>

</html>