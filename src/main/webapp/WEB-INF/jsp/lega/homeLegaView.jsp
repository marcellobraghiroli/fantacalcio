<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Home Lega - FantaFoot</title>

    <style>

        .box {
            background-color: rgba(255, 255, 255, 0.85);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .formazione {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: #fff;
            border-radius: 4px;
            text-decoration: none;
            margin: 10px;
        }

        .formazione:hover {
            background-color: #45a049;
        }

        main h1 {
            font-size: 2em;
            padding: 10px;
            margin: 0;
            color: darkred;
        }

        main h2 {
            font-size: 1.5em;
            padding: 10px;
            margin: 0;
            color: darkred;
        }

        #countdown {
            padding: 10px;
            font-size: 2em;
        }

        nav ul {
            list-style: none;
            padding: 20px;
            margin: 0;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 10px;
        }

        nav li {
            margin: 0;
            flex: 1;
        }

        nav a {
            display: inline-block;
            padding: 20px 20px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            text-align: center;
            box-sizing: border-box;
            font-size: 1.1em;
            width: 140px;
        }

        nav a:hover {
            background-color: #0056b3;
        }

        #back {
            background-color: #f35d5d;
        }

        #back:hover {
            background-color: #c86666;
        }

        .exitButton {
            margin: 10px;
            padding: 10px 20px;
            background-color: #f35d5d;
            color: #fff;
            border-radius: 4px;
            text-decoration: none;
        }

        .exitButton:hover {
            background-color: #c86666;
        }

        @media (max-width: 600px) {
            nav ul {
                flex-direction: column;
                align-items: center;
            }

            nav a {
                min-width: 200px;
            }
        }


    </style>

    <script>
        function startCountdown(tsInizio) {
            const countdownElement = document.getElementById('countdown');
            const endTime = new Date(tsInizio).getTime();
            const formLink = document.getElementById('formLink');

            function updateCountdown() {
                const now = new Date().getTime();
                //const date = new Date(now);
                //date.setDate(date.getDate() + 2);
                //const now2 = date.getTime();
                const distance = endTime - now;
                //console.log(distance);

                if (distance < 0) {
                    console.log('countdown ended');
                    countdownElement.innerHTML = "00 d : 00 h : 00 m : 00 s";
                    formLink.style.display = 'none';
                    clearInterval(interval);
                    return;
                }

                const days = Math.floor(distance / (1000 * 60 * 60 * 24));
                const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((distance % (1000 * 60)) / 1000);

                countdownElement.innerHTML =
                    (days < 10 ? '0' : '') + days + " d : " +
                    (hours < 10 ? '0' : '') + hours + " h : " +
                    (minutes < 10 ? '0' : '') + minutes + " m : " +
                    (seconds < 10 ? '0' : '') + seconds + " s";
            }

            updateCountdown();
            const interval = setInterval(updateCountdown, 1000);
        }

        window.onload = function () {
            const tsInizio = '${giornata.tsInizio}';
            if (tsInizio) {
                startCountdown(tsInizio);
            } else {
                document.getElementById('countdown').innerHTML = "00 d : 00 h : 00 m : 00 s";
            }
        };

        document.addEventListener('DOMContentLoaded', function () {

            var giornata = '${giornata}';
            if (!giornata) {
                var calLink = document.querySelector('a[href="calendarioView"]');
                calLink.addEventListener('click', function (event) {
                    event.preventDefault();
                });
                calLink.style.cursor = 'default';
            }


        });

    </script>


</head>
<body>

<%@include file="../../include/header.inc" %>

<main>

    <section id="navigazioneLega" class="box" style="margin-bottom: 40px;">

        <h1>${legaCorrente.nome}</h1>
        <h2>${squadraCorrente.nome}</h2>

        <nav>
            <ul>
                <li><a href="backToLeghe" id="back">Leghe</a></li>
                <li><a href="calendarioView">Calendario</a></li>
                <li><a href="classificaView">Classifica</a></li>
                <li><a href="roseView">Rose</a></li>
                <c:if test="${isAdmin}">
                    <li><a href="adminView">Area Admin</a></li>
                </c:if>
            </ul>
        </nav>


    </section>

    <section id="sezioneFormazione" class="box" style="margin-bottom: 40px;">

        <c:choose>
            <c:when test="${not empty giornata}">
                <h1 style="margin: 0; padding: 10px; color: darkred; font-size: 1.5em;">Scadenza invio formazione -
                    Giornata ${giornata.numero}</h1>
                <div id="countdown">00 d : 00 m : 00 h : 00 s</div>

                <c:if test="${not empty formSuccess}">
                    <c:choose>
                        <c:when test="${formSuccess}">
                            <p style="color: green; text-align: center; padding: 10px; margin: 0;">Formazione inviata
                                con successo</p>
                        </c:when>
                        <c:otherwise>
                            <p style="color: red; text-align: center; margin-top: 0; margin-bottom: 10px;">${errorMessage}</p>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <a href="#" class="formazione" id="formLink"
                   onclick="event.preventDefault(); document.getElementById('formazioneForm').submit();">Inserisci
                    formazione</a>
            </c:when>
            <c:otherwise>
                <h2 style="margin: 0; padding: 10px; color: darkred; font-size: 1.2em;">Il calendario non è ancora stato generato</h2>
            </c:otherwise>
        </c:choose>

    </section>

    <c:if test="${allowExit}">

        <section id="abbandonaLega" class="box">

            <h1 style="font-size: 1.5em;">Abbandona lega</h1>

            <c:choose>
                <c:when test="${empty giornata}">
                    <a href="abbandonaLega" class="exitButton"
                       onclick="return confirm('Sei sicuro di voler abbandonare la lega?\n\nL\'azione è irreversibile')">Abbandona</a>
                </c:when>
                <c:otherwise>
                    <h2 style="margin: 0; padding: 10px; color: darkred; font-size: 1.2em;">Non puoi uscire dalla lega a campionato
                        iniziato</h2>
                </c:otherwise>
            </c:choose>

        </section>

    </c:if>

    <form action="formazioneView" method="post" id="formazioneForm">
        <input type="hidden" name="idSquadra" value="${squadraCorrente.id}">
        <input type="hidden" name="numGiornata" value="${giornata.numero}">
    </form>

</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>