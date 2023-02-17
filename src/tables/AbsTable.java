package src.tables;

import db.IDBExecutor;

import java.util.List;


public abstract class AbsTable {

    private String tableName;
    private IDBExecutor dbExecutor;
    private String request;

    public AbsTable(String tableName, IDBExecutor dbExecutor) {
        this.tableName = tableName;
        this.dbExecutor = dbExecutor;
    }

    public void create(List<String> columns) {
        dbExecutor.execute(String.format("create table %s (%s)", tableName, String.join(",", columns)));
    }

    public void insert(Object object) {
        dbExecutor.execute(String.format("insert into %s values (%s)", tableName, object.toString()));
    }

    public void delete() {
        dbExecutor.execute(String.format("DROP TABLE IF EXISTS %s;", tableName));
    }

    public void update(String column1, String datanext, String column2, String datalast) {
        dbExecutor.execute(String.format("UPDATE %s SET %s='%s' WHERE %s='%s'", tableName, column1, datanext, column2, datalast));
    }

    public AbsTable selectCountAll() {
        String request = String.format("select count(*) from %s", tableName);
        dbExecutor.executeQuery(request);
        return this;
    }


    public AbsTable selectStudentsSex(String data, String column) {
        String request = (String.format("select %s from %s where sex = '%s'", column, tableName, data));
        dbExecutor.executeQuery(request);
        return this;
    }

    public AbsTable selectJoinThreeTable(String columns, String table1, String table2, String condition1, String table3, String condition2) {
        String request = (String.format("select %s from %s join %s on %s join %s on %s", columns, table1, table2, condition1, table3, condition2));
        dbExecutor.executeQuery(request);
        return this;
    }

    public AbsTable selectJoinTwoTable(String columns, String table1, String table2, String condition) {
        String request = (String.format("select %s from %s join %s on %s", columns, table1, table2, condition));
        dbExecutor.executeQuery(request);
        return this;
    }

}


