package schedule;

import inputdata.FormatDependency;

public class Dependency {
    public String preID;
    public String sucID;
    public String relationship;
    public int lagtime;

    public Dependency(FormatDependency dep){
        this.preID = dep.preID;
        this.sucID = dep.sucID;
        this.relationship = dep.relationship;
        this.lagtime = dep.lagtime;

    }

    public Dependency(String preID, String sucID, String relationship, int lagtime){
        this.preID = preID;
        this.sucID = sucID;
        this.relationship = relationship;
        this.lagtime = lagtime;
    }
}
