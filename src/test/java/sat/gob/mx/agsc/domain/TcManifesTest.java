package sat.gob.mx.agsc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcManifesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcManifes.class);
        TcManifes tcManifes1 = new TcManifes();
        tcManifes1.setId(1L);
        TcManifes tcManifes2 = new TcManifes();
        tcManifes2.setId(tcManifes1.getId());
        assertThat(tcManifes1).isEqualTo(tcManifes2);
        tcManifes2.setId(2L);
        assertThat(tcManifes1).isNotEqualTo(tcManifes2);
        tcManifes1.setId(null);
        assertThat(tcManifes1).isNotEqualTo(tcManifes2);
    }
}
