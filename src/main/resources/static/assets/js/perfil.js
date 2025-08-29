document.addEventListener("DOMContentLoaded", async () => {
  const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));
  if (!usuarioLogado) {
    alert("Você precisa estar logado para acessar o perfil.");
    window.location.href = "login.html";
    return;
  }

  // Preenche dados do usuário
  document.getElementById("nome").textContent = usuarioLogado.nome;
  document.getElementById("email").textContent = usuarioLogado.email;

  const listaJogosTbody = document.getElementById("lista-jogos");
  const listaUsuariosTbody = document.getElementById("listaUsuarios");

  // ========================= JOGOS =========================
  async function carregarJogos() {
    try {
      const resp = await fetch(`http://localhost:8080/api/perfil-jogos/usuario/${usuarioLogado.id}`);
      if (!resp.ok) throw new Error("Erro ao carregar jogos do perfil");

      const jogos = await resp.json();
      listaJogosTbody.innerHTML = "";

      if (jogos.length === 0) {
        listaJogosTbody.innerHTML = `<tr><td colspan="8">Nenhum jogo registrado ainda.</td></tr>`;
      } else {
        jogos.forEach(jogo => {
          const tr = document.createElement("tr");
          tr.innerHTML = `
            <td>${jogo.gameName}</td>
            <td>${jogo.status || "-"}</td>
            <td>${jogo.horasJogadas ?? "-"}</td>
            <td>${jogo.nota ?? "-"}</td>
            <td>${jogo.comentario || "-"}</td>
            <td>${jogo.dataInicio ? new Date(jogo.dataInicio).toLocaleDateString() : "-"}</td>
            <td>${jogo.dataConclusao ? new Date(jogo.dataConclusao).toLocaleDateString() : "-"}</td>
            <td>
              <button class="editar-jogo" data-id="${jogo.id}">Editar</button>
              <button class="remover-jogo" data-id="${jogo.id}">Remover</button>
            </td>
          `;
          listaJogosTbody.appendChild(tr);
        });
      }
    } catch (err) {
      console.error(err);
      alert("Erro ao carregar os jogos: " + err.message);
    }
  }

  // ========================= EVENTOS DE JOGOS =========================
  listaJogosTbody.addEventListener("click", async (e) => {
    const id = e.target.dataset.id;
    if (!id) return;

    // Remover jogo
    if (e.target.classList.contains("remover-jogo")) {
      if (!confirm("Deseja realmente remover este jogo?")) return;
      const resp = await fetch(`http://localhost:8080/api/perfil-jogos/${id}`, { method: "DELETE" });
      if (resp.ok) {
        alert("Jogo removido!");
        carregarJogos();
      } else {
        alert("Erro ao remover jogo.");
      }
    }

    // Editar jogo com janela padrão
    if (e.target.classList.contains("editar-jogo")) {
      const resp = await fetch(`http://localhost:8080/api/perfil-jogos/usuario/${usuarioLogado.id}`);
      const jogos = await resp.json();
      const jogo = jogos.find(j => j.id == id);
      if (!jogo) return;

      const novoStatus = prompt("Status do jogo (JOGOU, QUER_JOGAR, ABANDONOU):", jogo.status);
      if (!novoStatus) return;

      const novasHoras = prompt("Horas jogadas:", jogo.horasJogadas ?? "");
      const novaNota = prompt("Nota:", jogo.nota ?? "");
      const novoComentario = prompt("Comentário:", jogo.comentario ?? "");

      const payload = {
        status: novoStatus,
        horasJogadas: novasHoras ? parseInt(novasHoras) : null,
        nota: novaNota ? parseFloat(novaNota) : null,
        comentario: novoComentario || null
      };

      const putResp = await fetch(`http://localhost:8080/api/perfil-jogos/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      if (putResp.ok) {
        alert("Jogo atualizado!");
        carregarJogos();
      } else {
        alert("Erro ao atualizar jogo.");
      }
    }
  });

  // ========================= USUÁRIOS =========================
  async function carregarUsuarios() {
    try {
      const resp = await fetch("http://localhost:8080/api/usuarios");
      if (!resp.ok) throw new Error("Erro ao carregar usuários");

      const usuarios = await resp.json();
      listaUsuariosTbody.innerHTML = "";

      if (usuarios.length === 0) {
        listaUsuariosTbody.innerHTML = `<tr><td colspan="6">Nenhum usuário encontrado.</td></tr>`;
        return;
      }

      usuarios.forEach(u => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${u.id}</td>
          <td>${u.nome}</td>
          <td>${u.email}</td>
          <td>${u.perfil?.descricao || "-"}</td>
          <td>${u.perfil?.nivelAcesso || "-"}</td>
          <td>
            <button onclick="editarUsuario(${u.id}, '${u.nome}', '${u.email}')">Editar</button>
            <button onclick="deletarUsuario(${u.id})">Excluir</button>
          </td>
        `;
        listaUsuariosTbody.appendChild(tr);
      });
    } catch (err) {
      console.error(err);
      alert("Erro ao carregar usuários: " + err.message);
    }
  }

  window.deletarUsuario = async function (id) {
    if (!confirm("Deseja realmente excluir este usuário?")) return;
    const resp = await fetch(`http://localhost:8080/api/usuarios/${id}`, { method: "DELETE" });
    if (resp.ok) {
      alert("Usuário deletado!");
      carregarUsuarios();
    } else {
      alert("Erro ao deletar usuário.");
    }
  };

  window.editarUsuario = async function (id, nomeAtual, emailAtual) {
    const novoNome = prompt("Novo nome:", nomeAtual);
    if (!novoNome) return;

    const novoEmail = prompt("Novo email:", emailAtual);
    if (!novoEmail) return;

    const resp = await fetch(`http://localhost:8080/api/usuarios/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nome: novoNome, email: novoEmail })
    });

    if (resp.ok) {
      alert("Usuário atualizado!");
      carregarUsuarios();
    } else {
      alert("Erro ao atualizar usuário.");
    }
  };

  // ========================= BOTÃO SAIR =========================
  document.getElementById("sair").addEventListener("click", () => {
    localStorage.removeItem("usuarioLogado");
    window.location.href = "login.html";
  });

  // ========================= INICIALIZAÇÃO =========================
  await carregarJogos();
  await carregarUsuarios();
});
