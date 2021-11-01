
package EnTiTy_Class;
import java.util.Date;
public class KhoaHoc {
    private int maKH;
    private String maCD;
    private double hocphi;
    private int thoiluong;
    private Date ngayKG;
    private String ghichu;
    private String maNV;
    private Date ngayTao;

    public KhoaHoc() {
    }

    public KhoaHoc(int maKH, String maCD, double hocphi, int thoiluong, Date ngayKG, String ghichu, String maNV, Date ngayTao) {
        this.maKH = maKH;
        this.maCD = maCD;
        this.hocphi = hocphi;
        this.thoiluong = thoiluong;
        this.ngayKG = ngayKG;
        this.ghichu = ghichu;
        this.maNV = maNV;
        this.ngayTao = ngayTao;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public double getHocphi() {
        return hocphi;
    }

    public void setHocphi(double hocphi) {
        this.hocphi = hocphi;
    }

    public int getThoiluong() {
        return thoiluong;
    }

    public void setThoiluong(int thoiluong) {
        this.thoiluong = thoiluong;
    }

    public Date getNgayKG() {
        return ngayKG;
    }

    public void setNgayKG(Date ngayKG) {
        this.ngayKG = ngayKG;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return this.maCD + " (" + this.ngayKG +")";
    }
    
    
}
