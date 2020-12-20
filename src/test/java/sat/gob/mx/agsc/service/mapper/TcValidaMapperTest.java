package sat.gob.mx.agsc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TcValidaMapperTest {

    private TcValidaMapper tcValidaMapper;

    @BeforeEach
    public void setUp() {
        tcValidaMapper = new TcValidaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tcValidaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tcValidaMapper.fromId(null)).isNull();
    }
}
