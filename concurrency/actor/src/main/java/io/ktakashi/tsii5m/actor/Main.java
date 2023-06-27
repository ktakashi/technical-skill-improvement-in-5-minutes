package io.ktakashi.tsii5m.actor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Main {
    public static void main(String... args) throws Exception {
        var actorInfo = CatRetrieverFactory.createActor();
        var actor = actorInfo.actor();
        var in = actorInfo.inChannel();
        var out = actorInfo.outChannel();
        actor.start();
        in.put("200");
        in.put("404");
        System.out.println(out.take());
        System.out.println(out.take());
        System.out.println(out.poll());
        actor.stop();
    }
}

interface Actor {
    void start();
    void stop();
}

class LogicalActor<In, Out> implements Actor {
    private final BlockingQueue<In> in;
    private final BlockingQueue<Out> out;
    private final List<Thread> threads;

    LogicalActor(Function<In, Out> logic) {
        this.in = new LinkedBlockingQueue<>();
        this.out = new LinkedBlockingQueue<>();
        this.threads = IntStream.range(0, 10).mapToObj(i -> new Thread(makeRunnable(logic))).toList();
    }

    private Runnable makeRunnable(Function<In, Out> logic) {
        return () -> {
            while (true) {
                try {
                    out.put(logic.apply(in.take()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };
    }

    @Override
    public void start() {
        threads.forEach(Thread::start);
    }

    @Override
    public void stop() {
        threads.stream().peek(Thread::interrupt).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public BlockingQueue<In> getInChannel() {
        return in;
    }

    public BlockingQueue<Out> getOutChannel() {
        return out;
    }
}

record ActorInfo<T extends Actor, In, Out>(T actor, BlockingQueue<In> inChannel, BlockingQueue<Out> outChannel) {
}

class CatRetrieverFactory {
    private CatRetrieverFactory() {

    }
    public static ActorInfo<Actor, String, Optional<byte[]>> createActor() {
        var actor = new LogicalActor<>(makeRetrieveCat());
        return new ActorInfo<>(actor, actor.getInChannel(), actor.getOutChannel());
    }

    private static Function<String, Optional<byte[]>> makeRetrieveCat() {
        var client = HttpClient.newHttpClient();
        return status -> {
            var httpRequest = HttpRequest.newBuilder().GET().uri(URI.create("https://http.cat/" + status)).build();
            try {
                var response = client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
                return Optional.ofNullable(response.body());
            } catch (IOException e) {
                return Optional.empty();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return Optional.empty();
            }
        };
    }
}
