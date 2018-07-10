package com.starthub.controllers;

import com.starthub.models.Idea;
import com.starthub.services.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Harrison on 03/03/2018.
 */

@RestController
@RequestMapping("api/idea")
public class IdeaController extends CRUDController<Idea, Long> {

    private IdeaService service;

    public IdeaController(@Autowired IdeaService service) {
        super(service);
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public boolean save(@ModelAttribute Idea idea) {
        return service.save(idea) != null;
    }

}
