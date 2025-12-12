# Arquitetura do Sistema de Consultas Médicas

O projeto segue a arquitetura **Model-View-Controller (MVC)** e é dividido em camadas de responsabilidade, baseadas no padrão Spring Boot. 

## 1. Estrutura de Camadas

### 1.1. Camada de Apresentação (Controller)
* **Responsabilidade:** Receber requisições HTTP, validar DTOs de entrada e rotear a requisição para a camada de Serviço.
* **Componentes:** `AuthController`, `ConsultaController`, `HistoricoController`, `MedicoController`, `PacienteController`, `UsuarioController`.

### 1.2. Camada de Serviço (Service)
* **Responsabilidade:** Implementar as **regras de negócio** complexas (ex: checagem de disponibilidade, validação de permissões). Coordenar transações e interagir com a camada de Persistência.
* **Componentes:** `AuthServiceImpl`, `ConsultaServiceImpl`, `HistoricoServiceImpl`, `MedicoServiceImpl`,   `PacienteServiceImpl`, `UsuarioServiceImpl`.

### 1.3. Camada de Persistência (Repository)
* **Responsabilidade:** Interagir com o Banco de Dados (CRUD). Mapeado via Spring Data JPA.
* **Componentes:** `ConsultaRepository`, `HistoricoRepository`, `MedicoRepository`, `PacienteRepository`, `UsuarioRepository`.

### 1.4. Camada de Domínio (Model)
* **Responsabilidade:** Representar as entidades de negócio e a estrutura do banco de dados.
* **Componentes:** `Consulta`, `Historico`, `Medico`, `Paciente`, `StatusConsulta`, `TipoUsuario`, `Usuario`.

## 2. Padrões de Projeto Utilizados
* **DTO (Data Transfer Object):** Utilizado para transferir dados entre as camadas e padronizar a entrada/saída da API.
* **Service Layer:** Garante a separação das regras de negócio do controle de requisições.
* **Repository Pattern:** Abstração do acesso aos dados.