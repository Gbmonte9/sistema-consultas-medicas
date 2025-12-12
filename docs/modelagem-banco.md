# Modelagem do Banco de Dados (PostgreSQL)

O sistema utiliza um banco de dados relacional (PostgreSQL) para persistência. O modelo foi desenhado para suportar o agendamento e o relacionamento entre Médicos e Pacientes.



## 1. Entidades e Relacionamentos

### Tabela: usuarios
* **Descrição:** Armazena dados de login e informações básicas (nome, email, etc.) para todos os tipos de acesso (Admin, Médico, Paciente).
* **Colunas Principais:** `id` (PK), `nome`, `email`, `senha`, `role` (Admin/Medico/Paciente).

### Tabela: medicos
* **Descrição:** Informações específicas dos médicos.
* **Colunas Principais:** `id` (PK), `usuario_id` (FK para `usuarios`), `crm`, `especialidade`.

### Tabela: pacientes
* **Descrição:** Informações específicas dos pacientes.
* **Colunas Principais:** `id` (PK), `usuario_id` (FK para `usuarios`), `cpf`, `data_nascimento`.

### Tabela: consultas
* **Descrição:** Registros de agendamentos.
* **Colunas Principais:** `id` (PK), `medico_id` (FK), `paciente_id` (FK), `data_hora` (Início), `data_fim` (Fim), `status` (AGENDADA, CANCELADA, REALIZADA).