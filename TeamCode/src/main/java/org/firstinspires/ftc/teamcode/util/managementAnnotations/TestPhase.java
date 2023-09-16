package org.firstinspires.ftc.teamcode.util.managementAnnotations;

/**
 * to signify that this code is at a specified state in it's testing
 * <p> - phase: the testing phase the code is in </p>
 * <p> - disc: an optional description to elaborate (ex: to describe the current error in debugging) </p>
 */
public @interface TestPhase {
    TestingPhase phase();
    String disc() default "";
}
