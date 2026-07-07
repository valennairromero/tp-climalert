package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.dto.WeatherResponse;
import ar.edu.utn.frba.dds.entities.Clima;
import ar.edu.utn.frba.dds.service.WeatherApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
public class WeatherApiServiceTest {
  @Mock
  private RestClient restClient;

  @Mock
  private RestClient.RequestHeadersUriSpec uriSpec;

  @Mock
  private RestClient.RequestHeadersSpec headersSpec;

  @Mock
  private RestClient.ResponseSpec responseSpec;

  private WeatherApiService weatherApiService;

  @BeforeEach
  void setUp() {
    weatherApiService = new WeatherApiService("fake-key", "CABA");
    ReflectionTestUtils.setField(weatherApiService, "restClient", restClient);
  }

  @Test
  void obtieneElClimaActualCorrectamente() {
    WeatherResponse respuestaFake = new WeatherResponse(
        new WeatherResponse.Location("Buenos Aires"),
        new WeatherResponse.Current(36.5, 70,
            new WeatherResponse.Condition("Soleado"))
    );

    when(restClient.get()).thenReturn(uriSpec);
    when(uriSpec.uri(anyString(), any(Object[].class))).thenReturn(headersSpec);
    when(headersSpec.retrieve()).thenReturn(responseSpec);
    when(responseSpec.body(WeatherResponse.class)).thenReturn(respuestaFake);

    Clima clima = weatherApiService.obtenerClimaActual();

    assertEquals("Buenos Aires", clima.getUbicacion());
    assertEquals(36.5, clima.getTemperatura());
    assertEquals(70, clima.getHumedad());
    assertEquals("Soleado", clima.getCondicion());
  }

  @Test
  void tiraExcepcionCuandoLaRespuestaEsNula() {
    when(restClient.get()).thenReturn(uriSpec);
    when(uriSpec.uri(anyString(), any(Object[].class))).thenReturn(headersSpec);
    when(headersSpec.retrieve()).thenReturn(responseSpec);
    when(responseSpec.body(WeatherResponse.class)).thenReturn(null);

    assertThrows(IllegalStateException.class,
        () -> weatherApiService.obtenerClimaActual());
  }

  private static String anyString() {
    return org.mockito.ArgumentMatchers.anyString();
  }

  private static <T> T any(Class<T> type) {
    return org.mockito.ArgumentMatchers.any(type);
  }
}

