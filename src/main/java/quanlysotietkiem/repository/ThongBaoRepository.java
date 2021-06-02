package quanlysotietkiem.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import quanlysotietkiem.entity.Thongbao;

public interface ThongBaoRepository extends JpaRepository<Thongbao, Long> {
	List<Thongbao> findByNgay(Date ngay);
	List<Thongbao> findByKhachhang_id(long id);
	List<Thongbao> findByKhachhang_idAndTrangthai(long id,long trangthai);
	List<Thongbao> findByKhachhang_idAndNgay(long id,Date ngay);
}
