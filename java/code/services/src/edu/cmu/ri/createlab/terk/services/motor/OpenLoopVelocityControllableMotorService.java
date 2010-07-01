package edu.cmu.ri.createlab.terk.services.motor;

import edu.cmu.ri.createlab.terk.services.DeviceController;
import edu.cmu.ri.createlab.terk.services.Service;

/**
 * @author Chris Bartley (bartley@cmu.edu)
 */
public interface OpenLoopVelocityControllableMotorService extends Service, DeviceController
   {
   String TYPE_ID = "::TeRK::motor::OpenLoopVelocityControllableMotorService";

   String PROPERTY_NAME_MOTOR_DEVICE_ID = TYPE_ID + "::motor-device-id";

   String PROPERTY_NAME_MIN_VELOCITY = TYPE_ID + "::min-velocity";
   String PROPERTY_NAME_MAX_VELOCITY = TYPE_ID + "::max-velocity";

   /**
    * Sets the motor velocities to the given <code>velocities</code> (in native units).  Returns <code>true</code> upon
    * success; <code>false</code> otherwise. 
    */
   boolean setVelocities(final int[] velocities);
   }