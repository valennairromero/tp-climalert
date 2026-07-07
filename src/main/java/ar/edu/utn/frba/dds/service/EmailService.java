package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.entities.Clima;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

  private static final List<String> DESTINATARIOS = List.of(
      "admin@clima.com",
      "emergencias@clima.com",
      "meteorologia@clima.com"
  );

  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void enviarAlerta(Clima clima) {
    SimpleMailMessage mensaje = new SimpleMailMessage();
    mensaje.setTo(DESTINATARIOS.toArray(new String[0]));
    mensaje.setSubject("Alerta climatica - " + clima.getUbicacion());
    mensaje.setText(construirCuerpoCorreo(clima));

    try {
      mailSender.send(mensaje);
      LOGGER.info("Alerta enviada correctamente");
    } catch (Exception e) {
      LOGGER.error("Error al enviar el correo de alerta", e);
    }
  }

  private String construirCuerpoCorreo(Clima clima) {
    return "Se detectaron condiciones climaticas criticas.\n\n"
        + "Ubicacion: " + clima.getUbicacion() + "\n"
        + "Temperatura: " + clima.getTemperatura() + " C\n"
        + "Humedad: " + clima.getHumedad() + "%\n"
        + "Condicion: " + clima.getCondicion() + "\n"
        + "Fecha y hora: " + clima.getFechaConsulta();
  }
}