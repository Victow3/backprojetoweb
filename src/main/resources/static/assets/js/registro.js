document.getElementById('registro-form').addEventListener('submit', async function(e) {
  e.preventDefault();

  const nome = document.getElementById('nome').value.trim();
  const email = document.getElementById('email').value.trim();
  const senha = document.getElementById('senha').value;
  const confirmaSenha = document.getElementById('confirma-senha').value;
  const termosAceitos = document.getElementById('terms').checked;

  const responseMessage = document.getElementById('responseMessage');
  responseMessage.textContent = '';

  // Valida confirmação de senha
  if (senha !== confirmaSenha) {
    responseMessage.style.color = 'salmon';
    responseMessage.textContent = 'As senhas não coincidem!';
    return;
  }

  // Valida termos
  if (!termosAceitos) {
    responseMessage.style.color = 'salmon';
    responseMessage.textContent = 'Você precisa aceitar os termos!';
    return;
  }

  try {
    const response = await fetch('http://localhost:8080/api/usuarios', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ nome, email, senha })
    });

    const data = await response.json();

    if (response.ok) {
      responseMessage.style.color = 'lightgreen';
      responseMessage.textContent = 'Usuário criado com sucesso!';
      this.reset(); // limpa o formulário
    } else {
      responseMessage.style.color = 'salmon';
      if (data.errors) {
        responseMessage.textContent = Object.values(data.errors).join(', ');
      } else if (data.error) {
        responseMessage.textContent = data.error;
      } else {
        responseMessage.textContent = 'Erro ao criar usuário.';
      }
    }
  } catch (err) {
    responseMessage.style.color = 'salmon';
    responseMessage.textContent = 'Erro na requisição: ' + err.message;
  }
});
