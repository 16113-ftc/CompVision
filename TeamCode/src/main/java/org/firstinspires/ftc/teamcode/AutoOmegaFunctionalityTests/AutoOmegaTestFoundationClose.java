package org.firstinspires.ftc.teamcode.AutoOmegaFunctionalityTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.OmegaSquadRobot;


@Autonomous(name = "Omega:Auto Foundation Test(close)", group = "Autonomous")

@Disabled
public class AutoOmegaTestFoundationClose extends LinearOpMode {
    OmegaSquadRobot robot = new OmegaSquadRobot();
    double MIN_POSITION = 0, MAX_POSITION = 1;
    double foundationLeftPosition = 1.0;
    double foundationRightPosition = 0.0;


    //@Override
    public void runOpMode() {
        // Put initialization blocks here.
        robot.init(hardwareMap);

        telemetry.addData("Autonomous Mode Status", "Ready to Run");
        telemetry.update();

        waitForStart();

        while(foundationLeftPosition > MIN_POSITION && opModeIsActive() && foundationRightPosition < MAX_POSITION) {
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
    }

}

