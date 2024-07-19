<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Scheda giocatore - FantaFoot</title>

    <style>

        .gioc-section {
            background-color: rgba(255, 255, 255, 0.85);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
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

        .player-card {
            background-image: linear-gradient(120deg, #9db1c9 0%, #203b6b 100%);
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            padding: 10px;
            margin: 10px;
            width: 50%;
            text-align: center;
            justify-content: space-evenly;
            align-items: center;
            flex-direction: column;
            overflow: auto;
            color: white;
        }

        article h1 {
            font-size: 1.5em;
            padding: 10px;
            margin: 0;
            color: white;
        }

        article h2 {
            font-size: 1.2em;
            padding: 10px;
            margin: 0;
        }

        table {
            display: block;
            border-collapse: collapse;
            text-align: center;
            padding: 20px 20px 0;
            box-sizing: border-box;
            color: black;
        }

        tbody td {
            padding: 10px;
            border: 1px solid #ddd;
            width: 80px;
            background-color: white;
        }

        td h1 {
            font-size: 1.2em;
            margin: 0;
            padding: 0;
            color: black;
        }

        td p {
            font-size: 1em;
            margin: 0;
            padding-top: 10px;
        }

        .hide {
            border: none;
            background-color: transparent;
        }

    </style>

</head>
<body>

<%@include file="../../../include/header.inc" %>

<main>

    <section class="gioc-section">


        <a href="rosaView" class="back-button">Torna alla rosa</a>

        <h1 style="color: darkred; font-size: 2em;">Scheda Giocatore</h1>

        <article class="player-card">

            <h1 style="text-shadow: 2px 2px 4px rgba(0,0,0,0.7);">${giocatore.nome}</h1>
            <h2>${giocatore.ruolo} - ${giocatore.squadraReale}</h2>

            <table>
                <tbody>
                <tr>
                    <td colspan="3">
                        <h1>Media</h1>
                        <p><fmt:formatNumber value="${giocatore.media}" maxFractionDigits="2"/></p>
                    </td>
                    <td colspan="3">
                        <h1>Fantamedia</h1>
                        <p><fmt:formatNumber value="${giocatore.fantamedia}" maxFractionDigits="2"/></p>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <h1>Presenze</h1>
                        <p>${giocatore.presenze}</p>
                    </td>
                    <td colspan="2">
                        <h1>Goal</h1>
                        <p>${giocatore.goalFatti}</p>
                    </td>
                    <td colspan="2">
                        <h1>Assist</h1>
                        <p>${giocatore.assist}</p>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <h1>Rigori sbagliati</h1>
                        <p>${giocatore.rigSbagliati}</p>
                    </td>
                    <td colspan="2">
                        <h1>Goal subiti</h1>
                        <p>${giocatore.goalSubiti}</p>
                    </td>
                    <td colspan="2">
                        <h1>Rigori parati</h1>
                        <p>${giocatore.rigParati}</p>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <h1>Autogoal</h1>
                        <p>${giocatore.autogoal}</p>
                    </td>
                    <td colspan="2">
                        <h1>Ammonizioni</h1>
                        <p>${giocatore.ammonizioni}</p>
                    </td>
                    <td colspan="2">
                        <h1>Espulsioni</h1>
                        <p>${giocatore.espulsioni}</p>
                    </td>
                </tr>
                <tr>
                    <td class="hide"></td>
                    <td class="hide"></td>
                    <td class="hide"></td>
                    <td class="hide"></td>
                    <td class="hide"></td>
                    <td class="hide"></td>
                </tr>
                </tbody>
            </table>

        </article>


    </section>

</main>

<%@include file="../../../include/footer.inc" %>

</body>
</html>