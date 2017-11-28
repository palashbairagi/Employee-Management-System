package com.thinking.machines.dl;
import java.util.*;
public class Employee implements EmployeeInterface
{
private int id;
private String name;
private String sex;
private Date dateOfBirth;
private String designation;
public void setID(int id)
{
this.id=id;
}
public int getID()
{
return this.id;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setSex(String sex)
{
this.sex=sex;
}
public String getSex()
{
return this.sex;
}
public void setDateOfBirth(Date dateOfBirth)
{
this.dateOfBirth=dateOfBirth;
}
public Date getDateOfBirth()
{
return this.dateOfBirth;
}
public void setDesignation(String designation)
{
this.designation=designation;
}
public String getDesignation()
{
return this.designation;
}
}