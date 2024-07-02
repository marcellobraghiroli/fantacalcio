<form action="giocView" method="post" style="display: contents;">
    <input type="hidden" name="idGioc" value="${gioc.giocatore.id}">

    <article class="gioc-box" onclick="this.parentNode.submit();">
        <section>
            <h1 style="text-align: center;">${gioc.giocatore.nome}</h1>
            <p style="padding-top: 0;">
                <b>Prezzo: </b>${gioc.prezzo}<br><b>Squadra: </b>${gioc.giocatore.squadraReale}<br><b>Media: </b>${gioc.giocatore.media} <b>Fantamedia: </b>${gioc.giocatore.fantamedia}
            </p>
        </section>
        <c:if test="${allowMod}">
            <img src="images/trashcan.png" width="22" height="22" alt="Trashcan - remove player"
            onclick="event.stopPropagation(); removePlayer(${gioc.giocatore.id})">
        </c:if>
    </article>
</form>