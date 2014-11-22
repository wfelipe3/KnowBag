package knowbag.learn.concurrency

/**
 * Created by feliperojas on 19/11/14.
 */
class NotThreadSafeClass {

    int accum = 0

    void addToAccum() {
        accum = accum + 1
    }
}
