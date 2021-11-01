
package DAO;

import EnTiTy_Class.ChuyenDe;
import SQL.JDBC.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ChuyenDeDAO extends DaoEdu<ChuyenDe, String>{

    @Override
    public void insert(ChuyenDe e) {
        String sql = "insert into ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) values (?,?,?,?,?,?)";
        JDBCHelper.executeUpdate(sql, e.getMaCD(), e.getTenCD(), e.getHocphi(), e.getThoiluong(),
                               e.getHinh(), e.getMota());
   }

    @Override
    public void update(ChuyenDe e) {
        String sql = "update ChuyenDe set TenCD = ?, HocPhi =?, ThoiLuong = ?, Hinh = ?, MoTa = ? where MaCD = ?";
        JDBCHelper.executeUpdate(sql, e.getTenCD(), e.getHocphi(), e.getThoiluong(),
                               e.getHinh(), e.getMota(), e.getMaCD());
    }

    @Override
    public void delete(String k) {
        String sql = "delete from dbo.ChuyenDe where MaCD = ?";
        JDBCHelper.executeUpdate(sql, k);
    }

    @Override
    public List<ChuyenDe> selectAll() {
        String sql = "select*from dbo.ChuyenDe";
        return this.selectBySQL(sql);
   }

    @Override
    public ChuyenDe selectById(String k) {
        String sql = "select*from dbo.ChuyenDe where MaCD = ?";
        List<ChuyenDe> list = this.selectBySQL(sql, k);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    @Override
    protected List<ChuyenDe> selectBySQL(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.excecuteQuery(sql, args);
            while(rs.next()){
                ChuyenDe cd = new ChuyenDe();
                cd.setMaCD(rs.getString("MaCD"));
                cd.setTenCD(rs.getString("TenCD"));
                cd.setHocphi(rs.getDouble("HocPhi"));
                cd.setThoiluong(rs.getInt("ThoiLuong"));
                cd.setHinh(rs.getString("Hinh"));
                cd.setMota(rs.getString("MoTa"));
                list.add(cd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
   }
    
}
