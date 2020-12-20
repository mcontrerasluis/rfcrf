package sat.gob.mx.agsc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TcTipoImpMapperTest {

    private TcTipoImpMapper tcTipoImpMapper;

    @BeforeEach
    public void setUp() {
        tcTipoImpMapper = new TcTipoImpMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tcTipoImpMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tcTipoImpMapper.fromId(null)).isNull();
    }
}
