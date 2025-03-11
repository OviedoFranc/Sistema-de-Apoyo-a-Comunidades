package ar.edu.utn.frba.dds.Persistencia.repositorios;

import ar.edu.utn.frba.dds.Modelos.Comunidades.Comunidad;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAO;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAOHibernate;

public class RepositorioComunidades extends Repositorio<Comunidad> {

  private static RepositorioComunidades instance = null;

  private RepositorioComunidades(DAO<Comunidad> dao) {
    super(dao);
  }

  public static RepositorioComunidades getInstance() {
    if (instance == null) {
      instance = new RepositorioComunidades(new DAOHibernate<>(Comunidad.class));
    }
    return instance;
  }
}