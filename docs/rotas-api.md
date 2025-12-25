# 📑 Especificação de Rotas (Endpoints)

A API utiliza **JSON** para troca de dados e segue os padrões REST. Quase todas as rotas exigem o Header `Authorization: Bearer <TOKEN_JWT>`.

---

## 🔐 1. Módulo de Autenticação (`/api/auth`)

| Método | URI | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/login` | Autentica usuário e retorna `LoginResponseDTO` com o Token JWT. | 200 OK |

---

## 👥 2. Módulo de Usuários (`/api/usuarios`)

| Método | URI | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/registrar` | Cria um novo usuário (Admin, Médico ou Paciente). | 201 Created |
| `GET` | `/` | Lista todos os usuários cadastrados. | 200 OK |
| `GET` | `/{id}` | Busca um usuário específico por UUID. | 200 OK |
| `GET` | `/tipo/{tipo}` | Filtra usuários por categoria (ex: `ADMIN`, `MEDICO`). | 200 OK |
| `PUT` | `/{id}` | Atualiza perfil de usuário (Salvar Alterações). | 200 OK |
| `DELETE` | `/{id}` | Remove um usuário do sistema. | 204 No Content |

---

## 📅 3. Módulo de Consultas (`/api/consultas`)

| Método | URI | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/` | Agendamento padrão de consulta. | 201 Created |
| `POST` | `/agendar-e-finalizar` | **Fluxo Integrado:** Cria consulta e já registra o histórico médico. | 201 Created |
| `GET` | `/medico/{id}/hoje` | **Dashboard:** Lista a agenda do dia (Hoje) de um médico. | 200 OK |
| `GET` | `/medico/{id}/estatisticas` | **Dashboard:** Contadores (Hoje, Atendidos, Cancelados). | 200 OK |
| `GET` | `/medico/{id}/pacientes` | Lista pacientes únicos atendidos por um médico. | 200 OK |
| `PUT` | `/{id}/finalizar` | Altera o status da consulta para FINALIZADA. | 204 No Content |
| `PUT` | `/{id}/cancelar` | Altera o status da consulta para CANCELADA. | 204 No Content |

---

## 📝 4. Módulo de Históricos e Prontuários (`/api/historicos`)

| Método | URI | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/` | Registra novo histórico/atendimento médico. | 201 Created |
| `GET` | `/paciente/{id}` | **Prontuário:** Lista todos os históricos de um paciente. | 200 OK |
| `GET` | `/consulta/{id}` | Busca o histórico vinculado a uma consulta específica. | 200 OK |
| `GET` | `/pdf/consultas` | **Relatórios:** Gera e baixa o PDF do histórico geral. | 200 OK |
| `PUT` | `/{id}` | Atualiza observações ou receitas de um histórico. | 200 OK |

---

## 🩺 5. Médicos e Pacientes (`/api/medicos` & `/api/pacientes`)

| Método | URI | Descrição | Status Sucesso |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/medicos/especialidade?nome=X` | Filtra médicos por nome da especialidade. | 200 OK |
| `POST` | `/api/pacientes` | Cadastro público de pacientes (CPF criptografado via AES). | 201 Created |
| `GET` | `/api/pacientes` | Lista todos os pacientes (CPF descriptografado para o sistema). | 200 OK |

---

### 💡 Notas de Integração
* **IDs:** Todos os identificadores `{id}` utilizam o padrão **UUID** (ex: `550e8400-e29b-41d4-a716-446655440000`).
* **Datas:** Devem ser enviadas no formato ISO-8601 (`yyyy-MM-ddTHH:mm:ss`).
* **Segurança:** Rotas de exclusão (`DELETE`) e listagem total de usuários são restritas a usuários com Role `ADMIN`.