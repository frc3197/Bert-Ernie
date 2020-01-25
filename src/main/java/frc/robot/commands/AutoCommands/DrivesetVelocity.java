/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveSetVelocity extends PIDCommand {
  DriveTrain drivetrain;
  public double velocity;
  double a;

  /**
   * Creates a new DrivesetVelocity.
   */

  public DriveSetVelocity(DriveTrain drivetrain, double velocity) {
    super(
        // The controller that the command will use
        new PIDController(Constants.PID_Constants.kDVelocity.kP, Constants.PID_Constants.kDVelocity.kI,
            Constants.PID_Constants.kDVelocity.kD),
        // This should return the measurement
        drivetrain::CalcFPS,
        // This should return the setpoint (can also be a constant)
        velocity,
        // This uses the output
        output -> drivetrain.tankDrive(output, output));
    this.velocity = velocity;
    this.drivetrain = drivetrain;
    // Use the output here

    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  public double getVelo() {
    return this.velocity;
  }

  @Override
  public boolean isFinished() {
    if (velocity == drivetrain.CalcFPS()) {
      return true;
    } else {
      return false;
    }
  }

  public void end(boolean interrupted) {
    drivetrain.tankDrive(0, 0);

  }
}