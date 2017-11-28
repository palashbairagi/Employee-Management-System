package thinking.machines.dl;
import java.io.*;
import java.util.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
private String convertDateToString(Date date)
{
return date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
}
private Date convertStringToDate(String string)throws ParseException
{
Date date=null;
try{
date=simpleDateFormat.parse(string);
}
catch(ParseException parseException)
{
throw parseException;
}
return date;
}
public int getCount() throws DAOException
{
int count=0;
try{
File file=new File(dataFile);
if(file.exists()==false)return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"r");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
count++;
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println("EmployeeDAO[getCount()]"+ioException); //remove after testing
throw new DAOException("Data Exception");
}
return count;
}
public boolean exists(int id)throws DAOException
{
boolean found=false;
try
{
get(id);
found=true;
}catch(DAOException daoException)
{
System.out.println("EmployeeDAO[exists(int id)]:"+daoException);
}
return found;
}
public EmployeeInterface get(int id)throws DAOException
{
Employee employee=null;
try
{
File file=new File(dataFile);
if(file.exists()==false)throw new DAOException("Invalid id.");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"r");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid id.");
}
boolean found=false;
int vId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vId=Integer.parseInt(randomAccessFile.readLine());
if(vId==id)
{
employee=new Employee();
employee.setID(vId);
employee.setName(randomAccessFile.readLine());
employee.setSex(randomAccessFile.readLine());
try
{
employee.setDateOfBirth(convertStringToDate(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
throw new IOException("Invalid date of birth in data file");
}
employee.setDesignation(randomAccessFile.readLine());
found=true;
break;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
randomAccessFile.close();
if(!found)throw new DAOException("Invalid id.");
}catch(IOException ioException)
{
System.out.println("EmployeeDAO[get(int id)]:"+ioException);
}
return employee;
}
public java.util.ArrayList<EmployeeInterface>get() throws DAOException
{
ArrayList<EmployeeInterface>employees=new ArrayList<EmployeeInterface>();
Employee employee=null;
try{
File file=new File(dataFile);
if(file.exists()==false)throw new DAOException("No Employees");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"r");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("No Employees");
}
int vId;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employee=new Employee();
employee.setID(Integer.parseInt(randomAccessFile.readLine()));
employee.setName(randomAccessFile.readLine());
employee.setSex(randomAccessFile.readLine());
try
{
employee.setDateOfBirth(convertStringToDate(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
throw new IOException("Invalid date of birth in data file");
}
employee.setDesignation(randomAccessFile.readLine());
employees.add(employee);
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println("EmployeeDAO[get()]:"+ioException);
}
if(employees.size()==0)
{
throw new DAOException("No Employees");
}
return employees;
}
public void add(EmployeeInterface employee)throws DAOException
{
if(exists(employee.getID()))
{
throw new DAOException("Id.Exists");
}
try
{
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(new File(dataFile),"rw");
randomAccessFile.seek(randomAccessFile.length());
randomAccessFile.writeBytes(employee.getID()+"\n");
randomAccessFile.writeBytes(employee.getName()+"\n");
randomAccessFile.writeBytes(employee.getSex()+"\n");
randomAccessFile.writeBytes(convertDateToString(employee.getDateOfBirth())+"\n");
randomAccessFile.writeBytes(employee.getDesignation()+"\n");
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println("EmployeeDAo[add(EmployeeInterface employee)]:"+ioException);
}
}
public void update(EmployeeInterface employee)throws DAOException
{
if(!exists(employee.getID()))throw new DAOException("Invalid Employee");
ArrayList<EmployeeInterface>employees=get();
try
{
File file=new File(dataFile);
file.delete();
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
for(int i=0;i<employees.size();i++)
{
if(employees.get(i).getID()==employee.getID())
{
randomAccessFile.writeBytes(employee.getID()+"\n");
randomAccessFile.writeBytes(employee.getName()+"\n");
randomAccessFile.writeBytes(employee.getSex()+"\n");
randomAccessFile.writeBytes(convertDateToString(employee.getDateOfBirth())+"\n");
randomAccessFile.writeBytes(employee.getDesignation()+"\n");
}
else
{
randomAccessFile.writeBytes(employees.get(i).getID()+"\n");
randomAccessFile.writeBytes(employees.get(i).getName()+"\n");
randomAccessFile.writeBytes(employees.get(i).getSex()+"\n");
randomAccessFile.writeBytes(convertDateToString(employees.get(i).getDateOfBirth())+"\n");
randomAccessFile.writeBytes(employees.get(i).getDesignation()+"\n");
}
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println("EmployeeDAO[update(EmployeeInterface employee)]:"+ioException);
}
}
public void delete(int id)throws DAOException
{
if(!exists(id))throw new DAOException("Invalid id.");
ArrayList<EmployeeInterface>employees=get();
try
{
File file=new File(dataFile);
file.delete();
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
for(int i=0;i<employees.size();i++)
{
if(employees.get(i).getID()!=id)
{
randomAccessFile.writeBytes(employees.get(i).getID()+"\n");
randomAccessFile.writeBytes(employees.get(i).getName()+"\n");
randomAccessFile.writeBytes(employees.get(i).getSex()+"\n");
randomAccessFile.writeBytes(convertDateToString(employees.get(i).getDateOfBirth())+"\n");
randomAccessFile.writeBytes(employees.get(i).getDesignation()+"\n");
}
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println("EmployeeDAO[delete(int id)]:"+ioException);
}
}
}