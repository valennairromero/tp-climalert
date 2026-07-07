package ar.edu.utn.frba.dds;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.entities.Clima;
import ar.edu.utn.frba.dds.repository.IclimaRepository;
import ar.edu.utn.frba.dds.service.AlertaService;
import ar.edu.utn.frba.dds.service.EmailService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlertaServiceTest {

  @Mock
  private IclimaRepository climaRepository;

  @Mock
  private EmailService emailService;

  private AlertaService alertaService;

  @BeforeEach
  void setUp() {
    alertaService = new AlertaService(climaRepository, emailService);
  }

  @Test
  void enviaAlertaCuandoTemperaturaYHumedadSuperanElLimite() {
    Clima clima = Clima.builder()
        .ubicacion("CABA").temperatura(36.0).humedad(65)
        .condicion("Soleado").fechaConsulta(LocalDateTime.now()).build();
    when(climaRepository.obtenerUltimo()).thenReturn(clima);

    alertaService.analizarCondicionesClimaticas();

    verify(emailService, times(1)).enviarAlerta(clima);
  }

  @Test
  void noEnviaAlertaCuandoLaTemperaturaEsNormal() {
    Clima clima = Clima.builder()
        .ubicacion("CABA").temperatura(22.0).humedad(65)
        .condicion("Nublado").fechaConsulta(LocalDateTime.now()).build();
    when(climaRepository.obtenerUltimo()).thenReturn(clima);

    alertaService.analizarCondicionesClimaticas();

    verify(emailService, never()).enviarAlerta(clima);
  }

  @Test
  void noEnviaAlertaCuandoLaHumedadEsNormal() {
    Clima clima = Clima.builder()
        .ubicacion("CABA").temperatura(38.0).humedad(40)
        .condicion("Soleado").fechaConsulta(LocalDateTime.now()).build();
    when(climaRepository.obtenerUltimo()).thenReturn(clima);

    alertaService.analizarCondicionesClimaticas();

    verify(emailService, never()).enviarAlerta(clima);
  }

  @Test
  void noHaceNadaCuandoNoHayRegistros() {
    when(climaRepository.obtenerUltimo()).thenReturn(null);

    alertaService.analizarCondicionesClimaticas();

    verify(emailService, never()).enviarAlerta(null);
  }
}