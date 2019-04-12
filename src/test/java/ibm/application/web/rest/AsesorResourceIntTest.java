package ibm.application.web.rest;

import ibm.application.IbmApp;

import ibm.application.domain.Asesor;
import ibm.application.repository.AsesorRepository;
import ibm.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static ibm.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AsesorResource REST controller.
 *
 * @see AsesorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IbmApp.class)
public class AsesorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDAD = "BBBBBBBBBB";

    @Autowired
    private AsesorRepository asesorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAsesorMockMvc;

    private Asesor asesor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AsesorResource asesorResource = new AsesorResource(asesorRepository);
        this.restAsesorMockMvc = MockMvcBuilders.standaloneSetup(asesorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asesor createEntity(EntityManager em) {
        Asesor asesor = new Asesor()
            .nombre(DEFAULT_NOMBRE)
            .especialidad(DEFAULT_ESPECIALIDAD);
        return asesor;
    }

    @Before
    public void initTest() {
        asesor = createEntity(em);
    }

    @Test
    @Transactional
    public void createAsesor() throws Exception {
        int databaseSizeBeforeCreate = asesorRepository.findAll().size();

        // Create the Asesor
        restAsesorMockMvc.perform(post("/api/asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asesor)))
            .andExpect(status().isCreated());

        // Validate the Asesor in the database
        List<Asesor> asesorList = asesorRepository.findAll();
        assertThat(asesorList).hasSize(databaseSizeBeforeCreate + 1);
        Asesor testAsesor = asesorList.get(asesorList.size() - 1);
        assertThat(testAsesor.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAsesor.getEspecialidad()).isEqualTo(DEFAULT_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void createAsesorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = asesorRepository.findAll().size();

        // Create the Asesor with an existing ID
        asesor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsesorMockMvc.perform(post("/api/asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asesor)))
            .andExpect(status().isBadRequest());

        // Validate the Asesor in the database
        List<Asesor> asesorList = asesorRepository.findAll();
        assertThat(asesorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = asesorRepository.findAll().size();
        // set the field null
        asesor.setNombre(null);

        // Create the Asesor, which fails.

        restAsesorMockMvc.perform(post("/api/asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asesor)))
            .andExpect(status().isBadRequest());

        List<Asesor> asesorList = asesorRepository.findAll();
        assertThat(asesorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEspecialidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = asesorRepository.findAll().size();
        // set the field null
        asesor.setEspecialidad(null);

        // Create the Asesor, which fails.

        restAsesorMockMvc.perform(post("/api/asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asesor)))
            .andExpect(status().isBadRequest());

        List<Asesor> asesorList = asesorRepository.findAll();
        assertThat(asesorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAsesors() throws Exception {
        // Initialize the database
        asesorRepository.saveAndFlush(asesor);

        // Get all the asesorList
        restAsesorMockMvc.perform(get("/api/asesors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asesor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].especialidad").value(hasItem(DEFAULT_ESPECIALIDAD.toString())));
    }
    
    @Test
    @Transactional
    public void getAsesor() throws Exception {
        // Initialize the database
        asesorRepository.saveAndFlush(asesor);

        // Get the asesor
        restAsesorMockMvc.perform(get("/api/asesors/{id}", asesor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asesor.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.especialidad").value(DEFAULT_ESPECIALIDAD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAsesor() throws Exception {
        // Get the asesor
        restAsesorMockMvc.perform(get("/api/asesors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsesor() throws Exception {
        // Initialize the database
        asesorRepository.saveAndFlush(asesor);

        int databaseSizeBeforeUpdate = asesorRepository.findAll().size();

        // Update the asesor
        Asesor updatedAsesor = asesorRepository.findById(asesor.getId()).get();
        // Disconnect from session so that the updates on updatedAsesor are not directly saved in db
        em.detach(updatedAsesor);
        updatedAsesor
            .nombre(UPDATED_NOMBRE)
            .especialidad(UPDATED_ESPECIALIDAD);

        restAsesorMockMvc.perform(put("/api/asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsesor)))
            .andExpect(status().isOk());

        // Validate the Asesor in the database
        List<Asesor> asesorList = asesorRepository.findAll();
        assertThat(asesorList).hasSize(databaseSizeBeforeUpdate);
        Asesor testAsesor = asesorList.get(asesorList.size() - 1);
        assertThat(testAsesor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAsesor.getEspecialidad()).isEqualTo(UPDATED_ESPECIALIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingAsesor() throws Exception {
        int databaseSizeBeforeUpdate = asesorRepository.findAll().size();

        // Create the Asesor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsesorMockMvc.perform(put("/api/asesors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asesor)))
            .andExpect(status().isBadRequest());

        // Validate the Asesor in the database
        List<Asesor> asesorList = asesorRepository.findAll();
        assertThat(asesorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAsesor() throws Exception {
        // Initialize the database
        asesorRepository.saveAndFlush(asesor);

        int databaseSizeBeforeDelete = asesorRepository.findAll().size();

        // Delete the asesor
        restAsesorMockMvc.perform(delete("/api/asesors/{id}", asesor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Asesor> asesorList = asesorRepository.findAll();
        assertThat(asesorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asesor.class);
        Asesor asesor1 = new Asesor();
        asesor1.setId(1L);
        Asesor asesor2 = new Asesor();
        asesor2.setId(asesor1.getId());
        assertThat(asesor1).isEqualTo(asesor2);
        asesor2.setId(2L);
        assertThat(asesor1).isNotEqualTo(asesor2);
        asesor1.setId(null);
        assertThat(asesor1).isNotEqualTo(asesor2);
    }
}
