package com.gabriel.consultasmedicas.controller;

import com.gabriel.consultasmedicas.interfaces.IHistoricoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/historico") // Nova rota base: /historico
public class HistoricoController {
    
    private final IHistoricoService historicoService; // Novo Service

    public HistoricoController(IHistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    // GET /historico/consultas -> Gera relatório/histórico em PDF (Admin)
    // Se for um histórico específico de paciente/médico, adicione um PathVariable: @GetMapping("/paciente/{id}")
    @GetMapping("/consultas") 
    public ResponseEntity<byte[]> gerarHistoricoConsultasPDF() {
        
        // 1. O Service busca os dados e usa PDFBox para gerar o PDF.
        byte[] pdfBytes = historicoService.gerarHistoricoConsultasPDF();

        // 2. Configura os cabeçalhos para que o navegador baixe o arquivo como PDF.
        return ResponseEntity.ok()
                // Define o nome do arquivo que será baixado
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"historico_consultas.pdf\"")
                // Define o tipo de conteúdo como PDF
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}