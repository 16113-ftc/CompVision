package org.firstinspires.ftc.teamcode.AutoOmegaBlue.OutsideBlue;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.OmegaSquadRobot;

@Autonomous(name = "Auto Drive Blue (outside park, block edge)", group = "Autonomous")
//@Disabled
public class AutoOmegaBlueOutsideBlockEdge extends LinearOpMode {
    //1.5 seconds of spinning at 0.75 = 2 ft.
    OmegaSquadRobot robot = new OmegaSquadRobot();
    private ElapsedTime runtime = new ElapsedTime();

    Orientation lastAngles = new Orientation();
    double                  globalAngle;

    static final double FORWARD_SPEED = 0.5;
    static final double REVERSE_SPEED = -0.5;

    double foundationLeftPosition = 1.0;
    double foundationRightPosition = 0.0;

    double FOUNDATION_INITIAL_POSITION = 0;

    double MIN_POSITION = 0, MAX_POSITION = 1;
    float speed = 0.0f;
    double turnspeed = 0.0f;
    //foundationLeftPosition = FOUNDATION_INITIAL_POSITION;
    //foundationRightPosition = FOUNDATION_INITIAL_POSITION;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        // Put initialization blocks here.
        robot.init(hardwareMap);

        telemetry.addData("Autonomous Mode Status", "Ready to Run");
        telemetry.update();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        robot.imu.initialize(parameters);

        while (!isStopRequested() && !robot.imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }
        waitForStart();

        SteerForSeconds(0.4);
        //Spin90Left();
        rotate(80,0.5);
        SteerForSeconds(0.5);

        rotate(-80,0.5);
        SteerForSeconds(1);

        StopSteering();

        GrabStone();

        //After grabbing the block, come backward
        robot.leftFront.setPower(REVERSE_SPEED);
        robot.leftBack.setPower(REVERSE_SPEED);
        robot.rightFront.setPower(REVERSE_SPEED);
        robot.rightBack.setPower(REVERSE_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 0.3)) {
            telemetry.addData("Path", "Grabbed Block going back: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }
        StopSteering();

        //Turn 90 Degrees to go under the alliance bridge
        //Spin90Left();
        rotate(80,0.5);

        StopSteering();

        SpeedForward(2.25);

        StopSteering();
        //Turn towards foundation

        while (opModeIsActive() && !robot.ts_bottom.isPressed()) {
            robot.clawDC.setPower(0.5);
            //power of 1 is needed to go up
            robot.clawGripper.setPosition(250);
            telemetry.addData("Pick up stone", "up");
            telemetry.update();
        }

        robot.clawDC.setPower(0);

        //Spin90Right();
        rotate(-80,0.5);

        StopSteering();

        robot.leftFront.setPower(0.2);
        robot.leftBack.setPower(0.2);
        robot.rightFront.setPower(0.2);
        robot.rightBack.setPower(0.2);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            robot.clawGripper.setPosition(250);
            telemetry.addData("Path", "Forward to foundation: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        StopSteering();

        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1) {
            //stone released
            robot.clawGripper.setPosition(-45);
            sleep(1000);
            robot.clawDrop.setPosition(180);
            telemetry.addData("Release  stone: 2.5f S Elapsed", runtime.seconds());
            telemetry.update();

        }

        robot.leftFront.setPower(-1);
        robot.leftBack.setPower(-1);
        robot.rightFront.setPower(-1);
        robot.rightBack.setPower(-1);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 0.8)) {
            telemetry.addData("Path", "Go back after placing stone: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

        //Spin90Right();
        rotate(-80,0.5);

        StopSteering();

        while(opModeIsActive() &&!robot.ts_top.isPressed()) {
            robot.clawDC.setPower(-0.5);
            //power of 1 is needed to go up
            telemetry.addData("Drop Claw", "down");
            telemetry.update();
        }

        robot.clawDC.setPower(0);

        SpeedForward(0.5);

        StopSteering();
    }




    private void SteerForSeconds(double time) {
        robot.leftFront.setPower(FORWARD_SPEED);
        robot.leftBack.setPower(FORWARD_SPEED);
        robot.rightFront.setPower(FORWARD_SPEED);
        robot.rightBack.setPower(FORWARD_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Towards Block: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

    }

    private void Spin90Right() {
        robot.leftFront.setPower(-0.5);
        robot.leftBack.setPower(-0.5);
        robot.rightFront.setPower(0.5);
        robot.rightBack.setPower(0.5);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1.65)) {
            telemetry.addData("Path", "Turning 90 Deg to face Bridge: %2.5f S  Elapsed", runtime.seconds());

            telemetry.update();

        }
    }

    private void Spin90Left() {
        robot.leftFront.setPower(0.5);
        robot.leftBack.setPower(0.5);
        robot.rightFront.setPower(-0.5);
        robot.rightBack.setPower(-0.5);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1.6)) {
            telemetry.addData("Path", "Turning 90 Deg to go under bridge: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }
    }

    private void StopSteering() {
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
    }

    private void ShuffleLeft(double time) {
        robot.leftFront.setPower(-FORWARD_SPEED);
        robot.leftBack.setPower(FORWARD_SPEED);
        robot.rightFront.setPower(FORWARD_SPEED);
        robot.rightBack.setPower(-FORWARD_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "shuffle left %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }


    }

    private void ShuffleRight(double time) {
        robot.leftFront.setPower(FORWARD_SPEED);
        robot.leftBack.setPower(-FORWARD_SPEED);
        robot.rightFront.setPower(-FORWARD_SPEED);
        robot.rightBack.setPower(FORWARD_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "shuffle right %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }




    }
    private void GrabStone() {
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1.5)
        {
            robot.clawDrop.setPosition(-180);
            telemetry.addData("Claw Drop", "-180");
            telemetry.update();
        }
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1.3)
        {
            robot.clawGripper.setPosition(250);
            telemetry.addData("Claw Grip", "250");
            telemetry.update();
        }
    }
    private void resetAngle()
    {
        lastAngles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
    private double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    /**
     * See if we are moving in a straight line and if not return a power correction value.
     * @return Power adjustment, + is adjust left - is adjust right.
     */
    private double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
    private void rotate(int degrees, double power)
    {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees > 0)
        {   // turn right.
            leftPower = power;
            rightPower = -power;
        }
        else if (degrees < 0)
        {   // turn left.
            leftPower = -power;
            rightPower = power;
        }
        else return;

        // set power to rotate.
        robot.leftFront.setPower(leftPower);
        robot.leftBack.setPower(leftPower);
        robot.rightFront.setPower(rightPower);
        robot.rightBack.setPower(rightPower);


        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {
                telemetry.addData("angle", getAngle());
                telemetry.addData("target", degrees);
                telemetry.update();
            }

            while (opModeIsActive() && getAngle() > degrees) {
                telemetry.addData("angle", getAngle());
                telemetry.addData("target", degrees);
                telemetry.update();
            }

        }
        else    // left turn.
            while (opModeIsActive() && getAngle() < degrees) {
                telemetry.addData("angle", getAngle());
                telemetry.addData("target", degrees);
                telemetry.update();
            }

        // turn the motors off.
        robot.rightBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.leftFront.setPower(0);

        // wait for rotation to stop.


        // reset angle tracking on new heading.
        resetAngle();
    }
    private void SpeedForward(double time) {
        robot.leftFront.setPower(1);
        robot.leftBack.setPower(1);
        robot.rightFront.setPower(1);
        robot.rightBack.setPower(1);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < time)) {
            telemetry.addData("Path", "Towards Block: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }
    }
}