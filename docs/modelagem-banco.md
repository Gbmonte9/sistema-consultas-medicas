# 🗄️ Modelagem do Banco de Dados (PostgreSQL)

O sistema utiliza o **PostgreSQL** como banco de dados relacional. A modelagem foi projetada para garantir a integridade referencial e a segurança de dados sensíveis, utilizando identificadores únicos universais (**UUID**).



---

## 1. Dicionário de Dados e Entidades

### 1.1. Tabela: `usuarios`
Centraliza o acesso ao sistema. Cada registro define um perfil de acesso único via Role-Based Access Control (RBAC).
* **`id`**: `UUID` (Primary Key) - Gerado automaticamente.
* **`nome`**: `VARCHAR(255)` - Nome completo do usuário.
* **`email`**: `VARCHAR(255)` (Unique) - Utilizado para login.
* **`senha`**: `VARCHAR(255)` - Hash gerado via **BCrypt**.
* **`tipo_usuario`**: `ENUM` (ADMIN, MEDICO, PACIENTE).

### 1.2. Tabela: `medicos`
Extensão da entidade usuário para profissionais de saúde.
* **`id`**: `UUID` (Primary Key).
* **`crm`**: `VARCHAR(20)` (Unique).
* **`especialidade`**: `VARCHAR(100)`.
* **`usuario_id`**: `UUID` (Foreign Key -> `usuarios`).

### 1.3. Tabela: `pacientes`
Extensão da entidade usuário para clientes da clínica.
* **`id`**: `UUID` (Primary Key).
* **`cpf`**: `VARCHAR(255)` (Encrypted) - Armazena o valor criptografado via **AES-128**.
* **`telefone`**: `VARCHAR(20)`.
* **`usuario_id`**: `UUID` (Foreign Key -> `usuarios`).

### 1.4. Tabela: `consultas`
Gerencia o fluxo de agendamentos e status de atendimento.
* **`id`**: `UUID` (Primary Key).
* **`medico_id`**: `UUID` (Foreign Key -> `medicos`).
* **`paciente_id`**: `UUID` (Foreign Key -> `pacientes`).
* **`data_hora`**: `TIMESTAMP` - Início do atendimento.
* **`status`**: `VARCHAR(20)` (AGENDADA, CANCELADA, FINALIZADA).
* **`motivo`**: `TEXT`.

### 1.5. Tabela: `historicos`
Armazena a evolução clínica e registros pós-consulta.
* **`id`**: `UUID` (Primary Key).
* **`consulta_id`**: `UUID` (Foreign Key -> `consultas`).
* **`paciente_id`**: `UUID` (Foreign Key -> `pacientes`).
* **`observacoes`**: `TEXT` - Evolução clínica.
* **`receita`**: `TEXT` - Medicamentos e prescrições.
* **`data_registro`**: `TIMESTAMP`.

---

## 2. Regras de Relacionamento (Cardinalidade)

1. **Usuário -> Médico/Paciente:** Relacionamento **1:1** (Um usuário é ou um médico ou um paciente).
2. **Médico -> Consultas:** Relacionamento **1:N** (Um médico possui várias consultas agendadas).
3. **Paciente -> Consultas:** Relacionamento **1:N** (Um paciente pode ter vários agendamentos).
4. **Consulta -> Histórico:** Relacionamento **1:1** (Uma consulta gera exatamente um registro de histórico após finalizada).



---

## 3. Segurança na Camada de Dados

* **UUIDs:** Substituem os IDs sequenciais (1, 2, 3...) para evitar o vazamento de informações sobre o volume de dados e impedir ataques de enumeração de IDs na URL.
* **Encryption Converter:** O campo `pacientes.cpf` possui um conversor JPA que cifra o dado antes do `INSERT` e decifra no `SELECT`.
* **Constraints:** Implementação de `ON DELETE CASCADE` ou `RESTRICT` dependendo da regra de negócio para evitar registros órfãos.