<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrazione</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
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
    <form action="register" method="post">
        <label for="name">Nome:</label>
        <input type="text" id="name" name="name" required autocomplete="off">
        <label for="surname">Cognome:</label>
        <input type="text" id="surname" name="surname" required autocomplete="off">
        <label for="birthdate">Data di nascita:</label>
        <input type="date" id="birthdate" name="birthdate" required>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required autocomplete="off">
        <label for="phone">Telefono:</label>
        <input type="tel" id="phone" name="phone">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required autocomplete="off">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <input type="submit" value="Registrati">
    </form>
</main>
</body>
</html>