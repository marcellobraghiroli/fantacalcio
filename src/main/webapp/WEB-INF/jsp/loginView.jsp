<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FantaFoot - Login</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
        }
        main {
            background: linear-gradient(to bottom, #fff, #658ff1, #fff);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-box {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
            margin-bottom: 100px;
            margin-top: 100px;
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

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 20px;
            box-sizing: border-box;
            font-size: 15px;
        }
        input[type="submit"] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 15px;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .options {
            text-align: center;
            margin-top: 20px;
        }
        .options a {
            color: #333;
            text-decoration: none;
        }
        .options a:hover {
            text-decoration: underline;
            cursor: pointer;
        }
        @media (max-width: 600px) {
            main {
                width: 100%;
                padding: 10px;
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

    <section class="login-box">
        <h1 style="text-align: center;">Login</h1>

    <c:if test="${not empty errorMessage}">
        <p style="color: red; text-align: center">${errorMessage}</p>
    </c:if>
    <c:if test="${not empty loggedOutMessage}">
        <p style="color: green; text-align: center">${loggedOutMessage}</p>
    </c:if>

    <form action="login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required autocomplete="off" maxlength="45">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required autocomplete="off" maxlength="45">
        <input type="submit" value="Login">
    </form>

    <div class="options">
        <a>Password dimenticata?</a> | <a href="/registerView">Registrati</a>
    </div>

    </section>
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
