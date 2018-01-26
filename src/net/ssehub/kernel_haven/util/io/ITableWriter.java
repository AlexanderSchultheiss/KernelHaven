package net.ssehub.kernel_haven.util.io;

import java.io.Closeable;
import java.io.IOException;

import net.ssehub.kernel_haven.util.null_checks.NonNull;
import net.ssehub.kernel_haven.util.null_checks.Nullable;

/**
 * A writer for writing structured, table-like data.
 *
 * @author Adam
 */
public interface ITableWriter extends Closeable {
    
    /**
     * <p>
     * Writes a single row of data. The first call of this is used to determine the header of the table. This means,
     * that subsequent calls to this method must provide the same kind of data (i.e. they all must have the same class).
     * <b>This method should not be mixed with the {@link #writeRow(Object...)} method</b>, since this method creates
     * an internal state for the structure of data to extract from the objects.
     * </p>
     * <p>
     * The object passed to this method is translated in column values in one of the following ways:
     * <ol>
     *      <li>If the objects class is annotated with the {@link TableRow} annotation, each method marked with the
     *          {@link TableElement} annotation is a single column value.</li>
     *      <li>If the object implements {@link ITableRow}, the {@link ITableRow#getContent()} method is used to get
     *          the column values.</li>
     *      <li>Otherwise, the {@link #toString()} method is used as a single column value.</li>
     * </ol>
     * </p>
     * 
     * @param row The object representing the row data.
     * 
     * @throws IOException If writing to the file fails.
     * @throws IllegalArgumentException If different types of rows (i.e. different classes) are passed to the same
     *      writer, and this violates the internal state of the writer.
     */
    public void writeObject(@NonNull Object row) throws IOException, IllegalArgumentException;
    
    /**
     * Writes a single row of data. The given fields are written directly. <b>This method should not be mixed with the
     * {@link #writeObject(Object)} method</b>, since the other method creates an internal state for the structure
     * of data to extract from the objects.
     * <br />
     * Elements in the columns array are converted to a string via their {@link Object#toString()} method.
     * <code>null</code> elements in the array are converted into an empty string ("").
     * 
     * @param columns The field values to write. Not null; elements inside the array may be <code>null</code>.
     *      May be empty.
     * 
     * @throws IOException If writing to the file fails.
     */
    public void writeRow(@Nullable Object /*@NonNull*/ ... columns) throws IOException;
    // TODO: commented out @NonNull annotation because checkstyle can't parse it

    /**
     * Optional possibility how to handle a row, which is intended to be an header. Some specific writers may have
     * special formatting for header rows. The default implementation just calls {@link #writeRow(Object...))}.
     * 
     * @see #writeRow(Object...)
     * 
     * @param fields The field values to write. Not null. May be empty.
     * 
     * @throws IOException If writing to the file fails.
     */
    public default void writeHeader(@Nullable Object /*@NonNull*/ ... fields) throws IOException {
        // TODO: commented out @NonNull annotation because checkstyle can't parse it
        writeRow(fields);
    }

}
