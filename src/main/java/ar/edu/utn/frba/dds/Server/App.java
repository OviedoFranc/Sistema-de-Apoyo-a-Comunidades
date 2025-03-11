package ar.edu.utn.frba.dds.Server;

import ar.edu.utn.frba.dds.Modelos.Rankings.RankingIncidentes;
import ar.edu.utn.frba.dds.Persistencia.repositorios.RepositorioRankings;

public class App {

  public static void main(String[] args) {
    if (RepositorioRankings.getInstance().all().isEmpty()) {
      RankingIncidentes.getInstance().generarRankings();
    }

    Server.init();
  }
}
