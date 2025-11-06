package com.mycompany.programa1matriculacalificaciones.servicio;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;

/**
 * Servicio simplificado para "enviar" correo abriendo el cliente de correo
 * por defecto del usuario (mailto:). Esto evita dependencias externas y
 * funciona en la mayoría de entornos de escritorio.
 */
public class MailService {
    public boolean enviarCorreo(String to, String subject, String body) {
        try {
            if (!Desktop.isDesktopSupported()) return false;
            String s = URLEncoder.encode(subject == null ? "" : subject, "UTF-8");
            String b = URLEncoder.encode(body == null ? "" : body, "UTF-8");
            String uri = String.format("mailto:%s?subject=%s&body=%s", to, s, b);
            Desktop.getDesktop().mail(new URI(uri));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
