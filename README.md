## Welcome!
This is FTC Team 12756's  robot source code for 2018 - Rover Ruckus.

This source code is based on a clone of the official FTC example code.

This GitHub repository contains the source code that is used to build an Android app to control a *FIRST* Tech Challenge competition robot.  To use this SDK, download/clone the entire project to your local computer.

## Getting Started

If you are new to the *FIRST* Tech Challenge software and control system, you should visit the online wiki to learn how to install, configure, and use the software and control system:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/ftctechnh/ftc_app/wiki

Note that the wiki is an "evergreen" document that is constantly being updated and edited.  It contains the most current information about the *FIRST* Tech Challenge software and control system.

## Downloading the Project
It is important to note that this repository is large and can take a long time and use a lot of space to download. If you would like to save time and space, there are some options that you can choose to download only the most current version of the Android project folder:

* If you are a git user, *FIRST* recommends that you use the --depth command line argument to only clone the most current version of the repository:

<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;git clone --depth=1 https://github.com/ftctechnh/ftc_app.git</p>

* Or, if you prefer, you can use the "Download Zip" button available through the main repository page.  Downloading the project as a .ZIP file will keep the size of the download manageable.

* You can also download the project folder (as a .zip or .tar.gz archive file) from the Downloads subsection of the Releases page for this repository.

Once you have downloaded and uncompressed (if needed) your folder, you can use Android Studio to import the folder  ("Import project (Eclipse ADT, Gradle, etc.)").

## Getting Help
### User Documentation and Tutorials
*FIRST* maintains an online wiki with information and tutorials on how to use the *FIRST* Tech Challenge software and robot control system.  You can access the wiki at the following address:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/ftctechnh/ftc_app/wiki

### Javadoc Reference Material
The Javadoc reference documentation for the FTC SDK is now available online.  Visit the following URL to view the FTC SDK documentation as a live website:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://ftctechnh.github.io/ftc_app/doc/javadoc/index.html    

Documentation for the FTC SDK is also included with this repository.  There is a subfolder called "doc" which contains several subfolders:

 * The folder "apk" contains the .apk files for the FTC Driver Station and FTC Robot Controller apps.
 * The folder "javadoc" contains the JavaDoc user documentation for the FTC SDK.

### Online User Forum
For technical questions regarding the SDK, please visit the FTC Technology forum:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://ftcforum.usfirst.org/forumdisplay.php?156-FTC-Technology

**************************************************************************************
# FTC Example Code Release Information
**************************************************************************************

Version 4.0 (released on 18.09.12)

Changes include:
 * Initial support for UVC compatible cameras 
    - If UVC camera has a unique serial number, RC will detect and enumerate by serial number.
    - If UVC camera lacks a unique serial number, RC will only support one camera of that type connected.
    - Calibration settings for a few cameras are included (see TeamCode/src/main/res/xml/teamwebcamcalibrations.xml for details).
    - User can upload calibration files from Program and Manage web interface.
    - UVC cameras seem to draw a fair amount of electrical current from the USB bus.
         + This does not appear to present any problems for the REV Robotics Control Hub.
	 + This does seem to create stability problems when using some cameras with an Android phone-based Robot Controller.
	 + FTC Tech Team is investigating options to mitigate this issue with the phone-based Robot Controllers.
    - Updated sample Vuforia Navigation and VuMark Op Modes to demonstrate how to use an internal phone-based camera and an external UVC webcam.    

 * Support for improved motor control.
    - REV Robotics Expansion Hub firmware 1.8 and greater will support a feed forward mechanism for closed loop motor control.
    - FTC SDK has been modified to support PIDF coefficients (proportional, integral, derivative, and feed forward).
    - FTC Blocks development tool modified to include PIDF programming blocks.
    - Deprecated older PID-related methods and variables.
    - REV's 1.8.x PIDF-related changes provide a more linear and accurate way to control a motor.

 * Wireless
    - Added 5GHz support for wireless channel changing for those devices that support it.
        + Tested with Moto G5 and E4 phones.
	+ Also tested with other (currently non-approved) phones such as Samsung Galaxy S8.

* Improved Expansion Hub firmware update support in Robot Controller app
    - Changes to make the system more robust during the firmware update process (when performed through Robot Controller app).
    - User no longer has to disconnect a downstream daisy-chained Expansion Hub when updating an Expansion Hub's firmware.
        + If user is updating an Expansion Hub's firmware through a USB connection, he/she does not have to disconnect RS485 connection to other Expansion Hubs.
	+ The user still must use a USB connection to update an Expansion Hub's firmware.
	+ The user cannot update the Expansion Hub firmware for a downstream device that is daisy chained through an RS485 connection.
    - If an Expansion Hub accidentally gets "bricked" the Robot Controller app is now more likely to recognize the Hub when it scans the USB bus.
        + Robot Controller app should be able to detect an Expansion Hub, even if it accidentally was bricked in a previous update attempt.
	+ Robot Controller app should be able to install the firmware onto the Hub, even if if accidentally was bricked in a previous update attempt.
 
 * Resiliency
    - FTC software can detect and enable an FTDI reset feature that is available with REV Robotics v1.8 Expansion Hub firmware and greater.
        + When enabled, the Expansion Hub can detect if it hasn't communicated with the Robot Controller over the FTDI (USB) connection.
	+ If the Hub hasn't heard from the Robot Controller in a while, it will reset the FTDI connection.
	+ This action helps system recover from some ESD-induced disruptions.
    - Various fixes to improve reliability of FTC software.
     
 * Blocks
    - Fixed errors with string and list indices in blocks export to java.
    - Support for USB connected UVC webcams.
    - Refactored optimized Blocks Vuforia code to support Rover Ruckus image targets.
    - Added programming blocks to support PIDF (proportional, integral, derivative and feed forward) motor control.
    - Added formatting options (under Telemetry and Miscellaneous categories) so user can set how many decimal places to display a numerical value.
    - Support to play audio files (which are uploaded through Blocks web interface) on Driver Station in addition to the Robot Controller.
    - Fixed bug with Download Image of Blocks feature.
    - Support for REV Robotics Blinkin LED Controller.
    - Support for REV Robotics 2m Distance Sensor.
    - Added support for a REV Touch Sensor (no longer have to configure as a generic digital device).
    - Added blocks for DcMotorEx methods.
        + These are enhanced methods that you can use when supported by the motor controller hardware.
	+ The REV Robotics Expansion Hub supports these enhanced methods.
	+ Enhanced methods include methods to get/set motor velocity (in encoder pulses per second), get/set PIDF coefficients, etc..

 * Modest Improvements in Logging
    - Decrease frequency of battery checker voltage statements.
    - Removed non-FTC related log statements (wherever possible).
    - Introduced a "Match Logging" feature.
        + Under "Settings" a user can enable/disable this feature (it's disabled by default).
	+ If enabled, user provides a "Match Number" through the Driver Station user interface (top of the screen).
	    * The Match Number is used to create a log file specifically with log statements from that particular Op Mode run.
	    * Match log files are stored in /sdcard/FIRST/matlogs on the Robot Controller.
	    * Once an op mode run is complete, the Match Number is cleared.
	    * This is a convenient way to create a separate match log with statements only related to a specific op mode run.
 
 * New Devices
    - Support for REV Robotics Blinkin LED Controller.
    - Support for REV Robotics 2m Distance Sensor.
    - Added configuration option for REV 20:1 HD Hex Motor.
    - Added support for a REV Touch Sensor (no longer have to configure as a generic digital device).
    
 * Miscellaneous
    - Fixed some errors in the definitions for acceleration and velocity in our javadoc documentation.
    - Added ability to play audio files on Driver Station
    - When user is configuring an Expansion Hub, the LED on the Expansion Hub will change blink pattern (purple-cyan)  to indicate which Hub is currently being configured.
    - Renamed I2cSensorType to I2cDeviceType.
    - Added an external sample Op Mode that demonstrates localization using 2018-2019 (Rover Ruckus presented by QualComm) Vuforia targets.
    - Added an external sample Op Mode that demonstrates how to use the REV Robotics 2m Laser Distance Sensor.
    - Added an external sample Op Mode that demonstrates how to use the REV Robotics Blinkin LED Controller.
    - Re-categorized external Java sample Op Modes to "TeleOp" instead of "Autonomous".
    
Known issues:
 * Initial support for UVC compatible cameras
    - UVC cameras seem to draw significant amount of current from the USB bus.
        + This does not appear to present any problems for the REV Robotics Control Hub.
	+ This does seem to create stability problems when using some cameras with an Android phone-based Robot Controller.
	+ FTC Tech Team is investigating options to mitigate this issue with the phone-based Robot Controllers.
    - There might be a possible deadlock which causes the RC to become unresponsive when using a UVC webcam with a Nougat Android Robot Controller.

 * Wireless
    - When user selects a wireless channel, this channel does not necessarily persist if the phone is power cycled.
        + Tech Team is hoping to eventually address this issue in a future release.
	+ Issue has been present since apps were introduced (i.e., it is not new with the v4.0 release).
    - Wireless channel is not currently displayed for WiFi Direct connections.

 * Miscellaneous
    - The blink indication feature that shows which Expansion Hub is currently being configured does not work for a newly created configuration file.
        + User has to first save a newly created configuration file and then close and re-edit the file in order for blink indicator to work.


**************************************************************************************
# Release Information
**************************************************************************************

Version 3.6 (built on 17.12.18)

Changes include:
 * Blocks Changes
     - Uses updated Google Blockly software to allow users to edit their op modes on Apple iOS devices (including iPad and iPhone).
     - Improvement in Blocks tool to handle corrupt op mode files.
     - Autonomous op modes should no longer get switched back to tele-op after re-opening them to be edited.
     - The system can now detect type mismatches during runtime and alert the user with a message on the Driver Station.
 * Updated javadoc documentation for setPower() method to reflect correct range of values (-1 to +1).
 * Modified VuforiaLocalizerImpl to allow for user rendering of frames
     - Added a user-overrideable onRenderFrame() method which gets called by the class's renderFrame() method.

**************************************************************************************
# Release Information
**************************************************************************************

Version 3.5 (built on 17.10.30)

Changes with version 3.5 include:
 * Introduced a fix to prevent random op mode stops, which can occur after the Robot Controller app has been paused and then resumed (for example, when a user temporarily turns off the display of the Robot Controller phone, and then turns the screen back on).
 * Introduced a fix to prevent random op mode stops, which were previously caused by random peer disconnect events on the Driver Station.
 * Fixes issue where log files would be closed on pause of the RC or DS, but not re-opened upon resume.
 * Fixes issue with battery handler (voltage) start/stop race.
 * Fixes issue where Android Studio generated op modes would disappear from available list in certain situations.
 * Fixes problem where OnBot Java would not build on REV Robotics Control Hub.
 * Fixes problem where OnBot Java would not build if the date and time on the Robot Controller device was "rewound" (set to an earlier date/time).
 * Improved error message on OnBot Java that occurs when renaming a file fails.
 * Removed unneeded resources from android.jar binaries used by OnBot Java to reduce final size of Robot Controller app.
 * Added MR_ANALOG_TOUCH_SENSOR block to Blocks Programming Tool.

**************************************************************************************
# Release Information
**************************************************************************************

Version 3.4 (built on 17.09.06)

Changes with version 3.4 include:
 * Added telemetry.update() statement for BlankLinearOpMode template.
 * Renamed sample Block op modes to be more consistent with Java samples.
 * Added some additional sample Block op modes.
 * Reworded OnBot Java readme slightly.

**************************************************************************************
