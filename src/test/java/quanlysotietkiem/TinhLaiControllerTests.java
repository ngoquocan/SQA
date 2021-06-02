package quanlysotietkiem;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import quanlysotietkiem.controller.RutSoController;


@SpringBootTest
public class TinhLaiControllerTests {
	@Autowired
	private RutSoController rutso;

	@Test
	public void tinhlai_songaynhohon0() throws ParseException {
		//set ngay mo
				String ngay1 = "2021-05-27";
				String ngay2 = "2021-04-27";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 0;
		Long iddaohan = Long.valueOf(1);
		float laisuat = (float) 3.8;
		long expected = 0;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_khongkyhan_1() throws ParseException {
		//set ngay mo
				String ngay1 = "2021-03-25";
				String ngay2 = "2021-05-25";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 0;
		Long iddaohan = Long.valueOf(1);
		float laisuat = (float) 2.4;
		long expected = 4066;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	/*ky han 1 thang*/
	@Test
	public void tinhlai_1thang_1() throws ParseException { //trước kỳ hạn
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-05-20";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 30;
		Long iddaohan = Long.valueOf(1);
		float laisuat = (float) 3.5;
		long expected = 1666;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_1thang_2() throws ParseException { //đúng bằng kỳ hạn
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-05-25";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 30;
		Long iddaohan = Long.valueOf(1);
		float laisuat = (float) 3.5;
		long expected = 2916;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_1thang_khongquayvong() throws ParseException { //quá kỳ hạn không quay vòng
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-05-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 30;
		Long iddaohan = Long.valueOf(1);
		float laisuat = (float) 3.5;
		long expected = 2916;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void tinhlai_1thang_quayvonggoc_1() throws ParseException { //quá kỳ hạn quay vòng gốc 1 chu kỳ
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-05-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 30;
		Long iddaohan = Long.valueOf(2);
		float laisuat = (float) 3.5;
		long expected = 3249;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_1thang_quayvonggoc_2() throws ParseException { //bằng 2 chu kỳ quay vòng gốc
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-06-24";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 30;
		Long iddaohan = Long.valueOf(2);
		float laisuat = (float) 3.5;
		long expected = 5832;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_1thang_quayvonggoc_3() throws ParseException { //quá kỳ hạn quay vòng gốc 2 chu kỳ
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-06-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 30;
		Long iddaohan = Long.valueOf(2);
		float laisuat = (float) 3.5;
		long expected = 6232;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_1thang_quayvonggocvalai_1() throws ParseException { //quá kỳ hạn quay vòng gốc va lai
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-05-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 30;
		Long iddaohan = Long.valueOf(3);
		float laisuat = (float) 3.5;
		long expected = 3250;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_1thang_quayvonggocvalai_2() throws ParseException { //bằng 2 chu kỳ quay vòng gốc va lãi
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-06-24";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 30;
		Long iddaohan = Long.valueOf(3);
		float laisuat = (float) 3.5;
		long expected = 5841;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_1thang_quayvonggocvalai_3() throws ParseException { //quá kỳ hạn quay vòng gốc va lãi 2 chu kỳ
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-06-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 30;
		Long iddaohan = Long.valueOf(3);
		float laisuat = (float) 3.5;
		long expected = 6243;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	/*end kỳ hạn 1 tháng*/
	/*kỳ hạn 3 tháng*/
	@Test
	public void tinhlai_3thang_1() throws ParseException { //trước kỳ hạn
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-05-20";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 90;
		Long iddaohan = Long.valueOf(1);
		float laisuat = (float) 3.8;
		long expected = 1666;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_3thang_2() throws ParseException { //đúng bằng kỳ hạn
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-07-24";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 90;
		Long iddaohan = Long.valueOf(1);
		float laisuat = (float) 3.8;
		long expected = 9500;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_3thang_khongquayvong() throws ParseException { //quá kỳ hạn không quay vòng
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-07-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 90;
		Long iddaohan = Long.valueOf(1);
		float laisuat = (float) 3.8;
		long expected = 9500;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void tinhlai_3thang_quayvonggoc_1() throws ParseException { //quá kỳ hạn quay vòng gốc 1 chu kỳ
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-07-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 90;
		Long iddaohan = Long.valueOf(2);
		float laisuat = (float) 3.8;
		long expected = 9900;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_3thang_quayvonggoc_2() throws ParseException { //bằng 2 chu kỳ quay vòng gốc
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-10-22";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 90;
		Long iddaohan = Long.valueOf(2);
		float laisuat = (float) 3.8;
		long expected = 19000;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_3thang_quayvonggoc_3() throws ParseException { //quá kỳ hạn quay vòng gốc 2 chu kỳ
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-10-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 90;
		Long iddaohan = Long.valueOf(2);
		float laisuat = (float) 3.8;
		long expected = 19533;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_3thang_quayvonggocvalai_1() throws ParseException { //quá kỳ hạn quay vòng gốc va lai 1 chu kỳ
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-07-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 90;
		Long iddaohan = Long.valueOf(3);
		float laisuat = (float) 3.8;
		long expected = 9903;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_3thang_quayvonggocvalai_2() throws ParseException { //bằng 2 chu kỳ quay vòng gốc va lãi
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-10-22";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 90;
		Long iddaohan = Long.valueOf(3);
		float laisuat = (float) 3.8;
		long expected = 19090;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	@Test
	public void tinhlai_3thang_quayvonggocvalai_3() throws ParseException { //quá kỳ hạn quay vòng gốc va lãi 2 chu kỳ
		//set ngay mo
				String ngay1 = "2021-04-25";
				String ngay2 = "2021-10-30";
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date ngaymo1 = sdf1.parse(ngay1);
				java.sql.Date ngaymo = new java.sql.Date(ngaymo1.getTime());
				java.util.Date ngaydong1 = sdf1.parse(ngay2);
				java.sql.Date ngaydong = new java.sql.Date(ngaydong1.getTime());
		
		long sotien = 1000000;
		int songaykyhan = 90;
		Long iddaohan = Long.valueOf(3);
		float laisuat = (float) 3.8;
		long expected = 19633;
		long actual = rutso.tinhlai(ngaymo,ngaydong, songaykyhan , sotien , iddaohan , laisuat);
		
		assertEquals(expected, actual);
		
	}
	/*end kỳ hạn 3 tháng*/
}
