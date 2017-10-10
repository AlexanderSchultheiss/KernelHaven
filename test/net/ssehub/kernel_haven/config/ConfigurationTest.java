package net.ssehub.kernel_haven.config;

import static net.ssehub.kernel_haven.config.Setting.Type.BOOLEAN;
import static net.ssehub.kernel_haven.config.Setting.Type.DIRECTORY;
import static net.ssehub.kernel_haven.config.Setting.Type.ENUM;
import static net.ssehub.kernel_haven.config.Setting.Type.FILE;
import static net.ssehub.kernel_haven.config.Setting.Type.INTEGER;
import static net.ssehub.kernel_haven.config.Setting.Type.PATH;
import static net.ssehub.kernel_haven.config.Setting.Type.REGEX;
import static net.ssehub.kernel_haven.config.Setting.Type.SETTING_LIST;
import static net.ssehub.kernel_haven.config.Setting.Type.STRING;
import static net.ssehub.kernel_haven.config.Setting.Type.STRING_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import net.ssehub.kernel_haven.SetUpException;

/**
 * Tests the {@link Configuration} class.
 * 
 * @author Adam
 * @author El-Sharkawy
 */
public class ConfigurationTest {

    /**
     * Tests that string values are handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testStringValues() throws SetUpException {
        Setting<String> s1 = new Setting<>("s1", STRING, false, null, "");
        Setting<String> s2 = new Setting<>("s2", STRING, true, "default", "");
        Setting<String> s3 = new Setting<>("s3", STRING, true, "default", "");
        
        Properties props = new Properties();
        props.setProperty("s2", "notDefault");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        
        assertThat(config.getValue(s1), nullValue());
        assertThat(config.getValue(s2), is("notDefault"));
        assertThat(config.getValue(s3), is("default"));
    }
    
    /**
     * Tests that integer values are handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testIntegerValues() throws SetUpException {
        Setting<Integer> s1 = new Setting<>("s1", INTEGER, false, null, "");
        Setting<Integer> s2 = new Setting<>("s2", INTEGER, true, "1", "");
        Setting<Integer> s3 = new Setting<>("s3", INTEGER, true, "-1", "");
        
        Properties props = new Properties();
        props.setProperty("s2", "2");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        
        assertThat(config.getValue(s1), nullValue());
        assertThat(config.getValue(s2), is(2));
        assertThat(config.getValue(s3), is(-1));
    }
    
    /**
     * Tests that invalid integer values are handled correctly.
     * 
     * @throws SetUpException wanted.
     */
    @Test(expected = SetUpException.class)
    public void testInvalidIntegerValues() throws SetUpException {
        Setting<Integer> s1 = new Setting<>("s1", INTEGER, false, null, "");
        
        Properties props = new Properties();
        props.setProperty("s1", "notaninteger");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
    }
    
    /**
     * Tests that directory values are handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testDirectoryValues() throws SetUpException {
        Setting<File> s1 = new Setting<>("s1", DIRECTORY, false, null, "");
        Setting<File> s2 = new Setting<>("s2", DIRECTORY, true, "testdata/", "");
        Setting<File> s3 = new Setting<>("s3", DIRECTORY, true, "testdata/", "");
        
        Properties props = new Properties();
        props.setProperty("s2", "testdata/configs/");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        
        assertThat(config.getValue(s1), nullValue());
        assertThat(config.getValue(s2), is(new File("testdata/configs")));
        assertThat(config.getValue(s3), is(new File("testdata")));
    }
    
    /**
     * Tests that invalid directory values are handled correctly.
     * 
     * @throws SetUpException wanted.
     */
    @Test(expected = SetUpException.class)
    public void testInvalidDirectoryValues() throws SetUpException {
        Setting<File> s1 = new Setting<>("s1", DIRECTORY, false, null, "");
        
        Properties props = new Properties();
        props.setProperty("s1", "doesnt_exist");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
    }
    
    /**
     * Tests that file values are handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testFileValues() throws SetUpException {
        Setting<File> s1 = new Setting<>("s1", FILE, false, null, "");
        Setting<File> s2 = new Setting<>("s2", FILE, true, "testdata/configs/minimal.properties", "");
        Setting<File> s3 = new Setting<>("s3", FILE, true, "testdata/configs/minimal.properties", "");
        
        Properties props = new Properties();
        props.setProperty("s2", "testdata/configs/gigantic.properties");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        
        assertThat(config.getValue(s1), nullValue());
        assertThat(config.getValue(s2), is(new File("testdata/configs/gigantic.properties")));
        assertThat(config.getValue(s3), is(new File("testdata/configs/minimal.properties")));
    }
    
    /**
     * Tests that invalid file values are handled correctly.
     * 
     * @throws SetUpException wanted.
     */
    @Test(expected = SetUpException.class)
    public void testInvalidFileValues() throws SetUpException {
        Setting<File> s1 = new Setting<>("s1", FILE, false, null, "");
        
        Properties props = new Properties();
        props.setProperty("s1", "doesnt_exist");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
    }
    
    /**
     * Tests that path values are handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testPathValues() throws SetUpException {
        Setting<File> s1 = new Setting<>("s1", PATH, false, null, "");
        Setting<File> s2 = new Setting<>("s2", PATH, true, "some/path/tofile", "");
        Setting<File> s3 = new Setting<>("s3", PATH, true, "some/path/tofile", "");
        
        Properties props = new Properties();
        props.setProperty("s2", "other/path/todir/");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        
        assertThat(config.getValue(s1), nullValue());
        assertThat(config.getValue(s2), is(new File("other/path/todir/")));
        assertThat(config.getValue(s3), is(new File("some/path/tofile")));
    }
    
    
    /**
     * Tests that Boolean config values are handled correctly by the {@link Configuration}.
     * @throws SetUpException Must not occur, is not tested by this test.
     */
    @Test
    public void testBooleanValues() throws SetUpException {
        Setting<Boolean> falseValue = new Setting<>("value.false", BOOLEAN, false, "false", "");
        Setting<Boolean> trueValue = new Setting<>("value.true", BOOLEAN, false, "true", "");
        Setting<Boolean> overwriteableValue = new Setting<>("value.x", BOOLEAN, false, "true", "");
        
        Properties prop = new Properties();
        prop.setProperty(overwriteableValue.getKey(), "false");
        Configuration config = new Configuration(prop);
        config.registerSetting(falseValue);
        config.registerSetting(trueValue);
        config.registerSetting(overwriteableValue);
        
        boolean value = config.getValue(falseValue);
        Assert.assertFalse("False value is treated as true", value);
        
        value = config.getValue(trueValue);
        Assert.assertTrue("True value is treated as false", value);
        
        value = config.getValue(overwriteableValue);
        Assert.assertFalse("False value is treated as true", value);
    }
    
    /**
     * Tests that regex values are handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testRegexValues() throws SetUpException {
        Setting<Pattern> s1 = new Setting<>("s1", REGEX, false, null, "");
        Setting<Pattern> s2 = new Setting<>("s2", REGEX, true, ".*", "");
        Setting<Pattern> s3 = new Setting<>("s3", REGEX, true, ".*", "");
        
        Properties props = new Properties();
        props.setProperty("s2", "[a-zA-Z0-9]+");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        
        assertThat(config.getValue(s1), nullValue());
        assertThat(config.getValue(s2).pattern(), is("[a-zA-Z0-9]+"));
        assertThat(config.getValue(s3).pattern(), is(".*"));
    }
    
    /**
     * Tests that invalid regex values are handled correctly.
     * 
     * @throws SetUpException wanted.
     */
    @Test(expected = SetUpException.class)
    public void testInvalidRegexValues() throws SetUpException {
        Setting<Pattern> s1 = new Setting<>("s1", REGEX, false, null, "");
        
        Properties props = new Properties();
        props.setProperty("s1", "(*|+)");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
    }
    
    /**
     * Tests that string list values are handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testStringListValues() throws SetUpException {
        Setting<List<String>> s1 = new Setting<>("s1", STRING_LIST, false, null, "");
        Setting<List<String>> s2 = new Setting<>("s2", STRING_LIST, true, "a, b, c", "");
        Setting<List<String>> s3 = new Setting<>("s3", STRING_LIST, true, "a, b, c", "");
        
        Properties props = new Properties();
        props.setProperty("s2", "one and, two\nand, three");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        
        assertThat(config.getValue(s1), nullValue());
        assertThat(config.getValue(s2), is(Arrays.asList("one and", "two\nand", "three")));
        assertThat(config.getValue(s3), is(Arrays.asList("a", "b", "c")));
    }
    
    /**
     * An enum for testing purposes.
     */
    private static enum TestEnum {
        VALUE_1,
        VALUE_2,
        VALUE_3
    }
    
    /**
     * Tests that enum values are handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testEnumValues() throws SetUpException {
        Setting<TestEnum> s1 = new EnumSetting<ConfigurationTest.TestEnum>("s1", TestEnum.class, false, null, "");
        Setting<TestEnum> s2 = new EnumSetting<ConfigurationTest.TestEnum>("s2", TestEnum.class, true,
                TestEnum.VALUE_1, "");
        Setting<TestEnum> s3 = new EnumSetting<ConfigurationTest.TestEnum>("s3", TestEnum.class, true,
                TestEnum.VALUE_1, "");
        
        Properties props = new Properties();
        props.setProperty("s2", "value_3");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        
        assertThat(config.getValue(s1), nullValue());
        assertThat(config.getValue(s2), is(TestEnum.VALUE_3));
        assertThat(config.getValue(s3), is(TestEnum.VALUE_1));
    }
    
    /**
     * Tests that invalid enum values are handled correctly.
     * 
     * @throws SetUpException wanted.
     */
    @Test(expected = SetUpException.class)
    public void testInvalidEnumValues() throws SetUpException {
        Setting<TestEnum> s1 = new EnumSetting<ConfigurationTest.TestEnum>("s1", TestEnum.class, false, null, "");
        
        Properties props = new Properties();
        props.setProperty("s1", "not_a_value");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
    }
    
    /**
     * Tests that missing mandatory enum values are handled correctly.
     * 
     * @throws SetUpException wanted.
     */
    @Test(expected = SetUpException.class)
    public void testMissingMandatoryEnumValues() throws SetUpException {
        Setting<TestEnum> s1 = new EnumSetting<ConfigurationTest.TestEnum>("s1", TestEnum.class, true, null, "");
        
        Properties props = new Properties();
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
    }
    
    /**
     * Tests that trying to register settings with type ENUM that are not instances of EnumSetting are handled
     * correctly.
     * 
     * @throws SetUpException wanted.
     */
    @Test(expected = SetUpException.class)
    public void testInvalidEnumSettingClass() throws SetUpException {
        Setting<TestEnum> s1 = new Setting<>("s1", ENUM, false, null, "");
        
        Properties props = new Properties();
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
    }
    
    /**
     * Tests that setting list values are handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testSettingListValues() throws SetUpException {
     // mandatory should have no effect
        Setting<List<String>> s1 = new Setting<>("s1", SETTING_LIST, true, null, "");
        Setting<List<String>> s2 = new Setting<>("s2", SETTING_LIST, true, null, "");
        // default should have no effect
        Setting<List<String>> s3 = new Setting<>("s3", SETTING_LIST, true, "default", "");
        
        Properties props = new Properties();
        props.setProperty("s2.0", "first value");
        props.setProperty("s2.1", "second value");
        props.setProperty("s2.2", "third value");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        
        assertThat(config.getValue(s1), is(Arrays.asList()));
        assertThat(config.getValue(s2), is(Arrays.asList("first value", "second value", "third value")));
        assertThat(config.getValue(s3), is(Arrays.asList()));
    }
    
    /**
     * Checks whether registering the same setting key twice with different settings correctly throws an exception.
     * 
     * @throws SetUpException wanted. 
     */
    @Test(expected = SetUpException.class)
    public void testRegisterDifferentTwice() throws SetUpException {
        Setting<String> s1 = new Setting<>("same.key", STRING, false, null, "");
        Setting<String> s2 = new Setting<>("same.key", STRING, false, null, "");
        
        Configuration config = new Configuration(new Properties());
        config.registerSetting(s1);
        config.registerSetting(s2);
    }
    
    /**
     * Checks whether registering the same setting key twice with the same setting works correctly.
     * 
     * @throws SetUpException unwanted. 
     */
    @Test
    public void testRegisterSameTwice() throws SetUpException {
        Setting<String> s1 = new Setting<>("same.key", STRING, false, null, "");
        
        Configuration config = new Configuration(new Properties());
        config.registerSetting(s1);
        config.registerSetting(s1);
    }
    
    /**
     * Checks whether accessing not registered settings correctly throws an exception.
     * 
     * @throws SetUpException unwanted. 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReadNotRegistered() throws SetUpException {
        Setting<String> s1 = new Setting<>("same.key", STRING, false, null, "");
        
        Configuration config = new Configuration(new Properties());
        config.getValue(s1);
    }
    
    /**
     * Checks whether writing not registered settings correctly throws an exception.
     * 
     * @throws SetUpException unwanted. 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetNotRegistered() throws SetUpException {
        Setting<String> s1 = new Setting<>("same.key", STRING, false, null, "");
        
        Configuration config = new Configuration(new Properties());
        config.setValue(s1, "");
    }
    
    /**
     * Checks whether a missing mandatory setting correctly throws an exception.
     * 
     * @throws SetUpException wanted.
     */
    @Test(expected = SetUpException.class)
    public void testMissingMandatory() throws SetUpException {
        Setting<String> s1 = new Setting<>("key", STRING, true, null, "");
        
        Configuration config = new Configuration(new Properties());
        config.registerSetting(s1);
    }
    
    /**
     * Checks whether a missing mandatory setting does not throw an exception if it has a default value.
     * 
     * @throws SetUpException unwante.d
     */
    @Test
    public void testMissingMandatoryWithDefault() throws SetUpException {
        Setting<String> s1 = new Setting<>("key", STRING, true, "default", "");
        
        Configuration config = new Configuration(new Properties());
        config.registerSetting(s1);
    }
    
    /**
     * Tests whether a setting with an invalid generic is handled correctly.
     * 
     * @throws SetUpException unwanted.
     */
    @Test(expected = ClassCastException.class)
    public void testInvalidGeneric() throws SetUpException {
        Setting<Integer> s1 = new Setting<>("key", BOOLEAN, true, "true", "");
        
        Configuration config = new Configuration(new Properties());
        config.registerSetting(s1);
        Integer integer = config.getValue(s1); // without the assignment we don't get an exception...
        assertThat(integer, is(1)); // not actually reached
    }
    
    /**
     * Tests that setting a value correctly overwrites the previous value.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testSetValue() throws SetUpException {
        Setting<String> s1 = new Setting<>("s1", STRING, false, null, "");
        
        Properties props = new Properties();
        props.setProperty("s1", "original");
        Configuration config = new Configuration(props);
        config.registerSetting(s1);
        
        assertThat(config.getValue(s1), is("original"));
        
        config.setValue(s1, "new");
        
        assertThat(config.getValue(s1), is("new"));
    }
    
    /**
     * Tests that checks are correctly disabled.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testDisabledChecks() throws SetUpException {
        // not existing directory
        Setting<File> s1 = new Setting<>("s1", DIRECTORY, false, null, "");
        // not existing file
        Setting<File> s2 = new Setting<>("s2", FILE, false, null, "");
        // missing mandatory enum value
        Setting<TestEnum> s3 = new EnumSetting<ConfigurationTest.TestEnum>("s3", TestEnum.class, true, null, "");
        // missing mandatory value
        Setting<String> s4 = new Setting<>("s4", STRING, true, null, "");
        
        Properties props = new Properties();
        props.setProperty("s1", "doesnt_exist");
        props.setProperty("s2", "doesnt_exist");
        
        Configuration config = new Configuration(props, false);
        config.registerSetting(s1);
        config.registerSetting(s2);
        config.registerSetting(s3);
        config.registerSetting(s4);
    }
    
    /**
     * Tests that reading from a file works.
     * 
     * @throws SetUpException unwanted.
     */
    @Test
    public void testReadFromFile() throws SetUpException {
        Setting<String> s1 = new Setting<>("s1", STRING, true, null, "");
        
        Configuration config = new Configuration(new File("testdata/configs/test.properties"));
        config.registerSetting(s1);
        
        assertThat(config.getValue(s1), is("value"));
        
        assertThat(config.getPropertyFile(), is(new File("testdata/configs/test.properties")));
    }
    
    /**
     * Tests that reading from a not existing file works correctly.
     * 
     * @throws SetUpException wanted.
     */
    @Test(expected = SetUpException.class)
    public void testReadFromInvalidFile() throws SetUpException {
        new Configuration(new File("testdata/configs/doesnt_exist.properties"));
    }
    
    /**
     * Tests manually setting the property file.
     */
    @Test
    public void testSetPropertyFile() {
        Configuration config = new Configuration(new Properties());
        assertThat(config.getPropertyFile(), nullValue());
        
        config.setPropertyFile(new File("some.properties"));
        assertThat(config.getPropertyFile(), is(new File("some.properties")));
    }
    
    /**
     * Test the old getProperty() method.
     */
    @Test
    @SuppressWarnings("deprecation")
    public void testOldGetProperty() {
        Properties props = new Properties();
        props.setProperty("s1", "value");
        Configuration config = new Configuration(props);
        
        assertThat(config.getProperty("s1"), is("value"));
        assertThat(config.getProperty("s1", "default"), is("value"));
        assertThat(config.getProperty("s2"), nullValue());
        assertThat(config.getProperty("s2", "default"), is("default"));
    }
    
}