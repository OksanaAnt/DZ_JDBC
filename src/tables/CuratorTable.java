package src.tables;
import db.IDBExecutor;

public class CuratorTable extends AbsTable {

    public CuratorTable(IDBExecutor idbExecutor) {
        super("curator", idbExecutor);
    }
}
