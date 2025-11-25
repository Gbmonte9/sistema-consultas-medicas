const token = localStorage.getItem("token");

// Função genérica para chamadas API
async function api(url, method = "GET", body = null) {
    return await fetch(url, {
        method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: body ? JSON.stringify(body) : null
    });
}

// Carregar médicos no select
async function carregarMedicos() {
    try {
        const res = await api("http://localhost:8080/medicos");
        const medicos = await res.json();

        const select = document.getElementById("medico");
        medicos.forEach(m => {
            const option = document.createElement("option");
            option.value = m.id;
            option.textContent = `${m.nome} - ${m.especialidade}`;
            select.appendChild(option);
        });
    } catch (err) {
        mostrarErro("Erro ao carregar médicos");
    }
}

// Agendar consulta
async function agendarConsulta() {
    const medicoId = document.getElementById("medico").value;
    const dataHora = document.getElementById("dataHora").value;
    const motivo = document.getElementById("motivo").value.trim();

    if (!medicoId || !dataHora || !motivo) {
        mostrarErro("Preencha todos os campos!");
        return;
    }

    try {
        const res = await api("http://localhost:8080/consultas", "POST", {
            medicoId,
            dataHora,
            motivo
        });

        if (res.ok) {
            mostrarSucesso("Consulta agendada com sucesso!");
            // Limpar campos
            document.getElementById("medico").value = "";
            document.getElementById("dataHora").value = "";
            document.getElementById("motivo").value = "";
        } else {
            const data = await res.json();
            mostrarErro(data.message || "Erro ao agendar consulta");
        }
    } catch (err) {
        mostrarErro("Erro na conexão com o servidor");
    }
}

// Cancelar agendamento
function cancelar() {
    window.history.back();
}

// Mensagem de erro
function mostrarErro(msg) {
    const div = document.getElementById("errorMsg");
    div.textContent = msg;
    div.classList.remove("d-none");
    setTimeout(() => div.classList.add("d-none"), 4000);
}

// Mensagem de sucesso
function mostrarSucesso(msg) {
    const div = document.getElementById("successMsg");
    div.textContent = msg;
    div.classList.remove("d-none");
    setTimeout(() => div.classList.add("d-none"), 4000);
}

// Inicializar médicos
carregarMedicos();
