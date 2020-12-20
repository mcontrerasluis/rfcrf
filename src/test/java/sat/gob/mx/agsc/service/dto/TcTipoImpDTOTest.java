package sat.gob.mx.agsc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcTipoImpDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcTipoImpDTO.class);
        TcTipoImpDTO tcTipoImpDTO1 = new TcTipoImpDTO();
        tcTipoImpDTO1.setId(1L);
        TcTipoImpDTO tcTipoImpDTO2 = new TcTipoImpDTO();
        assertThat(tcTipoImpDTO1).isNotEqualTo(tcTipoImpDTO2);
        tcTipoImpDTO2.setId(tcTipoImpDTO1.getId());
        assertThat(tcTipoImpDTO1).isEqualTo(tcTipoImpDTO2);
        tcTipoImpDTO2.setId(2L);
        assertThat(tcTipoImpDTO1).isNotEqualTo(tcTipoImpDTO2);
        tcTipoImpDTO1.setId(null);
        assertThat(tcTipoImpDTO1).isNotEqualTo(tcTipoImpDTO2);
    }
}
