package ar.edu.utn.frba.dds.entities;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Builder
@Data
public class Clima {
  private Long id;

  private LocalDateTime fechaConsulta;

  private Double temperatura;

  private Integer humedad;

  private String condicion;

  private String ubicacion;
}
