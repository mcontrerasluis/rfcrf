package sat.gob.mx.agsc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcTipoImpTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcTipoImp.class);
        TcTipoImp tcTipoImp1 = new TcTipoImp();
        tcTipoImp1.setId(1L);
        TcTipoImp tcTipoImp2 = new TcTipoImp();
        tcTipoImp2.setId(tcTipoImp1.getId());
        assertThat(tcTipoImp1).isEqualTo(tcTipoImp2);
        tcTipoImp2.setId(2L);
        assertThat(tcTipoImp1).isNotEqualTo(tcTipoImp2);
        tcTipoImp1.setId(null);
        assertThat(tcTipoImp1).isNotEqualTo(tcTipoImp2);
    }
}
