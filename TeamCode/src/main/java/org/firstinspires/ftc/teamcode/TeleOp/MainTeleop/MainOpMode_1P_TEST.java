// 1 player, made from MainOpMode.java

package org.firstinspires.ftc.teamcode.TeleOp.MainTeleop;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Commands.DriveRobotOptimalCommand_1P_TEST;
import org.firstinspires.ftc.teamcode.util.PlaystationAliases;

/*
TODO:
Implement photon https://github.com/8872/8872PP/pull/3/files

NOTES:

OLD STUFF
- pitch starting position is 1 -> 0, angle is 0 -> 180 (clockwise)
- claw release: 0.4, grab: 0.25
- arm_OLD: 1 -> 0.6 -> 0.2

NEW
- arm (axon): 0.88 (MAX 0.9) -> 0.3 (MAX 0.2)      0.59 Middle cutoff
- pitch: 0.22 -> 0.6
- claw: 0.4 (open) -> 0.5 (closed)
 */

@Disabled
@Config
@TeleOp(name = "TEST_MainOpMode_1PLAYER")
public class MainOpMode_1P_TEST extends BaseOpMode {
    public static double A_armPosition = -1;
    public static double A_pitchPosition = -1;
    public static double A_clawPosition = -1;


    public boolean activated = true;
    public boolean isClawOpen = true;




    public double armMin = 0.3;
    public double armMax = 0.89; // 0.88 when red tape

    public double pitchMin = 0.22; // 0.22 when red tape
    public double pitchMax = 0.6;

    public double clawClosed = 0.7; // 0.5 -> 0.6
    public double clawOpen = 0.47; // 0.4 -> 0.5

    public double manualArmIncrement = 0.0005;



//    public void setArmPosition(double targetArmPosition) {
//        // 0: up
//        // 1: down
//        double percent = (targetArmPosition - armMin) / (armMax - armMin);
//
//        if (percent < 50) {
//            // arm is mostly up: adjust pitch so that pitch/claw is perpendicular against wall
//            arm.setPosition(targetArmPosition);
//            pitch.setPosition(0); //TODO
//        } else {
//            // arm is mostly down: we do not want to move the pitch
//            arm.setPosition(targetArmPosition);
//        }
//    }

    public boolean axonInitialized = false;
    public boolean noPitchDelayForNext = true;
    public double armPosition = 1;
//    public double targetArmPosition;
    public double armPercent;
    public SequentialCommandGroup updateArm() {
        return new SequentialCommandGroup(
            new InstantCommand(() -> {armPercent = (armPosition - armMin) / (armMax - armMin);}),
            new ConditionalCommand(
                new SequentialCommandGroup(
                    // arm is mostly up: adjust pitch so that pitch/claw is perpendicular against wall
                    new InstantCommand(() -> {
                        arm.setPosition(armPosition);
                    }),
//                    new WaitCommand(500),
//                    new WaitCommand(noPitchDelayForNext ? 0 : (500 - (axonInitialized ? 200 : 0))),
                    new WaitCommand(axonInitialized ? (noPitchDelayForNext ? 0 : 300) : 500),
                    new InstantCommand(() -> {
                        double pitchPercent = (0.5 - armPercent) / (0.5);
                        // pitchPercent * 1 - (1 - pitchPercent) * 0.65
                        pitch.setPosition((1 - pitchPercent) * (1 - 0.65) + 0.65); // perpendicular to board
                        axonInitialized = true;
                        noPitchDelayForNext = false;
                    })
                ),
                new SequentialCommandGroup(
                    // arm is mostly down: we do not want to move the pitch
                    new InstantCommand(() -> {
                        pitch.setPosition(0.22); // ready to pick up
                        arm.setPosition(armPosition);
                    })
                ),
                () -> armPercent < 0.5
            )
        );
    }

    /*
    -1: first run
    0: on dustpan
    1: hovering over dustpan
    2: scoring (extended)
     */
    public double armPickupStage = -1;

    public InstantCommand armPickup;



    @Override
    public void initialize() {
        super.initialize();
        register(driveSubsystem, intakeSubsystem);


        // Test / tune arm
        gb1(PlaystationAliases.TRIANGLE).whenPressed(() -> {
            if (A_armPosition != -1) {
//                arm.setPosition(A_armPosition);

                armPosition = A_armPosition;
                updateArm().schedule();
            }
            if (A_pitchPosition != -1) {
                pitch.setPosition(A_pitchPosition);
            }
            if (A_clawPosition != -1) {
                claw.setPosition(A_clawPosition);
            }
        });

        // Intake normal and reverse
        gb1(PlaystationAliases.CROSS).whileHeld(intakeSubsystem.startIntakeCommand());
        gb1(PlaystationAliases.TRIANGLE).whileHeld(intakeSubsystem.reverseIntakeCommand());

        // Arm pickup stages
        armPickup =
            new InstantCommand(
                () -> {
                    if (armPickupStage == -1) {
                        // Arm is currently unpowered, on dustpan

                        new SequentialCommandGroup(
                            new InstantCommand(() -> claw.setPosition(clawClosed)), // Close claw
                            new WaitCommand(250),
                            new InstantCommand(() -> {armPosition = 0.8;}),
                            updateArm()
                        ).schedule();

                        armPickupStage = 1;
                        isClawOpen = false;
                    } else if (armPickupStage == 0) {
                        // Arm is currently hovering over dustpan

                        new SequentialCommandGroup(
                            new InstantCommand(() -> {armPosition = 0.86;}),
                            updateArm(),
                            new WaitCommand(150),

                            new InstantCommand(() -> {armPosition = armMax;}),
                            updateArm(),
                            new WaitCommand(250),

                            new InstantCommand(() -> claw.setPosition(clawClosed)), // Close claw
                            new WaitCommand(200),
                            new InstantCommand(() -> {armPosition = 0.8;}),
                            updateArm()
                        ).schedule();

                        armPickupStage = 1;
                        isClawOpen = false;
                    } else if (armPickupStage == 1) {
                        // Arm is currently hovering over dustpan with a pixel

                        new SequentialCommandGroup(
//                            new InstantCommand(() -> {armPosition = 0.3;}), // Default
                            new InstantCommand(() -> {armPosition = 0.33;}), // Slightly above pixel bottom
                            updateArm()
                        ).schedule();

                        armPickupStage = 2;
                        isClawOpen = false;
                    } else if (armPickupStage == 2) {
                        // Arm is currently hovering scoring

                        new SequentialCommandGroup(
                            new InstantCommand(() -> claw.setPosition(clawOpen)), // Open claw
                            new WaitCommand(100),

                            // Shake it
                            new InstantCommand(() -> {pitch.setPosition(pitch.getPosition() + 0.1);}),
                            new WaitCommand(100),
                            new InstantCommand(() -> {pitch.setPosition(pitch.getPosition() - 0.1);}),
                            new WaitCommand(100),

                            new WaitCommand(500),

                            // Move arm back
                            new InstantCommand(() -> {armPosition = 0.8;}),
                            updateArm()
                        ).schedule();

                        armPickupStage = 0;
                        isClawOpen = true;
                    }
                }
            );

        gb1(PlaystationAliases.CIRCLE).whenPressed(
                armPickup
        );

        // Retry pickup
        gb1(PlaystationAliases.SQUARE).whenPressed(
            new InstantCommand(
                () -> {
                    if (armPickupStage == 1) {
                        new SequentialCommandGroup(
                            new InstantCommand(() -> claw.setPosition(clawOpen)), // Open claw
                            new WaitCommand(200),

                            new InstantCommand(() -> {armPosition = 0.86;}),
                            updateArm(),
                            new WaitCommand(150),

                            new InstantCommand(() -> {armPosition = armMax;}),
                            updateArm(),
                            new WaitCommand(250),

                            new InstantCommand(() -> claw.setPosition(clawClosed)), // Close claw
                            new WaitCommand(200),
                            new InstantCommand(() -> {armPosition = 0.8;}),
                            updateArm()
                        ).schedule();
                    }
                }
            )
        );

        // Manual claw control
//        gb2(PlaystationAliases.SQUARE).whenPressed(
//                () -> {
//                    isClawOpen = !isClawOpen;
//                    if (isClawOpen) {
//                        claw.setPosition(0.4);
//                    } else {
//                        claw.setPosition(0.5);
//                    }
//                }
//        )














//        gb2(PlaystationAliases)

//        gb1(GamepadKeys.Button.A).toggleWhenPressed(armSubsystem.moveArm(ArmSubsystem.armPosHome), armSubsystem.moveArm(ArmSubsystem.armPosAway));
//        gb1(GamepadKeys.Button.B).toggleWhenPressed(armSubsystem.movePitch(ArmSubsystem.pitchPosHome), armSubsystem.movePitch(ArmSubsystem.pitchPosAway));
       // clawSubsystem.setDefaultCommand(axleMoveCommand);
        //armSubsystem.setDefaultCommand(armMoveCommand);
        driveSubsystem.setDefaultCommand(new DriveRobotOptimalCommand_1P_TEST(driveSubsystem, gamepadEx1));

        // TODO LIFT
//        liftSubsystem.setDefaultCommand(manualLiftCommand);

    }


    private boolean lastTouchpad = false;
    public void run()
    {

        // Manual arm control
        if (gamepad1.dpad_left) {
            armPosition = armPosition - manualArmIncrement;
        }
        if (gamepad1.dpad_right) {
            armPosition = armPosition + manualArmIncrement;
        }
        if (gamepad1.dpad_left || gamepad1.dpad_right) {
            updateArm().schedule();
        }

        // Manual arm control: touchpad x (left = dustpan, right = score)
        if (gamepad1.touchpad_finger_1 && armPickupStage == 2) {
            double x = gamepad1.touchpad_finger_1_x;
            double y = gamepad1.touchpad_finger_1_y;

//            double armPercent = (1 - ((x + 1) * 0.5)); // full range

            double armPercent = (0.5 - ((x + 1) * 0.25)); // scoring range

            armPosition = (armPercent * (armMax - armMin)) + armMin;

            noPitchDelayForNext = true;
            updateArm().schedule();

            telemetry.addLine("touchpad active: finger1 " + x + " " + y);
        }

        // Manual claw control: Press down on touchpad
        if (gamepad1.touchpad && !lastTouchpad) {
//            isClawOpen = !isClawOpen;
//            if (isClawOpen) {
//                claw.setPosition(0.4);
//            } else {
//                claw.setPosition(0.5);
//            }
            armPickup.schedule();
        }
        lastTouchpad = gamepad1.touchpad;


        // Manual lift
        double power = 0;

        if (gamepad1.dpad_up) {
            power = power + 1;
        }
        if (gamepad1.dpad_down) {
            power = power - 1;
        }

        liftLeft.motor.setPower(-power);
        liftRight.motor.setPower(-power);

        telemetry.addData("armPickupStage", armPickupStage);
        telemetry.addData("armPosition", armPosition);
        telemetry.update();
//        AprilTagDetector.updateAprilTagDetections();
//        AprilTagDetector.aprilTagTelemetry(telemetry);


//        driveRobotOptimalCommand.execute();





        super.run();
    }
}