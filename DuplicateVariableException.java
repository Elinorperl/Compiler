package oop.ex6.codeelements;

/**
 * An exception class that extends one of our main exceptions.
 * This exception is raised when a duplicate variable is found inside a scope.
 */
public class DuplicateVariableException extends CodeElementsException {
    String MESSAGE = "Duplicate variable name inside a scope";

    /**
     * The exception constructor.
     */
    public DuplicateVariableException()
    {}

    /**
     * @return a message that occurs to indicate what type of error occurred.
     */
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

