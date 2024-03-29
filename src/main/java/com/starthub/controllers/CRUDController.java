package com.starthub.controllers;

import com.starthub.services.IService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Harrison on 02/03/2018.
 */
public abstract class CRUDController<T, ID extends Serializable> {

    private IService<T, ID> service;

    public CRUDController(IService<T, ID> service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean save(@RequestBody T t) {
        return service.save(t) != null;
    }

    @RequestMapping("")
    public List<T> findAll() throws Exception{
        return service.findAll();
    }

    @RequestMapping("/{id}")
    public T findOne(@PathVariable("id") ID id) throws Exception {
        return service.findOne(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") ID id) {
        return service.delete(id);
    }
}
