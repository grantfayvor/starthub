package com.starthub.messages;

/**
 * Created by Harrison on 3/24/2018.
 */
public class FeedMessage {

    private boolean upVote;
    private long feedId;

    public boolean isUpVote() {
        return upVote;
    }

    public void setUpVote(boolean upVote) {
        this.upVote = upVote;
    }

    public long getFeedId() {
        return feedId;
    }

    public void setFeedId(long feedId) {
        this.feedId = feedId;
    }

    @Override
    public String toString() {
        return "FeedMessage{" +
                "upVote=" + upVote +
                ", feedId=" + feedId +
                '}';
    }
}
