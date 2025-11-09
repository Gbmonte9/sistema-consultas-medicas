# 📘 Modelagem de Classes

Este documento descreve as classes principais do sistema e seus relacionamentos.

## 🧩 Classe `Usuario`
Representa o usuário base do sistema (médico, paciente, ou administrador).

| Atributo | Tipo | Descrição |
|-----------|------|-----------|
| id | Long | Identificador único |
| nome | String | Nome completo do usuário |
| email | String | E-mail de login |
| senha | String | Senha criptografada |
| tipo | String | Define se é "medico", "paciente" ou "admin" |

---

## 🩺 Classe `Medico`
Representa os médicos cadastrados no sistema.

| Atributo | Tipo | Descrição |
|-----------|------|-----------|
| id | Long | Identificador único |
| crm | String | Código de registro profissional |
| especialidade | String | Especialidade médica |
| usuario | Usuario | Relacionamento 1:1 com a tabela de usuários |

**Relacionamentos**
- Um `Medico` está vinculado a **um** `Usuario`.
- Um `Medico` pode possuir vários `Historico` de consultas.

---

## 🧍 Classe `Paciente`
Representa os pacientes cadastrados no sistema.

| Atributo | Tipo | Descrição |
|-----------|------|-----------|
| id | Long | Identificador único |
| cpf | String | Documento pessoal |
| telefone | String | Número de contato |
| usuario | Usuario | Relacionamento 1:1 com a tabela de usuários |

**Relacionamentos**
- Um `Paciente` está vinculado a **um** `Usuario`.
- Um `Paciente` pode ter vários registros de `Historico`.

---

## 🗓️ Classe `Historico`
Representa o histórico de consultas ou atendimentos médicos.

| Atributo | Tipo | Descrição |
|-----------|------|-----------|
| id | Long | Identificador único |
| descricao | String | Descrição do atendimento |
| dataConsulta | LocalDateTime | Data e hora da consulta |
| medico | Medico | Relacionamento N:1 (vários históricos para um médico) |
| paciente | Paciente | Relacionamento N:1 (vários históricos para um paciente) |

**Relacionamentos**
- Muitos `Historico` pertencem a um `Medico`.
- Muitos `Historico` pertencem a um `Paciente`.

---

## 🔗 Relacionamentos Gerais

- `Usuario` é o núcleo do sistema (base para médicos e pacientes).
- `Medico` e `Paciente` se ligam a `Usuario` por relacionamento **OneToOne**.
- `Historico` conecta `Medico` e `Paciente` por relacionamentos **ManyToOne**.

---

## 🧠 Observações

- As classes usam anotações do **Jakarta Persistence (JPA)**.
- A comunicação com o banco será feita pelos repositórios (`Repository`).
- Controllers irão utilizar os repositórios para expor endpoints REST.
