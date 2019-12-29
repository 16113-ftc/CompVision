package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "Omega:Auto Drive (blue)", group = "Autonomous")
public class AutoOmegaBlue extends LinearOpMode {
    //1.5 seconds of spinning at 0.75 = 2 ft.
    OmegaSquadRobot robot = new OmegaSquadRobot();
    private ElapsedTime runtime = new ElapsedTime();

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

        Boolean Tf = true;
        waitForStart();

        SteerForSeconds(1.4);
        StopSteering();

        GrabStone();

        //After grabbing the block, come backward
        robot.leftFront.setPower(REVERSE_SPEED);
        robot.leftBack.setPower(REVERSE_SPEED);
        robot.rightFront.setPower(REVERSE_SPEED);
        robot.rightBack.setPower(REVERSE_SPEED);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Grabbed Block going back: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }
        StopSteering();

        //Turn 90 Degrees to go under the alliance bridge
        Spin90Left();

        StopSteering();

        SteerForSeconds(5.5);

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

        Spin90Right();

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

        robot.leftFront.setPower(-0.5);
        robot.leftBack.setPower(-0.5);
        robot.rightFront.setPower(-0.5);
        robot.rightBack.setPower(-0.5);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Go back after placing stone: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

        Spin90Right();

        StopSteering();

        while(opModeIsActive() &&!robot.ts_top.isPressed()) {
            robot.clawDC.setPower(-0.5);
            //power of 1 is needed to go up
            telemetry.addData("Drop Claw", "down");
            telemetry.update();
        }

        robot.clawDC.setPower(0);

        SteerForSeconds(3);

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

        while (opModeIsActive() && (runtime.seconds() < 1.55)) {
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

        while (opModeIsActive() && (runtime.seconds() < 1.65)) {
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
}