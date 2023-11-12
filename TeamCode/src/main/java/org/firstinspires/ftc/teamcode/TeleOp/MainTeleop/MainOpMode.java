package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

/*
TODO:
Implement photon https://github.com/8872/8872PP/pull/3/files

NOTES:
- pitch starting position is 1 -> 0, angle is 0 -> 180 (clockwise)
- claw release: 0.4, grab: 0.25
- arm: 1 -> 0.6 -> 0.2
 */

@Config
@TeleOp(name = "MainTeleOp")
public class MainOpMode extends BaseOpMode {
    public static double angle = -1;
    public static double position = -1;
    public boolean activated = true;

    @Override
    public void initialize() {
        super.initialize();
        register(driveSubsystem, intakeSubsystem, armSubsystem, clawSubsystem, pitchSubsystem);




        gb2(PlaystationAliases.CROSS).whileHeld(startIntakeCommand);
        gb2(PlaystationAliases.SQUARE).toggleWhenPressed(grabAndUpCommand, releaseAndDownCommand);

        driveSubsystem.setDefaultCommand(driveRobotOptimalCommand);
        liftSubsystem.setDefaultCommand(manualLiftCommand);


        // reverse
        gb1(PlaystationAliases.SQUARE).whenPressed(() -> {
            driveSubsystem.speedMultiplier = -driveSubsystem.speedMultiplier;
        });



        // open and down

//        gb2(PlaystationAliases.CROSS).whenPressed(() -> {
//            if (activated) {
//
////            claw.setPosition(0.42);
////            pitch.setPosition(0);
////            arm.setPosition(0.9);
////            sleep(1000);
////            arm.setPosition(1);
//
////            claw.setPosition(0.42);
////            pitch.setPosition(0);
////            arm.setPosition(0.9);
////            sleep(1400);
////            arm.setPosition(1);
//
//                // drop off
//                claw.setPosition(0.40);
//                sleep(200);
//                pitch.setPosition(0.52);
//                sleep(300);
//                pitch.setPosition(0.6);
//                sleep(1000);
//
//
//                pitch.setPosition(0);
//                arm.setPosition(0.9);
//                sleep(2200);
//                arm.setPosition(1);
//
//                activated = false;
//            }
//        });
//        // close and up
//        gb2(PlaystationAliases.CIRCLE).whenPressed(() -> {
//            if (!activated) {
////            claw.setPosition(0.25);
////            sleep(200);
////            arm.setPosition(0.5);
////            sleep(1200);
////            arm.setPosition(0.4);
////            pitch.setPosition(0.16);
//
////            claw.setPosition(0.25);
////            sleep(200);
////            arm.setPosition(0.5);
////            sleep(1200);
////            arm.setPosition(0.45);
////            pitch.setPosition(0.2);
//
//                claw.setPosition(0.25);
//                sleep(200);
//                arm.setPosition(0.5);
//                sleep(1200);
//                pitch.setPosition(0.55);
//
//                activated = true;
//            }
//        });

        //TEST
//        gb2(PlaystationAliases.SQUARE).whenPressed(() -> {
//
//
//
//            if (position != -1) {
//                pitch.setPosition(position);
//            } else if (angle != -1) {
//                pitch.turnToAngle(angle);
//            }
//        });

        gb2(PlaystationAliases.TRIANGLE).whenPressed(() -> {
            if (position != -1) {
                pitch.setPosition(position);
            } else if (angle != -1) {
                pitch.turnToAngle(angle);
            }
        });




//        gb1(GamepadKeys.Button.A).toggleWhenPressed(armSubsystem.moveArm(ArmSubsystem.armPosHome), armSubsystem.moveArm(ArmSubsystem.armPosAway));
//        gb1(GamepadKeys.Button.B).toggleWhenPressed(armSubsystem.movePitch(ArmSubsystem.pitchPosHome), armSubsystem.movePitch(ArmSubsystem.pitchPosAway));
       // clawSubsystem.setDefaultCommand(axleMoveCommand);
        //armSubsystem.setDefaultCommand(armMoveCommand);

    }

    public void run()
    {
        telemetry.addData("Claw Pos", clawSubsystem.claw.getPosition());
        telemetry.addData("Claw Angle", clawSubsystem.claw.getAngle());
        telemetry.addData("Intake Command is scheduled", startIntakeCommand.isScheduled());
        telemetry.addData("grabAndUpCommand is scheduled", grabAndUpCommand.isScheduled());
        telemetry.addData("releaseAndDownCommand is scheduled", releaseAndDownCommand.isScheduled());
        telemetry.update();
//        AprilTagDetector.updateAprilTagDetections();
//        AprilTagDetector.aprilTagTelemetry(telemetry);


        super.run();
    }
}