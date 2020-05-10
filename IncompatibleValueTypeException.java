package oop.ex6.codeelements;

/**
 * An exception class that is thrown when a value is incompatible
 * with the variable type.
 */
public class IncompatibleValueTypeException extends CodeElementsException {

    String MESSAGE = "Incompatible value and type";

    /**
     * The exception constructor
     */
    public IncompatibleValueTypeException() {}

    /**
     * @return the message that describes the type of error.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
