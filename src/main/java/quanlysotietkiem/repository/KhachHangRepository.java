package quanlysotietkiem.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import quanlysotietkiem.entity.Khachhang;

public interface KhachHangRepository extends JpaRepository<Khachhang, Long> {
	List<Khachhang> findByTaikhoan(String a);
	List<Khachhang> findBySotaikhoan_id(Long id);
}
