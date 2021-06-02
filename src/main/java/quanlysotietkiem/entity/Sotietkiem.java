package quanlysotietkiem.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sotietkiem")
public class Sotietkiem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "maStk")
	private String maStk;
	@Column(name = "ngaymo")
	private Date ngaymo;
	@OneToOne
	@JoinColumn(name = "kyhan_id")
	private Kyhan kyhan;
	@Column(name = "ngaydong")
	private Date ngaydong;
	@Column(name = "sotien")
	private long sotien;
	@Column(name = "tienlai")
	private long tienlai;
	@Column(name = "tongtien")
	private long tongtien;
	@OneToOne
	@JoinColumn(name = "khachhang_id")
	private Khachhang khachhang;
	@OneToOne
	@JoinColumn(name = "daohan_id")
	private DaoHan daohan;
	
	public long getTongtien() {
		return tongtien;
	}
	public void setTongtien(long tongtien) {
		this.tongtien = tongtien;
	}
	public DaoHan getDaohan() {
		return daohan;
	}
	public void setDaohan(DaoHan daohan) {
		this.daohan = daohan;
	}
	public Date getNgaydong() {
		return ngaydong;
	}
	public void setNgaydong(Date ngaydong) {
		this.ngaydong = ngaydong;
	}
	public Khachhang getKhachhang() {
		return khachhang;
	}
	public void setKhachhang(Khachhang khachhang) {
		this.khachhang = khachhang;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMaStk() {
		return maStk;
	}
	public void setMaStk(String maStk) {
		this.maStk = maStk;
	}
	public Date getNgaymo() {
		return ngaymo;
	}
	public void setNgaymo(Date ngaymo) {
		this.ngaymo = ngaymo;
	}
	
	public Kyhan getKyhan() {
		return kyhan;
	}
	public void setKyhan(Kyhan kyhan) {
		this.kyhan = kyhan;
	}
	public long getSotien() {
		return sotien;
	}
	public void setSotien(long sotien) {
		this.sotien = sotien;
	}
	public long getTienlai() {
		return tienlai;
	}
	public void setTienlai(long tienlai) {
		this.tienlai = tienlai;
	}
	
	
	
}
