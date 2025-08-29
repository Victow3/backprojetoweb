class Footer extends HTMLElement {
  connectedCallback() {
    this.innerHTML = `
    <footer class="footer-color py-3 mt-auto">
        <div class="container">
            <ul class="nav justify-content-center border-bottom pb-3 mb-3">
                <li class="nav-item"><a href="index.html" class="nav-link px-2 text-white">Home</a></li>
                <li class="nav-item"><a href="about.html" class="nav-link px-2 text-white">About</a></li>
                <li class="nav-item"><a href="atividades.html" class="nav-link px-2 text-white">Atividades</a></li>
            </ul>
            <p class="text-center text-white">© 2025 Company, Inc</p>
        </div>
  </footer>
    `;
    }
}

customElements.define("main-footer", Footer);