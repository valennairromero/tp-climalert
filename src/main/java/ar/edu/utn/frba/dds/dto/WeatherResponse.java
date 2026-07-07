package ar.edu.utn.frba.dds.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherResponse(
    Location location,
    Current current
) {

  public record Location(
      String name
  ) {}

  public record Current(
      @JsonProperty("temp_c") Double tempC,
      Integer humedad,
      Condition condition
  ) {}

  public record Condition(
      String text
  ) {}
}