package quanlysotietkiem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import quanlysotietkiem.entity.Taikhoannganhang;

public interface TKNHRepository extends JpaRepository<Taikhoannganhang, Long> {
	List<Taikhoannganhang> findBySotaikhoan(String a);
}
