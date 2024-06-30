<%--
  Created by IntelliJ IDEA.
  User: marcellobraghiroli
  Date: 24/06/24
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it">
<head>
    <title>Error</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            background: linear-gradient(to bottom, #0000bd, #add8e6, #0000bd);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        main {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }
    </style>

</head>
<body>

<main>
    <h1>Ci scusiamo per il disagio</h1>
    <h2>I nostri serivizi non sono attualmente disponibili</h2>
    <p>${errorMessage}</p>
</main>

</body>
</html>
