package com.shy.jcms.repository;

import com.shy.jcms.domain.Pages;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pages entity.
 */
public interface PagesRepository extends JpaRepository<Pages,Long> {

}
