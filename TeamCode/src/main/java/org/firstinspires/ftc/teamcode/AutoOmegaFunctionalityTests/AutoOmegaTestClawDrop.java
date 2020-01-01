package org.firstinspires.ftc.teamcode.AutoOmegaFunctionalityTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.OmegaSquadRobot;

@Autonomous(name = "Omega:Auto Claw Drop", group = "Autonomous")

@Disabled
public class AutoOmegaTestClawDrop extends LinearOpMode {
    OmegaSquadRobot robot = new OmegaSquadRobot();
    private ElapsedTime runtime = new ElapsedTime();

    //@Override
    public void runOpMode() {
        // Put initialization blocks here.
        robot.init(hardwareMap);

        telemetry.addData("Autonomous Mode Status", "Ready to Run");
        telemetry.update();

        waitForStart();
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1.5)
        {
            robot.clawDrop.setPosition(-180);
            telemetry.addData("Claw Drop", "-180");
            telemetry.update();
        }
    }
}
