# 🧩 Modelagem de Classes — Sistema de Gestão de Consultas Médicas

A modelagem de classes deste sistema utiliza o **JPA (Java Persistence API)** para mapear objetos Java em tabelas do **PostgreSQL**.  
A estrutura foi desenhada para suportar uma hierarquia clara de usuários e um fluxo de atendimento totalmente rastreável.

---

## 🧱 Estrutura de Pacotes (Back-end)

```text
src/main/java/com/gabriel/consultasmedicas/
├── model/                # Entidades JPA (Mapeamento do Banco)
├── dto/                  # Objetos de Transferência (Request/Response)
├── repository/           # Interfaces Spring Data JPA
└── controller/           # Camada de Exposição da API
```

---

## 🧍‍♂️ Entidade Usuario

Classe central para autenticação e controle de acesso (**Spring Security**).

### 🔑 Atributos Principais
- `id`: UUID (Identificador Universal)
- `nome`
- `email` (único)
- `senha` (Hash via **BCrypt**)
- `tipoUsuario`: Enum (`ADMIN`, `MEDICO`, `PACIENTE`)

### 🏷️ Anotações Chave
- `@Entity`
- `@Table(name = "usuarios")`

---

## 🩺 Entidade Medico

Extensão do usuário que armazena informações profissionais.

### 🔑 Atributos Principais
- `crm`: Registro profissional único
- `especialidade`: Área de atuação
- `usuario`: Relacionamento `@OneToOne` com `Usuario`

### 🔗 Relacionamentos
- `@OneToMany`: Lista de `Consulta` (agendamentos vinculados ao médico)

---

## 🧑‍⚕️ Entidade Paciente

Extensão do usuário com foco no prontuário e identificação clínica.

### 🔑 Atributos Principais
- `cpf`: Campo protegido via  
  `@Convert(converter = CpfCryptoConverter.class)`
- `dataNascimento`
- `telefone`
- `usuario`: Relacionamento `@OneToOne` com `Usuario`

### 🔗 Relacionamentos
- `@OneToMany`: Histórico de consultas e prontuários

---

## 📅 Entidade Consulta

Gerencia o estado e a temporalidade do atendimento.

### 🔑 Atributos Principais
- `dataHora`: `LocalDateTime`
- `status`: Enum (`AGENDADA`, `FINALIZADA`, `CANCELADA`)
- `motivo`: Descrição breve da queixa

### 🔗 Relacionamentos
- `@ManyToOne`: Referência ao `Medico`
- `@ManyToOne`: Referência ao `Paciente`
- `@OneToOne`: Associação opcional com `Historico` após finalização

---

## 🗓️ Entidade Historico

Registro clínico gerado após a conclusão da consulta (**Prontuário Digital**).

### 🔑 Atributos Principais
- `observacoes`: Diagnóstico e descrição do atendimento
- `receita`: Prescrição médica
- `dataRegistro`: Timestamp automático

### 🔗 Relacionamentos
- `@OneToOne`: Referência obrigatória para a `Consulta`
- `@ManyToOne`: Referência ao `Paciente`

---

## 🔗 Resumo de Mapeamento JPA

| De       | Para       | Tipo JPA | Propriedade no Código |
|--------|-----------|---------|-----------------------|
| Usuario | Medico    | 1:1     | `mappedBy = "usuario"` |
| Medico  | Consulta  | 1:N     | `List<Consulta> consultas` |
| Paciente| Consulta  | 1:N     | `List<Consulta> consultas` |
| Consulta| Historico | 1:1     | `@OneToOne(mappedBy = "consulta")` |

---

## 🧠 Lógica de Persistência Implementada

- **Identificadores UUID:** Evitam previsibilidade de IDs, aumentando a segurança.
- **Cascateamento (`CascadeType.ALL`):** Persistência e remoção propagadas entre entidades relacionadas.
- **Fetch Lazy (`FetchType.LAZY`):** Otimiza desempenho e reduz carga desnecessária no banco.
- **Criptografia Automática:**  
  O campo `cpf` é criptografado automaticamente antes da persistência via `AttributeConverter` customizado.

---