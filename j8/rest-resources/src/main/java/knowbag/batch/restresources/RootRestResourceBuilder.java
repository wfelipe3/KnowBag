package knowbag.batch.restresources;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by feliperojas on 6/10/15.
 */
public class RootRestResourceBuilder implements RestResourceBuilder {

    private Map<String, RestResourceBuilder> resources;
    private String[] names;

    public RootRestResourceBuilder(String[] names) {
        this.resources = new HashMap<>();
        this.names = names;
    }

    @Override
    public RestResource build() {
        return new RootRestResource(names, resources.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().build())));
    }

    @Override
    public RestResourceBuilder withResource(RestResourceBuilder resource) {
        resources.put(resource.getName(), resource);
        return this;
    }

    @Override
    public String getName() {
        return Arrays.asList(names).stream().collect(Collectors.joining(":"));
    }
}
