package sat.gob.mx.agsc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TcManifesMapperTest {

    private TcManifesMapper tcManifesMapper;

    @BeforeEach
    public void setUp() {
        tcManifesMapper = new TcManifesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tcManifesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tcManifesMapper.fromId(null)).isNull();
    }
}
