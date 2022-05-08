package com.aluxion.interview.resources;

import com.aluxion.interview.dto.ArticleDto;
import com.aluxion.interview.dto.PageDto;
import com.aluxion.interview.exeption.BadRequest;
import com.aluxion.interview.exeption.NoContent;
import com.aluxion.interview.services.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Klissman Granados
 */
@RestController
@RequestMapping("api/v1/articles")
public class ArticleResource {

    private final ArticleService articleService;

    /**
     * Constructor for the ArticleResource, which injects the ArticleService.
     * @param articleService
     */
    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Returns a list of articles.
     * @return
     * @throws IOException
     */
    @GetMapping
    public PageDto<ArticleDto> pageArticles(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="perPage",defaultValue = "10") Integer perPage
    ) {
        return this.articleService.pageArticles(page,perPage);
    }

    @GetMapping("/names-ordered-decreasing-by-comment")
    public PageDto<ArticleDto> orderByDescNumComments(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="perPage",defaultValue = "10") Integer perPage
    ) {
        return this.articleService.orderByDescNumComments(page,perPage);
    }

    @GetMapping("/names-ordered-ascendant-by-comment")
    public PageDto<ArticleDto> orderByAscNumComments(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="perPage",defaultValue = "10") Integer perPage
    ) {
        return this.articleService.orderByAscNumComments(page,perPage);
    }

    @GetMapping("/decreasing-alphabetically-same-comment-counts")
    public PageDto<ArticleDto> orderByDescTitle(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="perPage",defaultValue = "10") Integer perPage
    ) {
        PageDto<ArticleDto> articles = this.articleService.orderByDescTitle(page,perPage);
        if(articles == null)
            throw new NoContent("Articles not found");
        return articles;
    }

    /**
     * Return a PageDto<ArticleDTO> by query criteries
     * @param page
     * @param perPage
     * @param sort
     * @param by
     * @return
     * @throws IOException
     */
    @GetMapping("/query")
    public PageDto<ArticleDto> orderBy(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="perPage",defaultValue = "10") Integer perPage,
            @RequestParam(value = "sort" , required = false) String sort,
            @RequestParam(value = "group", required = false) String group,
            @RequestParam(value = "by",required = false) String by
    ) {

        if (sort == null && group == null || by == null && group == null) {
            throw new BadRequest("You must provide a sort or group parameter");
        } else if(sort != null){

            if(sort.equals("desc")){
                if(by.equals("num_comments")){
                    return this.articleService.orderByDescNumComments(page,perPage);
                }
            }

            if(sort.equals("asc")){
                if(by.equals("num_comments")){
                    return this.articleService.orderByAscNumComments(page,perPage);
                }
            }

        } else if(group != null){
            if(group.equals("num_comments")){ System.out.println("group by num_comments");
                if(sort.equals("desc")){
                    return this.articleService.orderByDescTitle(page,perPage);
                }
                if(sort.equals("asc")){
                    return this.articleService.orderByAscTitle(page,perPage);
                }
            }
        }

        return null;

    }

}
