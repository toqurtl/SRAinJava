package main;

import inputdata.ActualData;
import mcs.MCSResult;
import mcs.MCSResultOutput;
import mcs.MCSimulation;
import schedule.Activity;
import schedule.BuildSchedule;
import schedule.Schedule;
import inputdata.ScheduleData;

public class Main {

    public static Schedule schedule;

    public static void main(String args[]){
        //planned schedule input
        ScheduleData sData = new ScheduleData(IO.scheduleAdd);
        schedule = BuildSchedule.build(sData);
        /**
        for(Activity a : schedule){
            System.out.println(a.actualCost);
        }
         **/
        //actual data input
        ActualData aData = new ActualData(sData, IO.actualAdd);

        //Simulation
        MCSResult result = MCSimulation.simulation(schedule, aData, User.simulationNum);

        //result
        MCSResultOutput mcso = new MCSResultOutput(schedule, result, User.startDate);
        mcso.generalInfoOutput(IO.generaloutputAdd);
        mcso.activityInfoOutput(IO.activityoutputAdd);
        mcso.costperTimeOutput(IO.costperTimeAdd);
        mcso.ganttChartData(IO.ganttchartDataAdd);


        //MCSimulation.simulation(schedule);


    }
}
