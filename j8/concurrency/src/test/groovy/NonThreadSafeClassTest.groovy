import knowbag.learn.concurrency.ImmutableClass
import knowbag.learn.concurrency.NotThreadSafeClass
import org.junit.Test

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.hamcrest.MatcherAssert.assertThat

/**
 * Created by feliperojas on 19/11/14.
 */
class NonThreadSafeClassTest {

    @Test
    void givenNonThreadSafeClass_With100000Threads_ThenCheckClassState() {
        NotThreadSafeClass notThreadSafeClass = new NotThreadSafeClass()

        100000.times {
            Thread.start {
                notThreadSafeClass.addToAccum()
            }
        }

        assertThat(notThreadSafeClass.accum, is(not(100000)))
    }

    @Test
    void givenImmutableClass_With100000Threads_ThenCheckClassState() {
        ImmutableClass notThreadSafeClass = new ImmutableClass()

        100000.times {
            Thread.start {
                notThreadSafeClass = notThreadSafeClass.increment()
            }
        }

        assertThat(notThreadSafeClass.accum, is(100000))
    }
}
