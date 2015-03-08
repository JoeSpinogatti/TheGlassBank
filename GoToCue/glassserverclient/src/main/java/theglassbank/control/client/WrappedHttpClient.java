package theglassbank.control.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.util.concurrent.Future;

import theglassbank.control.ArgumentUtils;

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
     * @param body the value that will be added to the body of the post.
     *
     * @return a reference to the {@code Future} associated with the
     *         asynchronous request.
     * @throws IllegalArgumentException if {@code body} is empty or
     *                                  {@code null}.
     */
    public Future<HttpResponse<JsonNode>> performHttpPost(String body)
    {
        ArgumentUtils.checkNotEmpty(body, "body");
        return Unirest.post(url)
            .header("accept", "application/json")
            .body(body)
            .asJsonAsync();
    }
}
