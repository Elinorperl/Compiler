package oop.ex6.codeelements;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class to define the scopes within the code.
 */
public class Scope {


    private HashMap<String,Variable> variables = new HashMap<>();
    private Scope outerScope;
    private boolean isMethodScope;
    private ArrayList<Scope> innerScopes;
    private boolean isRoot;
    private static Scope root;

    /**
     * A class that finds the scope of a variable.
     * @param variableName  - the variable for the scope we're searching
     * @param scope our current scope
     * @return returns the scope in which the variable appears
     */
    public static Scope searchScopeOfVariable (String variableName, Scope scope)
    {
        while (scope !=null)
        {
            if (scope.variables.containsKey(variableName))
                return scope;
            scope = scope.outerScope;
        }
        return null;
    }

    /**
     * Resets the root.
     */
    public static void  resetRoot()
    {
        root = null;
    }

    /**
     * A getter function - gets our scope root.
     * @return returns our scope root
     */
    public static Scope getRoot()
    {
        return root;
    }

    /**
     * The scope constructor.
     * @param outerScope the outer scope - the "father" of our current scope
     * @param methodScope a boolean, indicating if the method is a scope, or not.
     * */
    public Scope(Scope outerScope, boolean methodScope)
    {
        this.outerScope = outerScope;
        this.isMethodScope = methodScope;
        this.innerScopes = new ArrayList<>();
        if (outerScope!= null) {
            isRoot = false;
            outerScope.innerScopes.add(this);
        }
        else {
            isRoot = true;
            root = this;
        }
    }

    /**
     * Gets our variable according to its value name
     * @param variableName the name of the variable we're getting.
     * @return returns the variable we're looking for.
     */
    public Variable getVariable(String variableName)
    {
        return variables.get(variableName);
    }

    /**
     * @return outer scope of current scope
     */
    public Scope getOuterScope()
    {
        return this.outerScope;
    }

    /**
     * Adds a variable to our dictionary of variables.
     * @param variable the variable we'd like to add.
     */
    public void addVariable(Variable variable) throws DuplicateVariableException
    {
        if (variables.containsKey(variable.getName()))
            throw new DuplicateVariableException();
        variables.put(variable.getName(),variable);
    }

    /**
     * @return returns true  if method is scope, false otherwise.
     */
    public boolean isMethodScope() {
        return isMethodScope;
    }
}
