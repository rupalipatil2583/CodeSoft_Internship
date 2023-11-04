import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class AddressBook {
    private JTextField name1;
    private JTextField zip1;
    private JTextField phone1;
    private JTextField email1;
    private JButton add;
    private JButton edit;
    private JButton search;
    private JButton display;
    private JButton clear;
    private JLabel head;
    private JLabel name;
    private JLabel phone;
    private JLabel email;
    private JLabel zip;
    private JPanel main;

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("AddressBook");
        frame.setContentPane(new AddressBook().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600,400);
        //frame.isResizable();
        frame.setVisible(true);
    }
    //connection
    PreparedStatement ps;
    Connection con;
    Statement stmt;
    ResultSet rs;

    // 2 dimension array to hold table contents
    // it holds temp values for now
    Object rowData[][] = {{"Row1-Column1", "Row1-Column2", "Row1-Column3"}};
    // array to hold column names
    Object columnNames[] = {"Column One", "Column Two", "Column Three"};

    // create a table model and table based on it
    DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
    JTable table = new JTable(mTableModel);

    public void create_connection()
    {
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
    public AddressBook() {

        create_connection();
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String nm=name1.getText();
                    String phno=phone1.getText();
                    String email=email1.getText();
                    int zip=Integer.parseInt(zip1.getText());
                    try
                    {
                        ps=con.prepareStatement("insert into ADDRESSBOOK(name,phone,email,zip)values(?,?,?,?)");
                        ps.setString(1,nm);
                        ps.setString(2,phno);
                        ps.setString(3,email);
                        ps.setInt(4,zip);
                        ps.executeUpdate();




                        if(name1.getText().equals("")|| phone1.getText().equals("")|| email1.getText().equals("")|| zip1.getText().equals(""))
                        {
                            JOptionPane.showMessageDialog(null,"Please enter all data");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Record Added");
                            name1.setText("");
                            phone1.setText("");
                            email1.setText("");
                            zip1.setText("");
                            name1.requestFocus();
                        }


                    }
                    catch (Exception e1)
                    {
                        System.out.println(e1);
                    }


            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nm=name1.getText();
                String phno=phone1.getText();
                String email=email1.getText();
                int zip=Integer.parseInt(zip1.getText());
                try
                {
                    ps=con.prepareStatement("Update AddressBook set name=?,phone=?,zip=? where email=?");
                    ps.setString(1,nm);
                    ps.setString(2,phno);
                    ps.setInt(3,zip);
                    ps.setString(4,email);
                    ps.executeUpdate();




                    if(name1.getText().equals("")|| phone1.getText().equals("")|| email1.getText().equals("")|| zip1.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please enter all data");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Record updated");

                        name1.setText("");
                        phone1.setText("");
                        email1.setText("");
                        zip1.setText("");
                        name1.requestFocus();
                    }


                }
                catch (Exception e1)
                {
                    System.out.println(e1);
                }


            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    String email=email1.getText();
                    ps=con.prepareStatement("select name,phone,email,zip from AddressBook where email=?");
                    ps.setString(1,email);
                    rs=ps.executeQuery();
                    if(rs.next())
                    {
                        String name=rs.getString(1);
                        String phone=rs.getString(2);
                        String emaill=rs.getString(3);
                        String zip=rs.getString(4);


                        name1.setText(name);
                        phone1.setText(phone);
                        email1.setText(emaill);
                        zip1.setText(zip);

                    }
                    else {

                        name1.setText("");
                        phone1.setText("");
                        email1.setText("");
                        zip1.setText("");
                        JOptionPane.showMessageDialog(null,"invalid email ");
                    }

                }
                catch (Exception e2)
                {

                }
                }
        });
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    // 2 dimension array to hold table contents
                    // it holds temp values for now
                    Object rowData[][] = {{"Row1-Column1", "Row1-Column2", "Row1-Column3"}};
                    // array to hold column names
                    Object columnNames[] = {"name", "phone", "email", "zip"};

                    // create a table model and table based on it
                    DefaultTableModel mTableModel = new DefaultTableModel(rowData, columnNames);
                    JTable table = new JTable(mTableModel);
                    // run the desired query
                    String query = "SELECT name, phone, email,zip FROM AddressBook";
                    // make a statement with the server
                    stmt = con.createStatement();
                    // execute the query and return the result
                    rs = stmt.executeQuery(query);

                    // remove the temp row
                    mTableModel.removeRow(0);

                    // create a temporary object array to hold the result for each row
                    Object[] rows;
                    // for each row returned
                    while (rs.next()) {
                        // add the values to the temporary row
                        rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)};
                        // add the temp row to the table
                        mTableModel.addRow(rows);
                    }
                    JFrame new1=new JFrame("Records");
                    new1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    JScrollPane scrollPane = new JScrollPane(table);
                    new1.add(scrollPane, BorderLayout.CENTER);
                    new1.setSize(700, 400);
                    new1.setVisible(true);
                }
                 catch (Exception exception)
                 {

                 }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name1.setText("");
                phone1.setText("");
                email1.setText("");
                zip1.setText("");
                name1.requestFocus();
            }
        });



    }



}
