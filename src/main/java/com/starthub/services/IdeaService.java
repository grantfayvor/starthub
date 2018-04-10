package com.starthub.services;

import com.starthub.models.Feed;
import com.starthub.models.Idea;
import com.starthub.repositories.IdeaRepository;
import com.starthub.utility.JsoupUtil;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;


/**
 * Created by Harrison on 03/03/2018.
 */

@Service
public class IdeaService extends AbstractService<Idea, Long> {

    @Autowired
    private FeedService feedService;
    private IdeaRepository repository;
    private Classifier classifier;

    public IdeaService(@Autowired IdeaRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Idea save(Idea idea) {
        try {
            idea = repository.save(idea);
            feedService.save(new Feed(idea));
            Document document = JsoupUtil.parseHtml(idea.getBody());
            System.out.println("the clean text is: " + document.text());
            return idea;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

}
