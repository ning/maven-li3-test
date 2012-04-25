package com.ning;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class TestLi3TestMojo
{
    @Test
    public void testSuccess() throws IOException, InterruptedException, MojoExecutionException, MojoFailureException
    {
        ProcessBuilderWrapper mockProcessBuilderWrapper = createMock(ProcessBuilderWrapper.class);
        File baseDir = new File(".");
        mockProcessBuilderWrapper.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessBuilderWrapper.getInputStream()).andReturn(getInputReaderFromString("0 fails and 0 exceptions\n"));
        expect(mockProcessBuilderWrapper.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessBuilderWrapper.waitFor();

        replay(mockProcessBuilderWrapper);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessBuilderWrapper);
        testingMojo.setBaseDir(baseDir);
        testingMojo.execute();

        verify(mockProcessBuilderWrapper);
    }

    @Test
    public void testFailOnLi3TestMultipleFails() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessBuilderWrapper mockProcessBuilderWrapper = createMock(ProcessBuilderWrapper.class);
        File baseDir = new File(".");
        mockProcessBuilderWrapper.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessBuilderWrapper.getInputStream()).andReturn(getInputReaderFromString("2 fails and 0 exceptions\n"));
        expect(mockProcessBuilderWrapper.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessBuilderWrapper.waitFor();

        replay(mockProcessBuilderWrapper);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessBuilderWrapper);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessBuilderWrapper);
    }

    @Test
    public void testFailOnLi3TestMultipleExceptions() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessBuilderWrapper mockProcessBuilderWrapper = createMock(ProcessBuilderWrapper.class);
        File baseDir = new File(".");
        mockProcessBuilderWrapper.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessBuilderWrapper.getInputStream()).andReturn(getInputReaderFromString("0 fails and 4 exceptions\n"));
        expect(mockProcessBuilderWrapper.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessBuilderWrapper.waitFor();

        replay(mockProcessBuilderWrapper);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessBuilderWrapper);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessBuilderWrapper);
    }

    @Test
    public void testFailOnLi3TestSingleFail() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessBuilderWrapper mockProcessBuilderWrapper = createMock(ProcessBuilderWrapper.class);
        File baseDir = new File(".");
        mockProcessBuilderWrapper.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessBuilderWrapper.getInputStream()).andReturn(getInputReaderFromString("1 fail and 0 exceptions\n"));
        expect(mockProcessBuilderWrapper.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessBuilderWrapper.waitFor();

        replay(mockProcessBuilderWrapper);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessBuilderWrapper);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessBuilderWrapper);
    }

    @Test
    public void testFailOnLi3TestSingleException() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessBuilderWrapper mockProcessBuilderWrapper = createMock(ProcessBuilderWrapper.class);
        File baseDir = new File(".");
        mockProcessBuilderWrapper.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessBuilderWrapper.getInputStream()).andReturn(getInputReaderFromString("0 fails and 1 exception\n"));
        expect(mockProcessBuilderWrapper.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessBuilderWrapper.waitFor();

        replay(mockProcessBuilderWrapper);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessBuilderWrapper);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessBuilderWrapper);
    }

    @Test
    public void testFailOnLi3TestNotFullPasses() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessBuilderWrapper mockProcessBuilderWrapper = createMock(ProcessBuilderWrapper.class);
        File baseDir = new File(".");
        mockProcessBuilderWrapper.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessBuilderWrapper.getInputStream()).andReturn(getInputReaderFromString("38 / 39 passes\n1 fail and 0 exceptions\n"));
        expect(mockProcessBuilderWrapper.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessBuilderWrapper.waitFor();

        replay(mockProcessBuilderWrapper);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessBuilderWrapper);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessBuilderWrapper);
    }

    @Test
    public void testExceutionExceptionOnLi3ProcessNotStarting() throws IOException, InterruptedException, MojoFailureException
    {
        ProcessBuilderWrapper mockProcessBuilderWrapper = createMock(ProcessBuilderWrapper.class);
        File baseDir = new File(".");
        mockProcessBuilderWrapper.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expectLastCall().andThrow(new IOException());

        replay(mockProcessBuilderWrapper);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessBuilderWrapper);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoExecutionException e) {
            assertEquals(e.getMessage(), "Couldn't get li3 test process to start.");
        }
        verify(mockProcessBuilderWrapper);
    }

    @Test
    public void testExceutionExceptionOnLi3ProcessInterrupted() throws IOException, InterruptedException, MojoFailureException
    {
        ProcessBuilderWrapper mockProcessBuilderWrapper = createMock(ProcessBuilderWrapper.class);
        File baseDir = new File(".");
        mockProcessBuilderWrapper.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessBuilderWrapper.getInputStream()).andReturn(getInputReaderFromString("39 / 39 passes\n0 fails and 0 exceptions\n"));
        expect(mockProcessBuilderWrapper.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessBuilderWrapper.waitFor();
        expectLastCall().andThrow(new InterruptedException());
        replay(mockProcessBuilderWrapper);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessBuilderWrapper);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoExecutionException e) {
            assertEquals(e.getMessage(), "Problem while waiting for li3 test process.");
        }
        verify(mockProcessBuilderWrapper);
    }


    private InputStream getInputReaderFromString(String s) throws UnsupportedEncodingException
    {
        return new ByteArrayInputStream(s.getBytes("UTF-8"));
    }

}
