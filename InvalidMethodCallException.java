package oop.ex6.parser;

/**
 * An exception class for when method call is invalid
 */
public class InvalidMethodCallException extends ParserException {

    String MESSAGE = "Invalid method call";

    /**
     * The exception constructor
     */
    public InvalidMethodCallException() {}

    /**
     * @return the message that describes the exception error
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

