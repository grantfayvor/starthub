package com.starthub.controllers;

import com.starthub.messages.FeedMessage;
import com.starthub.models.Feed;
import com.starthub.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Harrison on 3/24/2018.
 */

@Controller
@RequestMapping("api/feed")
public class FeedController {

    private FeedService feedService;

    public FeedController(@Autowired FeedService feedService) {
        this.feedService = feedService;
    }

    @RequestMapping("")
    @ResponseBody
    public List<Feed> findAll() throws Exception {
        return feedService.findAll();
    }

    @RequestMapping("/{id}")
    @ResponseBody
    public Feed findOne(@PathVariable("id") Long id) throws Exception {
        return feedService.findOne(id);
    }

    @MessageMapping("/vote")
    @SendTo("/topic/feed")
    public Feed vote(FeedMessage feedMessage) throws Exception{
        System.out.println("the feed message is " + feedMessage.toString());
        return feedService.vote(feedMessage.isUpVote(), feedMessage.getFeedId());
    }
}
