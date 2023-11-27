package git.markvn2.keys;

public class DBKeys {
    private static String SQLUser = "root";
    private static String SQLPassword = "PASSWORD";
;
    private static String SQLDatabase = "jdbc:mysql://localhost:3306/lp2";

    public static String getSQLUser(){
        return SQLUser;
    }
    public static String getSQLPassword(){
        return SQLPassword;
    }
    public static String getSQLDatabase(){
        return SQLDatabase;
    }
    public void setSQLUser(String user){
        SQLUser = user;
    }
    public void setSQLPassword(String password){
        SQLPassword = password;
    }
    public void setSQLDatabase(String database){
        SQLDatabase = database;
    }
}