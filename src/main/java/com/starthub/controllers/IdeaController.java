package com.starthub.controllers;

import com.starthub.models.Idea;
import com.starthub.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Harrison on 03/03/2018.
 */

@RestController
@RequestMapping("api/idea")
public class IdeaController extends CRUDController<Idea, Long> {

    @Autowired
    private IdeaService service;

    public IdeaController(IdeaService service) {
        super(service);
        this.service = service;
    }
}
