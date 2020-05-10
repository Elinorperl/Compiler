package oop.ex6.parser;

/**
 * An exception class to represent a bad condition statement.
 */
public class InvalidConditionException extends ParserException {
    String MESSAGE = "Condition is invalid";

    /**
     * The exception constructor
     */
    public InvalidConditionException() {}

    /**
     * @return the message that describes the type of error.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
