package quanlysotietkiem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HomControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
    public void showgdDangNhap() throws Exception {
        mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	//test dang nhập
	@Test
	public void dangnhapthanhcong() throws Exception {
		String data = "?taikhoan=ngoquocan&matkhau=161199";
		mockMvc.perform(get("/trangchu" + data))
		.andExpect(status().isOk())
		.andExpect(view().name("gdchinh"));
	}
	@Test
	public void taikhoankhongtontai() throws Exception {
		String data = "?taikhoan=nguyenvana&matkhau=161199";
		mockMvc.perform(get("/trangchu" + data))
		.andExpect(status().isOk())
		.andExpect(view().name("dangnhap"));
	}
	@Test
	public void matkhausai() throws Exception {
		String data = "?taikhoan=ngoquocan&matkhau=123456";
		mockMvc.perform(get("/trangchu" + data))
		.andExpect(status().isOk())
		.andExpect(view().name("dangnhap"));
	}

	//gọi trang chủ
	@Test
	public void goitrangchu_thatbai() throws Exception {
		String id = "5";
        mockMvc.perform(get("/trangchu" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	@Test
	public void goitrangchu_thanhcong() throws Exception {
		String id = "1";
        mockMvc.perform(get("/trangchu" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("gdchinh"));
    }
	
	//gọi trang thông tin cá nhân
	@Test
	public void goithongtin_thatbai() throws Exception {
		String id = "5";
        mockMvc.perform(get("/ttkh" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	@Test
	public void goithongtin_thanhcong() throws Exception {
		String id = "1";
        mockMvc.perform(get("/ttkh" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("gdtknh"));
    }
	
	//gọi trang danh sách sổ tiết kiệm
	@Test
	public void goiliststk_thatbai() throws Exception {
		String id = "5";
        mockMvc.perform(get("/liststk" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	@Test
	public void goiliststk_thanhcong() throws Exception {
		String id = "1";
        mockMvc.perform(get("/liststk" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("rutso/gdliststk"));
    }
	
	//gọi trang danh sách sổ tiết kiệm theo ngay
	@Test
	public void goiliststktheongay_thatbai() throws Exception {
		String id = "5";
		String ngay = "?ngaymo=2021-05-30";
        mockMvc.perform(get("/liststktheongay" + id + ngay))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	@Test
	public void goiliststktheongay_thanhcong() throws Exception {
		String id = "1";
		String ngay = "?ngaymo=2021-05-30";
        mockMvc.perform(get("/liststktheongay" + id + ngay))
        .andExpect(status().isOk())
        .andExpect(view().name("rutso/gdliststk"));
    }

	//gọi trang xem thông báo
	@Test
	public void xemthongbao_thatbai() throws Exception {
		String idthongbao = "100";
        mockMvc.perform(get("/thongbao" + idthongbao))
        .andExpect(status().isOk())
        .andExpect(view().name("lsgd"));
    }
	@Test
	public void xemthongbao_thanhcong() throws Exception {
		String idthongbao = "38";
        mockMvc.perform(get("/thongbao" + idthongbao))
        .andExpect(status().isOk())
        .andExpect(view().name("lsgd"));
    }
	
	//gọi trang xem lsgd
	@Test
	public void xemlsgd_thatbai() throws Exception {
		String id = "5";
        mockMvc.perform(get("/lsgd" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("dangnhap"));
    }
	@Test
	public void xemlsgd_thanhcong() throws Exception {
		String id = "1";
        mockMvc.perform(get("/lsgd" + id))
        .andExpect(status().isOk())
        .andExpect(view().name("lsgd"));
    }
}

