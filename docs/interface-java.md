# ☕ Interfaces Java — Sistema de Gestão de Consultas Médicas

Este documento descreve as **interfaces Java** que serão criadas no projeto, definindo os contratos de serviço entre as camadas **Controller → Service → Repository**.

---

## 🧩 Objetivo das Interfaces
As interfaces Java têm como função principal **definir o comportamento esperado** das classes de serviço sem expor detalhes de implementação.

Isso garante:
- Desacoplamento entre as camadas do sistema  
- Facilidade de manutenção e testes  
- Clareza nas responsabilidades de cada módulo  
- Suporte à injeção de dependências (Spring Boot)

---

## 🧱 Estrutura de Pastas
```
src/main/java/com/gabriel/consultasmedicas/
│
├── interfaces/
│   ├── IUsuarioService.java
│   ├── IMedicoService.java
│   ├── IPacienteService.java
│   ├── IConsultaService.java
│   └── IHistoricoService.java
```

---

## 🔧 Interfaces Planejadas

### 1. `IUsuarioService.java`
Responsável por operações genéricas de usuários (login, registro, etc.)

```java
package com.gabriel.consultasmedicas.interfaces;

import com.gabriel.consultasmedicas.model.Usuario;
import java.util.List;

public interface IUsuarioService {
    Usuario registrar(Usuario usuario);
    Usuario autenticar(String email, String senha);
    List<Usuario> listarTodos();
    void remover(Long id);
}
```

---

### 2. `IMedicoService.java`
Gerencia dados e ações específicas de médicos.

```java
package com.gabriel.consultasmedicas.interfaces;

import com.gabriel.consultasmedicas.model.Medico;
import java.util.List;

public interface IMedicoService {
    Medico salvar(Medico medico);
    List<Medico> listarTodos();
    Medico buscarPorId(Long id);
    void remover(Long id);
}
```

---

### 3. `IPacienteService.java`
Opera sobre informações de pacientes.

```java
package com.gabriel.consultasmedicas.interfaces;

import com.gabriel.consultasmedicas.model.Paciente;
import java.util.List;

public interface IPacienteService {
    Paciente salvar(Paciente paciente);
    List<Paciente> listarTodos();
    Paciente buscarPorId(Long id);
    void remover(Long id);
}
```

---

### 4. `IConsultaService.java`
Gerencia o ciclo de vida das consultas médicas.

```java
package com.gabriel.consultasmedicas.interfaces;

import com.gabriel.consultasmedicas.model.Consulta;
import java.util.List;

public interface IConsultaService {
    Consulta agendarConsulta(Consulta consulta);
    void cancelarConsulta(Long id);
    List<Consulta> listarTodas();
    List<Consulta> listarPorMedico(Long medicoId);
    List<Consulta> listarPorPaciente(Long pacienteId);
}
```

---

### 5. `IHistoricoService.java`
Responsável pelo controle do histórico de consultas médicas.

```java
package com.gabriel.consultasmedicas.interfaces;

import com.gabriel.consultasmedicas.model.Historico;
import java.util.List;

public interface IHistoricoService {
    Historico salvar(Historico historico);
    List<Historico> listarTodos();
    List<Historico> listarPorMedico(Long medicoId);
    List<Historico> listarPorPaciente(Long pacienteId);
}
```

---

## 🧠 Observações
- Todas as interfaces serão implementadas dentro da pasta `service/`  
  Exemplo: `ConsultaServiceImpl` implementa `IConsultaService`
- As controllers se comunicarão **apenas com as interfaces**, e não diretamente com as classes concretas.
- Essa abordagem segue o princípio **"Programar para interfaces, não para implementações"**

---

**Autor:** Gabriel Monte  
**Data:** 2025  
**Projeto:** Sistema de Gestão de Consultas Médicas
