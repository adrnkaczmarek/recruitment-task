package com.jawas.githubrestservice.rest.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GithubRepositoryModel
{
    private String fullName;
    private String description;
    private String cloneUrl;
    private Integer stars;
    private Date createdAt;
}
