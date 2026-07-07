package ar.edu.utn.frba.dds.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

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
