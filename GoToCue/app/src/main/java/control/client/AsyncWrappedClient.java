package control.client;

import android.os.StrictMode;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import control.ArgumentUtils;

/**
 * Created by creising on 4/1/15.
 */
public class AsyncWrappedClient
{
    /** The URL of the HTTP server the client is talking to. */
    private final String url;

    /**
     * Creates a new {@code WrappedHttpClient} for communicating to a HTTP
     * server.
     *
     * @param url the URL of the server.
     * @throws IllegalArgumentException if {@code url} is empty or null.
     */
    public AsyncWrappedClient(String url)
    {
        this.url = ArgumentUtils.checkNotEmpty(url, "url");
    }

    /**
     * Sends the given string in the body of a HTTP post to the server. This
     * action is done asynchronously. This method is non-blocking.
     *
     * @param jsonObject the value that will be added to the body of the post.
     *
     * @throws IllegalArgumentException if {@code body} is empty or
     *                                  {@code null}.
     */
    public void performHttpPost(final JSONObject jsonObject)
    {
        ArgumentUtils.checkNotNull(jsonObject, "jsonObject");

        new Thread(new Runnable()
        {
            public void run()
            {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);

                StringEntity input = null;
                try
                {
                    input = new StringEntity(jsonObject.toString());
                } catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                    return;
                }
                input.setContentType("application/json");
                post.setEntity(input);

                try
                {
                    client.execute(post);
                } catch (IOException e)
                {
                    throw new IllegalArgumentException(e);
                }
                client.getConnectionManager().shutdown();
            }
        });
    }
}
