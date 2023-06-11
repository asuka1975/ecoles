package com.ecoles.online.ecoles.repositories.records;

import lombok.NonNull;
import lombok.Value;

@Value
public class BookRecord {
    int id;
    @NonNull
    String title;
}
