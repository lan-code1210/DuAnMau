package EduSys_Education;

import DAO.NhanVienDAO;
import EnTiTy_Class.NhanVien;
import SQL.JDBC.DiaLogHelper;
import SQL.JDBC.ShareHelper;
import Validatio_EduSys.Validatio;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class QuanLyNhanVien extends javax.swing.JDialog {

    public QuanLyNhanVien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    NhanVienDAO dao = new NhanVienDAO();
    int index = - 1;

    public void init() {
        setLocationRelativeTo(null);
        setResizable(false);
        this.fillTable();
        this.index = -1;
        this.updateStatus();
    }

    public void setForm(NhanVien nv) {
        txtMaNV.setText(nv.getMaNV());
        txtHoTen.setText(nv.getHoten());
        txtMatKhau.setText(nv.getMatkhau());
        txtXacNhanMK.setText(nv.getMatkhau());
        if (nv.getVaitro()) {
            rdoTruongPhong.setSelected(true);
        } else {
            rdoNhanVien.setSelected(true);
        }
    }

    public NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setHoten(txtHoTen.getText());
        nv.setMatkhau(String.valueOf(txtMatKhau.getText()));
        nv.setVaitro(rdoTruongPhong.isSelected());
        return nv;
    }

    public void updateStatus() {
        boolean edit = (this.index >= 0);
        boolean first = (this.index == 0);
        boolean last = (this.index == tblNhanVien.getSelectedRow() - 1);

        txtMaNV.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !first);
        btnLast.setEnabled(edit && !first);
    }

    public void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
        this.index = - 1;
        this.updateStatus();
    }

    public void edit() {
        String manv = tblNhanVien.getValueAt(this.index, 0).toString();
        NhanVien nv = dao.selectById(manv);
        this.setForm(nv);
        Table1.setSelectedIndex(0);
        this.updateStatus();
    }

    public void first() {
        index = 0;
        this.edit();
    }

    public void prev() {
        if (this.index > 0) {
            this.index--;
            this.edit();
        }
    }

    public void next() {
        if (this.index < tblNhanVien.getRowCount() - 1) {
            this.index++;
            this.edit();
        }
    }

    public void last() {
        this.index = tblNhanVien.getRowCount() - 1;
        this.edit();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = this.dao.selectAll();
            for (NhanVien nv : list) {
                model.addRow(new Object[]{nv.getMaNV(), nv.getMatkhau(), nv.getHoten(), nv.getVaitro() ? "Trưởng Phòng" : "Nhân Viên"});
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            DiaLogHelper.warring(this, "Lỗi truy vấn dữ liệu", "Lỗi!!");
        }
    }

    public void insert() {
        NhanVien nv = getForm();
        String mk1 = new String(txtXacNhanMK.getPassword());
        if (Validatio.checkTrungMaNV(txtMaNV.getText()) == true) {
            DiaLogHelper.warring(this, "Mã nhân viên này đã tồn tại", "Lỗi!!");
            return;
        } else if (Validatio.CheckTrong( txtMaNV) == false) {
            return;
        } else if (Validatio.checkPassTrong(txtMatKhau) == false) {
            return;
        } else if (Validatio.checkPassTrong(txtXacNhanMK) == false) {
            return;
        } else if (Validatio.CheckTrong(txtHoTen) == false) {
            return;
        } else if (!mk1.equals(nv.getMatkhau())) {
            DiaLogHelper.warring(this, "Xác nhận mật khẩu không khớp với mật khẩu mới", "Lỗi!!");
        } else {
            try {
                dao.insert(nv);
                this.fillTable();
                DiaLogHelper.alert(this, "Thêm thành công", "Thông Báo!!");
                this.clearForm();
            } catch (Exception e) {
                e.printStackTrace();
                DiaLogHelper.warring(this, "Thêm thất bại", "Lỗi!!");
            }
        }
    }

    public void update() {
        NhanVien nv = getForm();
        String mk2 = new String(txtXacNhanMK.getPassword());
        if (Validatio.checkPassTrong(txtMatKhau) == false) {
            return;
        } else if (Validatio.checkPassTrong(txtXacNhanMK) == false) {
            return;
        } else if (Validatio.CheckTrong(txtHoTen) == false) {
            return;
        } else if (!mk2.equals(nv.getMatkhau())) {
            DiaLogHelper.warring(this, "Xác nhập mật khẩu không hợp lệ", "Lỗi!!");
        } else {
            try {
                dao.update(nv);
                this.fillTable();
                DiaLogHelper.alert(this, "Update thành công!!", "Thông Báo!!");
            } catch (Exception e) {
                e.printStackTrace();
                DiaLogHelper.warring(this, "Update thất bại!!", "Thông Báo!!");
            }
        }
    }

    public void delete() {
        if (ShareHelper.isManager()) {
            DiaLogHelper.warring(this, "Bạn không có quyền xóa nhân viên", "Lỗi!!");
        } else {
            String Manv = txtMaNV.getText();
            if (Manv.equals(ShareHelper.USER.getMaNV())) {
                DiaLogHelper.warring(this, "Bạn không được xóa chính bạn", "Lỗi!!");
            } else if (DiaLogHelper.confirm(this, "Bạn có thật sự muốn xóa nhân viên này không?", "Thông Báo!!")) {
                try {
                    dao.delete(Manv);
                    fillTable();
                    clearForm();
                    DiaLogHelper.alert(this, "Xóa thành công!!", "Thông Báo!!");
                } catch (Exception e) {
                    e.printStackTrace();
                    DiaLogHelper.warring(this, "Xóa thất bại!!", "Lỗi!!");
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        Table1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtXacNhanMK = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rdoTruongPhong = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        chkHienThi = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EduSys - Quản Lý Nhân Viên");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Quản Lý Nhân Viên Quản Trị ");

        jLabel2.setText("Mã Nhân Viên: ");

        txtMaNV.setForeground(new java.awt.Color(153, 153, 153));
        txtMaNV.setText("Mã nhân viên*");
        txtMaNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaNVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaNVFocusLost(evt);
            }
        });

        jLabel3.setText("Mật Khẩu: ");

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

        jLabel4.setText("Xác Nhận Mật Khẩu: ");

        txtXacNhanMK.setForeground(new java.awt.Color(153, 153, 153));
        txtXacNhanMK.setText("Nhập lại");
        txtXacNhanMK.setEchoChar('*');
        txtXacNhanMK.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                txtXacNhanMKHierarchyChanged(evt);
            }
        });
        txtXacNhanMK.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtXacNhanMKFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtXacNhanMKFocusLost(evt);
            }
        });

        jLabel5.setText("Họ Và Tên: ");

        txtHoTen.setForeground(new java.awt.Color(153, 153, 153));
        txtHoTen.setText("Họ và tên*");
        txtHoTen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHoTenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoTenFocusLost(evt);
            }
        });

        jLabel6.setText("Vai Trò: ");

        buttonGroup1.add(rdoTruongPhong);
        rdoTruongPhong.setText("Trường Phòng");

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setText("Nhân Viên");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        chkHienThi.setText("Hiển thị mật khẩu");
        chkHienThi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHienThiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNV)
                    .addComponent(txtMatKhau)
                    .addComponent(txtXacNhanMK)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkHienThi)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtHoTen)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoTruongPhong)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSua)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXoa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnMoi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                                .addComponent(btnFirst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPrev)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNext)))
                        .addGap(10, 10, 10)
                        .addComponent(btnLast)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chkHienThi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTruongPhong)
                    .addComponent(rdoNhanVien))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnLast)
                    .addComponent(btnNext)
                    .addComponent(btnPrev)
                    .addComponent(btnFirst))
                .addGap(18, 18, 18))
        );

        Table1.addTab("CẬP NHẬT", jPanel1);

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ NV", "MẬT KHẨU", "HỌ VÀ TÊN", "VAI TRÒ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                .addContainerGap())
        );

        Table1.addTab("DANH SÁCH", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Table1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(Table1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        index = tblNhanVien.getSelectedRow();
        edit();
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void chkHienThiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHienThiActionPerformed
        // TODO add your handling code here:
        if (chkHienThi.isSelected()) {
            txtMatKhau.setEchoChar((char) 0 );
            txtXacNhanMK.setEchoChar((char) 0 );
        }else{
            txtMatKhau.setEchoChar('*');
            txtXacNhanMK.setEchoChar('*');
        }
    }//GEN-LAST:event_chkHienThiActionPerformed

    private void txtMaNVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusGained
        // TODO add your handling code here:
        if(txtMaNV.getText().equals("Mã nhân viên*")){
           txtMaNV.setText("");
           txtMaNV.setForeground(Color.black);
           txtMaNV.setBorder(new LineBorder(Color.white));
       }
    }//GEN-LAST:event_txtMaNVFocusGained

    private void txtMaNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusLost
        // TODO add your handling code here:
        if(txtMaNV.getText().equals("")){
           txtMaNV.setText("Mã nhân viên*");
           txtMaNV.setForeground(new Color(255,102,102));
           txtMaNV.setBorder(new LineBorder(new Color(255,102,102)));
       }
    }//GEN-LAST:event_txtMaNVFocusLost

    private void txtMatKhauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusGained
        // TODO add your handling code here:
        if(txtMatKhau.getText().equals("Mật khẩu")){
            txtMatKhau.setText("");
            txtMatKhau.setForeground(Color.black);
            txtMatKhau.setBorder(new LineBorder(Color.white));
        }
    }//GEN-LAST:event_txtMatKhauFocusGained

    private void txtMatKhauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusLost
        // TODO add your handling code here:
        if(txtMatKhau.getText().equals("")){
            txtMatKhau.setText("Mật khẩu");
            txtMatKhau.setForeground(new Color(255,102,102));
            txtMatKhau.setBorder(new LineBorder(new Color(255,102,102)));
        }
    }//GEN-LAST:event_txtMatKhauFocusLost

    private void txtXacNhanMKFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtXacNhanMKFocusGained
        // TODO add your handling code here:
        if(txtXacNhanMK.getText().equals("Nhập lại")){
            txtXacNhanMK.setText("");
            txtXacNhanMK.setForeground(Color.black);
            txtXacNhanMK.setBorder(new LineBorder(Color.white));
        }
    }//GEN-LAST:event_txtXacNhanMKFocusGained

    private void txtHoTenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusGained
        // TODO add your handling code here:
        if(txtHoTen.getText().equals("Họ và tên*")){
           txtHoTen.setText("");
           txtHoTen.setForeground(Color.black);
           txtHoTen.setBorder(new LineBorder(Color.white));
       }
    }//GEN-LAST:event_txtHoTenFocusGained

    private void txtHoTenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusLost
        // TODO add your handling code here:
        if(txtHoTen.getText().equals("")){
           txtHoTen.setText("Họ và tên*");
           txtHoTen.setForeground(new Color(255,102,102));
           txtHoTen.setBorder(new LineBorder(new Color(255,102,102)));
       }
    }//GEN-LAST:event_txtHoTenFocusLost

    private void txtXacNhanMKHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_txtXacNhanMKHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtXacNhanMKHierarchyChanged

    private void txtXacNhanMKFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtXacNhanMKFocusLost
        // TODO add your handling code here:
        if(txtXacNhanMK.getText().equals("")){
            txtXacNhanMK.setText("Nhập lại");
            txtXacNhanMK.setForeground(new Color(255,102,102));
            txtXacNhanMK.setBorder(new LineBorder(new Color(255,102,102)));
        }
    }//GEN-LAST:event_txtXacNhanMKFocusLost

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
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyNhanVien dialog = new QuanLyNhanVien(new javax.swing.JFrame(), true);
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
    private javax.swing.JTabbedPane Table1;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkHienThi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoTruongPhong;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtXacNhanMK;
    // End of variables declaration//GEN-END:variables
}
