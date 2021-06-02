package quanlysotietkiem.entity;

import javax.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "khachhang")
public class Khachhang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "taikhoan")
	private String taikhoan;
	@Column(name = "matkhau")
	private String matkhau;
	@OneToOne
	@JoinColumn(name = "sotaikhoan_id")
	private Taikhoannganhang sotaikhoan;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTaikhoan() {
		return taikhoan;
	}
	public void setTaikhoan(String taikhoan) {
		this.taikhoan = taikhoan;
	}
	public String getMatkhau() {
		return matkhau;
	}
	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}
	public Taikhoannganhang getTknh() {
		return sotaikhoan;
	}
	public void setTknh(Taikhoannganhang sotaikhoan) {
		this.sotaikhoan = sotaikhoan;
	}

	
}
