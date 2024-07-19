<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Rose - FantaFoot</title>

    <style>

        .rose-section {
            background-color: rgba(255, 255, 255, 0.85);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .squadra-box {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            padding: 10px;
            margin: 10px;
            width: 75%;
            text-align: center;
        }

        .squadra-box:hover {
            background-color: #e8e8e8;
            cursor: pointer;
        }

        article h1 {
            font-size: 1.5em;
            padding: 10px;
            margin: 0;
            color: darkred;
        }

        article p {
            margin: 0;
            padding: 10px;
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

        .squadre {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-image: linear-gradient(120deg, #9db1c9 0%, #203b6b 100%);
            width: 60%;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px 0 rgba(0,0,0,0.4);
        }

    </style>

</head>
<body>

<%@include file="../../include/header.inc" %>

<main>

    <section class="rose-section">

        <a href="homeLegaView" class="back-button">Torna alla lega</a>

        <h1 style="color: darkred; font-size: 2em;">Rose ${legaCorrente.nome}</h1>

        <section class="squadre">

            <c:forEach var="squadra" items="${squadre}">

                <form action="goToRosaView" method="post" style="display: contents;">

                    <input type="hidden" name="id" value="${squadra.id}">
                    <input type="hidden" name="nome" value="${squadra.nome}">
                    <input type="hidden" name="creditiSpesi" value="${squadra.creditiSpesi}">

                    <input type="hidden" name="creditiLega" value="${squadra.lega.numCrediti}">
                    <input type="hidden" name="nomeAll" value="${squadra.allenatore.username}">

                    <input type="hidden" name="idAllSq" value="${squadra.allenatore.id}">

                    <article class="squadra-box" onclick="this.parentNode.submit();" <c:if
                            test="${squadra.allenatore.id == allenatoreLoggato.id}"> style="background-color: #d3d3d3;" onmouseout="this.style.backgroundColor='#d3d3d3';" onmouseover="this.style.backgroundColor='#bcbcbc';" </c:if>>

                        <h1>${squadra.nome}</h1>

                        <p><b>Allenatore: </b>${squadra.allenatore.username}&nbsp;&nbsp;&nbsp;&nbsp;
                            <b>Crediti: </b>${squadra.lega.numCrediti - squadra.creditiSpesi}</p>

                    </article>
                </form>

            </c:forEach>
        </section>

    </section>


</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>