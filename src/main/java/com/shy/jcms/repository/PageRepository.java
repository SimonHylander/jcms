package com.shy.jcms.repository;

import com.shy.jcms.domain.Page;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Page entity.
 */
public interface PageRepository extends JpaRepository<Page,Long> {

}
