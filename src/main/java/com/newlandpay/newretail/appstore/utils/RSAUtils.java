package com.newlandpay.newretail.appstore.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 公私钥文件的保存，有两种编码格式，PEM 和 DER<br>
 * 公私钥本身就是两个字节数据，只是PEM 以base64来编码保存，而DER直接保存二进制文件。<br>
 * openssl 生成的rsa密钥对默认是PEM格式，但是在导出公钥文件时，可以转换成DER格式保存。<br>
 * RSA 公私钥对由openssl命令生成<br>
 * 生成PEM编码的，带密码保护的rsa私钥<br>
 * openssl genrsa -des3 -out test-pwd-priv.pem 2048<br>
 *     输入私钥保护密码
 * 从私钥文件提取rsa公钥，并输出为DER编码的公钥文件
 * openssl rsa -in test-pwd-priv.pem -outform der -pubout -out test-pwd-pub.der
 *      输入上面的私钥保护密码
 * @author chenk
 *
 */
public class RSAUtils {
	
	private static final String PROVIDER_NAME = "BC";

	/**数字签名算法:SHA1withRSA**/
	public final static String SIGNATURE_ALGORITHM_SHA1WITHRSA ="SHA1withRSA";
	/**数字签名算法:MD5withRSA**/
	public final static String SIGNATURE_ALGORITHM_MD5WITHRSA ="MD5withRSA";

    static{
        Security.addProvider(new BouncyCastleProvider());
    }

	/**
	 * RSA加密
	 * @param key 公钥
	 * @param data 数据
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(Key key,byte[] data) throws Exception {
		return encrypt(key,"RSA/ECB/PKCS1Padding",data);
	}
	
	/**
	 * RSA加密
	 * @param key 加密
	 * @param transformation 算法
	 * @param data 数据
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(Key key,String transformation,byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance(transformation,PROVIDER_NAME);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			int blockSize = cipher.getBlockSize();
			int outputSize = cipher.getOutputSize(data.length);
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
					: data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize) {
					cipher.doFinal(data, i * blockSize, blockSize, raw, i
							* outputSize);
				} else {
					cipher.doFinal(data, i * blockSize, data.length - i
							* blockSize, raw, i * outputSize);
				}
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * RSA解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(Key key,byte[] data)throws Exception {
		return decrypt(key,"RSA/ECB/PKCS1Padding",data);
	}
	
	/**
	 * RSA解密
	 * @param key
	 * @param transformation
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(Key key,String transformation,byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance(transformation,PROVIDER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, key);
			int blockSize = cipher.getBlockSize(); //密文数据分块
			if(data.length % blockSize != 0)
				throw new Exception("RSA descrypt:unexpected data length:"+data.length);
			int blocks = data.length / blockSize; //获得分块数据
			int inputOffset = 0;
			int outputOffset = 0;
			byte[] buffer = new byte[blocks * blockSize];
			for(int i = 0;i<blocks;i++){
				int hasDone = cipher.doFinal(data,inputOffset,blockSize,buffer,outputOffset);
				outputOffset += hasDone;
				inputOffset += blockSize;
			}
			return ArrayUtils.subarray(buffer,0,outputOffset);
		} catch (Exception e) {
			throw e;
		}
    }
	
	/**
	 * 数据签名验签
	 * @param publicKey 公钥
	 * @param algorithm 数字签名算法  "MD5withRSA","SHA1withRSA" 等
	 * @param data 数据
	 * @param signData 验签数据
	 * @return true if the signature was verified, false if not.
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 * @throws Exception
	 */
	public static boolean verify(PublicKey publicKey,String algorithm,byte[] data,byte[] signData) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
		Signature signature=Signature.getInstance(algorithm);
		signature.initVerify(publicKey);
		signature.update(data);
	    return signature.verify(signData);
	}

	/**
	 * 进行数字签名
	 * @param privateKey 私钥
	 * @param data 签名数据
	 * @param algorithm 数字签名算法  "MD5withRSA","SHA1withRSA" 等
	 * @return the signature bytes of the signing operation's result.
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 * @throws Exception
	 */
	public static byte[] sign(PrivateKey privateKey,byte[] data,String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
		Signature signature=Signature.getInstance(algorithm);
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}
	
	/**
	 * Base64编码的字符串 转 公钥对象
	 * @param publicKeyBase64Str
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public static PublicKey loadPublicKey(String publicKeyBase64Str) throws Exception{
		byte[] buffer = Base64.decodeBase64(publicKeyBase64Str);
		return loadPublicKey(buffer);
	}

    /**
     * Base64编码的字符串 转 公钥对象
     * @param publicKeyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey loadPublicKey(byte[] publicKeyBytes) throws Exception{
        PublicKey publicKey = null;
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
	
	/**
	 * Base64编码的字符串 转 私钥对象
	 * @param privateKeyBase64Str
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public static PrivateKey loadPrivateKey(String privateKeyBase64Str) throws Exception{
        byte[] buffer= Base64.decodeBase64(privateKeyBase64Str);
        return loadPrivateKey(buffer);
	}

    /**
     * Base64编码的字符串 转 私钥对象
     * @param privateKeyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey loadPrivateKey(byte[] privateKeyBytes) throws Exception{
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 将PEM 格式的私钥文件转换成私钥对象
     * @param pemInputStream  PEM格式的私钥文件输入流
     * @return
     * @throws IOException
     */
    public static PrivateKey loadPrivateKeyFromPEMFile(InputStream pemInputStream) throws Exception {
        return loadPrivateKeyFromPEMFile(pemInputStream,"");
    }
    /**
     * 将PEM 格式的私钥文件转换成私钥对象 - 带密码保护
     * @param pemInputStream  PEM格式的私钥文件输入流
     *          @param pwd  密钥保护密码
     * @return
     * @throws IOException
     */
    public static PrivateKey loadPrivateKeyFromPEMFile(InputStream pemInputStream,final String pwd) throws Exception {

		PEMParser pemParser = new PEMParser(new InputStreamReader(pemInputStream));
		Object obj = pemParser.readObject();
		PEMDecryptorProvider pemDecryptorProvider = new JcePEMDecryptorProviderBuilder().build(pwd.toCharArray());
		JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter().setProvider("BC");
		KeyPair keyPair1 = null;
		if(obj instanceof PEMEncryptedKeyPair){
			System.out.println("Encrypted key - we will use provided password");
			keyPair1 = jcaPEMKeyConverter.getKeyPair(((PEMEncryptedKeyPair) obj).decryptKeyPair(pemDecryptorProvider));
		}else{
			System.out.println("Unencrypted key - no password needed");
			keyPair1 = jcaPEMKeyConverter.getKeyPair((PEMKeyPair) obj);
		}


        return keyPair1.getPrivate();
    }


    /**
     * 将PEM 格式的私钥文件转换成私钥对象
     * @param pemInputStream PEM格式的私钥文件输入流
     * @return
     * @throws IOException
     */
    public static PublicKey loadPublicKeyFromPEMFile(InputStream pemInputStream) throws Exception {
		PEMParser pemParser = new PEMParser(new InputStreamReader(pemInputStream));
		Object obj = pemParser.readObject();
		System.out.println(obj.getClass().getName());
		JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter().setProvider("BC");

		return jcaPEMKeyConverter.getPublicKey((SubjectPublicKeyInfo)obj);
    }

    /**
     * 从DER格式的公钥文件提取公钥对象
     * @param derInputStream
     * @return
     * @throws Exception
     */
    public static PublicKey loadPublicKeyFromDERFile(InputStream derInputStream) throws Exception {
        byte[] buffer = IOUtils.toByteArray(derInputStream);
        return loadPublicKey(buffer);
    }

}
