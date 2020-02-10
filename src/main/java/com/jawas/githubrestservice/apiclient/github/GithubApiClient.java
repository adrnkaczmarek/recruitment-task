package com.jawas.githubrestservice.apiclient.github;

import com.jawas.githubrestservice.apiclient.github.exception.GithubServiceException;
import com.jawas.githubrestservice.apiclient.github.model.GithubRepoResultModel;

public interface GithubApiClient
{
    GithubRepoResultModel findRepo(String user, String repo) throws GithubServiceException;
}
