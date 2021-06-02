package quanlysotietkiem;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import quanlysotietkiem.entity.Khachhang;
import quanlysotietkiem.entity.Taikhoannganhang;
import quanlysotietkiem.repository.KhachHangRepository;
import quanlysotietkiem.repository.TKNHRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class MoSoControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private KhachHangRepository khRepository;
	
	// moso()
	@Test
	public void goigdMoso_id_fail() throws Exception { //id khách hang khong tồn tại
		String data = "0";
		mockMvc.perform(get("/moso" + data))
		.andExpect(status().isOk())
		.andExpect(view().name("dangnhap"));
	}
	@Test
	public void goigdMoso_id_okay() throws Exception { //id hợp lệ
		String data = "1";
		mockMvc.perform(get("/moso" + data))
		.andExpect(status().isOk())
		.andExpect(view().name("moso/gdmoso"));
	}
	// xacthucSTK()
	@Test
	public void xacthucSTK_id_fail() throws Exception { //id khách hang khong tồn tại
		String data = "0";
		String data1 = "?sotien=1000000"
				+ "&kyhan=1"
				+ "&daohan=2";
		mockMvc.perform(get("/xacthucSTK" + data + data1))
		.andExpect(status().isOk())
		.andExpect(view().name("dangnhap"));
	}
	@Test
	public void xacthucSTK_sodukhongdu() throws Exception { //Số dư không đủ
		String data = "1";
		Khachhang kh = khRepository.findById(Long.parseLong(data)).get();
		Long sotien = kh.getTknh().getSodu() + 100000 ;
		if(sotien > 1000000) {
		String data1 = "?sotien=" + sotien
				+ "&kyhan=1"
				+ "&daohan=2";
		mockMvc.perform(get("/xacthucSTK" + data + data1))
		.andExpect(status().isOk())
		.andExpect(view().name("moso/gdmoso"));
		}
	}
	@Test
	public void xacthucSTK_soduconlaiFail() throws Exception { //Số dư còn lại không đủ 50000
		String data = "1";
		Khachhang kh = khRepository.findById(Long.parseLong(data)).get(); //lấy số dư
		Long sotien = kh.getTknh().getSodu() - 20000 ; //set tiền test
		if(sotien > 1000000) {
		String data1 = "?sotien=" + sotien
				+ "&kyhan=1"
				+ "&daohan=2";
		mockMvc.perform(get("/xacthucSTK" + data + data1))
		.andExpect(status().isOk())
		.andExpect(view().name("moso/gdmoso"));
		}
	}
	@Test
	public void xacthucSTK_soduconlai50K() throws Exception { //Số dư còn lại bằng đúng 50k
		String data = "1";
		Khachhang kh = khRepository.findById(Long.parseLong(data)).get(); //lấy số dư
		Long sotien = kh.getTknh().getSodu() - 50000 ; //set tiền test
		if(sotien > 1000000) {
		String data1 = "?sotien=" + sotien
				+ "&kyhan=1"
				+ "&daohan=2";
		mockMvc.perform(get("/xacthucSTK" + data + data1))
		.andExpect(status().isOk())
		.andExpect(view().name("moso/gdxacnhanSTK"));
		}
	}
	@Test
	public void xacthucSTK_okay() throws Exception { //okay
		String data = "1";
		Khachhang kh = khRepository.findById(Long.parseLong(data)).get(); //lấy số dư
		Long sotien = kh.getTknh().getSodu() - 60000 ; //set tiền test
		if(sotien > 1000000) {
		String data1 = "?sotien=" + sotien
				+ "&kyhan=1"
				+ "&daohan=2";
		mockMvc.perform(get("/xacthucSTK" + data + data1))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("moso/gdxacnhanSTK"));
		}
	}
	@Test
	public void xacthucSTK_kyhanfail() throws Exception { //kỳ hạn không tồn tại
		String data = "1";
		Khachhang kh = khRepository.findById(Long.parseLong(data)).get(); //lấy số dư
		Long sotien = kh.getTknh().getSodu() - 60000 ; //set tiền test
		if(sotien > 1000000) {
		String data1 = "?sotien=" + sotien
				+ "&kyhan=0"
				+ "&daohan=2";
		mockMvc.perform(get("/xacthucSTK" + data + data1))
		.andExpect(status().isOk())
		.andExpect(view().name("moso/gdmoso"));
		}
	}
	@Test
	public void xacthucSTK_daohanfail() throws Exception { //dao hạn không tồn tại
		String data = "1";
		Khachhang kh = khRepository.findById(Long.parseLong(data)).get(); //lấy số dư
		Long sotien = kh.getTknh().getSodu() - 60000 ; //set tiền test
		if(sotien > 1000000) {
		String data1 = "?sotien=" + sotien
				+ "&kyhan=1"
				+ "&daohan=0";
		mockMvc.perform(get("/xacthucSTK" + data + data1))
		.andExpect(status().isOk())
		.andExpect(view().name("moso/gdmoso"));
		}
	}	
	// luuSTK()
	@Test
	@Transactional
	public void luuSTK_id_fail() throws Exception { //id khách hang khong tồn tại
		String data = "0";
		String data1 = "?maStk=210531232531"
				+ "&ngaymo=2021-05-31"
				+ "&ngaydong=2021-06-30"
				+ "&kyhan=1"
				+ "&daohan=1"
				+ "&sotien=1000000"
				+ "&tienlai=2916"
				+ "&otp=0000";
		mockMvc.perform(get("/luuSTK" + data + data1))
		.andExpect(status().isOk())
		.andExpect(view().name("dangnhap"));
	}
	@Test
	@Transactional
	public void luuSTK_OTPfail() throws Exception { //OTP sai
		String data = "1";
		String data1 = "?maStk=210531232531"
				+ "&ngaymo=2021-05-31"
				+ "&ngaydong=2021-06-30"
				+ "&kyhan=1"
				+ "&daohan=1"
				+ "&sotien=1000000"
				+ "&tienlai=2916"
				+ "&otp=1234";
		mockMvc.perform(get("/luuSTK" + data + data1))
		.andExpect(status().isOk())
		.andExpect(view().name("moso/gdxacnhanSTK"));
	}
	@Test
	@Transactional
	public void luuSTK_Okay() throws Exception { 
		String data = "1";
		String data1 = "?maStk=210531232531"
				+ "&ngaymo=2021-05-31"
				+ "&ngaydong=2021-06-30"
				+ "&kyhan=1"
				+ "&daohan=1"
				+ "&sotien=1000000"
				+ "&tienlai=2916"
				+ "&otp=0000";
		mockMvc.perform(get("/luuSTK" + data + data1))
		.andExpect(status().isOk())
		.andExpect(view().name("moso/chitietstk"));
	}
	public void luuSTK_kyhanfail() {
		
	}
	public void luuSTK_daohanfail() {
		
	}
	public void luuSTK_ngaydongkhokhopkyhan() {
		
	}
}
