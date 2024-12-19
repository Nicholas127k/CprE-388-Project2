package com.example.data_classes;

import java.io.Serializable;
/**
 *these are the super special user types(enum) in user helper class they get called to declare what kind of
 * user a user is
 */
public enum UserType implements Serializable {
    NONE,
    STUDENT,
    PROFESSOR,
    COUNSELOR
}
