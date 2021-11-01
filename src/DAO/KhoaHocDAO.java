
package DAO;

import EnTiTy_Class.KhoaHoc;
import SQL.JDBC.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class KhoaHocDAO extends DaoEdu<KhoaHoc, String>{

    @Override
    public void insert(KhoaHoc e) {
        String sql = "insert into KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV, NgayTao) values (?,?,?,?,?,?,?)";
        JDBCHelper.executeUpdate(sql, e.getMaCD(), e.getHocphi(), e.getThoiluong(), e.getNgayKG(),
                               e.getGhichu(), e.getMaNV(), e.getNgayTao());
    }

    @Override
    public void update(KhoaHoc e) {
        String sql = "update dbo.KhoaHoc set MaCD = ?, HocPhi = ?, ThoiLuong = ?, NgayKG = ?, GhiChu = ?, MaNV = ?, NgayTao = ? where MaKH = ? ";
        JDBCHelper.executeUpdate(sql, e.getMaCD(), e.getHocphi(), e.getThoiluong(), e.getNgayKG(),
                               e.getGhichu(), e.getMaNV(), e.getNgayTao(), e.getMaKH());
    }

    @Override
    public void delete(String k) {
        String sql = "delete from dbo.KhoaHoc where MaKH = ?";
        JDBCHelper.executeUpdate(sql, k);
    }

    @Override
    public List<KhoaHoc> selectAll() {
        String sql = "select * from dbo.KhoaHoc";
        return this.selectBySQL(sql);
    }

    @Override
    public KhoaHoc selectById(String k) {
        String sql = "select * from dbo.KhoaHoc where MaKH = ?";
        List<KhoaHoc> list = this.selectBySQL(sql, k);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    @Override
    protected List<KhoaHoc> selectBySQL(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.excecuteQuery(sql, args);
            while(rs.next()){
                KhoaHoc kh = new KhoaHoc();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setMaCD(rs.getString("MaCD"));
                kh.setHocphi(rs.getDouble("HocPhi"));
                kh.setThoiluong(rs.getInt("ThoiLuong"));
                kh.setNgayKG(rs.getDate("NgayKG"));
                kh.setGhichu(rs.getString("GhiChu"));
                kh.setMaNV(rs.getString("MaNV"));
                kh.setNgayTao(rs.getDate("NgayTao"));
                list.add(kh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<KhoaHoc> selectByChuyenDe(String macd){
        String sql = "select * from dbo.KhoaHoc where MaCD = ?";
        return this.selectBySQL(sql, macd);
    }
    
    public List<Integer> selectYears(){
        String sql = "select distinct year(NgayKG) from dbo.KhoaHoc order by year(NgayKG) desc";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.excecuteQuery(sql);
            while(rs.next()){
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
