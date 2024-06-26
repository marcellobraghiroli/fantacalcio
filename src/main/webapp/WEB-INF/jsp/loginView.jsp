<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>FantaFoot - Login</title>

    <style>

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
            margin-bottom: 60px;
            margin-top: 60px;
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

<%@include file="../include/header.inc"%>

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

<%@include file="../include/footer.inc"%>

</body>
</html>
