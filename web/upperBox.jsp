<%--
  Created by IntelliJ IDEA.
  User: click
  Date: 12/7/2014
  Time: 2:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
  <link type="text/css" rel="stylesheet" href="resources/main.css">
</head>
<body>
<div class="upperbox">
  <div class="upperboxfill">

    <div class="upperboxadd">
      <span style="margin:0px 10px 0px 37px;color:#717171;"> |  </span>
      <a href="/logout"><span id="logout" class="accounts">
                Logout
            </span></a>
    </div>
    <div class="upperboxadd">
      <a href="settings.jsp">
        <button name="choose" id="settings" class="accounts">
          Settings
        </button>
      </a>
    </div>

    <div class="upperboxadd">
      <a>
        <button type="submit" value="contacts" name="choose" id="contacts" class="accounts">
          Contacts
        </button>
      </a>
    </div>
    <div class="upperboxadd">
      <a href="messages.jsp">
        <button name="choose" id="messages" class="accounts">
          Messages
        </button>
      </a>
    </div>
    <div class="upperboxadd">
      <a href="profile.jsp">
        <button id="profile" class="accounts">
          Profile
        </button>
      </a>
    </div>
  </div>
</div>
</body>
</html>
