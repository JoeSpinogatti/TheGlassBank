package theglassbank.control;

import org.junit.Test;

import static org.junit.Assert.assertSame;

/**
 * Tests the {@code ArgumentUtils} class.
 */
public class ArgumentUtilsTest
{
    /**
     * A {@code null} value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNotNullWithNull()
    {
        ArgumentUtils.checkNotNull(null, "name");
    }

    /**
     * A non-{@code null} value should not throw an exception.
     */
    @Test
    public void testCheckNotNullNonNull()
    {
        Object obj = new Object();
        Object returnedObj = ArgumentUtils.checkNotNull(obj, "name");
        assertSame(returnedObj, obj);
    }

    /**
     * A {@code null} value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNotEmptyNull()
    {
        ArgumentUtils.checkNotEmpty(null, "name");
    }

    /**
     * An empty value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNotEmptyWithEmpty()
    {
        ArgumentUtils.checkNotEmpty("   ", "name");
    }

    /**
     * A valid value should not throw an exception.
     */
    @Test
    public void testCheckNotEmptyNonEmpty()
    {
        String test = "test";
        String returnedValue = ArgumentUtils.checkNotEmpty(test, "name");
        assertSame(test, returnedValue);
    }
}
