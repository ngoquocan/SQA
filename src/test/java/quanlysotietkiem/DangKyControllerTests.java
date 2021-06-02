package quanlysotietkiem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;

import quanlysotietkiem.controller.DangKyController;
import quanlysotietkiem.entity.Taikhoannganhang;

@SpringBootTest
@AutoConfigureMockMvc
public class DangKyControllerTests {
	@Autowired
	private MockMvc mockMvc;
	//method nhapthongtinTKNH()
	@Test
    public void showgdNhapTKNH() throws Exception {
        mockMvc.perform(get("/dangky"))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdnhapthongtinTKNH"));
    }
	
	//method xacthucTKNH()
	@Test
	public void xacthucTKNH_thanhcong() throws Exception {
		String data = "?sotaikhoan=1000158986"
				+ "&sdt=0948481234"
				+ "&email=nguyenvana@gmail.com"
				+ "&cmnd=145871895";
        mockMvc.perform(get("/xacthucTKNH" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdxacthucTKNH"));
    }
	@Test
	public void xacthucTKNH_dacotaikhoan() throws Exception {
		String data = "?sotaikhoan=9876543210&"
				+ "sdt=0386126392"
				+ "&email=ngoquocan161199@gmail.com"
				+ "&cmnd=145871784";
        mockMvc.perform(get("/xacthucTKNH" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	@Test
	public void xacthucTKNH_saiSTK() throws Exception {
		String data = "?sotaikhoan=1000150000"
				+ "&sdt=0948481234"
				+ "&email=nguyenvana@gmail.com"
				+ "&cmnd=145871895";
        mockMvc.perform(get("/xacthucTKNH" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdnhapthongtinTKNH"));
    }
	@Test
	public void xacthucTKNH_saiSDT() throws Exception {
		String data = "?sotaikhoan=1000158986"
				+ "&sdt=0948480000"
				+ "&email=nguyenvana@gmail.com"
				+ "&cmnd=145871895";
        mockMvc.perform(get("/xacthucTKNH" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdnhapthongtinTKNH"));
    }
	@Test
	public void xacthucTKNH_saiEmail() throws Exception {
		String data = "?sotaikhoan=1000158986"
				+ "&sdt=0948481234"
				+ "&email=abcde@gmail.com"
				+ "&cmnd=145871895";
        mockMvc.perform(get("/xacthucTKNH" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdnhapthongtinTKNH"));
    }
	@Test
	public void xacthucTKNH_saiCMT() throws Exception {
		String data = "?sotaikhoan=1000158986"
				+ "&sdt=0948481234"
				+ "&email=nguyenvana@gmail.com"
				+ "&cmnd=145870000";
        mockMvc.perform(get("/xacthucTKNH" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdnhapthongtinTKNH"));
    }
	@Test
	public void xacthucTKNH_nhieutruong() throws Exception {
		String data = "?sotaikhoan=1000150000"
				+ "&sdt=0948480000"
				+ "&email=nguyenvana@gmail.com"
				+ "&cmnd=145870000";
        mockMvc.perform(get("/xacthucTKNH" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdnhapthongtinTKNH"));
    }
	
	//method dangkyTKMK()
	@Test
	public void showgdDangKyTKMK_dacotkmk() throws Exception {
		String data = "2";
        mockMvc.perform(get("/dangkyTKMK" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	@Test
	public void showgdDangKyTKMK_hople() throws Exception {
		String data = "5";
        mockMvc.perform(get("/dangkyTKMK" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdDangKyTKMK"));
    }
	@Test
	public void showgdDangKyTKMK_khongtontai() throws Exception {
		String data = "0";
        mockMvc.perform(get("/dangkyTKMK" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdnhapthongtinTKNH"));
    }
	
	//method luuTKMK()	
	@Test
	public void luuTKMK_dacotkmk() throws Exception {
		String data = "2";
        mockMvc.perform(get("/luuTKMK" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	@Test
	public void luuTKMK_khongtontai() throws Exception {
		String data = "0";
        mockMvc.perform(get("/luuTKMK" + data))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdnhapthongtinTKNH"));
    }
	@Test
	@Transactional
	public void luuTKMK_thanhcong() throws Exception {
		String data = "5";
		String data1 = "?taikhoan=nguyenvana"
				+ "&matkhau=161199"
				+ "&otp=0000";
        mockMvc.perform(get("/luuTKMK" + data + data1))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	@Test
	public void luuTKMK_taikhoandadcsudung() throws Exception {
		String data = "5";
		String data1 = "?taikhoan=ngoquocan"
				+ "&matkhau=161199"
				+ "&otp=0000";
        mockMvc.perform(get("/luuTKMK" + data + data1))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdDangKyTKMK"));
    }
	@Test
	public void luuTKMK_OTPsai() throws Exception {
		String data = "5";
		String data1 = "?taikhoan=nguyenvana"
				+ "&matkhau=161199"
				+ "&otp=1234";
        mockMvc.perform(get("/luuTKMK" + data + data1))
        .andExpect(status().isOk())
        .andExpect(view().name("dangky/gdDangKyTKMK"));
    }
	
}
