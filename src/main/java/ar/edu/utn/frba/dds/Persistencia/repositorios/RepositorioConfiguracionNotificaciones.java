package ar.edu.utn.frba.dds.Persistencia.repositorios;

import ar.edu.utn.frba.dds.Modelos.Notificaciones.ConfiguracionTipoNotificacion.ConfiguracionNotificaciones;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAO;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAOHibernate;

public class RepositorioConfiguracionNotificaciones extends Repositorio<ConfiguracionNotificaciones>{

  private static RepositorioConfiguracionNotificaciones instance = null;

  private RepositorioConfiguracionNotificaciones(DAO<ConfiguracionNotificaciones> dao) {
    super(dao);
  }

  public static RepositorioConfiguracionNotificaciones getInstance() {
    if (instance == null) {
      instance = new RepositorioConfiguracionNotificaciones(new DAOHibernate<>(ConfiguracionNotificaciones.class));
    }
    return instance;
  }

}
