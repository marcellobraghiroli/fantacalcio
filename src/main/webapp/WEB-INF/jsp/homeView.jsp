<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>FantaFoot</title>
  <style>
    body {
      font-family: 'Roboto', sans-serif;
      margin: 0;
      padding: 0;
      color: #333;
      background-color: #f4f4f4;
    }

    header {
      background-color: #fff;
      display: flex;
      justify-content: center;
      flex-direction: column;
      align-items: center;
      padding: 20px;
      box-shadow: 0 2px 4px 0 rgba(0,0,0,0.2);
      position: relative;
    }

    header h1, header h2 {
      text-align: center;
    }

    header h2 {
      font-style: italic;
    }

    #login-section {
      position: absolute;
      right: 30px;
      top: 50%;
      transform: translateY(-50%);
    }

    #login-section a {
      display: inline-block;
      padding: 10px 20px;
      background-color: #007BFF;
      color: #fff;
      border-radius: 4px;
      text-decoration: none;
    }

    #login-section a:hover {
      background-color: #0056b3;
    }

    footer {
      background-color: #fff;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      box-shadow: 0 -2px 4px 0 rgba(0,0,0,0.2);
      text-align: center;
    }

    footer h4 {
      margin-bottom: 5px; /* Imposta il margine inferiore a 5px */
    }

    #social a, #info a {
      color: black;
      text-decoration: none;
    }

    #social a:hover, #info a:hover {
      text-decoration: underline;
      cursor: pointer;
    }

    @media (max-width: 600px) {
      header {
        flex-direction: column;
        align-items: center;
      }
    }
  </style>
</head>
<body>
<header>

  <c:if test="${logged}">
    <section id="login-section">
      <a href="/logout">Logout</a>
    </section>
  </c:if>

  <h1>FantaFoot</h1>
  <h2>Il posto perfetto per la gestione del tuo fantacalcio</h2>

</header>
<main>
  <h1>Benvenuto ${allenatoreLoggato.username}!</h1>
  <p>Benvenuto nel fantastico mondo di FantaFoot!</p>
  <p>
    ${allenatoreLoggato.id} <br>
    ${allenatoreLoggato.username} <br>
    ${logged}
  </p>
</main>
<footer>
  <section id="contatti">
    <h4>Contatti</h4>
    <p style="margin: 0;">
       Email: support@fantafoot.com<br>
       Telefono: +39 800 800 8080<br>
       Indirizzo: Piazza Trento Trieste 1, 44123 Ferrara, Italia
    </p>
  </section>
  <section id="social">
    <h4>Social</h4>
    <article>
      <a>Facebook</a> |
      <a>Twitter</a> |
      <a>Instagram</a> |
      <a>TikTok</a>
    </article>
  </section>
  <section id="info">
    <h4>Informazioni</h4>
    <article>
      <a>Privacy Policy</a> |
      <a>Cookie Policy</a> |
      <a>Termini e condizioni</a> |
      <a>Area legale</a>
    </article>
  </section>
  <section id="copyright">
    <p>&copy; 2021 FantaFoot. Tutti i diritti riservati.</p>
  </section>
</footer>

</body>
</html>