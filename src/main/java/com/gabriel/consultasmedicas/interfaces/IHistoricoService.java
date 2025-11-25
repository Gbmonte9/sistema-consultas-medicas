package com.gabriel.consultasmedicas.interfaces;

import java.util.List;

import com.gabriel.consultasmedicas.model.Historico;

public interface IHistoricoService {
    Historico salvar(Historico historico);
    Historico atualizar(Long id, Historico historico);
    List<Historico> listarTodos();
    List<Historico> listarPorConsulta(Long consultaId);
    Historico buscarPorId(Long id);
    void remover(Long id);
	Historico cadastrarHistorico(Historico historico);
	byte[] gerarHistoricoConsultasPDF();
}
