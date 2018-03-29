package com.starthub.controllers;

import com.starthub.messages.FeedMessage;
import com.starthub.models.Feed;
import com.starthub.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<Feed> findAll(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) throws Exception {
        return feedService.findAll(pageNumber, pageSize);
    }

    @RequestMapping("/{id}")
    @ResponseBody
    public Feed findOne(@PathVariable("id") Long id) throws Exception {
        return feedService.findOne(id);
    }

    @MessageMapping("/vote")
    @SendTo("/exchange/feed")
    public Feed vote(@Payload FeedMessage feedMessage) throws Exception{
        return feedService.vote(feedMessage);
    }
}
