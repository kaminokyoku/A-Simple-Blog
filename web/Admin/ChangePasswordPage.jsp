<%--
  Created by IntelliJ IDEA.
  User: araw243
  Date: 7/06/2019
  Time: 11:34 AM
  To change this template use File | Settings | File Templates.
--%><!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>ChangePasswordPage</title>
    <link rel ="stylesheet" href="ChangePasswordcss.css">
    <script>

        var check = function () {

            if (document.getElementById('password').value ==
                document.getElementById('confirmpassword').value) {
                document.getElementById('message').style.color = 'green';
                document.getElementById('message').innerHTML = 'matching';
                document.getElementById('submit').style.visibility = "visible"

            } else {
                document.getElementById('message').style.color = 'red';
                document.getElementById('message').innerHTML = 'not matching';
                document.getElementById('submit').style.visibility = "hidden";

            }
        }
    </script>
</head>
<body>
<div class="change-password">
    <div class="form">
        <form class="block" action="./../changePasswordServlet" method="post" enctype="application/x-www-form-urlencoded">
            Enter New Password:<br>
            <input type="hidden" name="token" value="<%=request.getParameter("token")%>">
            <input type="Password" name="password" id="password" value="" onkeyup='check();'/><br>
            Confirm New Password:<br>
            <input type="Password" name="confirmpassword" id="confirmpassword" value="" onkeyup='check();'/>
            <span id='message'></span><br>
            <input type="submit" name="submit" id="submit" value="Submit">
        </form>
    </div>

</div>

</body>
</html>
