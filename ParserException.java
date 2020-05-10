package oop.ex6.parser;

/**
 * An abstract exception class that inherits exceptions within parser
 * Gives the exceptions a "cleaner" look.
 */
public abstract class ParserException extends Exception {

    /**
     * The exception constructor
     */
    public ParserException() {}

    /**
     * @return returns a message the describes the error.
     */
    @Override
    public abstract String getMessage();
}
