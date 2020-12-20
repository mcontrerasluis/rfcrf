package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.RfcrfApp;
import sat.gob.mx.agsc.config.TestSecurityConfiguration;
import sat.gob.mx.agsc.domain.TcTipoImp;
import sat.gob.mx.agsc.repository.TcTipoImpRepository;
import sat.gob.mx.agsc.service.TcTipoImpService;
import sat.gob.mx.agsc.service.dto.TcTipoImpDTO;
import sat.gob.mx.agsc.service.mapper.TcTipoImpMapper;

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
 * Integration tests for the {@link TcTipoImpResource} REST controller.
 */
@SpringBootTest(classes = { RfcrfApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TcTipoImpResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private TcTipoImpRepository tcTipoImpRepository;

    @Autowired
    private TcTipoImpMapper tcTipoImpMapper;

    @Autowired
    private TcTipoImpService tcTipoImpService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTcTipoImpMockMvc;

    private TcTipoImp tcTipoImp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcTipoImp createEntity(EntityManager em) {
        TcTipoImp tcTipoImp = new TcTipoImp()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .usuario(DEFAULT_USUARIO);
        return tcTipoImp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcTipoImp createUpdatedEntity(EntityManager em) {
        TcTipoImp tcTipoImp = new TcTipoImp()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .usuario(UPDATED_USUARIO);
        return tcTipoImp;
    }

    @BeforeEach
    public void initTest() {
        tcTipoImp = createEntity(em);
    }

    @Test
    @Transactional
    public void createTcTipoImp() throws Exception {
        int databaseSizeBeforeCreate = tcTipoImpRepository.findAll().size();
        // Create the TcTipoImp
        TcTipoImpDTO tcTipoImpDTO = tcTipoImpMapper.toDto(tcTipoImp);
        restTcTipoImpMockMvc.perform(post("/api/tc-tipo-imps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoImpDTO)))
            .andExpect(status().isCreated());

        // Validate the TcTipoImp in the database
        List<TcTipoImp> tcTipoImpList = tcTipoImpRepository.findAll();
        assertThat(tcTipoImpList).hasSize(databaseSizeBeforeCreate + 1);
        TcTipoImp testTcTipoImp = tcTipoImpList.get(tcTipoImpList.size() - 1);
        assertThat(testTcTipoImp.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTcTipoImp.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTcTipoImp.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testTcTipoImp.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testTcTipoImp.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createTcTipoImpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tcTipoImpRepository.findAll().size();

        // Create the TcTipoImp with an existing ID
        tcTipoImp.setId(1L);
        TcTipoImpDTO tcTipoImpDTO = tcTipoImpMapper.toDto(tcTipoImp);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTcTipoImpMockMvc.perform(post("/api/tc-tipo-imps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoImpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcTipoImp in the database
        List<TcTipoImp> tcTipoImpList = tcTipoImpRepository.findAll();
        assertThat(tcTipoImpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcTipoImpRepository.findAll().size();
        // set the field null
        tcTipoImp.setClave(null);

        // Create the TcTipoImp, which fails.
        TcTipoImpDTO tcTipoImpDTO = tcTipoImpMapper.toDto(tcTipoImp);


        restTcTipoImpMockMvc.perform(post("/api/tc-tipo-imps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoImpDTO)))
            .andExpect(status().isBadRequest());

        List<TcTipoImp> tcTipoImpList = tcTipoImpRepository.findAll();
        assertThat(tcTipoImpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcTipoImpRepository.findAll().size();
        // set the field null
        tcTipoImp.setDescripcion(null);

        // Create the TcTipoImp, which fails.
        TcTipoImpDTO tcTipoImpDTO = tcTipoImpMapper.toDto(tcTipoImp);


        restTcTipoImpMockMvc.perform(post("/api/tc-tipo-imps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoImpDTO)))
            .andExpect(status().isBadRequest());

        List<TcTipoImp> tcTipoImpList = tcTipoImpRepository.findAll();
        assertThat(tcTipoImpList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTcTipoImps() throws Exception {
        // Initialize the database
        tcTipoImpRepository.saveAndFlush(tcTipoImp);

        // Get all the tcTipoImpList
        restTcTipoImpMockMvc.perform(get("/api/tc-tipo-imps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tcTipoImp.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }
    
    @Test
    @Transactional
    public void getTcTipoImp() throws Exception {
        // Initialize the database
        tcTipoImpRepository.saveAndFlush(tcTipoImp);

        // Get the tcTipoImp
        restTcTipoImpMockMvc.perform(get("/api/tc-tipo-imps/{id}", tcTipoImp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tcTipoImp.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }
    @Test
    @Transactional
    public void getNonExistingTcTipoImp() throws Exception {
        // Get the tcTipoImp
        restTcTipoImpMockMvc.perform(get("/api/tc-tipo-imps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTcTipoImp() throws Exception {
        // Initialize the database
        tcTipoImpRepository.saveAndFlush(tcTipoImp);

        int databaseSizeBeforeUpdate = tcTipoImpRepository.findAll().size();

        // Update the tcTipoImp
        TcTipoImp updatedTcTipoImp = tcTipoImpRepository.findById(tcTipoImp.getId()).get();
        // Disconnect from session so that the updates on updatedTcTipoImp are not directly saved in db
        em.detach(updatedTcTipoImp);
        updatedTcTipoImp
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .usuario(UPDATED_USUARIO);
        TcTipoImpDTO tcTipoImpDTO = tcTipoImpMapper.toDto(updatedTcTipoImp);

        restTcTipoImpMockMvc.perform(put("/api/tc-tipo-imps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoImpDTO)))
            .andExpect(status().isOk());

        // Validate the TcTipoImp in the database
        List<TcTipoImp> tcTipoImpList = tcTipoImpRepository.findAll();
        assertThat(tcTipoImpList).hasSize(databaseSizeBeforeUpdate);
        TcTipoImp testTcTipoImp = tcTipoImpList.get(tcTipoImpList.size() - 1);
        assertThat(testTcTipoImp.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTcTipoImp.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTcTipoImp.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testTcTipoImp.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testTcTipoImp.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTcTipoImp() throws Exception {
        int databaseSizeBeforeUpdate = tcTipoImpRepository.findAll().size();

        // Create the TcTipoImp
        TcTipoImpDTO tcTipoImpDTO = tcTipoImpMapper.toDto(tcTipoImp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTcTipoImpMockMvc.perform(put("/api/tc-tipo-imps").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoImpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcTipoImp in the database
        List<TcTipoImp> tcTipoImpList = tcTipoImpRepository.findAll();
        assertThat(tcTipoImpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTcTipoImp() throws Exception {
        // Initialize the database
        tcTipoImpRepository.saveAndFlush(tcTipoImp);

        int databaseSizeBeforeDelete = tcTipoImpRepository.findAll().size();

        // Delete the tcTipoImp
        restTcTipoImpMockMvc.perform(delete("/api/tc-tipo-imps/{id}", tcTipoImp.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TcTipoImp> tcTipoImpList = tcTipoImpRepository.findAll();
        assertThat(tcTipoImpList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
