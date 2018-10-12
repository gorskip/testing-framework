package pl.pg.client.rest;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.JsonNode;

import java.util.List;

public interface Response<T> {

    int getStatus();

    T getBody();

    JsonNode getRawBody();

    Headers getHeaders();

    List getBodyAsList();

}
