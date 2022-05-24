# MySetMapper_JDBC
Task:
Implement SetMapperFactory.employeesSetMapper method.

It should return a SetMapper instance that maps whole result set to a set of Employee instances.

It should perform cursor moving in order to get all Employee instances. Consider result set to be fully scrollable (back and forward, begin, end, etc.). If an Employee has a Manager it should contain it as Employee instance as well.
