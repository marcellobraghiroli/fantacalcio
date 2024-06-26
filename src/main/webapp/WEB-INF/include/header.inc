<header>

  <img src="images/logoFF.jpg" alt="FantaFoot logo" class="logo">

  <hgroup>
    <h1>FantaFoot</h1>
    <h2>Il posto perfetto per la gestione del tuo fantacalcio</h2>
  </hgroup>

  <c:choose>
    <c:when test="${logged}">
      <section id="login-section">
        <a href="/logout">Logout</a>
      </section>
    </c:when>
    <c:otherwise>
      <section id="login-section" style="visibility: hidden;">
        <a style="visibility: hidden;">Logout</a>
      </section>
    </c:otherwise>
  </c:choose>

</header>