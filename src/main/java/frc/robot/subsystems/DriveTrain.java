
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class DriveTrain extends SubsystemBase {

  /**
   * s Creates all the Talon objects
   */
  // private Talon l1Talon = new Talon(Constants.TalonID.kLeft1.id);
  // private Talon l2Talon = new Talon(Constants.TalonID.kLeft2.id);
  // private Talon r1Talon = new Talon(Constants.TalonID.kRight1.id);
  // private Talon r2Talon = new Talon(Constants.TalonID.kRight2.id);
  /**
   * Creates the IDs/DriveTrain for the NEO Brushless motors
   */
  public WPI_TalonFX l1TalonFX = new WPI_TalonFX(Constants.TalonID.kLeft1.id);
  public WPI_TalonFX r1TalonFX = new WPI_TalonFX(Constants.TalonID.kRight1.id);
  public WPI_TalonFX l2TalonFX = new WPI_TalonFX(Constants.TalonID.kLeft2.id);
  public WPI_TalonFX r2TalonFX = new WPI_TalonFX(Constants.TalonID.kRight2.id);

  /**
   * Creates a new Speed Controller Group for the two left,and two right talon
   * motors
   */
  private SpeedControllerGroup leftMotors = new SpeedControllerGroup(l1TalonFX, l2TalonFX);
  private SpeedControllerGroup rightMotors = new SpeedControllerGroup(r1TalonFX, r2TalonFX);
  /**
   * Combines the SpeedControllerGroup to create a differential drive.
   */
  public DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

  /**
   * Constructor for the DriveTrain
   */
  public DriveTrain() {
    l1TalonFX.setSafetyEnabled(false);
    l2TalonFX.setSafetyEnabled(false);
    r1TalonFX.setSafetyEnabled(false);
    r2TalonFX.setSafetyEnabled(false);

    // l1TalonFX.configOpenloopRamp(.75);
    // l2TalonFX.configOpenloopRamp(.75);
    // r1TalonFX.configOpenloopRamp(.75);
    // r2TalonFX.configOpenloopRamp(.75);
    // l1SparkMax.setIdleMode(IdleMode.kBrake);
    // l2SparkMax.setIdleMode(IdleMode.kBrake);
    // r1SparkMax.setIdleMode(IdleMode.kBrake);
    // r2SparkMax.setIdleMode(IdleMode.kBrake);
    // l1SparkMax.TalonFXControlMode(2);

  }

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  /**
   * Constructor for the tankDrive method
   */

  public void resetEncoderValue() {
    l1TalonFX.setSelectedSensorPosition(0);
  }

  public double getVelocity(boolean isRight) {
    if (isRight) {
      return r1TalonFX.getSelectedSensorVelocity() * 10;
    } else {
      return l1TalonFX.getSelectedSensorVelocity() * 10;
    }
  }

  public double getEncoderValue() {
    // l1TalonFX.set(mode, value);
    return l1TalonFX.getSelectedSensorPosition();
  }

  public double getVelocityPID() {
    double Velocity;
    if (RobotContainer.driverA.get() == true) {
      Velocity = 10;
    } else
      Velocity = 0;
    return Velocity;
  }

  public double CalcFPS() {
    double output = ((getVelocity(true) + getVelocity(false) * -1) / 2);
    double outputFPS = ((output) / 1179.35 / 12);
    return outputFPS;
  }

  public void tankDrive(double l, double r) {
    // System.out.println((l + r) / 2);
    SmartDashboard.putNumber("VelocityValue", CalcFPS());
    drive.tankDrive(r, l, true);
  }

  public void calibrateHoodEncoder() {
    // if (forwardLimitSwitch.get()) // If the forward limit switch is pressed, then we
    // want to keep the values between -1 and 0
    // left1.set(-.1);
    // }
  }
}
