package server;

import java.sql.*;

public class DbConnection {

        String url = "jdbc:mysql://localhost:3306/chatjava";
        String username = "root";
        String password = "";
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        public boolean login(String name,String pass) throws SQLException {
            connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM users ";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
               if((resultSet.getString("username")).equals(name) && (resultSet.getString("password")).equals(pass)){
                   connection.close();
                   return true;
               }
            }
            connection.close();
                return false;
        }

    public String ajouter_utilisateur(String name,String pass) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        String query = "INSERT into users (username,password) values (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,pass);
        int res = preparedStatement.executeUpdate();
        connection.close();
        return res+"nouveau compte créer";

    }
    public boolean chercher_ami(String nameducleint,String clientcibl) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        String query = "select * from users " +
                " INNER JOIN amis where amis.id_user = users.id_user ";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            if((resultSet.getString("nom")).equals(clientcibl) && (resultSet.getString("username")).equals(nameducleint)){
                connection.close();
                return true;
            }
        }
        connection.close();
        return false;

    }
}




class Main{
    public static void main(String[] args) {
        DbConnection db = new DbConnection();
        try {
            boolean b = db.login("abdo","123");
            System.out.println(b);
            db.ajouter_utilisateur("hmida","123");
            b = db.chercher_ami("ahmed","hiiii");
            System.out.println("chercher ami"+b);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
