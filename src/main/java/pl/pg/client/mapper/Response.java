package pl.pg.client.mapper;

import com.mashape.unirest.http.Headers;

public interface Response<T> {

    int getStatus();

    T getBody();

    Headers getHeaders();

}
