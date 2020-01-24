/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Hopper;

public class Hop extends CommandBase {
  /**
   * Creates a new Hop.
   */
  Hopper hopper;

  public Hop(Hopper hopper) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.hopper = hopper;

    // ADD A REQUIREMENT
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    boolean hopperOn = RobotContainer.getHopper();
    if (hopperOn == true) {
      hopper.hopperStart(.8);
    } else {
      hopper.hopperStart(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hopper.hopperStart(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
