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
import java.util.Optional;

// Simulação de bibliotecas de PDF (na vida real, usaria iText ou JasperReports)
// import com.itextpdf.text.Document; 
// import com.itextpdf.text.Paragraph;
// import com.itextpdf.text.pdf.PdfWriter; 
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

    // -----------------------------------------------------------------------------------
    // MÉTODOS DE REGISTRO E ATUALIZAÇÃO
    // -----------------------------------------------------------------------------------

    @Override
    @Transactional
    public HistoricoResponseDTO registrarHistorico(HistoricoRequestDTO dto) {
        // 1. Regra de Negócio: A consulta deve existir e estar no status 'REALIZADA'
        Consulta consulta = consultaRepository.findById(dto.getConsultaId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada para registrar histórico."));
        
        // Verifica se a consulta já tem um histórico (Histórico é 1:1 com Consulta)
        if (historicoRepository.findByConsultaId(dto.getConsultaId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta consulta já possui um histórico registrado.");
        }

        // Validação adicional: A consulta deve ter o status REALIZADA (opcional, mas boa prática)
        // if (consulta.getStatus() != StatusConsulta.REALIZADA) {
        //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O histórico só pode ser registrado em consultas realizadas.");
        // }
        
        // 2. Mapeamento DTO para Entidade
        Historico novoHistorico = new Historico();
        novoHistorico.setConsulta(consulta);
        novoHistorico.setObservacoes(dto.getObservacoes());
        novoHistorico.setReceita(dto.getReceita());
        novoHistorico.setDataRegistro(LocalDateTime.now());
        
        // 3. Salva o histórico
        Historico historicoSalvo = historicoRepository.save(novoHistorico);

        // 4. (Opcional) Atualiza o status da consulta se necessário
        if (consulta.getStatus() != StatusConsulta.REALIZADA) {
            consulta.setStatus(StatusConsulta.REALIZADA);
            consultaRepository.save(consulta);
        }

        // 5. Retorna o DTO de Resposta
        return toResponseDTO(historicoSalvo);
    }

    @Override
    @Transactional
    public HistoricoResponseDTO atualizar(Long id, HistoricoRequestDTO dto) {
        Historico historico = historicoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado."));

        // Atualiza apenas os campos permitidos
        historico.setObservacoes(dto.getObservacoes());
        historico.setReceita(dto.getReceita());
        
        Historico historicoAtualizado = historicoRepository.save(historico);
        return toResponseDTO(historicoAtualizado);
    }

    // -----------------------------------------------------------------------------------
    // MÉTODOS DE BUSCA E REMOÇÃO
    // -----------------------------------------------------------------------------------
    
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
        if (!historicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado para remoção.");
        }
        historicoRepository.deleteById(id);
    }

    // -----------------------------------------------------------------------------------
    // MÉTODO DE NEGÓCIO: GERAÇÃO DE PDF
    // -----------------------------------------------------------------------------------

    @Override
    public byte[] gerarHistoricoConsultasPDF() {
        // No contexto real, este método usaria uma biblioteca de geração de PDF (iText, etc.)
        // para criar um documento formatado com os dados.
        
        List<Historico> historicos = historicoRepository.findAll();
        
        // Simulação da geração de PDF (retorna bytes de um texto simples para demonstração)
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
            // Em um ambiente de produção, logar o erro
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF do histórico: " + e.getMessage());
        }
    }


    // -----------------------------------------------------------------------------------
    // MÉTODOS AUXILIARES: DTO Mappers
    // -----------------------------------------------------------------------------------

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