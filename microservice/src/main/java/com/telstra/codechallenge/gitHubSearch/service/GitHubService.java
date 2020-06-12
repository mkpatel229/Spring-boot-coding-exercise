package com.telstra.codechallenge.gitHubSearch.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.telstra.codechallenge.gitHubSearch.model.GitHubUser;
import com.telstra.codechallenge.gitHubSearch.DTO.GitHubUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    @Value("${github.base.url}")
    private String gitHubBaseUrl;
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "FallbackGetUsers", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000") })
    public GitHubUserResponse getUsers(String per_page){

        GitHubUserResponse response =  restTemplate.getForObject(gitHubBaseUrl+ "/search/users?q=followers:0&sort=joined&order=asc&per_page="+per_page, GitHubUserResponse.class);

        return response;
    }

    public GitHubUserResponse FallbackGetUsers(String per_page){

        GitHubUserResponse response = new GitHubUserResponse();
        response.setItems(new ArrayList<GitHubUser>());
        response.getItems().add(new GitHubUser(0L,"SERVICE IS DOWN","SERVICE IS DOWN"));

        return response;
    }
}
