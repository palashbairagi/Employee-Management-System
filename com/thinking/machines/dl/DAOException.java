package com.thinking.machines.dl;
import java.io.*;
public class DAOException extends Exception implements Serializable
{
private String message;
public DAOException(String message)
{
this.message=message;
}
public void setException(String message)
{
this.message=message;
}
public String getException()
{
return this.message;
}
public String getMessage()
{
return this.message;
}
public String toString()
{
return this.message;
}
}
