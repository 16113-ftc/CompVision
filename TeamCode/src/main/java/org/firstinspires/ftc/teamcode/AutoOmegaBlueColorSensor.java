package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodSkyStone;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Autonomous(name = "Omega:Auto Blue Color Sensor", group = "Autonomous")
public class AutoOmegaBlueColorSensor extends LinearOpMode {
    //1.5 seconds of spinning at 0.75 = 2 ft.
    OmegaSquadRobot robot = new OmegaSquadRobot();
    private ElapsedTime runtime = new ElapsedTime();


    static final double FORWARD_SPEED = 0.5;
    static final double REVERSE_SPEED = -0.5;

    double foundationLeftPosition;
    double foundationRightPosition;

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

        Boolean Tf = true;

        waitForStart();

        //Go towards the block
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.4)) {
            robot.leftFront.setPower(0.5);
            robot.leftBack.setPower(0.5);
            robot.rightFront.setPower(0.5);
            robot.rightBack.setPower(0.5);
            telemetry.addData("Distance (cm)",
                    String.format(Locale.US, "%.02f", robot.stoneDistanceSensor.getDistance(DistanceUnit.CM)));
            telemetry.update();

        }

        StopSteering();
        //can be removed later
        sleep(5000);
        boolean skyStoneFound = false;
        while (opModeIsActive() && !skyStoneFound) {

            if (robot.stoneColorSensor.red() <= 20 && robot.stoneColorSensor.red() >= 60
                    && robot.stoneColorSensor.green() <= 25 && robot.stoneColorSensor.red() >= 70
                    && robot.stoneColorSensor.blue() <= 20 && robot.stoneColorSensor.red() >= 60) {
                DisplayInfo();
                StopSteering();
                skyStoneFound = true;
                sleep(5000);
            } else {
                robot.leftFront.setPower(FORWARD_SPEED);
                robot.leftBack.setPower(-FORWARD_SPEED);
                robot.rightFront.setPower(-FORWARD_SPEED);
                robot.rightBack.setPower(FORWARD_SPEED);
                DisplayInfo();
            }
        }

        ShuffleRight(0.5);
        StopSteering();
        DisplayInfo();
        sleep(5000);

        //claw Drop
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1.1) {
            robot.clawDrop.setPosition(-180);
            telemetry.addData("Claw Drop", "-180");
            telemetry.update();
        }

        //Move ClawGripper servo
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1.5) {
            robot.clawGripper.setPosition(250);
            telemetry.addData("Claw Grip", "250");
            telemetry.update();
        }

        //After grabbing the block, come backward
        robot.leftFront.setPower(REVERSE_SPEED);
        robot.leftBack.setPower(REVERSE_SPEED);
        robot.rightFront.setPower(REVERSE_SPEED);
        robot.rightBack.setPower(REVERSE_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1.5)) {
            telemetry.addData("Path", "Grabbed Block going back: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

        StopSteering();

        //Turn 90 Degrees to go under the alliance bridge
        robot.leftFront.setPower(0.5);
        robot.leftBack.setPower(0.5);
        robot.rightFront.setPower(-0.5);
        robot.rightBack.setPower(-0.5);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1.55)) {
            telemetry.addData("Path", "Turning 90 Deg to go under bridge: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

        StopSteering();

        //Pass through the alliance bridge
        robot.leftFront.setPower(FORWARD_SPEED);
        robot.leftBack.setPower(FORWARD_SPEED);
        robot.rightFront.setPower(FORWARD_SPEED);
        robot.rightBack.setPower(FORWARD_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 3.8)) {
            telemetry.addData("Path", "Passing under alliance bridge: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

        StopSteering();

        //Turn towards foundation
        robot.leftFront.setPower(-0.5);
        robot.leftBack.setPower(-0.5);
        robot.rightFront.setPower(-0.5);
        robot.rightBack.setPower(-0.5);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1)) {
            telemetry.addData("Path", "Turning 90 Deg towards foundation: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

        StopSteering();

        //Placing the stone on foundation
        robot.clawGripper.setPosition(-45);
        sleep(1000);
        robot.clawDrop.setPosition(180);
        telemetry.addData("Grabbing", true);
        telemetry.update();

        //
        robot.leftFront.setPower(0.5);
        robot.leftBack.setPower(-0.5);
        robot.rightFront.setPower(-0.5);
        robot.rightBack.setPower(0.5);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Turning 90 Deg to go under bridge: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }


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

    private void Spin90DegreesClockwise() {

    }

    private void Spin90DegreesCounterClockwise() {

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

    private void DisplayInfo() {
        telemetry.addData("Distance (cm)",
                String.format(Locale.US, "%.02f", robot.stoneDistanceSensor.getDistance(DistanceUnit.CM)));
        telemetry.addData("Alpha", robot.stoneColorSensor.alpha());
        telemetry.addData("Red  ", robot.stoneColorSensor.red());
        telemetry.addData("Green", robot.stoneColorSensor.green());
        telemetry.addData("Blue ", robot.stoneColorSensor.blue());
        telemetry.update();
    }
}