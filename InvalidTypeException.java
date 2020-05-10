package oop.ex6.codeelements;
/**
 * An exception that deals invalid types, types that don't exist.
 */
public class InvalidTypeException extends CodeElementsException {

    String MESSAGE = "Variable type is invalid";

    /**
     * The exception constructor.
     */
    public InvalidTypeException() {}

    /**
     * @return returns a message describing the type of error that occurred.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
