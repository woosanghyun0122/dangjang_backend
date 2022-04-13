package com.semi.dangjang.qna.controller;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

import com.semi.dangjang.common.FileUploadUtil;
import com.semi.dangjang.qna.domain.QnaDto;
import com.semi.dangjang.qna.service.QnaService;
import com.semi.dangjang.qnaComment.domain.QnaCommentDto;
import com.semi.dangjang.qnaComment.repository.QnaCommentDao;




@CrossOrigin("*")
@RestController
public class QnaController {
	
	@Value("${fileUploadPath}")
	String fileUploadPath ;
	
	@Value("${domain}")
	String domain;
	
	@Resource(name="qnaService")
	QnaService qnaService;
	
	
	
	@RequestMapping("/qna/list/{pg}/{category_code}")
	HashMap<String, Object> getList(@PathVariable("pg")int pg,@PathVariable("category_code")String category_code, QnaDto dto)
	{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+pg+","+category_code);
		dto.setStart((pg-1)*dto.getPageSize());
		dto.setCategory_code(category_code);
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("totalCnt", qnaService.getQnaTotalCnt(dto));
		map.put("list",qnaService.getQanList(dto));
		
		System.out.println(dto);
		
		return map;
	}
	
	@RequestMapping("/qna/view/{qna_seq}")
	QnaDto getView(@PathVariable("qna_seq")String id) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+qnaService.getView(id));
		
		return qnaService.getView(id);
	}
	@RequestMapping("/qnacomment/view/{qna_seq}")
	QnaCommentDto getCommentView(@PathVariable("qna_seq")String id) {
		System.out.println("!@##$@#$%^@%T@$%$^%&$^^U#%YH%E^H"+id);
		System.out.println("댓글답변댓글답변댓글답변댓글답변댓글답변댓글답변댓글답변댓글답변"+qnaService.getQnaCommentView(id));
		return qnaService.getQnaCommentView(id);
	}
	
	@RequestMapping("/qna/insert")
	Map<String,String> insert(MultipartFile file,QnaDto dto)
	{
		String uploadDir = fileUploadPath+"/image";
		if(file!=null) {
			try {
				String filename=FileUploadUtil.upload(uploadDir, file);
				dto.setImage(domain + "/" + uploadDir+"/" +filename);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		qnaService.qnaInsert(dto);
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", "success");
		return map;
	}
	
	@RequestMapping("/qnacomment/insert")
	Map<String,String> commentInsert(QnaCommentDto dto)
	{
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!"+dto);
		qnaService.qnaCommentInsert(dto);
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", "success");
		return map;
	}
	
	@RequestMapping("/qnacomment/update")
	Map<String,String> commentUpdate(QnaCommentDto dto)
	{
		qnaService.qnaCommentUpdate(dto);
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", "success");
		return map;
	}
	
	@RequestMapping("/qna/delete/{qna_seq}")
	Map<String,String> delete(@PathVariable("qna_seq")String qna_seq,HttpServletRequest req){
		QnaDto dto = new QnaDto();
		dto.setQna_seq(qna_seq);
		qnaService.qunDelete(dto);
		Map<String,String> map = new HashMap<String,String>();
		map.put("result","success");
		return map;
	}
	
	@RequestMapping("/qna/update")
	Map<String,String> update(MultipartFile file,QnaDto dto, HttpServletRequest req){
		String uploadDir = fileUploadPath +"/image";
		if(file!=null) {
			try {
				String filename = FileUploadUtil.upload(uploadDir, file);
				dto.setImage(domain+"/"+uploadDir+"/"+filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		qnaService.qnaUpdate(dto);
		Map<String,String> map = new HashMap<String,String>();
		map.put("result", "success");
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
