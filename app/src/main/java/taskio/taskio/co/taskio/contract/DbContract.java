package taskio.taskio.co.taskio.contract;

/**
 * Created by olaar on 6/10/16.
 */
public class DbContract {

    public static final String DBNAME = "co.simplyio.taskio";
    public static final int dbVersion = 2;
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";

    public class TaskListTable {
        public static final String TABLE_NAME = "tasklist";
        public static final String TASK_ID_COL = "task_id";
        public static final String TASK_TITLE_COL = "task_title";
        public static final String TASK_COMPLETED_COL = "task_completed";
        public static final String TASK_NO_MILESTONE_COL = "task_no_milestone";
        public static final String TASK_DESCRIPTION_COL = "task_descr";

        public String getCreateTableQuery() {
            String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,%s TEXT, %s TEXT, %s INTEGER DEFAULT 0,  %s DATETIME DEFAULT CURRENT_TIMESTAMP)",
                    TABLE_NAME, TASK_ID_COL, TASK_TITLE_COL, TASK_DESCRIPTION_COL, TASK_NO_MILESTONE_COL, DbContract.UPDATED_AT);
            return query;
        }

        public String getDeleteTableQuery(){
            String query = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
            return query;
        }
    }

    public class TaskMilestoneTable {
        public String TABLE_NAME = "task_milestone";
        public String TASK_ID_COL = "task_id";
        public String TASK_MILESTONE_COL = "task_milestone";
        public String TASK_MILESTONE_DESCR = "task_milestone_descr";
    }

}
