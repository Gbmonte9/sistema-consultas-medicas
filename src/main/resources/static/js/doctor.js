const token = localStorage.getItem("token");

// Função genérica de API
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

// ----- CARREGAR AGENDA -----
async function carregarAgenda() {
    const res = await api("http://localhost:8080/medico/agenda");
    const agenda = await res.json();

    const tbody = document.querySelector("#tabelaAgenda tbody");
    tbody.innerHTML = "";

    agenda.forEach(c => {
        tbody.innerHTML += `
            <tr>
                <td>${c.id}</td>
                <td>${c.pacienteNome}</td>
                <td>${c.dataHora}</td>
                <td>${c.status}</td>
                <td class="actions">
                    <button onclick="registrarEvolucao(${c.id})">Registrar Evolução</button>
                </td>
            </tr>
        `;
    });
}

// ----- CARREGAR HISTÓRICO -----
async function carregarHistorico() {
    const res = await api("http://localhost:8080/medico/historico");
    const historico = await res.json();

    const tbody = document.querySelector("#tabelaHistorico tbody");
    tbody.innerHTML = "";

    historico.forEach(p => {
        tbody.innerHTML += `
            <tr>
                <td>${p.id}</td>
                <td>${p.nome}</td>
                <td>${p.ultimaConsulta}</td>
                <td>${p.evolucao}</td>
            </tr>
        `;
    });
}

// ----- REGISTRAR EVOLUÇÃO -----
function registrarEvolucao(consultaId) {
    const evolucao = prompt("Digite a evolução clínica:");
    if (!evolucao) return;

    api(`http://localhost:8080/consultas/${consultaId}/evolucao`, "POST", { evolucao })
        .then(() => {
            alert("Evolução registrada!");
            carregarAgenda();
            carregarHistorico();
        });
}

// ----- LOGOUT -----
function logout() {
    localStorage.removeItem("token");
    window.location.href = "/login";
}

// ----- INICIAR -----
carregarAgenda();
carregarHistorico();
