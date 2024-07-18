<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Partita - FantaFoot</title>

    <style>
        .partita-section {
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

        .partita {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            gap: 10px;
            padding: 20px;
            margin-top: 10px;
            margin-bottom: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.4);
            width: 75%;
        }

        .info-partita {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
            border-radius: 8px;
            border: 2px solid #ddd;
            font-size: 1.5em;
        }

        .casa, .trasferta, .risultato {
            flex: 1;
            text-align: center;
            padding: 5px 10px;
            line-height: 1.5;
        }

        .risultato {
            margin: auto;
            font-weight: bold;
            font-size: 1.2em;
        }

        .info-giocatori {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
        }

        .gioc-casa {
            flex: 1;
            text-align: center;
            padding: 5px 20px;
            line-height: 1.5;
            width: 50%;
            border: 1px solid #ddd;
            border-radius: 8px;

            display: flex;
            flex-direction: row;
            justify-content: space-between;
            align-items: center;


        }

        .gioc-trasf {
            flex: 1;
            text-align: center;
            padding: 5px 20px;
            line-height: 1.5;
            width: 50%;
            border: 1px solid #ddd;
            border-radius: 8px;

            display: flex;
            flex-direction: row;
            justify-content: space-between;
            align-items: center;

        }

        .gioc-casa .info-gioc, .gioc-trasf .voto {
            text-align: left;
        }

        .gioc-casa .voto, .gioc-trasf .info-gioc {
            text-align: right;
        }

        article p {
            line-height: 1.5;
            margin: 10px;
        }

    </style>

</head>
<body>

<%@include file="../../include/header.inc" %>

<main>

    <section class="partita-section">

        <a href="calendarioView?numGiornata=${partita.giornata.numero}" class="back-button">Torna al calendario</a>

        <h1 style="color: darkred; font-size: 2em;">Dettaglio Partita</h1>


        <section class="partita">

            <article class="info-partita">
                <p class="casa">
                    <b>${partita.squadraCasa.nome}</b>
                    <br>
                    ${partita.puntiCasa}
                    <br>
                    <b>${formCasa.modulo}</b>
                </p>
                <p class="risultato">${partita.risultato}</p>
                <p class="trasferta">
                    <b>${partita.squadraTrasf.nome}</b>
                    <br>
                    ${partita.puntiTrasf}
                    <br>
                    <b>${formTrasf.modulo}</b>
                </p>
            </article>

            <c:if test="${not empty giocatoriCasa || not empty giocatoriTrasf}">

                <c:forEach var="i" begin="0" end="10" varStatus="status">

                    <c:set var="giocCasa" value="${giocatoriCasa[status.index]}"/>
                    <c:set var="giocTrasf" value="${giocatoriTrasf[status.index]}"/>

                    <section class="info-giocatori">

                        <c:choose>
                            <c:when test="${not empty giocatoriCasa}">
                                <article class="gioc-casa">

                                    <p class="info-gioc">
                                        <b><span style="${giocCasa.giocatore.ruolo == 'POR' ? 'color: #f09837;' :
                                              giocCasa.giocatore.ruolo == 'DIF' ? 'color: #357a23;' :
                                              giocCasa.giocatore.ruolo == 'CEN' ? 'color: #448bbb;' :
                                              giocCasa.giocatore.ruolo == 'ATT' ? 'color: #9c2224;' : ''}">
                                                ${giocCasa.giocatore.ruolo}</span> - ${giocCasa.giocatore.nome}
                                        </b>
                                        <br>
                                        <c:forEach begin="1" end="${giocCasa.giocatore.giocGiornata.goalFatti}"
                                                   var="goal">
                                            <img src="images/statsIcons/goal.png" alt="Goal"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocCasa.giocatore.giocGiornata.assist}"
                                                   var="goal">
                                            <img src="images/statsIcons/assist.png" alt="Assist"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocCasa.giocatore.giocGiornata.rigParati}"
                                                   var="goal">
                                            <img src="images/statsIcons/save.png" alt="Rigore parato"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocCasa.giocatore.giocGiornata.rigSbagliati}"
                                                   var="goal">
                                            <img src="images/statsIcons/rigoresbagliato.png" alt="Rigore sbagliato"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocCasa.giocatore.giocGiornata.goalSubiti}"
                                                   var="goal">
                                            <img src="images/statsIcons/goal_subito.png" alt="Goal subito"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocCasa.giocatore.giocGiornata.autogoal}"
                                                   var="goal">
                                            <img src="images/statsIcons/autogoal.png" alt="Autogoal"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:if test="${giocCasa.giocatore.giocGiornata.ammonito.toString() == 'Y'}">
                                            <img src="images/statsIcons/yellowcard.png" alt="Cartellino giallo"
                                                 style="width: 20px; height: 20px;">
                                        </c:if>
                                        <c:if test="${giocCasa.giocatore.giocGiornata.espulso.toString() == 'Y'}">
                                            <img src="images/statsIcons/redcard.png" alt="Cartellino rosso"
                                                 style="width: 20px; height: 20px;">
                                        </c:if>
                                    </p>
                                    <p class="voto">
                                            ${giocCasa.giocatore.giocGiornata.voto}
                                        <br>
                                        <b>${giocCasa.giocatore.giocGiornata.fantavoto}</b>
                                    </p>
                                </article>
                            </c:when>
                            <c:otherwise>

                                <c:choose>
                                    <c:when test="${status.index == 0}">
                                        <p style="font-weight: bold; font-size: 1.2em; width: 50%; height: 100%; text-align: center;">
                                            Formazione non schierata</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="font-weight: bold; font-size: 1.2em; width: 50%; height: 0; text-align: center;"></p>
                                    </c:otherwise>
                                </c:choose>

                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${not empty giocatoriTrasf}">
                                <article class="gioc-trasf">
                                    <p class="voto">
                                            ${giocTrasf.giocatore.giocGiornata.voto}
                                        <br>
                                        <b>${giocTrasf.giocatore.giocGiornata.fantavoto}</b>
                                    </p>
                                    <p class="info-gioc">
                                        <b>${giocTrasf.giocatore.nome} - <span style="${giocTrasf.giocatore.ruolo == 'POR' ? 'color: #f09837;' :
                                              giocTrasf.giocatore.ruolo == 'DIF' ? 'color: #357a23;' :
                                              giocTrasf.giocatore.ruolo == 'CEN' ? 'color: #448bbb;' :
                                              giocTrasf.giocatore.ruolo == 'ATT' ? 'color: #9c2224;' : ''}">
                                                ${giocTrasf.giocatore.ruolo}</span></b>
                                        <br>
                                        <c:if test="${giocTrasf.giocatore.giocGiornata.espulso.toString() == 'Y'}">
                                            <img src="images/statsIcons/redcard.png" alt="Cartellino rosso"
                                                 style="width: 20px; height: 20px;">
                                        </c:if>
                                        <c:if test="${giocTrasf.giocatore.giocGiornata.ammonito.toString() == 'Y'}">
                                            <img src="images/statsIcons/yellowcard.png" alt="Cartellino giallo"
                                                 style="width: 20px; height: 20px;">
                                        </c:if>
                                        <c:forEach begin="1" end="${giocTrasf.giocatore.giocGiornata.autogoal}"
                                                   var="goal">
                                            <img src="images/statsIcons/autogoal.png" alt="Autogoal"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocTrasf.giocatore.giocGiornata.goalSubiti}"
                                                   var="goal">
                                            <img src="images/statsIcons/goal_subito.png" alt="Goal subito"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocTrasf.giocatore.giocGiornata.rigSbagliati}"
                                                   var="goal">
                                            <img src="images/statsIcons/rigoresbagliato.png" alt="Rigore sbagliato"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocTrasf.giocatore.giocGiornata.rigParati}"
                                                   var="goal">
                                            <img src="images/statsIcons/save.png" alt="Rigore parato"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocTrasf.giocatore.giocGiornata.assist}"
                                                   var="goal">
                                            <img src="images/statsIcons/assist.png" alt="Assist"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                        <c:forEach begin="1" end="${giocTrasf.giocatore.giocGiornata.goalFatti}"
                                                   var="goal">
                                            <img src="images/statsIcons/goal.png" alt="Goal"
                                                 style="width: 20px; height: 20px;">
                                        </c:forEach>
                                    </p>
                                </article>
                            </c:when>
                            <c:otherwise>

                                <c:choose>
                                    <c:when test="${status.index == 0}">
                                        <p style="font-weight: bold; font-size: 1.2em; width: 50%; height: 100%; text-align: center;">
                                            Formazione non schierata</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="font-weight: bold; font-size: 1.2em; width: 50%; height: 0; text-align: center;"></p>
                                    </c:otherwise>
                                </c:choose>


                            </c:otherwise>
                        </c:choose>

                    </section>

                </c:forEach>

            </c:if>

            <c:if test="${empty giocatoriCasa && empty giocatoriTrasf}">
                <p style="font-weight: bold; font-size: 1.2em;">Formazioni non schierate</p>
            </c:if>

        </section>


    </section>

</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>