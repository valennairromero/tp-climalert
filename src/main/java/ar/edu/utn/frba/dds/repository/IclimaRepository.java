package ar.edu.utn.frba.dds.repository;

import ar.edu.utn.frba.dds.entities.Clima;
import java.util.List;

public interface IclimaRepository {

  void guardar(Clima clima);

  List<Clima> obtenerTodos();

  Clima obtenerUltimo();
}
