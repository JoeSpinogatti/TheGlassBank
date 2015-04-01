package control.client;


import control.data.GlassAction;

/**
 * The {@code GlassClient} is responsible to sending commands from a client
 * application to a running instance of a Glass Bank Server. All commands
 * are sent asynchronously (non-blocking).
 */
public interface GlassClient
{
    /**
     * Sends the given action to the server.
     *
     * @param action the action to send.
     * @throws IllegalArgumentException if {@code action} is {@code null}.
     */
    public void sendAction(GlassAction action);
}
