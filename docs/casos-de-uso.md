# ⚙️ Casos de Uso do Sistema

Este documento detalha as principais interações entre os usuários e a API, destacando as regras de negócio e restrições aplicadas em cada operação.



---

## 📋 Tabela de Casos de Uso

| ID | Caso de Uso | Atores | Descrição e Regras de Negócio |
| :--- | :--- | :--- | :--- |
| **C1** | **Autenticar Usuário** | Todos | Validação de credenciais via e-mail/senha. Retorna um **Token JWT** que define o nível de acesso (RBAC) para as demais rotas. |
| **C2** | **Cadastrar Paciente** | Admin / Público | Criação do perfil de paciente. O sistema realiza a criptografia **AES-128** do CPF antes da persistência. |
| **C3** | **Agendar Consulta** | Paciente / Admin | Reserva de horário. **Regra:** O sistema impede o agendamento se o médico já possuir uma consulta no mesmo horário ou se a data for retroativa. |
| **C4** | **Agendar e Finalizar** | Médico | **Fluxo Atômico:** Permite registrar uma consulta ocorrida fora do sistema, criando simultaneamente o agendamento e o histórico clínico. |
| **C5** | **Cancelar Consulta** | Todos | Altera status para `CANCELADA`. **Regra:** Não é permitido cancelar consultas com status `FINALIZADA`. |
| **C6** | **Registrar Histórico** | Médico | O médico insere observações e prescrições. Ao salvar, o status da consulta é alterado automaticamente para `FINALIZADA`. |
| **C7** | **Visualizar Dashboard** | Médico | Consulta à agenda do dia atual (`/hoje`) e estatísticas de produtividade (Atendidos vs. Cancelados). |
| **C8** | **Gerar Relatório PDF** | Admin | Consolidação de dados em documento PDF. A geração utiliza streams para suportar grandes volumes de dados sem comprometer a memória da API. |

---

## 🛠️ Detalhamento de Regras Críticas

### 🛡️ Segurança (C1)
* As senhas são processadas via `BCryptPasswordEncoder`.
* Tentativas de acesso a rotas de `Admin` por usuários de perfil `Paciente` resultam em erro **403 Forbidden**.

### 📅 Validação de Agenda (C3)
Antes de persistir uma nova consulta, a `ConsultaService` executa:
1. `validarHorarioDisponivel(medicoId, dataHora)`: Verifica se existe intersecção de horários no banco.
2. `validarAntecedencia(dataHora)`: Garante que agendamentos respeitem o tempo mínimo de antecedência configurado.

### 📄 Fluxo de Prontuário (C6)
O histórico é vinculado de forma única (`1:1`) à consulta. Uma vez registrado, o conteúdo torna-se parte do prontuário imutável do paciente, acessível apenas por médicos autorizados ou pelo próprio paciente via interface.

---

## 🔄 Fluxo Principal de Atendimento



1. **Paciente** solicita agendamento (C3).
2. **Sistema** valida disponibilidade e confirma.
3. **Médico** visualiza agenda no Dashboard (C7).
4. **Médico** realiza o atendimento e registra o Histórico (C6).
5. **Sistema** marca consulta como `FINALIZADA` e libera o PDF para o Admin.