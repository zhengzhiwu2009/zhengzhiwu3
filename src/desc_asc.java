import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class desc_asc { 
  Key key; 
  public desc_asc(String str) { 
    getKey(str);//生成密匙 
  } 
  /** 
  * 根据参数生成KEY 
  */ 
 /* public void getKey(String strKey) { 
    try { 
        KeyGenerator _generator = KeyGenerator.getInstance("DES"); 
        _generator.init(new SecureRandom(strKey.getBytes("utf-8"))); 
        this.key = _generator.generateKey(); 
        _generator = null; 
    } catch (Exception e) { 
        throw new RuntimeException("Error initializing SqlMap class. Cause: " + e); 
    } 
  } */
  
  public void getKey(String strKey) {
      try {
          KeyGenerator _generator = KeyGenerator.getInstance("DES");
          //防止linux下 随机生成key 
          SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
          secureRandom.setSeed(strKey.getBytes());
          
          _generator.init(56,secureRandom);
          this.key = _generator.generateKey();
          _generator = null;
      } catch (Exception e) {
          throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
      }
  }

  public String encryptStr(String strMing) {
      byte[] byteMi = null;
      byte[] byteMing = null;
      String strMi = "";
      BASE64Encoder base64en = new BASE64Encoder();
      try {
          byteMing = strMing.getBytes("UTF8");
          byteMi = this.encryptByte(byteMing);
          strMi = base64en.encode(byteMi);
      } catch (Exception e) {
          throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
      } finally {
          base64en = null;
          byteMing = null;
          byteMi = null;
      }
      return strMi;
  }


  public String decryptStr(String strMi) {
      BASE64Decoder base64De = new BASE64Decoder();
      byte[] byteMing = null;
      byte[] byteMi = null;
      String strMing = "";
      try {
          byteMi = base64De.decodeBuffer(strMi);
          byteMing = this.decryptByte(byteMi);
          strMing = new String(byteMing, "UTF8");
      } catch (Exception e) {
          throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
      } finally {
          base64De = null;
          byteMing = null;
          byteMi = null;
      }
      return strMing;
  }

  private byte[] encryptByte(byte[] byteS) {
      byte[] byteFina = null;
      Cipher cipher;
      try {
          cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
          cipher.init(Cipher.ENCRYPT_MODE, key);
          byteFina = cipher.doFinal(byteS);
      } catch (Exception e) {
          throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
      } finally {
          cipher = null;
      }
      return byteFina;
  }


  private byte[] decryptByte(byte[] byteD) {
      Cipher cipher;
      byte[] byteFina = null;
      try {
          cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
          cipher.init(Cipher.DECRYPT_MODE, key);
          byteFina = cipher.doFinal(byteD);
      } catch (Exception e) {
          throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
      } finally {
          cipher = null;
      }
      return byteFina;
  }

  public void encrypt(String file, String destFile) throws Exception { 
    Cipher cipher = Cipher.getInstance("DES"); 
    // cipher.init(Cipher.ENCRYPT_MODE, getKey()); 
    cipher.init(Cipher.ENCRYPT_MODE, this.key); 
    InputStream is = new FileInputStream(file); 
    OutputStream out = new FileOutputStream(destFile); 
    CipherInputStream cis = new CipherInputStream(is, cipher); 
    byte[] buffer = new byte[1024]; 
    int r; 
    while ((r = cis.read(buffer)) > 0) { 
        out.write(buffer, 0, r); 
    } 
    cis.close(); 
    is.close(); 
    out.close(); 
  } 
 
  public void decrypt(String file, String dest) throws Exception { 
    Cipher cipher = Cipher.getInstance("DES"); 
    cipher.init(Cipher.DECRYPT_MODE, this.key); 
    InputStream is = new FileInputStream(file); 
    OutputStream out = new FileOutputStream(dest); 
    CipherOutputStream cos = new CipherOutputStream(out, cipher); 
    byte[] buffer = new byte[1024]; 
    int r; 
    while ((r = is.read(buffer)) >= 0) { 
    	System.out.println();
        cos.write(buffer, 0, r); 
    } 
    cos.close(); 
    out.close(); 
    is.close(); 
  } 
  public static void main(String[] args) throws Exception { 
	  desc_asc td = new desc_asc("sac_hzph"); 
	//开发环境配置 
    //td.encrypt("D:/dev_tool/csrc_workspace_sacms/CSRC/src/main/resource/application.properties", "D:/dev_tool/csrc_workspace_sacms/CSRC/src/main/resource/sac_person"); //加密 
    //测试环境配置
	td.encrypt("/usr/local/tomcat-6.0.26_1/conf/context.xml", "/usr/local/tomcat-6.0.26_1/conf/sac_hzph"); //加密
    //生产环境配置
	//td.encrypt("D:/dev_tool/csrc_workspace_sacms/CSRC/src/main/resource/application.properties", "D:/dev_tool/csrc_workspace_sacms/CSRC/src/main/resource/sac_person"); //加密
  } 
}
