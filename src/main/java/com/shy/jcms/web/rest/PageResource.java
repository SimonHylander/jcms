package com.shy.jcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shy.jcms.domain.Page;
import com.shy.jcms.domain.User;
import com.shy.jcms.domain.rest.response.PageResponse;
import com.shy.jcms.repository.PageRepository;
import com.shy.jcms.repository.UserRepository;
import com.shy.jcms.web.rest.util.HeaderUtil;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Page.
 */
@RestController
@RequestMapping("/api")
public class PageResource {

    private final Logger log = LoggerFactory.getLogger(PageResource.class);

    @Inject
    private PageRepository pageRepository;

    @Inject private UserRepository userRepository;

    /**
     * POST  /pages -> Create a new page.
     */
    @RequestMapping(value = "/pages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Page> createPage(@RequestBody Page page) throws URISyntaxException {
        log.debug("REST request to save Page : {}", page);

        page.setCreated(new Date());
        page.setUpdated(new Date());

        if (page.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("page", "idexists", "A new page cannot already have an ID")).body(null);
        }
        Page result = pageRepository.save(page);
        return ResponseEntity.created(new URI("/api/pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("page", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pages -> Updates an existing page.
     */
    @RequestMapping(value = "/pages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Page> updatePage(@RequestBody Page page) throws URISyntaxException {
        log.debug("REST request to update Page : {}", page);
        if (page.getId() == null) {
            return createPage(page);
        }
        Page result = pageRepository.save(page);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("page", page.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pages -> get all the pages.
     */
    @RequestMapping(value = "/pages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PageResponse> getAllPages() {
        log.debug("REST request to get all Pages");
        List<PageResponse> response = new ArrayList<>();
        pageRepository.findAll().forEach(page -> {
            User createdUser = userRepository.findOne(page.getCreated_by());
            User updatedUser = userRepository.findOne(page.getUpdated_by());

            PageResponse pageResponse = new PageResponse();
            pageResponse.setId(page.getId());
            pageResponse.setTitle(page.getTitle());
            pageResponse.setType(page.getType());
            pageResponse.setStatus(page.getStatus());
            pageResponse.setContent(page.getContent());
            pageResponse.setCreated(page.getCreated());
            pageResponse.setUpdated(page.getUpdated());
            pageResponse.setCreated_by(createdUser.getFirstName() +" "+ createdUser.getLastName());
            pageResponse.setUpdated_by(updatedUser.getFirstName() +" "+ updatedUser.getLastName());

            response.add(pageResponse);
        });
        return response;
    }

    /**
     * GET  /pages/:id -> get the "id" page.
     */
    @RequestMapping(value = "/pages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Page> getPage(@PathVariable Long id) {
        log.debug("REST request to get Page : {}", id);
        Page page = pageRepository.findOne(id);
        return Optional.ofNullable(page)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pages/:id -> delete the "id" page.
     */
    @RequestMapping(value = "/pages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePage(@PathVariable Long id) {
        log.debug("REST request to delete Page : {}", id);
        pageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("page", id.toString())).build();
    }
}
