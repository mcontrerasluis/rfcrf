package sat.gob.mx.agsc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcValidaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcValidaDTO.class);
        TcValidaDTO tcValidaDTO1 = new TcValidaDTO();
        tcValidaDTO1.setId(1L);
        TcValidaDTO tcValidaDTO2 = new TcValidaDTO();
        assertThat(tcValidaDTO1).isNotEqualTo(tcValidaDTO2);
        tcValidaDTO2.setId(tcValidaDTO1.getId());
        assertThat(tcValidaDTO1).isEqualTo(tcValidaDTO2);
        tcValidaDTO2.setId(2L);
        assertThat(tcValidaDTO1).isNotEqualTo(tcValidaDTO2);
        tcValidaDTO1.setId(null);
        assertThat(tcValidaDTO1).isNotEqualTo(tcValidaDTO2);
    }
}
