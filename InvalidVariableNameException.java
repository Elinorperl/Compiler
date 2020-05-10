package oop.ex6.codeelements;
/**
 * An exception class thrown to represent when a variable name is invalid.
 */
public class InvalidVariableNameException extends CodeElementsException {

    String MESSAGE = "Variable name is invalid";

    /**
     * The exception constructor.
     */
    public InvalidVariableNameException() {}

    /**
     * @return returns a message describing the type of error that occurred.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

