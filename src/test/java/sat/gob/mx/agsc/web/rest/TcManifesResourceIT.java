package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.RfcrfApp;
import sat.gob.mx.agsc.config.TestSecurityConfiguration;
import sat.gob.mx.agsc.domain.TcManifes;
import sat.gob.mx.agsc.repository.TcManifesRepository;
import sat.gob.mx.agsc.service.TcManifesService;
import sat.gob.mx.agsc.service.dto.TcManifesDTO;
import sat.gob.mx.agsc.service.mapper.TcManifesMapper;

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
 * Integration tests for the {@link TcManifesResource} REST controller.
 */
@SpringBootTest(classes = { RfcrfApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TcManifesResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MORAL = 1;
    private static final Integer UPDATED_MORAL = 2;

    private static final Integer DEFAULT_FISICA = 1;
    private static final Integer UPDATED_FISICA = 2;

    private static final Integer DEFAULT_ISR = 0;
    private static final Integer UPDATED_ISR = 1;

    private static final Integer DEFAULT_IVA = 0;
    private static final Integer UPDATED_IVA = 1;

    private static final Integer DEFAULT_RFNORTE = 0;
    private static final Integer UPDATED_RFNORTE = 1;

    private static final Integer DEFAULT_RFSUR = 0;
    private static final Integer UPDATED_RFSUR = 1;

    private static final Integer DEFAULT_S_01 = 0;
    private static final Integer UPDATED_S_01 = 1;

    private static final Integer DEFAULT_S_02 = 0;
    private static final Integer UPDATED_S_02 = 1;

    private static final Integer DEFAULT_S_03 = 0;
    private static final Integer UPDATED_S_03 = 1;

    private static final Integer DEFAULT_S_04 = 0;
    private static final Integer UPDATED_S_04 = 1;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private TcManifesRepository tcManifesRepository;

    @Autowired
    private TcManifesMapper tcManifesMapper;

    @Autowired
    private TcManifesService tcManifesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTcManifesMockMvc;

    private TcManifes tcManifes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcManifes createEntity(EntityManager em) {
        TcManifes tcManifes = new TcManifes()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION)
            .moral(DEFAULT_MORAL)
            .fisica(DEFAULT_FISICA)
            .isr(DEFAULT_ISR)
            .iva(DEFAULT_IVA)
            .rfnorte(DEFAULT_RFNORTE)
            .rfsur(DEFAULT_RFSUR)
            .s01(DEFAULT_S_01)
            .s02(DEFAULT_S_02)
            .s03(DEFAULT_S_03)
            .s04(DEFAULT_S_04)
            .fecha(DEFAULT_FECHA)
            .usuario(DEFAULT_USUARIO);
        return tcManifes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcManifes createUpdatedEntity(EntityManager em) {
        TcManifes tcManifes = new TcManifes()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .moral(UPDATED_MORAL)
            .fisica(UPDATED_FISICA)
            .isr(UPDATED_ISR)
            .iva(UPDATED_IVA)
            .rfnorte(UPDATED_RFNORTE)
            .rfsur(UPDATED_RFSUR)
            .s01(UPDATED_S_01)
            .s02(UPDATED_S_02)
            .s03(UPDATED_S_03)
            .s04(UPDATED_S_04)
            .fecha(UPDATED_FECHA)
            .usuario(UPDATED_USUARIO);
        return tcManifes;
    }

    @BeforeEach
    public void initTest() {
        tcManifes = createEntity(em);
    }

    @Test
    @Transactional
    public void createTcManifes() throws Exception {
        int databaseSizeBeforeCreate = tcManifesRepository.findAll().size();
        // Create the TcManifes
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);
        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isCreated());

        // Validate the TcManifes in the database
        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeCreate + 1);
        TcManifes testTcManifes = tcManifesList.get(tcManifesList.size() - 1);
        assertThat(testTcManifes.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTcManifes.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTcManifes.getMoral()).isEqualTo(DEFAULT_MORAL);
        assertThat(testTcManifes.getFisica()).isEqualTo(DEFAULT_FISICA);
        assertThat(testTcManifes.getIsr()).isEqualTo(DEFAULT_ISR);
        assertThat(testTcManifes.getIva()).isEqualTo(DEFAULT_IVA);
        assertThat(testTcManifes.getRfnorte()).isEqualTo(DEFAULT_RFNORTE);
        assertThat(testTcManifes.getRfsur()).isEqualTo(DEFAULT_RFSUR);
        assertThat(testTcManifes.gets01()).isEqualTo(DEFAULT_S_01);
        assertThat(testTcManifes.gets02()).isEqualTo(DEFAULT_S_02);
        assertThat(testTcManifes.gets03()).isEqualTo(DEFAULT_S_03);
        assertThat(testTcManifes.gets04()).isEqualTo(DEFAULT_S_04);
        assertThat(testTcManifes.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTcManifes.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createTcManifesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tcManifesRepository.findAll().size();

        // Create the TcManifes with an existing ID
        tcManifes.setId(1L);
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcManifes in the database
        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.setClave(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.setDescripcion(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMoralIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.setMoral(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFisicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.setFisica(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsrIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.setIsr(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.setIva(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfnorteIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.setRfnorte(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfsurIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.setRfsur(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checks01IsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.sets01(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checks02IsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.sets02(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checks03IsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.sets03(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checks04IsRequired() throws Exception {
        int databaseSizeBeforeTest = tcManifesRepository.findAll().size();
        // set the field null
        tcManifes.sets04(null);

        // Create the TcManifes, which fails.
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);


        restTcManifesMockMvc.perform(post("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTcManifes() throws Exception {
        // Initialize the database
        tcManifesRepository.saveAndFlush(tcManifes);

        // Get all the tcManifesList
        restTcManifesMockMvc.perform(get("/api/tc-manifes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tcManifes.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].moral").value(hasItem(DEFAULT_MORAL)))
            .andExpect(jsonPath("$.[*].fisica").value(hasItem(DEFAULT_FISICA)))
            .andExpect(jsonPath("$.[*].isr").value(hasItem(DEFAULT_ISR)))
            .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA)))
            .andExpect(jsonPath("$.[*].rfnorte").value(hasItem(DEFAULT_RFNORTE)))
            .andExpect(jsonPath("$.[*].rfsur").value(hasItem(DEFAULT_RFSUR)))
            .andExpect(jsonPath("$.[*].s01").value(hasItem(DEFAULT_S_01)))
            .andExpect(jsonPath("$.[*].s02").value(hasItem(DEFAULT_S_02)))
            .andExpect(jsonPath("$.[*].s03").value(hasItem(DEFAULT_S_03)))
            .andExpect(jsonPath("$.[*].s04").value(hasItem(DEFAULT_S_04)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }
    
    @Test
    @Transactional
    public void getTcManifes() throws Exception {
        // Initialize the database
        tcManifesRepository.saveAndFlush(tcManifes);

        // Get the tcManifes
        restTcManifesMockMvc.perform(get("/api/tc-manifes/{id}", tcManifes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tcManifes.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.moral").value(DEFAULT_MORAL))
            .andExpect(jsonPath("$.fisica").value(DEFAULT_FISICA))
            .andExpect(jsonPath("$.isr").value(DEFAULT_ISR))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA))
            .andExpect(jsonPath("$.rfnorte").value(DEFAULT_RFNORTE))
            .andExpect(jsonPath("$.rfsur").value(DEFAULT_RFSUR))
            .andExpect(jsonPath("$.s01").value(DEFAULT_S_01))
            .andExpect(jsonPath("$.s02").value(DEFAULT_S_02))
            .andExpect(jsonPath("$.s03").value(DEFAULT_S_03))
            .andExpect(jsonPath("$.s04").value(DEFAULT_S_04))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }
    @Test
    @Transactional
    public void getNonExistingTcManifes() throws Exception {
        // Get the tcManifes
        restTcManifesMockMvc.perform(get("/api/tc-manifes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTcManifes() throws Exception {
        // Initialize the database
        tcManifesRepository.saveAndFlush(tcManifes);

        int databaseSizeBeforeUpdate = tcManifesRepository.findAll().size();

        // Update the tcManifes
        TcManifes updatedTcManifes = tcManifesRepository.findById(tcManifes.getId()).get();
        // Disconnect from session so that the updates on updatedTcManifes are not directly saved in db
        em.detach(updatedTcManifes);
        updatedTcManifes
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .moral(UPDATED_MORAL)
            .fisica(UPDATED_FISICA)
            .isr(UPDATED_ISR)
            .iva(UPDATED_IVA)
            .rfnorte(UPDATED_RFNORTE)
            .rfsur(UPDATED_RFSUR)
            .s01(UPDATED_S_01)
            .s02(UPDATED_S_02)
            .s03(UPDATED_S_03)
            .s04(UPDATED_S_04)
            .fecha(UPDATED_FECHA)
            .usuario(UPDATED_USUARIO);
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(updatedTcManifes);

        restTcManifesMockMvc.perform(put("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isOk());

        // Validate the TcManifes in the database
        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeUpdate);
        TcManifes testTcManifes = tcManifesList.get(tcManifesList.size() - 1);
        assertThat(testTcManifes.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTcManifes.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTcManifes.getMoral()).isEqualTo(UPDATED_MORAL);
        assertThat(testTcManifes.getFisica()).isEqualTo(UPDATED_FISICA);
        assertThat(testTcManifes.getIsr()).isEqualTo(UPDATED_ISR);
        assertThat(testTcManifes.getIva()).isEqualTo(UPDATED_IVA);
        assertThat(testTcManifes.getRfnorte()).isEqualTo(UPDATED_RFNORTE);
        assertThat(testTcManifes.getRfsur()).isEqualTo(UPDATED_RFSUR);
        assertThat(testTcManifes.gets01()).isEqualTo(UPDATED_S_01);
        assertThat(testTcManifes.gets02()).isEqualTo(UPDATED_S_02);
        assertThat(testTcManifes.gets03()).isEqualTo(UPDATED_S_03);
        assertThat(testTcManifes.gets04()).isEqualTo(UPDATED_S_04);
        assertThat(testTcManifes.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTcManifes.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTcManifes() throws Exception {
        int databaseSizeBeforeUpdate = tcManifesRepository.findAll().size();

        // Create the TcManifes
        TcManifesDTO tcManifesDTO = tcManifesMapper.toDto(tcManifes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTcManifesMockMvc.perform(put("/api/tc-manifes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcManifesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcManifes in the database
        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTcManifes() throws Exception {
        // Initialize the database
        tcManifesRepository.saveAndFlush(tcManifes);

        int databaseSizeBeforeDelete = tcManifesRepository.findAll().size();

        // Delete the tcManifes
        restTcManifesMockMvc.perform(delete("/api/tc-manifes/{id}", tcManifes.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TcManifes> tcManifesList = tcManifesRepository.findAll();
        assertThat(tcManifesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
