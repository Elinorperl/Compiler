package oop.ex6.codeelements;
/**
 * An exception class for when a final assignment is being attempted to
 * be reassigned.
 */
public class FinalAssigmentException extends CodeElementsException {
    String MESSAGE = "Reassignment to a final variable occurred";

    /**
     * The exception constructor.
     */
    public FinalAssigmentException() {}

    /**
     * @return the message that describes the occurring error.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
