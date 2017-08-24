package net.ssehub.kernel_haven.config;

import java.io.File;
import java.util.Properties;
import java.util.regex.Pattern;

import net.ssehub.kernel_haven.SetUpException;

/**
 * A configuration for the code model extractor.
 * 
 * @author Adam
 * @author Alice
 */
public class CodeExtractorConfiguration implements IConfiguration {

    private Properties properties;
    
    private long providerTimeout;
    
    private boolean cacheRead;
    
    private boolean cacheWrite;
    
    private File[] files;
    
    private Pattern filenamePattern;
    
    private int threads;
    
    private File sourceTree;
    
    private String arch;
    
    private File cacheDir;
    
    private File resourceDir;
    
    /**
     * Creates an empty configuration for the code model. Note that this configuration is not
     * valid and the setters have to be called first.
     */
    public CodeExtractorConfiguration() {
    }
    
    /**
     * The internal property object to refer to for extractor specific settings.
     * 
     * @param properties The properties, never <code>null</code>.
     */
    void setProperties(Properties properties) {
        this.properties = properties;
    }
    
    /**
     * Reads a property from the user configuration file.
     * 
     * @param key The key of the property.
     * @return The value set for the key, or <code>null</code> if not specified.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Overrides a property of the user configuration file. This should only be used for
     * extractor specific properties that do not have an extract method in this class.
     * 
     * @param key The key of the setting.
     * @param value The value of the setting.
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
    
    /**
     * Reads a property from the user configuration file.
     * 
     * @param key The key of the property.
     * @param defaultValue The default value to return if not specified in file.
     * @return The value set by the user, or the default value.
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * The timeout that the provider waits until the extractor is stopped.
     * 
     * @return The timeout in milliseconds. 0 means no timeout.
     */
    public long getProviderTimeout() {
        return providerTimeout;
    }
    
    /**
     * Whether to read from the code model cache.
     * 
     * @return Whether to read from the code model cache.
     */
    public boolean isCacheRead() {
        return cacheRead;
    }
    
    /**
     * Whether to write to the code model cache.
     * 
     * @return Whether to write to the code model cache.
     */
    public boolean isCacheWrite() {
        return cacheWrite;
    }
    
    /**
     * The list of files that the code extractor should analyze. It is not checked if they
     * actually exist.
     * 
     * @return The list of files (and directories) to analyze. Never <code>null</code>.
     */
    public File[] getFiles() {
        return files;
    }

    /**
     * The pattern to detect which files are source files.
     * 
     * @return The pattern for source files. Never <code>null</code>.
     */
    public Pattern getFilenamePattern() {
        return filenamePattern;
    }

    /**
     * The number of threads to use in the code extractor.
     * 
     * @return The number of threads.
     */
    public int getThreads() {
        return threads;
    }
    
    /**
     * Returns the directory of the source tree to analyze. If present, we have read access here.
     * 
     * @return The directory to analyze. May be <code>null</code>.
     */
    public File getSourceTree() {
        return sourceTree;
    }

    /**
     * The architecture of the Linux kernel to analyze.
     * 
     * @return The Linux architecture; may be <code>null</code>.
     */
    public String getArch() {
        return arch;
    }
    
    /**
     * Returns the directory where to read and write cache from. If present, then we have read and
     * write access here.
     * 
     * @return The cache dir; Never <code>null</code> if any of the caches of the providers is activated.
     */
    public File getCacheDir() {
        return cacheDir;
    }

    /**
     * The timeout that the provider waits until the extractor is stopped.
     * 
     * @param providerTimeout The timeout in milliseconds. 0 means no timeout.
     */
    public void setProviderTimeout(long providerTimeout) {
        this.providerTimeout = providerTimeout;
    }

    /**
     * Whether to read from the code model cache.
     * @param cacheRead Whether to read from the code model cache.
     */
    public void setCacheRead(boolean cacheRead) {
        this.cacheRead = cacheRead;
    }

    /**
     * Whether to write to the code model cache.
     * 
     * @param cacheWrite Whether to write to the code model cache.
     */
    public void setCacheWrite(boolean cacheWrite) {
        this.cacheWrite = cacheWrite;
    }

    /**
     * The list of files that the code extractor should analyze. It is not checked if they
     * actually exist.
     * 
     * @param files The list of files (and directories) to analyze. Never <code>null</code>.
     */
    public void setFiles(File[] files) {
        this.files = files;
    }

    /**
     * The pattern to detect which files are source files.
     * 
     * @param filenamePattern The pattern for source files. Never <code>null</code>.
     */
    public void setFilenamePattern(Pattern filenamePattern) {
        this.filenamePattern = filenamePattern;
    }

    /**
     * The number of threads to use in the code extractor.
     * 
     * @param threads The number of threads.
     */
    public void setThreads(int threads) {
        this.threads = threads;
    }
    
    /**
     * The directory of the source tree to analyze. If present, we have read access here.
     * 
     * @param sourceTree The directory to analyze. May be <code>null</code>.
     */
    public void setSourceTree(File sourceTree) {
        this.sourceTree = sourceTree;
    }

    /**
     * The architecture of the Linux kernel to analyze.
     * 
     * @param arch The Linux architecture; may be <code>null</code>.
     */
    public void setArch(String arch) {
        this.arch = arch;
    }
    
    /**
     * The directory where to read and write cache from. If present, then we have read and
     * write access here.
     * 
     * @param cacheDir The cache dir; Never <code>null</code> if any of the caches of the providers is activated.
     */
    public void setCacheDir(File cacheDir) {
        this.cacheDir = cacheDir;
    }
    
    /**
     * The resource directory. We need to have read and write access here.
     * 
     * @param resourceDir The resource directory. Never null.
     */
    public void setResourceDir(File resourceDir) {
        this.resourceDir = resourceDir;
    }
    
    /**
     * Returns the directory where the given extractor can store or read its resources from.
     * Extractors have their own directories named the same as their fully qualified class names
     * in a global resource directory specified in the config.
     * 
     * If the directory for the specified extractor is not yet created, then this methods creates it.
     * 
     * @param extractor The extractor that wants to store or read data.
     * @return The directory where the specified extractor can put and read its resources. Never null.
     * 
     * @throws SetUpException If creating the resource directory failed for some reason.
     */
    public File getExtractorResourceDir(Class<?> extractor) throws SetUpException {
        File extractorResDir = new File(resourceDir, extractor.getName());
        extractorResDir.mkdir();
        
        if (!extractorResDir.isDirectory() || !extractorResDir.canWrite()) {
            throw new SetUpException("Couldn't create resource dir for extractor " + extractor.getName());
        }
        
        return extractorResDir;
    }

}
