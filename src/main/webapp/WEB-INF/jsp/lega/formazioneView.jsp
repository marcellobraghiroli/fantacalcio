<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <title>Inserisci formazione - FantaFoot</title>

    <style>
        .formazione-section {
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

        .send-button {
            margin: 20px 10px 10px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: #fff;
            border-radius: 4px;
            text-decoration: none;
            border: none;
            font-family: inherit;
            font-size: inherit;
            cursor: pointer;
        }

        .send-button:hover {
            background-color: #45a049;
        }

        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
            font-size: 1.5em;
        }

        select {
            width: 50%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 15px;
        }

        .sel-modulo {
            padding: 20px;
            margin: 20px;
            box-shadow: 0 0 10px 0 rgba(0,0,0,0.4);
            background-image: linear-gradient(120deg, #265e93 0%, #203b6b 100%);
            color: white;
            border-radius: 8px;
            width: 40%;
        }

        .sel-giocatori {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            width: 80%;
            gap: 95px;
            padding: 60px 40px;
            border-radius: 8px;
            box-shadow: 0 0 10px 0 rgba(0,0,0,0.8);
            background-image: url(images/field2.jpg);
            background-size: 100% 100%;
            background-position: center;
            background-repeat: no-repeat;
            margin: 10px;
            border: 4px solid white;
        }

        .portiereSect, .difensoriSect, .centrocampistiSect, .attaccantiSect {
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            gap: 20px;
            width: 100%;
        }

        .sel-giocatori select {
            width: 20%;
        }

    </style>

    <script>

        const portieri = [
            <c:forEach var="portiere" items="${portieri}">
            {
                id: "${portiere.giocatore.id}",
                nome: "${portiere.giocatore.nome}"
            },
            </c:forEach>
        ];

        const difensori = [
            <c:forEach var="difensore" items="${difensori}">
            {
                id: "${difensore.giocatore.id}",
                nome: "${difensore.giocatore.nome}"
            },
            </c:forEach>
        ];

        const centrocampisti = [
            <c:forEach var="centrocampista" items="${centrocampisti}">
            {
                id: "${centrocampista.giocatore.id}",
                nome: "${centrocampista.giocatore.nome}"
            },
            </c:forEach>
        ];

        const attaccanti = [
            <c:forEach var="attaccante" items="${attaccanti}">
            {
                id: "${attaccante.giocatore.id}",
                nome: "${attaccante.giocatore.nome}"
            },
            </c:forEach>
        ];

        const formazione = [
            <c:forEach var="formGioc" items="${giocatoriForm}">
            {
                ordine: "${formGioc.ordine}",
                id: ${formGioc.giocatore.id},
            },
            </c:forEach>
        ];

        document.addEventListener('DOMContentLoaded', function () {
            aggiornaModulo();
            preselectGiocatori();
        });

        function preselectGiocatori() {
            formazione.forEach(g => {
                const select = document.querySelector('select[ordine="' + g.ordine + '"]');
                select.value = g.id;
                disableSelectedOptions(select);
            });
        }

        function aggiornaModulo() {

            const modulo = document.getElementById('moduloSelector').value;

            const [dif, cen, att] = modulo.split('-').map(Number);

            const portiereSect = document.querySelector('.portiereSect');
            const difensoriSect = document.querySelector('.difensoriSect');
            const centrocampistiSect = document.querySelector('.centrocampistiSect');
            const attaccantiSect = document.querySelector('.attaccantiSect');
            attaccantiSect.innerHTML = '';
            centrocampistiSect.innerHTML = '';
            difensoriSect.innerHTML = '';
            portiereSect.innerHTML = '';

            portiereSect.appendChild(createSelector('POR', portieri, 1));

            for (let i = 0; i < dif; i++) {
                difensoriSect.appendChild(createSelector('DIF', difensori, i + 2));
            }

            for (let i = 0; i < cen; i++) {
                centrocampistiSect.appendChild(createSelector('CEN', centrocampisti, i + 2 + dif));
            }

            for (let i = 0; i < att; i++) {
                attaccantiSect.appendChild(createSelector('ATT', attaccanti, i + 2 + dif + cen));
            }

            addChangeListeners();

        }

        function addChangeListeners() {
            document.querySelectorAll('.portiereSect select, .difensoriSect select, .centrocampistiSect select, .attaccantiSect select').forEach(select => {
                select.addEventListener('change', function () {
                    disableSelectedOptions(select);
                });
            });
        }

        function disableSelectedOptions(changedSelect) {
            const role = changedSelect.name;
            //const selects = document.querySelectorAll(`select[name=${role}]`);
            const selects = document.querySelectorAll('select[name=' + role + ']');

            selects.forEach(select => {
                const selectedValues = Array.from(selects).map(s => s.value).filter(v => v);
                Array.from(select.options).forEach(option => {
                    if (selectedValues.includes(option.value) && option.value !== select.value) {
                        option.disabled = true;
                    } else {
                        option.disabled = false;
                    }
                });
            });
        }

        function createSelector(ruolo, giocatori, ordine) {
            const select = document.createElement('select');
            select.name = ruolo;
            select.required = true;
            select.setAttribute('ordine', ordine);

            const emptyOption = document.createElement('option');
            emptyOption.value = '';
            emptyOption.textContent = ruolo + '...';
            //emptyOption.disabled = true;
            emptyOption.selected = true;
            select.appendChild(emptyOption);

            giocatori.forEach(giocatore => {
                const option = document.createElement('option');
                option.value = giocatore.id;
                option.textContent = giocatore.nome;
                select.appendChild(option);
            });

            return select;
        }

        function submitFormazione() {

            const form = document.querySelector('form[action="sendFormazione"]');

            var modulo = document.getElementById('moduloSelector').value;

            form.elements['modulo'].value = modulo;

            //var inputs = [];
            var idGiocatori = [];

            for(let i = 1; i <= 11; i++) {
                const select = document.querySelector('select[ordine="' + i + '"]');
                var idGioc = select.value;
                if(idGioc === '') {
                    alert('Seleziona tutti i giocatori');
                    return;
                }
                //var input = document.createElement('input');
                //input.type = 'hidden';
                //input.name = 'idGioc' + i;
                //input.value = idGioc;
                //form.appendChild(input);
                //inputs.push(input);
                idGiocatori.push(idGioc);
            }

            //inputs.forEach(input => form.appendChild(input));

            //Stampo tutti i campi input del form
            //console.log(form.elements);

            var idGiocatoriString = idGiocatori.join(',');

            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'idGiocatori';
            input.value = idGiocatoriString;
            form.appendChild(input);

            form.submit();

        }


    </script>


</head>
<body>

<%@include file="../../include/header.inc" %>

<main>

    <section class="formazione-section">

        <a href="homeLegaView" class="back-button"
           onclick="return confirm('Sei sicuro di voler tornare alla lega?\n\nEventuali modifiche non salvate andranno perse');">Torna
            alla lega</a>

        <h1 style="color: darkred; font-size: 2em; margin-top: 20px; margin-bottom: 10px;">Inserisci formazione -
            Giornata ${prossGiornata.numero}</h1>

        <section class="sel-modulo">

            <label for="moduloSelector">Seleziona modulo:</label>
            <select id="moduloSelector" name="modulo" required onchange="aggiornaModulo();">

                <option value="3-4-3" ${moduloForm == '3-4-3' ? 'selected' : ''}>3-4-3</option>
                <option value="3-5-2" ${moduloForm == '3-5-2' ? 'selected' : ''}>3-5-2</option>
                <option value="4-5-1" ${moduloForm == '4-5-1' ? 'selected' : ''}>4-5-1</option>
                <option value="4-4-2" ${moduloForm == '4-4-2' ? 'selected' : ''}>4-4-2</option>
                <option value="4-3-3" ${moduloForm == '4-3-3' ? 'selected' : ''}>4-3-3</option>
                <option value="5-4-1" ${moduloForm == '5-4-1' ? 'selected' : ''}>5-4-1</option>
                <option value="5-3-2" ${moduloForm == '5-3-2' ? 'selected' : ''}>5-3-2</option>

            </select>

        </section>


        <section class="sel-giocatori">

            <section class="attaccantiSect"></section>

            <section class="centrocampistiSect"></section>

            <section class="difensoriSect"></section>

            <section class="portiereSect"></section>

        </section>

        <button class="send-button" onclick="submitFormazione()">Invia formazione</button>


    </section>

    <form action="sendFormazione" method="post" style="display: none;">

        <c:if test="${not empty giocatoriForm}">
            <input type="hidden" name="idFormazione" value="${giocatoriForm[0].formazione.id}">
        </c:if>

        <input type="hidden" name="modulo">

        <input type="hidden" name="idSquadra" value="${squadra.id}">

        <input type="hidden" name="numGiornata" value="${prossGiornata.numero}">

    </form>


</main>

<%@include file="../../include/footer.inc" %>

</body>
</html>