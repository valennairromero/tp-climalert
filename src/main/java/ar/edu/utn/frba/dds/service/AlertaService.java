package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.entities.Clima;
import ar.edu.utn.frba.dds.repository.IclimaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AlertaService {

  private static final double TEMP_LIMITE = 35.0;
  private static final int HUMEDAD_LIMITE = 60;

  private final IclimaRepository climaRepository;
  private final EmailService emailService;

  public AlertaService(IclimaRepository climaRepository, EmailService emailService) {
    this.climaRepository = climaRepository;
    this.emailService = emailService;
  }

  @Scheduled(fixedRate = 60000)
  public void analizarCondicionesClimaticas() {
    Clima ultimoClima = climaRepository.obtenerUltimo();

    if (ultimoClima != null && esCondicionCritica(ultimoClima)) {
      emailService.enviarAlerta(ultimoClima);
    }
  }

  boolean esCondicionCritica(Clima clima) {
    return clima.getTemperatura() > TEMP_LIMITE
        && clima.getHumedad() > HUMEDAD_LIMITE;
  }
}