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
    .form-section {
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

  <section class="nav-section" style="margin-bottom: 40px;">

    <h1>${legaCorrente.nome}</h1>
    <h2>${squadraCorrente}</h2>

    <nav>
      <ul>
        <li><a href="backToLeghe" id="back">Leghe</a></li>
        <li><a href="calendario">Calendario</a></li>
        <li><a href="classifica">Classifica</a></li>
        <li><a href="rose">Rose</a></li>
        <c:if test="${isAdmin}">
          <li><a href="areaAdmin">Area Admin</a></li>
        </c:if>
      </ul>
    </nav>


  </section>

  <section class="form-section">

    <c:choose>
      <c:when test="${not empty giornata}">
        <h3 style="margin: 0; padding: 10px; color: darkred">Scadenza invio formazione</h3>
        <div id="countdown">00:00:00:00</div>
        <a href="formazione" class="formazione">Inserisci formazione</a>
      </c:when>
      <c:otherwise>
        <h3 style="margin: 0; padding: 10px; color: darkred">Il calendario non è ancora stato generato</h3>
      </c:otherwise>
    </c:choose>

  </section>

</main>

<%@include file="../../include/footer.inc"%>

</body>
</html>