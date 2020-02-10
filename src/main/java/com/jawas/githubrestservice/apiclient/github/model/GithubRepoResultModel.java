package com.jawas.githubrestservice.apiclient.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepoResultModel
{
    @JsonProperty("full_name") private String fullName;
    @JsonProperty("description") private String description;
    @JsonProperty("clone_url") private String cloneUrl;
    @JsonProperty("stargazers_count") private Integer stargazersCount;
    @JsonProperty("created_at") private Date createdAt;
}
