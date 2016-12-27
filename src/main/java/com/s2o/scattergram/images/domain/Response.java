package com.s2o.scattergram.images.domain;

/**
 * Created by blancof1 on 02/11/2016.
 */
public class Response {
    private final long id;
    private final String content;

    /**
     * constructor
     * @param id
     * @param content
     */
    public Response(long id, String content){
        this.id=id;
        this.content = content;
    }

    /**
     * get the id of the response
     * @return
     */
    public long getId() {
        return id;
    }

    /**
     * get the content of the response
     * @return
     */
    public String getContent() {
        return content;
    }



}
