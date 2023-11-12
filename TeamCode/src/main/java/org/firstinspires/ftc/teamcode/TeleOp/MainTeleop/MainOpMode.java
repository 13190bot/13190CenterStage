package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.CV.AprilTagDetector;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

import java.lang.reflect.WildcardType;

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
        register(driveSubsystem, intakeSubsystem);



        /*


//        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).toggleWhenPressed(driveRobotCentricSlowModeCommand);

//        gb2(PlaystationAliases.CROSS).whileHeld(startIntakeCommand);


        gb2(PlaystationAliases.SQUARE).whenPressed(() -> {
            intakeSubsystem.startIntake();
        });
        gb2(PlaystationAliases.SQUARE).whenReleased(() -> {
            intakeSubsystem.stopIntake();
        });


        // open and down

        gb2(PlaystationAliases.CROSS).whenPressed(() -> {
            if (activated) {

//            claw.setPosition(0.42);
//            pitch.setPosition(0);
//            arm.setPosition(0.9);
//            sleep(1000);
//            arm.setPosition(1);

//            claw.setPosition(0.42);
//            pitch.setPosition(0);
//            arm.setPosition(0.9);
//            sleep(1400);
//            arm.setPosition(1);

                // drop off
                claw.setPosition(0.40);
                sleep(200);
                pitch.setPosition(0.52);
                sleep(300);
                pitch.setPosition(0.6);
                sleep(1000);


                pitch.setPosition(0);
                arm.setPosition(0.9);
                sleep(2200);
                arm.setPosition(1);

                activated = false;
            }
        });
        // close and up
        gb2(PlaystationAliases.CIRCLE).whenPressed(() -> {
            if (!activated) {
//            claw.setPosition(0.25);
//            sleep(200);
//            arm.setPosition(0.5);
//            sleep(1200);
//            arm.setPosition(0.4);
//            pitch.setPosition(0.16);

//            claw.setPosition(0.25);
//            sleep(200);
//            arm.setPosition(0.5);
//            sleep(1200);
//            arm.setPosition(0.45);
//            pitch.setPosition(0.2);

                claw.setPosition(0.25);
                sleep(200);
                arm.setPosition(0.5);
                sleep(1200);
                pitch.setPosition(0.55);

                activated = true;
            }
        });

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
                arm.setPosition(position);
            } else if (angle != -1) {
                arm.turnToAngle(angle);
            }
        });

         */





        // put arm to resting position
        arm.setPosition(0.55);


        // v2
        gb2(PlaystationAliases.CROSS).whenPressed(() -> {
            intakeSubsystem.startIntake();
        });
        gb2(PlaystationAliases.CROSS).whenReleased(() -> {
            intakeSubsystem.stopIntake();
        });


        gb2(PlaystationAliases.CIRCLE).toggleWhenPressed(
                new SequentialCommandGroup(
                        new InstantCommand(() -> {arm.setPosition(1);}),
                        new WaitCommand(600),
                        new InstantCommand(() -> {claw.setPosition(0.25);}),
                        new WaitCommand(400),
                        new InstantCommand(() -> {arm.setPosition(0.45);}),
                        new WaitCommand(1000),
                        new InstantCommand(() -> {pitch.setPosition(0.65);})
                ),
//            claw.setPosition(0.25);
//            sleep(200);
//            arm.setPosition(0.5);
//            sleep(1200);
//            pitch.setPosition(0.55);

                new SequentialCommandGroup(
                        new InstantCommand(() -> {claw.setPosition(0.40);}),
                        new WaitCommand(300),
//                        new InstantCommand(() -> {pitch.setPosition(0.62);}),
//                        new WaitCommand(100),
//                        new InstantCommand(() -> {pitch.setPosition(0.7);}),
//                        new WaitCommand(700),
                        new InstantCommand(() -> {pitch.setPosition(0.6);}),
                        new WaitCommand(100),
                        new InstantCommand(() -> {pitch.setPosition(0.7);}),
                        new WaitCommand(700),

                        new InstantCommand(() -> {pitch.setPosition(0.1);}),
//                        new InstantCommand(() -> {arm.setPosition(0.85);}),
//                        new WaitCommand(700),
//                        new InstantCommand(() -> {arm.setPosition(0.95);})
//                        new InstantCommand(() -> {arm.disable();})
                        new InstantCommand(() -> {arm.setPosition(0.55);})
                )
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
        );

        // 0.33
        gb2(PlaystationAliases.TRIANGLE).whenPressed(() -> {
            if (position != -1) {
                pitch.setPosition(position);
            } else if (angle != -1) {
                pitch.turnToAngle(angle);
            }
        });













































        // reverse
        gb1(PlaystationAliases.SQUARE).whenPressed(() -> {
           driveSubsystem.speedMultiplier = -driveSubsystem.speedMultiplier;
        });
//        gb2(PlaystationAliases)

//        gb1(GamepadKeys.Button.A).toggleWhenPressed(armSubsystem.moveArm(ArmSubsystem.armPosHome), armSubsystem.moveArm(ArmSubsystem.armPosAway));
//        gb1(GamepadKeys.Button.B).toggleWhenPressed(armSubsystem.movePitch(ArmSubsystem.pitchPosHome), armSubsystem.movePitch(ArmSubsystem.pitchPosAway));
       // clawSubsystem.setDefaultCommand(axleMoveCommand);
        //armSubsystem.setDefaultCommand(armMoveCommand);
        driveSubsystem.setDefaultCommand(driveRobotOptimalCommand);
        liftSubsystem.setDefaultCommand(manualLiftCommand);

    }

    public void run()
    {
        telemetry.addData("clawpos", clawSubsystem.claw.getPosition());
        telemetry.addData("clawang", clawSubsystem.claw.getAngle());
        telemetry.update();
//        AprilTagDetector.updateAprilTagDetections();
//        AprilTagDetector.aprilTagTelemetry(telemetry);


//        driveRobotOptimalCommand.execute();
        super.run();
    }
}