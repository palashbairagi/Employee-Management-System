package com.thinking.machines.pl;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import javax.swing.border.*;
public class EmployeePanel extends JPanel
{
private JLabel idCaption,nameCaption,sexCaption;
private JLabel dateOfBirthCaption,designationCaption;
private JTextField id,name,dateOfBirth;
private JTextField nonEditableSex,nonEditableDesignation;
private JComboBox designation;
private JRadioButton male,female;
private ButtonGroup sex;
private SimpleDateFormat simpleDateFormat;
private TitledBorder titledBorder;
private Color enabledColor=Color.black;
private Color disabledColor=Color.red;
private JPanel sexPanel;
public EmployeePanel()
{
initComponents();
}
public void initComponents()
{
simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
idCaption=new JLabel("ID.");
nameCaption=new JLabel("Name");
sexCaption=new JLabel("Sex");
dateOfBirthCaption=new JLabel("Date of birth(dd-mm-yyyy)");
designationCaption=new JLabel("Designation");
id=new JTextField(10);
name=new JTextField(50);
dateOfBirth=new JTextField(12);
designation=new JComboBox();
designation.addItem("Manager");
designation.addItem("Security Officer");
designation.addItem("Clerk");
designation.addItem("Accountant");
designation.addItem("Receptionist");
male=new JRadioButton("Male");
female=new JRadioButton("Female");
male.setSelected(true);
sex=new ButtonGroup();
sex.add(male);
sex.add(female);
nonEditableDesignation=new JTextField(25);
nonEditableSex=new JTextField(10);
nonEditableDesignation.setEditable(false);
nonEditableSex.setEditable(false);
titledBorder=BorderFactory.createTitledBorder("Details");
setBorder(titledBorder);
setLayout(null);
sexPanel=new JPanel();
idCaption.setBounds(10,20,100,25);
id.setBounds(50,20,100,25);
nameCaption.setBounds(10,55,100,25);
name.setBounds(50,55,250,25);
sexCaption.setBounds(10,90,100,25);
sexPanel.setBounds(50,90,150,25);
dateOfBirthCaption.setBounds(10,125,160,25);
dateOfBirth.setBounds(175,125,100,25);
designationCaption.setBounds(10,160,100,25);
designation.setBounds(100,160,200,25);
nonEditableDesignation.setBounds(100,160,200,25);
nonEditableSex.setBounds(50,90,150,25);
add(idCaption);
add(id);
add(nameCaption);
add(name);
add(sexCaption);
sexPanel.setLayout(new GridLayout(1,2));
sexPanel.add(male);
sexPanel.add(female);
add(sexPanel);
add(dateOfBirthCaption);
add(dateOfBirth);
add(designationCaption);
add(designation);
add(nonEditableSex);
add(nonEditableDesignation);
}
private boolean isConvertedDateValid(java.util.Date date,String string)
{
String dt=date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
return string.equals(dt);
}
public int getID()
{
return Integer.parseInt(id.getText().trim());
}
public void setID(int id)
{
this.id.setText(String.valueOf(id));
}
public String getName()
{
return name.getText().trim();
}
public void setName(String name)
{
this.name.setText(name);
}
public String getSex()
{
nonEditableSex.setText((male.isSelected())?"Male":"Female");
return(male.isSelected())?"Male":"Female";
}
public void setSex(String sex)
{
sex=sex.toUpperCase();
if(sex.equals("MALE"))
{
male.setSelected(true);
female.setSelected(false);
nonEditableSex.setText("Male");
}
else
{
male.setSelected(false);
female.setSelected(true);
nonEditableSex.setText("Female");
}
}
public Date getDateOfBirth() throws ParseException
{
java.util.Date dateOfBirth=simpleDateFormat.parse(this.dateOfBirth.getText().trim());
if(isConvertedDateValid(dateOfBirth,this.dateOfBirth.getText().trim()))
{
return dateOfBirth;
}
else
{
throw new ParseException("Invalid date of birth",0);
}
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth.setText(dateOfBirth.getDate()+"-"+(dateOfBirth.getMonth()+1)
+"-"+(dateOfBirth.getYear()+1900));
}
public String getDesignation()
{
nonEditableDesignation.setText(designation.getSelectedItem().toString());
return designation.getSelectedItem().toString();
}
public void setDesignation(String designation)
{
nonEditableDesignation.setText(designation);
this.designation.setSelectedItem(designation);
}
public void requestFocusOnID()
{
id.requestFocus();
}
public void requestFocusOnName()
{
name.requestFocus();
}
public void reset()
{
id.setText("");
name.setText("");
male.setSelected(true);
female.setSelected(false);
dateOfBirth.setText("");
designation.setSelectedIndex(0);
}
public void set(int id,String name,String sex,java.util.Date dateOfBirth,String designation)
{
setID(id);
setName(name);
setSex(sex);
setDateOfBirth(dateOfBirth);
setDesignation(designation);
}
public void enableAll()
{
id.setEditable(true);
name.setEditable(true);
sexPanel.setVisible(true);
nonEditableSex.setVisible(true);
dateOfBirth.setEditable(true);
designation.setVisible(true);
nonEditableDesignation.setVisible(false);
super.repaint();
}
public void disableAll()
{
id.setEditable(false);
name.setEditable(false);
sexPanel.setVisible(false);
nonEditableSex.setVisible(true);
dateOfBirth.setEditable(false);
designation.setVisible(false);
nonEditableDesignation.setVisible(true);
super.repaint();
}
public void disableID()
{
id.setEditable(false);
}
public void enableID()
{
id.setEditable(false);
}
public boolean validate(EmployeeUI employeeUI)
{
if(id.isEnabled())
{
if(InputValidator.isTextFieldEmpty(employeeUI,id,"ID. required",true))return false;
if(!InputValidator.isInteger(employeeUI,id,"ID. should be a number",true,false))return false;
if(!InputValidator.isInValidRange(employeeUI,id,1,99999,"ID. should be in between (1-99999)",true,false))return false;
}
if(InputValidator.isTextFieldEmpty(employeeUI,name,"Name required",true))return false;
if(InputValidator.isTextFieldEmpty(employeeUI,dateOfBirth,"Date of birth required",true))return false;
if(!InputValidator.isValidDDMMYYYYDate(employeeUI,dateOfBirth,"Invalid date of birth",true,false))return false;
return true;
}
}