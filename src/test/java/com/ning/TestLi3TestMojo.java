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
        ProcessRunner mockProcessRunner = createMock(ProcessRunner.class);
        File baseDir = new File(".");
        mockProcessRunner.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessRunner.getInputStream()).andReturn(getInputReaderFromString("0 fails and 0 exceptions\n"));
        expect(mockProcessRunner.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessRunner.waitFor();

        replay(mockProcessRunner);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessRunner);
        testingMojo.setBaseDir(baseDir);
        testingMojo.execute();

        verify(mockProcessRunner);
    }

    @Test
    public void testFailOnLi3TestMultipleFails() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessRunner mockProcessRunner = createMock(ProcessRunner.class);
        File baseDir = new File(".");
        mockProcessRunner.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessRunner.getInputStream()).andReturn(getInputReaderFromString("2 fails and 0 exceptions\n"));
        expect(mockProcessRunner.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessRunner.waitFor();

        replay(mockProcessRunner);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessRunner);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessRunner);
    }

    @Test
    public void testFailOnLi3TestMultipleExceptions() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessRunner mockProcessRunner = createMock(ProcessRunner.class);
        File baseDir = new File(".");
        mockProcessRunner.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessRunner.getInputStream()).andReturn(getInputReaderFromString("0 fails and 4 exceptions\n"));
        expect(mockProcessRunner.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessRunner.waitFor();

        replay(mockProcessRunner);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessRunner);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessRunner);
    }

    @Test
    public void testFailOnLi3TestSingleFail() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessRunner mockProcessRunner = createMock(ProcessRunner.class);
        File baseDir = new File(".");
        mockProcessRunner.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessRunner.getInputStream()).andReturn(getInputReaderFromString("1 fail and 0 exceptions\n"));
        expect(mockProcessRunner.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessRunner.waitFor();

        replay(mockProcessRunner);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessRunner);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessRunner);
    }

    @Test
    public void testFailOnLi3TestSingleException() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessRunner mockProcessRunner = createMock(ProcessRunner.class);
        File baseDir = new File(".");
        mockProcessRunner.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessRunner.getInputStream()).andReturn(getInputReaderFromString("0 fails and 1 exception\n"));
        expect(mockProcessRunner.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessRunner.waitFor();

        replay(mockProcessRunner);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessRunner);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessRunner);
    }

    @Test
    public void testFailOnLi3TestNotFullPasses() throws IOException, InterruptedException, MojoExecutionException
    {
        ProcessRunner mockProcessRunner = createMock(ProcessRunner.class);
        File baseDir = new File(".");
        mockProcessRunner.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessRunner.getInputStream()).andReturn(getInputReaderFromString("38 / 39 passes\n1 fail and 0 exceptions\n"));
        expect(mockProcessRunner.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessRunner.waitFor();

        replay(mockProcessRunner);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessRunner);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoFailureException e) {
            assertEquals(e.getMessage(), "Lithium Tests Failed");
        }
        verify(mockProcessRunner);
    }

    @Test
    public void testExceutionExceptionOnLi3ProcessNotStarting() throws IOException, InterruptedException, MojoFailureException
    {
        ProcessRunner mockProcessRunner = createMock(ProcessRunner.class);
        File baseDir = new File(".");
        mockProcessRunner.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expectLastCall().andThrow(new IOException());

        replay(mockProcessRunner);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessRunner);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoExecutionException e) {
            assertEquals(e.getMessage(), "Couldn't get li3 test process to start.");
        }
        verify(mockProcessRunner);
    }

    @Test
    public void testExceutionExceptionOnLi3ProcessInterrupted() throws IOException, InterruptedException, MojoFailureException
    {
        ProcessRunner mockProcessRunner = createMock(ProcessRunner.class);
        File baseDir = new File(".");
        mockProcessRunner.runWith(baseDir.getCanonicalPath() + "/libraries/lithium/console/li3", "app/tests");
        expect(mockProcessRunner.getInputStream()).andReturn(getInputReaderFromString("39 / 39 passes\n0 fails and 0 exceptions\n"));
        expect(mockProcessRunner.getErrorStream()).andReturn(getInputReaderFromString(""));
        mockProcessRunner.waitFor();
        expectLastCall().andThrow(new InterruptedException());
        replay(mockProcessRunner);
        Li3TestMojo testingMojo = new Li3TestMojo(mockProcessRunner);
        testingMojo.setBaseDir(baseDir);
        try {
            testingMojo.execute();
            fail();
        }
        catch (MojoExecutionException e) {
            assertEquals(e.getMessage(), "Problem while waiting for li3 test process.");
        }
        verify(mockProcessRunner);
    }


    private InputStream getInputReaderFromString(String s) throws UnsupportedEncodingException
    {
        return new ByteArrayInputStream(s.getBytes("UTF-8"));
    }

}
