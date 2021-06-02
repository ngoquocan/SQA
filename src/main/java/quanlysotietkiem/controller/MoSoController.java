package quanlysotietkiem.controller;


import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import quanlysotietkiem.entity.DaoHan;
import quanlysotietkiem.entity.Khachhang;
import quanlysotietkiem.entity.Kyhan;
import quanlysotietkiem.entity.Sotietkiem;
import quanlysotietkiem.entity.Taikhoannganhang;
import quanlysotietkiem.entity.Thongbao;
import quanlysotietkiem.repository.DaoHanRepository;
import quanlysotietkiem.repository.KhachHangRepository;
import quanlysotietkiem.repository.KyHanRepository;
import quanlysotietkiem.repository.SoTietKiemRepository;
import quanlysotietkiem.repository.TKNHRepository;
import quanlysotietkiem.repository.ThongBaoRepository;

@Controller
@RequestMapping()
public class MoSoController {
	@Autowired
	private KhachHangRepository khRepository;

	@Autowired
	private SoTietKiemRepository stkRepository;
	
	@Autowired
	private KyHanRepository kyhanRepository;
	
	@Autowired
	private TKNHRepository tknhRepository;
	
	@Autowired
	private DaoHanRepository daohanRepository;
	
	@Autowired
	private ThongBaoRepository thongbaoRepository;

	@GetMapping("/moso{id}")
	public String moso(ModelMap model, @PathVariable("id") Long id) {
		Optional<Khachhang> khotp = khRepository.findById(id);
		// không tồn tại id về trang đăng nhập
		if(khotp.isEmpty()) {
			model.addAttribute("tkmk", new Khachhang());
			return "dangnhap";
		}
		Khachhang kh = khotp.get();
		List<Kyhan> listkyhan = kyhanRepository.findAll();
		List<DaoHan> listdaohan = daohanRepository.findAll();
		model.addAttribute("listkyhan",listkyhan);
		model.addAttribute("listdaohan",listdaohan);
		model.addAttribute("tkmk", kh);
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		return "moso/gdmoso";
	}

	@GetMapping("/xacthucSTK{id}")
	public String xacthucSTK(ModelMap model, @PathVariable("id") Long id,
			@RequestParam(defaultValue = "") String sotien, @RequestParam(defaultValue = "") String kyhan,@RequestParam(defaultValue = "") String daohan ) {
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
				
		//check kỳ hạn đáo hạn không hợi lệ junit
		Optional<Kyhan> kyhanotp = kyhanRepository.findById(Long.parseLong(kyhan));
		Optional<DaoHan> daohanotp = daohanRepository.findById(Long.parseLong(daohan));
		if(kyhanotp.isEmpty() || daohanotp.isEmpty()) {
			List<Kyhan> listkyhan = kyhanRepository.findAll();
			List<DaoHan> listdaohan = daohanRepository.findAll();
			model.addAttribute("listkyhan",listkyhan);
			model.addAttribute("listdaohan",listdaohan);
			model.addAttribute("tkmk", kh);
			return "moso/gdmoso";
		}
		
		
		Sotietkiem stk = new Sotietkiem();
		if (Long.parseLong(sotien) <= kh.getTknh().getSodu()-50000) {
				//set kỳ hạn
				stk.setKyhan(kyhanotp.get());
				stk.setSotien(Long.parseLong(sotien));
				stk.setKhachhang(kh);
				
				//setdaohan
				stk.setDaohan(daohanotp.get());
				
				//tạo mã sổ tiết kiệm
				String ngay = java.time.LocalDate.now().toString().strip(); //dang 2019-01-22
				String mangay = ngay.substring(2,4) + ngay.substring(5,7) + ngay.substring(8); // [)
				String gio = java.time.LocalTime.now().toString().strip(); //dang 09:01:14.341
				String magio = gio.substring(0,2) + gio.substring(3,5) + gio.substring(6,8);
				stk.setMaStk(mangay + magio);
				
				//ngày tạo stk
				long mili = System.currentTimeMillis();
				java.sql.Date ngaymo = new java.sql.Date(mili);
				
				stk.setNgaymo(ngaymo);
				
				//ngay dong stk
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(stk.getNgaymo());
				cal.add(GregorianCalendar.DAY_OF_MONTH, stk.getKyhan().getSongay());
				  //đổi java.until.date sang java.sql.date
				java.sql.Date ngaydong = new java.sql.Date(cal.getTime().getTime());
				stk.setNgaydong(ngaydong);
				
				//java.sql.Date sqlDate = java.sql.Date.valueOf( cal.getTime() );
				//stk.setNgaydong(cal.getTime());
				
				//tinh tien lai
				long tienlai = (long) (stk.getSotien()*stk.getKyhan().getLaisuat()*stk.getKyhan().getKyhan()/12/100); //sotien * laisuattheonam * kyhan / 12 / 100
				stk.setTienlai(tienlai);
				
				
				model.addAttribute("tkmk", kh);
				model.addAttribute("stk", stk);
				return "moso/gdxacnhanSTK";
		}
		else if(Long.parseLong(sotien) > kh.getTknh().getSodu()) {
			model.addAttribute("thongbao", "Số dư của bạn không đủ");
		}
		else{
			model.addAttribute("thongbao", "Số dư còn lại tối thiểu 50.000 VNĐ");
		}
		List<Kyhan> listkyhan = kyhanRepository.findAll();
		List<DaoHan> listdaohan = daohanRepository.findAll();
		model.addAttribute("listkyhan",listkyhan);
		model.addAttribute("listdaohan",listdaohan);
		model.addAttribute("tkmk", kh);
		return "moso/gdmoso";
	}

	@GetMapping("/luuSTK{id}")
	public String luuSTK(ModelMap model, Sotietkiem stk, @PathVariable("id") Long id,
			@RequestParam(defaultValue = "") String otp,@RequestParam(defaultValue = "") String kyhan, @RequestParam(defaultValue = "") String daohan) {
		Optional<Khachhang> khotp = khRepository.findById(id);
		// không tồn tại id về trang đăng nhập
		if(khotp.isEmpty()) {
			model.addAttribute("tkmk", new Khachhang());
			return "dangnhap";
		}
		Khachhang kh = khotp.get();
		
		stk.setKhachhang(kh);
		if (otp.equals("0000")) {
			stk.setKhachhang(kh);
			List<Kyhan> listkyhan = kyhanRepository.findByTenkyhan(kyhan);
			for(Kyhan a : listkyhan ) {
				stk.setKyhan(a);
			}
			stk.setDaohan(daohanRepository.findById(Long.parseLong(daohan)).get());
			stkRepository.save(stk); //lưu tt sôt tk
			
			//trừ tiền trong tk
			Taikhoannganhang tknh = kh.getTknh();
			tknh.setSodu(tknh.getSodu() - stk.getSotien());
			tknhRepository.save(tknh);
			
			List<Sotietkiem> lso = stkRepository.findByMaStk(stk.getMaStk());
			for(Sotietkiem x : lso) {
				model.addAttribute("stk", x);
			}
			
			//tạo lịch sử giao dịch
			long millis=System.currentTimeMillis();  
			java.sql.Date ngay = new java.sql.Date(millis);
			long milliss=System.currentTimeMillis();  
			java.sql.Time gio = new java.sql.Time(milliss);
			String noidung = "- " + stk.getSotien() + " vnđ nội dung: mở sổ tiết kiệm";
			Thongbao thongbao = new Thongbao(ngay, gio, noidung, (long) 0, kh);
			thongbaoRepository.save(thongbao);
			//tạo thông báo
			List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
			Collections.reverse(listthongbao);
			model.addAttribute("soluongthongbao",listthongbao.size());
			model.addAttribute("listthongbao",listthongbao);
						
			model.addAttribute("tkmk", kh);
			//gọi đến gd xem ds sổ tiết kiệm hiện có
			return "moso/chitietstk";
		} else {
			model.addAttribute("thongbao", "Mã OTP không chính xác");
			model.addAttribute("tkmk",kh);
			model.addAttribute("stk", stk);
			//tạo thông báo
			List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
			Collections.reverse(listthongbao);
			model.addAttribute("soluongthongbao",listthongbao.size());
			model.addAttribute("listthongbao",listthongbao);
			
			return "moso/gdxacnhanSTK";
		}
	}
	@GetMapping("/chitietstk{id}")
	public String chitietsotietkiem(ModelMap model,@PathVariable("id") Long id) {
		Sotietkiem stk = stkRepository.findById(id).get();
		Khachhang kh = stk.getKhachhang();
		model.addAttribute("tkmk",kh);
		model.addAttribute("stk",stk);
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
				
		return "moso/chitietstk";
	}
}
