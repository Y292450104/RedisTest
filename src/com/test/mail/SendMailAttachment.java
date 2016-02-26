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
* @ClassName: SendMailAttachment
* @Description: ����Email ���ʹ�������email
* @author: �°�����
* @date: 2015-1-12 ����9:42:56
*
*/ 
public class SendMailAttachment {

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
        //3�������ʼ�������
        ts.connect("smtp.yinhe.com", "yinj@yinhe.com", "hello5166");
        //4�������ʼ�
        Message message = createAttachMail(session);
        //5�������ʼ�
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
    
    /**
    * @Method: createAttachMail
    * @Description: ����һ����������ʼ�
    * @Anthor:�°�����
    *
    * @param session
    * @return
    * @throws Exception
    */ 
    public static MimeMessage createAttachMail(Session session) throws Exception{
        MimeMessage message = new MimeMessage(session);
        
        //�����ʼ��Ļ�����Ϣ
        //������
        message.setFrom(new InternetAddress("yinj@yinhe.com"));
        //�ռ���
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("292450104@qq.com"));
        //�ʼ�����
        message.setSubject("JavaMail�ʼ����Ͳ���");
        
        //�����ʼ����ģ�Ϊ�˱����ʼ����������������⣬��Ҫʹ��charset=UTF-8ָ���ַ�����
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("ʹ��JavaMail�����Ĵ��������ʼ�", "text/html;charset=UTF-8");
        
        //�����ʼ�����
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("src\\com\\test\\mail\\resource\\busy40.png"));
        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());  //
        
        //���������������ݹ�ϵ
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");
        
        message.setContent(mp);
        message.saveChanges();
        //��������Emailд�뵽E�̴洢
        //message.writeTo(new FileOutputStream("K:\\attachMail.eml"));
        //�������ɵ��ʼ�
        return message;
    }
}