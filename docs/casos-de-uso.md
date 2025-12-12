# ⚙️ Casos de Uso do Sistema

O sistema de gestão de consultas é dividido nos seguintes Casos de Uso, refletindo as principais interações e regras de negócio:

| Código | Nome | Atores | Descrição |
| :--- | :--- | :--- | :--- |
| **C1** | Cadastrar Paciente | Admin | Permite criar o perfil de um paciente, associando a um usuário para login e gerenciamento de permissões. |
| **C2** | Cadastrar Médico | Admin | Registra médicos, vinculando o usuário e as informações profissionais obrigatórias (CRM, Especialidade). |
| **C3** | **Agendar Consulta** | Paciente | Permite a seleção de médico, data e hora. O sistema **verifica a disponibilidade do médico** (sem conflito de horário) e a antecedência mínima (30 min) antes de salvar o agendamento. |
| **C4** | **Cancelar Consulta** | Paciente / Médico / Admin | Altera o status da consulta para CANCELADA. Regras de negócio como antecedência mínima para cancelamento e proibição de cancelar consultas `REALIZADAS` devem ser aplicadas. |
| **C5** | **Visualizar Agenda** | Médico / Paciente / Admin | Permite a consulta à agenda. O Médico visualiza sua agenda completa. O Paciente visualiza apenas suas consultas agendadas. O Admin visualiza todas as consultas do sistema. |
| **C6** | **Registrar Histórico** | Médico | Permite ao médico registrar o relatório da consulta e/ou prescrição. Este processo altera o status da consulta para `REALIZADA`. |
| **C7** | **Gerar Relatório (PDF)** | Admin | Inicia a geração de um relatório estatístico de consultas (por período, médico, ou status). O processamento é idealmente executado em **segundo plano (assíncrono)** para evitar travamento da API. |
| **C8** | **Autenticar Usuário** | Paciente, Médico, Admin | Permite o login via credenciais (usuário/senha) e gera um token de acesso (JWT) para autorização das demais operações na API. |