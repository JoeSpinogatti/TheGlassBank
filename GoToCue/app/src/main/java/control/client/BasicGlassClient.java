package control.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import control.ArgumentUtils;
import control.data.GlassAction;


/**
 * The {@code BasicGlassClient} provides a basic implementation of a
 * {@code GlassClient}. All commands are sent to the server using HTTP posts.
 */
public class BasicGlassClient implements GlassClient
{
    /** For sending commands to the server. */
    private final WrappedHttpClient httpClient;

    /**
     * Creates a {@code BasicGlassClient} with the given URL.
     *
     * @param url the server's URL.
     * @throws IllegalArgumentException if {@code url} is {@code null} or
     *                                  empty.
     */
    public BasicGlassClient(String url)
    {
        this(new WrappedHttpClient(url));
    }

    /**
     * Creates a new {@code BasicGlassClient}. The client will use the given
     * {@code httpClient} to communicate to the server.
     *
     * @param httpClient the client.
     * @throws IllegalArgumentException if {@code httpClient} is {@code null}.
     */
    protected BasicGlassClient(WrappedHttpClient httpClient)
    {
        this.httpClient = ArgumentUtils.checkNotNull(httpClient, "httpClient");
    }

    @Override
    public void sendAction(GlassAction action)
    {
        ArgumentUtils.checkNotNull(action, "action");

        JSONObject json = new JSONObject();
        try
        {
            json.put("device", action.getDevice());
            Map<String, String> cmds = action.getCommands();
            for(Map.Entry<String, String> entry : cmds.entrySet())
            {
                json.put(entry.getKey(), entry.getValue());
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        // convert the POJO into a JSON string and send it to the server
        httpClient.performHttpPost(json);

    }
}
