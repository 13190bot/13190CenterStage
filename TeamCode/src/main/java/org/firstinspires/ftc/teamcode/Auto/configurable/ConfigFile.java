package org.firstinspires.ftc.teamcode.Auto.configurable;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.firstinspires.ftc.teamcode.Auto.BaseAuto;

public abstract class ConfigFile {
    //fields
    public final     int alliance         ;
    public final     int numCycles        ;
    public final boolean doesUseYellow    ;
    public final boolean doesUsePurple    ;
    public final boolean sameSideCycles   ; //which side to cycle from first
    public final boolean doesPark         ;
    public final     int preferredRigging ;
    public final     int startingDist     ;
    public final  double startingHeading  ;

    //helper methods (idk how useful some of these are)

    //TODO: check if [this.getClass().getName().substring(1)] returns class name
    public String getPrintableName () { return "config file [" + this.getClass().getName().substring(1) + "]"; }

    public String allianceToString () {
        switch (alliance) {
            case  1: return "Blue";
            case -1: return "Red" ;
            default: throw new IllegalStateException( getPrintableName() + ": invalid alliance (" + alliance + ")");
        }
    }

    public double startingX () {
        return alliance == RED ? RED_POSE : BLUE_POSE;
    }

    public double startingY () {
        return CLOSE_POSE - (FAR_LENGTH * startingDist);
    }

    public Pose2d startingRRPose () { return new Pose2d(startingX(), startingY(), startingHeading); }

    public boolean doesCycle () { return numCycles != 0; }
    public boolean needsPropDetection () { return doesUsePurple || doesUseYellow; }

    //enums & constants
    public static final int //alliance colors
            RED  =-1,
            BLUE = 1
    ;

    public static final double //alliance pose
        RED_POSE  = -1,
        BLUE_POSE = -1
    ;


    public static final int //rigging enum
            LEFT_RIGGING    =-1,
            RIGHT_RIGGING   = 1,
            DEFAULT_RIGGING = 0
    ;

    public static final int //starting pose enum
        CLOSE = 0,
        FAR   = 1
    ;

    public static final double //starting pose y
        CLOSE_POSE = -1,
        FAR_LENGTH = BaseAuto.TILE_SIZE * 2 //dist between close and far
    ;

    //methods that need to be overrided
    public abstract int alliance     () ;
    public abstract int startingDist () ;

    //methods that have default value
    public      int numCycles        () { return 0;    }
    public  boolean doesUseYellow    () { return true; }
    public  boolean doesUsePurple    () { return true; }
    public  boolean sameSideCycles   () { return true; } //which side to cycle from first
    public  boolean doesPark         () { return true; }
    public      int preferredRigging () { return DEFAULT_RIGGING; }
    public   double startingHeading () { return Math.toRadians(90); } //TODO: check if this is right

    //constructor
    public ConfigFile () {
        alliance         = alliance();
        startingDist     = startingDist();
        numCycles        = numCycles();
        doesUseYellow    = doesUseYellow();
        doesUsePurple    = doesUsePurple();
        sameSideCycles   = sameSideCycles();
        doesPark         = doesPark();
        preferredRigging = preferredRigging();
        startingHeading  = startingHeading();

        //check invariants
        if (
                alliance != RED
             && alliance != BLUE
        ) throw new IllegalArgumentException( getPrintableName() + ": invalid alliance (" + alliance + ")");

        if (
                startingDist != CLOSE
             && startingDist != FAR
        ) throw new IllegalArgumentException( getPrintableName() + ": invalid startingPose (" + startingDist + ")");

        if (
                preferredRigging != DEFAULT_RIGGING
             && preferredRigging != LEFT_RIGGING
             && preferredRigging != RIGHT_RIGGING
        ) throw new IllegalArgumentException( getPrintableName() + ": invalid preferredRigging (" + preferredRigging + ")");
    }
}
