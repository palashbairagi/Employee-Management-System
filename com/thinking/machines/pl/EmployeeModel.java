package com.thinking.machines.pl;
import com.thinking.machines.dl.*;
import javax.swing.table.*;
import java.util.*;

public class EmployeeModel extends AbstractTableModel
{
private ArrayList<EmployeeInterface>employees;
private EmployeeDAO employeeDAO;
private String [] columnTitles={"S.No.","ID","Name","Sex","Date of birth","Designation"};
public EmployeeModel()
{
employeeDAO=new EmployeeDAO();
try
{
employees=employeeDAO.get();
}catch(DAOException daoException)
{
System.out.println("EmployeeModel [EmployeeModel()]:"+daoException);
employees=new ArrayList<EmployeeInterface>();
}
}
public int getRowCount()
{
return employees.size();
}
public int getColumnCount()
{
return columnTitles.length;
} 
public boolean isCellEditable()
{
return false;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(rowIndex>=0 && rowIndex<=employees.size())
{
EmployeeInterface employee=employees.get(rowIndex);
if(columnIndex==0)return new Integer(rowIndex+1);
if(columnIndex==1)return employee.getID();
if(columnIndex==2)return employee.getName();
if(columnIndex==3)return employee.getSex();
if(columnIndex==4)return convertDateToString(employee.getDateOfBirth());
if(columnIndex==5)return employee.getDesignation();
}
return "";
}
public Class getColumnClass(int columnIndex)
{
try
{
if(columnIndex==0 || columnIndex==1)return Class.forName("java.lang.Integer");
if(columnIndex>=2 && columnIndex<=5)return Class.forName("java.lang.String");
return Class.forName("java.lang.String");
}catch(ClassNotFoundException classNotFoundException)
{
return "".getClass();
}
}
public String getColumnName(int columnIndex)
{
return columnTitles[columnIndex];
}
private String convertDateToString(java.util.Date date)
{
return date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
}
public void changeNotify()
{
try
{
employees=employeeDAO.get();
}catch(DAOException daoException)
{
System.out.println("EmployeeModel [EmployeeModel()]:"+daoException);
employees=new ArrayList<EmployeeInterface>();
}
}
public EmployeeInterface get(int index)
{
if(index>=0 && index<employees.size())
{
return employees.get(index);
}
else
{
throw new ArrayIndexOutOfBoundsException("Invalid employee index");
}
}
public int getEmployeeIndex(int id)
{
int e;
e=0;
while(e<employees.size())
{
if(employees.get(e).getID()==id)return e;
e++;
}
return -1;
}
}