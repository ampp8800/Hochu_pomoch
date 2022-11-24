package com.ampp8800.hochupomoch.data;

//deleted class
public class DatabaseNewsRepository {
    private static DatabaseNewsRepository databaseNewsRepository;

    private DatabaseNewsRepository() {
    }

    public static DatabaseNewsRepository newInstance() {
        if (databaseNewsRepository == null) {
            databaseNewsRepository = new DatabaseNewsRepository();
        }
        return databaseNewsRepository;
    }

}
