package sat.gob.mx.agsc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TcTipoSolMapperTest {

    private TcTipoSolMapper tcTipoSolMapper;

    @BeforeEach
    public void setUp() {
        tcTipoSolMapper = new TcTipoSolMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tcTipoSolMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tcTipoSolMapper.fromId(null)).isNull();
    }
}
