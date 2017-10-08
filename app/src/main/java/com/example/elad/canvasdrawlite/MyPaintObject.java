package com.example.elad.canvasdrawlite;

/**
 * Created by elad on 02/07/2017.
 */

public class MyPaintObject {

    String jsoncode;
    String id;
    String time;
    String name;

    public MyPaintObject() {
    }

    public MyPaintObject(String jsoncode, String id, String time, String name) {
        this.jsoncode = jsoncode;
        this.id = id;
        this.time = time;
        this.name = name;
    }

    public String getJsoncode() {
        return jsoncode;
    }

    public void setJsoncode(String jsoncode) {
        this.jsoncode = jsoncode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

