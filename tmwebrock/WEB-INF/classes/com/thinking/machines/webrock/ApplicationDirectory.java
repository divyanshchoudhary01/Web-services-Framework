package com.thinking.machines.webrock;
import java.io.*;
public class ApplicationDirectory
{
private File directory;
public ApplicationDirectory()
{
this.directory=null;
}
public void setDirectory(File directory)
{
this.directory=directory;
}
public File getDirectory()
{
return this.directory;
}
}