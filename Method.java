package oop.ex6.codeelements;

import oop.ex6.parser.InvalidMethodCallException;
import oop.ex6.parser.InvalidMethodException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that represents a method.
 */
public class Method {
    private String name;
    private ArrayList<VariableType> parameterVariableTypes;
    private static HashMap<String, Method> allMethods = new HashMap<>();
    private String METHODNAME = "[a-zA-Z]{1}\\w{0,}";

    /**
     * @param name method name for which we're searching.
     * @return the method name if it exists in our hash map of all methods.
     * @throws InvalidMethodCallException is thrown when the method we're searching for doesn't exist in our
     * hash map.
     */
    public static Method getMethod(String name) throws InvalidMethodCallException {
        if (!allMethods.containsKey(name))
            throw new InvalidMethodCallException();
        return allMethods.get(name);
    }

    /**
     * Resets the method list.
     */
    public static void resetAllMethods()
    {
        allMethods = new HashMap<>();
    }

    /**
     * A constructor for a program method.
     * @param name method name (it's "signature")
     * @param parameterVariableTypes an Array of the parameter types.
     * @throws InvalidMethodException thrown when the method was called in our scope.
     */
    public Method(String name, ArrayList<VariableType> parameterVariableTypes) throws InvalidMethodException {
        this.name = name;
        verifyMethodName();
        this.parameterVariableTypes = parameterVariableTypes;
        allMethods.put(this.name, this);
    }

    /**
     * Verifies that the method name is valid.
     * @throws InvalidMethodException is thrown if the method name is invalid.
     */
    private void verifyMethodName() throws InvalidMethodException
    {
        Pattern pattern = Pattern.compile(METHODNAME);
        Matcher matcher = pattern.matcher(this.name);
        if (!matcher.matches())
            throw new InvalidMethodException();
    }

    /**
     * @return A getter function - returns the variable types.
     */
    public ArrayList<VariableType> getParameterVariableTypes()
    {
        return this.parameterVariableTypes;
    }




}
