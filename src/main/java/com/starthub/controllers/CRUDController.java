package com.starthub.controllers;

import com.starthub.services.IService;
import org.springframework.web.bind.annotation.*;

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
    public T save(@RequestBody T t) {
        return service.save(t);
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
