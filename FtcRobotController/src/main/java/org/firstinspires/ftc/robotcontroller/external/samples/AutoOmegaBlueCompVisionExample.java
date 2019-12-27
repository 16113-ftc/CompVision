package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaSkyStone;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodSkyStone;

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "Omega:Auto Blue Comp Vision Example", group = "Autonomous")
@Disabled
public class AutoOmegaBlueCompVisionExample extends LinearOpMode {
    //1.5 seconds of spinning at 0.75 = 2 ft.
    OmegaSquadRobot robot = new OmegaSquadRobot();
    private ElapsedTime runtime = new ElapsedTime();

    private VuforiaSkyStone vuforiaSkyStone;
    private TfodSkyStone tfodSkyStone;

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

        vuforiaSkyStone = new VuforiaSkyStone();
        tfodSkyStone = new TfodSkyStone();

        List<Recognition> recognitions = new ArrayList<Recognition>();
        int index = 0;

        vuforiaSkyStone.initialize(
                "AXuJ5Oz/////AAABmQJXCiv7P0lpn2i8TzOD7GZTbO3W8SEVstdC6PAqVUBTkzb3D6JUCyRJmgTJ0bxiVXBiMeb1dBCb4WQp0ZOmxQiWYeoyrRq05f0EvoP74axLnzPt2os0X2YQQJXd2A/H37vvO/620kEFfttERkjeqBco56diQ2oyxGraywCANpprRuHfHHXI91sz85w2mGD4C3SHZ53vy86/1ze5Z4X1veFuIZOQHg02sgwPEjn9OeHkNVFVo9U7jkzVwchcXOTpIGcyMw55W2GR6kPAq55EjaIFb2h+/A2i2n8DYC9rTaMHWC3sLo7BSMmNvm4nFfEWiQh/jAGvFLvGCpdK7IIjezhMvAxdqADqMjUklSEPrrxU", // vuforiaLicenseKey
                VuforiaLocalizer.CameraDirection.BACK, // cameraDirection
                true, // useExtendedTracking
                false, // enableCameraMonitoring
                VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES, // cameraMonitorFeedback
                0, // dx
                0, // dy
                0, // dz
                90, // xAngle
                -90, // yAngle
                0, // zAngle
                true); // useCompetitionFieldTargetLocations
        // Set min confidence threshold to 0.7
        tfodSkyStone.initialize(vuforiaSkyStone, 0.7F, true, true);
        // Initialize TFOD before waitForStart.
        // Init TFOD here so the object detection labels are visible
        // in the Camera Stream preview window on the Driver Station.
        tfodSkyStone.activate();
        robot.init(hardwareMap);

        telemetry.addData("Autonomous Mode Status", "Ready to Run");
        telemetry.update();

        Boolean Tf = true;

        waitForStart();

        //Go towards the block

        ShuffleLeft(1.8);
        //stop
        StopSteering();

        int searchCount = 0;

        boolean skystoneFound = false;

        while (opModeIsActive() && (!skystoneFound || searchCount >10)) {
            searchCount += 1;
            robot.leftFront.setPower(0.5);
            robot.leftBack.setPower(0.5);
            robot.rightFront.setPower(0.5);
            robot.rightBack.setPower(0.5);

            sleep(300);

            StopSteering();
            sleep(1500);

            recognitions = tfodSkyStone.getRecognitions();
            // If list is empty, inform the user. Otherwise, go
            // through list and display info for each recognition.
            if (recognitions.size() == 0) {
                telemetry.addData("TFOD", "No items detected.");
            } else {
                index = 0;
                // Iterate through list and call a function to
                // display info for each recognized object.
                // TODO: Enter the type for variable named recognition
                for (Recognition recognition : recognitions) {
                    // Display info.
                    displayInfo(recognition, index);
                    if (recognition.getLabel().equalsIgnoreCase("Skystone")) {
                        StopSteering();
                        skystoneFound = true;
                        break;
                    }

                    // Increment index.
                    index = index + 1;
                }
            }
            telemetry.update();
        }



        robot.leftFront.setPower(0.5);
        robot.leftBack.setPower(0.5);
        robot.rightFront.setPower(-0.5);
        robot.rightBack.setPower(-0.5);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1.55)) {
            telemetry.addData("Path", "Turning 90 Deg: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

        SteerForSeconds(1);


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
            telemetry.addData("Path", "Towards Block: %2.5f S  Elapsed", runtime.seconds());
            telemetry.update();
        }

    }

    private void displayInfo(Recognition recognition, int i) {
        // Display label info.
        // Display the label and index number for the recognition.
        telemetry.addData("label " + i, recognition.getLabel());
        // Display upper corner info.
        // Display the location of the top left corner
        // of the detection boundary for the recognition
        telemetry.addData("Left, Top " + i, recognition.getLeft() + ", " + recognition.getTop());
        // Display lower corner info.
        // Display the location of the bottom right corner
        // of the detection boundary for the recognition
        telemetry.addData("Right, Bottom " + i, recognition.getRight() + ", " + recognition.getBottom());
    }
}