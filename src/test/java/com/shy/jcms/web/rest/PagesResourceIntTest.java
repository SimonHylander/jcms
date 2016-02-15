package com.shy.jcms.web.rest;

import com.shy.jcms.Application;
import com.shy.jcms.domain.Pages;
import com.shy.jcms.repository.PagesRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PagesResource REST controller.
 *
 * @see PagesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PagesResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final Float DEFAULT_TYPE = 1F;
    private static final Float UPDATED_TYPE = 2F;
    private static final String DEFAULT_AUTHOR = "AAAAA";
    private static final String UPDATED_AUTHOR = "BBBBB";
    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";
    private static final String DEFAULT_CREATED = "AAAAA";
    private static final String UPDATED_CREATED = "BBBBB";
    private static final String DEFAULT_UPDATED = "AAAAA";
    private static final String UPDATED_UPDATED = "BBBBB";

    @Inject
    private PagesRepository pagesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPagesMockMvc;

    private Pages pages;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PagesResource pagesResource = new PagesResource();
        ReflectionTestUtils.setField(pagesResource, "pagesRepository", pagesRepository);
        this.restPagesMockMvc = MockMvcBuilders.standaloneSetup(pagesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pages = new Pages();
        pages.setTitle(DEFAULT_TITLE);
        pages.setType(DEFAULT_TYPE);
        pages.setAuthor(DEFAULT_AUTHOR);
        pages.setStatus(DEFAULT_STATUS);
        pages.setCreated(DEFAULT_CREATED);
        pages.setUpdated(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void createPages() throws Exception {
        int databaseSizeBeforeCreate = pagesRepository.findAll().size();

        // Create the Pages

        restPagesMockMvc.perform(post("/api/pagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pages)))
                .andExpect(status().isCreated());

        // Validate the Pages in the database
        List<Pages> pagess = pagesRepository.findAll();
        assertThat(pagess).hasSize(databaseSizeBeforeCreate + 1);
        Pages testPages = pagess.get(pagess.size() - 1);
        assertThat(testPages.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPages.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPages.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testPages.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPages.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPages.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    @Transactional
    public void getAllPagess() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        // Get all the pagess
        restPagesMockMvc.perform(get("/api/pagess?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pages.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.doubleValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
                .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())));
    }

    @Test
    @Transactional
    public void getPages() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

        // Get the pages
        restPagesMockMvc.perform(get("/api/pagess/{id}", pages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pages.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.doubleValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPages() throws Exception {
        // Get the pages
        restPagesMockMvc.perform(get("/api/pagess/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePages() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

		int databaseSizeBeforeUpdate = pagesRepository.findAll().size();

        // Update the pages
        pages.setTitle(UPDATED_TITLE);
        pages.setType(UPDATED_TYPE);
        pages.setAuthor(UPDATED_AUTHOR);
        pages.setStatus(UPDATED_STATUS);
        pages.setCreated(UPDATED_CREATED);
        pages.setUpdated(UPDATED_UPDATED);

        restPagesMockMvc.perform(put("/api/pagess")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pages)))
                .andExpect(status().isOk());

        // Validate the Pages in the database
        List<Pages> pagess = pagesRepository.findAll();
        assertThat(pagess).hasSize(databaseSizeBeforeUpdate);
        Pages testPages = pagess.get(pagess.size() - 1);
        assertThat(testPages.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPages.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPages.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testPages.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPages.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPages.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    @Transactional
    public void deletePages() throws Exception {
        // Initialize the database
        pagesRepository.saveAndFlush(pages);

		int databaseSizeBeforeDelete = pagesRepository.findAll().size();

        // Get the pages
        restPagesMockMvc.perform(delete("/api/pagess/{id}", pages.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pages> pagess = pagesRepository.findAll();
        assertThat(pagess).hasSize(databaseSizeBeforeDelete - 1);
    }
}
