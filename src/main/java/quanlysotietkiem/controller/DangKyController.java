package quanlysotietkiem.controller;

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
import quanlysotietkiem.entity.Taikhoannganhang;
import quanlysotietkiem.repository.KhachHangRepository;
import quanlysotietkiem.repository.TKNHRepository;

@Controller
@RequestMapping()
public class DangKyController {	
	@Autowired
	private TKNHRepository tknhRepository;
	@Autowired
	private KhachHangRepository khRepository;
	
	@GetMapping("/dangky")
	public String nhapthongtinTKNH(ModelMap model) {
		model.addAttribute("tknh", new Taikhoannganhang());
		return "dangky/gdnhapthongtinTKNH";
	}
	
	@GetMapping("/xacthucTKNH")
	public String xacthucTKNH(ModelMap model, Taikhoannganhang tknh) {
		List<Taikhoannganhang> listtk = tknhRepository.findBySotaikhoan(tknh.getSotaikhoan());
		for (Taikhoannganhang tk : listtk) {
			if(tk.getCmnd().equals(tknh.getCmnd()) && tk.getSdt().equals(tknh.getSdt()) && tk.getEmail().equals(tknh.getEmail()))
			{	
				//đã có tài khoản về trang đăng nhập
				List<Khachhang> listKH = khRepository.findBySotaikhoan_id(tk.getId());
				if(listKH.size() != 0) {
					model.addAttribute("thongbao", "Số tài khoản đã được đăng ký, vui lòng đăng nhập!!!");
					model.addAttribute("tkmk",new Khachhang());
					return "dangnhap";
				}
				else {
					model.addAttribute("tknh",tk);
					return "dangky/gdxacthucTKNH";
				}
			}
			model.addAttribute("thongbao", "Thông tin không khớp!!!");
			model.addAttribute("tknh",tknh);
			return "dangky/gdnhapthongtinTKNH";
		}
		model.addAttribute("thongbao", "Số tài khoản không tồn tại!!!");
		model.addAttribute("tknh",tknh);
		return "dangky/gdnhapthongtinTKNH";
	}
	@GetMapping("/dangkyTKMK{id}")
	public String dangkyTKMK(ModelMap model, @PathVariable("id") Long id) {
		Khachhang tkmk = new Khachhang();
		
		//Tìm tknh theo id tknh
		Optional<Taikhoannganhang> tknhotp = tknhRepository.findById(id);
		//tknh ko tồn tại
		if(tknhotp.isEmpty()) {
			model.addAttribute("thongbao", "Tài khoản không tồn tại");
			model.addAttribute("tknh",new Taikhoannganhang());
			return "dangky/gdnhapthongtinTKNH";
		}
		Taikhoannganhang tknh = tknhotp.get();
		//Tknh đã đăng ký tkmk
		List<Khachhang> listKH = khRepository.findBySotaikhoan_id(tknh.getId());
		if(listKH.size() != 0) {
			model.addAttribute("thongbao", "Số tài khoản đã được đăng ký, vui lòng đăng nhập!!!");
			model.addAttribute("tkmk",new Khachhang());
			return "dangnhap";
		}
		tkmk.setTknh(tknh);
		model.addAttribute("tkmk", tkmk);
		return "dangky/gdDangKyTKMK";
	}
	@GetMapping("/luuTKMK{id}")
	public String luuTKMK(ModelMap model,Khachhang tkmk,@RequestParam(defaultValue = "")  String otp,@PathVariable("id") Long id) {
		//Tìm tknh theo id tknh
		Optional<Taikhoannganhang> tknhotp = tknhRepository.findById(id);
		//tknh ko tồn tại
		if(tknhotp.isEmpty()) {
			model.addAttribute("thongbao", "Tài khoản không tồn tại");
			model.addAttribute("tknh",new Taikhoannganhang());
			return "dangky/gdnhapthongtinTKNH";
		}
		Taikhoannganhang tknh = tknhotp.get();
		//Tknh đã đăng ký tkmk
		List<Khachhang> listKH = khRepository.findBySotaikhoan_id(tknh.getId());
		if(listKH.size() != 0) {
			model.addAttribute("thongbao", "Số tài khoản đã được đăng ký, vui lòng đăng nhập!!!");
			model.addAttribute("tkmk",new Khachhang());
			return "dangnhap";
		}
		
		//tìm theo tên đăng nhập	
		List<Khachhang> listkh = khRepository.findByTaikhoan(tkmk.getTaikhoan());		
		//kiểm tra tên đăng nhập đã đc dùng chưa
		if(listkh.isEmpty()) {	
			if(otp.equals("0000")) {
				tkmk.setTknh(tknh);
				khRepository.save(tkmk);
				model.addAttribute("tkmk",tkmk);
				return "dangnhap";
			}
			else {
				model.addAttribute("thongbao", "Mã OTP không chính xác");
				tkmk.setTknh(tknh);
				model.addAttribute("tkmk", tkmk);
				return "dangky/gdDangKyTKMK";
			}
		}
		model.addAttribute("thongbao", "Tên đăng nhập đã được sử dụng");
		tkmk.setTknh(tknh);
		model.addAttribute("tkmk", tkmk);
		return "dangky/gdDangKyTKMK";
	}
}
