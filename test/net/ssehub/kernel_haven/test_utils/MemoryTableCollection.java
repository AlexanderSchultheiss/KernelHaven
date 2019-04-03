/*
 * Copyright 2017-2019 University of Hildesheim, Software Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ssehub.kernel_haven.test_utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.ssehub.kernel_haven.analysis.PipelineAnalysis;
import net.ssehub.kernel_haven.util.io.ITableCollection;
import net.ssehub.kernel_haven.util.io.ITableReader;
import net.ssehub.kernel_haven.util.io.ITableWriter;
import net.ssehub.kernel_haven.util.null_checks.NonNull;

/**
 * An {@link ITableCollection} that creates {@link MemoryTableWriter}s. Useful to access the result of a test execution
 * in a programmatic way (via {@link MemoryTableWriter#getTable(String)}.
 * 
 * @author Adam
 */
public class MemoryTableCollection implements ITableCollection {

    /**
     * For manual use.
     */
    public MemoryTableCollection() {
        // Nothing to do
    }
    
    /**
     * Required by {@link PipelineAnalysis} (create Result).
     * @param ignored Will be ignored.
     */
    public MemoryTableCollection(File ignored) {
        // Nothing to do
    }
    
    @Override
    public void close() {
        // nothing to do
    }

    @Override
    public @NonNull ITableReader getReader(@NonNull String name) throws IOException {
        throw new IOException("The MemoryTableCollection can not read");
    }

    @Override
    public @NonNull Set<@NonNull String> getTableNames() throws IOException {
        return MemoryTableWriter.getTableNames();
    }

    @Override
    public @NonNull ITableWriter getWriter(@NonNull String name) throws IOException {
        return new MemoryTableWriter(name);
    }

    @Override
    public @NonNull Set<@NonNull File> getFiles() throws IOException {
        return new HashSet<>();
    }

}
