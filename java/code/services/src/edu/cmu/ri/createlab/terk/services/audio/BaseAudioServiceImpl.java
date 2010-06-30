package edu.cmu.ri.createlab.terk.services.audio;

import edu.cmu.ri.createlab.terk.properties.PropertyManager;
import edu.cmu.ri.createlab.terk.properties.PropertyManagerWrapper;

public abstract class BaseAudioServiceImpl extends PropertyManagerWrapper implements AudioService
   {
   public BaseAudioServiceImpl(final PropertyManager propertyManager)
      {
      super(propertyManager);
      }

   public final String getTypeId()
      {
      return TYPE_ID;
      }
   }