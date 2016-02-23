package com.shy.jcms.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Config.
 */
@Entity
@Table(name = "config")
public class Config implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "site_name")
    private String site_name;
    
    @Column(name = "email")
    private String email;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSite_name() {
        return site_name;
    }
    
    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Config config = (Config) o;
        if(config.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, config.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Config{" +
            "id=" + id +
            ", site_name='" + site_name + "'" +
            ", email='" + email + "'" +
            '}';
    }
}
