package DAO;

import EnTiTy_Class.NguoiHoc;
import SQL.JDBC.DateHelper;
import SQL.JDBC.JDBCHelper;
import SQL.JDBC.ShareHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class NguoiHocDAO extends DaoEdu<NguoiHoc, String> {

    @Override
    public void insert(NguoiHoc e) {
        String sql = "insert into dbo.NguoiHoc(MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV, NgayDK) values(?,?,?,?,?,?,?,?,?)";
        JDBCHelper.executeUpdate(sql, e.getMaNH(), e.getHoten(), e.getNgaySinh(), e.isGioiTinh(), e.getDienthoai(),
                e.getEmail(), e.getGhichu(), ShareHelper.USER.getMaNV(), DateHelper.stringa(new Date(), "yyyy/MM/dd"));
    }

    @Override
    public void update(NguoiHoc e) {
        String sql = "update NguoiHoc set HoTen = ?, NgaySinh = ?, GioiTinh = ?, DienThoai = ?, Email = ?, GhiChu = ? where MaNH = ?";
        JDBCHelper.executeUpdate(sql, e.getHoten(), DateHelper.stringa(e.getNgaySinh(), "MM/dd/yyyy"), e.isGioiTinh(), e.getDienthoai(),
                e.getEmail(), e.getGhichu(), e.getMaNH());
    }

    @Override
    public void delete(String k) {
        String sql = "delete from dbo.NguoiHoc where MaNH = ?";
        JDBCHelper.executeUpdate(sql, k);
    }

    @Override
    public List<NguoiHoc> selectAll() {
        String sql = "select* from dbo.NguoiHoc ";
        return this.selectBySQL(sql);
    }

    @Override
    public NguoiHoc selectById(String k) {
        String sql = "select*from dbo.NguoiHoc where MaNH = ? ";
        List<NguoiHoc> list = this.selectBySQL(sql, k);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    protected List<NguoiHoc> selectBySQL(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.excecuteQuery(sql, args);
            while (rs.next()) {
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString("MaNH"));
                nh.setHoten(rs.getString("HoTen"));
                nh.setNgaySinh(rs.getDate("NgaySinh"));
                nh.setGioiTinh(rs.getBoolean("GioiTinh"));
                nh.setDienthoai(rs.getString("DienThoai"));
                nh.setEmail(rs.getString("Email"));
                nh.setGhichu(rs.getString("GhiChu"));
                nh.setMaNV(rs.getString("MaNV"));
                nh.setNgayDK(rs.getDate("NgayDK"));
                list.add(nh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NguoiHoc> selectByTuKhoa(String key) {
        String sql = "select*from dbo.NguoiHoc where HoTen LIKE ? order by NgayDK desc";
        return selectBySQL(sql, "%" + key + "%");
    }

    public List<NguoiHoc> selectNoInCourse(int makh, String tukhoa) {
        String sql = "select*from NguoiHoc \n"
                + "where HoTen like ? and \n"
                + "MaNH not in (select MaNH from HocVien where MaKH = ?)";
        return this.selectBySQL(sql, "%" + tukhoa + "%", makh);
    }
}
