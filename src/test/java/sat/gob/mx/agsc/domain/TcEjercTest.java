package sat.gob.mx.agsc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcEjercTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcEjerc.class);
        TcEjerc tcEjerc1 = new TcEjerc();
        tcEjerc1.setId(1L);
        TcEjerc tcEjerc2 = new TcEjerc();
        tcEjerc2.setId(tcEjerc1.getId());
        assertThat(tcEjerc1).isEqualTo(tcEjerc2);
        tcEjerc2.setId(2L);
        assertThat(tcEjerc1).isNotEqualTo(tcEjerc2);
        tcEjerc1.setId(null);
        assertThat(tcEjerc1).isNotEqualTo(tcEjerc2);
    }
}
