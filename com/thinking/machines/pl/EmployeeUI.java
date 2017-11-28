package com.thinking.machines.pl;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.*;
import com.thinking.machines.dl.*;
public class EmployeeUI extends JFrame implements ActionListener,ListSelectionListener
{
private JTable employeesTable;
private EmployeePanel employeePanel;
private EmployeeModel employeeModel;
private JScrollPane jScrollPane;
private JButton addButton,editButton,cancelButton,deleteButton;
private JLabel recordCountCaption,recordCount;
private Container container;
private EmployeeDAO employeeDAO;
private int totalNumberOfRecords;
public enum uiModes{ADDMODE,EDITMODE,DELETEMODE,VIEWMODE};
private uiModes mode;
public EmployeeUI()
{
super("Employee Management");
employeeDAO=new EmployeeDAO();
initComponents();
setViewStage();
}
public void initComponents()
{
container=getContentPane();
employeePanel=new EmployeePanel();
employeeModel=new EmployeeModel();
employeesTable=new JTable(employeeModel);
employeesTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
TableColumn tc;
employeesTable.getColumnModel().getColumn(0).setPreferredWidth(30);
employeesTable.getColumnModel().getColumn(1).setPreferredWidth(30);
employeesTable.getColumnModel().getColumn(2).setPreferredWidth(100);
employeesTable.getColumnModel().getColumn(3).setPreferredWidth(30);
employeesTable.getColumnModel().getColumn(4).setPreferredWidth(30);
employeesTable.getColumnModel().getColumn(5).setPreferredWidth(100);
employeesTable.getSelectionModel().addListSelectionListener(this);
jScrollPane=new JScrollPane(employeesTable,ScrollPaneConstants.
VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.
HORIZONTAL_SCROLLBAR_ALWAYS);
Font font=new Font("Arial",Font.BOLD,14);
recordCountCaption=new JLabel("Records: ");
recordCountCaption.setFont(font);
recordCountCaption.setForeground(Color.black);
try
{
totalNumberOfRecords=employeeDAO.getCount();
}catch(DAOException daoException)
{
totalNumberOfRecords=0;
}
recordCount=new JLabel(String.valueOf(totalNumberOfRecords));
recordCount.setFont(font);
recordCount.setForeground(new Color(14,3,105));
addButton=new JButton("Add");
editButton=new JButton("Edit");
deleteButton=new JButton("Delete");
cancelButton=new JButton("Cancel");
addButton.addActionListener(this);
editButton.addActionListener(this);
deleteButton.addActionListener(this);
cancelButton.addActionListener(this);
container.setLayout(null);
recordCountCaption.setBounds(600,10,100,30);
recordCount.setBounds(705,10,100,30);
jScrollPane.setBounds(10,40,850,265);
employeePanel.setBounds(10,320,350,210);
addButton.setBounds(10,540,75,25);
editButton.setBounds(95,540,75,25);
deleteButton.setBounds(180,540,75,25);
cancelButton.setBounds(265,540,75,25);
container.add(recordCountCaption);
container.add(recordCount);
container.add(jScrollPane);
container.add(employeePanel);
container.add(addButton);
container.add(editButton);
container.add(deleteButton);
container.add(cancelButton);
Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
setSize(900,650);
setLocation((int)(dimension.width/2-(getWidth()/2)),(int)(dimension.height/2-(getHeight()/2)));
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void setViewStage()
{
employeesTable.setEnabled(true);
mode=uiModes.VIEWMODE;
employeePanel.disableAll();
addButton.setEnabled(true);
if(totalNumberOfRecords==0)
{
editButton.setEnabled(false);
deleteButton.setEnabled(false);
}
else
{
editButton.setEnabled(true);
deleteButton.setEnabled(true);
}
cancelButton.setEnabled(false);
addButton.setText("Add");
editButton.setText("Edit");
}
private void setAddModeStage()
{
employeesTable.setEnabled(false);
mode=uiModes.ADDMODE;
addButton.setText("Save");
editButton.setEnabled(false);
deleteButton.setEnabled(false);
cancelButton.setEnabled(true);
employeePanel.reset();
employeePanel.enableAll();
employeePanel.requestFocusOnID();
}
private void setEditModeStage()
{
employeesTable.setEnabled(false);
mode=uiModes.EDITMODE;
editButton.setText("Update");
addButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
employeePanel.enableAll();
employeePanel.disableID();
employeePanel.requestFocusOnName();
}
private void setDeleteStage()
{
employeesTable.setEnabled(false);
mode=uiModes.DELETEMODE;
deleteButton.setEnabled(false);
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
}
public void actionPerformed(ActionEvent event)
{
if(event.getSource()==addButton)
{
if(mode==uiModes.VIEWMODE)
{
setAddModeStage();
}
else
{
addEmployee();
}
}
if(event.getSource()==editButton)
{
if(mode==uiModes.VIEWMODE)
{
setEditModeStage();
}
else
{
updateEmployee();
}
}
if(event.getSource()==deleteButton)
{
if(employeesTable.getSelectedRow()==-1)
{
JOptionPane.showMessageDialog(this,"Select a row to delete");
return;
}
setDeleteStage();
int selected=JOptionPane.showConfirmDialog(this,"Are you sure?","Confirming delete operation",JOptionPane.YES_NO_OPTION);
if(selected==JOptionPane.YES_OPTION)
{
try 
{
employeeDAO.delete(employeePanel.getID());
employeeModel.changeNotify();
employeesTable.addNotify();
employeesTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Employee Deleted");
employeePanel.reset();
if(totalNumberOfRecords>0)
{
employeesTable.setRowSelectionInterval(0,0);
employeesTable.scrollRectToVisible(employeesTable.getCellRect(0,0,false));
valueChanged(null);
}
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
else
{
setViewStage();
}
}
if(event.getSource()==cancelButton)
{
valueChanged(null);
setViewStage();
}
}
private void addEmployee()
{
if(!employeePanel.validate(this))return;
EmployeeInterface employee=new Employee();
employee.setID(employeePanel.getID());
employee.setName(employeePanel.getName());
employee.setSex(employeePanel.getSex());
try
{
employee.setDateOfBirth(employeePanel.getDateOfBirth());
}catch(ParseException parseException)
{

}
employee.setDesignation(employeePanel.getDesignation());
try
{
employeeDAO.add(employee);
employeeModel.changeNotify();
employeesTable.addNotify();
employeesTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Employee Added");
int newRowIndex=employeeModel.getEmployeeIndex(employee.getID());
employeesTable.setRowSelectionInterval(newRowIndex,newRowIndex);
employeesTable.scrollRectToVisible(employeesTable.getCellRect(newRowIndex,0,false));
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
private void updateEmployee()
{
if(!employeePanel.validate(this))return;
EmployeeInterface employee=new Employee();
employee.setID(employeePanel.getID());
employee.setName(employeePanel.getName());
employee.setSex(employeePanel.getSex());
try
{
employee.setDateOfBirth(employeePanel.getDateOfBirth());
}catch(ParseException parseException)
{

}
employee.setDesignation(employeePanel.getDesignation());
try
{
employeeDAO.update(employee);
employeeModel.changeNotify();
employeesTable.addNotify();
employeesTable.repaint();
setTotalNumberOfRecords();
super.repaint();
JOptionPane.showMessageDialog(this,"Employee Update");
int newRowIndex=employeeModel.getEmployeeIndex(employee.getID());
employeesTable.setRowSelectionInterval(newRowIndex,newRowIndex);
employeesTable.scrollRectToVisible(employeesTable.getCellRect(newRowIndex,0,false));
setViewStage();
}catch(DAOException daoException)
{
JOptionPane.showMessageDialog(this,daoException.getMessage());
}
}
private void setTotalNumberOfRecords()
{
try
{
totalNumberOfRecords=employeeDAO.getCount();
}catch(DAOException daoException)
{
totalNumberOfRecords=0;
}
recordCount.setText(String.valueOf(totalNumberOfRecords));
}
public void valueChanged(ListSelectionEvent event)
{
int selectedRowIndex=employeesTable.getSelectedRow();
if(selectedRowIndex>=0)
{
EmployeeInterface employee=employeeModel.get(selectedRowIndex);
employeePanel.set(employee.getID(),employee.getName(),employee.getSex(),employee.getDateOfBirth(),employee.getDesignation());
}
else
{
employeePanel.reset();
}
}
}