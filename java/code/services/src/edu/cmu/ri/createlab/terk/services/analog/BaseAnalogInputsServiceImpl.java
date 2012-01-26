package edu.cmu.ri.createlab.terk.services.analog;

import java.util.Set;
import edu.cmu.ri.createlab.terk.expression.XmlDevice;
import edu.cmu.ri.createlab.terk.expression.XmlOperation;
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

   public final Object executeOperation(final XmlOperation operation)
      {
      if (OPERATION_NAME_GET_ANALOG_INPUT_VALUE.equalsIgnoreCase(operation.getName()))
         {
         // TODO: For now, just assume there's only one device...
         final Set<XmlDevice> devices = operation.getDevices();
         if (devices != null && !devices.isEmpty())
            {
            final XmlDevice device = devices.iterator().next();
            if (device != null)
               {
               return getAnalogInputValue(device.getId());
               }
            }
         }
      else
         {
         throw new UnsupportedOperationException();
         }
      return null;
      }
   }
