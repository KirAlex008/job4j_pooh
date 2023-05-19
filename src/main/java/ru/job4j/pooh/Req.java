package ru.job4j.pooh;

import java.util.ArrayList;
import java.util.List;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    private static final String GET = "GET";
    private static final String POST = "POST";
    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        List<String> list = new ArrayList<>();
        String[] partsOfReq  = content.split(System.lineSeparator());
        String[] partsOfLine = partsOfReq[0].split(" ");
        if (partsOfLine[0].equals(POST)) {
            list.add(partsOfLine[0]);
            String[] partsOfWord = partsOfLine[1].split("/");
            list.add(partsOfWord[1]);
            list.add(partsOfWord[2]);
            list.add(partsOfReq[7]);
        } else if (partsOfLine[0].equals(GET)) {
            list.add(partsOfLine[0]);
            String[] partsOfWord = partsOfLine[1].split("/");
            list.add(partsOfWord[1]);
            list.add(partsOfWord[2]);
            if (partsOfWord.length <= 3) {
                list.add("");
            } else {
                list.add(partsOfWord[3]);
            }
        }
        return new Req(list.get(0), list.get(1), list.get(2), list.get(3));
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}