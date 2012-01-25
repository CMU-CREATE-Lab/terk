package edu.cmu.ri.createlab.terk.services.motor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import edu.cmu.ri.createlab.terk.expression.XmlDevice;
import edu.cmu.ri.createlab.terk.expression.XmlOperation;
import edu.cmu.ri.createlab.terk.expression.XmlParameter;
import edu.cmu.ri.createlab.terk.properties.PropertyManager;
import edu.cmu.ri.createlab.terk.services.BaseDeviceControllingService;
import org.apache.log4j.Logger;

/**
 * @author Chris Bartley (bartley@cmu.edu)
 */
public abstract class BaseOpenLoopVelocityControllableMotorServiceImpl extends BaseDeviceControllingService implements OpenLoopVelocityControllableMotorService
   {
   private static final Logger LOG = Logger.getLogger(BaseOpenLoopVelocityControllableMotorServiceImpl.class);

   private final boolean[] maskAllOn;
   private final int[] allZeros;

   public BaseOpenLoopVelocityControllableMotorServiceImpl(final PropertyManager propertyManager, final int deviceCount)
      {
      super(propertyManager, deviceCount);

      // create and initialize the all-on mask array
      maskAllOn = new boolean[deviceCount];
      Arrays.fill(maskAllOn, true);

      // create and initialize the array used for zero velocities
      allZeros = new int[deviceCount];
      Arrays.fill(allZeros, 0);
      }

   public final String getTypeId()
      {
      return TYPE_ID;
      }

   public final boolean setVelocities(final int[] velocities)
      {
      return execute(maskAllOn, velocities);
      }

   public final boolean stop(final int... motorIds)
      {
      final boolean[] mask;
      if (motorIds == null || motorIds.length == 0)
         {
         mask = maskAllOn;
         }
      else
         {
         mask = new boolean[getDeviceCount()];
         Arrays.fill(mask, false);
         for (final int i : motorIds)
            {
            mask[i] = true;
            }
         }

      return execute(mask, allZeros);
      }

   public final Object executeOperation(final XmlOperation operation)
      {
      if (OPERATION_NAME_SET_VELOCITY.equalsIgnoreCase(operation.getName()))
         {
         return setVelocity(operation);
         }
      else
         {
         throw new UnsupportedOperationException();
         }
      }

   private boolean setVelocity(final XmlOperation o)
      {
      final Set<XmlDevice> devices = o.getDevices();
      final Map<Integer, Integer> data = new HashMap<Integer, Integer>(devices.size() * 2);

      for (final XmlDevice d : devices)
         {
         final Set<XmlParameter> params = d.getParameters();
         int velocity = 0;
         for (final XmlParameter p : params)
            {
            if (PARAMETER_NAME_VELOCITY.equalsIgnoreCase(p.getName()))
               {
               velocity = Integer.parseInt(p.getValue());
               }
            }
         data.put(d.getId(), velocity);
         }

      return setVelocities(data);
      }

   private boolean setVelocities(final Map<Integer, Integer> velocityData)
      {
      final Set<Map.Entry<Integer, Integer>> entries = velocityData.entrySet();

      final boolean[] mask = new boolean[getDeviceCount()];
      Arrays.fill(mask, false);

      final int[] velocities = new int[getDeviceCount()];
      Arrays.fill(velocities, 0);

      for (final Map.Entry<Integer, Integer> e : entries)
         {
         if (e.getKey() < getDeviceCount())
            {
            mask[e.getKey()] = true;
            velocities[e.getKey()] = e.getValue();
            if (LOG.isDebugEnabled())
               {
               LOG.debug("Setting open-loop velocity-controllable motor device " + e.getKey() + " to " + e.getValue());
               }
            }
         }

      return execute(mask, velocities);
      }

   protected abstract boolean execute(final boolean[] mask, final int[] velocities);
   }