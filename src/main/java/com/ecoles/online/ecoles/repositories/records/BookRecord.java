package com.ecoles.online.ecoles.repositories.records;

import lombok.NonNull;
import lombok.Value;

@Value
public class BookRecord {
    long id;
    @NonNull
    String title;
}
