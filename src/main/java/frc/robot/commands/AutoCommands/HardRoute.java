/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoCommands.SubCommands.AlignShoot;
import frc.robot.commands.AutoCommands.SubCommands.Delay;
import frc.robot.commands.AutoCommands.SubCommands.DriveDistanceSimple;
import frc.robot.commands.AutoCommands.SubCommands.ElevateAuto;
import frc.robot.subsystems.Arms;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
/**
 * Defines a HardRoute object.
 */
public class HardRoute extends SequentialCommandGroup {
  /**
   * Creates a new HardRoute.
   * @param driveTrain DriveTrain subsystem
   * @param arms Arms subsystem
   * @param intake Intake subsystem
   * @param hood Hood subsystem
   * @param shooter Shooter subsystem
   * @param hopper Hopper subsystem
   * Runs selected commands sequentially.
   */
  public HardRoute(DriveTrain driveTrain, Arms arms, Hood hood, Shooter shooter, Hopper hopper, Turret turret,Elevator elevator) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new AlignShoot(shooter, hood, turret),new ElevateAuto(elevator, hopper, shooter),
          new DriveDistanceSimple(driveTrain, 130, hopper),new Delay(),new DriveDistanceSimple(driveTrain, -50,hopper),
          new AlignShoot(shooter, hood, turret),new ElevateAuto(elevator, hopper, shooter));
  }
}
