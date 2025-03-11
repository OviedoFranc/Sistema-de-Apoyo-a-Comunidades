package ar.edu.utn.frba.dds.Persistencia.repositorios;

import ar.edu.utn.frba.dds.Modelos.Rankings.MetodosRanking;
import ar.edu.utn.frba.dds.Persistencia.EntityManagerHelper;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAO;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAOHibernate;
import java.time.LocalDate;
import java.util.List;

public class RepositorioRankings extends Repositorio<MetodosRanking> {
  private static RepositorioRankings instance = null;

  private RepositorioRankings(DAO<MetodosRanking> dao) {
    super(dao);
  }

  public static RepositorioRankings getInstance() {
    if (instance == null) {
      instance = new RepositorioRankings(new DAOHibernate<>(MetodosRanking.class));
    }
    return instance;
  }
}
