/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
public class DriveTrain extends SubsystemBase {
  private CANSparkMax l1SparkMax = new CANSparkMax(Constants.CANSparkMaxID.kLeft1.id, MotorType.kBrushless);
  private CANSparkMax l2SparkMax = new CANSparkMax(Constants.CANSparkMaxID.kLeft2.id, MotorType.kBrushless);
  private CANSparkMax r1SparkMax = new CANSparkMax(Constants.CANSparkMaxID.kRight1.id, MotorType.kBrushless);
  private CANSparkMax r2SparkMax = new CANSparkMax(Constants.CANSparkMaxID.kRight2.id, MotorType.kBrushless);

  private SpeedControllerGroup leftMotors = new SpeedControllerGroup(l1SparkMax, l2SparkMax);
  private SpeedControllerGroup rightMotors = new SpeedControllerGroup(r1SparkMax, r2SparkMax);

  private DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

  /**
   * Creates a new ExampleSubsystem.
   */
  public DriveTrain() {
    l1SparkMax.setIdleMode(IdleMode.kBrake);
    l2SparkMax.setIdleMode(IdleMode.kBrake);
    r1SparkMax.setIdleMode(IdleMode.kBrake);
    r2SparkMax.setIdleMode(IdleMode.kBrake);
    }
  
  

  @Override
  public void periodic() {
    
    // This method will be called once per scheduler run
  }
  public void tankDrive(double l, double r)
  {
    drive.tankDrive(l, r ,true);
  }
}
