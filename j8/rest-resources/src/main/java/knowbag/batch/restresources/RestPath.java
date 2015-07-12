package knowbag.batch.restresources;

/**
 * Created by feliperojas on 6/10/15.
 */
public class RestPath {

    private String path;
    private HttpMethod method;

    private RestPath(String path, HttpMethod method) {
        this.path = path;
        this.method = method;
    }

    public static RestPath newRestPath(String path, HttpMethod method) {
        return new RestPath(path, method);
    }

    public String getResource() {
        return path;
    }

    public RestPath head() {
        if (path.contains("/")) {
            String[] tokens = path.split("/", 2);
            return newRestPath(tokens[0], HttpMethod.NONE);
        } else {
            return this;
        }
    }

    public RestPath tail() {
        if (path.contains("/")) {
            String[] tokens = path.split("/", 2);
            return newRestPath(tokens[1], method);
        } else {
            return this;
        }
    }
}
