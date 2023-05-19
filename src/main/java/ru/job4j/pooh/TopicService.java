package ru.job4j.pooh;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    private static final String GET = "GET";
    private static final String POST = "POST";

    private String addSubscriber(String nameOfTopic, String subscriber) {
        topics.putIfAbsent(nameOfTopic, new ConcurrentHashMap<>());
        topics.get(nameOfTopic).putIfAbsent(subscriber, new ConcurrentLinkedQueue<>());
        Optional<String> result = Optional.ofNullable(topics.get(nameOfTopic).get(subscriber).poll());
        return result.orElse("");
    }

    private void addData(String nameOfTopic, String data) {
        if (!nameOfTopic.equals(null)) {
            topics.get(nameOfTopic)
                    .values()
                    .forEach(queue -> queue.offer(data));
        }
    }

    @Override
    public Resp process(Req req) {
        String line = "";
        String status = "";
        if (POST.equals(req.httpRequestType())) {
            addData(req.getSourceName(), req.getParam());
        }
        if (GET.equals(req.httpRequestType())) {
            line = addSubscriber(req.getSourceName(), req.getParam());
            status = "".equals(line) ? "204" : "200";
        }
        return new Resp(line, status);
    }
}