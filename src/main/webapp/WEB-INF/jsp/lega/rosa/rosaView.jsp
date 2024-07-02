<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Rosa</title>

    <style>
        .gioc-section {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .back-button {
            margin: 10px;
            padding: 10px 20px;
            background-color: #f35d5d;
            color: #fff;
            border-radius: 4px;
            text-decoration: none;
        }

        .back-button:hover {
            background-color: #c86666;
        }

        article h1 {
            font-size: 1.2em;
            padding: 10px;
            margin: 0;
        }

        article p {
            margin: 0;
            padding: 10px;
        }

        .gioc-box {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            padding: 10px;
            margin: 10px;
            width: 50%;
            display: flex;
            justify-content: space-evenly;
            align-items: center;
            flex-direction: row;
        }

        .gioc-box:hover {
            background-color: #e8e8e8;
            cursor: pointer;
        }

        .ruolo {
            color: darkred;
            font-size: 1.5em;
            margin: 0;
            padding-top: 10px;
            padding-bottom: 0;
        }

        img {
            justify-self: flex-end;
            border: 2px solid #000;
            border-radius: 4px;
        }

        img:hover {
            cursor: pointer;
            background-color: #ff6c6c;
        }

    </style>

    <script>
        function removePlayer(giocId) {
            document.removeForm.idGioc.value = giocId;
            document.removeForm.submit();
        }
    </script>


</head>
<body>

<%@include file="../../../include/header.inc" %>

<main>

    <section class="gioc-section">

        <a href="backToRose" class="back-button">Torna alle rose</a>

        <h1 style="color: darkred; font-size: 2em;">Rosa ${squadra.nome}</h1>

        <p style="text-align: center; margin-top: 0; font-size: 1.2em;">
        <b>Allenatore: </b>${nomeAll}<br>
        <b>Crediti: </b>${creditiLega - squadra.creditiSpesi}
        </p>

        <h2 class="ruolo" style="color: #f09837;">Portieri</h2>

        <c:forEach var="gioc" items="${portieri}">
            <%@include file="../../../include/box_giocatore.inc" %>
        </c:forEach>

        <h2 class="ruolo" style="color: #357a23;">Difensori</h2>

        <c:forEach var="gioc" items="${difensori}">
            <%@include file="../../../include/box_giocatore.inc" %>
        </c:forEach>

        <h2 class="ruolo" style="color: #448bbb;">Centrocampisti</h2>

        <c:forEach var="gioc" items="${centrocampisti}">
            <%@include file="../../../include/box_giocatore.inc" %>
        </c:forEach>

        <h2 class="ruolo" style="color: #9c2224;">Attaccanti</h2>

        <c:forEach var="gioc" items="${attaccanti}">
            <%@include file="../../../include/box_giocatore.inc" %>
        </c:forEach>

    </section>

    <form name="removeForm" action="removeGioc" method="post" style="display: none;">
        <input type="hidden" name="idGioc">
        <input type="hidden" name="idSquadra" value="${squadra.id}">
    </form>


</main>

<%@include file="../../../include/footer.inc" %>

</body>
</html>