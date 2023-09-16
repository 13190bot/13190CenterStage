package org.firstinspires.ftc.teamcode.util.managementAnnotations;

/**
 * to signify that this code is used to test
 * <p> - result: the pass/fail result of the previous test </p>
 * <p> - subject: the subject of the test (Ex: DefaultCmdTest) </p>
 * <p> - disc: an optional description to describe the test (hopefully the name is descriptive enough) </p>
 * <p> - resultExp: an explanation and/or elaboration of the result (so as to describe the error) </p>
 */
public @interface Test {
    TestResult result();
    String subject();
    String disc() default "";
    String resultExp() default "";
}
