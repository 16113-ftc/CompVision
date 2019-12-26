package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Omega:Auto Blue Comp Vision", group = "Autonomous")
public class AutoOmegaBlueCompVision extends LinearOpMode {
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
        robot.leftFront.setPower(FORWARD_SPEED);
        robot.leftBack.setPower(FORWARD_SPEED);
        robot.rightFront.setPower(FORWARD_SPEED);
        robot.rightBack.setPower(FORWARD_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1.4)) {
            telemetry.addData("Path", "Towards Block: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

        StopSteering();

        //claw Drop
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1.1)
        {
            robot.clawDrop.setPosition(-180);
            telemetry.addData("Claw Drop", "-180");
            telemetry.update();
        }

        //Move ClawGripper servo
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1.5)
        {
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

    private void SteerForSeconds() {

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


}