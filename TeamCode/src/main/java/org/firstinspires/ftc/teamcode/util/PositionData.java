package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.roadrunner.geometry.Pose2d;

//used to signify the location of some object within 3d space (localisation can be done on a 2d space
public class PositionData {
    public PositionData (double x, double y, double z, double aX, double aY, double aZ) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.aX = aX;
        this.aY = aY;
        this.aZ = aZ;
    }

    public PositionData (Pose2d pose) {
        this.x = pose.getX();
        this.y = pose.getY();
        this.z = 0;

        this.aX = 0;
        this.aY = 0;
        this.aZ = pose.getHeading();
    }

    public double x, y, z;
    public double aX, aY, aZ;

    public Pose2d toPose2d () {
        return new Pose2d(x, y, aZ);
    }
}
