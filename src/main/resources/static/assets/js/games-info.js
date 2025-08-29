const params = new URLSearchParams(window.location.search);
const gameId = params.get("id");

async function loadGame() {
  const resp = await fetch(`http://localhost:8080/api/games/${gameId}`);
  const game = await resp.json();

  document.getElementById("game-info").innerHTML = `
    <h2>${game.name}</h2>
    <img src="${(game.coverUrl || '').replace('t_thumb', 't_1080p')}" 
         alt="${game.name}" class="img-fluid mb-3" style="max-width:200px">
    <p>${game.summary || "Sem descrição disponível."}</p>
  `;

  document.getElementById("gameId").value = game.id;
  document.getElementById("gameName").value = game.name;
}

document.getElementById("perfilJogoForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const payload = {
    perfilId: 1, // futuramente pegar do usuário logado
    gameId: document.getElementById("gameId").value,
    gameName: document.getElementById("gameName").value,
    status: document.getElementById("status").value,
    horasJogadas: document.getElementById("horasJogadas").value || null,
    nota: document.getElementById("nota").value || null,
    comentario: document.getElementById("comentario").value || null
  };

  const res = await fetch("http://localhost:8080/api/perfil-jogos", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });

  if (res.ok) {
    alert("Jogo registrado no perfil com sucesso!");
  } else {
    const err = await res.json();
    alert("Erro: " + JSON.stringify(err));
  }
});

loadGame();
