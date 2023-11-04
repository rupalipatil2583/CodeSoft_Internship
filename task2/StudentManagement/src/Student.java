//Student Management System1
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Student {
    private JPanel main;
    private JTextField name;
    private JTextField rno;
    private JTextField cls;
    private JTextField mark;
    private JButton add;
    private JTable table1;
    private JButton update;
    private JButton search;
    private JButton del;
    private JTextField txtRoll;
    private JScrollPane scrollpane;
    private JButton clear;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student");
        frame.setContentPane(new Student().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    //connection
    PreparedStatement ps;
    Connection con;
    Statement stmt;
    ResultSet rs;

    public void connect() {
        try
            {
            //load driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //establish connection
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "System", "rupali123");
            System.out.println("connection done");
            stmt = con.createStatement();

            } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            }
    }

    public Student() {
       createTable();
        main.setSize(600,800);
        //txtRoll.setToolTipText("Enter Roll No to search record");
        connect();
    add.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nm, clas;
            int roll;
            float marks;

            nm = name.getText();
            clas = cls.getText();
            roll = Integer.parseInt(rno.getText());
            marks = Float.parseFloat(mark.getText());


            try{
               ps=con.prepareStatement("insert into STUDENTMANAGEMENT(roll_no,name,class,marks)values(?,?,?,?)");
                ps.setInt(1,roll);
                ps.setString(2,nm);
                ps.setString(3,clas);
                ps.setFloat(4,marks);
                ps.executeUpdate();



                if(name.getText().equals("")|| rno.getText().equals("")|| cls.getText().equals("")|| mark.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Please enter all data");
                }
                else {
                    String[] data={rno.getText(),name.getText(),cls.getText(),mark.getText() };
                    DefaultTableModel tbmodel=(DefaultTableModel)table1.getModel();
                    tbmodel.addRow(data);       //row added
                    JOptionPane.showMessageDialog(null,"Record Added");
                    name.setText("");
                    //cls.setText("");
                    rno.setText("");
                    mark.setText("");
                    name.requestFocus();
                }
            }
            catch (Exception exception)
            {

            }
        }
    });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nm, clas;
                int roll;
                float marks;

                nm = name.getText();
                clas = cls.getText();
                roll = Integer.parseInt(rno.getText());
                marks = Float.parseFloat(mark.getText());


                try{
                    ps=con.prepareStatement("Update STUDENTMANAGEMENT set name=?,class=?,marks=? where roll_no=?");

                    ps.setString(1,nm);
                    ps.setString(2,clas);
                    ps.setFloat(3,marks);
                    ps.setInt(4,roll);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Updated");


                    if(name.getText().equals("")|| rno.getText().equals("")|| cls.getText().equals("")|| mark.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please enter all data");
                    }
                    else {
                        String[] data={rno.getText(),name.getText(),cls.getText(),mark.getText() };
                        DefaultTableModel tbmodel=(DefaultTableModel)table1.getModel();
                        //tbmodel.addRow(data);//row added
                        name.setText("");
                        //cls.setText("");
                        rno.setText("");
                        mark.setText("");
                        name.requestFocus();
                    }
                }
                catch (Exception exception)
                {

                }

            }
        });
        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nm, clas;
                int roll;
                float marks;

                nm = name.getText();
                clas = cls.getText();
                roll = Integer.parseInt(rno.getText());
                marks = Float.parseFloat(mark.getText());

                try
                {
                    ps=con.prepareStatement("delete from STUDENTMANAGEMENT where roll_no= "+roll);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record deleted");
                    name.setText("");
                    //cls.setText("");
                    rno.setText("");
                    mark.setText("");
                    name.requestFocus();

                }
                catch(Exception e1)
                {

                }

            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    String rn=txtRoll.getText();
                    ps=con.prepareStatement("select roll_no,name,class,marks from STUDENTMANAGEMENT where roll_no=?");
                    ps.setString(1,rn);
                    rs=ps.executeQuery();
                    if(rs.next())
                    {
                        String rno1=rs.getString(1);
                        String nm=rs.getString(2);
                        String cs=rs.getString(3);
                        String mk=rs.getString(4);


                        rno.setText(rno1);
                        name.setText(nm);
                        cls.setText(cs);
                        mark.setText(mk);

                    }
                    else {
                        name.setText("");
                       // cls.setText("");
                        rno.setText("");
                        mark.setText("");
                        txtRoll.setText("");
                        JOptionPane.showMessageDialog(null,"invalid roll number");
                    }

                }
                catch (Exception e2)
                {

                }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name.setText("");
                //cls.setText("");
                rno.setText("");
                mark.setText("");
                txtRoll.setText("");
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
   private void createTable()
    {
        Object[][] data={

        };
        table1.setModel(new DefaultTableModel(
                data,new String[]{"Roll_No","Name","Class","Mark"}
        ));
    }





}
