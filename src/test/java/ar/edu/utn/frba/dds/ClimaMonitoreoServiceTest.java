package ar.edu.utn.frba.dds;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.entities.Clima;
import ar.edu.utn.frba.dds.repository.IclimaRepository;
import ar.edu.utn.frba.dds.service.ClimaMonitoreoService;
import ar.edu.utn.frba.dds.service.WeatherApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClimaMonitoreoServiceTest {

  @Mock
  private WeatherApiService weatherApiService;

  @Mock
  private IclimaRepository climaRepository;

  private ClimaMonitoreoService climaMonitorService;

  @BeforeEach
  void setUp() {
    climaMonitorService = new ClimaMonitoreoService(weatherApiService, climaRepository);
  }

  @Test
  void guardaElClimaCuandoLaConsultaEsExitosa() {
    Clima clima = Clima.builder()
        .ubicacion("CABA").temperatura(25.0).humedad(50)
        .condicion("Nublado").build();
    when(weatherApiService.obtenerClimaActual()).thenReturn(clima);

    climaMonitorService.registrarClimaActual();

    verify(climaRepository, times(1)).guardar(clima);
  }

  @Test
  void noRompeCuandoLaConsultaFalla() {
    when(weatherApiService.obtenerClimaActual())
        .thenThrow(new IllegalStateException("API caida"));

    climaMonitorService.registrarClimaActual();

    verify(climaRepository, never()).guardar(org.mockito.ArgumentMatchers.any());
  }
}
