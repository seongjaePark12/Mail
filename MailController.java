package com.spring.javagreenS;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/study")
public class StudyController {	
	@Autowired
	JavaMailSender mailSender;

	// 메일폼 호출
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.GET)
	public String mailFormGet() {
		return "study/mail/mailForm";
	}
	
	// 메일전송
	@RequestMapping(value = "/mail/mailForm", method = RequestMethod.POST)
	public String mailFormPost(MailVO vo) {
		try {
			String toMail = vo.getToMail();
			String title = vo.getTitle();
			String content = vo.getContent();
			// 메세지를 변환시켜서 보관함(messageHelper)에 저장하여 준비한다.
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			// 메일보관함에 회원이 보낸온 메세지를 모두 저장시켜둔다.
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title);
			messageHelper.setText(content);
			// 메세지 보관함의 내용을 편집해서 다시 보관함에 담아둔다.
			content = content.replace("\n", "<br>");
			content += "<br><hr><h3>길동이가 보냅니다.</h3><hr><br>";
			content += "<p><img src=\"cid:main.jpg\" width='500px'></p><hr>";
			content += "<p>방문하기 : <a href='https://github.com/seongjaePark12'>사이트</a></p>";
			content += "<hr>";
			messageHelper.setText(content, true);
			// 본문에 기재된 그림파일의 경로를 따로 표시시켜준다.
			FileSystemResource file = new FileSystemResource("C:\\Users\\박성재\\git\\repository6\\javagreenS\\src\\main\\webapp\\resources\\images\\main.png");
			messageHelper.addInline("main.jpg", file);
			// 메일 전송하기
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "redirect:/msg/mailSendOk";
	}

}










