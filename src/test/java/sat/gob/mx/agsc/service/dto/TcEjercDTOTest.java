package sat.gob.mx.agsc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcEjercDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcEjercDTO.class);
        TcEjercDTO tcEjercDTO1 = new TcEjercDTO();
        tcEjercDTO1.setId(1L);
        TcEjercDTO tcEjercDTO2 = new TcEjercDTO();
        assertThat(tcEjercDTO1).isNotEqualTo(tcEjercDTO2);
        tcEjercDTO2.setId(tcEjercDTO1.getId());
        assertThat(tcEjercDTO1).isEqualTo(tcEjercDTO2);
        tcEjercDTO2.setId(2L);
        assertThat(tcEjercDTO1).isNotEqualTo(tcEjercDTO2);
        tcEjercDTO1.setId(null);
        assertThat(tcEjercDTO1).isNotEqualTo(tcEjercDTO2);
    }
}
