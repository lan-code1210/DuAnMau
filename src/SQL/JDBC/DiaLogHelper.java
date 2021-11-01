
package SQL.JDBC;
import java.awt.Component;
import javax.swing.JOptionPane;

public class DiaLogHelper {
   
    public static void alert(Component parent, String message, String titel){
        JOptionPane.showMessageDialog(parent, message, titel, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void warring(Component parent, String message, String titel){
        JOptionPane.showMessageDialog(parent, message, titel, JOptionPane.ERROR_MESSAGE);
    }
   
     public static boolean confirm(Component parent, String message, String titel) {
         int chose = JOptionPane.showConfirmDialog(parent, message, titel, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
         return chose == JOptionPane.YES_OPTION;
     }  
     
    
    public static void showInputDiaLog(Component parent, String message, String titel){
        JOptionPane.showInputDialog(parent, message, titel, JOptionPane.INFORMATION_MESSAGE);
    }

}
