package net.ssehub.kernel_haven.code_model.ast;

import net.ssehub.kernel_haven.util.logic.Formula;
import net.ssehub.kernel_haven.util.null_checks.NonNull;

/**
 * Represents unparsed string of code inside the AST. See class comment of {@link ISyntaxElement}.
 * 
 * @author Adam
 */
public class Code extends AbstractSyntaxElementNoNesting implements ICode {

    private @NonNull String text;
    
    /**
     * Creates this {@link Code}.
     * 
     * @param presenceCondition The presence condition of this element.
     * @param text The unparsed code string.
     */
    public Code(@NonNull Formula presenceCondition, @NonNull String text) {
        super(presenceCondition);
        this.text = text;
    }
    
    /**
     * Returns the unparsed code string.
     * 
     * @return The unparsed code string.
     */
    public @NonNull String getText() {
        return text;
    }
    
    @Override
    public @NonNull String elementToString(@NonNull String indentation) {
        return text + "\n";
    }

    @Override
    public void accept(@NonNull ISyntaxElementVisitor visitor) {
        visitor.visitCode(this);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() + text.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        
        if (obj instanceof Code && super.equals(obj)) {
            Code other = (Code) obj;
            equal = this.text.equals(other.text);
        }
        
        return equal;
    }
    
}
