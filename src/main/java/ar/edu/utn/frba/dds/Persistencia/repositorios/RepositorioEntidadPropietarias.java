package ar.edu.utn.frba.dds.Persistencia.repositorios;

import ar.edu.utn.frba.dds.Modelos.EntidadPropietaria;
import ar.edu.utn.frba.dds.Persistencia.EntityManagerHelper;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAO;
import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAOHibernate;
import java.util.List;

public class RepositorioEntidadPropietarias extends Repositorio<EntidadPropietaria>{

  private static RepositorioEntidadPropietarias instance = null;

  private RepositorioEntidadPropietarias(DAO<EntidadPropietaria> dao) {
    super(dao);
  }

  public static RepositorioEntidadPropietarias getInstance() {
    if (instance == null) {
      instance = new RepositorioEntidadPropietarias(new DAOHibernate<>(EntidadPropietaria.class));
    }
    return instance;
  }

  public Boolean existeEntidadPropietariaConElNombre(String nombre){
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    List results = EntityManagerHelper.createQuery("from EntidadPropietaria where nombre = :nombre").setParameter("nombre", nombre).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();

    return !results.isEmpty();
  }
}
