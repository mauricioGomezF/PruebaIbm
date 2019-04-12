package ibm.application.web.rest;

import ibm.application.IbmApp;

import ibm.application.domain.Consumo;
import ibm.application.repository.ConsumoRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static ibm.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConsumoResource REST controller.
 *
 * @see ConsumoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IbmApp.class)
public class ConsumoResourceIntTest {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTO = 999999999999D;
    private static final Double UPDATED_MONTO = 999999999998D;

    @Autowired
    private ConsumoRepository consumoRepository;

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

    private MockMvc restConsumoMockMvc;

    private Consumo consumo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsumoResource consumoResource = new ConsumoResource(consumoRepository);
        this.restConsumoMockMvc = MockMvcBuilders.standaloneSetup(consumoResource)
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
    public static Consumo createEntity(EntityManager em) {
        Consumo consumo = new Consumo()
            .fecha(DEFAULT_FECHA)
            .descripcion(DEFAULT_DESCRIPCION)
            .monto(DEFAULT_MONTO);
        return consumo;
    }

    @Before
    public void initTest() {
        consumo = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsumo() throws Exception {
        int databaseSizeBeforeCreate = consumoRepository.findAll().size();

        // Create the Consumo
        restConsumoMockMvc.perform(post("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumo)))
            .andExpect(status().isCreated());

        // Validate the Consumo in the database
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeCreate + 1);
        Consumo testConsumo = consumoList.get(consumoList.size() - 1);
        assertThat(testConsumo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testConsumo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testConsumo.getMonto()).isEqualTo(DEFAULT_MONTO);
    }

    @Test
    @Transactional
    public void createConsumoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consumoRepository.findAll().size();

        // Create the Consumo with an existing ID
        consumo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumoMockMvc.perform(post("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumo)))
            .andExpect(status().isBadRequest());

        // Validate the Consumo in the database
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = consumoRepository.findAll().size();
        // set the field null
        consumo.setFecha(null);

        // Create the Consumo, which fails.

        restConsumoMockMvc.perform(post("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumo)))
            .andExpect(status().isBadRequest());

        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = consumoRepository.findAll().size();
        // set the field null
        consumo.setDescripcion(null);

        // Create the Consumo, which fails.

        restConsumoMockMvc.perform(post("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumo)))
            .andExpect(status().isBadRequest());

        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsumos() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get all the consumoList
        restConsumoMockMvc.perform(get("/api/consumos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getConsumo() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        // Get the consumo
        restConsumoMockMvc.perform(get("/api/consumos/{id}", consumo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consumo.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConsumo() throws Exception {
        // Get the consumo
        restConsumoMockMvc.perform(get("/api/consumos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsumo() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        int databaseSizeBeforeUpdate = consumoRepository.findAll().size();

        // Update the consumo
        Consumo updatedConsumo = consumoRepository.findById(consumo.getId()).get();
        // Disconnect from session so that the updates on updatedConsumo are not directly saved in db
        em.detach(updatedConsumo);
        updatedConsumo
            .fecha(UPDATED_FECHA)
            .descripcion(UPDATED_DESCRIPCION)
            .monto(UPDATED_MONTO);

        restConsumoMockMvc.perform(put("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsumo)))
            .andExpect(status().isOk());

        // Validate the Consumo in the database
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeUpdate);
        Consumo testConsumo = consumoList.get(consumoList.size() - 1);
        assertThat(testConsumo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testConsumo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testConsumo.getMonto()).isEqualTo(UPDATED_MONTO);
    }

    @Test
    @Transactional
    public void updateNonExistingConsumo() throws Exception {
        int databaseSizeBeforeUpdate = consumoRepository.findAll().size();

        // Create the Consumo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsumoMockMvc.perform(put("/api/consumos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumo)))
            .andExpect(status().isBadRequest());

        // Validate the Consumo in the database
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsumo() throws Exception {
        // Initialize the database
        consumoRepository.saveAndFlush(consumo);

        int databaseSizeBeforeDelete = consumoRepository.findAll().size();

        // Delete the consumo
        restConsumoMockMvc.perform(delete("/api/consumos/{id}", consumo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Consumo> consumoList = consumoRepository.findAll();
        assertThat(consumoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consumo.class);
        Consumo consumo1 = new Consumo();
        consumo1.setId(1L);
        Consumo consumo2 = new Consumo();
        consumo2.setId(consumo1.getId());
        assertThat(consumo1).isEqualTo(consumo2);
        consumo2.setId(2L);
        assertThat(consumo1).isNotEqualTo(consumo2);
        consumo1.setId(null);
        assertThat(consumo1).isNotEqualTo(consumo2);
    }
}
