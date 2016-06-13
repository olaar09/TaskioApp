package taskio.taskio.co.taskio.controller;

/**
 * Created by olaar on 6/10/16.
 */
public class TaskListController {
    public int _taskId, _completed;
    public String _taskTitle, _taskDescr;

    public TaskListController(int _taskId, String _taskTitle, String _taskDescr) {
        this._taskId = _taskId;
        this._taskTitle = _taskTitle;
        this._taskDescr = _taskDescr;
    }

    public TaskListController(int _taskId, String _taskTitle, String _taskDescr,int _completed) {
        this._taskId = _taskId;
        this._taskTitle = _taskTitle;
        this._taskDescr = _taskDescr;
        this._completed = _completed;
    }

    public String get_taskTitle() {
        return _taskTitle;
    }

    public String get_taskDescr() {
        return _taskDescr;
    }

    public int get_completed(){
       return _completed;
    }
    public int get_taskId() {
        return this._taskId;
    }

    public void set_taskId(int _taskId) {
        this._taskId = _taskId;
    }

    public void set_taskTitle(String _taskTitle) {
        this._taskTitle = _taskTitle;
    }

    public void set_taskDescr(String _taskDescr) {
        this._taskDescr = _taskDescr;
    }
}
