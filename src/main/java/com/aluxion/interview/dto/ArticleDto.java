package com.aluxion.interview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

/**
 * Article DTO.
 * @author Klissman Granados
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleDto {
    private String title;
    private String storyTitle;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String url;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String author;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer numComments;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer storyId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String storyUrl;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer parentId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String createdAt;
}
