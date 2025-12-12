# 🧩 Modelagem de Classes — Sistema de Gestão de Consultas Médicas

## 📘 Visão Geral
A modelagem de classes define as entidades principais do sistema e seus relacionamentos, que serão refletidos nas tabelas do banco de dados via JPA (Java Persistence API).

---

## 🧱 Estrutura de Pastas
```
src/
└── main/
    └── java/
        └── com/sistemamedico/
            └── model/
                ├── Usuario.java
                ├── Medico.java
                ├── Paciente.java
                ├── Consulta.java
                └── Historico.java
```

---

## 🧍‍♂️ Classe `Usuario`
Representa os usuários do sistema, podendo ser **Administrador**, **Médico** ou **Paciente**.

**Atributos:**
- `id`: Identificador único.
- `nome`: Nome completo do usuário.
- `email`: E-mail de acesso (único).
- `senha`: Senha criptografada.
- `tipo`: Enum (ADMIN, MEDICO, PACIENTE).

**Relacionamentos:**
- `@OneToOne` com `Medico` ou `Paciente` (dependendo do tipo).

**Métodos úteis:**
- `isAdmin()`, `isMedico()`, `isPaciente()` para verificação de papel.

---

## 🩺 Classe `Medico`
Representa o médico vinculado a um usuário do tipo **MEDICO**.

**Atributos:**
- `id`: Identificador único.
- `crm`: Código de registro profissional.
- `especialidade`: Especialidade médica.
- `usuario`: Associação com a classe `Usuario`.

**Relacionamentos:**
- `@OneToOne` com `Usuario`.
- `@OneToMany` com `Consulta` (um médico pode ter várias consultas).
- `@OneToMany` com `Historico` (um médico pode ter vários históricos).

---

## 🧑‍⚕️ Classe `Paciente`
Representa o paciente vinculado a um usuário do tipo **PACIENTE**.

**Atributos:**
- `id`: Identificador único.
- `cpf`: Documento de identificação.
- `dataNascimento`: Data de nascimento.
- `telefone`: Contato.
- `endereco`: Endereço completo.
- `usuario`: Associação com `Usuario`.

**Relacionamentos:**
- `@OneToOne` com `Usuario`.
- `@OneToMany` com `Consulta` (um paciente pode ter várias consultas).
- `@OneToMany` com `Historico` (um paciente pode ter vários históricos).

---

## 📅 Classe `Consulta`
Registra as consultas médicas agendadas.

**Atributos:**
- `id`: Identificador único.
- `dataConsulta`: Data e hora da consulta (`LocalDateTime`).
- `status`: Enum (`AGENDADA`, `REALIZADA`, `CANCELADA`).
- `motivo`: String (motivo da consulta).
- `medico`: Médico responsável (`@ManyToOne`).
- `paciente`: Paciente atendido (`@ManyToOne`).

**Relacionamentos:**
- `@ManyToOne` com `Medico`.
- `@ManyToOne` com `Paciente`.
- Uma `Consulta` pode ter **um** `Historico` associado (1:1) após ser realizada.

---

## 🗓️ Classe `Historico`
Registra o histórico clínico e observações resultantes de uma consulta.

**Atributos:**
- `id`: Identificador único.
- `observacoes`: Texto das observações/diagnóstico.
- `receita`: Texto da prescrição, se houver.
- `dataRegistro`: Data e hora do registro (`LocalDateTime`).
- `consulta`: Associação com a `Consulta` (`@OneToOne`).
- `medico`: Referência ao `Medico` que registrou (`@ManyToOne`).
- `paciente`: Referência ao `Paciente` relacionado (`@ManyToOne`).

**Relacionamentos:**
- `Historico` ↔ `Consulta`: 1:1 (uma consulta pode gerar um histórico).
- `Historico` ↔ `Medico`: N:1 (vários históricos para um médico).
- `Historico` ↔ `Paciente`: N:1 (vários históricos para um paciente).

---

## 🔗 Relacionamentos Resumidos

| Entidade | Relacionamento | Tipo |
|-----------|----------------|------|
| `Usuario` ↔ `Medico` | 1:1 | `@OneToOne` |
| `Usuario` ↔ `Paciente` | 1:1 | `@OneToOne` |
| `Medico` ↔ `Consulta` | 1:N | `@OneToMany` / `@ManyToOne` |
| `Paciente` ↔ `Consulta` | 1:N | `@OneToMany` / `@ManyToOne` |
| `Consulta` ↔ `Historico` | 1:1 | `@OneToOne` |
| `Medico` ↔ `Historico` | 1:N | `@OneToMany` / `@ManyToOne` |
| `Paciente` ↔ `Historico` | 1:N | `@OneToMany` / `@ManyToOne` |

---

## 🧩 Diagrama Simplificado

```
Usuario (id, nome, email, senha, tipo)
 │
 ├── Medico (id, crm, especialidade, usuario_id)
 │     └── Consulta (id, dataConsulta, status, motivo, medico_id, paciente_id)
 │           └── Historico (id, observacoes, receita, dataRegistro, consulta_id)
 │
 └── Paciente (id, cpf, dataNascimento, telefone, endereco, usuario_id)
       └── Consulta (...)
             └── Historico (...)
```

---

## 🧠 Observações
- As classes estão anotadas com `@Entity` e `@Table`.
- O `@GeneratedValue` define IDs automáticos.
- Todos os relacionamentos são gerenciados pelo **Hibernate (JPA)**.
- A modelagem é compatível com bancos como **MySQL**, **PostgreSQL** e **MariaDB**.
