package com.my_project.domain;

import com.my_project.MySetMapper;
import com.my_project.SetMapper;

import java.util.Set;

public class SetMapperFactory {

    public SetMapper<Set<Employee>> employeesSetMapper() {
        return new MySetMapper();
    }
}

