package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.RfcrfApp;
import sat.gob.mx.agsc.config.TestSecurityConfiguration;
import sat.gob.mx.agsc.domain.TcEjerc;
import sat.gob.mx.agsc.repository.TcEjercRepository;
import sat.gob.mx.agsc.service.TcEjercService;
import sat.gob.mx.agsc.service.dto.TcEjercDTO;
import sat.gob.mx.agsc.service.mapper.TcEjercMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TcEjercResource} REST controller.
 */
@SpringBootTest(classes = { RfcrfApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TcEjercResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private TcEjercRepository tcEjercRepository;

    @Autowired
    private TcEjercMapper tcEjercMapper;

    @Autowired
    private TcEjercService tcEjercService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTcEjercMockMvc;

    private TcEjerc tcEjerc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcEjerc createEntity(EntityManager em) {
        TcEjerc tcEjerc = new TcEjerc()
            .clave(DEFAULT_CLAVE)
            .valor(DEFAULT_VALOR)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .usuario(DEFAULT_USUARIO);
        return tcEjerc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcEjerc createUpdatedEntity(EntityManager em) {
        TcEjerc tcEjerc = new TcEjerc()
            .clave(UPDATED_CLAVE)
            .valor(UPDATED_VALOR)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .usuario(UPDATED_USUARIO);
        return tcEjerc;
    }

    @BeforeEach
    public void initTest() {
        tcEjerc = createEntity(em);
    }

    @Test
    @Transactional
    public void createTcEjerc() throws Exception {
        int databaseSizeBeforeCreate = tcEjercRepository.findAll().size();
        // Create the TcEjerc
        TcEjercDTO tcEjercDTO = tcEjercMapper.toDto(tcEjerc);
        restTcEjercMockMvc.perform(post("/api/tc-ejercs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcEjercDTO)))
            .andExpect(status().isCreated());

        // Validate the TcEjerc in the database
        List<TcEjerc> tcEjercList = tcEjercRepository.findAll();
        assertThat(tcEjercList).hasSize(databaseSizeBeforeCreate + 1);
        TcEjerc testTcEjerc = tcEjercList.get(tcEjercList.size() - 1);
        assertThat(testTcEjerc.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTcEjerc.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testTcEjerc.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testTcEjerc.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testTcEjerc.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createTcEjercWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tcEjercRepository.findAll().size();

        // Create the TcEjerc with an existing ID
        tcEjerc.setId(1L);
        TcEjercDTO tcEjercDTO = tcEjercMapper.toDto(tcEjerc);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTcEjercMockMvc.perform(post("/api/tc-ejercs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcEjercDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcEjerc in the database
        List<TcEjerc> tcEjercList = tcEjercRepository.findAll();
        assertThat(tcEjercList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcEjercRepository.findAll().size();
        // set the field null
        tcEjerc.setClave(null);

        // Create the TcEjerc, which fails.
        TcEjercDTO tcEjercDTO = tcEjercMapper.toDto(tcEjerc);


        restTcEjercMockMvc.perform(post("/api/tc-ejercs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcEjercDTO)))
            .andExpect(status().isBadRequest());

        List<TcEjerc> tcEjercList = tcEjercRepository.findAll();
        assertThat(tcEjercList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcEjercRepository.findAll().size();
        // set the field null
        tcEjerc.setValor(null);

        // Create the TcEjerc, which fails.
        TcEjercDTO tcEjercDTO = tcEjercMapper.toDto(tcEjerc);


        restTcEjercMockMvc.perform(post("/api/tc-ejercs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcEjercDTO)))
            .andExpect(status().isBadRequest());

        List<TcEjerc> tcEjercList = tcEjercRepository.findAll();
        assertThat(tcEjercList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTcEjercs() throws Exception {
        // Initialize the database
        tcEjercRepository.saveAndFlush(tcEjerc);

        // Get all the tcEjercList
        restTcEjercMockMvc.perform(get("/api/tc-ejercs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tcEjerc.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }
    
    @Test
    @Transactional
    public void getTcEjerc() throws Exception {
        // Initialize the database
        tcEjercRepository.saveAndFlush(tcEjerc);

        // Get the tcEjerc
        restTcEjercMockMvc.perform(get("/api/tc-ejercs/{id}", tcEjerc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tcEjerc.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }
    @Test
    @Transactional
    public void getNonExistingTcEjerc() throws Exception {
        // Get the tcEjerc
        restTcEjercMockMvc.perform(get("/api/tc-ejercs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTcEjerc() throws Exception {
        // Initialize the database
        tcEjercRepository.saveAndFlush(tcEjerc);

        int databaseSizeBeforeUpdate = tcEjercRepository.findAll().size();

        // Update the tcEjerc
        TcEjerc updatedTcEjerc = tcEjercRepository.findById(tcEjerc.getId()).get();
        // Disconnect from session so that the updates on updatedTcEjerc are not directly saved in db
        em.detach(updatedTcEjerc);
        updatedTcEjerc
            .clave(UPDATED_CLAVE)
            .valor(UPDATED_VALOR)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .usuario(UPDATED_USUARIO);
        TcEjercDTO tcEjercDTO = tcEjercMapper.toDto(updatedTcEjerc);

        restTcEjercMockMvc.perform(put("/api/tc-ejercs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcEjercDTO)))
            .andExpect(status().isOk());

        // Validate the TcEjerc in the database
        List<TcEjerc> tcEjercList = tcEjercRepository.findAll();
        assertThat(tcEjercList).hasSize(databaseSizeBeforeUpdate);
        TcEjerc testTcEjerc = tcEjercList.get(tcEjercList.size() - 1);
        assertThat(testTcEjerc.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTcEjerc.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testTcEjerc.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testTcEjerc.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testTcEjerc.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTcEjerc() throws Exception {
        int databaseSizeBeforeUpdate = tcEjercRepository.findAll().size();

        // Create the TcEjerc
        TcEjercDTO tcEjercDTO = tcEjercMapper.toDto(tcEjerc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTcEjercMockMvc.perform(put("/api/tc-ejercs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcEjercDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcEjerc in the database
        List<TcEjerc> tcEjercList = tcEjercRepository.findAll();
        assertThat(tcEjercList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTcEjerc() throws Exception {
        // Initialize the database
        tcEjercRepository.saveAndFlush(tcEjerc);

        int databaseSizeBeforeDelete = tcEjercRepository.findAll().size();

        // Delete the tcEjerc
        restTcEjercMockMvc.perform(delete("/api/tc-ejercs/{id}", tcEjerc.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TcEjerc> tcEjercList = tcEjercRepository.findAll();
        assertThat(tcEjercList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
