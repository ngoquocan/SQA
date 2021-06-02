package quanlysotietkiem.entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "thongbao")
public class Thongbao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "ngay")
	private Date ngay;
	@Column(name = "gio")
	private Time gio;
	@Column(name = "noidung")
	private String noidung;
	@Column(name = "trangthai") //0 là chưa xem 1 là đã xem
	private long trangthai;
	@OneToOne
	@JoinColumn(name = "khachhang_id")
	private Khachhang khachhang;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getNgay() {
		return ngay;
	}
	public void setNgay(Date ngay) {
		this.ngay = ngay;
	}
	public Time getGio() {
		return gio;
	}
	public void setGio(Time gio) {
		this.gio = gio;
	}
	public Khachhang getKhachhang() {
		return khachhang;
	}
	public void setKhachhang(Khachhang khachhang) {
		this.khachhang = khachhang;
	}
	public String getNoidung() {
		return noidung;
	}
	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}
	public long getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(long trangthai) {
		this.trangthai = trangthai;
	}
	public Thongbao(Date ngay, Time gio, String noidung, long trangthai, Khachhang khachhang) {
		this.ngay = ngay;
		this.gio = gio;
		this.noidung = noidung;
		this.trangthai = trangthai;
		this.khachhang = khachhang;
	}
	public Thongbao() {
	}
}
