package com.aluxion.interview.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for a page of results.
 * @author Klissman Granados
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PageDto <T> {
    protected Integer page;
    protected Integer perPage;
    protected Integer total;
    protected Integer totalPages;
    protected List<T> data = new ArrayList<>();

    @Override
    public PageDto clone() throws CloneNotSupportedException {
        PageDto<T> pageDto = new PageDto<>();
        pageDto.page = page;
        pageDto.perPage = perPage;
        pageDto.total = total;
        pageDto.totalPages = totalPages;
        pageDto.data = new ArrayList<>(data);
        return pageDto;
    }

    public void calculateMetaData(int perPage,int page) {

        this.setTotal(this.data.size());
        if(perPage > this.getTotal() || perPage<=0) perPage = this.data.size();
        if(page > this.getTotalPages() || page<=0) page = 1;

        int start = (page - 1) * perPage;
        int end = start + perPage;

        if(end > this.data.size() || end<=0) end = this.data.size();
        if(start > this.data.size() || start <=0) start = 0;

        this.setPerPage(perPage);
        this.setPage(page);

        this.setTotalPages((int) this.getTotal()/this.getPerPage());
        this.setData(this.data.subList(start, end));

    }

}
