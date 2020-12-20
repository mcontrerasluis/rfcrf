package sat.gob.mx.agsc.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sat.gob.mx.agsc.web.rest.TestUtil;

public class TdRegFrontTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TdRegFront.class);
        TdRegFront tdRegFront1 = new TdRegFront();
        tdRegFront1.setId(1L);
        TdRegFront tdRegFront2 = new TdRegFront();
        tdRegFront2.setId(tdRegFront1.getId());
        assertThat(tdRegFront1).isEqualTo(tdRegFront2);
        tdRegFront2.setId(2L);
        assertThat(tdRegFront1).isNotEqualTo(tdRegFront2);
        tdRegFront1.setId(null);
        assertThat(tdRegFront1).isNotEqualTo(tdRegFront2);
    }
}
