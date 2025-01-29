/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cp2project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author ahmet
 */
public class DatabaseManagement {

    private static final String connection = "jdbc:mysql://localhost:3306/emaildb?user=root&password=1234";

    //Tests connection of Database and java connector
    public static boolean TestConnection() {

        boolean rvalue = true;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connection);

        } catch (SQLException ex) {
            rvalue = false;
            System.out.println(" error:" + ex.getMessage());
        }
        return rvalue;
    }

    //Takes an email variable and searchs on userinfo table then returns that user
    public static ResultSet SearchByEmail(String email) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM userinfo WHERE mail = '" + email + "'";

            rset = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }

        return rset;
    }

    //Searchs for all mails on mailinfo table regardless its other attiribuates
    public static ResultSet SearchAllMails() {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            //like ile değil eşiittir ile karşılaştır ve passwordu da burdan ara
            String query = "SELECT * FROM mailinfo";
            //mail arama özelliği ve taslak olarak kaydetme
            //databasede userinfolar gereksiz

            rset = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //Searchs for draft mails on mailinfo table and return that mail
    public static ResultSet SearchAllMailsIsDraft() {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            //like ile değil eşiittir ile karşılaştır ve passwordu da burdan ara
            String query = "SELECT * FROM mailinfo WHERE is_draft = '" + 1 + "'";
            //mail arama özelliği ve taslak olarak kaydetme
            //databasede userinfolar gereksiz

            rset = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //Searches mails by it is mail content and returns that mail
    public static ResultSet SearchAllMailsByText(String text) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE text LIKE '%" + text + "%'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //Searches users if its email and password match
    public static ResultSet SearchByEmailPassword(String email, String password) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            //like ile değil eşiittir ile karşılaştır ve passwordu da burdan ara
            String query = "SELECT * FROM userinfo WHERE mail = '" + email + "'";
            //mail arama özelliği ve taslak olarak kaydetme
            //databasede userinfolar gereksiz

            ResultSet rset2 = stmt.executeQuery(query);
            while (rset2.next()) {
                if (rset2 != null && rset2.getString("password").equals(password)) {
                    rset = rset2;
                }
            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //Takes necessary vairables and adds user on userinfo table
    public static boolean AddUser(String name, String surname, String birth_date, int gender, String email, String password) {
        boolean value = true;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connection);
            //connection successfull
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO userinfo (name, surname, birth_date, gender, mail, password,role) "
                    + "VALUES('" + name + "', '" + surname + "', '" + birth_date + "', '" + gender + "', '" + email + "', '" + password + "', '" + "regular" + "')";

            stmt.executeUpdate(query);
            conn.close();
        } catch (SQLException ex) {
            value = false;
            System.out.println(" error:" + ex.getMessage());
        }
        return value;
    }

    //Searches mails by where it sended
    public static ResultSet SearchByToMail(String email) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE tomail = '" + email + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //Searches mails by where it from sended
    public static ResultSet SearchByFromMail(String email) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE frommail = '" + email + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //Searches mails by where it sended but method is also look for if it is draft or not
    public static ResultSet SearchByFromMail(String email, boolean truth_value) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE frommail = '" + email + "' AND is_draft = 1";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //This method Insert into new mail on database table.
    public static boolean AddMail(String mailtext, String towho, String fromwho, int is_draft, Timestamp timestamp) {
        boolean value = true;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO mailinfo (text, tomail, frommail,is_draft,timestamp)VALUES('" + mailtext + "','" + towho + "','" + fromwho + "','" + is_draft + "','" + timestamp + "')";

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
            value = false;
        }
        return value;
    }

    //This method is updating existing user on userinfo database table.
    public static boolean UpdateUser(User user) {
        boolean value = true;
        Connection conn = null;
        String name = user.getName();
        String surname = user.getSurname();
        String birthdate = user.getBirth_date();
        int gender = user.getGender();
        String email = user.getMail();
        String password = user.getPassword();
        int id = user.getId();
        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "UPDATE userinfo SET name = '" + name + "', surname = '" + surname + "', birth_date = '" + birthdate + "', gender = '" + gender + "', mail = '" + email + "', password = '" + password + "' WHERE id = " + id;

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
            value = false;
        }
        return value;
    }

    //This method is deleting existing user on userinfo database table.
    public static boolean DeleteUser(User user) {
        boolean value = true;
        Connection conn = null;

        int id = user.getId();
        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "DELETE FROM emaildb.userinfo  WHERE id = " + id;

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
            value = false;
        }
        return value;
    }

    //Searches mails by it is mail content. This method is using on draftScreen. 
    public static ResultSet SearchByText(String text) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE text = '" + text + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //Searches mails by it is mail content and where it is sended.
    public static ResultSet SearchByTextTomail(String text, String tomail) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE text LIKE '%" + text + "%' AND tomail = '" + tomail + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //Searches mails by it is mail content and where it is sended from.
    public static ResultSet SearchByTextFrommail(String text, String frommail) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE text LIKE '%" + text + "%' AND frommail = '" + frommail + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //This method changes draft mails to normal mails by using UPDATE query.
    public static boolean ChangeDraftMail(String text, String towho, int mailid, Timestamp timestamp) {
        boolean value = true;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "UPDATE mailinfo SET text = '" + text + "', tomail = '" + towho + "', is_draft = '" + 0 + "', timestamp = '" + timestamp + "' WHERE id = " + mailid;

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
            value = false;
        }
        return value;
    }

    //Searches mails by it is content where it is sended and where it is sended from.
    public static ResultSet SearchByMailAll(String textmail, String fromwho, String towho) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE text = '" + textmail + "' AND frommail = '" + fromwho + "' AND tomail = '" + towho + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //This method deletes mails by it is id number.
    public static boolean DeleteMailById(int id) {

        boolean value = true;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "DELETE FROM emaildb.mailinfo  WHERE id = " + id;

            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
            value = false;
        }
        return value;
    }

    //This method searches mails by it is id number.
    public static ResultSet SearchMailById(int id) {
        ResultSet rset = null;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE id = '" + id + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }
        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //This method searches mails by comparing its time data and where it sended. If it is newer it returns a resultset.
    public static ResultSet SearchMailByTimeN(Timestamp timestamp, String tomail) {
        ResultSet rset = null;
        Connection conn = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = sdf.format(timestamp);
        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE timestamp > '" + formattedTimestamp + "' AND tomail = '" + tomail + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //This method searches mails by comparing its time data and where it sended. If it is older it returns a resultset.
    public static ResultSet SearchMailByTimeO(Timestamp timestamp, String tomail) {
        ResultSet rset = null;
        Connection conn = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = sdf.format(timestamp);
        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE timestamp < '" + formattedTimestamp + "' AND tomail = '" + tomail + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //This method searches mails by comparing its time data. If it is newer it returns a resultset.
    public static ResultSet AdminSearchMailByTimeN(Timestamp timestamp) {
        ResultSet rset = null;
        Connection conn = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = sdf.format(timestamp);
        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE timestamp > '" + formattedTimestamp + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

    //This method searches mails by comparing its time data. If it is older it returns a resultset.
    public static ResultSet AdminSearchMailByTimeO(Timestamp timestamp) {
        ResultSet rset = null;
        Connection conn = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = sdf.format(timestamp);
        try {
            conn = DriverManager.getConnection(connection);
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM mailinfo WHERE timestamp < '" + formattedTimestamp + "'";

            ResultSet rset2 = stmt.executeQuery(query);

            if (rset2 != null) {
                rset = rset2;

            }

        } catch (SQLException e) {
            System.out.println(" error:" + e.getMessage());
        }
        return rset;
    }

}
