package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.entities.Clima;
import ar.edu.utn.frba.dds.repository.IclimaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ClimaMonitoreoService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClimaMonitoreoService.class);

  private final WeatherApiService weatherApiService;
  private final IclimaRepository climaRepository;

  public ClimaMonitoreoService(WeatherApiService weatherApiService,
                             IclimaRepository climaRepository) {
    this.weatherApiService = weatherApiService;
    this.climaRepository = climaRepository;
  }

  @Scheduled(fixedRate = 300000)
  public void registrarClimaActual() {
    try {
      Clima clima = weatherApiService.obtenerClimaActual();
      climaRepository.guardar(clima);
      LOGGER.info("Clima registrado: {}", clima);
    } catch (Exception e) {
      LOGGER.error("Error al obtener/guardar el clima actual", e);
    }
  }
}
