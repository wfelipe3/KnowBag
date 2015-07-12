package knowbag.batch.restresources;

/**
 * Created by feliperojas on 6/10/15.
 */
final public class RestResourceBuilders {

    private RestResourceBuilders(){
    }

    public static RestResourceBuilder newRoot(String... names) {
        return new RootRestResourceBuilder(names);
    }

    public static RestResourceBuilder newResource(String name) {
        return new RegularRestResourceBuilder(name);
    }
}
