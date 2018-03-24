package com.starthub.services;

import com.starthub.models.Feed;
import com.starthub.models.Idea;
import com.starthub.repositories.IdeaRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Harrison on 03/03/2018.
 */

@Service
public class IdeaService extends AbstractService<Idea, Long> {

    private IdeaRepository repository;
    @Autowired
    private FeedService feedService;

    public IdeaService(@Autowired IdeaRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Idea save(Idea idea) {
        System.out.println(idea);
        try {
            idea = repository.save(idea);
            feedService.save(new Feed(idea));
            Document document = Jsoup.parse(idea.getBody());
            System.out.println("the clean text is: " + document.text());
            return idea;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

}
