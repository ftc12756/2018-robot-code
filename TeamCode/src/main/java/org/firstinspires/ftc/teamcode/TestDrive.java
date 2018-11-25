package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;



@TeleOp(name="Test Motor1", group="Linear Opmode")
//@Disabled


public class TestDrive extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive1 = null;
    private DcMotor rightDrive1 = null;
    private DcMotor leftDrive2 = null;
    private DcMotor rightDrive2 = null;
    private DcMotor riseDrive = null;
    private DcMotor PacDrive = null;
    private CRServo hookServo = null;
    private CRServo stomachServo = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive1 = hardwareMap.get(DcMotor.class, "left_drive1");
        rightDrive1 = hardwareMap.get(DcMotor.class, "right_drive1");
        leftDrive2 = hardwareMap.get(DcMotor.class, "left_drive2");
        rightDrive2 = hardwareMap.get(DcMotor.class, "right_drive2");
        riseDrive = hardwareMap.get (DcMotor.class, "rise_drive");
        hookServo = hardwareMap.get (CRServo.class, "hook_servo");
        stomachServo = hardwareMap.get (CRServo.class, "scoop_servo");
        PacDrive = hardwareMap.get (DcMotor.class, "pac_drive");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive1.setDirection(DcMotor.Direction.REVERSE);
        rightDrive1.setDirection(DcMotor.Direction.FORWARD);
        leftDrive2.setDirection(DcMotor.Direction.REVERSE);
        rightDrive2.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;
            // Speed scale is for matching speed for 2 different motor gear ratios (front vs. rear)
            double speedScale = 72.0 / 90.0;
            double risePower = 1.0;
            double riseScale = .2;
            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.left_stick_x;
            leftPower    = Range.clip(drive + turn, -0.5, 0.5) ;
            rightPower   = Range.clip(drive - turn, -0.5, 0.5) ;

            //servo controls


            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            leftDrive1.setPower(leftPower * speedScale);
            rightDrive1.setPower(rightPower * speedScale);
            leftDrive2.setPower(leftPower);
            rightDrive2.setPower(rightPower);

            double          stomachOffSet      = 0;                       // Servo mid position
            final double    STOMACH_SPEED      = 0.1 ;                   // sets rate to move servo


            // Use gamepad left & right Bumpers to open and close the claw
            if (gamepad2.right_bumper)
                stomachOffSet += STOMACH_SPEED;
            else if (gamepad2.left_bumper)
                stomachOffSet -= STOMACH_SPEED;

            // Move both servos to new position.  Assume servos are mirror image of each other.
           stomachOffSet = Range.clip(stomachOffSet, -0.5, 0.5);
            //leftServo1.setPosition(MID_SERVO + stomachOffSet);


            // Use gamepad buttons to move arm up (Y) and down (A)
            if (gamepad2.y)
                hookServo.setPower(0.5);

            if (gamepad2.b)
                hookServo.setPower(0);

            if (gamepad2.x)
                hookServo.setPower(-0.5);

            if (gamepad2.dpad_up)
                PacDrive.setPower(0.5);

            if (gamepad2.dpad_down)
                PacDrive.setPower(-0.5);

            risePower = (gamepad2.left_stick_y * riseScale);
            riseDrive.setPower(risePower);

              //  leftServo1.setPower(ARM_DOWN_POWER);
            //else
              //  leftServo1.setPower(0.0);

            // Send telemetry message to signify robot running;
            telemetry.addData("Stomach",  "Offset = %.2f", stomachOffSet);
            telemetry.addData("Rise",  "Power = %.2f", risePower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
