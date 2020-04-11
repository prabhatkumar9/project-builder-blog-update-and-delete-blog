package utility;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager{
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
	Properties prop;
	prop = loadPropertiesFile();
	final String driver = prop.getProperty("driver");
	final String url = prop.getProperty("url");
	final String user = prop.getProperty("username");
	final String pass = prop.getProperty("password");
	Class.forName(driver);
	Connection con;
	con = DriverManager.getConnection(url,user,pass);
	if(con != null) {
	    return con;
	}else {
	    System.out.println("check your connection.");
	    return null;
	}
    }
    
    public static Properties loadPropertiesFile() throws IOException {
	Properties prop = new Properties();
	InputStream in = ConnectionManager.class.getClassLoader().getResourceAsStream("jdbc.properties");
	prop.load(in);
	in.close();
	return prop;
    } 
}
