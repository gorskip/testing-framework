package pl.pg.config.rest;

import lombok.Data;

@Data
public class Type {

    private String status;
    private String body;
    private String headers;
    private boolean withOrder;
}
