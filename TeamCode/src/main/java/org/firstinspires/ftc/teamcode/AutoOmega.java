package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "AutoOmega", group = "")
public class AutoOmega extends LinearOpMode {

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor clawDC;
    private DcMotor foundationDC;

    private Servo clawDrop;
    private Servo clawGripper;
    private Servo foundationLeft;
    private Servo foundationRight;

    double foundationLeftPosition;
    double foundationRightPosition;

    double FOUNDATION_INITIAL_POSITION = 0;

    double MIN_POSITION = 0, MAX_POSITION = 1;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        // Put initialization blocks here.

        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        clawDC = hardwareMap.dcMotor.get("clawDC");
        foundationDC = hardwareMap.dcMotor.get("foundationDC");
        clawDrop = hardwareMap.servo.get("clawDrop");
        clawGripper = hardwareMap.servo.get("clawGripper");
        foundationLeft = hardwareMap.servo.get("foundationLeft");
        foundationRight = hardwareMap.servo.get("foundationRight");
        float speed = 0.0f;
        double turnspeed = 0.0f;

        telemetry.addData("Mode", "waiting");

        waitForStart();

        foundationLeftPosition = FOUNDATION_INITIAL_POSITION;
        foundationRightPosition = FOUNDATION_INITIAL_POSITION;


        if (opModeIsActive()) {

            leftFront.setPower(0.5);
            leftBack.setPower(0.5);
            rightFront.setPower(-0.5);
            rightBack.setPower(-0.5);
            sleep(2000);

            leftFront.setPower(0);
            leftBack.setPower(0);
            rightFront.setPower(0);
            rightBack.setPower(0);

            clawDrop.setPosition(-180);
            sleep(1000);
            clawGripper.setPosition(250);
            telemetry.addData("Grabbing", gamepad2.a);
            telemetry.update();

            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.update();
            }
        }
    }
}