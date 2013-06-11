package creative.air.security.base64;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import net.iharder.Base64;

public class DECTools {
	private final byte[] DESkey = { 9, 5, 2, 7, 2, 0, 1, 3 };//设置密钥，略去
	private final byte[] DESIV = { 9, 5, 2, 7, 2, 0, 0, 4 };//设置向量，略去

	private AlgorithmParameterSpec iv = null;//加密算法的参数接口，IvParameterSpec是它的一个实现
	private Key key = null;

	public DECTools() {
		try {
			DESKeySpec keySpec = new DESKeySpec(DESkey);//设置密钥参数
			iv = new IvParameterSpec(DESIV);//设置向量
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");//获得密钥工厂
			key = keyFactory.generateSecret(keySpec);//得到密钥对象
		} catch (Exception e) {
			//todo
		}
	}

	public String encode(String data) throws Exception {
		Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");//得到加密对象Cipher
		enCipher.init(Cipher.ENCRYPT_MODE, key, iv);//设置工作模式为加密模式，给出密钥和向量
		byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
		return Base64.encodeBytes(pasByte);
	}

	public String decode(String data) throws Exception {
		Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		deCipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] pasByte = deCipher.doFinal(Base64.decode(data));
		return new String(pasByte, "UTF-8");
	}
}