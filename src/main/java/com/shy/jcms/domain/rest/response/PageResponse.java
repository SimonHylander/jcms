package com.shy.jcms.domain.rest.response;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PageResponse implements Serializable {
    private Long id;

    private String title;

    private Integer type;

    private Integer status;

    private String content;

    private Date created;

    private Date updated;

    private String created_by;

    private String updated_by;

    /*private PageResponse(PageResponse pageResponse){
        this.id = pageResponse.id;
        this.type = pageResponse.id;
        this.status = pageResponse.status;
        this.content = pageResponse.content;
        this.created = pageResponse.created;
        this.updated = pageResponse.updated;
        ti
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) { this.updated_by = updated_by; }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageResponse page = (PageResponse) o;
        if(page.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, page.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Page{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
            ", content='" + content + "'" +
            ", created='" + created + "'" +
            ", updated='" + updated + "'" +
            ", created_by='" + created_by + "'" +
            ", updated_by='" + updated_by + "'" +
            '}';
    }
}
