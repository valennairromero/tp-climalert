package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import ar.edu.utn.frba.dds.entities.Clima;
import ar.edu.utn.frba.dds.repository.impl.InMemoryClimaRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryClimaRepositoryTest {

  private InMemoryClimaRepository repository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryClimaRepository();
  }

  @Test
  void devuelveNullCuandoNoHayClimasGuardados() {
    assertNull(repository.obtenerUltimo());
  }

  @Test
  void guardaYObtieneElUltimoClimaCorrectamente() {
    Clima primero = Clima.builder()
        .ubicacion("CABA").temperatura(20.0).humedad(50)
        .condicion("Nublado").fechaConsulta(LocalDateTime.now()).build();
    Clima segundo = Clima.builder()
        .ubicacion("CABA").temperatura(36.0).humedad(70)
        .condicion("Soleado").fechaConsulta(LocalDateTime.now()).build();

    repository.guardar(primero);
    repository.guardar(segundo);

    assertEquals(segundo, repository.obtenerUltimo());
  }

  @Test
  void obtieneTodosLosClimasGuardados() {
    Clima clima = Clima.builder()
        .ubicacion("CABA").temperatura(25.0).humedad(55)
        .condicion("Templado").build();

    repository.guardar(clima);

    assertEquals(1, repository.obtenerTodos().size());
    assertEquals(clima, repository.obtenerTodos().get(0));
  }
}
