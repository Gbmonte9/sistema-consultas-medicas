# 🏛️ Arquitetura do Sistema

O projeto adota uma **Arquitetura em Camadas (Layered Architecture)**, seguindo os princípios do Spring Boot para garantir alta coesão, baixo acoplamento e escalabilidade.



---

## 1. Fluxo de Requisição e Segurança

Diferente de sistemas MVC tradicionais, esta API implementa uma camada de segurança interceptora que protege os recursos antes mesmo de chegarem aos controllers.

1. **Camada de Filtro (Security Filter):** Intercepta o cabeçalho `Authorization`, valida o Token JWT e estabelece o contexto de segurança.
2. **Controller:** Recebe o JSON, realiza a validação sintática via `Jakarta Validation` e delega para o serviço.
3. **Service:** Executa a lógica de negócio, orquestra as transações e as regras de segurança (RBAC).
4. **Repository:** Persiste ou recupera dados do PostgreSQL via JPA/Hibernate.

---

## 2. Detalhamento das Camadas

### 2.1. Camada de Apresentação (Controllers)
* **Responsabilidade:** Expor endpoints RESTful e gerenciar o protocolo HTTP (Status Codes, Headers).
* **Destaque:** Utiliza DTOs para evitar o **Mass Assignment**, impedindo que usuários externos alterem campos sensíveis diretamente nas entidades.

### 2.2. Camada de Negócio (Services)
* **Responsabilidade:** É o coração da aplicação. Aqui residem as regras de agendamento, cálculos de estatísticas de dashboard e a lógica de geração de PDF.
* **Padrão:** Interface (`IService`) e Implementação (`ServiceImpl`), facilitando a inversão de controle e testes unitários.

### 2.3. Camada de Acesso a Dados (Repositories)
* **Responsabilidade:** Abstração total do SQL. Utiliza o poder do Spring Data JPA para consultas dinâmicas e gerenciamento do ciclo de vida das entidades.

### 2.4. Camada de Segurança (Security & Crypto)
* **JWT Service:** Gerenciamento de geração, expiração e assinatura de tokens.
* **Converter Layer:** Implementação de `AttributeConverter` para que a criptografia **AES-128** do CPF ocorra de forma transparente entre o Model e a Tabela.

---

## 3. Estrutura de Transferência de Dados (DTOs)

Para garantir a segurança e a performance da rede, o sistema utiliza diferentes tipos de DTOs:

* **RequestDTO:** Campos necessários para criação/atualização (ex: `LoginRequestDTO`).
* **ResponseDTO:** Dados higienizados para o cliente (ex: `ConsultaResponseDTO` que não expõe senhas ou dados internos).

---

## 4. Tecnologias de Suporte
* **Lombok:** Automatização de boilerplate code (Getters, Setters, Constructors).
* **Java 21 Records:** Utilizados em alguns DTOs pela imutabilidade e performance.
* **Jakarta Validation:** Regras de `@NotNull`, `@Email` e `@CPF` aplicadas diretamente na entrada.