package com.telstra.codechallenge.gitHubSearch.controller;


import com.telstra.codechallenge.gitHubSearch.DTO.GitHubUserResponse;
import com.telstra.codechallenge.gitHubSearch.service.GitHubService;
import com.telstra.codechallenge.gitHubSearch.model.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class gitHubController {

    @Autowired
    private GitHubService gitHubService;

    @RequestMapping(path = "/gitHub/zeroFollowerUser", method = RequestMethod.GET)
    public ResponseEntity<GitHubUserResponse> getGitHubUsers(@RequestParam(value = "per_page", defaultValue = "10") String per_page){
        return ResponseEntity.ok(gitHubService.getUsers(per_page));
    }
}
