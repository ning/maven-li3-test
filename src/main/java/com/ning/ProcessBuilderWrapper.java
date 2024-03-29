package com.ning;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProcessBuilderWrapper
{
    private Process p;
    public void runWith(String li3CommandPath, String testPath, File workingDirectory) throws IOException
    {
        List<String> command = new ArrayList<String>();
        command.add(li3CommandPath);
        command.add("test"); // run the test command on li3
        command.add(testPath);
        ProcessBuilder builder = new ProcessBuilder(command);
        if(workingDirectory != null){
            builder.directory(workingDirectory);
        }
        p = builder.start();
    }

    public InputStream getInputStream()
    {
        if(p == null){
            return null;
        }
        return p.getInputStream();
    }

    public InputStream getErrorStream()
    {
        if (p == null){
            return null;
        }
        return p.getErrorStream();
    }

    public void waitFor() throws InterruptedException
    {
        if (p == null){
            return;
        }
        p.waitFor();
    }
}
