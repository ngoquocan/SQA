package quanlysotietkiem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "daohan")
public class DaoHan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "loaidaohan")
	private String loaidaohan;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoaidaohan() {
		return loaidaohan;
	}
	public void setLoaidaohan(String loaidaohan) {
		this.loaidaohan = loaidaohan;
	}
	public DaoHan() {
		super();
	}
	public DaoHan(String loaidaohan) {
		super();
		this.loaidaohan = loaidaohan;
	}


	
}
