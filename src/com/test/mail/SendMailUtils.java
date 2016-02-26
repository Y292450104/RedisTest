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
		send("292450104@qq.com","邮件测试","邮件测试");
	}
	
	/**
	 * 该方法的调用时线程安全的
	 * @param toAddress
	 * @param title
	 * @param content 可以为html内容，单不包括图片等附件，否则需要参考SendMailNormal类中的等方法进行修改
	 * @throws Exception
	 * 
	 */
	public static void send(String toAddress, String title, String content) throws Exception {
		Properties prop = new Properties();
		prop.setProperty(mail_transport_protocol, getConfig(mail_transport_protocol));
		prop.setProperty(mail_smtp_auth, getConfig(mail_smtp_auth));
		// 使用JavaMail发送邮件的5个步骤
		// 1、创建session
		Session session = Session.getInstance(prop);
		// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(true);
		// 2、通过session得到transport对象
		Transport ts = session.getTransport();
		// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
		ts.connect(getConfig(mail_host), getConfig(mail_username), getConfig(mail_password));
		// 4、创建邮件
		Message message = createSimpleMail(session, toAddress, title, content);
		// 5、发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}

	/**
	 * @Method: createSimpleMail
	 * @Description: 创建一封只包含文本的邮件
	 * @Anthor:孤傲苍狼
	 *
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public static MimeMessage createSimpleMail(Session session, String toAddress,String title, String content) throws Exception {
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(getConfig(mail_from_address)));
		// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
		// 邮件的标题
		message.setSubject(title);
		// 邮件的文本内容
		message.setContent(content, "text/html;charset=UTF-8");
		// 返回创建好的邮件对象
		return message;
	}
}
