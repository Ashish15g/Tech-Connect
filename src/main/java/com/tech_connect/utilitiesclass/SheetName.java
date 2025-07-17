package com.tech_connect.utilitiesclass;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SheetName {
    String value();
}