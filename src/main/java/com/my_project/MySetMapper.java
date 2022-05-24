package com.my_project;


import com.my_project.domain.Employee;
import com.my_project.domain.FullName;
import com.my_project.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MySetMapper implements SetMapper<Set<Employee>> {
    private static final String ID = "ID";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String MIDDLENAME = "MIDDLENAME";
    private static final String POSITION = "POSITION";
    private static final String HIREDATE = "HIREDATE";
    private static final String SALARY = "SALARY";
    private static final String MANAGER = "MANAGER";
    private static int rowCount;
    private final Set<Employee> result = new HashSet<>();

    @Override
    public Set<Employee> mapSet(ResultSet resultSet) {
        rowCount = getRowCount(resultSet);
        Employee employee = null;

        try {
            for (int i = 1; i <= rowCount; i++) {
                resultSet.next();
                Employee manager = null;

                if (resultSet.getInt(MANAGER) != 0) {
                    manager = getManager(resultSet, resultSet.getInt(MANAGER));
                }
                resultSet.absolute(i);

                employee = new Employee(
                        BigInteger.valueOf(resultSet.getInt(ID)),
                        new FullName(resultSet.getString(FIRSTNAME),
                                resultSet.getString(LASTNAME),
                                resultSet.getString(MIDDLENAME)),
                        Position.valueOf(resultSet.getString(POSITION)),
                        resultSet.getDate(HIREDATE).toLocalDate(),
                        BigDecimal.valueOf(resultSet.getDouble(SALARY)),
                        manager
                );

                result.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return Collections.unmodifiableSet(result);
    }

    private Employee getManager(ResultSet resultSet, int numberOfManager) throws SQLException {
        Employee result = null;
        resultSet.beforeFirst();

        for (int i = 1; i <= rowCount; i++) {
            resultSet.next();

            if (resultSet.getInt(ID) == numberOfManager) {

                Employee managerOfManager = null;

                if (resultSet.getInt(MANAGER) != 0) {
                    managerOfManager = getManager(resultSet, resultSet.getInt(MANAGER));
                }

                resultSet.absolute(i);

                result = new Employee(
                        BigInteger.valueOf(resultSet.getInt(ID)),
                        new FullName(resultSet.getString(FIRSTNAME),
                                resultSet.getString(LASTNAME),
                                resultSet.getString(MIDDLENAME)),
                        Position.valueOf(resultSet.getString(POSITION)),
                        resultSet.getDate(HIREDATE).toLocalDate(),
                        BigDecimal.valueOf(resultSet.getDouble(SALARY)),
                        managerOfManager
                );
            }
        }

        return result;
    }

    private int getRowCount(ResultSet resultSet) {
        int result = 0;
        try {
            resultSet.last();
            result = resultSet.getRow();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                resultSet.beforeFirst();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }

        return result;
    }
}
