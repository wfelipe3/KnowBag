package knowbag.batch.restresources;

import java.util.Map;

/**
 * Created by feliperojas on 6/10/15.
 */
public class RootRestResource implements RestResource {

    private Map<String, RestResource> resources;
    private String[] names;

    public RootRestResource(String[] names, Map<String, RestResource> resources) {
        this.names = names;
        this.resources = resources;
    }

    @Override
    public <T> T execute(RestPath path, Request request) {
        if (path.isLastResource())
            return new Response(404);
        return resources.getOrDefault(path.head().getResource(), RestResources.empty()).execute(path.tail(), request);
    }


}
