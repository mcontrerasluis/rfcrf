package sat.gob.mx.agsc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcManifesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcManifesDTO.class);
        TcManifesDTO tcManifesDTO1 = new TcManifesDTO();
        tcManifesDTO1.setId(1L);
        TcManifesDTO tcManifesDTO2 = new TcManifesDTO();
        assertThat(tcManifesDTO1).isNotEqualTo(tcManifesDTO2);
        tcManifesDTO2.setId(tcManifesDTO1.getId());
        assertThat(tcManifesDTO1).isEqualTo(tcManifesDTO2);
        tcManifesDTO2.setId(2L);
        assertThat(tcManifesDTO1).isNotEqualTo(tcManifesDTO2);
        tcManifesDTO1.setId(null);
        assertThat(tcManifesDTO1).isNotEqualTo(tcManifesDTO2);
    }
}
