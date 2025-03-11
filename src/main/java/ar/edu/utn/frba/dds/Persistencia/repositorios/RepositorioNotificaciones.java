package ar.edu.utn.frba.dds.Persistencia.repositorios;

import ar.edu.utn.frba.dds.Modelos.Notificaciones.Notificacion.Notificacion;
import ar.edu.utn.frba.dds.Persistencia.EntityManagerHelper;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAO;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAOHibernate;
import java.util.List;

public class RepositorioNotificaciones extends Repositorio<Notificacion>{

  private static RepositorioNotificaciones instance = null;

  private RepositorioNotificaciones(DAO<Notificacion> dao) {
    super(dao);
  }

  public static RepositorioNotificaciones getInstance() {
    if (instance == null) {
      instance = new RepositorioNotificaciones(new DAOHibernate<>(Notificacion.class));
    }
    return instance;
  }

  public List getNotificacionesDeUsuario (long id) {
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    List resultados = EntityManagerHelper.createQuery("from Notificacion where usuarioANotificar_id = :usuario").setParameter("usuario", id).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();

    return resultados;
  }

}
