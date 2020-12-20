package sat.gob.mx.agsc.web.rest;

import sat.gob.mx.agsc.RfcrfApp;
import sat.gob.mx.agsc.config.TestSecurityConfiguration;
import sat.gob.mx.agsc.domain.TcValida;
import sat.gob.mx.agsc.repository.TcValidaRepository;
import sat.gob.mx.agsc.service.TcValidaService;
import sat.gob.mx.agsc.service.dto.TcValidaDTO;
import sat.gob.mx.agsc.service.mapper.TcValidaMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TcValidaResource} REST controller.
 */
@SpringBootTest(classes = { RfcrfApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class TcValidaResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MORAL = 0;
    private static final Integer UPDATED_MORAL = 1;

    private static final Integer DEFAULT_FISICA = 0;
    private static final Integer UPDATED_FISICA = 1;

    private static final Integer DEFAULT_ISR = 0;
    private static final Integer UPDATED_ISR = 1;

    private static final Integer DEFAULT_IVA = 0;
    private static final Integer UPDATED_IVA = 1;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    @Autowired
    private TcValidaRepository tcValidaRepository;

    @Autowired
    private TcValidaMapper tcValidaMapper;

    @Autowired
    private TcValidaService tcValidaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTcValidaMockMvc;

    private TcValida tcValida;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcValida createEntity(EntityManager em) {
        TcValida tcValida = new TcValida()
            .clave(DEFAULT_CLAVE)
            .descripcion(DEFAULT_DESCRIPCION)
            .moral(DEFAULT_MORAL)
            .fisica(DEFAULT_FISICA)
            .isr(DEFAULT_ISR)
            .iva(DEFAULT_IVA)
            .usuario(DEFAULT_USUARIO);
        return tcValida;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TcValida createUpdatedEntity(EntityManager em) {
        TcValida tcValida = new TcValida()
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .moral(UPDATED_MORAL)
            .fisica(UPDATED_FISICA)
            .isr(UPDATED_ISR)
            .iva(UPDATED_IVA)
            .usuario(UPDATED_USUARIO);
        return tcValida;
    }

    @BeforeEach
    public void initTest() {
        tcValida = createEntity(em);
    }

    @Test
    @Transactional
    public void createTcValida() throws Exception {
        int databaseSizeBeforeCreate = tcValidaRepository.findAll().size();
        // Create the TcValida
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(tcValida);
        restTcValidaMockMvc.perform(post("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isCreated());

        // Validate the TcValida in the database
        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeCreate + 1);
        TcValida testTcValida = tcValidaList.get(tcValidaList.size() - 1);
        assertThat(testTcValida.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTcValida.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTcValida.getMoral()).isEqualTo(DEFAULT_MORAL);
        assertThat(testTcValida.getFisica()).isEqualTo(DEFAULT_FISICA);
        assertThat(testTcValida.getIsr()).isEqualTo(DEFAULT_ISR);
        assertThat(testTcValida.getIva()).isEqualTo(DEFAULT_IVA);
        assertThat(testTcValida.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    public void createTcValidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tcValidaRepository.findAll().size();

        // Create the TcValida with an existing ID
        tcValida.setId(1L);
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(tcValida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTcValidaMockMvc.perform(post("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcValida in the database
        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClaveIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcValidaRepository.findAll().size();
        // set the field null
        tcValida.setClave(null);

        // Create the TcValida, which fails.
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(tcValida);


        restTcValidaMockMvc.perform(post("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isBadRequest());

        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcValidaRepository.findAll().size();
        // set the field null
        tcValida.setDescripcion(null);

        // Create the TcValida, which fails.
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(tcValida);


        restTcValidaMockMvc.perform(post("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isBadRequest());

        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMoralIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcValidaRepository.findAll().size();
        // set the field null
        tcValida.setMoral(null);

        // Create the TcValida, which fails.
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(tcValida);


        restTcValidaMockMvc.perform(post("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isBadRequest());

        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFisicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcValidaRepository.findAll().size();
        // set the field null
        tcValida.setFisica(null);

        // Create the TcValida, which fails.
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(tcValida);


        restTcValidaMockMvc.perform(post("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isBadRequest());

        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsrIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcValidaRepository.findAll().size();
        // set the field null
        tcValida.setIsr(null);

        // Create the TcValida, which fails.
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(tcValida);


        restTcValidaMockMvc.perform(post("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isBadRequest());

        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tcValidaRepository.findAll().size();
        // set the field null
        tcValida.setIva(null);

        // Create the TcValida, which fails.
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(tcValida);


        restTcValidaMockMvc.perform(post("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isBadRequest());

        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTcValidas() throws Exception {
        // Initialize the database
        tcValidaRepository.saveAndFlush(tcValida);

        // Get all the tcValidaList
        restTcValidaMockMvc.perform(get("/api/tc-validas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tcValida.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].moral").value(hasItem(DEFAULT_MORAL)))
            .andExpect(jsonPath("$.[*].fisica").value(hasItem(DEFAULT_FISICA)))
            .andExpect(jsonPath("$.[*].isr").value(hasItem(DEFAULT_ISR)))
            .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA)))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }
    
    @Test
    @Transactional
    public void getTcValida() throws Exception {
        // Initialize the database
        tcValidaRepository.saveAndFlush(tcValida);

        // Get the tcValida
        restTcValidaMockMvc.perform(get("/api/tc-validas/{id}", tcValida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tcValida.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.moral").value(DEFAULT_MORAL))
            .andExpect(jsonPath("$.fisica").value(DEFAULT_FISICA))
            .andExpect(jsonPath("$.isr").value(DEFAULT_ISR))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }
    @Test
    @Transactional
    public void getNonExistingTcValida() throws Exception {
        // Get the tcValida
        restTcValidaMockMvc.perform(get("/api/tc-validas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTcValida() throws Exception {
        // Initialize the database
        tcValidaRepository.saveAndFlush(tcValida);

        int databaseSizeBeforeUpdate = tcValidaRepository.findAll().size();

        // Update the tcValida
        TcValida updatedTcValida = tcValidaRepository.findById(tcValida.getId()).get();
        // Disconnect from session so that the updates on updatedTcValida are not directly saved in db
        em.detach(updatedTcValida);
        updatedTcValida
            .clave(UPDATED_CLAVE)
            .descripcion(UPDATED_DESCRIPCION)
            .moral(UPDATED_MORAL)
            .fisica(UPDATED_FISICA)
            .isr(UPDATED_ISR)
            .iva(UPDATED_IVA)
            .usuario(UPDATED_USUARIO);
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(updatedTcValida);

        restTcValidaMockMvc.perform(put("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isOk());

        // Validate the TcValida in the database
        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeUpdate);
        TcValida testTcValida = tcValidaList.get(tcValidaList.size() - 1);
        assertThat(testTcValida.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTcValida.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTcValida.getMoral()).isEqualTo(UPDATED_MORAL);
        assertThat(testTcValida.getFisica()).isEqualTo(UPDATED_FISICA);
        assertThat(testTcValida.getIsr()).isEqualTo(UPDATED_ISR);
        assertThat(testTcValida.getIva()).isEqualTo(UPDATED_IVA);
        assertThat(testTcValida.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTcValida() throws Exception {
        int databaseSizeBeforeUpdate = tcValidaRepository.findAll().size();

        // Create the TcValida
        TcValidaDTO tcValidaDTO = tcValidaMapper.toDto(tcValida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTcValidaMockMvc.perform(put("/api/tc-validas").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tcValidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TcValida in the database
        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTcValida() throws Exception {
        // Initialize the database
        tcValidaRepository.saveAndFlush(tcValida);

        int databaseSizeBeforeDelete = tcValidaRepository.findAll().size();

        // Delete the tcValida
        restTcValidaMockMvc.perform(delete("/api/tc-validas/{id}", tcValida.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TcValida> tcValidaList = tcValidaRepository.findAll();
        assertThat(tcValidaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
