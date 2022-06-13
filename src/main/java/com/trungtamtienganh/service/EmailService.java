package com.trungtamtienganh.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

public interface EmailService {
	void sendOtpEmail(String to, String name, String otp)
			throws UnsupportedEncodingException, MessagingException;
}
