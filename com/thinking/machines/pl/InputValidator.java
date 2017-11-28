package com.thinking.machines.pl;
import javax.swing.*;
import java.text.*;

public class InputValidator
{
public static boolean isTextFieldEmpty(JFrame container,JTextField
textField, String message,boolean displayMessage)
{
boolean empty=false;
if(textField.getText().trim().length()==0)
{
empty=true;
if(displayMessage)JOptionPane.showMessageDialog(container,message);
textField.requestFocus();
}
return empty;
}
public static boolean isInteger(JFrame container,JTextField textField
,String message,boolean displayMessage,boolean eraseIfInvalid)
{
boolean integer=true;
try
{
Integer.parseInt(textField.getText().trim());
}catch(NumberFormatException numberFormatException)
{
integer=false;
if(displayMessage)JOptionPane.showMessageDialog(container,message);
if(eraseIfInvalid)textField.setText("");
textField.requestFocus();
}
return integer;
}
public static boolean isValidDDMMYYYYDate(JFrame container,JTextField
textField,String message,boolean displayMessage,boolean eraseIfInvalid)
{
boolean valid=true;
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
try
{
java.util.Date date=simpleDateFormat.parse(textField.getText().trim());
String string=date.getDate()+"-"+(date.getMonth()+1)+"-"+(date.getYear()+1900);
valid=string.equals(textField.getText().trim());
}catch(ParseException parseException)
{
valid=false;
}
if(!valid)
{
if(displayMessage)JOptionPane.showMessageDialog(container,message);
if(eraseIfInvalid)textField.setText("");
textField.requestFocus();
}
return valid;
}
public static boolean isInValidRange(JFrame container,JTextField textField,long 
minimum, long maximum,String message,boolean displayMessage,boolean eraseIfInvalid)
{
boolean valid=true;
try
{
long x=Long.parseLong(textField.getText().trim());
if(x<minimum || x>maximum)valid=false;
}catch(NumberFormatException numberFormatException)
{
valid=false;
}
if(!valid)
{
if(displayMessage)JOptionPane.showMessageDialog(container,message);
if(eraseIfInvalid)textField.setText("");
textField.requestFocus();
}
return valid;
}
}