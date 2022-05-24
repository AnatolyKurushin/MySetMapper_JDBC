package com.my_project;

import java.sql.ResultSet;

public interface SetMapper<T> {
    T mapSet(ResultSet resultSet);
}
