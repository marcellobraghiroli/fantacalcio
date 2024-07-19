<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Inserisci giocatori - FantaFoot</title>

    <style>

        .ins-player {
            background-color: rgba(255, 255, 255, 0.85);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), 0px -2px 4px rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        #searchBar {
            width: 20%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 1em;
            box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.4);
        }

        nav {
            border: 1px solid #ddd;
            background-color: #f8f8f8;
            margin: 20px;
            padding: 10px;
            box-shadow: 0 0 10px 0 rgba(0,0,0,0.4);
        }

        nav ul {
            list-style: none;
            display: flex;
            justify-content: space-around;
            padding: 0;
            margin: 0;
        }

        nav ul li {
            position: relative;
            margin: 0;
            padding: 0;
        }

        nav ul li a {
            display: block;
            padding: 10px 20px;
            text-decoration: none;
            color: #000;
        }

        nav ul li a:hover {
            background-color: #ddd;
        }

        nav ul li.active a {
            background-color: #007BFF;
            color: #fff;
        }

        #playerList {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .player {
            display: block;
            padding: 10px 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            text-align: center;
            background-color: white;
            margin-bottom: 5px;
            margin-top: 5px;
        }

        .player:hover {
            background-color: #ddd;
            cursor: pointer;
        }

        .players {
            border-radius: 8px;
            max-height: 400px;
            padding: 20px;
            width: 50%;
            overflow: auto;
            background-image: linear-gradient(120deg, #9db1c9 0%, #203b6b 100%);
            box-shadow: 0 0 10px 0 rgba(0,0,0,0.4);
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

        dialog[open] {
            display: block;
            position: fixed;
            z-index: 1;
            padding-top: 100px;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: auto;
            padding: 20px;
            border: 1px solid #888;
            width: 30%;
            max-width: 500px;
            border-radius: 8px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            font-size: 1.2em;
        }

        .close {

            display: blocK;
            width: 100%;
            background-color: #f35d5d;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            text-align: center;
            cursor: pointer;
            box-sizing: border-box;
            margin: 10px auto 0 auto;
            font-size: 15px;

        }

        .close:hover {
            background-color: #c86666;
            text-decoration: none;
            cursor: pointer;
        }

        label {
            display: block;
            margin-bottom: 15px;
        }

        input[type="number"] {
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

        function submitChangeRole(ruolo, event) {
            event.preventDefault();
            var clickedLink = event.target;
            if (clickedLink.closest('li').classList.contains('active')) {
                return;
            }
            document.changeRole.ruolo.value = ruolo;
            document.changeRole.submit();
        }

        document.addEventListener('DOMContentLoaded', function () {
            const giocatoriList = Array.from(document.querySelectorAll('.player'));
            const searchInput = document.getElementById('searchBar');

            //
            const modal = document.getElementById('myModal');
            const close = document.getElementsByClassName('close')[0];
            const playerNameSpan = document.getElementById('playerName');
            const playerIdInput = document.getElementById('playerId');
            const priceInput = document.getElementById('price');
            //

            searchInput.addEventListener('input', function () {
                const searchValue = searchInput.value.toLowerCase();
                giocatoriList.forEach(giocatore => {
                    const nome = giocatore.textContent.toLowerCase();
                    giocatore.style.display = nome.includes(searchValue) ? 'block' : 'none';
                });
            });

            //
            giocatoriList.forEach(giocatore => {
                giocatore.addEventListener('click', function () {
                    const playerName = giocatore.querySelector('b').textContent;
                    const playerId = giocatore.getAttribute('data-id');
                    playerNameSpan.textContent = playerName;
                    playerIdInput.value = playerId;
                    priceInput.value = '';
                    modal.showModal();
                });
            });

            close.onclick = function () {
                modal.close();
            }

            window.onclick = function (event) {
                if (event.target == modal) {            //il modal occupa tutta la finestra, se clicco fuori dal suo contenuto lo chiudo
                    modal.close();
                }
            }

            document.getElementById('submitButton').addEventListener('click', function (event) {
                var prezzo = document.getElementById('price').value;

                var creditiDisponibili = ${squadraCorrente.lega.numCrediti - squadraCorrente.creditiSpesi};

                if (prezzo > creditiDisponibili) {
                    event.preventDefault();
                    alert('Non hai abbastanza crediti');
                }

            });

            //

        });

    </script>

</head>
<body>

<%@include file="../../../include/header.inc" %>

<main>

    <section class="ins-player">

        <a href="rosaView" class="back-button">Torna alla rosa</a>

        <h1 style="font-size: 2em; margin: 0; padding: 20px; color: darkred;">Inserisci giocatori</h1>

        <label for="searchBar" style="font-weight: bold; font-size: 1.2em;">Cerca giocatore o squadra:</label>
        <input type="search" id="searchBar" placeholder="Giocatore o squadra...">


        <c:if test="${not empty success}">
            <c:choose>
                <c:when test="${success}">
                    <p style="color: green; text-align: center; margin: 20px 0 0 0;">Giocatore aggiunto alla rosa</p>
                </c:when>
                <c:otherwise>
                    <p style="color: red; text-align: center; margin: 20px 0 0 0;">Il giocatore è già in rosa</p>
                </c:otherwise>
            </c:choose>
        </c:if>


        <nav>
            <ul>
                <li <c:if test="${ruolo == 'POR'}">class="active"</c:if>>
                    <a href="#" onclick="submitChangeRole('POR', event)">POR</a>
                </li>
                <li <c:if test="${ruolo == 'DIF'}">class="active"</c:if>>
                    <a href="#" onclick="submitChangeRole('DIF', event)">DIF</a>
                </li>
                <li <c:if test="${ruolo == 'CEN'}">class="active"</c:if>>
                    <a href="#" onclick="submitChangeRole('CEN', event)">CEN</a>
                </li>
                <li <c:if test="${ruolo == 'ATT'}">class="active"</c:if>>
                    <a href="#" onclick="submitChangeRole('ATT', event)">ATT</a>
                </li>
            </ul>
        </nav>

        <section class="players">
            <ul id="playerList">
                <c:forEach var="giocatore" items="${giocatori}">
                    <li class="player" data-id="${giocatore.id}">
                        <b>${giocatore.nome}</b> [${giocatore.squadraReale}]
                    </li>
                </c:forEach>
            </ul>
        </section>


    </section>

    <form name="changeRole" action="insGiocView" method="post" style="display: none;">
        <input type="hidden" name="ruolo">
    </form>


    <dialog id="myModal">
        <section class="modal-content">
            <p style="margin-top: 0; margin-bottom: 15px;"><b>Crediti:</b> ${squadraCorrente.lega.numCrediti - squadraCorrente.creditiSpesi}</p>
            <form id="priceForm" action="insGioc" method="post">

                <input type="hidden" id="playerId" name="idGioc">
                <input type="hidden" name="idSquadra" value="${squadraCorrente.id}">

                <label for="price">Inserisci il prezzo per <b id="playerName"></b></label>
                <input type="number" id="price" name="price" placeholder="Prezzo" required min="1">
                <input type="submit" id="submitButton" value="Conferma">
            </form>
            <button class="close">Annulla</button>
        </section>
    </dialog>

</main>

<%@include file="../../../include/footer.inc" %>

</body>
</html>