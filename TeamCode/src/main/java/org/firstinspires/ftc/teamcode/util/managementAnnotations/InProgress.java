package org.firstinspires.ftc.teamcode.util.managementAnnotations;

/**
 * to signify that this code is unfinished or is under revision
 * <p> - toDo: a list of strings (because strings cannot multiline) that represent the todo list </p>
 * <p> - done: a list of strings that represent tasks checked off of the todo list </p>
 */
public @interface InProgress {
    String[] toDo() default {};
    String[] done() default {};
}
