package taskio.taskio.co.taskio.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import taskio.taskio.co.taskio.contract.DbContract;
import taskio.taskio.co.taskio.controller.TaskListController;


/**
 * Created by olaar on 6/10/16.
 */
public class TaskListModel extends SQLiteOpenHelper {

    public DbContract dbContract = new DbContract();

    public TaskListModel(Context context) {
        super(context, DbContract.DBNAME, null, DbContract.dbVersion);
    }

    public void onCreate(SQLiteDatabase sql) {
        DbContract.TaskListTable taskListTable = dbContract.new TaskListTable(); // get contract inner class
        sql.execSQL(taskListTable.getCreateTableQuery());
    }

    public void onUpgrade(SQLiteDatabase sql, int oldVersion, int newVersion) {
        DbContract.TaskListTable taskListTable = dbContract.new TaskListTable(); // get contract inner class
        sql.execSQL(taskListTable.getDeleteTableQuery());
        onCreate(sql);
    }

    public void onDowngrade(SQLiteDatabase sql, int oldVersion, int newVersion) {
        DbContract.TaskListTable taskListTable = dbContract.new TaskListTable(); // get contract inner class
        sql.execSQL(taskListTable.getDeleteTableQuery());
        onCreate(sql);
    }

    public void addNewTask(TaskListController taskListController) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.TaskListTable.TASK_TITLE_COL, taskListController.get_taskTitle());
        values.put(DbContract.TaskListTable.TASK_DESCRIPTION_COL, taskListController.get_taskDescr());

        long newRowId = sqLiteDatabase.insert(DbContract.TaskListTable.TABLE_NAME, null, values);
    }

    public List<TaskListController> getTask() {
        List<TaskListController> task = new ArrayList<TaskListController>();

        return task;
    }

    public List<TaskListController> getAllTasks() {
        List<TaskListController> taskList = new ArrayList<TaskListController>();

        String query = "SELECT * FROM " + DbContract.TaskListTable.TABLE_NAME + " ORDER BY " + DbContract.TaskListTable.TASK_ID_COL + " DESC";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cr = sqLiteDatabase.rawQuery(query, null);
        if (cr.moveToFirst()) {
            do {
                taskList.add(new TaskListController(cr.getInt(0), cr.getString(1), cr.getString(2),cr.getInt(3)));
            } while (cr.moveToNext());
        }

        return taskList;
    }

    public boolean updateTask(TaskListController taskListController) { // change to sql helper method "update() later"

        String query = "UPDATE " + DbContract.TaskListTable.TABLE_NAME + " SET " + DbContract.TaskListTable.TASK_TITLE_COL + " = ? , " + DbContract.TaskListTable.TASK_DESCRIPTION_COL + " = ?  " +
                " WHERE " + DbContract.TaskListTable.TASK_ID_COL + " = ? ";

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        SQLiteStatement stmt = sqLiteDatabase.compileStatement(query);
        stmt.bindString(1, taskListController.get_taskTitle());
        stmt.bindString(2, taskListController.get_taskDescr());
        stmt.bindLong(3, taskListController.get_taskId());

        stmt.execute();

        return true;
    }

    public boolean deleteTask(TaskListController taskListController) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] arg = {"" + taskListController.get_taskId()};
        sqLiteDatabase.delete(DbContract.TaskListTable.TABLE_NAME, " task_id = ? ", arg);
        return true;
    }

    public boolean taskCompleted(TaskListController taskListController) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();

        // update tasklist table
        values.put(DbContract.TaskListTable.TASK_NO_MILESTONE_COL, 1);
        String[] arg = {"" + taskListController.get_taskId()};
        sqLiteDatabase.update(DbContract.TaskListTable.TABLE_NAME, values, "task_id = ? ", arg);

        // update milestone table

        return true;
    }

    public boolean redoCompletedTask(TaskListController taskListController) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();

        // update tasklist table
        values.put(DbContract.TaskListTable.TASK_NO_MILESTONE_COL, 0);
        String[] arg = {"" + taskListController.get_taskId()};
        sqLiteDatabase.update(DbContract.TaskListTable.TABLE_NAME, values, "task_id = ? ", arg);

        // update milestone table

        return true;
    }

}
