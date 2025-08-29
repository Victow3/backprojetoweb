const grid = document.getElementById("games-grid");
const params = new URLSearchParams(window.location.search);
const termoBusca = params.get("search"); // <- corrigido

let limit = 60;
let offset = 0;
let carregando = false;
let ultimoLoteVazio = false;

function cardHtml(game) {
  const cover = (game.coverUrl || '').replace('t_thumb', 't_1080p') || 'assets/images/no-cover.png';
  const ano = game.releaseDate ? new Date(game.releaseDate).getFullYear() : "-";
  return `
    <a href="games-info.html?id=${game.id}" class="text-decoration-none text-white">
      <div class="game-card shadow">
        <img src="${cover}" alt="${game.name}" class="game-cover">
        <div class="overlay"><h5>${game.name}</h5></div>
        <div class="card-content">
          <p>${ano}</p>
        </div>
      </div>
    </a>
  `;
}

async function carregarMais() {
  if (carregando || ultimoLoteVazio) return;
  carregando = true;

  try {
    const base = "http://localhost:8080/api/games";
    const url = termoBusca
      ? `${base}/buscar?nome=${encodeURIComponent(termoBusca)}&limit=${limit}&offset=${offset}`
      : `${base}?limit=${limit}&offset=${offset}`;

    const resp = await fetch(url);
    const games = await resp.json();

    if (!Array.isArray(games) || games.length === 0) {
      ultimoLoteVazio = true;
      if (offset === 0) {
        grid.innerHTML = `<div class="col-12 text-center text-white">Nenhum jogo encontrado.</div>`;
      }
      return;
    }

    const frag = document.createDocumentFragment();
    games.forEach(g => {
      const col = document.createElement("div");
      col.className = "col-6 col-md-4 col-lg-3 col-xl-2";
      col.innerHTML = cardHtml(g);
      frag.appendChild(col);
    });
    grid.appendChild(frag);

    offset += limit;
  } catch (err) {
    console.error("Erro ao buscar jogos:", err);
  } finally {
    carregando = false;
  }
}

function montarBotaoMais() {
  let btn = document.getElementById("btn-carregar-mais");
  if (!btn) {
    btn = document.createElement("div");
    btn.className = "col-12 text-center my-4";
    btn.innerHTML = `<button id="btn-carregar-mais" class="btn btn-outline-light">Carregar mais</button>`;
    grid.parentElement.appendChild(btn);
    btn.querySelector("button").addEventListener("click", carregarMais);
  }
}

document.addEventListener("DOMContentLoaded", async () => {
  grid.innerHTML = ""; // limpa
  offset = 0;
  ultimoLoteVazio = false;
  await carregarMais();
  montarBotaoMais();
});
