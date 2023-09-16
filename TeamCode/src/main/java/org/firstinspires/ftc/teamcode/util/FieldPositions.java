package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.util.managementAnnotations.InProgress;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestPhase;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestingPhase;

@TestPhase(phase = TestingPhase.UNTESTED)
@InProgress(
        toDo = {"SET VALS"}
)
public class FieldPositions {

    //RR poses (in) TODO: DEFINE
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
    BLUE_RIGGING_RIGHT_X
    ;

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
    BLUE_RIGGING_RIGHT_Y
    ;

    //RR headings
    public static final double facingBoardAng = Math.toRadians(90); //assuming facing forwards = facing red

    //april tag IDs
    public static int
    TAG;
}
