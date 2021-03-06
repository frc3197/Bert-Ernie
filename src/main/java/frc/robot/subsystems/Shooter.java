/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Defines a Shooter object. Code inside creates a variable for the Shooter
 * motor.
 */
public class Shooter extends PIDSubsystem {

  private WPI_TalonFX TalonShooter1 = new WPI_TalonFX(Constants.TalonID.kShooter1.id);
  public SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(.5, .12, 0.026);
  /**
   * Creates a new Shooter. Code inside configures encoder, minimum and maximum outputs, and PID Loop.
   */
  public Shooter() {
    super(new PIDController(4.44, .01,.2));
    TalonShooter1.setInverted(true);
    TalonShooter1.setSafetyEnabled(false);

    TalonShooter1.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx,
        Constants.kTimeoutMs);
    TalonShooter1.configNominalOutputForward(0, Constants.kTimeoutMs);
    TalonShooter1.configNominalOutputReverse(0, Constants.kTimeoutMs);
    
    TalonShooter1.configPeakOutputForward(1, Constants.kTimeoutMs);
    TalonShooter1.configPeakOutputReverse(-1, Constants.kTimeoutMs);

  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return 0;
  }
  /**
   * This method will be called once per scheduler run
   */
  @Override
  public void periodic() {
  
  }

  /**
   * Runs Shooter motor based on RPM
   * @param val RPM value
   */
  public void shooterVelocity(double val) {
    TalonShooter1.set(ControlMode.Velocity, val);
  }

  public double shooterVelocityRPS(){
    double units = TalonShooter1.getSelectedSensorVelocity();
    return ((units / 2048) * 10);
  }
  /**
   * Runs Shooter motor based on speed value
   * @param val Speed value
   */
  public void setShooter(double val) {
    TalonShooter1.set(val);
  }

  public boolean getMotor(){
    double val = TalonShooter1.get();
    if(val>.2) 
    return true;
    else return false;
  }
  
  public void feedForwardPID(double source ,double RPM) {
    double setpoint = (source * (feedforward.calculate(RPM / 60)
    + getController().calculate(shooterVelocityRPS(), (RPM/60))));

  TalonShooter1.setVoltage(setpoint);

   SmartDashboard.putNumber("Shooter Voltage",getVelocity() );
  // CHANGE GETSELECTEDSENSORVELOCITYTODISTANCEUNIT
  // THIS COULD BE AN OPTION FOR FEEDFORWARD WITH PID BUT SHOULD LOOK INTO FLYWHEEL CALCULATION
  // FLYWHEEL CALCULATION: (VOLTAGE / RPM) * SETPOINTRPM
                       }
  /**
   * Pulls Limelight's X Offset value and prints it.
   * @return Limelight's X Offset value
   */
  public double getXOffset() {
    // System.out.println(NetworkTableInstance.getDefault().getTable("limelight-hounds").getEntry("tx").getDouble(0));
    return NetworkTableInstance.getDefault().getTable("limelight-hounds").getEntry("tx").getDouble(0);
  }

  /**
   * Pulls Limelight's Y Offset value.
   * @return Limelight's Y-Offset value
   */
  public double getYOffset() {
    return NetworkTableInstance.getDefault().getTable("limelight-hounds").getEntry("ty").getDouble(0);
  }

  /**
   * Pulls encoder value in ticks.
   * @return Encoder value in ticks
   */
  public double getEncoderValue() {
    return TalonShooter1.getSelectedSensorPosition();
  }

  public double getVelocity(){
    double units = TalonShooter1.getSelectedSensorVelocity();
    return ((units/2048) * 600);
  }
  /**
   * Resets the encoder position to zero.
   */
  public void resetEncoder() {
    TalonShooter1.setSelectedSensorPosition(0);
  }


}
