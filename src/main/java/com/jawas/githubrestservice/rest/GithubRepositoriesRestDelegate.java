package com.jawas.githubrestservice.rest;

import com.jawas.githubrestservice.apiclient.github.GithubApiClient;
import com.jawas.githubrestservice.apiclient.github.exception.GithubServiceException;
import com.jawas.githubrestservice.apiclient.github.model.GithubRepoResultModel;
import com.jawas.githubrestservice.rest.model.GithubRepositoryModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class GithubRepositoriesRestDelegate
{
    private final GithubApiClient githubApiClient;

    GithubRepositoriesRestDelegate(GithubApiClient githubApiClient)
    {
        this.githubApiClient = githubApiClient;
    }

    GithubRepositoryModel findRepo(String owner, String repositoryName) throws GithubServiceException
    {
        final GithubRepoResultModel githubRepoResult = githubApiClient.findRepo(owner, repositoryName);
        final GithubRepositoryModel githubRepository = GithubRepositoryModel.builder()
                .description(githubRepoResult.getDescription())
                .fullName(githubRepoResult.getFullName())
                .cloneUrl(githubRepoResult.getCloneUrl())
                .createdAt(githubRepoResult.getCreatedAt())
                .stars(githubRepoResult.getStargazersCount())
                .build();
        log.info("Github repo retrieved {}", githubRepository);

        return githubRepository;
    }
}
