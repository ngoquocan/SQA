package quanlysotietkiem.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "taikhoannganhang")
public class Taikhoannganhang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "sotaikhoan")
	private String sotaikhoan;
	@Column(name = "hoten")
	private String hoten;
	@Column(name = "diachi")
	private String diachi;
	@Column(name = "ngaysinh")
	private Date ngaysinh;
	
	private String ngaysinh1;
	
	@Column(name = "sdt")
	private String sdt;
	@Column(name = "email")
	private String email;
	@Column(name = "cmnd")
	private String cmnd;
	@Column(name = "sodu")
	private long sodu;
	public long getSodu() {
		return sodu;
	}
	public void setSodu(long sodu) {
		this.sodu = sodu;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSotaikhoan() {
		return sotaikhoan;
	}
	public void setSotaikhoan(String sotaikhoan) {
		this.sotaikhoan = sotaikhoan;
	}
	public String getHoten() {
		return hoten;
	}
	public void setHoten(String hoten) {
		this.hoten = hoten;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public Date getNgaysinh() {
		return ngaysinh;
	}
	public void setNgaysinh(Date ngaysinh) {
		this.ngaysinh = ngaysinh;
	}
	public String getNgaysinh1() {
		return ngaysinh1;
	}
	public void setNgaysinh1(String ngaysinh1) {
		this.ngaysinh1 = ngaysinh1;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCmnd() {
		return cmnd;
	}
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}	
}
