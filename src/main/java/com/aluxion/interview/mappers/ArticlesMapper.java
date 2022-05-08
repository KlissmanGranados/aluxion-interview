package com.aluxion.interview.mappers;

import com.aluxion.interview.dto.ArticleDto;
import com.aluxion.interview.dto.PageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mapper for articles page.
 *
 * @author Klissman Granados
 */
public class ArticlesMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static boolean isArticleHaveTitleOrStoryTitle(ArticleDto article) {
        return (!(article.getTitle() == null || article.getTitle().isEmpty()) ||
                !(article.getStoryTitle() == null || article.getStoryTitle().isEmpty()));
    }

    /**
     * Use title if not null, otherwise use story title. Set zero comments in nulls values.
     * @param article
     * @return
     */
    private static ArticleDto useTitleOrStoryTitleAndSetZeroCommentsInNullsValues(ArticleDto article) {

        if(article.getNumComments() == null)
            article.setNumComments(0);

        if(article.getTitle() != null && !article.getTitle().isEmpty())
            article.setStoryTitle(null);
        else
            article.setTitle(null);

        return article;
    }

    public static PageDto<ArticleDto> map(StringBuilder stringBuilder) throws JsonProcessingException {

        final PageDto<ArticleDto> pageDto = new PageDto<>();
        final Map<String,Object> body = mapper.readValue(stringBuilder.toString(), Map.class);
        pageDto.setPage((Integer) body.get("page"));
        pageDto.setPerPage((Integer) body.get("per_page"));
        pageDto.setTotalPages((Integer) body.get("total_pages"));

        final List<ArticleDto> articles = Arrays
                .asList(mapper.readValue(mapper.writeValueAsString(body.get("data")),ArticleDto[].class))
                .stream()
                .filter(ArticlesMapper::isArticleHaveTitleOrStoryTitle)
                .map(ArticlesMapper::useTitleOrStoryTitleAndSetZeroCommentsInNullsValues)
                .collect(Collectors.toList());

        pageDto.setTotal(articles.size());
        pageDto.setData(articles);
        return pageDto;
    }
}
