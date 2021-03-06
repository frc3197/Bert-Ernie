
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.AutoConstants;
import frc.robot.Constants;
import frc.robot.Robot;

/**
 * Defines a DriveTrain object. Code inside creates variables for drive motors
 * used.
 */
public class DriveTrain extends SubsystemBase  {
public WPI_TalonFX l1TalonFX = new WPI_TalonFX(Constants.TalonID.kLeft1.id);
  public WPI_TalonFX r1TalonFX = new WPI_TalonFX(Constants.TalonID.kRight1.id);
  public WPI_TalonFX l2TalonFX = new WPI_TalonFX(Constants.TalonID.kLeft2.id);
  public WPI_TalonFX r2TalonFX = new WPI_TalonFX(Constants.TalonID.kRight2.id);
 
  private SpeedControllerGroup leftMotors = new SpeedControllerGroup(l1TalonFX, l2TalonFX);
  private SpeedControllerGroup rightMotors = new SpeedControllerGroup(r1TalonFX, r2TalonFX);


  public DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);
  public RamseteController ramseteController = new RamseteController(AutoConstants.ramseteB, AutoConstants.ramseteZeta);

  public Command placeholder;
  public Rotation2d m_rotation2d;
  public Pose2d m_pose = new Pose2d();
  public DifferentialDriveOdometry m_odometry;
  // TRACK WIDTH MEASUERED DISTANCE WHEEL CENTER TO WHEEL CENTER IN METERS
  public DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(AutoConstants.trackWidth);
  
  // public ChassisSpeeds chassisSpeeds = ramseteController.calculate(m_pose, AutoConstants.poseRef, AutoConstants.linearVelocityRefMeters, AutoConstants.angularVelocityRefRadiansPerSecond);

  // public PIDController leftPID = new PIDController(AutoConstants.kP, AutoConstants.kI, AutoConstants.kD);
  // public PIDController rightPID = new PIDController(AutoConstants.kP, AutoConstants.kI, AutoConstants.kD);
  
  
   
  //static Gyro gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
  public static AHRS gyro = new AHRS(SerialPort.Port.kUSB1);
  /**
   * Constructor for the DriveTrain
   */
  public DriveTrain() {
    m_rotation2d = new Rotation2d(getHeadingRadians());
    
    r1TalonFX.setSafetyEnabled(false);
    r2TalonFX.setSafetyEnabled(false);

    // l1TalonFX.configOpenloopRamp(.1);
    // l2TalonFX.configOpenloopRamp(.1);
    // r1TalonFX.configOpenloopRamp(.1);
    // r2TalonFX.configOpenloopRamp(.1);
    l1TalonFX.setInverted(true);
    l2TalonFX.setInverted(true);
    r1TalonFX.setInverted(true);
    r2TalonFX.setInverted(true);

    // leftPID.setSetpoint(0);
    // rightPID.setSetpoint(0);
  }

  /**
   * This method will be called once per scheduler run
   */
  @Override
  public void periodic() {
    var gyroAngle = Rotation2d.fromDegrees(getHeading());
    m_odometry.update(gyroAngle,getDistanceMetersLeft(), getDistanceMetersRight());
    // m_pose = m_odometry.update(gyroAngle,getDistanceMetersLeft(), getDistanceMetersRight());
  }

  public void resetOdometry(Pose2d pose) {
    resetEncoder();
    m_odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  public void resetEncoder() {
    r1TalonFX.setSelectedSensorPosition(0);
r2TalonFX.setSelectedSensorPosition(0);
l1TalonFX.setSelectedSensorPosition(0);
    l2TalonFX.setSelectedSensorPosition(0);
  }

  /**
   * Resets the Gyro
   */
  public void resetGyro() {
    gyro.reset();
  }

  
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftEncoderVelocityMeters(), getRightEncoderVelocityMeters());
  }

  public double getLeftEncoderVelocityMeters(){
    double velocityU = l1TalonFX.getSelectedSensorVelocity();
    return ((velocityU / Constants.gear_ratio) / 2048) * Units.inchesToMeters(6 * Math.PI) * 10;
  }


  public double getRightEncoderVelocityMeters(){
    double velocityU = -r1TalonFX.getSelectedSensorVelocity();
    return ((velocityU / Constants.gear_ratio) / 2048) * Units.inchesToMeters(6 * Math.PI) * 10;
  }

  public void zeroHeading(){
    gyro.reset();
  }

  public Pose2d getPose(){
    
    return m_odometry.getPoseMeters();
  }

  public double getDistanceMetersLeft(){
    double encoder = l1TalonFX.getSelectedSensorPosition();
    double distance = ((encoder / Constants.gear_ratio) / 2048.0) * 0.478779 ; 
    return distance;
  }

  public double getDistanceMetersRight(){
    double encoder = -r1TalonFX.getSelectedSensorPosition();
    double distance = ((encoder / Constants.gear_ratio) / 2048.0) * 0.478779; 
    return distance;
  }

  public double getDistance(){
    double encoder = l1TalonFX.getSelectedSensorPosition();
    double distance = ((encoder / Constants.gear_ratio) / 2048.0) * (6 * Math.PI); 
    return distance;
  }
  /**
   * Calibrates the Gyro
   */

  public double getHeading(){
    return (getGyroAngle() % 360);
  }
  public double getHeadingRadians(){

    return Units.degreesToRadians(getHeading());
      }

  public void tankDrive(double l, double r) {
    // System.out.println((l + r) / 2);
    drive.tankDrive(r, l, true);
  }

  public void arcadeDrive(double speed, double rotation) {

    drive.arcadeDrive(speed, rotation);
  }

  /**
   * Pulls Gyro's detected angle
   * 
   * @return Detected angle
   */
  public double getGyroAngle() {
    return -gyro.getAngle();
  }

  public void tankDriveVolts(double leftVolts,double rightVolts){
    leftMotors.setVoltage(leftVolts);
    rightMotors.setVoltage(-rightVolts);
    drive.feed();

  }

  public void calibrateGyro() {
  }

  public void setPlaceholder(Command command) {
    placeholder = command;

    if(placeholder.getName().equals("SixBallRun")){
      m_pose.equals(new Pose2d(3.132, -.75, m_rotation2d));
    }
    else if(placeholder.getName().equals("EightBallRoute")){
      m_pose.equals(new Pose2d(3.155, -7.5, m_rotation2d));}
    else{m_pose.equals(new Pose2d());}

    m_odometry = new DifferentialDriveOdometry(m_rotation2d, m_pose);
  }
}
