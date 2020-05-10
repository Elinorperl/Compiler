package oop.ex6.parser;

/**
 * An exception class representing invalid syntax errors.
 */
public class InvalidSyntaxException extends ParserException {

    String MESSAGE = "Invalid syntax";

    /**
     * The exception constructor
     */
    public InvalidSyntaxException() {}

    /**
     * @return returns a message the describes the error.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}



