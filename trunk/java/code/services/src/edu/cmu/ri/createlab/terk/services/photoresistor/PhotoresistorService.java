package edu.cmu.ri.createlab.terk.services.photoresistor;

import edu.cmu.ri.createlab.terk.services.DeviceController;
import edu.cmu.ri.createlab.terk.services.Service;

/**
 * @author Chris Bartley (bartley@cmu.edu)
 */
public interface PhotoresistorService extends Service, DeviceController
   {
   String TYPE_ID = "::TeRK::photoresistor::PhotoresistorService";

   /**
    * Returns the value of the photoresistor specified by the given <code>id</code>.  Returns <code>null</code> if the
    * value could not be retrieved.
    */
   Integer getPhotoresistorValue(final int id);

   /**
    * Returns the value of each photoresistor.  Returns <code>null</code> if the values could not be retrieved.
    */
   int[] getPhotoresistorValues();
   }