package oop.ex6.parser;


import oop.ex6.codeelements.*;
import java.util.ArrayList;

/**
 * An assignment call object.
 */
public class AssignmentCall  {

    /**
     * verifies the assignment calls that commenced.
     * @throws InvalidTypeException is thrown for an invalid type.
     * @throws UndeclaredVariableException is thrown for an undeclared variable.
     * @throws IncompatibleValueTypeException is thrown for an unmatched value and type.
     */
    static void verifyAll() throws InvalidTypeException, UndeclaredVariableException, IncompatibleValueTypeException
    {
        for (AssignmentCall item: listAllUnchecked)
        {
            if (item.assigner==null) {
                String variableName = item.assignerName;
                Scope scope = Scope.searchScopeOfVariable(variableName, item.scope);
                if (scope == null)
                    throw new UndeclaredVariableException();
                else
                    item.assigner = scope.getVariable(variableName);
            }
            VariableType typeAssigned =  item.assigned.getVariableType();
            if(item.assigner.getValue()==null)
                throw new UndeclaredVariableException();
            if (!typeAssigned.checkValue(item.assigner.getValue()))
                throw new InvalidTypeException();
        }

    }


    private static ArrayList<AssignmentCall> listAllUnchecked = new ArrayList<>();

    Variable assigner;
    String assignerName;
    Variable assigned;
    Scope scope;

    /**
     * Assignment call constructor
     * @param assigner the variable assigning the assignment
     * @param assigned the variable assigned to the assignment
     * @param assignerName the name of the assigner
     * @param scope the scope for which the assignment call is found
     */
    public AssignmentCall(Variable assigner, Variable assigned, String assignerName, Scope scope)
    {
        this.assigner = assigner;
        this.assigned = assigned;
        this.assignerName = assignerName;
        this.scope = scope;
        listAllUnchecked.add(this);
    }

    /**
     * resets the list of all assignment calls
     */
    public static void resetAll()
    {
        listAllUnchecked =  new ArrayList<>();
    }


}
