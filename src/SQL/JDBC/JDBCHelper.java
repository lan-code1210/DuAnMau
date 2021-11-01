package SQL.JDBC;

import java.sql.*;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerException;
public class JDBCHelper {
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String url = "jdbc:sqlserver://localhost:1433;databaseName=Polypro";
    private static String user = "sa";
    private static String pass = "1234";
    
    static {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static PreparedStatement getPstm(String sql, Object... args) throws Exception{
        Connection cn = DriverManager.getConnection(url, user, pass);
        PreparedStatement pst;
        if(sql.trim().startsWith("{")){
            pst = cn.prepareCall(sql);
        }else{
            pst = cn.prepareStatement(sql);
        }
        for(int i = 0; i < args.length; i++){
            pst.setObject(i + 1, args[i]);
        }
        return pst;
    }
    
    public static ResultSet excecuteQuery(String sql, Object... args) throws Exception{
        PreparedStatement stsm = JDBCHelper.getPstm(sql, args);
        return stsm.executeQuery();
    }
    
    public static Object value(String sql, Object... args){
        try {
            ResultSet rs = JDBCHelper.excecuteQuery(sql, args);
            if(rs.next()){
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static int executeUpdate(String sql, Object... args){
        try {
            PreparedStatement stsm = JDBCHelper.getPstm(sql, args);
            try{
                return stsm.executeUpdate();
            }finally{
                stsm.getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
