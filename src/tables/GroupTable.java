package src.tables;
import db.IDBExecutor;

public class GroupTable extends AbsTable {
    public GroupTable(IDBExecutor idbExecutor) {
        super("`group`", idbExecutor);
    }
}
