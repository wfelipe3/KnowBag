package photogram;

import spark.Spark;

/**
 * Created by feliperojas on 7/9/16.
 */
public class Main {

    public static void main(String args[]) {
        Spark.get("/photogram", (req, res) -> "photogram is the best");
    }

}
