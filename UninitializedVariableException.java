package oop.ex6.codeelements;

/**
 * An exception class that occurs when an uninitialized variable was assigned.
 */
public class UninitializedVariableException extends CodeElementsException {

    String MESSAGE = "Uninitialized variable assignment";

    /**
     * The exception constructor
     */
    public UninitializedVariableException() {}

    /**
     * @return a message that describes the given error.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
