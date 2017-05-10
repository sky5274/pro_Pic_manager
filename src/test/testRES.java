package test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

import sun.misc.BASE64Encoder;

public class testRES {

	@Test
	public void testGetEncode(){

		getKey();
		getKey();
		getKeyByPass();
		getKeyByPass();
	}

	@Test
	public void test2(){
		/*1、对称加密3DES秘钥生成*/
		KeyGenerator kg;
		try {
			kg = KeyGenerator.getInstance("DESede");
			kg.init(112);//must be equal to 112 or 168
			System.out.println("SecretKey:");
			System.out.println(kg.generateKey().getEncoded());
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		/*2、非对称加密RSA公钥和私钥生成*/
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(512);//RSA keys must be at least 512 bits long
			KeyPair key = keyGen.generateKeyPair();
			System.out.println("PublicKey:");
			System.out.println(key.getPublic().getEncoded());
			System.out.println("PrivateKey:");
			System.out.println(key.getPrivate().getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

	}
	@Test
	public void test3(){
		String s="fengy4n";
		System.out.println(OnSecreatKey(s));
	}
	public String OnSecreatKey(String Keysb) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			String SecretKey = base64en.encode(md5.digest(Keysb.getBytes("utf-8")));
			return SecretKey;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 随机生成秘钥
	 */
	public static void getKey() {
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(128);
			//要生成多少位，只需要修改这里即可128, 192或256
			SecretKey sk = kg.generateKey();
			byte[] b = sk.getEncoded();
			String s = byteToHexString(b);
			System.out.println(s);
			System.out.println("十六进制密钥长度为"+s.length());
			System.out.println("二进制密钥的长度为"+s.length()*4);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("没有此算法。");
		}
	}
	/**
	 * 使用指定的字符串生成秘钥
	 */
	public static void getKeyByPass() {
		//生成秘钥
		String password="testkey";
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			// kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
			//SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以生成的秘钥就一样。
			kg.init(128, new SecureRandom(password.getBytes()));
			SecretKey sk = kg.generateKey();
			byte[] b = sk.getEncoded();
			String s = byteToHexString(b);
			System.out.println(s);
			System.out.println("十六进制密钥长度为"+s.length());
			System.out.println("二进制密钥的长度为"+s.length()*4);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("没有此算法。");
		}
	}
	/**
	 * byte数组转化为16进制字符串
	 * @param bytes
	 * @return
	 */
	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String strHex=Integer.toHexString(bytes[i]);
			if(strHex.length() > 3) {
				sb.append(strHex.substring(6));
			} else {
				if(strHex.length() < 2) {
					sb.append("0" + strHex);
				} else {
					sb.append(strHex);
				}
			}
		}
		return sb.toString();
	}
	
	@Test
	public void test4(){
		String content = "AEStest";
        String password = "AEStest";
        System.out.println("密　钥：" + password);
        System.out.println("加密前：" + content);
        // 加密
        String encryptResult = encrypt(content, password);
        System.out.println("加密后：" + encryptResult);
        // 解密
        String decryptResult = decrypt(encryptResult, password);
        System.out.println("解密后：" + decryptResult);
	}
	
	/**
     * 加密
     * 
     * @param content
     *            待加密内容
     * @param key
     *            加密的密钥
     * @return
     */
    public static String encrypt(String content, String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteRresult = cipher.doFinal(byteContent);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteRresult.length; i++) {
                String hex = Integer.toHexString(byteRresult[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * 解密
     * 
     * @param content
     *            待解密内容
     * @param key
     *            解密的密钥
     * @return
     */
    public static String decrypt(String content, String key) {
        if (content.length() < 1)
            return null;
        byte[] byteRresult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
 

}
