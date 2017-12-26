package theglassbank.control.client;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code Driver} uses the glass client to send commands to the server via
 * the command line. This class is test utility and is not intended to be used
 * in production.
 */
public class Driver
{
    /** For reading input from the command line. */
    private final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public void performHttpPost(JSONObject jsonObject)
    {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://127.0.0.1:1337");

        StringEntity input = null;
        try
        {
            input = new StringEntity(jsonObject.toString());
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return;
        }
        input.setContentType("application/json");
        post.setEntity(input);


        /*
        post.addHeader("accept", "application/json");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        System.out.println("Sending " +jsonObject.toString());
        pairs.add(new BasicNameValuePair("commands", jsonObject.toString()));
        */
        //post.setEntity(new UrlEncodedFormEntity(pairs));
        post.setEntity(input);

        try
        {
            client.execute(post);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(e);
        }
        client.getConnectionManager().shutdown();
    }

    /**
     * Starts the driver. The user will be prompted for input which will then
     * be forwarded to the server.
     *
     * @throws IOException if there is an error reading the input from the
     *                     command line.
     */
    public void startDriver() throws IOException
    {
        // get the URL of the server we are talking to.
        //System.out.println("Enter the URL of the Glass Server: ");
        //String url = reader.readLine();

        // keep asking for information to create objects
        while(true)
        {
            System.out.println("-----------------------------------");

            // the name of the device being controlled by the server
            // (ION, Sound Console, etc)
            //System.out.print("Enter the device: ");
            //String device = reader.readLine();
            //System.out.println();

            // get all off the commands that should be sent.
            boolean run = true;
            JSONObject jsonObject = new JSONObject();
            while(run)
            {
                System.out.print("Enter in the key for a command (e.g. " +
                        "\"Cue\") or hit enter to stop adding commands: ");
                String key = reader.readLine();
                System.out.println();

                if(key.isEmpty())
                {
                    break;
                }

                System.out.print("Enter the value for the command (e.g. \"Next\"): ");
                String value = reader.readLine();
                System.out.println();
                try
                {
                    jsonObject.put(key, value);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            // create the object and send it
            performHttpPost(jsonObject);
        }
    }

    /**
     * The drivers main method.
     * @param args the argument is not used by the server.
     */
    public static void main(String[] args)
    {
        Driver driver = new Driver();
        try
        {
            driver.startDriver();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
