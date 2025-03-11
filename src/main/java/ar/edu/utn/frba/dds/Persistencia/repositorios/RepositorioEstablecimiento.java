package ar.edu.utn.frba.dds.Persistencia.repositorios;

import ar.edu.utn.frba.dds.Modelos.Entidad;
import ar.edu.utn.frba.dds.Modelos.Establecimiento;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAO;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAOHibernate;

public class RepositorioEstablecimiento extends Repositorio<Establecimiento> {
  private static RepositorioEstablecimiento instance = null;

  private RepositorioEstablecimiento(DAO<Establecimiento> dao) {
    super(dao);
  }

  public static RepositorioEstablecimiento getInstance() {
    if (instance == null) {
      instance = new RepositorioEstablecimiento(new DAOHibernate<>(Establecimiento.class));
    }
    return instance;
  }
}
