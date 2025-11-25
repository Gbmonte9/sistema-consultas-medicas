const token = localStorage.getItem("token");

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

// ----- CARREGAR CONSULTAS -----
async function carregarConsultas() {
    const res = await api("http://localhost:8080/paciente/consultas");
    const consultas = await res.json();

    const tbody = document.querySelector("#tabelaConsultas tbody");
    tbody.innerHTML = "";

    consultas.forEach(c => {
        tbody.innerHTML += `
            <tr>
                <td>${c.id}</td>
                <td>${c.medicoNome}</td>
                <td>${c.dataHora}</td>
                <td>${c.status}</td>
                <td>
                    <button onclick="cancelarConsulta(${c.id})">Cancelar</button>
                </td>
            </tr>
        `;
    });
}

// ----- AGENDAR NOVA CONSULTA -----
function agendarConsulta() {
    alert("Abrir tela de agendamento (appointment.html)");
}

// ----- CANCELAR CONSULTA -----
async function cancelarConsulta(id) {
    if (!confirm("Deseja cancelar esta consulta?")) return;

    await api(`http://localhost:8080/consultas/${id}`, "DELETE");
    carregarConsultas();
}

// ----- LOGOUT -----
function logout() {
    localStorage.removeItem("token");
    window.location.href = "/login";
}

// ----- INICIAR -----
carregarConsultas();
