package quanlysotietkiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;

@Entity
@Table(name = "kyhan")
public class Kyhan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "tenkyhan")
	private String tenkyhan;
	@Column(name = "kyhan")
	private int kyhan;
	@Column(name = "songay")
	private int songay;
	@Column(name = "laisuat")
	private float laisuat;
	
	public int getSongay() {
		return songay;
	}
	public void setSongay(int songay) {
		this.songay = songay;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTenkyhan() {
		return tenkyhan;
	}
	public void setTenkyhan(String tenkyhan) {
		this.tenkyhan = tenkyhan;
	}
	public int getKyhan() {
		return kyhan;
	}
	public void setKyhan(int kyhan) {
		this.kyhan = kyhan;
	}
	public float getLaisuat() {
		return laisuat;
	}
	public void setLaisuat(float laisuat) {
		this.laisuat = laisuat;
	}
	
	
}
