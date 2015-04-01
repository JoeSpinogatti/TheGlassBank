package control.data;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertSame;

/**
 * Tests the {@code BasicAction} class.
 */
public class BasicActionTest
{
    /**
     * A {@code null} device name will cause an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullDeviceName()
    {
        new BasicAction(null, new HashMap<String, String>());
    }

    /**
     * A {@code null} command map will cause an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullCommands()
    {
        new BasicAction("name", null);
    }

    /**
     * Test with valid arguments.
     */
    @Test
    public void testValidArguments()
    {
        String device = "device";
        Map<String, String> map = new HashMap<String, String>();

        GlassAction action = new BasicAction(device, map);

        assertSame(device, action.getDevice());
        assertSame(map, action.getCommands());
    }
}
