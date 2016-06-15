package taskio.taskio.co.taskio.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import taskio.taskio.co.taskio.contract.DbContract;
import taskio.taskio.co.taskio.controller.MilestoneController;
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
        DbContract.TaskMilestoneTable taskMilestoneTable = dbContract.new TaskMilestoneTable();

        sql.execSQL(taskListTable.getCreateTableQuery());
        sql.execSQL(taskMilestoneTable.getCreateTableQuery());
    }

    public void onUpgrade(SQLiteDatabase sql, int oldVersion, int newVersion) {
        DbContract.TaskListTable taskListTable = dbContract.new TaskListTable(); // get contract inner class
        DbContract.TaskMilestoneTable taskMilestoneTable = dbContract.new TaskMilestoneTable();

        sql.execSQL(taskListTable.getDeleteTableQuery());
        sql.execSQL(taskMilestoneTable.getDeleteTableQuery());
        onCreate(sql);
    }

    public void onDowngrade(SQLiteDatabase sql, int oldVersion, int newVersion) {
        DbContract.TaskListTable taskListTable = dbContract.new TaskListTable(); // get contract inner class
        DbContract.TaskMilestoneTable taskMilestoneTable = dbContract.new TaskMilestoneTable();

        sql.execSQL(taskListTable.getDeleteTableQuery());
        sql.execSQL(taskMilestoneTable.getDeleteTableQuery());
        onCreate(sql);
    }

    public long addNewTask(TaskListController taskListController) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.TaskListTable.TASK_TITLE_COL, taskListController.get_taskTitle());

        long returnRow = sqLiteDatabase.insert(DbContract.TaskListTable.TABLE_NAME, null, values);
        return returnRow;
    }

    public List<TaskListController> getTask(TaskListController taskListController) {
        List<TaskListController> task = new ArrayList<TaskListController>();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String[] cols = {DbContract.TaskListTable.TASK_ID_COL, DbContract.TaskListTable.TASK_TITLE_COL, DbContract.TaskListTable.TASK_COMPLETED_COL};
        String[] arr = {"" + taskListController.get_taskId()};

        Cursor cur = sqLiteDatabase.query(DbContract.TaskListTable.TABLE_NAME, cols, "task_id = ?", arr, null, null, null);

        if (cur.moveToFirst()) {
            task.add(new TaskListController(cur.getInt(0), cur.getString(1), cur.getInt(2)));
        }

        return task;
    }

    public List<TaskListController> getAllTasks() {
        List<TaskListController> taskList = new ArrayList<TaskListController>();

        String query = "SELECT * FROM " + DbContract.TaskListTable.TABLE_NAME + " ORDER BY " + DbContract.TaskListTable.TASK_ID_COL + " DESC";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cr = sqLiteDatabase.rawQuery(query, null);
        if (cr.moveToFirst()) {
            do {
                taskList.add(new TaskListController(cr.getInt(0), cr.getString(1), cr.getInt(2)));
            } while (cr.moveToNext());
        }

        return taskList;
    }

    public boolean updateTask(TaskListController taskListController) { // change to sql helper method "update() later"

        String query = "UPDATE " + DbContract.TaskListTable.TABLE_NAME + " SET " + DbContract.TaskListTable.TASK_TITLE_COL + " = ? " +
                " WHERE " + DbContract.TaskListTable.TASK_ID_COL + " = ? ";

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        SQLiteStatement stmt = sqLiteDatabase.compileStatement(query);
        stmt.bindString(1, taskListController.get_taskTitle());
        stmt.bindLong(2, taskListController.get_taskId());

        stmt.execute();

        return true;
    }

    public boolean deleteTask(TaskListController taskListController) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] arg = {"" + taskListController.get_taskId()};
        sqLiteDatabase.delete(DbContract.TaskListTable.TABLE_NAME, " task_id = ? ", arg);

        //
        sqLiteDatabase.delete(DbContract.TaskMilestoneTable.TABLE_NAME, " task_id = ? ", arg);
        return true;
    }

    public boolean taskCompleted(TaskListController taskListController,int completeOrNot , int onlyTask) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();

        // update tasklist table
        values.put(DbContract.TaskListTable.TASK_COMPLETED_COL, completeOrNot);
        String[] arg = {"" + taskListController.get_taskId()};
        sqLiteDatabase.update(DbContract.TaskListTable.TABLE_NAME, values, "task_id = ? ", arg);


        // update milestone table

        if (onlyTask == 0){
            sqLiteDatabase.update(DbContract.TaskMilestoneTable.TABLE_NAME, values, "task_id = ? ", arg);
        }
        return true;
    }

    public boolean mileStoneCompleted(int id, int completeOrNot) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DbContract.TaskMilestoneTable.TASK_MILESTONE_COMPLETED, completeOrNot);
        String[] arg = {"" + id};
        sqLiteDatabase.update(DbContract.TaskMilestoneTable.TABLE_NAME, values, "task_milestone_id = ?", arg);


        return true;
    }


    public boolean redoCompletedTask(TaskListController taskListController) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(DbContract.TaskListTable.TASK_COMPLETED_COL, 0);
        String[] arg = {"" + taskListController.get_taskId()};
        sqLiteDatabase.update(DbContract.TaskListTable.TABLE_NAME, values, "task_id = ? ", arg);


        // update milestone table

        sqLiteDatabase.update(DbContract.TaskMilestoneTable.TABLE_NAME, values, "task_id = ? ", arg);

        return true;
    }


    public void addMileStone(MilestoneController milestoneController) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.TaskMilestoneTable.TASK_MILESTONE_COL, milestoneController.get_mileStone());
        values.put(DbContract.TaskMilestoneTable.TASK_ID_COL, milestoneController.get_task_id());

        sqLiteDatabase.insert(DbContract.TaskMilestoneTable.TABLE_NAME, null, values);
    }

    public List<MilestoneController> getAllMilestone(MilestoneController milestoneController) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        List<MilestoneController> getTaskMileStones = new ArrayList<MilestoneController>();

        String[] cols = {DbContract.TaskMilestoneTable.TASK_MILESTONE_ID_COL, DbContract.TaskMilestoneTable.TASK_ID_COL, DbContract.TaskMilestoneTable.TASK_MILESTONE_COL, DbContract.TaskMilestoneTable.TASK_MILESTONE_COMPLETED};
        String[] arr = {"" + milestoneController.get_task_id()};

        Cursor cur = sqLiteDatabase.query(DbContract.TaskMilestoneTable.TABLE_NAME, cols, "task_id = ?", arr, null, null, null);
        if (cur.moveToFirst()) {
            do {
                Log.d("hhh =>","" + cur.getInt(0));
                getTaskMileStones.add(new MilestoneController(cur.getInt(0), cur.getString(2), cur.getInt(3), cur.getLong(1)));
            } while (cur.moveToNext());
        }

        return getTaskMileStones;
    }

    public List<MilestoneController> getCompletedMilestones(MilestoneController milestoneController) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        List<MilestoneController> getCompletedTaskMileStones = new ArrayList<MilestoneController>();

        String[] cols = {DbContract.TaskMilestoneTable.TASK_ID_COL, DbContract.TaskMilestoneTable.TASK_MILESTONE_COL, DbContract.TaskMilestoneTable.TASK_MILESTONE_COMPLETED, DbContract.TaskMilestoneTable.TASK_MILESTONE_ID_COL};
        String[] arr = {"" + milestoneController.get_task_id(), "1"};

        Cursor cur = sqLiteDatabase.query(DbContract.TaskMilestoneTable.TABLE_NAME, cols, "task_id = ? AND task_milestone_completed = ?", arr, null, null, null);
        if (cur.moveToFirst()) {
            do {
                getCompletedTaskMileStones.add(new MilestoneController(cur.getInt(0), cur.getString(1), cur.getInt(2), cur.getLong(3)));
            } while (cur.moveToNext());
        }

        return getCompletedTaskMileStones;
    }


    public boolean deleteMileStone(MilestoneController milestoneController) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] arg = {"" + milestoneController.get_mileStoneId()};
        sqLiteDatabase.delete(DbContract.TaskMilestoneTable.TABLE_NAME, " task_milestone_id = ? ", arg);
        return true;
    }
}
