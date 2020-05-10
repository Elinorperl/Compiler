package oop.ex6.parser;

/**
 * An exception class for an invalid method for any reason
 */
public class InvalidMethodException extends ParserException {

    String MESSAGE = "method is invalid";

    /**
     * An exception constructor
     */
    public InvalidMethodException() {}

    /**
     * @return returns a message the describes the error.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}


