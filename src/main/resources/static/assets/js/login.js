document.getElementById('loginForm').addEventListener('submit', async function(e) {
  e.preventDefault();

  const email = document.getElementById('email').value.trim();
  const senha = document.getElementById('senha').value;

  const responseMessage = document.getElementById('responseMessage');
  responseMessage.textContent = '';

  try {
    const response = await fetch('http://localhost:8080/api/usuarios/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email, senha })
    });

    if (response.ok) {
      const data = await response.json();
      responseMessage.style.color = 'lightgreen';
      responseMessage.textContent = `Bem-vindo, ${data.nome}!`;

      setTimeout(() => {
        window.location.href = 'index.html';
      }, 1000);
      // Aqui você pode redirecionar para a página principal
      // window.location.href = 'index.html';
    } else if (response.status === 401) {
      responseMessage.style.color = 'salmon';
      responseMessage.textContent = 'Email ou senha incorretos.';
    } else {
      responseMessage.style.color = 'salmon';
      responseMessage.textContent = 'Erro no login.';
    }
  } catch (err) {
    responseMessage.style.color = 'salmon';
    responseMessage.textContent = 'Erro na requisição: ' + err.message;
  }
});
