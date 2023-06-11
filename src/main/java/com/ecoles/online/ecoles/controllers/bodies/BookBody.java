package com.ecoles.online.ecoles.controllers.bodies;

import lombok.NonNull;
import lombok.Value;

@Value
public class BookBody {
    @NonNull
    String title;
}
