# Interface Web --- Sistema de Gestão de Consultas Médicas

## 🎯 Objetivo

A interface web deve ser intuitiva, responsiva e acessível, permitindo
interação entre pacientes, médicos e administradores.

------------------------------------------------------------------------

## 🧩 Telas e Componentes Principais

### 1. Tela de Login

-   Campos: e-mail, senha
-   Redireciona conforme o tipo de usuário

### 2. Painel do Administrador

-   Gerenciamento de médicos e pacientes
-   Consultas
-   Relatórios em PDF

### 3. Painel do Médico

-   Agenda
-   Histórico
-   Registro de evolução

### 4. Painel do Paciente

-   Consultas
-   Agendamento
-   Reagendamento/Cancelamento

### 5. Tela de Agendamento

-   Seleção de médico
-   Data e hora
-   Motivo da consulta

### 6. Tela de Relatórios

-   Relatórios PDF
-   Processos em background

------------------------------------------------------------------------

## 🎨 Estilo e Layout

-   Tema escuro
-   Bootstrap + CSS customizado
-   Font Awesome

------------------------------------------------------------------------

## 🔄 Fluxo de Navegação

Login → Painel Principal → Telas de Cadastro / Agendamento / Histórico /
Relatórios

------------------------------------------------------------------------

## 🧱 Estrutura de Pastas Web

    src/main/resources/
    ├── templates/
    ├── static/
    │   ├── css/
    │   └── js/
    └── images/

------------------------------------------------------------------------

# 🛠️ Detalhamento da Estrutura de Arquivos

## controller/ViewController.java

Controla as rotas para os arquivos HTML.

## templates/

-   login.html
-   admin_dashboard.html
-   doctor_dashboard.html
-   patient_dashboard.html
-   appointment.html

## static/js/

-   auth.js
-   admin.js
-   doctor.js
-   patient.js
-   appointment.js
-   api.js

## static/css/styles.css

Tema escuro customizado.

## fragments/

-   header.html
-   footer.html
-   sidebar.html
