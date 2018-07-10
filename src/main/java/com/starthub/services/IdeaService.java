package com.starthub.services;

import com.starthub.models.Feed;
import com.starthub.models.Idea;
import com.starthub.models.Rank;
import com.starthub.repositories.IdeaRepository;
import com.starthub.utility.AbstractClassifierUtil;
import com.starthub.utility.ClassifierUtil;
import com.starthub.utility.DecisionTreeUtil;
import com.starthub.utility.JsoupUtil;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.security.SecureRandom;


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
    @Value("${starthub.files.location}")
    private String fileSaveLocation;

    public IdeaService(@Autowired IdeaRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public Page<Idea> findUserIdeas(String username, int pageNumber, int pageSize) {
        return repository.findAllByCreatedByOrderByUpdatedAtDesc(username, new PageRequest(pageNumber, pageSize));
    }

    @Override
    public Idea save(Idea idea) {
        try {
            Document document = JsoupUtil.parseHtml(idea.getBody());
            idea.addTag(tagService.findByName(new ClassifierUtil().test(document.text())));
            String fileName = "";
            if (!idea.getDocument().isEmpty()) {
                fileName = FileSystems.getDefault().getSeparator() + new BigInteger(50, new SecureRandom()).toString(16) + "__" + idea.getDocument().getOriginalFilename();
                try (FileOutputStream outputStream = new FileOutputStream(fileSaveLocation + fileName)) {
                    outputStream.write(idea.getDocument().getBytes());
                } catch (IOException ex) {
                    System.out.println("an error occurred while trying to save the file \n" + ex);
                    fileName = "";
                }
            }
            idea.setDocumentLocation(fileName);
            idea = repository.save(idea);
            Feed feed = new Feed(idea);
            feed.setRank(feedService.saveRank(new Rank(new DecisionTreeUtil().test(document.text()))));
            feedService.save(feed);
            return idea;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

//    @Override
//    public Idea save(Idea idea) {
//        try {
//            Document document = JsoupUtil.parseHtml(idea.getBody());
//            idea.getTags().add(tagService.findByName(new ClassifierUtil().classify(document.text())));
//            String fileName = "";
//            if (!idea.getDocument().isEmpty()) {
//                fileName = FileSystems.getDefault().getSeparator() + new BigInteger(50, new SecureRandom()).toString(16) + "__" + idea.getDocument().getOriginalFilename();
//                try (FileOutputStream outputStream = new FileOutputStream(fileSaveLocation + fileName)) {
//                    outputStream.write(idea.getDocument().getBytes());
//                } catch (IOException ex) {
//                    System.out.println("an error occurred while trying to save the file \n" + ex);
//                    fileName = "";
//                }
//            }
//            idea.setDocumentLocation(fileName);
//            idea = repository.save(idea);
//            Feed feed = new Feed(idea);
//            feed.setRank(feedService.saveRank(new Rank(new DecisionTreeUtil().makeDecision(document.text()))));
//            feedService.save(feed);
//            return idea;
//        } catch (Exception ex) {
//            System.out.println(ex);
//            return null;
//        }
//    }

}
