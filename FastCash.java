
package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener{
    
    JButton fastcash1,fastcash2,fastcash3,fastcash4,fastcash5,fastcash6,back;
    String pinnumber;
    
    FastCash(String pinnumber){
        this.pinnumber=pinnumber;
        
        setLayout(null);
        
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2=i1.getImage().getScaledInstance(900,900,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel image=new JLabel(i3);
        image.setBounds(0,0,900,900);
        add(image);
        
        JLabel text=new JLabel("SELECT WITHDRAWL AMOUNT");
        text.setBounds(210,300,700,35);
        text.setForeground(Color.white);
        text.setFont(new Font("System",Font.BOLD,16)); 
        image.add(text);
        
        fastcash1=new JButton("Rs 100");
        fastcash1.setBounds(170,415,150,30);
        fastcash1.addActionListener(this);
        image.add(fastcash1);
        
        fastcash2=new JButton("Rs 500");
        fastcash2.setBounds(355,415,150,30);
        fastcash2.addActionListener(this);
        image.add(fastcash2);
        
        fastcash3=new JButton("Rs 1000");
        fastcash3.setBounds(170,450,150,30);
        fastcash3.addActionListener(this);
        image.add(fastcash3);
        
        fastcash4=new JButton("Rs 2000");
        fastcash4.setBounds(355,450,150,30);
        fastcash4.addActionListener(this);
        image.add(fastcash4);
        
        fastcash5=new JButton("Rs 5000");
        fastcash5.setBounds(170,485,150,30);
        fastcash5.addActionListener(this);
        image.add(fastcash5);
        
        fastcash6=new JButton("Rs 10000");
        fastcash6.setBounds(355,485,150,30);
        fastcash6.addActionListener(this);
        image.add(fastcash6);
        
        back=new JButton(" Back");
        back.setBounds(355,520,150,30);
        back.addActionListener(this);
        image.add(back);
        
        
        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==back){
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        }else {
            String amount = ((JButton)ae.getSource()).getText().substring(3);
            Conn c = new Conn();
            try{
                ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pinnumber+"'");
                int balance = 0;
                while(rs.next()){
                    if(rs.getString("type").equals("Deposit")){
                        balance += Integer.parseInt(rs.getString("amount"));
                    }else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }
                
                if(ae.getSource() != back && balance < Integer.parseInt(amount)){
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }
                
                Date date = new Date();
                String query = "insert into bank values('"+pinnumber+"', '"+date+"', 'Withdrawl', '"+amount+"')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Rs "+ amount + " Debited Successfully");
                
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);
            }catch(Exception e){
                System.out.println(e);
            }
        
        }
        
    }
    
    public static void main(String[] args) {
        new FastCash("");
    }
}
