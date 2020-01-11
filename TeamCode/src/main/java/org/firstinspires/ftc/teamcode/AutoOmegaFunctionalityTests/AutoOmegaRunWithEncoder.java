
        package org.firstinspires.ftc.teamcode.AutoOmegaFunctionalityTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OmegaSquadRobot;

@Autonomous(name="Drive Encoder", group="Exercises")
//@Disabled
public class AutoOmegaRunWithEncoder extends LinearOpMode
{
    OmegaSquadRobot robot = new OmegaSquadRobot();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        // wait for start button.

        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        // set left motor to run to 5000 encoder counts.

        int curPosition = robot.rightFront.getCurrentPosition();

        // set both motors to 25% power. Movement will start.

        robot.leftFront.setPower(0.5);
        robot.leftBack.setPower(0.5);
        robot.rightFront.setPower(0.5);
        robot.rightBack.setPower(0.5);


        // wait while opmode is active and left motor is busy running to position.
        resetStartTime();
        while (opModeIsActive() &&  getRuntime() > 5) {
            telemetry.addData("encoder-fwd ", robot.rightFront.getCurrentPosition() + "  busy=" + robot.leftFront.isBusy());
            telemetry.addData("Time Elapsed ", getRuntime() );
            telemetry.update();
            idle();
        }

        // set motor power to zero to turn off motors. The motors stop on their own but
        // power is still applied so we turn off the power.

        robot.leftFront.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        robot.leftBack.setPower(0);

        telemetry.addData("encoder-fwd ", Math.abs(curPosition - robot.rightFront.getCurrentPosition()));
        telemetry.update();
        sleep(5000);
        // wait 5 sec so you can observe the final encoder position.

    }
}