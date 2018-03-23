package com.starthub.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Harrison on 02/03/2018.
 */
public abstract class AbstractService<T, ID extends Serializable> implements IService<T, ID> {

    private JpaRepository<T, ID> repository;

    public AbstractService(JpaRepository repository) {
        this.repository = repository;
    }

    public T save(T t) {
        try {
            return repository.save(t);
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    public List<T> findAll() throws Exception{
        return repository.findAll();
    }

    public T findOne(ID id) throws Exception{
        return repository.findOne(id);
    }

    public boolean delete(ID id){
        try {
            repository.delete(id);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
