package sat.gob.mx.agsc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TcValidaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TcValida.class);
        TcValida tcValida1 = new TcValida();
        tcValida1.setId(1L);
        TcValida tcValida2 = new TcValida();
        tcValida2.setId(tcValida1.getId());
        assertThat(tcValida1).isEqualTo(tcValida2);
        tcValida2.setId(2L);
        assertThat(tcValida1).isNotEqualTo(tcValida2);
        tcValida1.setId(null);
        assertThat(tcValida1).isNotEqualTo(tcValida2);
    }
}
