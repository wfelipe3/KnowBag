package knowbag.pomodoro.devops;

import spark.Spark;

/**
 * Created by feliperojas on 1/7/16.
 */
public class PingApp {

    public static void main(String[] args) {
        Spark.port(6789);
        Spark.get("ping", (req, res) -> "reply");
    }

}
