<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="css/styles.css">
  <title>FantaFoot</title>
  <style>
    body {
      color: #333;
      background-color: #f4f4f4;
    }

    main {
      text-align: center;
    }
  </style>
</head>
<body>

<%@include file="../include/header.inc"%>

<main>
  <h1>Benvenuto ${allenatoreLoggato.username}!</h1>
  <p>Benvenuto nel fantastico mondo di FantaFoot!</p>
  <p>
    ${allenatoreLoggato.id} <br>
    ${allenatoreLoggato.username} <br>
    ${logged}
  </p>
</main>

<%@include file="../include/footer.inc"%>

</body>
</html>