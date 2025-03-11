package ar.edu.utn.frba.dds.Persistencia.repositorios;

import ar.edu.utn.frba.dds.Modelos.Servicio;
import ar.edu.utn.frba.dds.Persistencia.EntityManagerHelper;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAO;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAOHibernate;
import java.util.List;

public class RepositorioServicios extends Repositorio<Servicio> {
  private static RepositorioServicios instance = null;

  private RepositorioServicios(DAO<Servicio> dao) {
    super(dao);
  }

  public static RepositorioServicios getInstance() {
    if (instance == null) {
      instance = new RepositorioServicios(new DAOHibernate<>(Servicio.class));
    }
    return instance;
  }
}
