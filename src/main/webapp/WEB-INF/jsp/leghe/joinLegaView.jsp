<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Iscriviti a una lega</title>

    <style>

        main {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .join-box {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.2);
            margin-top: 60px;
            margin-bottom: 60px;
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 20px;
            box-sizing: border-box;
            font-size: 15px;
        }
        input[type="submit"] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 15px;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .back-button {
            display: block;
            background-color: #f35d5d;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            text-align: center;
            cursor: pointer;
            font-size: 15px;
            width: 100%;
            box-sizing: border-box;
            margin: 10px auto 0 auto;
        }

        .back-button:hover {
            background-color: #c86666;
        }
    </style>

</head>
<body>

<%@include file="../../include/header.inc"%>

<main>

    <section class="join-box">

        <h1 style="text-align: center;">Entra in una lega</h1>

        <c:if test="${not empty errorMessage}">
            <p style="color: red; text-align: center; margin: 0 auto 10px auto;">${errorMessage}</p>
        </c:if>

        <form action="joinLega" method="post">
            <label for="code">Codice di invito:</label>
            <input type="text" id="code" name="codiceInvito" required autocomplete="off" pattern=".{6}" title="Il codice di invito deve essere di 6 caratteri" oninput="this.value = this.value.toUpperCase()">
            <label for="squadra">Nome squadra:</label>
            <input type="text" id="squadra" name="nomeSquadra" required autocomplete="off" maxlength="45">
            <input type="submit" value="Entra">
        </form>
        <a href="/legheView" class="back-button">Torna alle leghe</a>

    </section>

</main>

<%@include file="../../include/footer.inc"%>

</body>
</html>