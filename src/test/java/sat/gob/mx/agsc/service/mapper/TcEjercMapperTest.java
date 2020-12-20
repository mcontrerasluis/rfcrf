package sat.gob.mx.agsc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TcEjercMapperTest {

    private TcEjercMapper tcEjercMapper;

    @BeforeEach
    public void setUp() {
        tcEjercMapper = new TcEjercMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tcEjercMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tcEjercMapper.fromId(null)).isNull();
    }
}
