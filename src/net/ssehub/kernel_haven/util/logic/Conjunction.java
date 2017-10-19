package net.ssehub.kernel_haven.util.logic;


/**
 * A boolean conjunction operator (AND).
 * 
 * @author Adam (from KernelMiner project)
 * @author Sascha El-Sharkawy
 */
public final class Conjunction extends Formula {

    private static final long serialVersionUID = 8595985320940207982L;

    private Formula left;
    
    private Formula right;
    
    /**
     * Creates a boolean conjunction (AND).
     * 
     * @param left The left operand.
     * @param right The right operand.
     */
    public Conjunction(Formula left, Formula right) {
        this.left = left;
        this.right = right;
    }
    
    /**
     * Returns the formula that is nested on the left side of this conjunction.
     * 
     * @return The left operand.
     */
    public Formula getLeft() {
        return left;
    }
    
    /**
     * Returns the formula that is nested on the right side of this conjunction.
     * 
     * @return The right operand.
     */
    public Formula getRight() {
        return right;
    }
    
    @Override
    public boolean evaluate() {
        return left.evaluate() && right.evaluate();
    }

    @Override
    public String toString() {
        String leftStr = left.toString();
        if (!(left instanceof Conjunction) && left.getPrecedence() <= this.getPrecedence()) {
            leftStr = '(' + leftStr + ')';
        }
        
        String rightStr = right.toString();
        if (!(right instanceof Conjunction) && right.getPrecedence() <= this.getPrecedence()) {
            rightStr = '(' + rightStr + ')';
        }
        
        return leftStr + " && " + rightStr;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Conjunction) {
            Conjunction other = (Conjunction) obj;
            return left.equals(other.getLeft()) && right.equals(other.getRight());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return (left.hashCode() + right.hashCode()) * 4564;
    }

    @Override
    public int getLiteralSize() {
        return left.getLiteralSize() + right.getLiteralSize();
    }
    
    @Override
    protected <T> T accept(IFormulaVisitor<T> visitor) {
        return visitor.visitConjunction(this);
    }
    
    @Override
    protected void accept(IVoidFormulaVisitor visitor) {
        visitor.visitConjunction(this);
    }
    
    @Override
    protected int getPrecedence() {
        return 1;
    }
    
}
