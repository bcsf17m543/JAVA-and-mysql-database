import java.sql.*;
import java.util.*;
import javax.swing.*;



class Data
{
    private String fname;
    private String lname;
    private String password;
    private String email;
    private String gender;
    private String dob;
    
    public Data()
    {
        fname="";
        lname="";
        password="";
        email="";
        gender="";
        dob="";
    }
    public void Menu(Statement st)
    {
        String input; 
        int ch; 
        boolean var=false;
        while (true) 
        { 
            input = JOptionPane.showInputDialog(" Enter 1 to Create Account " + "\n Enter 2 to Signin \n Enter 3 to Update Profile" + "\n Enter 4 to Exit"); 
            ch = Integer.parseInt(input); 
            switch (ch) 
            { 
                case 1: 
                    CreateAccount(st);
                break; 
                 
                case 2: 
                    Signin(st,var);
                break; 

                case 3: 
                    UpdateProfile(st);
                break; 
 
                case 4: 
                System.exit(0); 
            }
        }
       
    }
    public boolean Correct(String eml)
    {
        String reg = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return eml.matches(reg);
    }
    public boolean AlreadyExists(String eml,Statement st)
    {
        boolean ans=false;
        try{
        String query="Select * from Mytable where Email='"+eml+"' ";   
        ResultSet rs = st.executeQuery(query);
        if(rs.next()){
            ans= true;
     
           }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            ans= false;
        }
        return ans;
        
    }
    public void CreateAccount(Statement st)
    {
        String input;
        int num=0;
        input=JOptionPane.showInputDialog("Enter first name: ");
        fname=input;
        input=JOptionPane.showInputDialog("Enter last name: ");
        lname=input;
        input=JOptionPane.showInputDialog("Enter password: (1-10 characters)"); //kaam baki h agr time mila
        password=input;
        input=JOptionPane.showInputDialog("Enter DOB: "); //kaam baki h agr time mila
        dob=input;
        input=JOptionPane.showInputDialog("Enter Gender: "); //kaam baki h agr time mila
        gender=input;
        while(true)
        {
            input=JOptionPane.showInputDialog("Enter Email: ");
            email=input;
            if(Correct(email))
            {
                if(AlreadyExists(email,st))
                {
                    JOptionPane.showMessageDialog(null,"An account for this email already exists.");
                    num=-1;
                    continue;
                }
                else
                {
                    num=1;
                    break;
                }
               
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Enter a valid Email.");
            }
           
        }
        if(num>0)
        {   try{
            String query="insert into Mytable (First_Name,Last_Name,Email,Pass,Gender,DOB) values('"+fname+"', '"+lname+"', '"+email+"', '"+password+"' , '"+gender+"','"+dob+"' )";
            int rs = st.executeUpdate( query );
            if(rs>0)
            {
                JOptionPane.showMessageDialog(null,"Record inserted successfully:)");
            }
            else{
                JOptionPane.showMessageDialog(null,"Error while inserting:(");
            }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
               
            }
           
        }


    }
    public void Signin(Statement st,boolean var)
    {
        String inp,a,b;
        inp =JOptionPane.showInputDialog("Enter email: ");
        a=inp;
        inp=JOptionPane.showInputDialog("Enter Password: ");
        b=inp;
        boolean ans=false;
        try{
        String query="Select * from Mytable where Email='"+a+"' and Pass='"+b+"' ";   
        ResultSet rs = st.executeQuery(query);
        if(rs.next()){
            ans= true;
     
           }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            ans= false;
        }
        if(ans)
        {
            JOptionPane.showMessageDialog(null,"Login Sucessfull:)");
            var=true;
        }
        else{
            JOptionPane.showMessageDialog(null,"incorrect email or password:)");
        }
    }
  
  

    public void UpdateCred(Statement st,int num)
    {
        
        boolean ans=false;
        String sat,p,s;
        sat=JOptionPane.showInputDialog("Enter value: ");
        p=sat;
        sat=JOptionPane.showInputDialog("Enter Email: ");
        s=sat;
        if(Correct(s))
        {
            if(AlreadyExists(s,st))
            {            
            String query=UpdateWaliQuery(num,p,s);
            try{
                
                int rs = st.executeUpdate(query);
                if(rs>0){
                    ans= true;
             
                   }
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                    ans= false;
                }
                if(ans)
                {
                    JOptionPane.showMessageDialog(null,"Updated  Sucessfully:)");
                    
                }
                else{
                    JOptionPane.showMessageDialog(null,"incorrect credential entered:)");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Email doesnot exist");
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Enter a valid email");
        }

    }
    public String UpdateWaliQuery(int num,String p,String s)
    {   String myquery="Update Mytable set Last_Name= '"+p+"' where Email='"+s+"'";
        switch(num)
        {
            case 1:
            myquery="Update Mytable set First_Name= '"+p+"' where Email='"+s+"'";
            break;

            case 2:
            myquery="Update Mytable set Last_Name= '"+p+"' where Email='"+s+"'";
            break;

            case 3:
            myquery="Update Mytable set Pass= '"+p+"' where Email='"+s+"'";
            break;

            case 4:
                myquery="Update Mytable set Gender= '"+p+"' where Email='"+s+"'";
            break;

            case 5:
             myquery="Update Mytable set DOB= '"+p+"' where Email='"+s+"'";
            break;

        }
        return myquery;
        
    }
    public void UpdateProfile(Statement st)
    {
        String input;
        int ch; 
        while (true) 
        { 
            input = JOptionPane.showInputDialog(" Enter 1 to update Firstname " + "\n Enter 2 to update lastname."+"\n Enter 3 to Update password." +"\n Enter 4 to update Gender."+"\n Enter 5 to update DOB"+"\n Enter 6 to Go back."); 
            ch = Integer.parseInt(input);
            switch (ch) 
            { 
                case 1: 
                    UpdateCred(st,1);
                break; 
                 
                case 2: 
                    UpdateCred(st,2);
                break; 

                case 3: 
                    UpdateCred(st,3);
                break; 
                 
                case 4: 
                    UpdateCred(st,4);
                break;
                
                case 5:
                    UpdateCred(st,5);
                break;

                case 6:
                return;

                
            }
 
        }
    
    }
}
class Signup
{
   
    public static void main(String args[]) throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost/signup";
        Connection con=DriverManager.getConnection(url,"root","root");
        Statement st=con.createStatement();
        Data d= new Data();
        d.Menu(st);   
    }
}