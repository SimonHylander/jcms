package com.shy.jcms.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A Pages.
 */
@Entity
@Table(name = "pages")
public class Pages implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private Float type;

    @Column(name = "content")
    private String content;

    @Column(name = "author")
    private String author;

    @Column(name = "status")
    private String status;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

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

    public Float getType() {
        return type;
    }

    public void setType(Float type) {
        this.type = type;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCreatedBy(){ return createdBy; }

    public void setCreateBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy(){ return createdBy; }

    public void setUpdatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pages pages = (Pages) o;
        return Objects.equals(id, pages.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pages{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", type='" + type + "'" +
            ", author='" + author + "'" +
            ", status='" + status + "'" +
            ", created='" + created + "'" +
            ", updated='" + updated + "'" +
            '}';
    }
}
