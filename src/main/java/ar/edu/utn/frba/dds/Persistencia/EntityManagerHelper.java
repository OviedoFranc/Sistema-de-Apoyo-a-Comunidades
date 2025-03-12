package ar.edu.utn.frba.dds.Persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EntityManagerHelper {

  private static EntityManagerFactory emf;

  private static ThreadLocal<EntityManager> threadLocal;

  static {
    initializeFactory();
  }
  public static void setEntityManagerFactory(EntityManagerFactory factory) {
    emf = factory;
  }
  // EntityManagerFactory por defecto lo utiliza con varialbes de entorno, caso contrario inyeccion de dependencias.
  private static void initializeFactory() {
    if (emf == null) {
      try {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();

        String[] keys = {
            "javax.persistence.jdbc.driver",
            "javax.persistence.jdbc.password",
            "javax.persistence.jdbc.url",
            "javax.persistence.jdbc.user",
            "hibernate.hbm2ddl.auto",
            "hibernate.connection.pool_size",
            "hibernate.show_sql"
        };

        for (String key : keys) {
          if (env.containsKey(key)) {
            configOverrides.put(key, env.get(key));
          }
        }

        emf = Persistence.createEntityManagerFactory("simple-persistence-unit", configOverrides);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static EntityManagerFactory emf() {
    if (emf == null) {
      throw new IllegalStateException("EntityManagerFactory no ha sido inicializado correctamente.");
    }
    return emf;
  }

  private static ThreadLocal<EntityManager> threadLocal() {
    if(threadLocal == null) {
      threadLocal = new ThreadLocal<>();
    }
    return threadLocal;
  }

  public static EntityManager entityManager() {
    return getEntityManager();
  }

  public static EntityManager getEntityManager() {
    EntityManager manager = threadLocal().get();
    if (manager == null || !manager.isOpen()) {
      manager = emf().createEntityManager();
      threadLocal().set(manager);
    }
    return manager;
  }

  public static void closeEntityManager() {
    EntityManager em = threadLocal.get();
    if(em != null) {
      em.close();
    }
    threadLocal.remove();
  }

  public static void closeEntityManagerFactory() {
    emf.close();
  }

  public static void beginTransaction() {
    EntityManager em = EntityManagerHelper.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      if (!tx.isActive()) {
        tx.begin();
      }
    } catch (Exception e) {
      tx.rollback();
    }
  }

  public static void commit() {
    EntityManager em = EntityManagerHelper.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    if(tx.isActive()){
      tx.commit();
    }

  }

  public static void rollback(){
    EntityManager em = EntityManagerHelper.getEntityManager();
    EntityTransaction tx = em.getTransaction();
    if(tx.isActive()){
      tx.rollback();
    }
  }

  public static Query createQuery(String query) {
    return getEntityManager().createQuery(query);
  }

  public static void persist(Object o){
    entityManager().persist(o);
  }

  public static <A> A withTransaction(Supplier<A> action) {
    beginTransaction();
    try {
      A result = action.get();
      commit();
      return result;
    } catch(Throwable e) {
      rollback();
      throw e;
    }
  }
}
