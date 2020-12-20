package sat.gob.mx.agsc.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TdGeneralMapperTest {

    private TdGeneralMapper tdGeneralMapper;

    @BeforeEach
    public void setUp() {
        tdGeneralMapper = new TdGeneralMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tdGeneralMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tdGeneralMapper.fromId(null)).isNull();
    }
}
