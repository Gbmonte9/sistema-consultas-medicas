# 🧱 Arquitetura do Sistema

O sistema segue o padrão **MVC (Model-View-Controller)** e será desenvolvido com **Spring Boot**.

---

## 🧩 Camadas do Sistema

- **Model:** classes de entidade que representam as tabelas do banco de dados.  
- **Repository:** interfaces que estendem `JpaRepository` e realizam operações CRUD.  
- **Service:** contém as regras de negócio e validações.  
- **Controller:** define os endpoints REST e controla as requisições.  
- **Config:** configurações gerais do sistema (segurança, async, etc).  

---

## 🔄 Fluxo de Requisição

```
Usuário → Controller → Service → Repository → Banco de Dados → Resposta
```

---

## 📁 Estrutura de Pastas (prevista)

```
src/main/java/com/gabriel/consultasmedicas/
│
├── controller/
├── model/
├── repository/
├── service/
├── config/
└── ConsultasMedicasApplication.java
```

---

## 🛠 Tecnologias
- Spring Boot  
- Spring Data JPA  
- Spring Security  
- Hibernate  
- PostgreSQL  
- Lombok  
- Swagger  
- PDFBox
