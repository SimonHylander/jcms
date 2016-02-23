package com.shy.jcms.web.rest;

import com.shy.jcms.Application;
import com.shy.jcms.domain.Page;
import com.shy.jcms.repository.PageRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PageResource REST controller.
 *
 * @see PageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PageResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final String DEFAULT_CONTENT = "AAAAA";
    private static final String UPDATED_CONTENT = "BBBBB";

//    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
//    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final Date DEFAULT_CREATED = new Date();
    private static final Date UPDATED_CREATED = new Date(System.currentTimeMillis());
    private static final Date DEFAULT_UPDATED = new Date();
    private static final Date UPDATED_UPDATED = new Date(System.currentTimeMillis());


    //private static final LocalDate DEFAULT_UPDATED = LocalDate.ofEpochDay(0L);
    //private static final LocalDate UPDATED_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATED_BY = new Long(1);
    private static final Long UPDATED_CREATED_BY = new Long(1);
    private static final Long DEFAULT_UPDATED_BY = new Long(1);
    private static final Long UPDATED_UPDATED_BY = new Long(1);

    @Inject
    private PageRepository pageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPageMockMvc;

    private Page page;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PageResource pageResource = new PageResource();
        ReflectionTestUtils.setField(pageResource, "pageRepository", pageRepository);
        this.restPageMockMvc = MockMvcBuilders.standaloneSetup(pageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        page = new Page();
        page.setTitle(DEFAULT_TITLE);
        page.setType(DEFAULT_TYPE);
        page.setStatus(DEFAULT_STATUS);
        page.setContent(DEFAULT_CONTENT);
        page.setCreated(DEFAULT_CREATED);
        page.setUpdated(DEFAULT_UPDATED);
        page.setCreated_by(DEFAULT_CREATED_BY);
        page.setUpdated_by(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createPage() throws Exception {
        int databaseSizeBeforeCreate = pageRepository.findAll().size();

        // Create the Page

        restPageMockMvc.perform(post("/api/pages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(page)))
                .andExpect(status().isCreated());

        // Validate the Page in the database
        List<Page> pages = pageRepository.findAll();
        assertThat(pages).hasSize(databaseSizeBeforeCreate + 1);
        Page testPage = pages.get(pages.size() - 1);
        assertThat(testPage.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPage.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPage.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPage.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testPage.getUpdated()).isEqualTo(DEFAULT_UPDATED);
        assertThat(testPage.getCreated_by()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPage.getUpdated_by()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void getAllPages() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

        // Get all the pages
        restPageMockMvc.perform(get("/api/pages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(page.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
                .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
                .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.toString())))
                .andExpect(jsonPath("$.[*].created_by").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].updated_by").value(hasItem(DEFAULT_UPDATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getPage() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

        // Get the page
        restPageMockMvc.perform(get("/api/pages/{id}", page.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(page.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.toString()))
            .andExpect(jsonPath("$.created_by").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.updated_by").value(DEFAULT_UPDATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPage() throws Exception {
        // Get the page
        restPageMockMvc.perform(get("/api/pages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePage() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

		int databaseSizeBeforeUpdate = pageRepository.findAll().size();

        // Update the page
        page.setTitle(UPDATED_TITLE);
        page.setType(UPDATED_TYPE);
        page.setStatus(UPDATED_STATUS);
        page.setContent(UPDATED_CONTENT);
        page.setCreated(UPDATED_CREATED);
        page.setUpdated(UPDATED_UPDATED);
        page.setCreated_by(UPDATED_CREATED_BY);
        page.setUpdated_by(UPDATED_UPDATED_BY);

        restPageMockMvc.perform(put("/api/pages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(page)))
                .andExpect(status().isOk());

        // Validate the Page in the database
        List<Page> pages = pageRepository.findAll();
        assertThat(pages).hasSize(databaseSizeBeforeUpdate);
        Page testPage = pages.get(pages.size() - 1);
        assertThat(testPage.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPage.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPage.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPage.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testPage.getUpdated()).isEqualTo(UPDATED_UPDATED);
        assertThat(testPage.getCreated_by()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPage.getUpdated_by()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void deletePage() throws Exception {
        // Initialize the database
        pageRepository.saveAndFlush(page);

		int databaseSizeBeforeDelete = pageRepository.findAll().size();

        // Get the page
        restPageMockMvc.perform(delete("/api/pages/{id}", page.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Page> pages = pageRepository.findAll();
        assertThat(pages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
