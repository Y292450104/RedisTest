package com.test.mail;

import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtils {
	private static final String mail_transport_protocol = "mail.transport.protocol";
	private static final String mail_smtp_auth = "mail.smtp.auth";
	private static final String mail_host = "mail.host";
	private static final String mail_username = "mail.username";
	private static final String mail_password = "mail.password";
	private static final String mail_from_address = "mail.from.InternetAddress";
	private static ResourceBundle bundle = ResourceBundle.getBundle("mail_config");
	
	public static String getConfig(String key) {
		String value = bundle.getString(key);
		System.out.println(new Date() + ">>>>>>>>>>>> " + key + ":" + value);
		return value;
	}
	
	public static void main(String[] args) throws Exception {
		send("292450104@qq.com","�ʼ�����","�ʼ�����");
	}
	
	/**
	 * �÷����ĵ���ʱ�̰߳�ȫ��
	 * @param toAddress
	 * @param title
	 * @param content ����Ϊhtml���ݣ���������ͼƬ�ȸ�����������Ҫ�ο�SendMailNormal���еĵȷ��������޸�
	 * @throws Exception
	 * 
	 */
	public static void send(String toAddress, String title, String content) throws Exception {
		Properties prop = new Properties();
		prop.setProperty(mail_transport_protocol, getConfig(mail_transport_protocol));
		prop.setProperty(mail_smtp_auth, getConfig(mail_smtp_auth));
		// ʹ��JavaMail�����ʼ���5������
		// 1������session
		Session session = Session.getInstance(prop);
		// ����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
		session.setDebug(true);
		// 2��ͨ��session�õ�transport����
		Transport ts = session.getTransport();
		// 3��ʹ��������û��������������ʼ��������������ʼ�ʱ����������Ҫ�ύ������û����������smtp���������û��������붼ͨ����֤֮����ܹ����������ʼ����ռ��ˡ�
		ts.connect(getConfig(mail_host), getConfig(mail_username), getConfig(mail_password));
		// 4�������ʼ�
		Message message = createSimpleMail(session, toAddress, title, content);
		// 5�������ʼ�
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}

	/**
	 * @Method: createSimpleMail
	 * @Description: ����һ��ֻ�����ı����ʼ�
	 * @Anthor:�°�����
	 *
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createSimpleMail(Session session, String toAddress,String title, String content) throws Exception {
		// �����ʼ�����
		MimeMessage message = new MimeMessage(session);
		// ָ���ʼ��ķ�����
		message.setFrom(new InternetAddress(getConfig(mail_from_address)));
		// ָ���ʼ����ռ��ˣ����ڷ����˺��ռ�����һ���ģ��Ǿ����Լ����Լ���
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
		// �ʼ��ı���
		message.setSubject(title);
		// �ʼ����ı�����
		message.setContent(content, "text/html;charset=UTF-8");
		// ���ش����õ��ʼ�����
		return message;
	}
}
