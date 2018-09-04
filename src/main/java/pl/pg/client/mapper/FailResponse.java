package pl.pg.client.mapper;

import com.mashape.unirest.http.Headers;

public class FailResponse<T> implements Response<T> {

    private final Exception e;

    public FailResponse(Exception e) {
        this.e = e;
    }


    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public T getBody() {
        return null;
    }

    @Override
    public Headers getHeaders() {
        return null;
    }

    public String getExceptionMessage() {
        return e.getMessage();
    }
}
