# 🔒 Segurança e Criptografia de Dados

Este documento detalha os mecanismos de proteção de dados sensíveis implementados na API, visando a conformidade com a **LGPD (Lei Geral de Proteção de Dados)**.

---

## 1. Criptografia de Dados Sensíveis (AES-128)

Diferente de informações comuns, dados como o **CPF** dos pacientes são armazenados de forma cifrada no banco de dados PostgreSQL.

### 🛠️ Especificações Técnicas:
* **Algoritmo:** AES (Advanced Encryption Standard).
* **Modo de Operação:** ECB/CBC (Cifragem Simétrica).
* **Codificação:** Base64 (para armazenamento em colunas de texto).
* **Chave de Criptografia:** Definida via variável de ambiente (`security.cpf.encrypt-secret`).

### 🔄 Fluxo de Funcionamento:
O sistema utiliza um `AttributeConverter` do JPA para tornar o processo transparente:
1. **Escrita (In):** Antes de salvar no banco, o CPF `12345678900` é transformado em algo como `v8T+jKx82m...`.
2. **Leitura (Out):** Ao buscar os dados, a API utiliza a chave secreta para decifrar o valor e retornar o CPF original para o sistema.

> ⚠️ **Atenção:** Se a chave de criptografia for alterada ou perdida, os dados já armazenados no banco tornar-se-ão ilegíveis.

---

## 2. Proteção de Credenciais (BCrypt)

As senhas dos usuários (Admin, Médicos e Pacientes) nunca são armazenadas em texto claro e **não podem ser descriptografadas**, nem mesmo pelos administradores.

* **Algoritmo:** BCrypt (Adaptive Hashing Function).
* **Mecanismo:** Utiliza a técnica de *Salt* aleatório para cada senha, impedindo ataques de Rainbow Tables.
* **Validação:** Durante o login, o sistema compara o hash gerado com o hash armazenado para validar a autenticidade.

---

## 3. Autenticação e Autorização (JWT)

A comunicação entre o cliente e a API é protegida por tokens **JWT (JSON Web Token)**.

* **Assinatura:** Cada token é assinado digitalmente usando um segredo HMAC-256.
* **Stateless:** A API não armazena sessões no servidor; todas as informações de permissão do usuário estão contidas no payload do token.
* **Expiração:** Tokens possuem tempo de vida limitado para minimizar riscos em caso de interceptação.

---

## 🛡️ Segurança nos Endpoints

| Recurso | Nível de Proteção | Técnica Utilizada |
| :--- | :--- | :--- |
| Senhas | Irreversível | BCrypt Hash |
| CPF do Paciente | Reversível (via chave) | AES-128 Symmetric |
| Tráfego de Dados | Proteção de Rota | JWT (Bearer Token) |
| Integridade | Validação | Jakarta Validation (Bean Validation) |

---

### 🔑 Configuração das Chaves
As chaves devem ser configuradas no `application.properties`:
```properties
# Deve possuir exatamente 16 caracteres para o AES-128
security.cpf.encrypt-secret=gabrielHealthS16

# Segredo complexo para o JWT
api.security.token.secret=${JWT_SECRET:minha-chave-secreta-32-chars}

