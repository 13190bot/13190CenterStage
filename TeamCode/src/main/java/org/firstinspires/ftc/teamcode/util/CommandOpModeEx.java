package org.firstinspires.ftc.teamcode.util;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.InProgress;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestPhase;
import org.firstinspires.ftc.teamcode.util.managementAnnotations.TestingPhase;

@TestPhase(phase = TestingPhase.UNTESTED)
@InProgress(
        toDo = {
                "implement scheduler in iLoop()",
                "implement in all positions where CommandOpMode is currently used"
        },
        done = {"implement basic sections expressed in OpMode"}
)
/**
 * CommandOpMode but with added functionality
 */
public abstract class CommandOpModeEx extends CommandOpMode {

    /**
     * override to run as a loop before after initialise() but before st()
     * <p> currently you have to manually call the scheduler </p>
     */
    public void iLoop () {}

    /**
     * override to run once when the start button is pressed
     */
    public void st () {}

    /**
     * override to run once when the opMode is stopped
     */
    public void stp () {}

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();

        while (opModeInInit()) {
            iLoop();
        }

        waitForStart();

        st();

        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            run();
        }
        reset();

        stp();
    }
}
