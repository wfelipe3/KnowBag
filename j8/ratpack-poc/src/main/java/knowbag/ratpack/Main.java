package knowbag.ratpack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import knowbag.ratpack.model.User;
import knowbag.ratpack.model.UserRepository;
import ratpack.exec.Promise;
import ratpack.func.Block;
import ratpack.handling.Context;
import ratpack.server.RatpackServer;
import ratpack.spring.Spring;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by feliperojas on 8/28/15.
 */
public class Main {

    private static final Map<String, String> NOT_FOUND = new HashMap<String, String>() {{
        put("status", "404");
        put("message", "NO EMAIL ADDRESS SUPPLIED");
    }};

    private static final Map<String, String> NO_EMAIL = new HashMap<String, String>() {{
        put("status", "400");
        put("message", "NO EMAIL ADDRESS SUPPLIED");
    }};

    public static void main(String args[]) throws Exception {
        RatpackServer.start(spec -> spec
                        .registry(Spring.spring(SprintBootConfig.class))
                        .handlers(chain -> chain
                                        .prefix("api/users", pchain -> pchain
                                                        .prefix(":username", uchain -> uchain
                                                                        .all(ctx -> {
                                                                            String username = ctx.getPathTokens().get("username");
                                                                            UserRepository repository = ctx.get(UserRepository.class);
                                                                            ObjectMapper mapper = ctx.get(ObjectMapper.class);
                                                                            Promise<User> userPromise = ctx.blocking(() -> repository.findByUsername(username));
                                                                            ctx.byMethod(method -> method
                                                                                            .get(getUser(ctx, userPromise))
                                                                                            .put(putUser(ctx, repository, mapper, userPromise))
                                                                                            .delete(deleteUser(ctx, userPromise, repository))
                                                                            );
                                                                        })
                                                        )
                                        ).all(ctx -> {
                                            UserRepository repository = ctx.get(UserRepository.class);
                                            ObjectMapper mapper = ctx.get(ObjectMapper.class);
                                            ctx.byMethod(method -> method
                                                            .post(() -> {
                                                                String json = ctx.getRequest().getBody().getText();
                                                                User user = mapper.readValue(json, User.class);
                                                                ctx.blocking(() -> repository.save(user)).then(u1 -> sendUser(ctx, u1));
                                                            })
                                                            .get(() -> ctx.blocking(repository::findAll).then(users -> {
                                                                ctx.getResponse().contentType("application/json");
                                                                ctx.getResponse().send(mapper.writeValueAsBytes(users));
                                                            }))
                                            );
                                        })
                        )
        );
    }

    private static Block getUser(Context ctx, Promise<User> userPromise) {
        return () -> userPromise.then(user -> sendUser(ctx, user));
    }

    private static Block putUser(Context ctx, UserRepository repository, ObjectMapper mapper, Promise<User> userPromise) throws java.io.IOException {
        return () -> {
            String json = ctx.getRequest().getBody().getText();
            Map<String, String> body = mapper.readValue(json, new TypeReference<Map<String, String>>() {
            });
            if (body.containsKey("email")) {
                userPromise.map(user -> {
                    user.setEmail(body.get("email"));
                    return user;
                }).blockingMap(repository::save).then(u1 -> sendUser(ctx, u1));
            } else {
                ctx.getResponse().status(400);
                ctx.getResponse().send(mapper.writeValueAsBytes(NO_EMAIL));
            }
        };
    }

    private static Block deleteUser(Context ctx, Promise<User> userPromise, UserRepository repository) {
        return () -> {
            userPromise.blockingMap(user -> {
                repository.delete(user);
                return null;
            }).then(user -> {
                ctx.getResponse().status(204);
                ctx.getResponse().send();
            });
        };
    }

    private static void sendUser(Context context, User user) {
        if (user == null) {
            notFound(context);
        }

        ObjectMapper mapper = context.get(ObjectMapper.class);
        context.getResponse().contentType("application/json");
        try {
            context.getResponse().send(mapper.writeValueAsBytes(user));
        } catch (JsonProcessingException e) {
            context.getResponse().status(500);
            context.getResponse().send("Error serializing user to JSON");
        }
    }

    private static void notFound(Context context) {
        ObjectMapper mapper = context.get(ObjectMapper.class);
        context.getResponse().status(404);
        try {
            context.getResponse().send(mapper.writeValueAsBytes(NOT_FOUND));
        } catch (JsonProcessingException e) {
            context.getResponse().send();
        }
    }
}
