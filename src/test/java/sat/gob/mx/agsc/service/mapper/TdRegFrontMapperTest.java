package sat.gob.mx.agsc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TdRegFrontMapperTest {

    private TdRegFrontMapper tdRegFrontMapper;

    @BeforeEach
    public void setUp() {
        tdRegFrontMapper = new TdRegFrontMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tdRegFrontMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tdRegFrontMapper.fromId(null)).isNull();
    }
}
