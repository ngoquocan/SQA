package quanlysotietkiem.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quanlysotietkiem.entity.Khachhang;
import quanlysotietkiem.entity.Sotietkiem;
import quanlysotietkiem.entity.Taikhoannganhang;
import quanlysotietkiem.entity.Thongbao;
import quanlysotietkiem.repository.KhachHangRepository;
import quanlysotietkiem.repository.SoTietKiemRepository;
import quanlysotietkiem.repository.TKNHRepository;
import quanlysotietkiem.repository.ThongBaoRepository;

@Controller
@RequestMapping()
public class HomeController {
	@Autowired
	private TKNHRepository tknhRepository;
	@Autowired
	private KhachHangRepository khRepository;
	
	@Autowired
	private SoTietKiemRepository stkRepository;
	
	@Autowired
	private ThongBaoRepository thongbaoRepository;
	
	
	@GetMapping
	public String dangnhap(ModelMap model) {
		model.addAttribute("tkmk", new Khachhang());
		return "dangnhap";
	}
	@GetMapping("/trangchu")
	public String kiemtradangnhap(ModelMap model,Khachhang tkmk) {
		if(tkmk.getTaikhoan().equals("admin") && tkmk.getMatkhau().equals("1234")) {
			return "admin/gdadmin";
		}
		List<Khachhang> listkh = khRepository.findByTaikhoan(tkmk.getTaikhoan());
		if(listkh.size() == 0) {
			model.addAttribute("thongbao", "Tài khoản không tồn tại");
			model.addAttribute("tkmk",tkmk);
			return "dangnhap";
		}
		else {
			for(Khachhang kh : listkh) {
				if(kh.getMatkhau().equals(tkmk.getMatkhau())) {
					model.addAttribute("tkmk",kh);
					//tạo thông báo
					List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
					Collections.reverse(listthongbao);
					model.addAttribute("soluongthongbao",listthongbao.size());
					model.addAttribute("listthongbao",listthongbao);
					return "gdchinh";
				}
			}
		}
		model.addAttribute("thongbao", "Mật khẩu không chính xác");
		model.addAttribute("tkmk",tkmk);
		return "dangnhap";
	}
	@GetMapping("/trangchu{id}")
	public String gdchinhgoitrangchu(ModelMap model,@PathVariable("id") Long id) {
		Optional<Khachhang> khotp = khRepository.findById(id);
		// không tồn tại id về trang đăng nhập
		if(khotp.isEmpty()) {
			model.addAttribute("tkmk", new Khachhang());
			return "dangnhap";
		}
		Khachhang kh = khotp.get();
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		model.addAttribute("tkmk",kh);
		return "gdchinh";
	}
	@GetMapping("/ttkh{id}")
	public String thongtinkhachhang(ModelMap model,@PathVariable("id") Long id) {
		Optional<Khachhang> khotp = khRepository.findById(id);
		// không tồn tại id về trang đăng nhập
		if(khotp.isEmpty()) {
			model.addAttribute("tkmk", new Khachhang());
			return "dangnhap";
		}
		Khachhang kh = khotp.get();
		Taikhoannganhang tknh = kh.getTknh();
		model.addAttribute("tkmk",kh);
		model.addAttribute("tknh",tknh);
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		return "gdtknh";
	}
	@GetMapping("/themtaikhoan")
	public String themtaikhoannganhang(ModelMap model) {
		model.addAttribute("tknh", new Taikhoannganhang());
		return "dangky/themtaikhoan";
	}
	@PostMapping("/luutaikhoan")
	public String luutaikhoannganhang(ModelMap model, Taikhoannganhang tknh) throws ParseException {
		String ngaysinh1 = tknh.getNgaysinh1();
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ngaysinh = sdf1.parse(ngaysinh1);
		java.sql.Date ngaysinh2 = new java.sql.Date(ngaysinh.getTime());

		
		tknh.setNgaysinh(ngaysinh2);
		tknhRepository.save(tknh);
		model.addAttribute("tknh",tknh);
		return "dangky/themtaikhoan";
	}
	
	@GetMapping("/test")
	public String test(ModelMap model) {
		model.addAttribute("tkmk", new Khachhang());
		return "test";
	}
	@GetMapping("/liststk{id}")
	public String liststk(ModelMap model, @PathVariable("id") Long id) {
		Optional<Khachhang> khotp = khRepository.findById(id);
		// không tồn tại id về trang đăng nhập
		if(khotp.isEmpty()) {
			model.addAttribute("tkmk", new Khachhang());
			return "dangnhap";
		}
		Khachhang kh = khotp.get();
		model.addAttribute("tkmk", kh);
		List<Sotietkiem> liststk = stkRepository.findByKhachhang_id(id);
		
		//lấy tạm đẻ làm stt
		Long stt = (long) 1;
		for(Sotietkiem stk : liststk) {
			stk.setTienlai(stt);
			stt++;
		}
		model.addAttribute("liststk",liststk);
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		return "rutso/gdliststk";
	}
	@GetMapping("/liststktheongay{id}")
	public String liststktheongay(ModelMap model, @PathVariable("id") Long id,@RequestParam(defaultValue = "")  String ngaymo) throws ParseException {
		Optional<Khachhang> khotp = khRepository.findById(id);
		// không tồn tại id về trang đăng nhập
		if(khotp.isEmpty()) {
			model.addAttribute("tkmk", new Khachhang());
			return "dangnhap";
		}
		Khachhang kh = khotp.get();
		model.addAttribute("tkmk", kh);
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		List<Sotietkiem> liststk ;
		if(ngaymo.equals("")) {
			liststk = stkRepository.findByKhachhang_id(id);
		}
		else {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date ngaymo_util= sdf1.parse(ngaymo);
			java.sql.Date ngaymo_sql = new java.sql.Date(ngaymo_util.getTime());
			liststk = stkRepository.findByKhachhang_idAndNgaymo(id, ngaymo_sql);
		}
		
		
		//lấy tạm đẻ làm stt
		Long stt = (long) 1;
		for(Sotietkiem stk : liststk) {
			stk.setTienlai(stt);
			stt++;
		}
		if(liststk.size()==0) {
			model.addAttribute("thongbao", "Không tìm thấy !!!");
			model.addAttribute("liststk",liststk);
			return "rutso/gdliststk";
		}
		model.addAttribute("liststk",liststk);
		return "rutso/gdliststk";
	}
	
	@GetMapping("/lsgd{id}")
	public String lichsugiaodich(ModelMap model, @PathVariable("id") Long id) {
		Optional<Khachhang> khotp = khRepository.findById(id);
		// không tồn tại id về trang đăng nhập
		if(khotp.isEmpty()) {
			model.addAttribute("tkmk", new Khachhang());
			return "dangnhap";
		}
		Khachhang kh = khotp.get();
		model.addAttribute("tkmk", kh);
		List<Thongbao> listlsgd = thongbaoRepository.findByKhachhang_id(id);
		Collections.reverse(listlsgd);
		//set trạng thái và set stt hiển thị
		Long stt = (long) 1;
		for(Thongbao thongbao : listlsgd) {
			thongbao.setTrangthai(1);
			thongbaoRepository.save(thongbao);
			//lưu tạm stt hiển thị	
			thongbao.setTrangthai(stt);
			stt++;
		}
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		
		model.addAttribute("listlsgd",listlsgd);
		return "lsgd";
	}
	
	@GetMapping("/lsgdtheongay{id}")
	public String lichsugiaodichtheongay(ModelMap model, @PathVariable("id") Long id,@RequestParam(defaultValue = "")  String ngay) throws ParseException {
		Khachhang kh = khRepository.findById(id).get();
		model.addAttribute("tkmk", kh);
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		
		List<Thongbao> listlsgd ;
		if(ngay.equals("")) {
			listlsgd = thongbaoRepository.findByKhachhang_id(id); //không nhập ngày thì tìm tất
		}
		else {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date ngay_util= sdf1.parse(ngay);
			java.sql.Date ngay_sql = new java.sql.Date(ngay_util.getTime());
			listlsgd = thongbaoRepository.findByKhachhang_idAndNgay(id, ngay_sql);
		}
		
		
		//set trạng thái
		Long stt = (long) 1;
		for(Thongbao thongbao : listlsgd) {
			thongbao.setTrangthai(1);
			thongbaoRepository.save(thongbao);
			//lưu tạm stt hiển thị		
			thongbao.setTrangthai(stt);
			stt++;
		}
		if(listlsgd.size()==0) {
			model.addAttribute("thongbao", "Không tìm thấy !!!");
			model.addAttribute("lsgd",listlsgd);
			return "lsgd";
		}
		model.addAttribute("listlsgd",listlsgd);
		return "lsgd";
	}
	
	@GetMapping("/thongbao{id}")
	public String xemthongbao(ModelMap model, @PathVariable("id") Long id) {
		Thongbao thongbao = thongbaoRepository.findById(id).get();
		Khachhang kh = thongbao.getKhachhang();
		model.addAttribute("tkmk", kh);
		thongbao.setTrangthai(1);
		thongbaoRepository.save(thongbao);
		//lấy tạm đẻ làm stt
		thongbao.setTrangthai(1);
		model.addAttribute("listlsgd",thongbao);
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		return "lsgd";
	}
}
