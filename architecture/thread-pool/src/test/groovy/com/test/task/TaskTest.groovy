package com.test.task
/**
 * Created by feliperojas on 22/11/14.
 */
class TaskTest {

    static void main(String[] args) {

        StringBuilder builder = new StringBuilder()


        TimerTask task = new TimerTask() {
            @Override
            void run() {
                synchronized (builder) {
                    builder.append(new Date().toString())
                    builder.append("\n")
                    builder.notify()
                }
            }
        }


        Timer timer = new Timer()
        timer.schedule(task, 0, 1000)

        Thread.start {
            while (true) {
                synchronized (builder) {
                    println builder.toString()
                    builder.wait()
                }
            }
        }
    }
}
