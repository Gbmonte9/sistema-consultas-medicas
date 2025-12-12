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
import java.util.List;
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

    @Override
    @Transactional
    public HistoricoResponseDTO atualizar(Long id, HistoricoRequestDTO dto) {
        Historico historico = historicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado."));

        historico.setObservacoes(dto.getObservacoes());
        historico.setReceita(dto.getReceita());
        
        Historico historicoAtualizado = historicoRepository.save(historico);
        return toResponseDTO(historicoAtualizado);
    }
    
    @Override
    public HistoricoResponseDTO buscarPorId(Long id) {
        Historico historico = historicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado."));
        return toResponseDTO(historico);
    }

    @Override
    public HistoricoResponseDTO buscarPorConsultaId(Long consultaId) {
        Historico historico = historicoRepository.findByConsultaId(consultaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado para a consulta ID: " + consultaId));
        return toResponseDTO(historico);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Historico historico = historicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado para remoção."));

        historicoRepository.delete(historico);
    }

    @Override
    public byte[] gerarHistoricoConsultasPDF() {
     
        List<Historico> historicos = historicoRepository.findAll();
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            StringBuilder pdfContent = new StringBuilder();
            pdfContent.append("--- RELATÓRIO DE HISTÓRICOS MÉDICOS ---\n\n");
            
            if (historicos.isEmpty()) {
                pdfContent.append("Nenhum histórico encontrado.\n");
            } else {
                for (Historico h : historicos) {
                    pdfContent.append("ID Histórico: ").append(h.getId()).append("\n");
                    pdfContent.append("Consulta ID: ").append(h.getConsulta().getId()).append("\n");
                    pdfContent.append("Data: ").append(h.getDataRegistro()).append("\n");
                    pdfContent.append("Observações (Resumo): ").append(h.getObservacoes().substring(0, Math.min(h.getObservacoes().length(), 50))).append("...\n");
                    pdfContent.append("--------------------------------------------------\n");
                }
            }
            
            baos.write(pdfContent.toString().getBytes(StandardCharsets.UTF_8));
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF do histórico: " + e.getMessage());
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