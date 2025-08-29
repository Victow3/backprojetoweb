const params = new URLSearchParams(window.location.search);
const gameId = params.get("id");

async function loadGame() {
  try {
    const resp = await fetch(`http://localhost:8080/api/games/${gameId}`);
    const game = await resp.json();

    document.getElementById("game-title").textContent = game.name;
    document.getElementById("game-summary").textContent = game.summary || "Sem descrição disponível.";
    document.getElementById("game-release").textContent = game.releaseDate ? new Date(game.releaseDate).toLocaleDateString() : "-";
    document.getElementById("game-developer").textContent = game.developer || "-";
    document.getElementById("game-cover").src = (game.coverUrl || 'assets/images/no-cover.png').replace('t_thumb','t_1080p');

    document.getElementById("gameId").value = game.id;
    document.getElementById("gameName").value = game.name;

    generateStars(game.averageRating || 0);
    generateRatingBars(game.ratingDistribution || {});
  } catch (err) {
    console.error("Erro ao carregar o jogo:", err);
  }
}

function generateStars(avgRating) {
  const container = document.getElementById("star-rating");
  container.innerHTML = '';
  for(let i=1; i<=5; i++){
    const star = document.createElement("i");
    star.className = i <= avgRating ? "fa-solid fa-star text-warning" : "fa-regular fa-star text-warning";
    container.appendChild(star);
  }
}

function generateRatingBars(distribution) {
  const container = document.getElementById("rating-bars");
  container.innerHTML = '';
  const total = Object.values(distribution).reduce((a,b)=>a+b,0);
  for(let i=5; i>=1; i--){
    const count = distribution[i] || 0;
    const percent = total ? (count / total * 100).toFixed(1) : 0;
    const row = document.createElement("div");
    row.className = "d-flex align-items-center mb-1";
    row.innerHTML = `
      <span class="me-2">${i}★</span>
      <div class="progress flex-grow-1">
        <div class="progress-bar bg-warning" role="progressbar" style="width:${percent}%"></div>
      </div>
      <span class="ms-2">${count}</span>
    `;
    container.appendChild(row);
  }
}

// Form submit
document.getElementById("perfilJogoForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));
  if (!usuarioLogado || !usuarioLogado.perfilId) {
    alert("Você precisa estar logado para registrar este jogo.");
    return;
  }

  const payload = {
    perfilUsuario: { id: usuarioLogado.perfilId }, // vincula corretamente o perfil
    gameId: parseInt(document.getElementById("gameId").value),
    gameName: document.getElementById("gameName").value,
    status: document.getElementById("status").value,
    horasJogadas: document.getElementById("horasJogadas").value ? parseInt(document.getElementById("horasJogadas").value) : null,
    nota: document.getElementById("nota").value ? parseFloat(document.getElementById("nota").value) : null,
    comentario: document.getElementById("comentario").value || null
  };

  try {
    const res = await fetch("http://localhost:8080/api/perfil-jogos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if(res.ok) {
      alert("Jogo registrado no perfil com sucesso!");
      document.getElementById("perfilJogoForm").reset();
    } else {
      const err = await res.json();
      alert("Erro: " + JSON.stringify(err));
    }
  } catch(err) {
    console.error("Erro ao salvar perfil:", err);
    alert("Erro ao salvar o perfil");
  }
});

loadGame();
