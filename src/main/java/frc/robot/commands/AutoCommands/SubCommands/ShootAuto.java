/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoCommands.SubCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;

public class ShootAuto extends CommandBase {
  private Shooter shooter;

  /**
   * Creates a new ShooterTest.
   */
  public ShootAuto(Shooter shooter) {
    this.shooter = shooter;
    addRequirements(shooter);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  /**
   * Called when the command is initially scheduled.
   */
  @Override
  public void initialize() {
    shooter.resetEncoder();
  }

  /**
   * Called every time the scheduler runs while the command is scheduled.
   */
  @Override
  public void execute() {
    SmartDashboard.putNumber("Shooter Encoder Ticks", shooter.getEncoderValue());
    SmartDashboard.putNumber("Right Trigger", RobotContainer.getShooter());
    SmartDashboard.putNumber("Shooter Velocity", shooter.getVelocity());
    shooter.shooterVelocity(RobotContainer.targetVelocity(1, 5500));
    // hopper.hopperElevator(RobotContainer.getShooter());
    // if(RobotContainer.getShooter() > 0.2){
    //   hopper.hopperFeeder(-0.4);
    // }else{
    //   hopper.hopperFeeder(0);
    // }
    // takes right trigger axis of driver 1 and runs up to 5500 RPM
  }

  /**
   * Called once the command ends or is interrupted.
   */
  @Override
  public void end(boolean interrupted) {
    
  }

  /**
   * Returns true when the command should end.
   */
  @Override
  public boolean isFinished() {
    Timer.delay(3);
    return true;
  }
}
