<%@ page session="false"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="css/styles.css">
  <title>FantaFoot</title>
  <style>

    main {
      text-align: center;
    }

    .main-section {
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0px 2px 4px rgba(0,0,0,0.5), 0px -2px 4px rgba(0,0,0,0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
    }

    .buttons-section {
      display: flex;
      justify-content: space-evenly;
      align-items: center;
      flex-direction: row;
      width: 50%;
    }

    .buttons-section a {
      padding: 10px 20px;
      background-color: #007BFF;
      color: #fff;
      border-radius: 4px;
      text-decoration: none;
      width:25%;
    }

    .buttons-section a:hover {
      background-color: #0056b3;
      cursor: pointer;
    }

    .lega-box {
      background-color: white;
      border-radius: 8px;
      box-shadow: 0px 2px 4px rgba(0,0,0,0.5), 0px -2px 4px rgba(0,0,0,0.5);
      padding: 10px;
      margin: 10px;
      width: 50%;
    }

    .lega-box:hover {
      background-color: #e8e8e8;
      cursor: pointer;
    }

  </style>
</head>
<body>

<%@include file="../../include/header.inc"%>

<main>

  <section class="main-section" style="margin-bottom: 40px;">
    <h1 style="color: darkred;">Benvenuto ${allenatoreLoggato.username}</h1>

    <section class="buttons-section">
      <a href="createLegaView" class="button">Crea una lega</a>
      <a href="joinLegaView" class="button">Entra in una lega</a>
    </section>

  </section>

  <section class="main-section">
    <h1 style="color: darkred;">Le tue leghe</h1>
    <c:choose>
      <c:when test="${empty leghe}">
        <p>Non sei ancora iscritto a nessuna lega</p>
      </c:when>
      <c:otherwise>
        <c:forEach var="lega" items="${leghe}">

          <form action="homeLega" method="post" style="display: contents;">

            <input type="hidden" name="idLega" value="${lega.id}">
            <input type="hidden" name="nomeLega" value="${lega.nome}">

            <article class="lega-box" onclick="this.parentNode.submit();">

              <h1>${lega.nome}</h1>
              <p><b>Numero partecipanti:</b> ${lega.numSquadre}<br><b>Crediti inziali:</b> ${lega.numCrediti}</p>
              <p style="font-style: italic; margin: 16px;">${lega.descrizione}</p>

            </article>
          </form>

        </c:forEach>
      </c:otherwise>
    </c:choose>
  </section>

</main>

<%@include file="../../include/footer.inc"%>

</body>
</html>