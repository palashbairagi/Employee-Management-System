import java.io.*;
import java.text.*;
import java.util.*;
import com.thinking.machines.dl.*;
class EmployeeManagementUI
{
private Console console;
private int id;
private String name,sex,designation;
private java.util.Date dateOfBirth;
private java.text.SimpleDateFormat simpleDateFormat;
EmployeeDAOInterface employeeDAO;
EmployeeManagementUI(Console console)
{
this.console=console;
simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
employeeDAO=new EmployeeDAO();
}
private boolean isConvertedDateValid(java.util.Date date,String string)
{
String dt=date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
return string.equals(dt);
}
private String convertDateToString(java.util.Date date)
{
return date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
}
public void addEmployee()
{
System.out.println("Employee(Add Module)");
try
{
id=Integer.parseInt(console.readLine("Enter ID: "));
}catch(NumberFormatException numberFormatException)
{
System.out.println("ID.should be an integer");
return;
}
name=console.readLine("Enter name: ");
String stringDateOfBirth;
stringDateOfBirth=console.readLine("Enter date of birth(dd-mm-yyyy): ");
try
{
dateOfBirth=simpleDateFormat.parse(stringDateOfBirth);
if(!isConvertedDateValid(dateOfBirth,stringDateOfBirth))
{
System.out.println("Date should be in dd-mm-yyyy format.");
return;
}
}catch(ParseException parseException)
{
System.out.println("Date should be in dd-mm-yyyy format.");
return;
}
sex=console.readLine("Enter sex (M/F): ");
sex=sex.toUpperCase();
if(sex.equals("M")) sex="MALE";
else if(sex.equals("F")) sex="FEMALE";
else
{
System.out.println("Invalid sex");
return;
}
designation=console.readLine("Enter designation :");
String save="";
while(true)
{
save=console.readLine("Save(Y/N): ");
save=save.toUpperCase();
if("YN".indexOf(save)!=-1)break;
}
if(save.equals("Y"))
{
EmployeeInterface employee=new Employee();
employee.setID(id);
employee.setName(name);
employee.setSex(sex);
employee.setDateOfBirth(dateOfBirth);
employee.setDesignation(designation);
try
{
employeeDAO.add(employee);
System.out.println("Record Saved");
}catch(DAOException daoexception)
{
System.out.println("daoexception");
}
}
else
{
System.out.println("Record not saved");
}
}
public void updateEmployee()
{
System.out.println("Employee(Update Module)");
try
{
id=Integer.parseInt(console.readLine("Enter ID :"));
}catch(NumberFormatException numberFormatException)
{
System.out.println("ID.should be an integer");
return;
}
try
{
EmployeeInterface employee=employeeDAO.get(id);
System.out.println("Name :"+employee.getName());
System.out.println("Sex :"+employee.getSex());
System.out.println("Date of birth :"+convertDateToString(employee.getDateOfBirth()));
System.out.println("Designation :"+employee.getDesignation());
}catch(DAOException daoException)
{
System.out.println(daoException);
return;
}
String edit="";
while(true)
{
edit=console.readLine("Edit(Y/N):");
edit=edit.toUpperCase();
if("YN".indexOf(edit)!=-1)break;
}
if(edit.equals("Y"))
{
name=console.readLine("Enter name :");
String stringDateOfBirth;
stringDateOfBirth=console.readLine("Enter date of birth (dd-mm-yyyy) :");
try
{
dateOfBirth=simpleDateFormat.parse(stringDateOfBirth);
if(!isConvertedDateValid(dateOfBirth,stringDateOfBirth))
{
System.out.println("Date should be in dd-mm-yyyy format.");
System.out.println("Record not updated");
return;
}
}catch(ParseException parseException)
{
System.out.println("Date should be in dd-mm-yyyy format.");
System.out.println("Record not updated");
return;
} 
sex=console.readLine("Enter sex (M/F):");
sex=sex.toUpperCase();
if(sex.equals("M")) sex="MALE";
else if(sex.equals("F")) sex="FEMALE";
else
{
System.out.println("Invalid sex");
System.out.println("Record not updated");
return;
}
designation=console.readLine("Enter designation :");
String update="";
while(true)
{
update=console.readLine("Update (Y/N) :");
update=update.toUpperCase();
if("YN".indexOf(update)!=-1) break;
}
if(update.equals("Y"))
{
EmployeeInterface employee=new Employee();
employee.setID(id);
employee.setName(name);
employee.setSex(sex);
employee.setDateOfBirth(dateOfBirth);
employee.setDesignation(designation);
try
{
employeeDAO.update(employee);
System.out.println("Record updated");
}catch(DAOException daoException)
{
System.out.println(daoException);
}
}
else
{
System.out.println("Record not modified");
return;
}
}
}
public void deleteEmployee()
{
System.out.println("Employee(Delete Module)");
try
{
id=Integer.parseInt(console.readLine("Enter ID :"));
}catch(NumberFormatException numberFormatException)
{
System.out.println("ID. should be an integer");
return;
}
try
{
EmployeeInterface employee=employeeDAO.get(id);
System.out.println("Name :"+employee.getName());
System.out.println("Sex :"+employee.getSex());
System.out.println("Date of birth :"+convertDateToString(employee.getDateOfBirth()));
System.out.println("Designation :"+employee.getDesignation());
String delete="";
while(true)
{
delete=console.readLine("Delete (Y/N) :");
delete=delete.toUpperCase();
if("YN".indexOf(delete)!=-1) break;
}
if(delete.equals("Y"))
{
employeeDAO.delete(id);
System.out.println("Record deleted");
}
else
{
System.out.println("Record not deleted");
}
}catch(DAOException daoException)
{
System.out.println(daoException);
}
}
public void displayEmployee()
{
System.out.println("Employee(Display Module)");
try
{
id=Integer.parseInt(console.readLine("Enter ID :"));
}catch(NumberFormatException numberFormatException)
{
System.out.println("ID. should be an integer");
return;
}
try
{
EmployeeInterface employee=employeeDAO.get(id);
System.out.println("Name :"+employee.getName());
System.out.println("Sex :"+employee.getSex());
System.out.println("Date of birth :"+convertDateToString(employee.getDateOfBirth()));
System.out.println("Designation :"+employee.getDesignation());
}catch(DAOException daoException)
{
System.out.println(daoException);
}
}
public void displayAllEmployees()
{
ArrayList<EmployeeInterface>employees;
System.out.println("Employee (List Module)");
try
{
employees=employeeDAO.get();
}catch(DAOException daoException)
{
System.out.println(daoException);
return;
}
EmployeeInterface employee;
for(int e=0;e<employees.size();e++)
{
employee=employees.get(e);
System.out.println("ID. :"+employee.getID());
System.out.println("Name :"+employee.getName());
System.out.println("Sex :"+employee.getSex());
System.out.println("Date of birth :"+convertDateToString(employee.getDateOfBirth()));
System.out.println("Designation :"+employee.getDesignation());
console.readLine("Press enter to continue...");
}
}
}
class example54psp
{
public static void main(String gg[])
{
Console console=System.console();
EmployeeManagementUI employeeManagementUI;
employeeManagementUI=new EmployeeManagementUI(console);
String choice;
while(true)
{
System.out.println("Employee Master Data Management");
System.out.println("Options");
System.out.println("1. Add");
System.out.println("2. Edit");
System.out.println("3. Delete");
System.out.println("4. Display one employee");
System.out.println("5. List of all employees");
System.out.println("6. Exit");

choice=console.readLine("Enter your choice(1-5) :");
if("123456".indexOf(choice)==-1)
{
System.out.println("Invalid choice");
continue;
}
if(choice.equals("1")) employeeManagementUI.addEmployee();
if(choice.equals("2")) employeeManagementUI.updateEmployee();
if(choice.equals("3")) employeeManagementUI.deleteEmployee();
if(choice.equals("4")) employeeManagementUI.displayEmployee();
if(choice.equals("5")) employeeManagementUI.displayAllEmployees();
if(choice.equals("6")) break;
}
console.printf("\n\n\n\t\tThank you for using this application");
}
}