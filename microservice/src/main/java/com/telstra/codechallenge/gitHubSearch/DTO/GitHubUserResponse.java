package com.telstra.codechallenge.gitHubSearch.DTO;

import com.telstra.codechallenge.gitHubSearch.model.GitHubUser;
import lombok.Data;

import java.util.List;

@Data
public class GitHubUserResponse {
    private List<GitHubUser> items;
}
