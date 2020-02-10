package com.jawas.githubrestservice.rest;

import com.jawas.githubrestservice.apiclient.github.GithubApiClient;
import com.jawas.githubrestservice.apiclient.github.exception.GithubServiceException;
import com.jawas.githubrestservice.rest.model.GithubRepositoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/repositories", produces = MediaType.APPLICATION_JSON_VALUE)
public class GithubRepositoriesRestController
{
    private final GithubRepositoriesRestDelegate githubRepositoriesRestDelegate;

    @Autowired
    public GithubRepositoriesRestController(GithubApiClient githubApiClient)
    {
        this.githubRepositoriesRestDelegate = new GithubRepositoriesRestDelegate(githubApiClient);
    }

    @GetMapping("/{owner}/{repositoryName}")
    public GithubRepositoryModel repositories(@PathVariable String owner, @PathVariable String repositoryName)
    {
        try
        {
            return githubRepositoriesRestDelegate.findRepo(owner, repositoryName);
        }
        catch (GithubServiceException exception)
        {
            if(exception.getStatusCode() == null)
            {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.valueOf(exception.getStatusCode()), exception.getMessage(), exception);
            }
        }
    }
}
