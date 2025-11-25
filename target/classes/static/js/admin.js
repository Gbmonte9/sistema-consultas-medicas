const token = localStorage.getItem("token");

// ----- FUNÇÃO PADRÃO PARA CHAMAR API -----
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

// ----- CARREGAR TODAS AS LISTAGENS -----
async function carregarDados() {
    await carregarMedicos();
    await carregarPacientes();
    await carregarConsultas();
}

// ----- MÉDICOS -----
async function carregarMedicos() {
    const res = await api("http://localhost:8080/medicos");
    const medicos = await res.json();

    const tbody = document.querySelector("#tabelaMedicos tbody");
    tbody.innerHTML = "";

    medicos.forEach(medico => {
        tbody.innerHTML += `
            <tr>
                <td>${medico.id}</td>
                <td>${medico.nome}</td>
                <td>${medico.especialidade}</td>
                <td class="actions">
                    <button onclick="editarMedico(${medico.id})">Editar</button>
                    <button onclick="removerMedico(${medico.id})">Remover</button>
                </td>
            </tr>
        `;
    });
}

function abrirCadastroMedico() {
    alert("Abrir formulário de cadastro de médico (HTML separada)");
}

async function removerMedico(id) {
    if (!confirm("Tem certeza que deseja remover este médico?")) return;

    await api(`http://localhost:8080/medicos/${id}`, "DELETE");
    carregarMedicos();
}

// ----- PACIENTES -----
async function carregarPacientes() {
    const res = await api("http://localhost:8080/pacientes");
    const pacientes = await res.json();

    const tbody = document.querySelector("#tabelaPacientes tbody");
    tbody.innerHTML = "";

    pacientes.forEach(p => {
        tbody.innerHTML += `
            <tr>
                <td>${p.id}</td>
                <td>${p.nome}</td>
                <td>${p.email}</td>
                <td class="actions">
                    <button onclick="editarPaciente(${p.id})">Editar</button>
                    <button onclick="removerPaciente(${p.id})">Remover</button>
                </td>
            </tr>
        `;
    });
}

function abrirCadastroPaciente() {
    alert("Abrir formulário de cadastro de paciente");
}

async function removerPaciente(id) {
    if (!confirm("Deseja remover este paciente?")) return;

    await api(`http://localhost:8080/pacientes/${id}`, "DELETE");
    carregarPacientes();
}

// ----- CONSULTAS -----
async function carregarConsultas() {
    const res = await api("http://localhost:8080/consultas");
    const consultas = await res.json();

    const tbody = document.querySelector("#tabelaConsultas tbody");
    tbody.innerHTML = "";

    consultas.forEach(c => {
        tbody.innerHTML += `
            <tr>
                <td>${c.id}</td>
                <td>${c.pacienteNome}</td>
                <td>${c.medicoNome}</td>
                <td>${c.dataHora}</td>
            </tr>
        `;
    });
}

// ----- RELATÓRIO PDF -----
async function gerarRelatorioPDF() {
    window.location.href = "http://localhost:8080/relatorios/consultas";
}

// ----- LOGOUT -----
function logout() {
    localStorage.removeItem("token");
    window.location.href = "/login";
}

// ----- INICIAR TABELAS -----
carregarDados();
