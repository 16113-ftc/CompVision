package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class OmegaSquadRobot {

    public DcMotor leftFront = null;
    public DcMotor rightFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightBack = null;
    public DcMotor clawDC = null;


    public Servo clawDrop = null;
    public Servo clawGripper = null;
    public Servo foundationLeft = null;
    public Servo foundationRight = null;
    public Servo capperServo = null;

    public TouchSensor ts_bottom;
    public TouchSensor ts_top;

    public ColorSensor stoneColorSensor;
    public DistanceSensor stoneDistanceSensor;

    public BNO055IMU imu;


    HardwareMap hardwareMap = null;



    public OmegaSquadRobot() {

    }

    public void init(HardwareMap omegaSquadHardwareMap) {
        hardwareMap = omegaSquadHardwareMap;

        //Initialize all DC Motors
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        clawDC = hardwareMap.dcMotor.get("clawDC");


        clawDrop = hardwareMap.servo.get("clawDrop");
        clawGripper = hardwareMap.servo.get("clawGripper");
        foundationLeft = hardwareMap.servo.get("foundationLeft");
        foundationRight = hardwareMap.servo.get("foundationRight");

        ts_bottom = hardwareMap.touchSensor.get("ts_bottom");
        ts_top = hardwareMap.touchSensor.get("ts_top");
        stoneColorSensor = hardwareMap.colorSensor.get("stoneColorSensor");
        stoneDistanceSensor = hardwareMap.get(DistanceSensor.class, "stoneDistanceSensor");

        imu = hardwareMap.get(BNO055IMU.class,"imu");


        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        //Set all DC Motors power to ZERO
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        clawDC.setPower(0);


        //Set all motors to run without encoders
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        clawDC.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        //Initialize all Servo Motors
        clawDrop = hardwareMap.servo.get("clawDrop");
        clawGripper = hardwareMap.servo.get("clawGripper");
        //open = left, closed = right
        foundationLeft = hardwareMap.servo.get("foundationLeft");
        //down = left, up = right
        foundationRight = hardwareMap.servo.get("foundationRight");
        //down = right, up = left
        capperServo = hardwareMap.servo.get("capperServo");

        //Set All servo motors initial position
        clawDrop.setPosition(.9);
        foundationLeft.setPosition(1);
        foundationRight.setPosition(0);

        clawGripper.setDirection(Servo.Direction.FORWARD);
        clawGripper.setPosition(0);

    }
}
