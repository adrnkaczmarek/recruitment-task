package com.jawas.githubrestservice.apiclient.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawas.githubrestservice.apiclient.github.exception.GithubServiceException;
import com.jawas.githubrestservice.apiclient.github.model.GithubRepoResultModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@PropertySource("classpath:service.properties")
public class GithubApiClientImpl implements GithubApiClient
{
    private final ObjectMapper parser;

    @Value("${githubApiBaseUrl}")
    private String githubApiBaseUrl;
    @Value("${reposPartPath}")
    private String reposPartPath;

    public GithubApiClientImpl()
    {
        this.parser = new ObjectMapper();
    }

    @Override
    public GithubRepoResultModel findRepo(String owner, String repo) throws GithubServiceException
    {
        GithubRepoResultModel githubRepoResultModel;
        final HttpClient httpClient = HttpClientBuilder.create().build();
        final String ownersRepoUrl = new StringBuilder()
                .append(githubApiBaseUrl)
                .append("/")
                .append(reposPartPath)
                .append("/")
                .append(owner)
                .append("/")
                .append(repo)
                .toString();

        try
        {
            final HttpGet httpGet = new HttpGet(ownersRepoUrl);
            final HttpResponse httpResponse = httpClient.execute(httpGet);
            final int statusCode = httpResponse.getStatusLine().getStatusCode();

            switch(statusCode)
            {
                case(HttpStatus.SC_OK):
                    githubRepoResultModel = parser.readValue(httpResponse.getEntity().getContent(), GithubRepoResultModel.class);
                    break;
                default:
                    throw new GithubServiceException("Something went wrong during calling github service.", statusCode);
            }
        }
        catch (IOException e)
        {
            throw new GithubServiceException(e);
        }

        return githubRepoResultModel;
    }
}
