package quanlysotietkiem;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import quanlysotietkiem.entity.Taikhoannganhang;
import quanlysotietkiem.repository.TKNHRepository;

@DataJpaTest
@Transactional
public class TaikhoannganhangTests {
	@Autowired
	private TKNHRepository tknhRepository;
	
	@Test
	public void testCreateTKNH() {
		Taikhoannganhang tknh = new Taikhoannganhang();
		tknhRepository.save(tknh);
	}
}
