package com.telstra.codechallenge.gitHubSearch.controller;

import com.telstra.codechallenge.gitHubSearch.DTO.GitHubUserResponse;
import com.telstra.codechallenge.gitHubSearch.model.GitHubUser;
import com.telstra.codechallenge.gitHubSearch.service.GitHubService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class gitHubControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private GitHubService gitHubService;

    private MockRestServiceServer mockRestServiceServer;

    @Value("${github.base.url}")
    private String gitHubBaseUrl;


    @Test
    public void getUsersMockTest() {
        final String url = gitHubBaseUrl + "/search/users?q=followers:0&sort=joined&order=asc&per_page=";

        this.mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(manyTimes(), requestTo(url + "10"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).body("{\n" +
                        "    \"items\": [\n" +
                        "        {\n" +
                        "            \"id\": 44,\n" +
                        "            \"login\": \"errfree\",\n" +
                        "            \"html_url\": \"https://github.com/errfree\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 81,\n" +
                        "            \"login\": \"engineyard\",\n" +
                        "            \"html_url\": \"https://github.com/engineyard\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 119,\n" +
                        "            \"login\": \"ministrycentered\",\n" +
                        "            \"html_url\": \"https://github.com/ministrycentered\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 128,\n" +
                        "            \"login\": \"collectiveidea\",\n" +
                        "            \"html_url\": \"https://github.com/collectiveidea\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 144,\n" +
                        "            \"login\": \"ogc\",\n" +
                        "            \"html_url\": \"https://github.com/ogc\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 150,\n" +
                        "            \"login\": \"sevenwire\",\n" +
                        "            \"html_url\": \"https://github.com/sevenwire\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 167,\n" +
                        "            \"login\": \"entryway\",\n" +
                        "            \"html_url\": \"https://github.com/entryway\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 264,\n" +
                        "            \"login\": \"merb\",\n" +
                        "            \"html_url\": \"https://github.com/merb\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 359,\n" +
                        "            \"login\": \"moneyspyder\",\n" +
                        "            \"html_url\": \"https://github.com/moneyspyder\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 374,\n" +
                        "            \"login\": \"sproutit\",\n" +
                        "            \"html_url\": \"https://github.com/sproutit\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}").contentType(MediaType.APPLICATION_JSON));

        GitHubUserResponse response = gitHubService.getUsers("10");

        assertEquals(10, response.getItems().size());
    }

    @Test
    void FallbackGetUsersTest() {
        GitHubUserResponse response = new GitHubUserResponse();
        response.setItems(new ArrayList<GitHubUser>());
        response.getItems().add(new GitHubUser(0L, "SERVICE IS DOWN", "SERVICE IS DOWN"));

        assertEquals(response, gitHubService.FallbackGetUsers("10"));
    }

    @Test
    void getGitHubUsersDefault() {
        final String url = "http://localhost:" + port + "/gitHub/zeroFollowerUser";
        ResponseEntity<GitHubUserResponse> responseEntity= restTemplate.getForEntity(url,GitHubUserResponse.class);

        assertEquals(200,responseEntity.getStatusCode().value());
        assertEquals(10,responseEntity.getBody().getItems().size());
    }
}