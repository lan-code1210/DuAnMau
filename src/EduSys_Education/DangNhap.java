package EduSys_Education;

import DAO.NhanVienDAO;
import EnTiTy_Class.NhanVien;
import SQL.JDBC.DiaLogHelper;
import SQL.JDBC.ShareHelper;
import Validatio_EduSys.Validatio;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

public class DangNhap extends javax.swing.JDialog {
    // java.awt.Frame parent;

    public DangNhap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    NhanVienDAO dao = new NhanVienDAO();

    public void init() {
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        txtTendangnhap.setText("TeoNV");
//        txtMatKhau.setText("123456");
    }

    public void DangNhap1() {

        String tendangnhap = txtTendangnhap.getText();
        String matkhau = new String(txtMatKhau.getPassword());
        if (Validatio.CheckTrong(txtTendangnhap) == false) {
            return;
        } else if (Validatio.CheckTrong(txtMatKhau) ==false) {
            return;
        } else { 
            NhanVien nv = dao.selectById(tendangnhap);
            if (nv == null) {
                DiaLogHelper.warring(this, "Tên đăng nhập của bạn sai", "Lỗi!!");
            } else if (!matkhau.equals(nv.getMatkhau())) {
                DiaLogHelper.warring(this, "Mật khẩu của bạn sai", "Lỗi!!");
            } else {
                ShareHelper.USER = nv;
                DiaLogHelper.alert(this, "Đăng nhập thành công", "Thông Báo!!");
                this.dispose();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtTendangnhap = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JPasswordField();
        btnDangNhap = new javax.swing.JButton();
        btnKetthuc = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        chkHienThi = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EduSys - Đăng nhập");
        setBackground(new java.awt.Color(0, 102, 102));

        txtTendangnhap.setForeground(new java.awt.Color(153, 153, 153));
        txtTendangnhap.setText("Tên đăng nhập");
        txtTendangnhap.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTendangnhapFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTendangnhapFocusLost(evt);
            }
        });

        txtMatKhau.setForeground(new java.awt.Color(153, 153, 153));
        txtMatKhau.setText("Mật khẩu");
        txtMatKhau.setEchoChar('*');
        txtMatKhau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMatKhauFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMatKhauFocusLost(evt);
            }
        });
        txtMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatKhauActionPerformed(evt);
            }
        });

        btnDangNhap.setIcon(new javax.swing.ImageIcon("C:\\Users\\Mr.Right\\Desktop\\Java3\\DAM\\src\\icon\\Key.png")); // NOI18N
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnDangNhap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDangNhap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });

        btnKetthuc.setIcon(new javax.swing.ImageIcon("C:\\Users\\Mr.Right\\Desktop\\Java3\\DAM\\src\\icon\\Exit.png")); // NOI18N
        btnKetthuc.setText("Kết thúc");
        btnKetthuc.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnKetthuc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKetthuc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKetthuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetthucActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 0));
        jLabel2.setText("ĐĂNG NHẬP");

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\Mr.Right\\Desktop\\Java3\\DuAnMau\\src\\icon\\User.png")); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\Mr.Right\\Desktop\\Java3\\DuAnMau\\src\\icon\\Unlock.png")); // NOI18N

        chkHienThi.setText("Hiển thị mật khẩu");
        chkHienThi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHienThiActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\Mr.Right\\Desktop\\Java3\\DAM\\src\\icon\\ongvang.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(80, 80, 80))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTendangnhap)
                            .addComponent(txtMatKhau)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkHienThi)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDangNhap)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                                .addComponent(btnKetthuc, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)))))
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTendangnhap, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(chkHienThi)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnKetthuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, txtMatKhau, txtTendangnhap});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKetthucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetthucActionPerformed
        // TODO add your handling code here:
        int bye = JOptionPane.showConfirmDialog(this, "Bạn muốn thoát không ?", "Thông báo từ tôi", JOptionPane.YES_NO_OPTION);
        if (bye == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_btnKetthucActionPerformed

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        // TODO add your handling code here:
        DangNhap1();
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void txtMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatKhauActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtMatKhauActionPerformed

    private void txtMatKhauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusLost
        // TODO add your handling code here:
        if(txtMatKhau.getText().equals("")){
            txtMatKhau.setText("Mật khẩu");
            txtMatKhau.setForeground(new Color(255,102,102));
            txtMatKhau.setBorder(new LineBorder(new Color(255,102,102)));
        }
    }//GEN-LAST:event_txtMatKhauFocusLost

    private void txtMatKhauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusGained
        // TODO add your handling code here:
        if(txtMatKhau.getText().equals("Mật khẩu")){
            txtMatKhau.setText("");
            txtMatKhau.setForeground(Color.black);
            txtMatKhau.setBorder(new LineBorder(Color.white));
        }
    }//GEN-LAST:event_txtMatKhauFocusGained

    private void txtTendangnhapFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTendangnhapFocusLost
        // TODO add your handling code here:
        if(txtTendangnhap.getText().equals("")){
            txtTendangnhap.setText("Tên đăng nhập");
            txtTendangnhap.setForeground(new Color(255,102,102));
            txtTendangnhap.setBorder(new LineBorder(new Color(255,102,102)));
        }
    }//GEN-LAST:event_txtTendangnhapFocusLost

    private void txtTendangnhapFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTendangnhapFocusGained
        // TODO add your handling code here:
        if(txtTendangnhap.getText().equals("Tên đăng nhập")){
            txtTendangnhap.setText("");
            txtTendangnhap.setForeground(Color.black);
            txtTendangnhap.setBorder(new LineBorder(Color.white));
        }
    }//GEN-LAST:event_txtTendangnhapFocusGained

    private void chkHienThiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHienThiActionPerformed
        // TODO add your handling code here:
        if (chkHienThi.isSelected()) {
            txtMatKhau.setEchoChar((char) 0 );
        }else{
            txtMatKhau.setEchoChar('*');
        }
    }//GEN-LAST:event_chkHienThiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DangNhap dialog = new DangNhap(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JButton btnKetthuc;
    private javax.swing.JCheckBox chkHienThi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTendangnhap;
    // End of variables declaration//GEN-END:variables
}
