<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Classifica - FantaFoot</title>

    <style>

        .class-section {
            background-color: rgba(255, 255, 255, 0.85);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            flex-direction: column;
            overflow: auto;
        }

        table {
            display: block;
        }

        table, th, td {

            border-collapse: collapse;
            padding: 10px;
            text-align: center;
            caption-side: bottom;
        }

        th, td {
            border: 2px solid black;
        }

        caption {
            font-style: italic;
            padding: 30px;
        }

        thead th {
            background-image: linear-gradient(120deg, #265e93 0%, #203b6b 100%);
            color: white;
        }


        .clickable {
            cursor: pointer;
        }

        .clickable:hover {
            background-image: linear-gradient(120deg, #9db1c9 0%, #203b6b 100%);
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


    </style>

    <script>
        function sortTable(n) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("classifica");
            switching = true;
            dir = "desc";
            while (switching) {
                switching = false;
                rows = table.rows;
                for (i = 1; i < (rows.length - 1); i++) {
                    shouldSwitch = false;
                    x = rows[i].getElementsByTagName("TD")[n];
                    y = rows[i + 1].getElementsByTagName("TD")[n];
                    if (dir == "asc") {
                        if (Number(x.innerHTML.toLowerCase()) > Number(y.innerHTML.toLowerCase())) {
                            shouldSwitch = true;
                            break;
                        }
                    } else if (dir == "desc") {
                        if (Number(x.innerHTML.toLowerCase()) < Number(y.innerHTML.toLowerCase())) {
                            shouldSwitch = true;
                            break;
                        }
                    }
                }
                if (shouldSwitch) {
                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                    switching = true;
                    switchcount++;
                } else {
                    if (switchcount == 0 && dir == "desc") {
                        dir = "asc";
                        switching = true;
                    }
                }
            }
        }
    </script>


</head>
<body>

<%@include file="../../include/header.inc" %>

<main>

    <section class="class-section">

        <a href="homeLegaView" class="back-button">Torna alla lega</a>

        <h1 style="color: darkred; font-size: 2em;">Classifica ${legaCorrente.nome}</h1>


        <table id="classifica">
            <caption>
                Clicca sulle intestazioni per ordinare la classifica<br><br>
                PG = Partite giocate<br>
                V = Partite vinte<br>
                P = Partite pareggiate<br>
                S = Partite perse<br>
                GF = Goal fatti<br>
                GS = Goal subiti
            </caption>
            <thead>
            <tr>
                <th>Pos</th>
                <th>Squadra</th>
                <th onclick="sortTable(2)" class="clickable">Punti</th>
                <th onclick="sortTable(3)" class="clickable">Fantapunti</th>
                <th>PG</th>
                <th onclick="sortTable(5)" class="clickable">V</th>
                <th onclick="sortTable(6)" class="clickable">P</th>
                <th onclick="sortTable(7)" class="clickable">S</th>
                <th onclick="sortTable(8)" class="clickable">GF</th>
                <th onclick="sortTable(9)" class="clickable">GS</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="squadra" items="${squadre}" varStatus="status">
                <tr
                        <c:choose>
                            <c:when test="${squadra.allenatore.id == allenatoreLoggato.id}">
                                style="background-color: #b0c3da;"
                            </c:when>
                            <c:otherwise>
                                style="background-color: white;"
                            </c:otherwise>
                        </c:choose>
                >
                    <td style="background-image: linear-gradient(120deg, #9db1c9 0%, #203b6b 100%); color: white;"><b>${status.index + 1}</b></td>
                    <td><b>${squadra.nome}</b><br><span
                            style="font-style: italic;">${squadra.allenatore.username}</span></td>
                    <td>${squadra.puntiClass}</td>
                    <td>${squadra.fantapunti}</td>
                    <td>${squadra.partGiocate}</td>
                    <td>${squadra.partVinte}</td>
                    <td>${squadra.partParegg}</td>
                    <td>${squadra.partPerse}</td>
                    <td>${squadra.goalFatti}</td>
                    <td>${squadra.goalSubiti}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


    </section>


</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>