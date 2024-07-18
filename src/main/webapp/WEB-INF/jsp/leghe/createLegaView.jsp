<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">

    <title>Crea una lega - FantaFoot</title>

    <style>

        main {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .create-box {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.2);
            width: 35%;
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        input[type="text"], input[type="number"], textarea {
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
    </style>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('submitButton').addEventListener('click', function(event) {
                var numSquadre = document.getElementById('members').value;
                if(numSquadre % 2 != 0) {
                    event.preventDefault();
                    alert('Il numero di squadre deve essere pari');
                }
            });
        });
    </script>

</head>
<body>

<%@include file="../../include/header.inc"%>

<main>

    <section class="create-box">

        <h1 style="text-align: center;">Crea una lega</h1>

        <c:if test="${not empty errorMessage}">
            <p style="color: red; text-align: center; margin: 0 auto 10px auto;">${errorMessage}</p>
        </c:if>

        <form action="createLega" method="post">
            <label for="name">Nome:</label>
            <input type="text" id="name" name="nome" value="${legaForm.nome}" required autocomplete="off" maxlength="45">
            <label for="description">Descrizione:</label>
            <textarea id="description" name="descrizione" rows="4" style="resize: none;">${legaForm.descrizione}</textarea>
            <label for="members">Numero partecipanti:</label>
            <input type="number" id="members" name="numSquadre" value="${legaForm.numSquadre}" required autocomplete="off" min="2" max="20">
            <label for="credits">Crediti iniziali:</label>
            <input type="number" id="credits" name="numCrediti" value="${legaForm.numCrediti}" required autocomplete="off" min="1">
            <label for="squadra">Nome della tua squadra:</label>
            <input type="text" id="squadra" name="nomeSquadra" value="${nomeSquadra}" required autocomplete="off" maxlength="45">
            <input type="submit" id="submitButton" value="Crea">
        </form>
        <a href="/legheView" class="back-button">Torna alle leghe</a>

    </section>

</main>

<%@include file="../../include/footer.inc"%>

</body>
</html>