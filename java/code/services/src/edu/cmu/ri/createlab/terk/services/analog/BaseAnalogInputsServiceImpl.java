package edu.cmu.ri.createlab.terk.services.analog;

import edu.cmu.ri.createlab.terk.properties.PropertyManager;
import edu.cmu.ri.createlab.terk.services.BaseDeviceControllingService;

/**
 * @author Chris Bartley (bartley@cmu.edu)
 */
public abstract class BaseAnalogInputsServiceImpl extends BaseDeviceControllingService implements AnalogInputsService
   {
   public BaseAnalogInputsServiceImpl(final PropertyManager propertyManager, final int deviceCount)
      {
      super(propertyManager, deviceCount);
      }

   public final String getTypeId()
      {
      return AnalogInputsService.TYPE_ID;
      }
   }
