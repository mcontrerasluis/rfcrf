package sat.gob.mx.agsc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TdGeneralTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TdGeneral.class);
        TdGeneral tdGeneral1 = new TdGeneral();
        tdGeneral1.setId(1L);
        TdGeneral tdGeneral2 = new TdGeneral();
        tdGeneral2.setId(tdGeneral1.getId());
        assertThat(tdGeneral1).isEqualTo(tdGeneral2);
        tdGeneral2.setId(2L);
        assertThat(tdGeneral1).isNotEqualTo(tdGeneral2);
        tdGeneral1.setId(null);
        assertThat(tdGeneral1).isNotEqualTo(tdGeneral2);
    }
}
