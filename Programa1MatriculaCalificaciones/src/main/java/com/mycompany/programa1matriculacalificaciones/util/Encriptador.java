package com.mycompany.programa1matriculacalificaciones.util;

import java.security.MessageDigest;

public class Encriptador {
    public static String encriptar(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return texto;
        }
    }
}
