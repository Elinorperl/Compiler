package oop.ex6.codeelements;

/**
 * One of the main exceptions in order to make a "cleaner" look
 * for all the exceptions. Acts as the "father" exception for
 * the exceptions in our codeelement package (and therefore is abstract)
 */
public abstract class CodeElementsException  extends Exception {

    /**
     * The exception constructor.
     */
    public CodeElementsException() {}

    /**
     * @return the message that describes the exception
     */
    @Override
    public abstract String getMessage();
}


