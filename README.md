# README
# 🏥 Sistema de Gestão de Consultas Médicas API

O **Sistema de Gestão de Consultas Médicas** é uma **API REST completa** desenvolvida em Java com Spring Boot. O projeto é voltado para o gerenciamento centralizado de pacientes, médicos e agendamentos de consultas, atuando como o *backend* essencial para clínicas de pequeno e médio porte.

## 🚀 Objetivo

Atuar como a camada de serviços (backend) que centraliza e facilita o controle de agendamentos e informações médicas.

* Permite que **aplicativos frontend** (web, mobile, desktop) se conectem e utilizem as funcionalidades de agendamento, acompanhamento de agendas médicas e administração do sistema.
* Garante a integridade e segurança dos dados através de regras de negócio robustas e controle de acesso.

## ⚙️ Tecnologias e Arquitetura

Este projeto adota uma arquitetura baseada em microsserviços/camadas REST.

| Categoria | Tecnologia | Uso |
| :--- | :--- | :--- |
| **Linguagem** | Java 17+ | Core da aplicação. |
| **Framework** | Spring Boot | Desenvolvimento rápido de APIs REST, Injeção de Dependência, Configuração. |
| **Persistência** | Spring Data JPA / Hibernate | Mapeamento Objeto-Relacional (ORM). |
| **Banco de Dados** | PostgreSQL | Armazenamento relacional robusto. |
| **Segurança** | Spring Security | Autenticação (JWT/Basic) e Autorização por tipo de usuário (Admin, Médico, Paciente). |
| **Desenvolvimento** | Lombok, Swagger | Redução de boilerplate, Documentação interativa de Endpoints. |
| **Assíncrono** | Java Threads / Executors | Execuções em segundo plano e tarefas paralelas (ex: geração de relatórios). |
| **Frontend (Futuro)** | HTML5, CSS3, Bootstrap (ou JavaFX) | Consumo da API por uma interface de usuário dedicada. |

## 🧩 Funcionalidades da API (Endpoints)

| Módulo | Funcionalidades |
| :--- | :--- |
| **Autenticação** | Cadastro, Login (geração de Token JWT) e Controle de permissões (Role-Based Access Control). |
| **CRUD Básico** | CRUD completo de Médicos, Pacientes e Consultas. |
| **Agendamento** | Agendamento, edição, cancelamento e verificação de conflito de consultas. |
| **Histórico** | Geração e recuperação de Histórico Médico. |
| **Relatórios** | Geração assíncrona de Relatórios em PDF (usando Threads/PDFBox). |

## 📚 Estrutura da Documentação

A documentação detalhada do projeto está localizada na pasta `/docs`. **Esta é a sua principal fonte de informação sobre a API.**

| Documento | Conteúdo |
| :--- | :--- |
| **`arquitetura.md`** | Detalhes da estrutura do sistema (MVC, Camadas de Serviço, Padrões, Componentes). |
| **`modelagem-banco.md`** | O Dicionário de Dados, Diagrama Entidade-Relacionamento (DER) do PostgreSQL. |
| **`casos-de-uso.md`** | Fluxos de trabalho principais, requisitos funcionais e não funcionais. |
| **`rotas-api.md`** | Especificação completa dos Endpoints REST (Método, URI, Parâmetros, Respostas). |
| **`interface.md`** | Wireframes e planejamento da interface de consumo da API (Frontend/Web/Mobile). |
| **`threads.md`** | Estratégia e justificação para o uso de concorrência e tarefas assíncronas. |

## 👨‍💻 Autor

Gabriel Monte
[LinkedIn](link-do-seu-linkedin) | [GitHub](link-do-seu-github)