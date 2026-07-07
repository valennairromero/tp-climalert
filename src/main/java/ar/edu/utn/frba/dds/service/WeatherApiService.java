package ar.edu.utn.frba.dds.service;

import ar.edu.utn.frba.dds.dto.WeatherResponse;
import ar.edu.utn.frba.dds.entities.Clima;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherApiService {

  private final RestClient restClient;
  private final String apiKey;
  private final String location;

  public WeatherApiService(
      @Value("${weather.api.key}") String apiKey,
      @Value("${weather.api.location:CABA}") String location
  ) {
    this.restClient = RestClient.create("https://api.weatherapi.com");
    this.apiKey = apiKey;
    this.location = location;
  }

  public Clima obtenerClimaActual() {

    WeatherResponse respuesta = restClient.get()
        .uri("/v1/current.json?key={key}&q={location}", apiKey, location)
        .retrieve()
        .body(WeatherResponse.class);

    if (respuesta == null || respuesta.current() == null) {
      throw new IllegalStateException("Respuesta invalida de WeatherAPI");
    }

    return Clima.builder()
        .fechaConsulta(LocalDateTime.now())
        .temperatura(respuesta.current().tempC())
        .humedad(respuesta.current().humedad())
        .condicion(respuesta.current().condition().text())
        .ubicacion(respuesta.location().name())
        .build();
  }
}
