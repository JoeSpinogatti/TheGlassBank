package theglassbank.control.data;

import java.util.Map;

import theglassbank.control.ArgumentUtils;

/**
 * A basic implementation of the {@code GlassAction} interface.
 */
public class BasicAction implements GlassAction
{
    /** The name of the device the action is intended for. */
    private final String device;

    /** Map of device commands. */
    private final Map<String, String> commands;

    /**
     * Crates a {@code BasicAction}.
     *
     * @param device the name of the device being controlled.
     * @param commands the map of commands.
     * @throws IllegalArgumentException if {@code device} is empty or if any
     *                                  argument is {@code null}.
     */
    public BasicAction(String device, Map<String, String> commands)
    {
        this.device = ArgumentUtils.checkNotEmpty(device, "device");
        this.commands = ArgumentUtils.checkNotNull(commands, "commands");
    }

    @Override
    public String getDevice()
    {
        return device;
    }

    @Override
    public Map<String, String> getCommands()
    {
        return commands;
    }

    @Override
    public String toString()
    {
        return "SimpleAction{" +
                "device='" + device + '\'' +
                ", commands=" + commands +
                '}';
    }
}