package com.mycompany.programa1matriculacalificaciones.servicio;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.ResultadoEvaluacion;

public class ResultadoService {
    private static final String RUTA = "datos/matriculaycalificaciones/resultados.dat";
    private ArchivoService<ResultadoEvaluacion> archivo = new ArchivoService<>();
    private List<ResultadoEvaluacion> resultados;

    public ResultadoService() {
        resultados = archivo.cargarLista(RUTA);
    }

    public void registrar(ResultadoEvaluacion r) {
        resultados.add(r);
        archivo.guardarLista(resultados, RUTA);
    }

    public List<ResultadoEvaluacion> listar() {
        return new ArrayList<>(resultados);
    }
}
