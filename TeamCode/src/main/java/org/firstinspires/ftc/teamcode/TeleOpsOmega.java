package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TeleOpsOmega", group = "TeleOp")
public class TeleOpsOmega extends LinearOpMode {
    OmegaSquadRobot robot = new OmegaSquadRobot();

    private static final boolean PHONE_IS_PORTRAIT = true  ;

    double MIN_POSITION = 0, MAX_POSITION = 1;
    double foundationLeftPosition = 1.0;
    double foundationRightPosition = 0.0;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        float speed = 0.0f;
        double turnspeed = 0.0f;



        robot.init(hardwareMap);

        telemetry.addData("Driver", "Waiting for you to Start");
        telemetry.update();

        waitForStart();

        // Put run blocks here.
        while (opModeIsActive()) {
            speed = gamepad1.left_stick_y;
            robot.leftFront.setPower(-speed);
            robot.leftBack.setPower(-speed);
            robot.rightFront.setPower(-speed);
            robot.rightBack.setPower(-speed);

            turnspeed = gamepad1.left_stick_x;
            robot.leftFront.setPower(-turnspeed);
            robot.leftBack.setPower(-turnspeed);
            robot.rightFront.setPower(turnspeed);
            robot.rightBack.setPower(turnspeed);

            //gamepad1.dpad_right is shuffling right when you press
            // the right button on the dpad
            if (gamepad1.dpad_right) {
                robot.leftFront.setPower(0.75);
                robot.leftBack.setPower(-0.75);
                robot.rightFront.setPower(-0.75);
                robot.rightBack.setPower(0.75);
            }

            //gamepad1.dpad_left is shuffling left when you press
            // the left button on the dpad
            if (gamepad1.dpad_left) {
                robot.leftFront.setPower(-0.75);
                robot.leftBack.setPower(0.75);
                robot.rightFront.setPower(0.75);
                robot.rightBack.setPower(-0.75);
            }

            if (gamepad1.dpad_up) {
                robot.leftFront.setPower(0.2);
                robot.leftBack.setPower(0.2);
                robot.rightFront.setPower(0.2);
                robot.rightBack.setPower(0.2);
            }

            if (gamepad1.dpad_down) {
                robot.leftFront.setPower(-0.2);
                robot.leftBack.setPower(-0.2);
                robot.rightFront.setPower(0.2);
                robot.rightBack.setPower(0.2);
            }

            //gamepad2.dpad_up is moving the claw up when you press
            // up on the dpad
            if (gamepad2.dpad_up && !robot.ts_bottom.isPressed()) {
                robot.clawDC.setPower(0.5);
                telemetry.addData("G2 D-Up", gamepad1.dpad_up);
                telemetry.update();
            } else if (gamepad2.dpad_down && !robot.ts_top.isPressed()) {
                robot.clawDC.setPower(-0.5);
                telemetry.addData("G2 D-Down", gamepad1.dpad_down);
                telemetry.update();
            } else {
                robot.clawDC.setPower(0);
            }



            if (gamepad2.a) {
                robot.clawDrop.setPosition(-180);
                sleep(1000);
                robot.clawGripper.setPosition(250);
                telemetry.addData("Grabbing", gamepad2.a);
                telemetry.update();
            }
            if (gamepad2.b) {
                robot.clawGripper.setPosition(-45);
                sleep(1000);
                robot.clawDrop.setPosition(180);
                telemetry.addData("Grabbing", gamepad2.b);
                telemetry.update();
            }



            if (robot.ts_top.isPressed()) {
                telemetry.addData("ts_top", "Is Pressed");
                telemetry.update();
                robot.clawDC.setPower(0);
            }

            if (robot.ts_bottom.isPressed()) {
                telemetry.addData("ts_bottom", "Is Pressed");
                telemetry.update();
                robot.clawDC.setPower(0);
            }

            if (foundationLeftPosition > MIN_POSITION && gamepad2.x && foundationRightPosition < MAX_POSITION) {
                foundationLeftPosition -= .01;
                robot.foundationLeft.setPosition(Range.clip(foundationLeftPosition, MIN_POSITION, MAX_POSITION));
                telemetry.addData("Foundation Left",
                        "  Actual(left)=" + robot.foundationLeft.getPosition()
                                + "  Position(left)=" + foundationLeftPosition);
                telemetry.update();

                foundationRightPosition += .01;
                robot.foundationRight.setPosition(Range.clip(foundationRightPosition, MIN_POSITION, MAX_POSITION));
                telemetry.addData("Foundation Right",
                        "  Actual(right)=" + robot.foundationRight.getPosition()
                                + "  Position(right)=" + foundationRightPosition);
                telemetry.update();



            }



            if(foundationLeftPosition < MAX_POSITION && gamepad2.y && foundationRightPosition > MIN_POSITION) {
                foundationLeftPosition += .01;
                robot.foundationLeft.setPosition(Range.clip(foundationLeftPosition, MIN_POSITION, MAX_POSITION));
                telemetry.addData("Foundation Left",
                        "  Actual(left)=" + robot.foundationLeft.getPosition()
                                + "  Position(left)=" + foundationLeftPosition);
                telemetry.update();

                foundationRightPosition -= .01;
                robot.foundationRight.setPosition(Range.clip(foundationRightPosition, MIN_POSITION, MAX_POSITION));
                telemetry.addData("Foundation Right",
                        "  Actual(right)=" + robot.foundationRight.getPosition()
                                + "  Position(right)=" + foundationRightPosition);
                telemetry.update();


            }

            if (gamepad2.left_bumper) {
                robot.capperServo.setPosition(0);

            }

            if (gamepad2.right_bumper) {
                robot.capperServo.setPosition(1);
            }

            idle();


        }


    }
}