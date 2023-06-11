package com.ecoles.online.ecoles.models;

import java.util.Date;

import io.micrometer.common.lang.NonNull;
import lombok.Value;

@Value
public class Book {
    int id;
    @NonNull
    String title;
    @NonNull
    Date createdAt;
}
