<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Registrazione</title>
    <style>

        main {
            background: linear-gradient(to bottom, #fff, #658ff1, #fff);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .register-box {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.2);
            margin-top: 60px;
            margin-bottom: 60px;
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        input[type="text"], input[type="password"], input[type="email"], input[type="date"], input[type="tel"] {
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
        .back-button {
            display: block;
            background-color: #f35d5d;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            text-align: center;
            cursor: pointer;
            font-size: 15px;
            width: 100%;
            box-sizing: border-box;
            margin: 10px auto 0 auto;
        }

        .back-button:hover {
            background-color: #c86666;
        }
        @media (max-width: 600px) {
            main {
                width: 100%;
                padding: 10px;
            }
        }
    </style>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('submitButton').addEventListener('click', function(event) {
                var password = document.getElementById('password').value;
                var confirmPassword = document.getElementById('confirmPassword').value;

                if (password !== confirmPassword) {
                    event.preventDefault();
                    alert('Le password non corrispondono');
                }
            });
        });
    </script>
</head>
<body>

<%@include file="../include/header.inc"%>

<main>
    <section class="register-box">
        <h1 style="text-align: center;">Registrati</h1>
    <c:if test="${not empty errorMessage}">
        <p style="color: red; text-align: center; margin: 0 auto 10px auto;">${errorMessage}</p>
    </c:if>
    <form id="registerForm" action="register" method="post">
        <label for="name">Nome:</label>
        <input type="text" id="name" name="nome" value="${allenatoreForm.nome}" required autocomplete="off" maxlength="45">
        <label for="surname">Cognome:</label>
        <input type="text" id="surname" name="cognome" value = "${allenatoreForm.cognome}" required autocomplete="off" maxlength="45">
        <label for="birthdate">Data di nascita:</label>
        <input type="date" id="birthdate" name="dataNascita" value = "${allenatoreForm.dataNascita}" required>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value = "${allenatoreForm.email}" required autocomplete="off" maxlength="45">
        <label for="phone">Telefono:</label>
        <input type="tel" id="phone" name="telefono" value = "${allenatoreForm.telefono}" maxlength="45">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value = "${allenatoreForm.username}" required autocomplete="off" maxlength="45">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value = "${allenatoreForm.password}" required maxlength="45">
        <label for="confirmPassword">Conferma password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" value = "${allenatoreForm.password}" required maxlength="45">
        <input id="submitButton" type="submit" value="Registrati">
    </form>
    <a href="/" class="back-button">Torna al login</a>
    </section>
</main>

<%@include file="../include/footer.inc"%>

</body>
</html>