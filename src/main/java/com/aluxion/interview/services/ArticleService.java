package com.aluxion.interview.services;

import com.aluxion.interview.dto.ArticleDto;
import com.aluxion.interview.dto.PageDto;
import com.aluxion.interview.mappers.ArticlesMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for management of articles.
 *
 * @author Klissman Granados
 */
@Service
public class ArticleService {

    private static final String MAIN_URI = "https://jsonmock.hackerrank.com/api/articles";
    private static PageDto<ArticleDto> cachedPage;

    public PageDto<ArticleDto> orderByDescNumComments(int page, int pageSize) {
        PageDto<ArticleDto> pageArticles = null;
        try {
            pageArticles = fetch().clone();
            pageArticles.getData()
                    .sort(Comparator.comparingInt(ArticleDto::getNumComments).reversed());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }finally {
            pageArticles.calculateMetaData(pageSize, page);
            return pageArticles;
        }
    }

    public PageDto<ArticleDto> orderByAscNumComments(int page, int pageSize) {
        PageDto<ArticleDto> pageArticles = null;
        try {
            pageArticles = fetch().clone();
            pageArticles.getData()
                    .sort(Comparator.comparingInt(ArticleDto::getNumComments));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }finally {
            pageArticles.calculateMetaData(pageSize, page);
            return pageArticles;
        }
    }

    public PageDto<ArticleDto> orderByDescTitle(int page, int pageSize) {
        return orderByDescOrAscTitle(page, pageSize, true);
    }

    public PageDto<ArticleDto> orderByAscTitle(int page, int pageSize) {
        return orderByDescOrAscTitle(page, pageSize, false);
    }

    private PageDto<ArticleDto> orderByDescOrAscTitle(int page, int pageSize, boolean desc) {
        PageDto<ArticleDto> pageArticles = groupByNumComments();
        if(pageArticles == null) return null;
        List<ArticleDto> articles = pageArticles.getData();
        if(!desc)
            articles = articles
                    .stream()
                    .filter(article -> article.getTitle() != null)
                    .sorted(Comparator.comparing(ArticleDto::getTitle))
                    .collect(Collectors.toList());
        if(desc)
            articles = articles
                    .stream()
                    .filter(article -> article.getTitle() != null)
                    .sorted(Comparator.comparing(ArticleDto::getTitle).reversed())
                    .collect(Collectors.toList());
        pageArticles.setData(articles);
        pageArticles.calculateMetaData(pageSize, page);
        return pageArticles;
    }

    private PageDto<ArticleDto> groupByNumComments() {
        PageDto<ArticleDto> pageDto = null;
        try {
            pageDto = fetch().clone();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } finally {
            if(pageDto == null) return null;
            List<ArticleDto> articles = pageDto.getData();
            HashMap<Integer,ArticleDto> group = new HashMap<>();

            boolean isHaveGroup = false;

            List<ArticleDto> groupArticles = new ArrayList<>();

            for(ArticleDto article : articles){

                if(article.getTitle() != null){
                    Integer numComments = article.getNumComments();
                    ArticleDto commonArticleByNumComments =  group.get(numComments);

                    if(commonArticleByNumComments == null){
                        group.put(numComments, article);
                    } else{
                        isHaveGroup = true;
                        groupArticles.add(commonArticleByNumComments);
                        groupArticles.add(article);
                    }
                }
            }

            pageDto.setData(groupArticles);
            if(!isHaveGroup) return null;
            return pageDto;
        }

    }

    public PageDto<ArticleDto> pageArticles(Integer page, Integer perPage) {

        PageDto<ArticleDto> articlePage = null;

        try {
            articlePage = fetch().clone();
        } catch ( CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if(articlePage == null) return null;
            articlePage.calculateMetaData(perPage,page);
            return articlePage;
        }
    }

    private PageDto<ArticleDto> fetch() throws IOException {

        if(cachedPage == null){
            PageDto<ArticleDto> page = ArticlesMapper.map(getFullData(1,10));
            int totalPages = page.getTotalPages();
            for(int i = 2; i <= totalPages; i++){
                page.getData().addAll(ArticlesMapper.map(getFullData(i,10)).getData());
            }
            cachedPage = page;
        }

        return cachedPage;
    }

    private StringBuilder getFullData(int page,int perPage) throws IOException {

        URI uri = UriComponentsBuilder.fromUriString(MAIN_URI)
                .queryParam("page", page)
                .queryParam("per_page", perPage)
                .build().toUri();

        URL url = new URL(uri.toString());
        URLConnection urlConnection = url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        urlConnection.getInputStream()));

        StringBuilder inputLine = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null){
            inputLine.append(line);
        }

        in.close();

        return inputLine;

    }
}
