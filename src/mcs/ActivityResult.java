package mcs;

import schedule.Activity;

public class ActivityResult {
    String actID;
    String actName;
    int duration;
    double cost;
    double quantity;
    int ES;
    int EF;
    int LS;
    int LF;
    int TF;



    public ActivityResult(Activity a){
        this.actID = a.activityID;
        this.actName = a.activityName;
        this.duration = a.actualDuration;
        this.ES = a.net.cpDate[0];
        this.EF = a.net.cpDate[1];
        this.LS = a.net.cpDate[2];
        this.LF = a.net.cpDate[3];
        this.TF = a.net.floatValue[0];
        this.cost = a.actualCost;
        this.quantity = a.quantity;
    }
}
