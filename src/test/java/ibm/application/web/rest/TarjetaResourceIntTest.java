package ibm.application.web.rest;

import ibm.application.IbmApp;

import ibm.application.domain.Tarjeta;
import ibm.application.repository.TarjetaRepository;
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
 * Test class for the TarjetaResource REST controller.
 *
 * @see TarjetaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IbmApp.class)
public class TarjetaResourceIntTest {

    private static final String DEFAULT_NUMERO_TARJETA = "2957 9079 5209 9754";
    private static final String UPDATED_NUMERO_TARJETA = "4988 3725 0192 0136";

    private static final String DEFAULT_CCV = "3871";
    private static final String UPDATED_CCV = "001";

    private static final String DEFAULT_TIPO_TARJETA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_TARJETA = "BBBBBBBBBB";

    @Autowired
    private TarjetaRepository tarjetaRepository;

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

    private MockMvc restTarjetaMockMvc;

    private Tarjeta tarjeta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TarjetaResource tarjetaResource = new TarjetaResource(tarjetaRepository);
        this.restTarjetaMockMvc = MockMvcBuilders.standaloneSetup(tarjetaResource)
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
    public static Tarjeta createEntity(EntityManager em) {
        Tarjeta tarjeta = new Tarjeta()
            .numeroTarjeta(DEFAULT_NUMERO_TARJETA)
            .ccv(DEFAULT_CCV)
            .tipoTarjeta(DEFAULT_TIPO_TARJETA);
        return tarjeta;
    }

    @Before
    public void initTest() {
        tarjeta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarjeta() throws Exception {
        int databaseSizeBeforeCreate = tarjetaRepository.findAll().size();

        // Create the Tarjeta
        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isCreated());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeCreate + 1);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getNumeroTarjeta()).isEqualTo(DEFAULT_NUMERO_TARJETA);
        assertThat(testTarjeta.getCcv()).isEqualTo(DEFAULT_CCV);
        assertThat(testTarjeta.getTipoTarjeta()).isEqualTo(DEFAULT_TIPO_TARJETA);
    }

    @Test
    @Transactional
    public void createTarjetaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarjetaRepository.findAll().size();

        // Create the Tarjeta with an existing ID
        tarjeta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isBadRequest());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroTarjetaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setNumeroTarjeta(null);

        // Create the Tarjeta, which fails.

        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCcvIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setCcv(null);

        // Create the Tarjeta, which fails.

        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoTarjetaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarjetaRepository.findAll().size();
        // set the field null
        tarjeta.setTipoTarjeta(null);

        // Create the Tarjeta, which fails.

        restTarjetaMockMvc.perform(post("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isBadRequest());

        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarjetas() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        // Get all the tarjetaList
        restTarjetaMockMvc.perform(get("/api/tarjetas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarjeta.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroTarjeta").value(hasItem(DEFAULT_NUMERO_TARJETA.toString())))
            .andExpect(jsonPath("$.[*].ccv").value(hasItem(DEFAULT_CCV.toString())))
            .andExpect(jsonPath("$.[*].tipoTarjeta").value(hasItem(DEFAULT_TIPO_TARJETA.toString())));
    }
    
    @Test
    @Transactional
    public void getTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        // Get the tarjeta
        restTarjetaMockMvc.perform(get("/api/tarjetas/{id}", tarjeta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarjeta.getId().intValue()))
            .andExpect(jsonPath("$.numeroTarjeta").value(DEFAULT_NUMERO_TARJETA.toString()))
            .andExpect(jsonPath("$.ccv").value(DEFAULT_CCV.toString()))
            .andExpect(jsonPath("$.tipoTarjeta").value(DEFAULT_TIPO_TARJETA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTarjeta() throws Exception {
        // Get the tarjeta
        restTarjetaMockMvc.perform(get("/api/tarjetas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();

        // Update the tarjeta
        Tarjeta updatedTarjeta = tarjetaRepository.findById(tarjeta.getId()).get();
        // Disconnect from session so that the updates on updatedTarjeta are not directly saved in db
        em.detach(updatedTarjeta);
        updatedTarjeta
            .numeroTarjeta(UPDATED_NUMERO_TARJETA)
            .ccv(UPDATED_CCV)
            .tipoTarjeta(UPDATED_TIPO_TARJETA);

        restTarjetaMockMvc.perform(put("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarjeta)))
            .andExpect(status().isOk());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
        Tarjeta testTarjeta = tarjetaList.get(tarjetaList.size() - 1);
        assertThat(testTarjeta.getNumeroTarjeta()).isEqualTo(UPDATED_NUMERO_TARJETA);
        assertThat(testTarjeta.getCcv()).isEqualTo(UPDATED_CCV);
        assertThat(testTarjeta.getTipoTarjeta()).isEqualTo(UPDATED_TIPO_TARJETA);
    }

    @Test
    @Transactional
    public void updateNonExistingTarjeta() throws Exception {
        int databaseSizeBeforeUpdate = tarjetaRepository.findAll().size();

        // Create the Tarjeta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarjetaMockMvc.perform(put("/api/tarjetas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarjeta)))
            .andExpect(status().isBadRequest());

        // Validate the Tarjeta in the database
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarjeta() throws Exception {
        // Initialize the database
        tarjetaRepository.saveAndFlush(tarjeta);

        int databaseSizeBeforeDelete = tarjetaRepository.findAll().size();

        // Delete the tarjeta
        restTarjetaMockMvc.perform(delete("/api/tarjetas/{id}", tarjeta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tarjeta> tarjetaList = tarjetaRepository.findAll();
        assertThat(tarjetaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tarjeta.class);
        Tarjeta tarjeta1 = new Tarjeta();
        tarjeta1.setId(1L);
        Tarjeta tarjeta2 = new Tarjeta();
        tarjeta2.setId(tarjeta1.getId());
        assertThat(tarjeta1).isEqualTo(tarjeta2);
        tarjeta2.setId(2L);
        assertThat(tarjeta1).isNotEqualTo(tarjeta2);
        tarjeta1.setId(null);
        assertThat(tarjeta1).isNotEqualTo(tarjeta2);
    }
}
