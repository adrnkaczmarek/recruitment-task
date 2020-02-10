package com.jawas.githubrestservice.apiclient.github.exception;

import lombok.Getter;

public class GithubServiceException extends Exception
{
    @Getter
    private Integer statusCode;

    public GithubServiceException(String message, Integer statusCode)
    {
        super(message);

        this.statusCode = statusCode;
    }

    public GithubServiceException(Throwable cause)
    {
        super(cause);
    }
}
