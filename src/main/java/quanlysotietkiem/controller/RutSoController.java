package quanlysotietkiem.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class RutSoController {
	@Autowired
	private KhachHangRepository khRepository;

	@Autowired
	private SoTietKiemRepository stkRepository;
	
	@Autowired
	private TKNHRepository tknhRepository;
	
	@Autowired
	private ThongBaoRepository thongbaoRepository;
	
	@GetMapping("/rutso{id}")
	public String rutso(ModelMap model, @PathVariable("id") Long id) {
		Khachhang kh = khRepository.findById(id).get();
		model.addAttribute("tkmk", kh);
		List<Sotietkiem> liststk = stkRepository.findByKhachhang_id(kh.getId());
		model.addAttribute("liststk",liststk);
		
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		
		//lấy tạm đẻ làm stt
		Long stt = (long) 1;
			for(Sotietkiem stk : liststk) {
				stk.setTienlai(stt);
				stt++;
			}
		return "rutso/gdliststk";
	}
	@GetMapping("/gdrutso{id}")
	public String chitietrutso(ModelMap model, @PathVariable("id") Long id) {
		Sotietkiem stk = stkRepository.findById(id).get();
		Khachhang kh = khRepository.findById(stk.getKhachhang().getId()).get();
		
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		
		model.addAttribute("tkmk", kh);
		
		//set lại sổ tiết kiệm theo lãi ko thời hạn và ngày đóng là ngày hôm nay
			//ngaydong
			long mili = System.currentTimeMillis();
			java.sql.Date ngaydong = new java.sql.Date(mili);
			
			long laitong = tinhlai(stk.getNgaymo(),ngaydong,stk.getKyhan().getSongay(),stk.getSotien(),stk.getDaohan().getId(),stk.getKyhan().getLaisuat());
			stk.setTienlai(laitong);
		
		model.addAttribute("stk",stk);
		return "rutso/gdrutso";
	}
	@GetMapping("/tattoan{id}")
	public String xacnhanrutso(ModelMap model,@RequestParam(defaultValue = "") String otp, @PathVariable("id") Long id) {
		Sotietkiem stk = stkRepository.findById(id).get();
		Taikhoannganhang tknh = tknhRepository.findById(stk.getKhachhang().getTknh().getId()).get();
		Khachhang kh = khRepository.findById(stk.getKhachhang().getId()).get();
		model.addAttribute("tkmk", kh); 

		
		if (otp.equals("0000")) {
			//set lại sổ tiết kiệm theo lãi ko thời hạn và ngày đóng là ngày hôm nay
			//ngaydong
			long mili = System.currentTimeMillis();
			java.sql.Date ngaydong = new java.sql.Date(mili);
			stk.setNgaydong(ngaydong);
			//tinh so ngay
			long songay = (stk.getNgaydong().getTime() - stk.getNgaymo().getTime())/(24 * 60 * 60 * 1000);
			//tính lãi
			
			long laitong = tinhlai(stk.getNgaymo(),ngaydong,stk.getKyhan().getSongay(),stk.getSotien(),stk.getDaohan().getId(),stk.getKyhan().getLaisuat());
			stk.setTienlai(laitong);
		
		
			tknh.setSodu(tknh.getSodu() + stk.getSotien() + stk.getTienlai());
			stkRepository.delete(stk);
			//tạo lịch sử giao dịch
			long millis=System.currentTimeMillis();  
			java.sql.Date ngay = new java.sql.Date(millis);
			long milliss=System.currentTimeMillis();  
			java.sql.Time gio = new java.sql.Time(milliss);
			long tien = stk.getSotien() + stk.getTienlai();
			String noidung = "+ " + tien + " vnđ nội dung: tất toán sổ tiết kiệm";
			Thongbao thongbao = new Thongbao(ngay, gio, noidung, (long) 0, kh);
			thongbaoRepository.save(thongbao);
			//tạo thông báo
			List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
			Collections.reverse(listthongbao);
			model.addAttribute("soluongthongbao",listthongbao.size());
			model.addAttribute("listthongbao",listthongbao);
				
			List<Sotietkiem> liststk = stkRepository.findByKhachhang_id(kh.getId());
			//lấy tạm đẻ làm stt
			Long stt = (long) 1;
				for(Sotietkiem sstk : liststk) {
					sstk.setTienlai(stt);
					stt++;
				}
				model.addAttribute("liststk",liststk);
				return "rutso/gdliststk";
			}
		model.addAttribute("thongbao", "Mã OTP không chính xác");
		model.addAttribute("stk",stk);
		//tạo thông báo
				List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
				Collections.reverse(listthongbao);
				model.addAttribute("soluongthongbao",listthongbao.size());
				model.addAttribute("listthongbao",listthongbao);
		return "rutso/gdrutso";
	}
	
	public long tinhlai(Date ngaymo,Date ngaydong, int songaykyhan, long sotien, Long iddaohan, float laisuat) {
		
		
		//stk.setNgaydong(ngaydong);
		//tinh so ngay
		long songay = (ngaydong.getTime() - ngaymo.getTime())/(24 * 60 * 60 * 1000);
		long tienlaikyhan = (long) (sotien*laisuat*songaykyhan/360/100);
		if(songay < 0) {
			return 0;
		}
		//tính lãi
		long laitong = 0;
		if(songaykyhan != 0) {
			long tiengocquayvong = sotien; //để lưu tiền gốc trong chu kỳ mới
			while (songay >= (long) (songaykyhan )) 
			{
				if(iddaohan == 1) { // không đáo hạn
					songay = 0;
					
					laitong = laitong + tienlaikyhan;
				}
				else if(iddaohan == 2) { //đáo hạn gốc
					songay = songay - (long) (songaykyhan);
					laitong = laitong + tienlaikyhan;
				}
				else if(iddaohan == 3) { //đáo hạn gốc và lãi
					//set tiền lãi theo tiền gốc mới.
					//sonho.setSotien(tiengocquayvong);
					long tienlai = (long) (tiengocquayvong*laisuat*songaykyhan/360/100);
					songay = songay - (long) (songaykyhan);
					tiengocquayvong += tienlai;
					laitong = laitong + tienlai;
					
				}
			}
			if(songay > 0) {
				laitong += tiengocquayvong * 2.4 / 360 / 100 * songay;
			}
		}
		else if(songaykyhan == 0 ){
			long tienlai = (long) (sotien * laisuat / 360 / 100 * songay );
			laitong = laitong + tienlai;
		}
		return laitong;
	}
}
