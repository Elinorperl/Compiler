package oop.ex6.codeelements;
/**
 * An exception class when there is a problem with the scope.
 */
public class InvalidScopeException extends CodeElementsException {

    String MESSAGE = "Scope issue";

    /**
     * The exception constructor.
     */
    public InvalidScopeException() {}

    /**
     * @return a message describing the type of error.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

