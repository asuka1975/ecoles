package com.ecoles.online.ecoles.models;

import lombok.NonNull;
import lombok.Value;

@Value
public class Author {
    int id;
    @NonNull
    String name;
}
