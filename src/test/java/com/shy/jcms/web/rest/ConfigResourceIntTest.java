package com.shy.jcms.web.rest;

import com.shy.jcms.Application;
import com.shy.jcms.domain.Config;
import com.shy.jcms.repository.ConfigRepository;

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
 * Test class for the ConfigResource REST controller.
 *
 * @see ConfigResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ConfigResourceIntTest {

    private static final String DEFAULT_SITE_NAME = "AAAAA";
    private static final String UPDATED_SITE_NAME = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private ConfigRepository configRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restConfigMockMvc;

    private Config config;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConfigResource configResource = new ConfigResource();
        ReflectionTestUtils.setField(configResource, "configRepository", configRepository);
        this.restConfigMockMvc = MockMvcBuilders.standaloneSetup(configResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        config = new Config();
        config.setSite_name(DEFAULT_SITE_NAME);
        config.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createConfig() throws Exception {
        int databaseSizeBeforeCreate = configRepository.findAll().size();

        // Create the Config

        restConfigMockMvc.perform(post("/api/configs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config)))
                .andExpect(status().isCreated());

        // Validate the Config in the database
        List<Config> configs = configRepository.findAll();
        assertThat(configs).hasSize(databaseSizeBeforeCreate + 1);
        Config testConfig = configs.get(configs.size() - 1);
        assertThat(testConfig.getSite_name()).isEqualTo(DEFAULT_SITE_NAME);
        assertThat(testConfig.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllConfigs() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get all the configs
        restConfigMockMvc.perform(get("/api/configs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(config.getId().intValue())))
                .andExpect(jsonPath("$.[*].site_name").value(hasItem(DEFAULT_SITE_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getConfig() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

        // Get the config
        restConfigMockMvc.perform(get("/api/configs/{id}", config.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(config.getId().intValue()))
            .andExpect(jsonPath("$.site_name").value(DEFAULT_SITE_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConfig() throws Exception {
        // Get the config
        restConfigMockMvc.perform(get("/api/configs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfig() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

		int databaseSizeBeforeUpdate = configRepository.findAll().size();

        // Update the config
        config.setSite_name(UPDATED_SITE_NAME);
        config.setEmail(UPDATED_EMAIL);

        restConfigMockMvc.perform(put("/api/configs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config)))
                .andExpect(status().isOk());

        // Validate the Config in the database
        List<Config> configs = configRepository.findAll();
        assertThat(configs).hasSize(databaseSizeBeforeUpdate);
        Config testConfig = configs.get(configs.size() - 1);
        assertThat(testConfig.getSite_name()).isEqualTo(UPDATED_SITE_NAME);
        assertThat(testConfig.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteConfig() throws Exception {
        // Initialize the database
        configRepository.saveAndFlush(config);

		int databaseSizeBeforeDelete = configRepository.findAll().size();

        // Get the config
        restConfigMockMvc.perform(delete("/api/configs/{id}", config.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Config> configs = configRepository.findAll();
        assertThat(configs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
