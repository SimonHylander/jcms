package com.shy.jcms.domain;

import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Date;

/**
 * A Page.
 */
@Entity
@Table(name = "page")
public class Page implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "created_by")
    private Long created_by;

    @Column(name = "updated_by")
    private Long updated_by;

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

    public Long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }

    public Long getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(Long updated_by) { this.updated_by = updated_by; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Page page = (Page) o;
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
