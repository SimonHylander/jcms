package com.shy.jcms.domain;


import javax.persistence.*;
import java.io.Serializable;
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
    private String created;

    @Column(name = "updated")
    private String updated;

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
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
