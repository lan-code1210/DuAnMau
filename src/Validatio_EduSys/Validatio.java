
package Validatio_EduSys;
import DAO.ChuyenDeDAO;
import DAO.NguoiHocDAO;
import DAO.NhanVienDAO;
import EnTiTy_Class.ChuyenDe;
import EnTiTy_Class.NguoiHoc;
import EnTiTy_Class.NhanVien;
import java.awt.Component;
import javax.swing.JTextField;
import SQL.JDBC.DiaLogHelper;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
public class Validatio {
   public static boolean CheckTrong(JTextField txtFiled){
       if(txtFiled.getText().isEmpty()){
           DiaLogHelper.warring(null, "Không được để trống dữu liệu", "Lỗi");
           txtFiled.requestFocus();
           txtFiled.setBorder(new LineBorder(Color.red));
           return false;
       }else{
       txtFiled.setBorder(new LineBorder(Color.green));
       return true;
       }
       
   }
   public static boolean checkPassTrong( JPasswordField pass){
       if(pass.getPassword().equals("")){
           DiaLogHelper.warring(null, "Không được để trống dữ liệu", "Lỗi!");
           pass.requestFocus();
           pass.requestFocus();
           pass.setBorder(new LineBorder(Color.red));
           return false;
       }else{
           pass.setBorder(new LineBorder(Color.green));
           return true;
       }
   }
   public static boolean checkNumber( JTextField filed){
       boolean checkl = true;
       try {
           double so = Double.parseDouble(filed.getText());
           if(so < 0){
               DiaLogHelper.warring(null, "Dữ liệu không đúng định dạng", "Lỗi!");
               filed.requestFocus();
               filed.setBorder(new LineBorder(Color.red));
               checkl = false;
           }else{
               filed.setBorder(new LineBorder(Color.green));
               checkl = true;
           }
       } catch (Exception e) {
           DiaLogHelper.warring(filed, "Sai định dạng", "Lỗi!!!");
           filed.setBorder(new LineBorder(Color.red));
           checkl = false;
       }
       return checkl;
   }
   public static boolean checkEmail(JTextField filed){
       boolean flag = true;
       Pattern pt = Pattern.compile("\\w+@\\w+(\\.\\w+){1,2}");
       Matcher mt = pt.matcher(filed.getText());
       if(!mt.find()){
           DiaLogHelper.warring(null, "Dữ liệu không đúng định dạng", "Lỗi!");
           filed.requestFocus();
           filed.setBorder(new LineBorder(Color.red));
           flag = false;
       }else{
           filed.setBorder(new LineBorder(Color.green));
           flag = true;
       }
       return flag;
   }
   public static boolean checkDate( JTextField txtString){
       boolean flag = true;
       try {
           SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
           String fmadate = sdf.format(sdf.parse(txtString.getText()));
           if(fmadate.equals(txtString.getText())){
               flag = true;
               txtString.setBorder(new LineBorder(Color.white));
           }else{
               DiaLogHelper.warring(null, "Dữ liệu không đúng định dạng", "Lỗi!");
               txtString.requestFocus();
               txtString.setBorder(new LineBorder(Color.red));
               flag = false;
           }
       } catch (Exception e) {
            DiaLogHelper.warring(null, "Dữ liệu không đúng định dạng", "Lỗi!");
               txtString.setBorder(new LineBorder(Color.red));
               flag = false;
       }
       return flag;
   }
   public static boolean checkSoDT(JTextField filed){
       Pattern pt = Pattern.compile("^0[3,9]\\d{8}");
       Matcher mt = pt.matcher(filed.getText());
       boolean flag = true;
       if(!mt.find()){
           DiaLogHelper.warring(null, "Dữ liệu không đúng định dạng (gồm 10 số và đắt đầu 03 hoặc 09)", "Lỗi!");
           filed.requestFocus();
           filed.setBorder(new LineBorder(Color.red));
           flag = false;
       }else{
           filed.setBorder(new LineBorder(Color.white));
           flag = true;
       }
       return flag;
   }
   public static boolean checkTrungMaNV(String MaNV){
       NhanVienDAO dao = new NhanVienDAO();
       NhanVien nv = dao.selectById(MaNV);
       if(nv != null){
           if(nv.getMaNV().equalsIgnoreCase(MaNV)){
               return true;
           }
       }
       return false;
   }
   public static boolean checkTrungMaNH(String MaNH) throws Exception{
       NguoiHocDAO dao = new NguoiHocDAO();
       NguoiHoc nh = dao.selectById(MaNH);
       if(nh != null){
           if(nh.getMaNH().trim().equalsIgnoreCase(MaNH)){
               return true;
           }
       }
       return false;
   }
   public static boolean checkTrungSDTNH(String sdt){
       NguoiHocDAO dao = new NguoiHocDAO();
       List<NguoiHoc> list = dao.selectAll();
       for(int i = 0; i < list.size(); i++){
           if(list.get(i).getDienthoai().trim().equals(sdt)){
               return true;
           }
       }
       return false;
   }
   public static boolean checkSLKyTuNH( JTextField filed){
       if(filed.getText().length() > 7){
          DiaLogHelper.warring(null, "Dữ liệu không đúng định dạng", "Lỗi!");
          filed.requestFocus();
           filed.setBorder(new LineBorder(Color.red));
           return false;
       }
       filed.setBorder(new LineBorder(Color.green));
       return true;
   }
   public static boolean checkTrungEmailNH(String email){
       NguoiHocDAO dao = new NguoiHocDAO();
       List<NguoiHoc> list = dao.selectAll();
       for(int i = 0; i < list.size(); i++){
           if(list.get(i).getEmail().trim().equals(email)){
               return true;
           }
       }
       return false;
   }
   public static boolean checkTrungMaCD(String MaCD){
       ChuyenDeDAO dao = new ChuyenDeDAO();
       ChuyenDe cd = dao.selectById(MaCD);
       if(cd != null){
           if(cd.getMaCD().trim().equalsIgnoreCase(MaCD)){
               return true;
           }
       }
       return false;
   }
   public static boolean checkSLKyTuCD( JTextField filed){
       if(filed.getText().length() > 5){
           DiaLogHelper.warring(null, "Dữ liệu không đúng định dạng", "Lỗi!");
           filed.requestFocus();
           filed.setBorder(new LineBorder(Color.red));
           return false;
       }
       filed.setBorder(new LineBorder(Color.green));
       return true;
   }
   public static boolean checkTrongLBL( JLabel lbl){
       if(lbl.getText() == null){
           DiaLogHelper.warring(null, "Dữ liệu không đúng định dạng", "Lỗi!");
           lbl.requestFocus();
           lbl.setBorder(new LineBorder(Color.red));
           return false;
       }else{
           lbl.setBorder(new LineBorder(Color.white));
           return true;
       }
   }
   public static void checkKhoangTrang(JTextField txt){
       
   }
}
