package ar.edu.utn.frba.dds.Persistencia.repositorios;

import ar.edu.utn.frba.dds.Persistencia.repositorios.daos.DAO;

import java.util.List;

public class Repositorio<T> {
    protected DAO<T> dao;

    public Repositorio(DAO<T> dao) {
        this.dao = dao;
    }

    public void setDao(DAO<T> dao) {
        this.dao = dao;
    }

    public List<T> all(){
        return this.dao.all();
    }

    public T get(long id){
        return this.dao.get(id);
    }

    public void add(Object object){
        this.dao.add(object);
    }

    public void update(Object object){
        this.dao.update(object);
    }

    public void delete(Object object){
        this.dao.delete(object);
    }

    public void clean() {
        this.dao.clean();
    }
}
