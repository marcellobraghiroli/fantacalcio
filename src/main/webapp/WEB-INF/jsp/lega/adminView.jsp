<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Area admin</title>

    <style>
        .admin-section {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            text-align: center;
        }

        .back-button {
            margin: 10px;
            padding: 10px 20px;
            background-color: #f35d5d;
            color: #fff;
            border-radius: 4px;
            text-decoration: none;
        }

        .back-button:hover {
            background-color: #c86666;
        }

        .box {
            background-color: white;
            padding: 20px;
            margin: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.2);
            width: 35%;
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }

        input[type="text"], select {
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

    </style>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var selectElement = document.getElementById('allSelect');
            var hiddenInput = document.getElementById('idAll');

            selectElement.addEventListener('change', function () {
                var selectedOption = selectElement.options[selectElement.selectedIndex];
                var selectedId = selectedOption.getAttribute('data-id');
                hiddenInput.value = selectedId;
            });
        });
    </script>


</head>
<body>

<%@include file="../../include/header.inc" %>

<main>

    <section class="admin-section">

        <a href="homeLegaView" class="back-button">Torna alla lega</a>

        <h1 style="color: darkred; font-size: 2em;">Area Admin</h1>

        <!-- Gestione calendario -->

        <section class="gest-calendario">
            <c:choose>
                <c:when test="${started}">
                    <h2>La stagione è iniziata</h2>

                    <h2>Calcola giornata</h2>
                    <button>Calcola</button>

                </c:when>
                <c:otherwise>
                    <c:if test="${gradoAdmin == 'super'}">
                        <h2>Genera calendario e inizia la stagione</h2>
                        <button>Genera</button>
                    </c:if>
                </c:otherwise>
            </c:choose>

        </section>

        <!-- Gestione inviti -->

        <section class="box">

            <h2 style="text-align: center; margin-top: 10px; margin-bottom: 10px;">Invita un allenatore nella lega</h2>

            <c:if test="${not empty invSuccess}">
                <c:choose>
                    <c:when test="${invSuccess}">
                        <p style="color: green; text-align: center; margin: 0 auto 10px auto;">Allenatore invitato con
                            successo</p>
                    </c:when>
                    <c:otherwise>
                        <p style="color: red; text-align: center; margin-top: 0; margin-bottom: 10px;">Allenatore
                            inesistente</p>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <form action="invitaAll" method="post">

                <input type="hidden" name="nomeLega" value="${legaCorrente.nome}">
                <input type="hidden" name="usernameAdmin" value="${allenatoreLoggato.username}">

                <label for="username">Username allenatore:</label>
                <input type="text" id="username" name="username" required autocomplete="off" maxlength="45">

                <input type="submit" id="submitButton" value="Invita">

            </form>

        </section>

        <!-- Gestione gradi admin -->

        <c:if test="${gradoAdmin == 'super'}">
            <section class="box">

                <h2 style="text-align: center; margin-top: 10px; margin-bottom: 10px;">Promuovi ad admin</h2>

                <c:if test="${not empty proSuccess}">
                    <c:choose>
                        <c:when test="${proSuccess}">
                            <p style="color: green; text-align: center; margin: 0 auto 10px auto;">Allenatore promosso con successo</p>
                        </c:when>
                        <c:otherwise>
                            <p style="color: red; text-align: center; margin-top: 0; margin-bottom: 10px;">L'allenatore è già un admin</p>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <form action="promuoviAll" method="post">

                    <input type="hidden" name="idLega" value="${legaCorrente.id}">
                    <input type="hidden" name="idAll" id="idAll">

                    <label for="username">Selezione allenatore:</label>
                    <select id="allSelect" name="username" required>
                        <option value="" disabled selected></option>
                        <c:forEach var="allenatore" items="${allenatoriLega}">
                            <option value="${allenatore.username}"
                                    data-id="${allenatore.id}">${allenatore.username}</option>
                        </c:forEach>
                    </select>

                    <input type="submit" id="submitButton2" value="Promuovi">

                </form>
            </section>
        </c:if>


    </section>

</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>