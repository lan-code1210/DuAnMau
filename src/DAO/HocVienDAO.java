
package DAO;

import EnTiTy_Class.HocVien;
import SQL.JDBC.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class HocVienDAO extends DaoEdu<HocVien, Object>{

    @Override
    public void insert(HocVien e) {
        String sql = "insert into HocVien (MaKH, MaNH, Diem) values (?,?,?)";
        JDBCHelper.executeUpdate(sql, e.getMaKH(), e.getMaNH(), e.getDiem());
    }

    @Override
    public void update(HocVien e) {
        String sql = "update dbo.HocVien set Diem = ? where MaHV = ?";
        JDBCHelper.executeUpdate(sql, e.getDiem(), e.getMaHV());
    }

    @Override
    public void delete(Object k) {
        String sql = "delete from dbo.HocVien where MaHV = ?";
        JDBCHelper.executeUpdate(sql, k);
    }

    @Override
    public List<HocVien> selectAll() {
        String sql = "select * from dbo.HocVien";
        return this.selectBySQL(sql);
     }

    @Override
    public HocVien selectById(Object k) {
        String sql = "select * from dbo.HocVien where MaHV = ?";
        List<HocVien> list = this.selectBySQL(sql, k);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    @Override
    protected List<HocVien> selectBySQL(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.excecuteQuery(sql, args);
            while(rs.next()){
                HocVien e = new HocVien();
                e.setMaHV(rs.getInt("MaHV"));
                e.setMaKH(rs.getInt("MaKH"));
                e.setMaNH(rs.getString("MaNH"));
                e.setDiem(rs.getDouble("Diem"));
                list.add(e);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
     }
    
    public List<HocVien> selectByKhoaHoc(int MaKH){
        String sql = "select * from dbo.HocVien where MaKH = ?";
        return this.selectBySQL(sql, MaKH);
    }
}
