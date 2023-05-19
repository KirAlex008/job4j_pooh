import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class TriggerTest {

    @Test
    public void test() {
        assertThat(1).isEqualTo(new Trigger().someLogic());
    }

}
