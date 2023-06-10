package com.ecoles.online.ecoles.models;

import io.micrometer.common.lang.NonNull;
import lombok.Value;

@Value
public class Book {
    long id;
    @NonNull
    String title;
}
