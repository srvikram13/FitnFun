package srvikram13.fitnfun.model;

/**
 * Created by vikram on 12/10/2016.
 */

public class Instruction {
    public final String description;
    public final int count;
    public final Action action;
    public final int points;
    public Instruction(Action action, int count, int points, String desc) {
        this.action = action;
        this.count = count;
        this.points = points;
        this.description = desc;
    }
}
