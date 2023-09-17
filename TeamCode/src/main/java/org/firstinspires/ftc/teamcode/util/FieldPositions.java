package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.util.managementAnnotations.InProgress;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestPhase;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestingPhase;

@TestPhase(phase = TestingPhase.UNTESTED)
@InProgress(
        toDo = {
                "set vals",
                "populate arrays",
                "should we turn rr vals into Pos2d and Vec2d?"
        }
)
/**
 * use ctrl+f to quickly scroll to you position. positions are in order of (the comments):
 * RR poses x (in)
 * RR poses y (in)
 * April tag positions RR (x)
 * April tag positions RR (y)
 * the positions of the tags by ID (x)
 * the positions of the tags by ID (y)
 * RR headings
 * april tag IDs
 * starting positions
 */
public class FieldPositions {

    //RR poses x (in) TODO: DEFINE
    public static /* final */ int
    RED_BACKDROP_LEFT_X,
    RED_BACKDROP_CENTER_X,
    RED_BACKDROP_RIGHT_X,

    BLUE_BACKDROP_LEFT_X,
    BLUE_BACKDROP_CENTER_X,
    BLUE_BACKDROP_RIGHT_X,

    RED_RIGGING_LEFT_X,
    RED_RIGGING_RIGHT_X,

    BLUE_RIGGING_LEFT_X,
    BLUE_RIGGING_RIGHT_X,

    STARTING_RED_LEFT_X,
    STARTING_RED_RIGHT_X,

    STARTING_BLUE_LEFT_X,
    STARTING_BLUE_RIGHT_X
    ;

    //RR poses y
    public static /* final */ int
    RED_BACKDROP_LEFT_Y,
    RED_BACKDROP_CENTER_Y,
    RED_BACKDROP_RIGHT_Y,

    BLUE_BACKDROP_LEFT_Y,
    BLUE_BACKDROP_CENTER_Y,
    BLUE_BACKDROP_RIGHT_Y,

    RED_RIGGING_LEFT_Y,
    RED_RIGGING_RIGHT_Y,

    BLUE_RIGGING_LEFT_Y,
    BLUE_RIGGING_RIGHT_Y,

    STARTING_RED_LEFT_Y,
    STARTING_RED_RIGHT_Y,

    STARTING_BLUE_LEFT_Y,
    STARTING_BLUE_RIGHT_Y
    ;

    //April tag positions RR (x)
    public static /* final */ double
    RED_LEFT_BOARD_X,
    RED_CENTER_BOARD_X,
    RED_RIGHT_BOARD_X,

    BLUE_LEFT_BOARD_X,
    BLUE_CENTER_BOARD_X,
    BLUE_RIGHT_BOARD_X,

    WALL_LEFT_X,
    WALL_RIGHT_X
    ;

    //April tag positions RR (y)
    public static /* final */ double
    RED_LEFT_BOARD_Y,
    RED_CENTER_BOARD_Y,
    RED_RIGHT_BOARD_Y,

    BLUE_LEFT_BOARD_Y,
    BLUE_CENTER_BOARD_Y,
    BLUE_RIGHT_BOARD_Y,

    WALL_LEFT_Y,
    WALL_RIGHT_Y
                    ;


    //the positions of the tags by ID (x)
    public static final double[] TAG_POS_BY_ID_X = {
            //
    };

    //the positions of the tags by ID (y)
    public static final double[] TAG_POS_BY_ID_Y = {
            //
    };

    //RR headings
    public static final double FACING_BOARD_RAD = Math.toRadians(90); //assuming facing forwards = facing red
    public static final double STARTING_POS_RAD = Math.toRadians(90); //assuming facing forwards = facing red


    //april tag IDs
    public static /* final */ int
    RED_LEFT_BOARD_ID,
    RED_CENTER_BOARD_ID,
    RED_RIGHT_BOARD_ID,

    BLUE_LEFT_BOARD_ID,
    BLUE_CENTER_BOARD_ID,
    BLUE_RIGHT_BOARD_ID,

    WALL_LEFT_ID,
    WALL_RIGHT_ID
    ;
}
