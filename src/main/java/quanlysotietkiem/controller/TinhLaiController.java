package quanlysotietkiem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import quanlysotietkiem.entity.DaoHan;
import quanlysotietkiem.entity.Khachhang;
import quanlysotietkiem.entity.Kyhan;
import quanlysotietkiem.entity.Sotietkiem;
import quanlysotietkiem.entity.Thongbao;
import quanlysotietkiem.repository.KhachHangRepository;
import quanlysotietkiem.repository.KyHanRepository;
import quanlysotietkiem.repository.SoTietKiemRepository;
import quanlysotietkiem.repository.ThongBaoRepository;

@Controller
@RequestMapping()
public class TinhLaiController {
	@Autowired
	private KhachHangRepository khRepository;

	@Autowired
	private SoTietKiemRepository stkRepository;
	
	@Autowired
	private KyHanRepository kyhanRepository;
	
	@Autowired
	private ThongBaoRepository thongbaoRepository;
	
	
	@GetMapping("/tinhlai{id}")
	public String tinhlai(ModelMap model, @PathVariable("id") Long id) {
		Khachhang kh = khRepository.findById(id).get();
		model.addAttribute("tkmk", kh);
		
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		
		List<Sotietkiem> liststk = stkRepository.findByKhachhang_id(kh.getId());
		model.addAttribute("liststk",liststk);
		/*
		List<String> stt = new ArrayList<String>();
		for(int i=0;i<liststk.size();i++) {
			stt.add(String.valueOf(i));
		}
		model.addAttribute("stt",stt);
		*/
		
		//lấy tạm đẻ làm stt
		Long stt = (long) 1;
		for(Sotietkiem stk : liststk) {
			stk.setTienlai(stt);
			stt++;
		}
		return "rutso/gdliststk";
	}
	@GetMapping("/gdtinhlai{id}")
	public String chitiettinhlai(ModelMap model, @PathVariable("id") Long id) {
		Sotietkiem stk = stkRepository.findById(id).get();
		Khachhang kh = khRepository.findById(stk.getKhachhang().getId()).get();
		
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		
		model.addAttribute("tkmk", kh);
		stk.setNgaydong(null);
		model.addAttribute("stk",stk);
		
		//set tổng cộng trong lần gọi đầu tiên
		Sotietkiem stktong = new Sotietkiem();
		Kyhan kyhan = new Kyhan();
		kyhan.setSongay((int) 0);
		kyhan.setTenkyhan("Tổng cộng:");
		stktong.setKyhan(kyhan);
		stktong.setTienlai(0);
		stktong.setSotien(0);
		stktong.setTongtien(0);
		DaoHan dh = new DaoHan();
		stktong.setDaohan(dh);

		model.addAttribute("stktong",stktong);
		return "tinhlai/gdtinhlai";
	}
	@GetMapping("/tinhlaistk{id}")
	public String chitiettinhlaitheongay(ModelMap model, @PathVariable("id") Long id,@RequestParam(defaultValue = "") String ngaydong)  throws ParseException  {
		Sotietkiem stk = stkRepository.findById(id).get();
		Khachhang kh = khRepository.findById(stk.getKhachhang().getId()).get();
		model.addAttribute("tkmk", kh);
		
		//tạo thông báo
		List<Thongbao> listthongbao = thongbaoRepository.findByKhachhang_idAndTrangthai(kh.getId(), 0);
		Collections.reverse(listthongbao);
		model.addAttribute("soluongthongbao",listthongbao.size());
		model.addAttribute("listthongbao",listthongbao);
		
		//set ngay đóng giả định
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date ngaydong1 = sdf1.parse(ngaydong);
		java.sql.Date ngaydong2 = new java.sql.Date(ngaydong1.getTime());
		stk.setNgaydong(ngaydong2);
		
		//tinh so ngay
		long songaytong = (stk.getNgaydong().getTime() - stk.getNgaymo().getTime())/(24 * 60 * 60 * 1000);
		DaoHan daohan = stk.getDaohan();
		
		if(songaytong<0) {
			model.addAttribute("thongbao","Ngày tính lãi phải lớn hơn ngày mở sổ");
			Sotietkiem stktong = new Sotietkiem();
			Kyhan kyhan = new Kyhan();
			kyhan.setSongay((int) 0);
			kyhan.setTenkyhan("Tổng cộng:");
			stktong.setKyhan(kyhan);
			stktong.setTienlai(0);
			stktong.setSotien(0);
			stktong.setTongtien(0);
			DaoHan dh = new DaoHan();
			dh.setLoaidaohan("0");
			stktong.setDaohan(dh);

			model.addAttribute("stktong",stktong);
			model.addAttribute("stk",stk);
			return "tinhlai/gdtinhlai";
		}
		/*
		Sotietkiem stktam = stkRepository.findById(id).get();
		//tính lãi
		long tienlai = (long) (stktam.getSotien() * stktam.getKyhan().getLaisuat() / 365 / 100 * songay );
		stktam.setTienlai(tienlai);
		
		//tổng tiền
		stktam.setSotien(stktam.getSotien() + stktam.getTienlai());
		*/
		List<Sotietkiem> liststk = new ArrayList<>();
		Sotietkiem stktong = new Sotietkiem();
		stktong.setSotien(stk.getSotien());
		long laitong = 0;
		long songay = songaytong;
		if(stk.getKyhan().getKyhan() != 0) { //kiểm tra xem có phải là không kỳ hạn
			long tiengocquayvong = stk.getSotien(); //để lưu tiền gốc mới trong đáo hạn gốc và lãi
			while (songay >= (long) (stk.getKyhan().getSongay() )) 
			{
				//tạo mới 1 sổ tiết kiệm theo sổ chính.
				Sotietkiem sonho = new Sotietkiem();
				Kyhan kyhan = stk.getKyhan();
				sonho.setKyhan(kyhan); //bao gồm kỳ hạn tổng ngày lãi suất trong bảng fe
				sonho.setDaohan(daohan);
				sonho.setSotien(stk.getSotien());
				sonho.setTienlai(stk.getTienlai());
				if(stk.getDaohan().getId() == 1) { // không đáo hạn
					songay = 0;
				}
				else if(stk.getDaohan().getId() == 2) { //đáo hạn gốc
					songay = songay - (long) (sonho.getKyhan().getSongay());
				}
				else if(stk.getDaohan().getId() == 3) { //đáo hạn gốc và lãi
					//set tiền lãi theo tiền gốc mới.
					sonho.setSotien(tiengocquayvong);
					long tienlai = (long) (sonho.getSotien()*sonho.getKyhan().getLaisuat()*sonho.getKyhan().getKyhan()/12/100);
					sonho.setTienlai(tienlai);
					songay = songay - (long) (sonho.getKyhan().getSongay());
					tiengocquayvong = sonho.getSotien() + sonho.getTienlai();
					
				}
				
				sonho.setTongtien(sonho.getSotien() + sonho.getTienlai());
				liststk.add(sonho);
				
				laitong = laitong + sonho.getTienlai();
			}
			if(songay > 0) {
				//tinh lai so ngay dư lãi suất không thời hạn
				Sotietkiem sonho = new Sotietkiem();
				Kyhan kyhan1 = new Kyhan();
				kyhan1.setSongay((int)songay);
				kyhan1.setTenkyhan("Không kỳ hạn");
				kyhan1.setLaisuat((float) 2.4);
				sonho.setKyhan(kyhan1); //bao gồm kỳ hạn tổng ngày lãi suất trong bảng fe
				sonho.setDaohan(daohan);
				sonho.setSotien(tiengocquayvong);
				long tienlai = (long) (sonho.getSotien() * sonho.getKyhan().getLaisuat() / 360 / 100 * songay );
				sonho.setTienlai(tienlai);
				sonho.setTongtien(sonho.getSotien() + sonho.getTienlai());
				liststk.add(sonho);
			
				laitong = laitong + sonho.getTienlai();
			}
		}
		else if(stk.getKyhan().getKyhan() == 0 ){ //gửi không kỳ hạn
			Sotietkiem sonho = new Sotietkiem();
			Kyhan kyhan = new Kyhan();
			kyhan.setSongay((int)songay);
			kyhan.setTenkyhan("Không kỳ hạn");
			kyhan.setLaisuat((float) 2.4);
			sonho.setKyhan(kyhan); //bao gồm kỳ hạn tổng ngày lãi suất trong bảng fe
			sonho.setDaohan(daohan);
			sonho.setSotien(stk.getSotien());
			long tienlai = (long) (sonho.getSotien() * sonho.getKyhan().getLaisuat() / 360 / 100 * songay );
			sonho.setTienlai(tienlai);
			sonho.setTongtien(stk.getSotien() + stk.getTienlai());
			liststk.add(sonho);
			
			laitong = laitong + sonho.getTienlai();
		}
		// set tổng 
		Kyhan kyhan = new Kyhan();	
		kyhan.setSongay((int) songaytong);
		kyhan.setTenkyhan("Tổng cộng:");
		stktong.setKyhan(kyhan);
		stktong.setDaohan(daohan);
		stktong.setTienlai(laitong);
		stktong.setSotien(stk.getSotien());
		stktong.setTongtien(stktong.getSotien() + stktong.getTienlai());
		
		
		model.addAttribute("stk",stk);
		model.addAttribute("stktong",stktong);
		model.addAttribute("liststk",liststk);
		return "tinhlai/gdtinhlai";
	}
}
