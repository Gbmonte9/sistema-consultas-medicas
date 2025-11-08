# 🗄️ Modelagem do Banco de Dados

O banco de dados será relacional (PostgreSQL) e seguirá o modelo abaixo:

---

## 🧬 Tabelas

### `usuarios`
| Campo | Tipo | Descrição |
|--------|------|-----------|
| id | SERIAL (PK) | Identificador |
| nome | VARCHAR(100) | Nome do usuário |
| email | VARCHAR(100) | E-mail |
| senha | VARCHAR(255) | Senha criptografada |
| tipo | ENUM('ADMIN', 'MEDICO', 'PACIENTE') | Tipo de usuário |

---

### `medicos`
| Campo | Tipo | Descrição |
|--------|------|-----------|
| id | SERIAL (PK) | Identificador |
| crm | VARCHAR(50) | Número do CRM |
| especialidade | VARCHAR(100) | Especialidade médica |
| usuario_id | INT (FK) | Chave estrangeira para `usuarios` |

---

### `pacientes`
| Campo | Tipo | Descrição |
|--------|------|-----------|
| id | SERIAL (PK) | Identificador |
| cpf | VARCHAR(14) | CPF do paciente |
| telefone | VARCHAR(20) | Telefone de contato |
| usuario_id | INT (FK) | Chave estrangeira para `usuarios` |

---

### `consultas`
| Campo | Tipo | Descrição |
|--------|------|-----------|
| id | SERIAL (PK) | Identificador |
| medico_id | INT (FK) | Médico responsável |
| paciente_id | INT (FK) | Paciente agendado |
| data_hora | TIMESTAMP | Data e hora da consulta |
| status | ENUM('AGENDADA', 'CANCELADA', 'REALIZADA') | Situação atual |

---

### `historicos`
| Campo | Tipo | Descrição |
|--------|------|-----------|
| id | SERIAL (PK) | Identificador |
| consulta_id | INT (FK) | Consulta relacionada |
| observacoes | TEXT | Observações médicas |
| receita | TEXT | Receita ou tratamento |
| data_registro | TIMESTAMP | Data do registro |

---
