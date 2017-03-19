<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html lang="">
    <head>
        <%@ page contentType="text/html; charset=UTF-8"%>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link href="../css/stylesheet.css" rel="stylesheet" type="text/css">
        <title>Search Page</title>
    </head>
    <jsp:include page="Header.jsp"/>
    <body>

    <a class="btn btn-info col-md-2" role="button" href="/home">Go back to your schedule</a>
    <div class="search col-md-12">
        <form action="/search/q">
            <label>Search:</label>
            <input type="text" name="username" placeholder="Type username.."/>
            <button class="btn btn-primary" type="submit" value="Search">Search!</button>
        </form>

        <c:choose>
            <c:when test="${users==null}">
                <p class="alert alert-danger">This user does not exist, try again.</p>
            </c:when>

            <c:otherwise>

                <table class="table table-hover">
                    <thead>
                    <tr>
                        <td width="100">Name: </td>
                        <td width="100">School: </td>
                        <td width="100">Add to group: </td>
                    </tr>
                    </thead>
                    <c:forEach items="${users}" var="user">
                            <tr>
                                <td>${user.user.username}</td>
                                <td>${user.user.school}</td>
                                <c:choose>
                                    <c:when test="${user.friendship}">
                                        <td>
                                            <form class="col-md-4" method="POST" action="/search/addToGroup?addMember=${user.user.userId}">
                                                <select name="addToGroup" class="btn btn-default dropdown-toggle form-control" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                                    <option class="dropdown-menu dropdown-menu-left col-md-4" aria-labelledby="dLabel" value="NONE" label="--Select group--"/>
                                                    <c:forEach var="groups" items="${groupList}">
                                                        <option value="${groups.grpId}" label="${groups.grpName}"></option>
                                                    </c:forEach>
                                                </select>
                                                <button class="btn btn-primary" type="submit"> Add to Group! </button>
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:when test="${loggedInId ne user.user.userId}">
                                        <td>
                                            <form action="/search/addFriend" method="POST">
                                                <button class="btn btn-primary" type="submit" name="userId" value=${user.user.userId}>Add Friend</button>
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td></td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
</div>
    </body>
</html>