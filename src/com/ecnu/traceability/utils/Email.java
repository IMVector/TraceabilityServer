package com.ecnu.traceability.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;

public class Email {

	public static boolean sendEmail(String to, String content) {
		boolean flag = false;

		final String from = "*@qq.com";
		final String password = "";

		// ָ�������ʼ�������Ϊ smtp.qq.com
		String host = "smtp.qq.com"; // QQ �ʼ�������
		// ��ȡϵͳ����
		Properties properties = System.getProperties();
		// �����ʼ�������
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		MailSSLSocketFactory mailSSLSocketFactory = null;
		try {
			mailSSLSocketFactory = new MailSSLSocketFactory();
		} catch (Exception e) {
			System.out.println("��ȡMailSSLSocketFactory����");
			System.out.println(e.getMessage());
		}
		mailSSLSocketFactory.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
		// ��ȡĬ��session����
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				// ����Ϊqq������Ȩ��
				return new PasswordAuthentication(from, password); // �������ʼ��û���������
			}
		});
		try {
//			// ����Ĭ�ϵ� MimeMessage ����
//			MimeMessage message = new MimeMessage(session);
//
//			// Set From: ͷ��ͷ�ֶ�
//			message.setFrom(new InternetAddress(from));
//
//			// Set To: ͷ��ͷ�ֶ�
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//			// Set Subject: ͷ�ֶ�
//			message.setSubject("����׷�ݷ��أ�");
//
//			// ���� HTML ��Ϣ, ���Բ���html��ǩ
//			message.setContent(content, "text/html");
//
//			// ������Ϣ
//			Transport.send(message);
            // ����Ĭ�ϵ� MimeMessage ����
            MimeMessage message = new MimeMessage(session);
            // Set From: ͷ��ͷ�ֶ�
            message.setFrom(new InternetAddress(from));
            // Set To: ͷ��ͷ�ֶ�
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: ͷ��ͷ�ֶ�
            message.setSubject("��֤�룺");
            // ������Ϣ��
            message.setText(content);
            // ������Ϣ
            Transport.send(message);
            System.out.println("�ѷ��͵��û�����");
			flag = true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public static boolean sendEmail(final String from, String to, String content, final String password) {
		boolean flag = false;
		// "wfupxkrwsgceeaje"
		// ָ�������ʼ�������Ϊ smtp.qq.com
		String host = "smtp.qq.com"; // QQ �ʼ�������
		// ��ȡϵͳ����
		Properties properties = System.getProperties();
		// �����ʼ�������
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		MailSSLSocketFactory mailSSLSocketFactory = null;
		try {
			mailSSLSocketFactory = new MailSSLSocketFactory();
		} catch (Exception e) {
			System.out.println("��ȡMailSSLSocketFactory����");
			System.out.println(e.getMessage());
		}
		mailSSLSocketFactory.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
		// ��ȡĬ��session����
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				// ����Ϊqq������Ȩ��
				return new PasswordAuthentication(from, password); // �������ʼ��û���������
			}
		});
		try {
			// ����Ĭ�ϵ� MimeMessage ����
			MimeMessage message = new MimeMessage(session);
			// Set From: ͷ��ͷ�ֶ�
			message.setFrom(new InternetAddress(from));
			// Set To: ͷ��ͷ�ֶ�
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set Subject: ͷ��ͷ�ֶ�
			message.setSubject("��֤�룺");
			// ������Ϣ��
			message.setText(content);
			// ������Ϣ
			Transport.send(message);
			System.out.println("��֤���ѷ��͵��û�����");
			flag = true;
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		return flag;
	}
}
