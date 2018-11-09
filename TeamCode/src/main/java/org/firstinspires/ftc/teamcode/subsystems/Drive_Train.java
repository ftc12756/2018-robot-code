package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public abstract class Drive_Train extends  LinearOpMode{

    private DcMotor leftDrive1 = null;
    private DcMotor rightDrive1 = null;
    //private DcMotor leftDrive2 = null;
    //private DcMotor rightDrive2 = null;

    static final double     COUNTS_PER_MOTOR_REV    = 28.0 ;    // HD hex motor encoder is 28 see: http://www.revrobotics.com/content/docs/Encoder-Guide.pdf
    static final double     DRIVE_GEAR_REDUCTION    = 20.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.52 ;     // For figuring circumference TODO: could change
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    static final double     DRIVE_SPEED             = 0.4;
    static final double     TURN_SPEED              = 0.3;


    public Drive_Train(){

        leftDrive1 = hardwareMap.get(DcMotor.class, "left_drive1");
        rightDrive1 = hardwareMap.get(DcMotor.class, "right_drive1");

        leftDrive1.setDirection(DcMotor.Direction.FORWARD);
        rightDrive1.setDirection(DcMotor.Direction.REVERSE);
        //leftDrive2.setDirection(DcMotor.Direction.FORWARD);
        //rightDrive2.setDirection(DcMotor.Direction.REVERSE);

        leftDrive1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // leftDrive2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rightDrive2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //leftDrive2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget1;
        int newRightTarget1;
        int newLeftTarget2;
        int newRightTarget2;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget1 = leftDrive1.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget1 = rightDrive1.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            //  newLeftTarget2 = leftDrive2.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            // newRightTarget2 = rightDrive2.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);

            //Set to the target position
            leftDrive1.setTargetPosition(newLeftTarget1);
            rightDrive1.setTargetPosition(newRightTarget1);
            //leftDrive2.setTargetPosition(newLeftTarget2);
            // rightDrive2.setTargetPosition(newRightTarget2);

            // Turn On RUN_TO_POSITION
            leftDrive1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //leftDrive2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //rightDrive2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftDrive1.setPower(Math.abs(speed));
            rightDrive1.setPower(Math.abs(speed));
            //leftDrive2.setPower(Math.abs(speed));
            //rightDrive2.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftDrive1.isBusy() || rightDrive1.isBusy() ))
                //  leftDrive2.isBusy() || rightDrive2.isBusy()))

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget1,  newRightTarget1); {
                leftDrive1.getCurrentPosition();
                rightDrive1.getCurrentPosition();
                telemetry.update();
            }

            // Stop all motion;
            leftDrive1.setPower(0);
            rightDrive1.setPower(0);
            //leftDrive2.setPower(0);
            //rightDrive2.setPower(0);

            // Turn off RUN_TO_POSITION
            leftDrive1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //leftDrive2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //rightDrive2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
