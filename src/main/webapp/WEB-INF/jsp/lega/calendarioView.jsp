<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Calendario - FantaFoot</title>

    <style>
        .cal-section {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            text-align: center;
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

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
            font-size: 1.5em;
        }

        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 15px;
        }

        .sel-giornata {
            padding: 5px 10px 20px;
        }

        .partite {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            gap: 10px;
            padding: 20px;
            margin-top: 10px;
            margin-bottom: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.4);
            width: 75%;
        }

        .partita {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
            border-radius: 8px;
            border: 1px solid #ddd;
            font-size: 1.2em;
        }

        .casa, .trasferta, .risultato {
            flex: 1;
            text-align: center;
            padding: 5px 10px;
            line-height: 1.5;
        }

        .risultato {
            margin: auto;
            font-weight: bold;
            font-size: 1.2em;
        }

        .partita:hover {
            background-color: #ddd;
            cursor: pointer;
        }

    </style>

    <script>

        function aggiornaPartite() {
            var giornataSelezionata = document.getElementById("giornataSelector").value;
            var partite = document.querySelectorAll(".partita");

            partite.forEach(function (partita) {
                if (partita.getAttribute("data-giornata") === giornataSelezionata) {
                    partita.style.display = "";
                } else {
                    partita.style.display = "none";
                }
            });
        }

        document.addEventListener('DOMContentLoaded', aggiornaPartite);
    </script>

</head>
<body>

<%@include file="../../include/header.inc" %>

<main>

    <section class="cal-section" id="calendario">

        <a href="homeLegaView" class="back-button">Torna alla lega</a>

        <h1 style="color: darkred; font-size: 2em;">Calendario ${legaCorrente.nome}</h1>

        <section class="sel-giornata">

            <label for="giornataSelector">Seleziona Giornata:</label>
            <select id="giornataSelector" onchange="aggiornaPartite()">
                <c:forEach var="numeroGiornata" items="${numeriGiornate}">
                    <option value="${numeroGiornata}" ${numeroGiornata == giornataCorrente.numero ? 'selected' : ''}>
                        Giornata ${numeroGiornata}</option>
                </c:forEach>
            </select>

        </section>


        <section class="partite" id="listaPartite">
            <c:forEach var="partita" items="${partite}">
                <form action="partitaView" method="post" style="display: contents;">

                    <input type="hidden" name="idPartita" value="${partita.id}">
                    <input type="hidden" name="idSqCasa" value="${partita.squadraCasa.id}">
                    <input type="hidden" name="idSqTrasf" value="${partita.squadraTrasf.id}">
                    <input type="hidden" name="numGiornata" value="${partita.giornata.numero}">

                    <article class="partita" data-giornata="${partita.giornata.numero}" onclick="this.parentNode.submit();">
                        <p class="casa">
                            <b>${partita.squadraCasa.nome}</b>
                            <br>
                                ${partita.puntiCasa}
                        </p>
                        <p class="risultato">${partita.risultato}</p>
                        <p class="trasferta">
                            <b>${partita.squadraTrasf.nome}</b>
                            <br>
                                ${partita.puntiTrasf}
                        </p>
                    </article>
                </form>
            </c:forEach>
        </section>


    </section>

</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>