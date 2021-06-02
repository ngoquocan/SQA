package quanlysotietkiem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import quanlysotietkiem.entity.Khachhang;
import quanlysotietkiem.entity.Kyhan;

public interface KyHanRepository extends JpaRepository<Kyhan, Long>{
	List<Kyhan> findByTenkyhan(String a);
}
