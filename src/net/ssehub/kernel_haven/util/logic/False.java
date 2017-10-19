package net.ssehub.kernel_haven.util.logic;

/**
 * The boolean constant "false".
 * 
 * @author Adam (from KernelMiner project)
 * @author Sascha El-Sharkawy
 */
public final class False extends Formula {

    /**
     * Shared instance for this class.
     * Currently not a pure singleton to avoid refactoring of complete architecture.
     */
    public static final False INSTANCE = new False();
    
    private static final long serialVersionUID = 6422261057525028423L;

    /**
     * Should not longer used outside of this class, use {@link #INSTANCE} instead.
     */
    @Deprecated
    public False() {}
    
    @Override
    public boolean evaluate() {
        return false;
    }

    @Override
    public String toString() {
        return "0";
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof False;
    }
    
    @Override
    public int hashCode() {
        return 2343242;
    }

    @Override
    public int getLiteralSize() {
        return 0;
    }
    
    @Override
    protected <T> T accept(IFormulaVisitor<T> visitor) {
        return visitor.visitFalse(this);
    }
    
    @Override
    protected void accept(IVoidFormulaVisitor visitor) {
        visitor.visitFalse(this);
    }
    
    @Override
    protected int getPrecedence() {
        return 3;
    }
    
}
