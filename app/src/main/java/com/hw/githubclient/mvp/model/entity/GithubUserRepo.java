package com.hw.githubclient.mvp.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GithubUserRepo {
    private List<GithubUser> repositories = new ArrayList<>(Arrays.asList(
            new GithubUser("0", "login1", "", ""),
            new GithubUser("1", "login2", "", ""),
            new GithubUser("2", "login3", "", ""),
            new GithubUser("3", "login4", "", ""),
            new GithubUser("4", "login5", "", "")));

    public List<GithubUser> getUsers() {
        return Collections.unmodifiableList(repositories);
    }
}
