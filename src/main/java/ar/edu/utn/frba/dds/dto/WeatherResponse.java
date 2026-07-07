package ar.edu.utn.frba.dds.dto;
public record WeatherResponse(
    Location location,
    Current current
) {

  public record Location(
      String name
  ) {}

  public record Current(
      Double temp_c,
      Integer humedad,
      Condition condition
  ) {}

  public record Condition(
      String text
  ) {}
}