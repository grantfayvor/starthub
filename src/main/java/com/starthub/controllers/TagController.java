package com.starthub.controllers;

import com.starthub.models.Tag;
import com.starthub.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Harrison on 3/20/2018.
 */

@RestController
@RequestMapping("api/tag")
public class TagController extends CRUDController<Tag, Long> {

    private TagService service;

    public TagController(@Autowired TagService service) {
        super(service);
        this.service = service;
    }
}
