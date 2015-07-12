package knowbag.batch.restresources;

/**
 * Created by feliperojas on 6/10/15.
 */
public class RestResources {

    private static final EmptyRestResource EMPTY_REST_RESOURCE = new EmptyRestResource();

    public static RestResource empty() {
        return EMPTY_REST_RESOURCE;
    }
}
