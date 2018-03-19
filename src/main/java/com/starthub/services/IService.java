package com.starthub.services;

import java.util.List;

/**
 * Created by Harrison on 02/03/2018.
 */
public interface IService<T, ID> {

    boolean save(T t);
    List<T> findAll() throws Exception;
    T findOne(ID id) throws Exception;
    boolean delete(ID id);

}
