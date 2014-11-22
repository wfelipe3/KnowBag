package knowbag.learn.concurrency

/**
 * Created by feliperojas on 19/11/14.
 */
class ImmutableClass {

    private int accum = 0

    ImmutableClass increment() {
        ImmutableClass immutableClass = new ImmutableClass()
        immutableClass.accum = accum + 1
        return immutableClass
    }

    int getAccum() {
        accum
    }

}
