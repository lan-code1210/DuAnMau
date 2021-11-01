package DAO;

import EnTiTy_Class.NhanVien;
import SQL.JDBC.JDBCHelper;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO extends DaoEdu<NhanVien, String> {

    @Override
    public void insert(NhanVien e) {
        String sql = "insert into dbo.NhanVien (MaNV,MatKhau,HoTen,VaiTro) values(?, ?, ?, ?)";
        JDBCHelper.executeUpdate(sql, e.getMaNV(), e.getMatkhau(), e.getHoten(), e.getVaitro());
    }

    @Override
    public void update(NhanVien e) {
        String sql = "update dbo.NhanVien\n"
                + "set MatKhau =?\n"
                + "where MaNV = ?";
        JDBCHelper.executeUpdate(sql, e.getMatkhau(), e.getMaNV());
    }

    @Override
    public void delete(String k) {
        String sql = "delete from dbo.NhanVien where MaNV = ?";
        JDBCHelper.executeUpdate(sql, k);
    }

    @Override
    public List<NhanVien> selectAll() {
        String sql = "select * from NhanVien";
        return selectBySQL(sql);
    }

    @Override
    public NhanVien selectById(String k) {
        String sql = "select * from NhanVien where MaNV = ?";
        List<NhanVien> list = selectBySQL(sql, k);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<NhanVien> selectBySQL(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.excecuteQuery(sql, args);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setHoten(rs.getString("HoTen"));
                nv.setMatkhau(rs.getString("MatKhau"));
                nv.setVaitro(rs.getBoolean("VaiTro"));
                list.add(nv);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
