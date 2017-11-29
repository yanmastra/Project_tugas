import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class Koneksi {
	private Connection con;

	public Connection getConnection(){
		if(con==null){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				this.con = DriverManager.getConnection("jdbc:mysql://localhost:3366/db_pr", "root", "");
			}catch( ClassNotFoundException e1 ){
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}catch( Exception e2 ){
				JOptionPane.showMessageDialog(null, e2.getMessage());
			}
		}
		return con;
	}
}
