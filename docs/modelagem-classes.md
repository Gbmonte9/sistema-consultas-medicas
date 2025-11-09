🧩 Modelagem de Classes — Sistema de Gestão de Consultas Médicas
🎯 Objetivo
A modelagem de classes define a estrutura principal do sistema em Java, organizando os dados, comportamentos e relacionamentos entre as entidades do domínio médico.

🏗️ Visão Geral
O sistema será estruturado em camadas:


Model (Domínio): contém as classes de entidade, responsáveis por representar os dados do sistema.


Repository / DAO: realiza o acesso e manipulação dos dados no banco.


Service: contém as regras de negócio.


Controller: realiza a comunicação entre a interface e o sistema.


View (futura interface): mostrará as informações para o usuário (pode ser web ou JavaFX/Swing).



🔹 Principais Classes do Domínio
🧍‍♂️ Paciente
Representa o paciente do sistema.
AtributoTipoDescriçãoidLongIdentificador úniconomeStringNome completo do pacientecpfStringCPF do pacientetelefoneStringContato principalemailStringE-mail para comunicaçãodataNascimentoLocalDateData de nascimentoenderecoStringEndereço completo
Relacionamentos:


Um paciente pode ter várias consultas.



👨‍⚕️ Medico
Representa o médico cadastrado no sistema.
AtributoTipoDescriçãoidLongIdentificador úniconomeStringNome completocrmStringRegistro profissionalespecialidadeStringÁrea de atuaçãotelefoneStringContatoemailStringE-mail profissional
Relacionamentos:


Um médico pode ter várias consultas agendadas.



📅 Consulta
Representa uma consulta médica agendada.
AtributoTipoDescriçãoidLongIdentificador únicodataConsultaLocalDateTimeData e hora da consultastatusString(Agendada, Realizada, Cancelada)observacoesStringObservações gerais
Relacionamentos:


Uma consulta pertence a um paciente e a um médico.


Pode ter um prontuário vinculado.



📋 Prontuario
Registra o histórico e observações médicas do paciente.
AtributoTipoDescriçãoidLongIdentificador únicodiagnosticoStringDiagnóstico médicoreceitaStringPrescrição e recomendaçõesdataRegistroLocalDateTimeData do registro
Relacionamentos:


Um prontuário pertence a uma consulta.



🏥 Administrador
Responsável por gerenciar usuários, médicos e pacientes no sistema.
AtributoTipoDescriçãoidLongIdentificador úniconomeStringNome completousuarioStringLogin do sistemasenhaStringSenha criptografada
Relacionamentos:


Pode realizar operações de cadastro e exclusão de médicos/pacientes.



💊 (Opcional) Prescricao
Se o sistema evoluir, essa classe pode ser separada do prontuário.
AtributoTipoDescriçãoidLongIdentificador únicomedicamentoStringNome do medicamentodosagemStringQuantidade e frequênciaduracaoTratamentoStringDuração do tratamentoobservacoesStringInstruções médicas

🔗 Relacionamentos Principais
ClasseTipo de RelacionamentoClassePaciente1..*ConsultaMedico1..*ConsultaConsulta1..1ProntuarioAdministrador1..*Medico / Paciente (gerenciamento)

🧱 Pacotes Sugeridos
src/
 └── main/
     └── java/
         ├── br.com.clinica.model/        → Entidades (Paciente, Medico, Consulta, etc.)
         ├── br.com.clinica.repository/   → Acesso a dados
         ├── br.com.clinica.service/      → Regras de negócio
         ├── br.com.clinica.controller/   → Lógica de controle
         └── br.com.clinica.view/         → Interface (JavaFX ou Web)


🗂️ Próximos passos


Criar o diagrama UML (classe e relacionamento).


Iniciar as classes de modelo (Paciente, Medico, Consulta, Prontuario).


Configurar o banco de dados (posteriormente, via Hibernate ou JDBC). 

