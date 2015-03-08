package theglassbank.control.data;

import java.util.Map;

/**
 * Contains details specific to controlling a device.
 */
public interface GlassAction
{
    /**
     * The name of the device being controlled (e.g., "Ion").
     *
     * @return Never {@code null}.
     */
    public String getDevice();

    /**
     * A map of commands that intended for the device. The key should be the
     * device's attribute name, and the value is the value of the attribute
     * itself (e.g., key="Cue" value="Next").
     *
     * @return Never {@code null}.
     */
    public Map<String, String> getCommands();
}
