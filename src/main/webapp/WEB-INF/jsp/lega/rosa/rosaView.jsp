<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Rosa - FantaFoot</title>

    <style>
        .gioc-section {
            background-color: rgba(255, 255, 255, 0.85);
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

        .insert-button {
            margin: 10px;
            padding: 10px 20px;
            background-color: #007BFF;
            color: #fff;
            border-radius: 4px;
            text-decoration: none;
            box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.4);
        }

        .insert-button:hover {
            background-color: #0056b3;
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
            color: black;
        }

        .gioc-box:hover {
            background-color: #e8e8e8;
            cursor: pointer;
        }

        .ruolo {
            font-size: 1.7em;
            margin: 0;
            padding-top: 10px;
            padding-bottom: 0;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.7);
            color: white;
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

        .giocatori {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background-image: linear-gradient(120deg, #9db1c9 0%, #203b6b 100%);
            box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.4);
            border-radius: 8px;
            color: white;
            width: 80%;
            padding: 20px;
            margin-top: 15px;
            margin-bottom: 10px;
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

        <p style="text-align: center; margin-top: 0; margin-bottom: 5px; font-size: 1.2em;">
            <b>Allenatore: </b>${squadra.allenatore.username}<br>
            <b>Crediti: </b>${squadra.lega.numCrediti - squadra.creditiSpesi}
        </p>

        <c:if test="${allowMod}">
            <a href="insGiocView" class="insert-button">Inserisci giocatori</a>
        </c:if>

        <section class="giocatori">

            <h1 class="ruolo">Portieri</h1>

            <c:forEach var="gioc" items="${portieri}">
                <%@include file="../../../include/box_giocatore.inc" %>
            </c:forEach>

            <h1 class="ruolo">Difensori</h1>

            <c:forEach var="gioc" items="${difensori}">
                <%@include file="../../../include/box_giocatore.inc" %>
            </c:forEach>

            <h1 class="ruolo">Centrocampisti</h1>

            <c:forEach var="gioc" items="${centrocampisti}">
                <%@include file="../../../include/box_giocatore.inc" %>
            </c:forEach>

            <h1 class="ruolo">Attaccanti</h1>

            <c:forEach var="gioc" items="${attaccanti}">
                <%@include file="../../../include/box_giocatore.inc" %>
            </c:forEach>

        </section>

    </section>

    <form name="removeForm" action="removeGioc" method="post" style="display: none;">
        <input type="hidden" name="idGioc">
        <input type="hidden" name="idSquadra" value="${squadra.id}">
    </form>


</main>

<%@include file="../../../include/footer.inc" %>

</body>
</html>