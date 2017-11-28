package com.thinking.machines.dl;
public interface EmployeeDAOInterface
{
public void add(EmployeeInterface employee) throws DAOException;
public void update(EmployeeInterface employee) throws DAOException; 
public void delete(int id) throws DAOException;
public EmployeeInterface get(int id) throws DAOException;
public java.util.ArrayList<EmployeeInterface> get() throws DAOException;
public int getCount() throws DAOException;
public boolean exists(int id) throws DAOException;
}