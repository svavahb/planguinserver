<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="">

    <head>
        <%@ page contentType="text/html; charset=UTF-8"%>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"

              integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link href="../css/stylesheet.css" rel="stylesheet" type="text/css">
        <title>Compare Page</title>
    </head>

    <jsp:include page="Header.jsp"/>

    <body>

    <h2 class="title">Compare</h2>

    <a class="btn btn-info col-md-2" role="button" href="/home">Go back to your schedule</a>

        <div class="col-md-4 col-md-offset-1 dropdown">
            <form method="POST" action="/compareFriends">
                <select name="selectedFriend" class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    <option class="dropdown-menu" aria-labelledby="dropdownMenu1" value="NONE" label="Compare with a friend!"/>
                    <c:forEach var="friend" items="${friendList}">
                        <option value="${friend.userId}" label="${friend.username}"></option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn btn-primary" value="Compare!">Compare!</button>
            </form>
        </div>

        <div class="col-md-4 dropdown">
            <form method="POST" action="/compareGroup">
                <select name="selectedGroup" class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    <option class="dropdown-menu" aria-labelledby="dropdownMenu1" value="NONE" label="Compare with a group!"/>
                    <c:forEach var="group" items="${groupList}">
                        <option value="${group.grpId}" label="${group.grpName}"></option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn btn-primary" value="Compare!">Compare!</button>
            </form>
    </div>

    <div class="tafla">

        <table class="col-md-8 col-centered table-bordered" cellspacing="0">
            <tbody>
            <tr>
                <th align="center" valign="middle" width="80"></th>
                <th align="center" valign="middle" width="100">Monday</th>
                <th align="center" valign="middle" width="100">Tuesday</th>
                <th align="center" valign="middle" width="100">Wednesday</th>
                <th align="center" valign="middle" width="100">Thursday</th>
                <th align="center" valign="middle" width="100">Friday</th>
                <th align="center" valign="middle" width="100">Saturday</th>
                <th align="center" valign="middle" width="100">Sunday</th>
            </tr>
            <c:set var="counter" value="0"/>
            <c:forEach var="slot" items="${timeSlots}">
            <tr>
                <c:if test="${counter mod 6 == 0}">
                    <td align="center" valign="middle" width="80" height="10" rowspan="6">
                        <c:out value="${slot}"/>
                    </td>
                </c:if>
                <c:set var="counter" value="${counter+1}"/>
                <%--MÁNUDAGUR--%>
                <c:set var="foundItem" value="false"/>
                <c:forEach var="item" items="${comparedSchedule}">
                    <c:set var="itemTime" value="${fn:substring(item, 11, 16)}"/>
                    <c:set var="itemDateStr" value="${fn:substring(item, 0, 10)}"/>
                    <fmt:parseDate value="${itemTime}" pattern="HH:mm" var="hours"/>
                    <fmt:parseDate value="${itemDateStr}" pattern="yyyy-MM-dd" var="itemDate"/>
                    <fmt:formatDate value="${itemDate}" pattern="E" var="weekDay"/>
                    <fmt:parseDate value="${slot}" pattern="HH:mm" var="slotTime"/>
                    <c:if test="${hours eq slotTime && weekDay eq 'Mon'}">
                        <td width="100" bgcolor="red"></td>
                        <c:set var="foundItem" value="true"/>
                    </c:if>
                </c:forEach>
                <c:if test="${foundItem eq false}">
                    <td width="100"></td>
                </c:if>
                    <%--ÞRIÐJUDAGUR--%>
                <c:set var="foundItem2" value="false"/>
                <c:forEach var="item" items="${comparedSchedule}">
                    <c:set var="itemTime" value="${fn:substring(item, 11, 16)}"/>
                    <c:set var="itemDateStr" value="${fn:substring(item, 0, 10)}"/>
                    <fmt:parseDate value="${itemTime}" pattern="HH:mm" var="hours"/>
                    <fmt:parseDate value="${itemDateStr}" pattern="yyyy-MM-dd" var="itemDate"/>
                    <fmt:formatDate value="${itemDate}" pattern="E" var="weekDay"/>
                    <fmt:parseDate value="${slot}" pattern="HH:mm" var="slotTime"/>
                    <c:if test="${hours eq slotTime && weekDay eq 'Tue'}">
                        <td width="100" bgcolor="red"></td>
                        <c:set var="foundItem2" value="true"/>
                    </c:if>
                </c:forEach>
                <c:if test="${foundItem2 eq false}">
                    <td width="100"></td>
                </c:if>
                    <%--MIÐVIKUDAGUR--%>
                <c:set var="foundItem3" value="false"/>
                <c:forEach var="item" items="${comparedSchedule}">
                    <c:set var="itemTime" value="${fn:substring(item, 11, 16)}"/>
                    <c:set var="itemDateStr" value="${fn:substring(item, 0, 10)}"/>
                    <fmt:parseDate value="${itemTime}" pattern="HH:mm" var="hours"/>
                    <fmt:parseDate value="${itemDateStr}" pattern="yyyy-MM-dd" var="itemDate"/>
                    <fmt:formatDate value="${itemDate}" pattern="E" var="weekDay"/>
                    <fmt:parseDate value="${slot}" pattern="HH:mm" var="slotTime"/>
                    <c:if test="${hours eq slotTime && weekDay eq 'Wed'}">
                        <td width="100" bgcolor="red"></td>
                        <c:set var="foundItem3" value="true"/>
                    </c:if>
                </c:forEach>
                <c:if test="${foundItem3 eq false}">
                    <td width="100"></td>
                </c:if>
                    <%--FIMMTUDAGUR--%>
                <c:set var="foundItem4" value="false"/>
                <c:forEach var="item" items="${comparedSchedule}">
                    <c:set var="itemTime" value="${fn:substring(item, 11, 16)}"/>
                    <c:set var="itemDateStr" value="${fn:substring(item, 0, 10)}"/>
                    <fmt:parseDate value="${itemTime}" pattern="HH:mm" var="hours"/>
                    <fmt:parseDate value="${itemDateStr}" pattern="yyyy-MM-dd" var="itemDate"/>
                    <fmt:formatDate value="${itemDate}" pattern="E" var="weekDay"/>
                    <fmt:parseDate value="${slot}" pattern="HH:mm" var="slotTime"/>
                    <c:if test="${hours eq slotTime && weekDay eq 'Thu'}">
                        <td width="100" bgcolor="red"></td>
                        <c:set var="foundItem4" value="true"/>
                    </c:if>
                </c:forEach>
                <c:if test="${foundItem4 eq false}">
                    <td width="100"></td>
                </c:if>
                    <%--FÖSTUDAGUR--%>
                <c:set var="foundItem5" value="false"/>
                <c:forEach var="item" items="${comparedSchedule}">
                    <c:set var="itemTime" value="${fn:substring(item, 11, 16)}"/>
                    <c:set var="itemDateStr" value="${fn:substring(item, 0, 10)}"/>
                    <fmt:parseDate value="${itemTime}" pattern="HH:mm" var="hours"/>
                    <fmt:parseDate value="${itemDateStr}" pattern="yyyy-MM-dd" var="itemDate"/>
                    <fmt:formatDate value="${itemDate}" pattern="E" var="weekDay"/>
                    <fmt:parseDate value="${slot}" pattern="HH:mm" var="slotTime"/>
                    <c:if test="${hours eq slotTime && weekDay eq 'Fri'}">
                        <td width="100" bgcolor="red"></td>
                        <c:set var="foundItem5" value="true"/>
                    </c:if>
                </c:forEach>
                <c:if test="${foundItem5 eq false}">
                    <td width="100"></td>
                </c:if>
                    <%--LAUGARDAGUR--%>
                <c:set var="foundItem6" value="false"/>
                <c:forEach var="item" items="${comparedSchedule}">
                    <c:set var="itemTime" value="${fn:substring(item, 11, 16)}"/>
                    <c:set var="itemDateStr" value="${fn:substring(item, 0, 10)}"/>
                    <fmt:parseDate value="${itemTime}" pattern="HH:mm" var="hours"/>
                    <fmt:parseDate value="${itemDateStr}" pattern="yyyy-MM-dd" var="itemDate"/>
                    <fmt:formatDate value="${itemDate}" pattern="E" var="weekDay"/>
                    <fmt:parseDate value="${slot}" pattern="HH:mm" var="slotTime"/>
                    <c:if test="${hours eq slotTime && weekDay eq 'Sat'}">
                        <td width="100" bgcolor="red"></td>
                        <c:set var="foundItem6" value="true"/>
                    </c:if>
                </c:forEach>
                <c:if test="${foundItem6 eq false}">
                    <td width="100"></td>
                </c:if>
                    <%--SUNNUDAGUR--%>
                <c:set var="foundItem7" value="false"/>
                <c:forEach var="item" items="${comparedSchedule}">
                    <c:set var="itemTime" value="${fn:substring(item, 11, 16)}"/>
                    <c:set var="itemDateStr" value="${fn:substring(item, 0, 10)}"/>
                    <fmt:parseDate value="${itemTime}" pattern="HH:mm" var="hours"/>
                    <fmt:parseDate value="${itemDateStr}" pattern="yyyy-MM-dd" var="itemDate"/>
                    <fmt:formatDate value="${itemDate}" pattern="E" var="weekDay"/>
                    <fmt:parseDate value="${slot}" pattern="HH:mm" var="slotTime"/>
                    <c:if test="${hours eq slotTime && weekDay eq 'Sun'}">
                        <td width="100" bgcolor="red"></td>
                        <c:set var="foundItem7" value="true"/>
                    </c:if>
                </c:forEach>
                <c:if test="${foundItem7 eq false}">
                    <td width="100"></td>
                </c:if>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    </body>


</html>

