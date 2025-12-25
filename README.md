# 🏥 API de Gestão de Consultas Médicas

Esta é uma **API RESTful profissional** desenvolvida com **Java 21** e **Spring Boot 3**, projetada para gerenciar o ecossistema de uma clínica médica. A API lida com autenticação, perfis de usuários, agendamentos inteligentes e geração de prontuários médicos.

---

## 💻 Projeto Full Stack
Este repositório contém o **Back-end** do sistema. Para visualizar ou rodar a interface de usuário, acesse o repositório do Front-end:
👉 **[Front-end em React - Sistema de Consultas](https://github.com/Gbmonte9/react-medica-consulta)**



---

## 🚀 Diferenciais de Implementação

* **Java 21 LTS:** Uso de recursos modernos da linguagem para alta performance.
* **Privacidade (AES-128):** Criptografia de dados sensíveis (CPF) na persistência, garantindo conformidade com a LGPD.
* **Segurança Stateless:** Autenticação via **JWT (JSON Web Token)** com controle de acesso por perfis (RBAC).
* **Documentação:** Integração com **Swagger/OpenAPI** para visualização e testes dos endpoints.

---

## ⚙️ Stack Tecnológica

| Categoria | Tecnologia | Uso |
| :--- | :--- | :--- |
| **Linguagem** | Java 21 | Versão LTS (Virtual Threads). |
| **Framework** | Spring Boot 3.2.2 | Base do desenvolvimento. |
| **Segurança** | Spring Security & JWT | Filtros de segurança e tokens. |
| **Banco de Dados** | PostgreSQL | Armazenamento relacional. |
| **Documentação** | Swagger (OpenAPI) | Interface UI para testes. |

---

## 🛠️ Como Executar (Eclipse IDE)

1. **Banco de Dados:** Crie o banco `sistema-consultas-medicas` no PostgreSQL.
2. **Importação:** No Eclipse, importe como *Existing Maven Project*.
3. **Properties:** Configure `application.properties` com seu usuário, senha e as chaves:
   - `security.cpf.encrypt-secret=gabrielHealthS16` (16 caracteres)
   - `api.security.token.secret=seu-segredo-jwt`
4. **Execução:** Rode a classe `ConsultasMedicasApplication.java` como *Spring Boot App*.

---

## 🧩 Endpoints da API

### 🔐 Autenticação e Usuários (`/api/auth` & `/api/usuarios`)
* `POST /api/auth/login` - Realiza login e retorna o Token JWT.
* `POST /api/usuarios/registrar` - Cadastro de novos usuários no sistema.
* `GET /api/usuarios` - Listagem de todos os usuários.
* `GET /api/usuarios/tipo/{tipo}` - Filtra usuários (ADMIN, MEDICO, PACIENTE).
* `PUT /api/usuarios/{id}` - Atualiza perfil de usuário.

### 🩺 Pacientes e Médicos (`/api/pacientes` & `/api/medicos`)
* `POST /api/pacientes` - Cadastro de pacientes (CPF criptografado).
* `GET /api/pacientes` - Lista pacientes (Sistema descriptografa o CPF).
* `GET /api/medicos` - Lista todos os médicos e especialidades.

### 📅 Consultas e Dashboards (`/api/consultas`)
* `GET /api/consultas/medico/{id}/hoje` - Agenda diária do médico.
* `GET /api/consultas/medico/{id}/estatisticas` - Dados para dashboard (contadores).
* `POST /api/consultas/agendar-e-finalizar` - Fluxo completo (Agendamento + Histórico).

### 📝 Históricos e Relatórios (`/api/historicos`)
* `GET /api/historicos/paciente/{id}` - Prontuário completo do paciente.
* `GET /api/historicos/pdf/consultas` - Download de relatório geral em **PDF**.

---

## 👨‍💻 Autor

**Gabriel Monte** Desenvolvedor Back-end Java  

🔗 [LinkedIn](https://www.linkedin.com/in/gabriel-rodrigues-mt/)  
💻 [GitHub](https://github.com/Gbmonte9)