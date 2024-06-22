<%--
  Created by IntelliJ IDEA.
  User: marcellobraghiroli
  Date: 21/06/24
  Time: 17:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FantaFoot - Login</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(to bottom, #0000bd, #add8e6, #0000bd);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        main {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.1);
            margin-bottom: 20px;
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

<main>

    <c:if test="${not empty errorMessage}">
        <p style="color: red; text-align: center">${errorMessage}</p>
    </c:if>
    <c:if test="${not empty loggedOutMessage}">
        <p style="color: green; text-align: center">${loggedOutMessage}</p>
    </c:if>

    <form action="login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required autocomplete="off">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required autocomplete="off">
        <input type="submit" value="Login">
    </form>

    <div class="options">
        <a href="/forgot-password">Password dimenticata?</a> | <a href="/registerView">Registrati</a>
    </div>
</main>

</body>
</html>
