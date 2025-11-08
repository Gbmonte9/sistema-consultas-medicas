# 🧵 Uso de Threads no Sistema

O sistema utilizará **threads e execução assíncrona** para otimizar desempenho e evitar travamentos em processos longos.

---

## 🔄 Principais aplicações

1. **Envio de e-mails de confirmação**
   - O envio será feito em segundo plano após o agendamento.

2. **Atualização de consultas em tempo real**
   - Atualizações automáticas na UI sem necessidade de recarregar a página (via AJAX ou JavaFX Thread).

3. **Geração de relatórios PDF**
   - O processo de geração rodará em thread separada para não bloquear o servidor.

4. **Notificações automáticas**
   - Threads programadas (via `@Scheduled`) para avisar consultas próximas.

---

## 🧰 Tecnologias envolvidas
- `@EnableAsync` e `@Async` (Spring Boot)
- `ExecutorConfig` personalizado para controle de threads
- `ScheduledExecutorService` para tarefas agendadas
