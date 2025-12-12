# 🌐 Interface Web — Sistema de Gestão de Consultas Médicas

Este documento descreve a **interface web** do sistema, que será implementada usando **HTML, CSS, Bootstrap e JavaScript**, integrando com o backend Java (Spring Boot).

---

## 🎯 Objetivo
A interface web deve ser **intuitiva, responsiva e acessível**, permitindo que usuários (pacientes, médicos e administradores) interajam com o sistema via navegador.

---

## 🧩 Telas e Componentes Principais

### 1. Tela de Login
- Campos: **E-mail**, **Senha** - Botão: `Entrar`
- Valida credenciais e redireciona conforme tipo de usuário:
  - Admin → Painel Administrativo
  - Médico → Painel Médico
  - Paciente → Painel do Paciente

---

### 2. Painel do Administrador
- Listagem de médicos e pacientes com ações:
  - Cadastrar, editar, remover
- Tabela de consultas
- Acesso à geração de relatórios em PDF

---

### 3. Painel do Médico
- Agenda de consultas do dia
- Histórico dos pacientes
- Botão de registro de evolução clínica
- Notificações de novos agendamentos

---

### 4. Painel do Paciente
- Listagem de consultas agendadas
- Botão para agendar nova consulta
- Cancelamento ou reagendamento de consultas

---

### 5. Tela de Agendamento
- Campos:
  - Seleção de médico (dropdown)
  - Data e hora da consulta
  - Motivo da consulta
- Botões: `Confirmar`, `Cancelar`
- Validação de disponibilidade do médico

---

### 6. Tela de Relatórios
- Geração de relatórios em PDF:
  - Consultas por período
  - Médicos mais consultados
  - Pacientes ativos
- Processos em segundo plano (Threads) para não travar a UI

---

## 🎨 Estilo e Layout
- Tema: tons neutros (cinza escuro e preto) com textos brancos
- Layout responsivo usando **Bootstrap** - Botões escuros com destaque (`btn-dark`)
- Cards para separar seções e informações
- Ícones via **Font Awesome** - **Wireframes:** Esboços visuais (mockups) detalhados para cada painel principal serão mantidos na pasta `/assets/wireframes`.

---

## 🔄 Fluxo de Navegação

Login → Painel Principal (Admin / Médico / Paciente) → Telas de Cadastro / Agendamento / Histórico / Relatórios

---

## 🧱 Estrutura de Pastas Web

src/main/resources/ ├── templates/ # HTML (Thymeleaf) das páginas ├── static/ │ ├── css/ # Arquivos CSS (Bootstrap + custom) │ ├── js/ # JavaScript │ └── images/ # Ícones e imagens

---

## 🧠 Observações

* Cada tela HTML terá integração com os endpoints REST do backend
* Uso de AJAX ou fetch para atualizar dados sem recarregar páginas
* Interface responsiva e compatível com dispositivos móveis



