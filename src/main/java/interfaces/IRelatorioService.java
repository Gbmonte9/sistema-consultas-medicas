package interfaces;

import java.io.File;

public interface IRelatorioService {
    File gerarRelatorioConsultas();
    File gerarRelatorioPacientesAtivos();
}
