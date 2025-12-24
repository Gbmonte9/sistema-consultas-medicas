package com.gabriel.consultasmedicas.service;

import com.gabriel.consultasmedicas.dto.HistoricoRequestDTO;
import com.gabriel.consultasmedicas.dto.HistoricoResponseDTO;
import com.gabriel.consultasmedicas.interfaces.IHistoricoService;
import com.gabriel.consultasmedicas.model.Consulta;
import com.gabriel.consultasmedicas.model.Historico;
import com.gabriel.consultasmedicas.model.StatusConsulta;
import com.gabriel.consultasmedicas.repository.ConsultaRepository;
import com.gabriel.consultasmedicas.repository.HistoricoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID; 
import java.util.stream.Collectors;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Service
public class HistoricoServiceImpl implements IHistoricoService {

    private final HistoricoRepository historicoRepository;
    private final ConsultaRepository consultaRepository;

    public HistoricoServiceImpl(HistoricoRepository historicoRepository, ConsultaRepository consultaRepository) {
        this.historicoRepository = historicoRepository;
        this.consultaRepository = consultaRepository;
    }

    @Override
    @Transactional
    public HistoricoResponseDTO registrarHistorico(HistoricoRequestDTO dto) {
        Consulta consulta = consultaRepository.findById(dto.getConsultaId()) 
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada para registrar histórico."));
        
        if (historicoRepository.findByConsultaId(dto.getConsultaId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta consulta já possui um histórico registrado.");
        }
        
        Historico novoHistorico = new Historico();
        novoHistorico.setConsulta(consulta);
        novoHistorico.setObservacoes(dto.getObservacoes());
        novoHistorico.setReceita(dto.getReceita());
        novoHistorico.setDataRegistro(LocalDateTime.now());
        
        Historico historicoSalvo = historicoRepository.save(novoHistorico);

        if (consulta.getStatus() != StatusConsulta.REALIZADA) {
            consulta.setStatus(StatusConsulta.REALIZADA);
            consultaRepository.save(consulta);
        }

        return toResponseDTO(historicoSalvo);
    }

    /**
     * NOVO: Busca todo o prontuário de um paciente.
     * Essencial para o botão "Ver Prontuário" no React.
     */
    @Override
    public List<HistoricoResponseDTO> buscarPorPacienteId(UUID pacienteId) {
        List<Historico> historicos = historicoRepository.findByPacienteId(pacienteId);
        return historicos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HistoricoResponseDTO atualizar(UUID id, HistoricoRequestDTO dto) {
        Historico historico = historicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado."));

        historico.setObservacoes(dto.getObservacoes());
        historico.setReceita(dto.getReceita());
        
        Historico historicoAtualizado = historicoRepository.save(historico);
        return toResponseDTO(historicoAtualizado);
    }
    
    @Override
    public HistoricoResponseDTO buscarPorId(UUID id) {
        Historico historico = historicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado."));
        return toResponseDTO(historico);
    }

    @Override
    public HistoricoResponseDTO buscarPorConsultaId(UUID consultaId) {
        Historico historico = historicoRepository.findByConsultaId(consultaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado para a consulta ID: " + consultaId));
        return toResponseDTO(historico);
    }

    @Override
    @Transactional
    public void remover(UUID id) {
        Historico historico = historicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado para remoção."));

        historicoRepository.delete(historico);
    }

    @Override
    public byte[] gerarHistoricoConsultasPDF() {
        List<Historico> historicos = historicoRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            StringBuilder pdfContent = new StringBuilder();
            pdfContent.append("==================================================\n");
            pdfContent.append("       RELATÓRIO DE HISTÓRICOS MÉDICOS          \n");
            pdfContent.append("==================================================\n\n");
            
            if (historicos.isEmpty()) {
                pdfContent.append("Nenhum histórico encontrado.\n");
            } else {
                for (Historico h : historicos) {
                    pdfContent.append("DATA: ").append(h.getDataRegistro().format(formatter)).append("\n");
                    pdfContent.append("PACIENTE: ").append(h.getConsulta().getPaciente().getUsuario().getNome()).append("\n");
                    pdfContent.append("MÉDICO: ").append(h.getConsulta().getMedico().getUsuario().getNome()).append("\n");
                    pdfContent.append("OBSERVAÇÕES: ").append(h.getObservacoes()).append("\n");
                    pdfContent.append("RECEITA: ").append(h.getReceita()).append("\n");
                    pdfContent.append("--------------------------------------------------\n");
                }
            }
            
            baos.write(pdfContent.toString().getBytes(StandardCharsets.UTF_8));
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar o relatório: " + e.getMessage());
        }
    }

    private HistoricoResponseDTO toResponseDTO(Historico historico) {
        return HistoricoResponseDTO.builder()
            .id(historico.getId()) 
            .consultaId(historico.getConsulta().getId())
            .observacoes(historico.getObservacoes())
            .receita(historico.getReceita())
            .dataRegistro(historico.getDataRegistro())
            .build();
    }
}