package com.whaty.products.webtrn.password;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.struts2.ServletActionContext;
  
public class ValidateCodeAction {   
    private ByteArrayInputStream inputStream;     
       
    public String getValidateImage() throws Exception{    
        char[] mapTable = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',   
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',   
                'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',   
                '9' };   
           
//      在内存中创建图象      
        int width=60, height=20;      
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);   
  
        //   
        Graphics2D g = image.createGraphics();   
       
        // 填充背景色   
        g.setColor(Color.white);   
        g.fillRect(0, 0, width, height);   
       
        // 绘制边框   
        g.setColor(Color.BLACK);   
        g.drawRect(0, 0, width - 1, height - 1);   
           
         // 验证码   
        String security = "";   
           
        // 随机生成验证码   
        Random random = new Random();   
        for (int i = 0; i < 4; i++) {   
            security += mapTable[random.nextInt(mapTable.length)];   
        }   
       
        // 绘制验证码   
        g.setColor(Color.BLACK);   
        g.setFont(new Font("MS Sans Serif", Font.PLAIN, 18));   
       
        String temp = security.substring(0, 1);   
        g.drawString(temp, 7, 15);   
        temp = security.substring(1, 2);   
        g.drawString(temp, 18, 17);   
        temp = security.substring(2, 3);   
        g.drawString(temp, 28, 18);   
        temp = security.substring(3, 4);   
        g.drawString(temp, 40, 17);   
       
        // 绘制干扰点   
        g.setColor(Color.BLACK);   
        for (int i = 0; i < 20; i++) {   
            g.drawOval(random.nextInt(width), random.nextInt(height), 0, 0);   
        }   
        g.dispose();    
           
        ServletActionContext.getRequest().getSession().setAttribute("security", security);   
        ByteArrayOutputStream output = new ByteArrayOutputStream();      
        ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);      
        ImageIO.write(image, "JPEG", imageOut);      
        imageOut.close();      
        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());      
        this.setInputStream(input);      
        return "SUCCESS";      
    }   
  
    public void setInputStream(ByteArrayInputStream inputStream) {      
        this.inputStream = inputStream;      
    }      
    public ByteArrayInputStream getInputStream() {      
        return inputStream;      
    }   
}