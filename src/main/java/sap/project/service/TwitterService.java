package sap.project.service;

import twitter4j.TwitterException;

public interface TwitterService {
    public void createTweet(String tweet) throws TwitterException;
}
