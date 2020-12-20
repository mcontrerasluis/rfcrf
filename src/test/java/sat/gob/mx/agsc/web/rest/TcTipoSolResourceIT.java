package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.RfcrfApp;
import sat.gob.mx.agsc.config.TestSecurityConfiguration;
import sat.gob.mx.agsc.domain.TcTipoSol;
import sat.gob.mx.agsc.repository.TcTipoSolRepository;
import sat.gob.mx.agsc.service.TcTipoSolService;
import sat.gob.mx.agsc.service.dto.TcTipoSolDTO;
import sat.gob.mx.agsc.service.mapper.TcTipoSolMapper;

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
 * Integration tests for the {@link TcTipoSolResource} REST controller.
 */
@SpringBootTest(classes = { RfcrfApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TcTipoSolResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ISR = 0;
    private static final Integer UPDATED_ISR = 1;

    private static final Integer DEFAULT_IVA = 0;
    private static final Integer UPDATED_IVA = 1;

    private static final Integer DEFAULT_EFIRMA = 0;
    private static final Integer UPDATED_EFIRMA = 1;

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private TcTipoSolRepository tcTipoSolRepository;

    @Autowired
    private TcTipoSolMapper tcTipoSolMapper;

    @Autowired
    private TcTipoSolService tcTipoSolService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTcTipoSolMockMvc;

    private TcTipoSol tcTipoSol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcTipoSol createEntity(EntityManager em) {
        TcTipoSol tcTipoSol = new TcTipoSol()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION)
            .isr(DEFAULT_ISR)
            .iva(DEFAULT_IVA)
            .efirma(DEFAULT_EFIRMA)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .usuario(DEFAULT_USUARIO);
        return tcTipoSol;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcTipoSol createUpdatedEntity(EntityManager em) {
        TcTipoSol tcTipoSol = new TcTipoSol()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .isr(UPDATED_ISR)
            .iva(UPDATED_IVA)
            .efirma(UPDATED_EFIRMA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .usuario(UPDATED_USUARIO);
        return tcTipoSol;
    }

    @BeforeEach
    public void initTest() {
        tcTipoSol = createEntity(em);
    }

    @Test
    @Transactional
    public void createTcTipoSol() throws Exception {
        int databaseSizeBeforeCreate = tcTipoSolRepository.findAll().size();
        // Create the TcTipoSol
        TcTipoSolDTO tcTipoSolDTO = tcTipoSolMapper.toDto(tcTipoSol);
        restTcTipoSolMockMvc.perform(post("/api/tc-tipo-sols").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoSolDTO)))
            .andExpect(status().isCreated());

        // Validate the TcTipoSol in the database
        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeCreate + 1);
        TcTipoSol testTcTipoSol = tcTipoSolList.get(tcTipoSolList.size() - 1);
        assertThat(testTcTipoSol.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTcTipoSol.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTcTipoSol.getIsr()).isEqualTo(DEFAULT_ISR);
        assertThat(testTcTipoSol.getIva()).isEqualTo(DEFAULT_IVA);
        assertThat(testTcTipoSol.getEfirma()).isEqualTo(DEFAULT_EFIRMA);
        assertThat(testTcTipoSol.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testTcTipoSol.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testTcTipoSol.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createTcTipoSolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tcTipoSolRepository.findAll().size();

        // Create the TcTipoSol with an existing ID
        tcTipoSol.setId(1L);
        TcTipoSolDTO tcTipoSolDTO = tcTipoSolMapper.toDto(tcTipoSol);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTcTipoSolMockMvc.perform(post("/api/tc-tipo-sols").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoSolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcTipoSol in the database
        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcTipoSolRepository.findAll().size();
        // set the field null
        tcTipoSol.setClave(null);

        // Create the TcTipoSol, which fails.
        TcTipoSolDTO tcTipoSolDTO = tcTipoSolMapper.toDto(tcTipoSol);


        restTcTipoSolMockMvc.perform(post("/api/tc-tipo-sols").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoSolDTO)))
            .andExpect(status().isBadRequest());

        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcTipoSolRepository.findAll().size();
        // set the field null
        tcTipoSol.setDescripcion(null);

        // Create the TcTipoSol, which fails.
        TcTipoSolDTO tcTipoSolDTO = tcTipoSolMapper.toDto(tcTipoSol);


        restTcTipoSolMockMvc.perform(post("/api/tc-tipo-sols").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoSolDTO)))
            .andExpect(status().isBadRequest());

        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsrIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcTipoSolRepository.findAll().size();
        // set the field null
        tcTipoSol.setIsr(null);

        // Create the TcTipoSol, which fails.
        TcTipoSolDTO tcTipoSolDTO = tcTipoSolMapper.toDto(tcTipoSol);


        restTcTipoSolMockMvc.perform(post("/api/tc-tipo-sols").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoSolDTO)))
            .andExpect(status().isBadRequest());

        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcTipoSolRepository.findAll().size();
        // set the field null
        tcTipoSol.setIva(null);

        // Create the TcTipoSol, which fails.
        TcTipoSolDTO tcTipoSolDTO = tcTipoSolMapper.toDto(tcTipoSol);


        restTcTipoSolMockMvc.perform(post("/api/tc-tipo-sols").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoSolDTO)))
            .andExpect(status().isBadRequest());

        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEfirmaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcTipoSolRepository.findAll().size();
        // set the field null
        tcTipoSol.setEfirma(null);

        // Create the TcTipoSol, which fails.
        TcTipoSolDTO tcTipoSolDTO = tcTipoSolMapper.toDto(tcTipoSol);


        restTcTipoSolMockMvc.perform(post("/api/tc-tipo-sols").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoSolDTO)))
            .andExpect(status().isBadRequest());

        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTcTipoSols() throws Exception {
        // Initialize the database
        tcTipoSolRepository.saveAndFlush(tcTipoSol);

        // Get all the tcTipoSolList
        restTcTipoSolMockMvc.perform(get("/api/tc-tipo-sols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tcTipoSol.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].isr").value(hasItem(DEFAULT_ISR)))
            .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA)))
            .andExpect(jsonPath("$.[*].efirma").value(hasItem(DEFAULT_EFIRMA)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }
    
    @Test
    @Transactional
    public void getTcTipoSol() throws Exception {
        // Initialize the database
        tcTipoSolRepository.saveAndFlush(tcTipoSol);

        // Get the tcTipoSol
        restTcTipoSolMockMvc.perform(get("/api/tc-tipo-sols/{id}", tcTipoSol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tcTipoSol.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.isr").value(DEFAULT_ISR))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA))
            .andExpect(jsonPath("$.efirma").value(DEFAULT_EFIRMA))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }
    @Test
    @Transactional
    public void getNonExistingTcTipoSol() throws Exception {
        // Get the tcTipoSol
        restTcTipoSolMockMvc.perform(get("/api/tc-tipo-sols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTcTipoSol() throws Exception {
        // Initialize the database
        tcTipoSolRepository.saveAndFlush(tcTipoSol);

        int databaseSizeBeforeUpdate = tcTipoSolRepository.findAll().size();

        // Update the tcTipoSol
        TcTipoSol updatedTcTipoSol = tcTipoSolRepository.findById(tcTipoSol.getId()).get();
        // Disconnect from session so that the updates on updatedTcTipoSol are not directly saved in db
        em.detach(updatedTcTipoSol);
        updatedTcTipoSol
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .isr(UPDATED_ISR)
            .iva(UPDATED_IVA)
            .efirma(UPDATED_EFIRMA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .usuario(UPDATED_USUARIO);
        TcTipoSolDTO tcTipoSolDTO = tcTipoSolMapper.toDto(updatedTcTipoSol);

        restTcTipoSolMockMvc.perform(put("/api/tc-tipo-sols").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoSolDTO)))
            .andExpect(status().isOk());

        // Validate the TcTipoSol in the database
        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeUpdate);
        TcTipoSol testTcTipoSol = tcTipoSolList.get(tcTipoSolList.size() - 1);
        assertThat(testTcTipoSol.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTcTipoSol.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTcTipoSol.getIsr()).isEqualTo(UPDATED_ISR);
        assertThat(testTcTipoSol.getIva()).isEqualTo(UPDATED_IVA);
        assertThat(testTcTipoSol.getEfirma()).isEqualTo(UPDATED_EFIRMA);
        assertThat(testTcTipoSol.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testTcTipoSol.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testTcTipoSol.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTcTipoSol() throws Exception {
        int databaseSizeBeforeUpdate = tcTipoSolRepository.findAll().size();

        // Create the TcTipoSol
        TcTipoSolDTO tcTipoSolDTO = tcTipoSolMapper.toDto(tcTipoSol);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTcTipoSolMockMvc.perform(put("/api/tc-tipo-sols").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcTipoSolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcTipoSol in the database
        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTcTipoSol() throws Exception {
        // Initialize the database
        tcTipoSolRepository.saveAndFlush(tcTipoSol);

        int databaseSizeBeforeDelete = tcTipoSolRepository.findAll().size();

        // Delete the tcTipoSol
        restTcTipoSolMockMvc.perform(delete("/api/tc-tipo-sols/{id}", tcTipoSol.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TcTipoSol> tcTipoSolList = tcTipoSolRepository.findAll();
        assertThat(tcTipoSolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
