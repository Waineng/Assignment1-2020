import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

/**
 * A couple of basic unit-tests that your code should pass
 * The grader will test your code with additional tests
 *
 */
public class SampleTest {
    //the below code is adapted from the following reference
    //testing system.out using junit:
    //https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println

    private final ByteArrayOutputStream outData = new ByteArrayOutputStream();
    private final PrintStream origOutput = System.out;

    private final static String FILE_PATH = "src" + File.separator + "test" + File.separator +
            "resources" + File.separator + "sample_games";

    @Before
    public void init() {
        System.setOut(new PrintStream(outData));
    }

    @After
    public void restore() {
        System.setOut(origOutput);
    }

    @Test
    public void testEchoArguments() {
        String[] args = {FILE_PATH, "1", "2", "K.Leonard"};
        ShootingStreak.main(args);
        String allOutput = outData.toString();
        assertTrue(allOutput.startsWith(FILE_PATH+ " 1 2 K.Leonard"));
    }

    @Test
    public void test3MissesOnly() {
        String[] args = {FILE_PATH, "1", "2", "K.Leonard"};
        ShootingStreak.main(args);
        String allOutput = outData.toString();
        assertTrue(allOutput.contains("K.Leonard 3 0"));
    }

    @Test
    public void testPlayerWithNoShots() {
        String[] args = {FILE_PATH, "1", "2", "KLeonard"};//typo in name on purpose
        ShootingStreak.main(args);
        String allOutput = outData.toString();
        assertTrue(allOutput.contains("KLeonard 0 0"));
    }
}
