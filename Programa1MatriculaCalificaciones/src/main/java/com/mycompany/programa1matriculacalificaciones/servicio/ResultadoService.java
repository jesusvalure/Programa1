package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.ResultadoEvaluacion;
import java.util.*;

public class ResultadoService {

    private final ArchivoService<ResultadoEvaluacion> archivoService;
    private List<ResultadoEvaluacion> resultados;

    public ResultadoService() {
        this.archivoService = new ArchivoService<>("datos/matriculaycalificaciones/resultados.dat");
        this.resultados = archivoService.cargar();
        if (this.resultados == null) {
            this.resultados = new ArrayList<>();
        }
    }

    public void registrarResultado(ResultadoEvaluacion r) {
        resultados.add(r);
        archivoService.guardar(resultados);
    }

    public List<ResultadoEvaluacion> listarResultados() {
        return new ArrayList<>(resultados);
    }

    public List<ResultadoEvaluacion> resultadosPorEstudiante(String nombre) {
        List<ResultadoEvaluacion> lista = new ArrayList<>();
        for (ResultadoEvaluacion r : resultados) {
            if (r.getEstudiante().equalsIgnoreCase(nombre)) lista.add(r);
        }
        return lista;
    }

    
}
