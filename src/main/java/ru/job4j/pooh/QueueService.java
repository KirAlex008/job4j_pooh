package ru.job4j.pooh;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    private static final String GET = "GET";
    private static final String POST = "POST";

    private boolean add(String name, String value) {
        queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
        return queue.get(name).offer(value);
    }

    private Optional<String> extract(String name) {
        queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());
        return Optional.ofNullable(queue.get(name).poll());
    }

    @Override
    public Resp process(Req req) {
        String line = "";
        String status = "";
        if (POST.equals(req.httpRequestType())) {
            add(req.getSourceName(), req.getParam());
        }
        if (GET.equals(req.httpRequestType())) {
            line = extract(req.getSourceName()).orElse("");
            status = "".equals(line) ? "204" : "200";
        }
        return new Resp(line, status);
    }
}