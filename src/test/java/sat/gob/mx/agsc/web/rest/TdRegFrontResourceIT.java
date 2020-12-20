package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.RfcrfApp;
import sat.gob.mx.agsc.config.TestSecurityConfiguration;
import sat.gob.mx.agsc.domain.TdRegFront;
import sat.gob.mx.agsc.repository.TdRegFrontRepository;
import sat.gob.mx.agsc.service.TdRegFrontService;
import sat.gob.mx.agsc.service.dto.TdRegFrontDTO;
import sat.gob.mx.agsc.service.mapper.TdRegFrontMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TdRegFrontResource} REST controller.
 */
@SpringBootTest(classes = { RfcrfApp.class, TestSecurityConfiguration.class })
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TdRegFrontResourceIT {

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_DOMICILIO_REGION = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_SUCURSAL_REGION = "AAAAAAAAAA";
    private static final String UPDATED_SUCURSAL_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_IMPUESTOD = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_IMPUESTOD = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_SOLICITUDD = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SOLICITUDD = "BBBBBBBBBB";

    private static final Integer DEFAULT_EJERCICIOD = 1;
    private static final Integer UPDATED_EJERCICIOD = 2;

    private static final String DEFAULT_ESTATUS = "AAAAAAAAAA";
    private static final String UPDATED_ESTATUS = "BBBBBBBBBB";

    private static final String DEFAULT_FOLIO = "AAAAAAAAAA";
    private static final String UPDATED_FOLIO = "BBBBBBBBBB";

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private TdRegFrontRepository tdRegFrontRepository;

    @Mock
    private TdRegFrontRepository tdRegFrontRepositoryMock;

    @Autowired
    private TdRegFrontMapper tdRegFrontMapper;

    @Mock
    private TdRegFrontService tdRegFrontServiceMock;

    @Autowired
    private TdRegFrontService tdRegFrontService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTdRegFrontMockMvc;

    private TdRegFront tdRegFront;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TdRegFront createEntity(EntityManager em) {
        TdRegFront tdRegFront = new TdRegFront()
            .region(DEFAULT_REGION)
            .domicilioRegion(DEFAULT_DOMICILIO_REGION)
            .sucursalRegion(DEFAULT_SUCURSAL_REGION)
            .tipoImpuestod(DEFAULT_TIPO_IMPUESTOD)
            .tipoSolicitudd(DEFAULT_TIPO_SOLICITUDD)
            .ejerciciod(DEFAULT_EJERCICIOD)
            .estatus(DEFAULT_ESTATUS)
            .folio(DEFAULT_FOLIO)
            .rfc(DEFAULT_RFC)
            .fecha(DEFAULT_FECHA)
            .usuario(DEFAULT_USUARIO);
        return tdRegFront;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TdRegFront createUpdatedEntity(EntityManager em) {
        TdRegFront tdRegFront = new TdRegFront()
            .region(UPDATED_REGION)
            .domicilioRegion(UPDATED_DOMICILIO_REGION)
            .sucursalRegion(UPDATED_SUCURSAL_REGION)
            .tipoImpuestod(UPDATED_TIPO_IMPUESTOD)
            .tipoSolicitudd(UPDATED_TIPO_SOLICITUDD)
            .ejerciciod(UPDATED_EJERCICIOD)
            .estatus(UPDATED_ESTATUS)
            .folio(UPDATED_FOLIO)
            .rfc(UPDATED_RFC)
            .fecha(UPDATED_FECHA)
            .usuario(UPDATED_USUARIO);
        return tdRegFront;
    }

    @BeforeEach
    public void initTest() {
        tdRegFront = createEntity(em);
    }

    @Test
    @Transactional
    public void createTdRegFront() throws Exception {
        int databaseSizeBeforeCreate = tdRegFrontRepository.findAll().size();
        // Create the TdRegFront
        TdRegFrontDTO tdRegFrontDTO = tdRegFrontMapper.toDto(tdRegFront);
        restTdRegFrontMockMvc.perform(post("/api/td-reg-fronts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdRegFrontDTO)))
            .andExpect(status().isCreated());

        // Validate the TdRegFront in the database
        List<TdRegFront> tdRegFrontList = tdRegFrontRepository.findAll();
        assertThat(tdRegFrontList).hasSize(databaseSizeBeforeCreate + 1);
        TdRegFront testTdRegFront = tdRegFrontList.get(tdRegFrontList.size() - 1);
        assertThat(testTdRegFront.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testTdRegFront.getDomicilioRegion()).isEqualTo(DEFAULT_DOMICILIO_REGION);
        assertThat(testTdRegFront.getSucursalRegion()).isEqualTo(DEFAULT_SUCURSAL_REGION);
        assertThat(testTdRegFront.getTipoImpuestod()).isEqualTo(DEFAULT_TIPO_IMPUESTOD);
        assertThat(testTdRegFront.getTipoSolicitudd()).isEqualTo(DEFAULT_TIPO_SOLICITUDD);
        assertThat(testTdRegFront.getEjerciciod()).isEqualTo(DEFAULT_EJERCICIOD);
        assertThat(testTdRegFront.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testTdRegFront.getFolio()).isEqualTo(DEFAULT_FOLIO);
        assertThat(testTdRegFront.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testTdRegFront.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTdRegFront.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createTdRegFrontWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tdRegFrontRepository.findAll().size();

        // Create the TdRegFront with an existing ID
        tdRegFront.setId(1L);
        TdRegFrontDTO tdRegFrontDTO = tdRegFrontMapper.toDto(tdRegFront);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTdRegFrontMockMvc.perform(post("/api/td-reg-fronts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdRegFrontDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TdRegFront in the database
        List<TdRegFront> tdRegFrontList = tdRegFrontRepository.findAll();
        assertThat(tdRegFrontList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdRegFrontRepository.findAll().size();
        // set the field null
        tdRegFront.setRegion(null);

        // Create the TdRegFront, which fails.
        TdRegFrontDTO tdRegFrontDTO = tdRegFrontMapper.toDto(tdRegFront);


        restTdRegFrontMockMvc.perform(post("/api/td-reg-fronts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdRegFrontDTO)))
            .andExpect(status().isBadRequest());

        List<TdRegFront> tdRegFrontList = tdRegFrontRepository.findAll();
        assertThat(tdRegFrontList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomicilioRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdRegFrontRepository.findAll().size();
        // set the field null
        tdRegFront.setDomicilioRegion(null);

        // Create the TdRegFront, which fails.
        TdRegFrontDTO tdRegFrontDTO = tdRegFrontMapper.toDto(tdRegFront);


        restTdRegFrontMockMvc.perform(post("/api/td-reg-fronts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdRegFrontDTO)))
            .andExpect(status().isBadRequest());

        List<TdRegFront> tdRegFrontList = tdRegFrontRepository.findAll();
        assertThat(tdRegFrontList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSucursalRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdRegFrontRepository.findAll().size();
        // set the field null
        tdRegFront.setSucursalRegion(null);

        // Create the TdRegFront, which fails.
        TdRegFrontDTO tdRegFrontDTO = tdRegFrontMapper.toDto(tdRegFront);


        restTdRegFrontMockMvc.perform(post("/api/td-reg-fronts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdRegFrontDTO)))
            .andExpect(status().isBadRequest());

        List<TdRegFront> tdRegFrontList = tdRegFrontRepository.findAll();
        assertThat(tdRegFrontList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = tdRegFrontRepository.findAll().size();
        // set the field null
        tdRegFront.setEstatus(null);

        // Create the TdRegFront, which fails.
        TdRegFrontDTO tdRegFrontDTO = tdRegFrontMapper.toDto(tdRegFront);


        restTdRegFrontMockMvc.perform(post("/api/td-reg-fronts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdRegFrontDTO)))
            .andExpect(status().isBadRequest());

        List<TdRegFront> tdRegFrontList = tdRegFrontRepository.findAll();
        assertThat(tdRegFrontList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTdRegFronts() throws Exception {
        // Initialize the database
        tdRegFrontRepository.saveAndFlush(tdRegFront);

        // Get all the tdRegFrontList
        restTdRegFrontMockMvc.perform(get("/api/td-reg-fronts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tdRegFront.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].domicilioRegion").value(hasItem(DEFAULT_DOMICILIO_REGION)))
            .andExpect(jsonPath("$.[*].sucursalRegion").value(hasItem(DEFAULT_SUCURSAL_REGION)))
            .andExpect(jsonPath("$.[*].tipoImpuestod").value(hasItem(DEFAULT_TIPO_IMPUESTOD)))
            .andExpect(jsonPath("$.[*].tipoSolicitudd").value(hasItem(DEFAULT_TIPO_SOLICITUDD)))
            .andExpect(jsonPath("$.[*].ejerciciod").value(hasItem(DEFAULT_EJERCICIOD)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS)))
            .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO)))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTdRegFrontsWithEagerRelationshipsIsEnabled() throws Exception {
        when(tdRegFrontServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTdRegFrontMockMvc.perform(get("/api/td-reg-fronts?eagerload=true"))
            .andExpect(status().isOk());

        verify(tdRegFrontServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTdRegFrontsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tdRegFrontServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTdRegFrontMockMvc.perform(get("/api/td-reg-fronts?eagerload=true"))
            .andExpect(status().isOk());

        verify(tdRegFrontServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTdRegFront() throws Exception {
        // Initialize the database
        tdRegFrontRepository.saveAndFlush(tdRegFront);

        // Get the tdRegFront
        restTdRegFrontMockMvc.perform(get("/api/td-reg-fronts/{id}", tdRegFront.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tdRegFront.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.domicilioRegion").value(DEFAULT_DOMICILIO_REGION))
            .andExpect(jsonPath("$.sucursalRegion").value(DEFAULT_SUCURSAL_REGION))
            .andExpect(jsonPath("$.tipoImpuestod").value(DEFAULT_TIPO_IMPUESTOD))
            .andExpect(jsonPath("$.tipoSolicitudd").value(DEFAULT_TIPO_SOLICITUDD))
            .andExpect(jsonPath("$.ejerciciod").value(DEFAULT_EJERCICIOD))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS))
            .andExpect(jsonPath("$.folio").value(DEFAULT_FOLIO))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }
    @Test
    @Transactional
    public void getNonExistingTdRegFront() throws Exception {
        // Get the tdRegFront
        restTdRegFrontMockMvc.perform(get("/api/td-reg-fronts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTdRegFront() throws Exception {
        // Initialize the database
        tdRegFrontRepository.saveAndFlush(tdRegFront);

        int databaseSizeBeforeUpdate = tdRegFrontRepository.findAll().size();

        // Update the tdRegFront
        TdRegFront updatedTdRegFront = tdRegFrontRepository.findById(tdRegFront.getId()).get();
        // Disconnect from session so that the updates on updatedTdRegFront are not directly saved in db
        em.detach(updatedTdRegFront);
        updatedTdRegFront
            .region(UPDATED_REGION)
            .domicilioRegion(UPDATED_DOMICILIO_REGION)
            .sucursalRegion(UPDATED_SUCURSAL_REGION)
            .tipoImpuestod(UPDATED_TIPO_IMPUESTOD)
            .tipoSolicitudd(UPDATED_TIPO_SOLICITUDD)
            .ejerciciod(UPDATED_EJERCICIOD)
            .estatus(UPDATED_ESTATUS)
            .folio(UPDATED_FOLIO)
            .rfc(UPDATED_RFC)
            .fecha(UPDATED_FECHA)
            .usuario(UPDATED_USUARIO);
        TdRegFrontDTO tdRegFrontDTO = tdRegFrontMapper.toDto(updatedTdRegFront);

        restTdRegFrontMockMvc.perform(put("/api/td-reg-fronts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdRegFrontDTO)))
            .andExpect(status().isOk());

        // Validate the TdRegFront in the database
        List<TdRegFront> tdRegFrontList = tdRegFrontRepository.findAll();
        assertThat(tdRegFrontList).hasSize(databaseSizeBeforeUpdate);
        TdRegFront testTdRegFront = tdRegFrontList.get(tdRegFrontList.size() - 1);
        assertThat(testTdRegFront.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testTdRegFront.getDomicilioRegion()).isEqualTo(UPDATED_DOMICILIO_REGION);
        assertThat(testTdRegFront.getSucursalRegion()).isEqualTo(UPDATED_SUCURSAL_REGION);
        assertThat(testTdRegFront.getTipoImpuestod()).isEqualTo(UPDATED_TIPO_IMPUESTOD);
        assertThat(testTdRegFront.getTipoSolicitudd()).isEqualTo(UPDATED_TIPO_SOLICITUDD);
        assertThat(testTdRegFront.getEjerciciod()).isEqualTo(UPDATED_EJERCICIOD);
        assertThat(testTdRegFront.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testTdRegFront.getFolio()).isEqualTo(UPDATED_FOLIO);
        assertThat(testTdRegFront.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testTdRegFront.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTdRegFront.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTdRegFront() throws Exception {
        int databaseSizeBeforeUpdate = tdRegFrontRepository.findAll().size();

        // Create the TdRegFront
        TdRegFrontDTO tdRegFrontDTO = tdRegFrontMapper.toDto(tdRegFront);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTdRegFrontMockMvc.perform(put("/api/td-reg-fronts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tdRegFrontDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TdRegFront in the database
        List<TdRegFront> tdRegFrontList = tdRegFrontRepository.findAll();
        assertThat(tdRegFrontList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTdRegFront() throws Exception {
        // Initialize the database
        tdRegFrontRepository.saveAndFlush(tdRegFront);

        int databaseSizeBeforeDelete = tdRegFrontRepository.findAll().size();

        // Delete the tdRegFront
        restTdRegFrontMockMvc.perform(delete("/api/td-reg-fronts/{id}", tdRegFront.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TdRegFront> tdRegFrontList = tdRegFrontRepository.findAll();
        assertThat(tdRegFrontList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
