package ar.edu.utn.frba.dds.Persistencia.repositorios;

import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Persistencia.EntityManagerHelper;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAO;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAOHibernate;

import java.util.List;

public class RepositorioEntidades extends Repositorio<Entidad> {
  private static RepositorioEntidades instance = null;

  private RepositorioEntidades(DAO<Entidad> dao) {
    super(dao);
  }

  public static RepositorioEntidades getInstance() {
    if (instance == null) {
      instance = new RepositorioEntidades(new DAOHibernate<>(Entidad.class));
    }
    return instance;
  }
}

