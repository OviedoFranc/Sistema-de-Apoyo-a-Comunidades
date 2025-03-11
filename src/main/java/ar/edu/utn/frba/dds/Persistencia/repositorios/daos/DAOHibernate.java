package ar.edu.utn.frba.dds.Persistencia.repositorios.daos;

import ar.edu.utn.frba.dds.Persistencia.EntityManagerHelper;
import java.util.List;

public class DAOHibernate<T> implements DAO<T> {
    private Class<T> type;

    public DAOHibernate(Class<T> type){
        this.type = type;
    }

    @Override
    public List<T> all() {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        List<T> list = EntityManagerHelper.getEntityManager().createQuery("FROM " + type.getSimpleName()).getResultList();
        EntityManagerHelper.getEntityManager().getTransaction().commit();

        return list;
    }

    @Override
    public T get(long id) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        T o = EntityManagerHelper.getEntityManager().find(type, id);
        EntityManagerHelper.getEntityManager().getTransaction().commit();

        return o;
    }

    @Override
    public void add(Object object) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(object);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void update(Object object) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(object);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void clean() {
        //
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().clear();
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void delete(Object object) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(object);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}