<%@ page session="false"%>
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
    .info-section {
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0px 2px 4px rgba(0,0,0,0.5), 0px -2px 4px rgba(0,0,0,0.5);

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
    }

    .formazione {
      padding: 10px 20px;
      background-color: #4CAF50;
      color: #fff;
      border-radius: 4px;
      text-decoration: none;
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

    ul {
      list-style: none;
      padding: 40px;
      margin: 0;
      display: flex;
      justify-content: space-evenly;
      align-items: center;
    }

    ul li {
      margin: 10px;
    }

    ul li a {
      padding: 20px;
      background-color: #007BFF;
      color: #fff;
      border-radius: 4px;
      text-decoration: none;
      display: block;
    }

    ul li a:hover {
      background-color: #0056b3;
    }

    @media (max-width: 600px) {
      ul {
        flex-direction: column;
      }
    }

  </style>

  <script>
    function startCountdown(tsInizio) {
      const countdownElement = document.getElementById('countdown');
      const endTime = new Date(tsInizio).getTime();

      function updateCountdown() {
        const now = new Date().getTime();
        //const now = new Date(2024, 7, 7, 11, 0, 0).getTime();
        const distance = endTime - now;

        if (distance < 0) {
          countdownElement.innerHTML = "00:00:00:00";
          clearInterval(interval);
          return;
        }

        const days = Math.floor(distance / (1000 * 60 * 60 * 24));
        const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((distance % (1000 * 60)) / 1000);

        countdownElement.innerHTML =
                (days < 10 ? '0' : '') + days + ":" +
                (hours < 10 ? '0' : '') + hours + ":" +
                (minutes < 10 ? '0' : '') + minutes + ":" +
                (seconds < 10 ? '0' : '') + seconds;
      }

      updateCountdown();
      const interval = setInterval(updateCountdown, 1000);
    }

    window.onload = function() {
      const tsInizio = '${giornata.tsInizio}';
      if (tsInizio) {
        startCountdown(tsInizio);
      } else {
        document.getElementById('countdown').innerHTML = "00:00:00:00";
      }
    };
  </script>


</head>
<body>

<%@include file="../../include/header.inc"%>

<main>

  <section class="info-section" style="margin-bottom: 40px;">

    <h1>${legaCorrente.nome}</h1>
    <h2>${squadraCorrente.nome}</h2>

    <c:choose>
      <c:when test="${not empty giornata}">
        <h3 style="margin: 0; margin-top: 20px; padding: 10px;">Scadenza invio formazione</h3>
        <div id="countdown">00:00:00:00</div>
        <a href="formazione" class="formazione">Inserisci formazione</a>
      </c:when>
      <c:otherwise>
        <p>Il calendario non è ancora stato generato</p>
      </c:otherwise>
    </c:choose>


  </section>

  <section class="nav-section">

    <nav>
      <ul>
        <li><a href="backToLeghe">< Le tue leghe</a></li>
        <li><a href="calendario">Calendario</a></li>
        <li><a href="classifica">Classifica</a></li>
        <li><a href="rose">Rose</a></li>
        <c:if test="${isAdmin}">
          <li><a href="areaAdmin">Area Admin</a></li>
        </c:if>
      </ul>
    </nav>

  </section>

</main>

<%@include file="../../include/footer.inc"%>

</body>
</html>