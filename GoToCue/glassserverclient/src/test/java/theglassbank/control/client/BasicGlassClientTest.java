package theglassbank.control.client;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;
import java.util.Map;

import theglassbank.control.data.BasicAction;
import theglassbank.control.data.GlassAction;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests the {@code BasicGlassClient}.
 */
public class BasicGlassClientTest
{
    /** Mocked HTTP client. */
    private final WrappedHttpClient mockedHttpClient =
            mock(WrappedHttpClient.class);

    /** The implementation being tested. */
    private final GlassClient glassClient =
            new BasicGlassClient(mockedHttpClient);

    /**
     * Trying to create a client with a {@code null} URL should cause an
     * exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullUrl()
    {
        String url = null;
        new BasicGlassClient(url);
    }

    /**
     * Trying to create a client with a {@code null} HTTP client should cause an
     * exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullClient()
    {
        WrappedHttpClient wrappedHttpClient = null;
        new BasicGlassClient(wrappedHttpClient);
    }

    /**
     * Trying to sned a {@code null} action should cause an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullAction()
    {
        glassClient.sendAction(null);
    }

    /**
     * Test sending a valid action.
     */
    @Test
    public void testSendAction()
    {
        // data being sent
        String device = "lights";
        String cmdKey = "cue";
        String cmdValue = "go";

        Map<String, String> cmds = new HashMap<String, String>();
        cmds.put(cmdKey, cmdValue);
        GlassAction action = new BasicAction(device, cmds);

        // for verifying message sent to server
        ArgumentCaptor<String> argumentCaptor =
                ArgumentCaptor.forClass(String.class);

        // send it
        glassClient.sendAction(action);

        // confirm we called the HTTP client
        verify(mockedHttpClient).performHttpPost(argumentCaptor.capture());
        String sentString = argumentCaptor.getValue();

        // check for key values in JSON string
        assertTrue(sentString.contains(device));
        assertTrue(sentString.contains(cmdKey));
        assertTrue(sentString.contains(cmdValue));
    }
}
