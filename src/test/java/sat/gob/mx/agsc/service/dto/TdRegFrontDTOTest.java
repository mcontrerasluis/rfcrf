package sat.gob.mx.agsc.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TdRegFrontDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TdRegFrontDTO.class);
        TdRegFrontDTO tdRegFrontDTO1 = new TdRegFrontDTO();
        tdRegFrontDTO1.setId(1L);
        TdRegFrontDTO tdRegFrontDTO2 = new TdRegFrontDTO();
        assertThat(tdRegFrontDTO1).isNotEqualTo(tdRegFrontDTO2);
        tdRegFrontDTO2.setId(tdRegFrontDTO1.getId());
        assertThat(tdRegFrontDTO1).isEqualTo(tdRegFrontDTO2);
        tdRegFrontDTO2.setId(2L);
        assertThat(tdRegFrontDTO1).isNotEqualTo(tdRegFrontDTO2);
        tdRegFrontDTO1.setId(null);
        assertThat(tdRegFrontDTO1).isNotEqualTo(tdRegFrontDTO2);
    }
}
