import hello.threads.MyThreadsFactory;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by williame on 7/3/17.
 */
public class MyThreadsFactoryTest {

    @Test
    public void getListOfRunningThreads(){
        MyThreadsFactory factory = new MyThreadsFactory();
        List<String> executionResults = factory.createAndExecuteThreads(1);
        executionResults.forEach(System.out::println);
        Assert.assertThat(executionResults, CoreMatchers.hasItems("Hello main","Hello Thread-0","Done!"));
    }

    @Test(expected = IllegalThreadStateException.class)
    public void executeListOfRunningThreadsTwice(){
        MyThreadsFactory factory = new MyThreadsFactory();
        factory.createAndExecuteThreads(2);
    }

}
