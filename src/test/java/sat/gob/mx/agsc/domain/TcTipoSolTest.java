package sat.gob.mx.agsc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcTipoSolTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcTipoSol.class);
        TcTipoSol tcTipoSol1 = new TcTipoSol();
        tcTipoSol1.setId(1L);
        TcTipoSol tcTipoSol2 = new TcTipoSol();
        tcTipoSol2.setId(tcTipoSol1.getId());
        assertThat(tcTipoSol1).isEqualTo(tcTipoSol2);
        tcTipoSol2.setId(2L);
        assertThat(tcTipoSol1).isNotEqualTo(tcTipoSol2);
        tcTipoSol1.setId(null);
        assertThat(tcTipoSol1).isNotEqualTo(tcTipoSol2);
    }
}
