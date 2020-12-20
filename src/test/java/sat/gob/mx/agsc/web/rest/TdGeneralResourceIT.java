package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.RfcrfApp;
import sat.gob.mx.agsc.config.TestSecurityConfiguration;
import sat.gob.mx.agsc.domain.TdGeneral;
import sat.gob.mx.agsc.repository.TdGeneralRepository;
import sat.gob.mx.agsc.service.TdGeneralService;
import sat.gob.mx.agsc.service.dto.TdGeneralDTO;
import sat.gob.mx.agsc.service.mapper.TdGeneralMapper;

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
 * Integration tests for the {@link TdGeneralResource} REST controller.
 */
@SpringBootTest(classes = { RfcrfApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TdGeneralResourceIT {

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TIPO_SOLICITUDD = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SOLICITUDD = "BBBBBBBBBB";

    private static final String DEFAULT_FOLIO = "AAAAAAAAAA";
    private static final String UPDATED_FOLIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTATUS = 1;
    private static final Integer UPDATED_ESTATUS = 2;

    @Autowired
    private TdGeneralRepository tdGeneralRepository;

    @Autowired
    private TdGeneralMapper tdGeneralMapper;

    @Autowired
    private TdGeneralService tdGeneralService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTdGeneralMockMvc;

    private TdGeneral tdGeneral;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TdGeneral createEntity(EntityManager em) {
        TdGeneral tdGeneral = new TdGeneral()
            .rfc(DEFAULT_RFC)
            .fecha(DEFAULT_FECHA)
            .tipoSolicitudd(DEFAULT_TIPO_SOLICITUDD)
            .folio(DEFAULT_FOLIO)
            .estatus(DEFAULT_ESTATUS);
        return tdGeneral;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TdGeneral createUpdatedEntity(EntityManager em) {
        TdGeneral tdGeneral = new TdGeneral()
            .rfc(UPDATED_RFC)
            .fecha(UPDATED_FECHA)
            .tipoSolicitudd(UPDATED_TIPO_SOLICITUDD)
            .folio(UPDATED_FOLIO)
            .estatus(UPDATED_ESTATUS);
        return tdGeneral;
    }

    @BeforeEach
    public void initTest() {
        tdGeneral = createEntity(em);
    }

    @Test
    @Transactional
    public void createTdGeneral() throws Exception {
        int databaseSizeBeforeCreate = tdGeneralRepository.findAll().size();
        // Create the TdGeneral
        TdGeneralDTO tdGeneralDTO = tdGeneralMapper.toDto(tdGeneral);
        restTdGeneralMockMvc.perform(post("/api/td-generals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdGeneralDTO)))
            .andExpect(status().isCreated());

        // Validate the TdGeneral in the database
        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeCreate + 1);
        TdGeneral testTdGeneral = tdGeneralList.get(tdGeneralList.size() - 1);
        assertThat(testTdGeneral.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testTdGeneral.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTdGeneral.getTipoSolicitudd()).isEqualTo(DEFAULT_TIPO_SOLICITUDD);
        assertThat(testTdGeneral.getFolio()).isEqualTo(DEFAULT_FOLIO);
        assertThat(testTdGeneral.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
    }

    @Test
    @Transactional
    public void createTdGeneralWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tdGeneralRepository.findAll().size();

        // Create the TdGeneral with an existing ID
        tdGeneral.setId(1L);
        TdGeneralDTO tdGeneralDTO = tdGeneralMapper.toDto(tdGeneral);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTdGeneralMockMvc.perform(post("/api/td-generals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdGeneralDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TdGeneral in the database
        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdGeneralRepository.findAll().size();
        // set the field null
        tdGeneral.setRfc(null);

        // Create the TdGeneral, which fails.
        TdGeneralDTO tdGeneralDTO = tdGeneralMapper.toDto(tdGeneral);


        restTdGeneralMockMvc.perform(post("/api/td-generals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdGeneralDTO)))
            .andExpect(status().isBadRequest());

        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdGeneralRepository.findAll().size();
        // set the field null
        tdGeneral.setFecha(null);

        // Create the TdGeneral, which fails.
        TdGeneralDTO tdGeneralDTO = tdGeneralMapper.toDto(tdGeneral);


        restTdGeneralMockMvc.perform(post("/api/td-generals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdGeneralDTO)))
            .andExpect(status().isBadRequest());

        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTipoSolicituddIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdGeneralRepository.findAll().size();
        // set the field null
        tdGeneral.setTipoSolicitudd(null);

        // Create the TdGeneral, which fails.
        TdGeneralDTO tdGeneralDTO = tdGeneralMapper.toDto(tdGeneral);


        restTdGeneralMockMvc.perform(post("/api/td-generals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdGeneralDTO)))
            .andExpect(status().isBadRequest());

        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdGeneralRepository.findAll().size();
        // set the field null
        tdGeneral.setFolio(null);

        // Create the TdGeneral, which fails.
        TdGeneralDTO tdGeneralDTO = tdGeneralMapper.toDto(tdGeneral);


        restTdGeneralMockMvc.perform(post("/api/td-generals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdGeneralDTO)))
            .andExpect(status().isBadRequest());

        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdGeneralRepository.findAll().size();
        // set the field null
        tdGeneral.setEstatus(null);

        // Create the TdGeneral, which fails.
        TdGeneralDTO tdGeneralDTO = tdGeneralMapper.toDto(tdGeneral);


        restTdGeneralMockMvc.perform(post("/api/td-generals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdGeneralDTO)))
            .andExpect(status().isBadRequest());

        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTdGenerals() throws Exception {
        // Initialize the database
        tdGeneralRepository.saveAndFlush(tdGeneral);

        // Get all the tdGeneralList
        restTdGeneralMockMvc.perform(get("/api/td-generals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tdGeneral.getId().intValue())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].tipoSolicitudd").value(hasItem(DEFAULT_TIPO_SOLICITUDD)))
            .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)));
    }
    
    @Test
    @Transactional
    public void getTdGeneral() throws Exception {
        // Initialize the database
        tdGeneralRepository.saveAndFlush(tdGeneral);

        // Get the tdGeneral
        restTdGeneralMockMvc.perform(get("/api/td-generals/{id}", tdGeneral.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tdGeneral.getId().intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.tipoSolicitudd").value(DEFAULT_TIPO_SOLICITUDD))
            .andExpect(jsonPath("$.folio").value(DEFAULT_FOLIO))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS));
    }
    @Test
    @Transactional
    public void getNonExistingTdGeneral() throws Exception {
        // Get the tdGeneral
        restTdGeneralMockMvc.perform(get("/api/td-generals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTdGeneral() throws Exception {
        // Initialize the database
        tdGeneralRepository.saveAndFlush(tdGeneral);

        int databaseSizeBeforeUpdate = tdGeneralRepository.findAll().size();

        // Update the tdGeneral
        TdGeneral updatedTdGeneral = tdGeneralRepository.findById(tdGeneral.getId()).get();
        // Disconnect from session so that the updates on updatedTdGeneral are not directly saved in db
        em.detach(updatedTdGeneral);
        updatedTdGeneral
            .rfc(UPDATED_RFC)
            .fecha(UPDATED_FECHA)
            .tipoSolicitudd(UPDATED_TIPO_SOLICITUDD)
            .folio(UPDATED_FOLIO)
            .estatus(UPDATED_ESTATUS);
        TdGeneralDTO tdGeneralDTO = tdGeneralMapper.toDto(updatedTdGeneral);

        restTdGeneralMockMvc.perform(put("/api/td-generals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdGeneralDTO)))
            .andExpect(status().isOk());

        // Validate the TdGeneral in the database
        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeUpdate);
        TdGeneral testTdGeneral = tdGeneralList.get(tdGeneralList.size() - 1);
        assertThat(testTdGeneral.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testTdGeneral.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTdGeneral.getTipoSolicitudd()).isEqualTo(UPDATED_TIPO_SOLICITUDD);
        assertThat(testTdGeneral.getFolio()).isEqualTo(UPDATED_FOLIO);
        assertThat(testTdGeneral.getEstatus()).isEqualTo(UPDATED_ESTATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTdGeneral() throws Exception {
        int databaseSizeBeforeUpdate = tdGeneralRepository.findAll().size();

        // Create the TdGeneral
        TdGeneralDTO tdGeneralDTO = tdGeneralMapper.toDto(tdGeneral);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTdGeneralMockMvc.perform(put("/api/td-generals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdGeneralDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TdGeneral in the database
        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTdGeneral() throws Exception {
        // Initialize the database
        tdGeneralRepository.saveAndFlush(tdGeneral);

        int databaseSizeBeforeDelete = tdGeneralRepository.findAll().size();

        // Delete the tdGeneral
        restTdGeneralMockMvc.perform(delete("/api/td-generals/{id}", tdGeneral.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TdGeneral> tdGeneralList = tdGeneralRepository.findAll();
        assertThat(tdGeneralList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
