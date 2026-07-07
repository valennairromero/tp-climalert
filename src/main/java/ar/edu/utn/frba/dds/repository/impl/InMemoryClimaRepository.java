package ar.edu.utn.frba.dds.repository.impl;

import ar.edu.utn.frba.dds.entities.Clima;
import ar.edu.utn.frba.dds.repository.IclimaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryClimaRepository implements IclimaRepository {

  private final List<Clima> climas = new ArrayList<>();

  @Override
  public void guardar(Clima clima) {
    climas.add(clima);
  }

  @Override
  public List<Clima> obtenerTodos() {
    return climas.stream().toList();
  }

  @Override
  public Clima obtenerUltimo() {

    if (climas.isEmpty()) {
      return null;
    }
    return climas.get(climas.size() - 1);
  }
}
