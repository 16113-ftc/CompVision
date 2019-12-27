package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Test: Servo", group = "Autonomous")
public class TestServo extends LinearOpMode {
    DcMotor motor;
    Servo servo;
    TouchSensor touchSensor;
    ColorSensor colorSensor;
    DistanceSensor distanceSensor;

    private ElapsedTime runtime = new ElapsedTime();
    double MIN_POSITION = 0, MAX_POSITION = 1;
    float speed = 0.0f;

    @Override
    public void runOpMode() {
        // Put initialization blocks here.
        motor = hardwareMap.get(DcMotor.class, "motor");
        servo = hardwareMap.get(Servo.class, "servo");
        touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");

        servo.setPosition(0);

        telemetry.addData("Autonomous Mode Status", "Ready to Run");
        telemetry.update();

        double servoPosition = 0;
        Boolean Tf = true;

        waitForStart();

        runtime.reset();

        //claw Drop
        runtime.reset();
        while (opModeIsActive())
        {
            servoPosition = 0.01;

            servo.setPosition(0);
            telemetry.addData("Servo Position", "0");
            telemetry.update();
        }
    }
}