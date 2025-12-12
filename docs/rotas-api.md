# Rotas (Endpoints) da API REST

A API utiliza JSON para troca de dados e segue os códigos de status HTTP padrão.

## 1. Módulo de Autenticação (Security)

| Método | URI | Descrição | Status de Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/login` | Autentica o usuário e retorna o token JWT. | 200 OK |
| `POST` | `/api/auth/register` | Cria um novo usuário (Admin/Medico/Paciente). | 201 Created |

## 2. Módulo de Consultas

| Método | URI | Descrição | Status de Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/consultas` | **Agendar nova consulta.** (Requer DTO: `medicoId`, `pacienteId`, `dataHora`). | 201 Created |
| `GET` | `/api/consultas/{id}` | Busca consulta por ID. | 200 OK |
| `PUT` | `/api/consultas/{id}/cancelar` | Cancela uma consulta. | 200 OK |
| `GET` | `/api/consultas/medico/{medicoId}` | Lista agenda de um médico. | 200 OK |

## 3. Módulo de Médicos

| Método | URI | Descrição | Status de Sucesso |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/medicos` | Cria novo registro de médico (Apenas ADMIN). | 201 Created |
| `GET` | `/api/medicos` | Lista todos os médicos. | 200 OK |
| `PUT` | `/api/medicos/{id}` | Atualiza dados do médico. | 200 OK |