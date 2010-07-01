package edu.cmu.ri.createlab.terk.services.motor;

import edu.cmu.ri.createlab.terk.properties.PropertyManager;
import edu.cmu.ri.createlab.terk.services.BaseDeviceControllingService;

/**
 * @author Chris Bartley (bartley@cmu.edu)
 */
public abstract class OpenLoopBaseVelocityControllableMotorServiceImpl extends BaseDeviceControllingService implements OpenLoopVelocityControllableMotorService
   {
   public OpenLoopBaseVelocityControllableMotorServiceImpl(final PropertyManager propertyManager, final int deviceCount)
      {
      super(propertyManager, deviceCount);
      }

   public final String getTypeId()
      {
      return TYPE_ID;
      }

   public abstract boolean setVelocities(final int[] velocities);
   }