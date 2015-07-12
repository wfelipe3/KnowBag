package knowbag.batch.restresources;

/**
 * Created by feliperojas on 6/10/15.
 */
public class RegularRestResourceBuilder implements RestResourceBuilder {

    private String name;

    public RegularRestResourceBuilder(String name) {
        this.name = name;
    }

    @Override
    public RestResource build() {
        return new RegularRestResource(name);
    }

    @Override
    public RestResourceBuilder withResource(RestResourceBuilder resource) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
