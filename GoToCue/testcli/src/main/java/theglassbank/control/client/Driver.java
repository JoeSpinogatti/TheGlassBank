package theglassbank.control.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import control.client.BasicGlassClient;
import control.client.GlassClient;
import control.data.BasicAction;
import control.data.GlassAction;


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
        System.out.println("Enter the URL of the Glass Server: ");
        String url = reader.readLine();

        // create the server
        GlassClient server = new BasicGlassClient(url);

        // keep asking for information to create objects
        while(true)
        {
            System.out.println("-----------------------------------");

            // the name of the device being controlled by the server
            // (ION, Sound Console, etc)
            System.out.print("Enter the device: ");
            String device = reader.readLine();
            System.out.println();

            // get all off the commands that should be sent.
            Map<String, String> map = new HashMap<String, String>();
            boolean run = true;
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
                map.put(key, value);
            }

            // create the object and send it
            GlassAction action = new BasicAction(device, map);
            System.out.println("Sending the following: " + action);
            server.sendAction(action);
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
