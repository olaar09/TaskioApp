package taskio.taskio.co.taskio.controller;

/**
 * Created by olaar on 6/13/16.
 */
public class MilestoneController {

    public String _mileStone;
    public int _mileStoneId,_mileStoneComleted;
    public long _task_id;

    public MilestoneController() {

    }

    public MilestoneController(int _mileStoneId, String _mileStone, int _mileStoneComleted,long _task_id) {
        this._mileStone = _mileStone;
        this._mileStoneId = _mileStoneId;
        this._mileStoneComleted = _mileStoneComleted;
        this._task_id = _task_id;
    }

    public String get_mileStone() {
        return this._mileStone;
    }

    public int get_mileStoneId() {
        return this._mileStoneId;
    }

    public int get_mileStoneComleted() {
        return this._mileStoneComleted;
    }

    public long get_task_id(){
        return this._task_id;
    }

    public void set_mileStoneComleted(int _mileStoneComleted) {
        this._mileStoneComleted = _mileStoneComleted;
    }

    public void set_mileStone(String _mileStone) {
        this._mileStone = _mileStone;
    }

    public void set_task_id(long _task_id){
        this._task_id = _task_id;
    }
}
