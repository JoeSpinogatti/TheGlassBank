package control.client;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;
import java.util.Map;

import control.data.BasicAction;
import control.data.GlassAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
    public void testSendAction() throws JSONException
    {
        // data being sent
        String device = "lights";
        String cmdKey = "cue";
        String cmdValue = "go";

        Map<String, String> cmds = new HashMap<String, String>();
        cmds.put(cmdKey, cmdValue);
        GlassAction action = new BasicAction(device, cmds);

        // send it
        glassClient.sendAction(action);

        // confirm we called the HTTP client
        verify(mockedHttpClient).performHttpPost(any(JSONObject.class));
    }
}
