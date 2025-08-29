document.getElementById('loginForm').addEventListener('submit', async function(e) {
  e.preventDefault();

  const email = document.getElementById('email').value.trim();
  const senha = document.getElementById('senha').value;
  const responseMessage = document.getElementById('responseMessage');
  responseMessage.textContent = '';

  try {
    const response = await fetch('http://localhost:8080/api/usuarios/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, senha })
    });

    if (!response.ok) {
      responseMessage.style.color = 'salmon';
      responseMessage.textContent = response.status === 401 ?
        'Email ou senha incorretos.' : 'Erro no login.';
      return;
    }

    const userData = await response.json();

    // ✅ já vem com perfilId
    localStorage.setItem("usuarioLogado", JSON.stringify({
      id: userData.id,
      nome: userData.nome,
      email: userData.email,
      perfilId: userData.perfilId
    }));

    responseMessage.style.color = 'lightgreen';
    responseMessage.textContent = `Bem-vindo, ${userData.nome}!`;

    setTimeout(() => {
      window.location.href = 'index.html';
    }, 1000);

  } catch(err) {
    responseMessage.style.color = 'salmon';
    responseMessage.textContent = 'Erro na requisição: ' + err.message;
    console.error(err);
  }
});
