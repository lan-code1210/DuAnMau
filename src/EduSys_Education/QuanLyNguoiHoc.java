package EduSys_Education;

import DAO.NguoiHocDAO;
import EnTiTy_Class.NguoiHoc;
import SQL.JDBC.DateHelper;
import SQL.JDBC.DiaLogHelper;
import SQL.JDBC.ShareHelper;
import Validatio_EduSys.Validatio;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class QuanLyNguoiHoc extends javax.swing.JDialog {
    
    public QuanLyNguoiHoc(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    NguoiHocDAO dao = new NguoiHocDAO();
    int index = - 1;
    
    public void init(){
        setLocationRelativeTo(null);
        setResizable(false);
        
        this.fillTable();
        this.index = - 1;
        this.updateStatus();
    }
    public void setForm(NguoiHoc nh){
        txtMaNH.setText(nh.getMaNH());
        txtHoTen.setText(nh.getHoten());
        txtNgaySinh.setText(DateHelper.stringa(nh.getNgaySinh(), "MM/dd/yyyy"));
        txtEamil.setText(nh.getEmail());
        txtDienThoai.setText(nh.getDienthoai());
        txtGhiChu.setText(nh.getGhichu());
        if(nh.isGioiTinh()){
            rdoNam.setSelected(true);
        }else{
            rdoNu.setSelected(true);
        }
    }
    public NguoiHoc getForm(){
        NguoiHoc nh = new NguoiHoc();
        nh.setMaNH(txtMaNH.getText().toUpperCase());
        nh.setHoten(txtHoTen.getText());
        nh.setGioiTinh(rdoNam.isSelected());
        nh.setNgaySinh(DateHelper.dt(txtNgaySinh.getText(), "MM/dd/yyyy"));
        nh.setDienthoai(txtDienThoai.getText());
        nh.setEmail(txtEamil.getText());
        nh.setGhichu(txtGhiChu.getText());
        return nh;
    }
    public void updateStatus(){
        boolean edit = (this.index >= 0);
        boolean first = (this.index == 0);
        boolean last = (this.index == tblNguoiHoc.getSelectedRow() - 1);
        
        txtMaNH.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !first);
        btnLast.setEnabled(edit && !first);
    }
    public void clear(){
        txtMaNH.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtEamil.setText("");
        txtDienThoai.setText("");
        txtGhiChu.setText("");
    }
    public void edit(){
        String Manh = tblNguoiHoc.getValueAt(this.index, 0).toString();
        NguoiHoc nh = dao.selectById(Manh);
        this.setForm(nh);
        table.setSelectedIndex(0);
        this.updateStatus();
    }
    public void clearFrom(){
        this.clear();
        this.index = - 1;
        this.updateStatus();
    }
    public void fillTable(){
        DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            String tukhoa = txtTimKiem.getText();
            List<NguoiHoc> list = this.dao.selectByTuKhoa(tukhoa);
            for(NguoiHoc nh : list){
                model.addRow(new Object[] {nh.getMaNH(), nh.getHoten(), nh.isGioiTinh() ? "Nam" : "Nữ", DateHelper.stringa(nh.getNgaySinh(), "MM/dd/yyyy"),
                                           nh.getDienthoai(), nh.getEmail(), nh.getMaNV(), DateHelper.stringa(nh.getNgayDK(), "MM/dd/yyyy")});
            }
            model.fireTableDataChanged();
       } catch (Exception e) {
           e.printStackTrace();
            DiaLogHelper.warring(this, "Lỗi truy vấn dữ liệu!!", "Lỗi !!");
        }
    }
    public void timKiem(){
        fillTable();
        clearFrom();
        index = - 1;
        updateStatus();
    }
    public void first(){
        index = 0;
        this.edit();
    }
    public void prev(){
        if(this.index > 0){
            index--;
            this.edit();
        }
    }
    public void next(){
        if(this.index < tblNguoiHoc.getRowCount() - 1){
            this.index++;
            this.edit();
        }
    }
    public void last(){
        this.index = tblNguoiHoc.getRowCount() - 1;
        this.edit();
    }
    public void insert(){
        try {
                
            if(Validatio.checkTrungMaNH(txtMaNH.getText()) == true){
                DiaLogHelper.warring(this, "Mã người học đã tồn tại!!", "Lỗi!!");
                return;
            }else if(Validatio.CheckTrong( txtMaNH) == false){
                return;
            }else if(Validatio.checkSLKyTuNH( txtMaNH) == false){
                return;
            }else if(Validatio.CheckTrong( txtHoTen) == false){
                return;
            }else if(Validatio.CheckTrong( txtNgaySinh) == false){
                return;
            }else if(Validatio.checkDate( txtNgaySinh) == false){
                return;
            }else if(Validatio.CheckTrong( txtDienThoai) == false){
                return;
            }else if(Validatio.checkSoDT(txtDienThoai) == false){
                return;
            }else if(Validatio.checkTrungSDTNH(txtDienThoai.getText()) == true){
                DiaLogHelper.warring(this, "Số điện thoại này đã tồn tại!!", "Lỗi!!");
                return;
            }else if(Validatio.CheckTrong( txtEamil) == false){
                return;
            }else if(Validatio.checkEmail(txtEamil) == false){
                return;
            }else if(Validatio.checkTrungEmailNH(txtEamil.getText()) == true){
                DiaLogHelper.warring(this, "Email này đã tồn tại!!", "Lỗi!!");
                return;
            }else{
                int count = 0;
                count = tblNguoiHoc.getRowCount();
                
                NguoiHoc nh = getForm();
                dao.insert(nh);
                this.fillTable();
                DiaLogHelper.alert(this, "Thêm thành công!!", "Thông báo!!");
                this.clearFrom();
            }
        } catch (Exception e) {
            e.printStackTrace();
            DiaLogHelper.warring(this, "Thêm thất bại!!", "Lỗi!!");
        }
    }
    
    public void update(){
        if(Validatio.CheckTrong( txtMaNH) == false){
                return;
            }else if(Validatio.CheckTrong( txtHoTen) == false){
                return;
            }else if(Validatio.CheckTrong( txtNgaySinh) == false){
                return;
            }else if(Validatio.checkDate( txtNgaySinh) == false){
                return;
            }else if(Validatio.CheckTrong( txtDienThoai) == false){
                return;
            }else if(Validatio.checkSoDT(txtDienThoai) == false){
                return;
            }else if(Validatio.checkTrungSDTNH(txtDienThoai.getText()) == true){
                DiaLogHelper.warring(this, "Số điện thoại này đã tồn tại!!", "Lỗi!!");
                return;
            }else if(Validatio.CheckTrong( txtEamil) == false){
                return;
            }else if(Validatio.checkEmail(txtEamil) == false){
                return;
            }else if(Validatio.checkTrungEmailNH(txtEamil.getText()) == true){
                DiaLogHelper.warring(this, "Email này đã tồn tại!!", "Lỗi!!");
                return;
            }else{
                NguoiHoc nh = getForm();
                try {
                    dao.update(nh);
                    this.fillTable();
                    DiaLogHelper.alert(this, "Cập nhật thành công!!", "Thông Báo!!");
            } catch (Exception e) {
                e.printStackTrace();
                DiaLogHelper.warring(this, "Cập nhật thất bại!!", "Lỗi!!");
            }
            }
    }
    public void delete(){
        if(!ShareHelper.isManager()){
            DiaLogHelper.warring(this, "Bạn khong có quyền xóa người học này!!", "Lỗi!!");
        }else{
            String manh = txtMaNH.getText();
            if(DiaLogHelper.confirm(this, "Bạn có muốn xóa nhân viên này không??", "Thông báo!!") ){
                try {
                    dao.delete(manh);
                    fillTable();
                    clearFrom();
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
        table = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDienThoai = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEamil = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EduSys - Quản Lý Người Học");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Quản Lý Người Học");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Mã Người Học: ");

        txtMaNH.setForeground(new java.awt.Color(153, 153, 153));
        txtMaNH.setText("Mã*");
        txtMaNH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaNHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaNHFocusLost(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Họ và Tên: ");

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

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Giới Tính: ");

        buttonGroup1.add(rdoNam);
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");
        rdoNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNamActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Ngày Sinh: ");

        txtNgaySinh.setForeground(new java.awt.Color(153, 153, 153));
        txtNgaySinh.setText("dd/mm/yyyy*");
        txtNgaySinh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNgaySinhFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgaySinhFocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Điện Thoại: ");

        txtDienThoai.setForeground(new java.awt.Color(153, 153, 153));
        txtDienThoai.setText("SDT*");
        txtDienThoai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDienThoaiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDienThoaiFocusLost(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Địa Chỉ Email: ");

        txtEamil.setForeground(new java.awt.Color(153, 153, 153));
        txtEamil.setText("Email*");
        txtEamil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEamilFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEamilFocusLost(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Ghi Chú(nếu có): ");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa ");
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

        btnFirst.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(txtMaNH)
                    .addComponent(txtHoTen)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoNu, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165)
                        .addComponent(txtNgaySinh))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtEamil)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(235, 235, 235)
                                .addComponent(jLabel5))
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLast)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaNH, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEamil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table.addTab("CẬP NHẬT", jPanel1);

        jLabel9.setText("Tìm Kiếm: ");

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ NH", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL", "MÃ NV", "NGÀY ĐK"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiHocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNguoiHoc);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        table.addTab("DANH SÁCH", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(table)
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
                .addComponent(table)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdoNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNamActionPerformed

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

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearFrom();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiHocMouseClicked
        // TODO add your handling code here:
        index = tblNguoiHoc.getSelectedRow();
        edit();
    }//GEN-LAST:event_tblNguoiHocMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        timKiem();
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtMaNHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNHFocusGained
        // TODO add your handling code here:
        if(txtMaNH.getText().equals("Mã*")){
           txtMaNH.setText("");
           txtMaNH.setForeground(Color.black);
           txtMaNH.setBorder(new LineBorder(Color.white));
       }
    }//GEN-LAST:event_txtMaNHFocusGained

    private void txtMaNHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNHFocusLost
        // TODO add your handling code here:
        if(txtMaNH.getText().equals("")){
           txtMaNH.setText("Mã*");
           txtMaNH.setForeground(new Color(255,102,102));
           txtMaNH.setBorder(new LineBorder(new Color(255,102,102)));
       }
    }//GEN-LAST:event_txtMaNHFocusLost

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

    private void txtNgaySinhFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgaySinhFocusGained
        // TODO add your handling code here:
        if(txtNgaySinh.getText().equals("dd/mm/yyyy*")){
           txtNgaySinh.setText("");
           txtNgaySinh.setForeground(Color.black);
           txtNgaySinh.setBorder(new LineBorder(Color.white));
       }
    }//GEN-LAST:event_txtNgaySinhFocusGained

    private void txtNgaySinhFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgaySinhFocusLost
        // TODO add your handling code here:
        if(txtNgaySinh.getText().equals("")){
           txtNgaySinh.setText("dd/mm/yyyy*");
           txtNgaySinh.setForeground(new Color(255,102,102));
           txtNgaySinh.setBorder(new LineBorder(new Color(255,102,102)));
       }
    }//GEN-LAST:event_txtNgaySinhFocusLost

    private void txtDienThoaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDienThoaiFocusGained
        // TODO add your handling code here:
        if(txtDienThoai.getText().equals("SDT*")){
           txtDienThoai.setText("");
           txtDienThoai.setForeground(Color.black);
           txtDienThoai.setBorder(new LineBorder(Color.white));
       }
    }//GEN-LAST:event_txtDienThoaiFocusGained

    private void txtDienThoaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDienThoaiFocusLost
        // TODO add your handling code here:
        if(txtDienThoai.getText().equals("")){
           txtDienThoai.setText("SDT*");
           txtDienThoai.setForeground(new Color(255,102,102));
           txtDienThoai.setBorder(new LineBorder(new Color(255,102,102)));
       }
    }//GEN-LAST:event_txtDienThoaiFocusLost

    private void txtEamilFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEamilFocusGained
        // TODO add your handling code here:
        if(txtEamil.getText().equals("Email*")){
           txtEamil.setText("");
           txtEamil.setForeground(Color.black);
           txtEamil.setBorder(new LineBorder(Color.white));
       }
    }//GEN-LAST:event_txtEamilFocusGained

    private void txtEamilFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEamilFocusLost
        // TODO add your handling code here:
        if(txtEamil.getText().equals("")){
           txtEamil.setText("Email*");
           txtEamil.setForeground(new Color(255,102,102));
           txtEamil.setBorder(new LineBorder(new Color(255,102,102)));
       }
    }//GEN-LAST:event_txtEamilFocusLost

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
            java.util.logging.Logger.getLogger(QuanLyNguoiHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyNguoiHoc dialog = new QuanLyNguoiHoc(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane table;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtDienThoai;
    private javax.swing.JTextField txtEamil;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNH;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
