package control.client;


import android.os.StrictMode;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import control.ArgumentUtils;

/**
 * Wraps the {@code Unirest} http service in an instantiable class for ease of
 * testing.
 */
public class WrappedHttpClient
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
    public WrappedHttpClient(String url)
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
    public void performHttpPost(JSONObject jsonObject)
    {
        ArgumentUtils.checkNotNull(jsonObject, "jsonObject");

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("commands", jsonObject.toString()));
        try
        {
            post.setEntity(new UrlEncodedFormEntity(pairs));
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalArgumentException(e);
        }
        try
        {
            client.execute(post);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}
