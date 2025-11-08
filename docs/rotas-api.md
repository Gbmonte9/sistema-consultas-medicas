# 🌐 Rotas da API

| Método | Endpoint | Descrição | Acesso |
|---------|-----------|------------|---------|
| POST | `/auth/login` | Realiza autenticação de usuário | Público |
| POST | `/usuarios` | Cria novo usuário | Admin |
| GET | `/medicos` | Lista médicos cadastrados | Público |
| GET | `/pacientes` | Lista pacientes cadastrados | Admin |
| POST | `/consultas` | Agenda nova consulta | Paciente |
| GET | `/consultas` | Lista todas as consultas | Médico / Paciente |
| PUT | `/consultas/{id}/cancelar` | Cancela consulta | Médico / Paciente |
| GET | `/relatorios/consultas` | Gera relatório em PDF | Admin |
