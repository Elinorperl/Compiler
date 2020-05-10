package oop.ex6.codeelements;
/**
 * An exception class that is thrown when an undeclared variable has been given an assignment.
 */
public class UndeclaredVariableException extends CodeElementsException {

    String MESSAGE = "Undeclared variable assignment";

    /**
     * The exception constructor
     */
    public UndeclaredVariableException() {}

    /**
     * @return a message that describes the given error.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
