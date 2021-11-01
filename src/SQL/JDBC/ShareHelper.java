
package SQL.JDBC;

import EnTiTy_Class.NhanVien;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

public class ShareHelper {
    
    public static NhanVien USER = null;

    public static void clear(){
        ShareHelper.USER = null;
    }
   
    public static boolean isManager(){
        return ShareHelper.isLogin() && USER.getVaitro();
    }
   
    public static boolean isLogin() {
        return ShareHelper.USER != null;
    }
}
