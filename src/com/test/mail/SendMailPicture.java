package com.test.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
* @ClassName: Sendmail
* @Description: ���ʹ�ͼƬ��Email
* @author: �°�����
* @date: 2015-1-12 ����9:42:56
*
*/ 
public class SendMailPicture {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        
        Properties prop = new Properties();
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //ʹ��JavaMail�����ʼ���5������
        //1������session
        Session session = Session.getInstance(prop);
        //����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
        session.setDebug(true);
        //2��ͨ��session�õ�transport����
        Transport ts = session.getTransport();
        //3�������ʼ�����������Ҫ�������ṩ������û��������������֤
        ts.connect("smtp.yinhe.com", "yinj@yinhe.com", "hello5166");
        //4�������ʼ�
        Message message = createImageMail(session);
        //5�������ʼ�
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
    
    /**
    * @Method: createImageMail
    * @Description: ����һ���ʼ����Ĵ�ͼƬ���ʼ�
    * @Anthor:�°�����
    *
    * @param session
    * @return
    * @throws Exception
    */ 
    public static MimeMessage createImageMail(Session session) throws Exception {
        //�����ʼ�
        MimeMessage message = new MimeMessage(session);
        // �����ʼ��Ļ�����Ϣ
        //������
        message.setFrom(new InternetAddress("yinj@yinhe.com"));
        //�ռ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("292450104@qq.com"));
        //�ʼ�����
        message.setSubject("��ͼƬ���ʼ�");

        // ׼���ʼ�����
        // ׼���ʼ���������
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("<br/>����һ���ʼ����Ĵ�ͼƬ<img src='cid:xxx.jpg'>���ʼ�"
        		+ "<br/>�Ǻ���"
        		+ "<br/>����", "text/html;charset=UTF-8");
        // ׼��ͼƬ����
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src\\com\\test\\mail\\resource\\busy40.png"));
        image.setDataHandler(dh);
        image.setContentID("xxx.jpg");
        // �������ݹ�ϵ
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(image);
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();
        //�������õ��ʼ�д�뵽E�����ļ�����ʽ���б���
        //message.writeTo(new FileOutputStream("K:\\ImageMail.eml"));
        //���ش����õ��ʼ�
        return message;
    }
}