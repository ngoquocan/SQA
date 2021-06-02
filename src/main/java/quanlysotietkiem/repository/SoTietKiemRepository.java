package quanlysotietkiem.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import quanlysotietkiem.entity.Khachhang;
import quanlysotietkiem.entity.Sotietkiem;

public interface SoTietKiemRepository extends JpaRepository<Sotietkiem, Long>{
	List<Sotietkiem> findByKhachhang_id(Long id);
	List<Sotietkiem> findByKhachhang_idAndNgaymo(Long id, Date ngaymo);
	List<Sotietkiem> findByMaStk(String mastk);
}
