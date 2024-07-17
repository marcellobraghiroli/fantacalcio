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

        #degrada, #elimina, #espelli {
            background-color: #f35d5d;
        }

        #degrada:hover, #elimina:hover, #espelli:hover {
            background-color: #c86666;
        }

    </style>

</head>
<body>

<%@include file="../../include/header.inc" %>

<main>

    <section class="admin-section">

        <a href="homeLegaView" class="back-button">Torna alla lega</a>

        <h1 style="color: darkred; font-size: 2em; margin-top: 20px; margin-bottom: 10px;">Area Admin</h1>

        <!-- Gestione calendario -->

        <section class="box">
            <c:choose>
                <c:when test="${started}">
                    <h2 style="margin-top: 10px; margin-bottom: 20px;">La stagione è iniziata</h2>

                    <c:if test="${not empty genSuccess}">
                        <c:if test="${genSuccess}">
                            <p style="color: green; text-align: center; margin-top: 0; margin-bottom: 10px;">Calendario
                                generato con successo</p>
                        </c:if>
                    </c:if>


                    <h2 style="color: darkred; text-align: center; margin-top: 10px; margin-bottom: 10px;">Calcola
                        giornata</h2>

                    <c:if test="${not empty calcSuccess}">
                        <c:choose>
                            <c:when test="${calcSuccess}">
                                <p style="color: green; text-align: center; margin-top: 0; margin-bottom: 10px;">
                                    Giornata calcolata con successo</p>
                            </c:when>
                            <c:otherwise>
                                <p style="color: red; text-align: center; margin-top: 0; margin-bottom: 10px;">${errMessage}</p>
                            </c:otherwise>
                        </c:choose>
                    </c:if>


                    <form action="calcGiornata" method="post">

                        <input type="hidden" name="idLega" value="${legaCorrente.id}">

                        <label for="giornataSelector">Seleziona Giornata:</label>
                        <select id="giornataSelector" name="numGiornata" required>
                            <option value="" disabled selected></option>
                            <c:forEach var="giornata" items="${giornate}">
                                <option value="${giornata.numero}">
                                    Giornata ${giornata.numero}</option>
                            </c:forEach>
                        </select>

                        <input type="submit" value="Calcola">

                    </form>

                </c:when>
                <c:otherwise>
                    <h2 style="margin-top: 10px; margin-bottom: 20px;">La stagione non è ancora
                        iniziata</h2>
                    <c:if test="${gradoAdmin == 'super'}">

                        <h2 style="color: darkred; text-align: center; margin-top: 10px; margin-bottom: 10px;">Genera
                            calendario</h2>

                        <c:if test="${not empty genSuccess}">
                            <c:if test="${!genSuccess}">
                                <p style="color: red; text-align: center; margin-top: 0; margin-bottom: 10px;">${errMessage}</p>
                            </c:if>
                        </c:if>

                        <form action="generaCal" method="post">
                            <input type="hidden" name="idLega" value="${legaCorrente.id}">

                            <input type="submit" value="Genera">

                        </form>

                    </c:if>
                </c:otherwise>
            </c:choose>

        </section>

        <!-- Gestione inviti -->

        <section class="box">

            <h2 style="text-align: center; color: darkred; margin-top: 10px; margin-bottom: 10px;">Invita un allenatore
                nella lega</h2>

            <c:if test="${not empty invSuccess}">
                <c:choose>
                    <c:when test="${invSuccess}">
                        <p style="color: green; text-align: center; margin: 0 auto 10px auto;">Allenatore invitato con
                            successo</p>
                    </c:when>
                    <c:otherwise>
                        <p style="color: red; text-align: center; margin-top: 0; margin-bottom: 10px;">${errMessage}</p>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <form action="invitaAll" method="post">

                <label for="username">Username allenatore:</label>
                <input type="text" id="username" name="username" required autocomplete="off" maxlength="45">

                <input type="submit" id="submitButton" value="Invita">

            </form>

        </section>

        <!-- Gestione gradi admin -->

        <c:if test="${gradoAdmin == 'super'}">
            <section class="box">

                <h2 style="text-align: center; color: darkred; margin-top: 10px; margin-bottom: 10px;">Gestisci gli
                    admin</h2>

                <c:if test="${not empty proSuccess}">
                    <c:choose>
                        <c:when test="${proSuccess}">
                            <p style="color: green; text-align: center; margin: 0 auto 10px auto;">${succMessage}</p>
                        </c:when>
                        <c:otherwise>
                            <p style="color: red; text-align: center; margin-top: 0; margin-bottom: 10px;">${errMessage}</p>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <form action="promDegrAll" method="post">

                    <label for="allSelect">Selezione allenatore:</label>
                    <select id="allSelect" name="idAll" required>
                        <option value="" disabled selected></option>
                        <c:forEach var="allenatore" items="${allenatoriLega}">
                            <option value="${allenatore.id}">${allenatore.username}</option>
                        </c:forEach>
                    </select>

                    <input type="submit" id="submitButton2" name="action" value="Promuovi">
                    <input type="submit" id="degrada" style="margin-top: 10px;" name="action" value="Degrada">

                </form>
            </section>
        </c:if>

        <!-- Espelli allenatori -->

        <c:if test="${gradoAdmin == 'super' && !started}">

            <section class="box">

                <h2 style="text-align: center; color: darkred; margin-top: 10px; margin-bottom: 10px;">Espelli un
                    allenatore</h2>

                <c:if test="${not empty espSuccess && espSuccess}">
                    <p style="color: green; text-align: center; margin: 0 auto 10px auto;">${succMessage}</p>
                </c:if>

                <form action="espAll" method="post">

                    <label for="allSelect2">Selezione allenatore:</label>
                    <select id="allSelect2" name="idAll" required>
                        <option value="" disabled selected></option>
                        <c:forEach var="allenatore" items="${allenatoriLega}">
                            <option value="${allenatore.id}">${allenatore.username}</option>
                        </c:forEach>
                    </select>

                    <input type="submit" id="espelli" value="Espelli"
                           onclick="return confirm('Sei sicuro di voler espellere l\'allenatore?');">


                </form>


            </section>

        </c:if>

        <!-- Elimina lega -->

        <c:if test="${gradoAdmin == 'super'}">

            <section class="box">
                <h2 style="text-align: center; color: darkred; margin-top: 10px; margin-bottom: 10px;">Elimina lega</h2>

                <form action="eliminaLega" method="post">

                    <input type="hidden" name="idLega" value="${legaCorrente.id}">

                    <input type="submit" id="elimina" value="Elimina"
                           onclick="return confirm('Sei sicuro di voler eliminare la lega?\n\nL\'azione è irreversibile');">
                </form>

            </section>

        </c:if>


    </section>

</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>