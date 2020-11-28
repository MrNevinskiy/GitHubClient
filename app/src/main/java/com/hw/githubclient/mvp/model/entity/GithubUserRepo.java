package com.hw.githubclient.mvp.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GithubUserRepo {
    private List<GithubUser> repositories = new ArrayList<>(Arrays.asList(
            new GithubUser("0", "login1", "", ""),
            new GithubUser("1", "login1", "", ""),
            new GithubUser("1", "login1", "", ""),
            new GithubUser("1", "login1", "", ""),
            new GithubUser("1", "login1", "", "")));

    public List<GithubUser> getUsers() {
        return Collections.unmodifiableList(repositories);
    }
}
