package com.flowingbit.data.collect.house_spider.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public String test(){
        GithubRepoPageProcessor githubRepoPageProcessor = new GithubRepoPageProcessor();
        githubRepoPageProcessor.main(new String[3]);
        return "";
    }
}
