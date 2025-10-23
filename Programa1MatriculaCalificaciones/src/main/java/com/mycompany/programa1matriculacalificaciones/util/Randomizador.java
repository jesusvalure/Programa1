package com.mycompany.programa1matriculacalificaciones.util;

import java.util.Collections;
import java.util.List;

public class Randomizador {
    public static <T> void mezclar(List<T> lista) {
        if (lista == null) return;
        Collections.shuffle(lista);
    }
}
