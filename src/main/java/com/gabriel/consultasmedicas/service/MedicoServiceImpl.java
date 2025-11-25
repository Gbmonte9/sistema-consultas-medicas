package com.gabriel.consultasmedicas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.gabriel.consultasmedicas.interfaces.IMedicoService;
import com.gabriel.consultasmedicas.model.Medico;
import com.gabriel.consultasmedicas.repository.MedicoRepository;

@Service
public class MedicoServiceImpl implements IMedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Medico cadastrarMedico(Medico medico) {
        return medicoRepository.save(medico);
    }

    @Override
    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    @Override
    public Medico buscarPorId(Long id) {
        return medicoRepository.findById(id).orElse(null);
    }

    @Override
    public Medico salvar(Medico medico) {
        return medicoRepository.save(medico);
    }

    @Override
    public void remover(Long id) {
        medicoRepository.deleteById(id);
    }
}
