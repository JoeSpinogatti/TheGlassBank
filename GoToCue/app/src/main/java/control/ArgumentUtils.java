package control;

/**
 * Contains helper method for validating method arguments.
 */
public class ArgumentUtils
{
    /**
     * Checks to see if the given {@code object} is {@code null}.
     *
     * @param object the object to check.
     * @param name the name of the variable. The name will be included in the
     *             exception.
     * @param <T> the type being checked.
     * @return the original object.
     * @throws IllegalArgumentException if {@code object} is {@code null}.
     */
    public static <T> T checkNotNull(T object, String name)
    {
        if(object == null)
        {
            throw new IllegalArgumentException(name + " cannot be null");
        }
        return object;
    }

    /**
     * Checks to see if {@code value} is empty or {@code null}.
     *
     * @param value the value to check.
     * @param name the name of the variable. The name will be included in the
     *             exception.
     * @return the original value.
     * @throws IllegalArgumentException if {@code value} is empty or
     *                                  {@code null}.
     */
    public static String checkNotEmpty(String value, String name)
    {
        if(value == null || value.trim().isEmpty())
        {
            throw new IllegalArgumentException(name + "" +
                    "cannot be empty or null");
        }
        return value;
    }
}
