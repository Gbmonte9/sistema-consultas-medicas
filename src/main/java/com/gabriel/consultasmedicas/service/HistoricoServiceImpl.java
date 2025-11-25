package com.gabriel.consultasmedicas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.gabriel.consultasmedicas.interfaces.IHistoricoService;
import com.gabriel.consultasmedicas.model.Historico;
import com.gabriel.consultasmedicas.repository.HistoricoRepository;

@Service
public class HistoricoServiceImpl implements IHistoricoService {

    private final HistoricoRepository historicoRepository;

    public HistoricoServiceImpl(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    @Override
    public Historico cadastrarHistorico(Historico historico) {
        return historicoRepository.save(historico);
    }

    @Override
    public List<Historico> listarTodos() {
        return historicoRepository.findAll();
    }

    @Override
    public Historico buscarPorId(Long id) {
        return historicoRepository.findById(id).orElse(null);
    }

    @Override
    public Historico salvar(Historico historico) {
        return historicoRepository.save(historico);
    }

    @Override
    public void remover(Long id) {
        historicoRepository.deleteById(id);
    }

	@Override
	public Historico atualizar(Long id, Historico historico) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Historico> listarPorConsulta(Long consultaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] gerarHistoricoConsultasPDF() {
		// TODO Auto-generated method stub
		return null;
	}
}
