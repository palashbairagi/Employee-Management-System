package com.thinking.machines.dl;

import java.sql.*;
import java.util.*;

public class EmployeeDAO implements EmployeeDAOInterface
{
private static Connection getConnection() throws DAOException
{
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
}catch(ClassNotFoundException classNotFoundException)
{
System.out.println("EmployeeDAO[Connection getConnection()]:"+classNotFoundException);
throw new DAOException("JdbcOdbcDriver not found");
}
Connection connection=null;
try
{
connection=DriverManager.getConnection("jdbc:odbc:tmdsn","thinkingmachines","thinkingmachines");
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO[Connection getConnection()]:"+sqlException);
throw new DAOException("Unable to connect using DSN:tmdsn");
}
return connection;
}
private java.util.Date convertSQLDateToUtilDate(java.sql.Date sqlDate)
{
return new java.util.Date(sqlDate.getYear(),sqlDate.getMonth(),sqlDate.getDate());
}
private java.sql.Date convertUtilDateToSQLDate(java.util.Date utilDate)
{
return new java.sql.Date(utilDate.getYear(),utilDate.getMonth(),utilDate.getDate());
}
public int getCount() throws DAOException
{
int count=0;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("EmployeeDAO[int getCount()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select count(*) as total_records from employee");
resultSet.next();
count=resultSet.getInt("total_records");
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO [int getCount()]:"+sqlException);
throw new DAOException("Unable to get record count.");
}
finally
{
try
{
if(!connection.isClosed())connection.close();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO [int getCount()]"+sqlException);
throw new DAOException("Unable to close connection to database.");
}
}
return count;
}
public boolean exists(int id)throws DAOException
{
int count=0;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("EmployeeDAO [boolean exists(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select count(*) as total_records from employee where id=?");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();
resultSet.next();
count=resultSet.getInt("total_records");
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO [boolean exists(int id)]:"+sqlException);
throw new DAOException("Unable to check existence");
}
finally
{
try
{
if(!connection.isClosed())connection.close();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO [boolean exists(int id)]:"+sqlException);
throw new DAOException("Unable to close connection to database");
}
}
return count!=0;
}
public EmployeeInterface get(int id)throws DAOException
{
EmployeeInterface employee=null;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("EmployeeDAO [EmployeeInterface get(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
ResultSet resultSet=null;
try
{
preparedStatement=connection.prepareStatement("select * from employee where id=?");
preparedStatement.setInt(1,id);
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
employee=new Employee();
employee.setID(id);
employee.setName(resultSet.getString("name").trim());
employee.setSex(resultSet.getString("sex").trim());
employee.setDateOfBirth(convertSQLDateToUtilDate(resultSet.getDate("date_of_birth")));
employee.setDesignation(resultSet.getString("designation").trim());
}
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO [boolean exists(int id)]:"+sqlException);
throw new DAOException("Unable to get record");
}
finally
{
try
{
if(!connection.isClosed())connection.close();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO[EmployeeInterface get(int id)]"+sqlException);
throw new DAOException("Unable to close connection to database");
}
}
if(employee==null)throw new DAOException("Invalid ID");
return employee;
}
public java.util.ArrayList<EmployeeInterface>get() throws DAOException
{
ArrayList<EmployeeInterface> employees=new ArrayList<EmployeeInterface>();
EmployeeInterface employee;
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("EmployeeDAO [ArrayList<EmployeeInterface> get()]:"+daoException);
throw daoException;
}
Statement statement=null;
ResultSet resultSet=null;
try
{
statement=connection.createStatement();
resultSet=statement.executeQuery("select * from employee");
while(resultSet.next())
{
employee=new Employee();
employee.setID(resultSet.getInt("id"));
employee.setName(resultSet.getString("name").trim());
employee.setSex(resultSet.getString("sex").trim());
employee.setDateOfBirth(convertSQLDateToUtilDate(resultSet.getDate("date_of_birth")));
employee.setDesignation(resultSet.getString("designation").trim());
employees.add(employee);
}
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO [ArrayList<EmployeeInterface>get()]:"+sqlException);
throw new DAOException("Unable to get record");
}
finally
{
try
{
if(!connection.isClosed())connection.close();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO[EmployeeInterface get(int id)]"+sqlException);
throw new DAOException("Unable to close connection to database");
}
}
if(employees.size()==0)throw new DAOException("No employees");
return employees;
}

public void add(EmployeeInterface employee) throws DAOException
{
if(exists(employee.getID()))throw new DAOException("ID exists");
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("EmployeeDAO [void add(EmployeeInterface employee)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("insert into employee values(?,?,?,?,?)");
preparedStatement.setInt(1,employee.getID());
preparedStatement.setString(2,employee.getName());
preparedStatement.setString(3,employee.getSex());
preparedStatement.setDate(4,convertUtilDateToSQLDate(employee.getDateOfBirth()));
preparedStatement.setString(5,employee.getDesignation());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO [void add(EmployeeInterface employee)]:"+sqlException);
throw new DAOException("Unable to insert record");
}
finally
{
try
{
if(!connection.isClosed())connection.close();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO[void add(EmployeeInterface employee)]"+sqlException);
throw new DAOException("Unable to close connection to database");
}
}
}



public void update(EmployeeInterface employee) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("EmployeeDAO [void update(EmployeeInterface employee)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("update employee set name=?,sex=?,date_of_birth=?,designation=? where id=?");
preparedStatement.setString(1,employee.getName());
preparedStatement.setString(2,employee.getSex());
preparedStatement.setDate(3,convertUtilDateToSQLDate(employee.getDateOfBirth()));
preparedStatement.setString(4,employee.getDesignation());
preparedStatement.setInt(5,employee.getID());
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO [void update(EmployeeInterface employee)]:"+sqlException);
throw new DAOException("Unable to update record");
}
finally
{
try
{
if(!connection.isClosed())connection.close();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO[void update(EmployeeInterface employee)]"+sqlException);
throw new DAOException("Unable to close connection to database");
}
}
}


public void delete(int id) throws DAOException
{
Connection connection;
try
{
connection=getConnection();
}catch(DAOException daoException)
{
System.out.println("EmployeeDAO [void delete(int id)]:"+daoException);
throw daoException;
}
PreparedStatement preparedStatement=null;
try
{
preparedStatement=connection.prepareStatement("delete from employee where id=?");
preparedStatement.setInt(1,id);
preparedStatement.executeUpdate();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO [void delete(int id)]:"+sqlException);
throw new DAOException("Unable to delete record");
}
finally
{
try
{
if(!connection.isClosed())connection.close();
}catch(SQLException sqlException)
{
System.out.println("EmployeeDAO[void delete(int id)]"+sqlException);
throw new DAOException("Unable to close connection to database");
}
}
}
}