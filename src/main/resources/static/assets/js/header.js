class Header extends HTMLElement {
  connectedCallback() {
    const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

    this.innerHTML = `
<header class="container-fluid p-0">
  <nav class="navbar navbar-expand-md navbar-dark bg-transparent p-0">
    <div class="container p-0">
      <a class="navbar-brand col-md-4" href="index.html">Place Holder</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
        aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse col-md-8" id="navbarCollapse">
        <div class="d-flex w-100 justify-content-end gap-3 align-items-center">
          <a href="games.html" class="text-white text-decoration-none">Games</a>

          ${usuarioLogado 
            ? `<div class="logged-user d-flex align-items-center gap-2">
                 <span class="text-white">Olá,</span>
                 <a href="perfil.html" class="user-name-link text-decoration-none">${usuarioLogado.nome}</a>
                 <button id="logoutBtn" class="btn btn-logout">Logout</button>
               </div>` 
            : `<a href="login.html" class="btn text-white custom-btn-login">Login</a>`}

          <form id="searchForm" role="search" class="d-flex">
            <input id="searchInput" class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-light" type="submit">
              <i class="fas fa-search"></i>
            </button>
          </form>
        </div>
      </div>
    </div>
  </nav>
</header>
    `;

    // ===== JS de busca =====
    const searchForm = this.querySelector('#searchForm');
    const searchInput = this.querySelector('#searchInput');

    if (searchForm && searchInput) {
      searchForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const nome = searchInput.value.trim();
        if (!nome) return;
        window.location.href = `games.html?search=${encodeURIComponent(nome)}`;
      });

      let timeout;
      searchInput.addEventListener('input', () => {
        clearTimeout(timeout);
        timeout = setTimeout(() => {
          const nome = searchInput.value.trim();
          if (nome) {
            // Busca dinâmica opcional
          }
        }, 300);
      });
    }

    // ===== Logout =====
    const logoutBtn = this.querySelector('#logoutBtn');
    if (logoutBtn) {
      logoutBtn.addEventListener('click', () => {
        localStorage.removeItem("usuarioLogado");
        window.location.reload();
      });
    }
  }
}

customElements.define("main-header", Header);
