package sat.gob.mx.agsc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TdGeneralDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TdGeneralDTO.class);
        TdGeneralDTO tdGeneralDTO1 = new TdGeneralDTO();
        tdGeneralDTO1.setId(1L);
        TdGeneralDTO tdGeneralDTO2 = new TdGeneralDTO();
        assertThat(tdGeneralDTO1).isNotEqualTo(tdGeneralDTO2);
        tdGeneralDTO2.setId(tdGeneralDTO1.getId());
        assertThat(tdGeneralDTO1).isEqualTo(tdGeneralDTO2);
        tdGeneralDTO2.setId(2L);
        assertThat(tdGeneralDTO1).isNotEqualTo(tdGeneralDTO2);
        tdGeneralDTO1.setId(null);
        assertThat(tdGeneralDTO1).isNotEqualTo(tdGeneralDTO2);
    }
}
