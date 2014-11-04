package knowbag.j8.functional;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by feliperojas on 3/11/14.
 */
public class FunctiontalInterfacesLearn {

    @Test
    public void learn_to_create_and_user_a_functional_interface() throws IOException {
        String firstLine = readTestFile((BufferedReader r) -> r.readLine());
        String twoLines = readTestFile((BufferedReader r) -> r.readLine() + r.readLine());
        assertThat(firstLine, is("This was created in order to contain all the information related to my studies, the idea is to try"));
        assertThat(twoLines, is("This was created in order to contain all the information related to my studies, the idea is to try" +
                "to gather as much as I can so it be easy to re-visit previous study sessions and learning topics."));
    }

    @FunctionalInterface
    private interface FunctionalFileReader {
        public String readFile(BufferedReader reader) throws IOException;
    }

    private String readTestFile(FunctionalFileReader f) throws IOException {
        URL resource = String.class.getResource("/fileTest.txt");
        try (BufferedReader r = new BufferedReader(new FileReader(resource.getPath()))) {
            return f.readFile(r);
        }
    }

}
