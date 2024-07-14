<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>La tua lega</title>

    <style>
        .form-section {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);

            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;

        }

        .nav-section {
            background-color: white;
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
            font-size: 2em; /* dimensione del font per h1 */
            padding: 10px;
            margin: 0;
            color: darkred;
        }

        main h2 {
            font-size: 1.5em; /* dimensione del font per h2 */
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

        @media (max-width: 600px) {
            nav ul {
                flex-direction: column;
                align-items: center;
            }

            nav a {
                min-width: 200px; /* Adattamento della larghezza minima per dispositivi mobili */
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
                const date = new Date(now);
                date.setDate(date.getDate() + 2);
                const now2 = date.getTime();
                const distance = endTime - now2;
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

    <section class="nav-section" style="margin-bottom: 40px;">

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

    <section class="form-section">

        <c:choose>
            <c:when test="${not empty giornata}">
                <h3 style="margin: 0; padding: 10px; color: darkred">Scadenza invio formazione -
                    Giornata ${giornata.numero}</h3>
                <div id="countdown">00 d : 00 m : 00 h : 00 s</div>

                <c:if test="${not empty formSuccess}">
                    <c:choose>
                        <c:when test="${formSuccess}">
                            <p style="color: green; text-align: center; padding: 10px; margin: 0;">Formazione inviata con successo</p>
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
                <h3 style="margin: 0; padding: 10px; color: darkred">Il calendario non è ancora stato generato</h3>
            </c:otherwise>
        </c:choose>

    </section>

    <form action="formazioneView" method="post" id="formazioneForm">
        <input type="hidden" name="idSquadra" value="${squadraCorrente.id}">
        <input type="hidden" name="numGiornata" value="${giornata.numero}">
    </form>

</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>