package com.shy.jcms.repository;

import com.shy.jcms.domain.Config;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Config entity.
 */
public interface ConfigRepository extends JpaRepository<Config,Long> {

}
