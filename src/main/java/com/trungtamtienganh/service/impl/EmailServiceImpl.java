package com.trungtamtienganh.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.trungtamtienganh.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${EMAIL}")
	String from;
	
	private final String OTP_EMAIL_SUBJECT = "OTP xác nhận tài khoản";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void sendOtpEmail(String to, String name, String otp)
			throws UnsupportedEncodingException, MessagingException {
		String content = "<!DOCTYPE html><html><head></head><body><p>Xin chào " + name + "</p>"
				+ "<p>Mã OTP để xác thực tài khoản của bạn là: </p>" 
				+ "<p><b>" + otp + "</b></p>" + "<br>"
				+ "<p>Vì lí do bảo mật, tuyệt đối không đưa mã này cho người lạ.</p>"
				+ "<p>Note: Có thời hạn là 5 phút.</p></body></html>";

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(from, "Trung tâm tiếng anh Hào Nguyễn");
		helper.setTo(to);
		helper.setSubject(OTP_EMAIL_SUBJECT);

		helper.setText(content, true);
		javaMailSender.send(message);

		logger.info(OTP_EMAIL_SUBJECT);
		logger.info(to);
		logger.info(otp);

	}

}
