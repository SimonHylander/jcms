package com.shy.jcms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shy.jcms.domain.Pages;
import com.shy.jcms.repository.PagesRepository;
import com.shy.jcms.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pages.
 */
@RestController
@RequestMapping("/api")
public class PagesResource {

    private final Logger log = LoggerFactory.getLogger(PagesResource.class);

    @Inject
    private PagesRepository pagesRepository;

    /**
     * POST  /pages -> Create a new pages.
     */
    @RequestMapping(value = "/pages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pages> createPages(@RequestBody Pages pages) throws URISyntaxException {

        pages.setCreated(new Date());
        pages.setUpdated(new Date());

        System.out.println(pages);

        log.debug("REST request to save Pages : {}", pages);
        if (pages.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pages", "idexists", "A new pages cannot already have an ID")).body(null);
        }
        Pages result = pagesRepository.save(pages);
        return ResponseEntity.created(new URI("/api/pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pages", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pages -> Updates an existing pages.
     */
    @RequestMapping(value = "/pages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pages> updatePages(@RequestBody Pages pages) throws URISyntaxException {
        log.debug("REST request to update Pages : {}", pages);
        if (pages.getId() == null) {
            return createPages(pages);
        }
        Pages result = pagesRepository.save(pages);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pages", pages.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pages -> get all the pages.
     */
    @RequestMapping(value = "/pages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pages> getAllPages() {
        log.debug("REST request to get all Pages");
        return pagesRepository.findAll();
            }

    /**
     * GET  /pages/:id -> get the "id" pages.
     */
    @RequestMapping(value = "/pages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pages> getPages(@PathVariable Long id) {
        log.debug("REST request to get Pages : {}", id);
        Pages pages = pagesRepository.findOne(id);
        return Optional.ofNullable(pages)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pages/:id -> delete the "id" pages.
     */
    @RequestMapping(value = "/pages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePages(@PathVariable Long id) {
        log.debug("REST request to delete Pages : {}", id);
        pagesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pages", id.toString())).build();
    }
}
