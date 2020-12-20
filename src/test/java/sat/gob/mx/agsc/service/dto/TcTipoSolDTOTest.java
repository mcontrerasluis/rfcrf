package sat.gob.mx.agsc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcTipoSolDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcTipoSolDTO.class);
        TcTipoSolDTO tcTipoSolDTO1 = new TcTipoSolDTO();
        tcTipoSolDTO1.setId(1L);
        TcTipoSolDTO tcTipoSolDTO2 = new TcTipoSolDTO();
        assertThat(tcTipoSolDTO1).isNotEqualTo(tcTipoSolDTO2);
        tcTipoSolDTO2.setId(tcTipoSolDTO1.getId());
        assertThat(tcTipoSolDTO1).isEqualTo(tcTipoSolDTO2);
        tcTipoSolDTO2.setId(2L);
        assertThat(tcTipoSolDTO1).isNotEqualTo(tcTipoSolDTO2);
        tcTipoSolDTO1.setId(null);
        assertThat(tcTipoSolDTO1).isNotEqualTo(tcTipoSolDTO2);
    }
}
