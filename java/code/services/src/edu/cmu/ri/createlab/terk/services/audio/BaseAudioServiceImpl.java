package edu.cmu.ri.createlab.terk.services.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import edu.cmu.ri.createlab.terk.expression.XmlDevice;
import edu.cmu.ri.createlab.terk.expression.XmlOperation;
import edu.cmu.ri.createlab.terk.expression.XmlParameter;
import edu.cmu.ri.createlab.terk.properties.PropertyManager;
import edu.cmu.ri.createlab.terk.properties.PropertyManagerWrapper;
import org.apache.log4j.Logger;

public abstract class BaseAudioServiceImpl extends PropertyManagerWrapper implements AudioService
   {
   private static final Logger LOG = Logger.getLogger(BaseAudioServiceImpl.class);

   private final File audioDirectory;

   /**
    * Creates a BaseAudioServiceImpl with the given {@link PropertyManager} and uses the given
    * <code>audioDirectory</code> for the audio directory.
    */
   public BaseAudioServiceImpl(final PropertyManager propertyManager, final File audioDirectory)
      {
      super(propertyManager);
      this.audioDirectory = audioDirectory;
      }

   public final String getTypeId()
      {
      return TYPE_ID;
      }

   /**
    * Returns a device count of 1.  Subclasses cannot override this since there isn't support for specifying any other
    * device ID in the {@link AudioService} methods.
    */
   @Override
   public final int getDeviceCount()
      {
      return 1;
      }

   @Override
   public final Object executeOperation(final XmlOperation o)
      {
      if (o != null)
         {
         final Set<XmlDevice> devices = o.getDevices();
         if ((devices != null) && (!devices.isEmpty()))
            {
            final XmlDevice device = devices.iterator().next();

            if (OPERATION_NAME_PLAY_TONE.equalsIgnoreCase(o.getName()) ||
                OPERATION_NAME_PLAY_TONE_ASYNCHRONOUSLY.equalsIgnoreCase(o.getName()))
               {
               final Map<String, XmlParameter> parameterMap = device.getParametersAsMap();
               if ((parameterMap != null) && (!parameterMap.isEmpty()))
                  {
                  final XmlParameter amplitudeParam = parameterMap.get(PARAMETER_NAME_TONE_AMPLITUDE);
                  final XmlParameter durationParam = parameterMap.get(PARAMETER_NAME_TONE_DURATION);
                  final XmlParameter frequencyParam = parameterMap.get(PARAMETER_NAME_TONE_FREQUENCY);
                  if (amplitudeParam != null && durationParam != null && frequencyParam != null)
                     {
                     final Integer amplitude = amplitudeParam.getValueAsInteger();
                     final Integer duration = durationParam.getValueAsInteger();
                     final Integer frequency = frequencyParam.getValueAsInteger();

                     if (amplitude != null && duration != null && frequency != null)
                        {
                        if (OPERATION_NAME_PLAY_TONE_ASYNCHRONOUSLY.equalsIgnoreCase(o.getName()))
                           {
                           playToneAsynchronously(frequency, amplitude, duration, null);
                           }
                        else
                           {
                           playTone(frequency, amplitude, duration);
                           }
                        }
                     }
                  }
               }
            else if (OPERATION_NAME_PLAY_CLIP.equalsIgnoreCase(o.getName()) ||
                     OPERATION_NAME_PLAY_CLIP_ASYNCHRONOUSLY.equalsIgnoreCase(o.getName()))
               {
               final XmlParameter fileParameter = device.getParameter(PARAMETER_NAME_CLIP_FILE);
               if (fileParameter != null)
                  {
                  final String fileStr = fileParameter.getValue();
                  if ((fileStr != null) && (fileStr.length() > 0))
                     {
                     final File file = new File(audioDirectory, fileStr);
                     try
                        {
                        if (OPERATION_NAME_PLAY_CLIP_ASYNCHRONOUSLY.equalsIgnoreCase(o.getName()))
                           {
                           playSoundAsynchronously(getFileAsBytes(file), null);
                           }
                        else
                           {
                           playSound(getFileAsBytes(file));
                           }
                        }
                     catch (IOException e)
                        {
                        LOG.error("IOException while trying to read sound file [" + file + "]", e);
                        }
                     }
                  }
               }
            else
               {
               throw new UnsupportedOperationException();
               }
            }
         }
      return null;
      }

   // TODO: I stole this from FileUtils in CREATE Lab Commons Util, just because I was in a rush and didn't want to add
   // the dependancy right now.
   private static byte[] getFileAsBytes(final File file) throws IOException
      {
      if (file != null && file.exists() && file.isFile())
         {
         final InputStream is = new FileInputStream(file);

         // Get the size of the file
         final long length = file.length();

         // You cannot create an array using a long type. It needs to be an int type. Before converting to an int type,
         // check to ensure that file is not larger than Integer.MAX_VALUE.
         if (length > Integer.MAX_VALUE)
            {
            throw new IOException("File is too large to read " + file.getName());
            }

         // Create the byte array to hold the data
         final byte[] bytes = new byte[(int)length];

         // Read in the bytes
         int offset = 0;
         int numRead;
         while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
            {
            offset += numRead;
            }

         // Ensure all the bytes have been read in
         if (offset < bytes.length)
            {
            throw new IOException("Could not completely read file " + file.getName());
            }

         // Close the input stream and return bytes
         is.close();
         return bytes;
         }
      return null;
      }
   }