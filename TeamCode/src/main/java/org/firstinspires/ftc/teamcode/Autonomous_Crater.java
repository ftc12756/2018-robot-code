package org.firstinspires.ftc.teamcode;/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Pushbot: Auto Drive By Encoder To Crater", group="Pushbot")
//@Disabled
public class Autonomous_Crater extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime     runtime = new ElapsedTime();

    private DcMotor riseDrive = null;



    static final double     EL_COUNTS_PER_MOTOR_REV    = 4.0 ;    // HD hex motor encoder is 28 see: http://www.revrobotics.com/content/docs/Encoder-Guide.pdf
    static final double     EL_DRIVE_GEAR_REDUCTION    = 72.0 ;     // This is < 1.0 if geared UP
    static final double     EL_WHEEL_DIAMETER_INCHES   = 1.17 ;     // For figuring circumference TODO: could change
    static final double     EL_COUNTS_PER_INCH         = (EL_COUNTS_PER_MOTOR_REV * EL_DRIVE_GEAR_REDUCTION) /
            (EL_WHEEL_DIAMETER_INCHES * 3.1415);


    static final double     RISE_SPEED              = 0.2;

//    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         */

        riseDrive = hardwareMap.get(DcMotor.class, "rise_drive");
        //leftDrive2 = hardwareMap.get(DcMotor.class, "left_drive2");
        //rightDrive2 = hardwareMap.get(DcMotor.class, "right_drive2");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();



        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                leftDrive1.getCurrentPosition(),
                rightDrive1.getCurrentPosition());
        telemetry.update();

        telemetry.addData("Elevator0",  "Starting at %7d",
                riseDrive.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
         // S1: Forward 12 Inches with 5 Sec timeout
        elevatorDrive(RISE_SPEED, 3, 3);
        encoderDrive(DRIVE_SPEED, 2, 2, 3);
        elevatorDrive(RISE_SPEED, -3, 3);
        encoderDrive(DRIVE_SPEED,  12,  12, 5.0);  // S1: Forward 12 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   -9.5, 9.5, 4.0);  // S2: Turn Left 5 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, 34, 34, 4.0);  // S3: Reverse 12 Inches with 4 Sec timeout
        encoderDrive(TURN_SPEED,   9.5, -9.5, 4.0);  // S2: Turn Left 5 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED,  14,  14, 5.0);  // S1: Forward 12 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   4.75, -4.75, 4.0);  // S2: Turn Left 5 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED,  43,  43, 5.0);  // S1: Forward 12 Inches with 5 Sec timeout
        

        sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */

    public void elevatorDrive(double speed,
                                  double heightInches,
                                  double timeoutS) {
        int newHeightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newHeightTarget = riseDrive.getCurrentPosition() + (int)(heightInches * EL_COUNTS_PER_INCH);

            //Set to the target position
            riseDrive.setTargetPosition(newHeightTarget);

            // Turn On RUN_TO_POSITION
            riseDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            riseDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (riseDrive.isBusy() ))
            {
                // Display it for the driver.
                telemetry.addData("Elevator1",  "Running to %7d: %7d", newHeightTarget,
                                                    riseDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            riseDrive.setPower(0);
            //leftDrive2.setPower(0);
            //rightDrive2.setPower(0);

            // Turn off RUN_TO_POSITION
            riseDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
