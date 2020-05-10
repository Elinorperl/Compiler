package oop.ex6.parser;

import oop.ex6.codeelements.*;

import java.util.ArrayList;

/**
 *
 */
public class MethodCall {
    String name;
    ArrayList<String> parameterVariableValues;
    Scope scope;
    static ArrayList<MethodCall> allMethodCalls = new ArrayList<>();

    /**
     * Resets all the method calls
     */
    static void resetAll()
    {
        allMethodCalls = new ArrayList<>();
    }

    /**
     * Verifies all the method calls
     * @throws InvalidMethodCallException throws an invalid method call
     * @throws IncompatibleValueTypeException throws for an unmatched value and type
     * @throws UndeclaredVariableException throws for undeclared variable
     * @throws UninitializedVariableException throws for an uninitialized variable.
     */
    static void verifyAll() throws InvalidMethodCallException, IncompatibleValueTypeException,
            UndeclaredVariableException, UninitializedVariableException {
        Method method;
        Variable variable;
        for (MethodCall call : allMethodCalls) {
            method = Method.getMethod(call.name);
            // iterate over all variables of a method call
            if (method.getParameterVariableTypes()!=null && call.parameterVariableValues !=null) {
                if (method.getParameterVariableTypes().size() != call.parameterVariableValues.size())
                    throw new InvalidMethodCallException();
                for (int i = 0; i < call.parameterVariableValues.size(); i++) {
                    if (!method.getParameterVariableTypes().get(i).checkValue(call.parameterVariableValues.get(i)))
                    {
                        variable = Variable.getVariable(call.parameterVariableValues.get(i),call.scope);
                        if (variable == null)
                            throw new UninitializedVariableException();
                        if (!method.getParameterVariableTypes().get(i).checkValue(variable.getValue()))
                            throw new IncompatibleValueTypeException();
                    }
                }
            }

        }
    }

    /**
     * Method call constructor
     * @param name name of method call
     * @param variableValues the variable values from the method call
     * @param scope the scope where the method lies
     * @throws UndeclaredVariableException throws for an undeclared variable.
     */
    MethodCall(String name, ArrayList<String> variableValues, Scope scope) throws UndeclaredVariableException {
        this.name = name;
        this.parameterVariableValues = variableValues;
        this.scope = scope;
        allMethodCalls.add(this);
    }

}
