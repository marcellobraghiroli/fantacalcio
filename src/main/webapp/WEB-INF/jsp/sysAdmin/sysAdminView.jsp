<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Amministratore</title>

    <style>
        .main-section {
            background-color: rgba(255, 255, 255, 0.85);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            text-align: center;
        }

        main h1 {
            font-size: 2em;
            padding: 10px;
            margin: 0;
            color: darkred;
        }

        main h2 {
            font-size: 1.5em;
            padding: 10px;
            margin: 0;
        }

        label {
            display: block;
            margin: 10px 0 10px 0;
            font-weight: bold;
            font-size: 1.2em;
        }

        select, input[type="file"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0 10px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 15px;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            margin: 10px 0 10px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 15px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .votiForm {
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 8px 0 rgba(0, 0, 0, 0.4);
            margin: 20px;
            background-color: white;
        }


    </style>

</head>
<body>

<%@include file="../../include/header.inc" %>

<main>

    <section class="main-section" id="sezioneAmministrazione">

        <h1>Sezione di amministrazione</h1>
        <h2>Carica i voti per la giornata</h2>

        <article class="votiForm">
            <c:if test="${not empty success}">
                <c:choose>
                    <c:when test="${success}">
                        <p style="color: green">Voti caricati con successo</p>
                    </c:when>
                    <c:otherwise>
                        <p style="color: red">${errMessage}</p>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <form action="caricaVoti" method="post" enctype="multipart/form-data">
                <label for="giornataSelector">Seleziona Giornata:</label>
                <select id="giornataSelector" name="giornata" required>
                    <option value="" disabled selected></option>
                    <c:forEach var="giornata" items="${giornate}">
                        <option value="${giornata.numero}">
                            Giornata ${giornata.numero}</option>
                    </c:forEach>
                </select>

                <label for="fileVoti">Carica file voti:</label>
                <input type="file" id="fileVoti" name="fileVoti" accept=".csv" required>

                <input type="submit" value="Carica">
            </form>
        </article>


    </section>

</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>