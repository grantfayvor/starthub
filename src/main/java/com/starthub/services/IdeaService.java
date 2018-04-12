package com.starthub.services;

import com.starthub.models.Feed;
import com.starthub.models.Idea;
import com.starthub.models.Tag;
import com.starthub.repositories.IdeaRepository;
import com.starthub.utility.ClassifierUtil;
import com.starthub.utility.JsoupUtil;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Harrison on 03/03/2018.
 */

@Service
public class IdeaService extends AbstractService<Idea, Long> {

    @Autowired
    private FeedService feedService;
    @Autowired
    private TagService tagService;
    private IdeaRepository repository;

    public IdeaService(@Autowired IdeaRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Idea save(Idea idea) {
        try {
            Document document = JsoupUtil.parseHtml(idea.getBody());
            idea.getTags().add(tagService.findByName(new ClassifierUtil().classify(document.text())));
            idea = repository.save(idea);
            feedService.save(new Feed(idea));
            return idea;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

}
