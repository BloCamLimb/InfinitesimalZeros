package infinitesimalzeros.common.util;


import java.util.Arrays;
import java.util.UUID;

public class EncryptUtils {
	
	static UUID uuid = UUID.fromString("f836b4cb-4ad2-3047-bede-1f92822d0050");
	static int m[] = {0x98, 0xef, 0x75, 0x8e, 0x0f, 0x05, 0x40, 0xa0, 0xd8, 0xf9, 0x25, 0x53, 0x8e, 0xa8, 0xb1, 0xd8};
	static String v = uuid.toString();
	static int p[] = {Integer.parseInt(v.substring(0, 2), 16), Integer.parseInt(v.substring(2, 4), 16), Integer.parseInt(v.substring(4, 6), 16), Integer.parseInt(v.substring(6, 8), 16), Integer.parseInt(v.substring(9, 11), 16), Integer.parseInt(v.substring(11, 13), 16), Integer.parseInt(v.substring(14, 16), 16), Integer.parseInt(v.substring(16, 18), 16), Integer.parseInt(v.substring(19, 21), 16), Integer.parseInt(v.substring(21, 23), 16), Integer.parseInt(v.substring(24, 26), 16), Integer.parseInt(v.substring(26, 28), 16), Integer.parseInt(v.substring(28, 30), 16), Integer.parseInt(v.substring(30, 32), 16), Integer.parseInt(v.substring(32, 34), 16), Integer.parseInt(v.substring(34, 36), 16)};
	
	private final static String encryptMasterUUID() {
		
		int q[] = new int[16];
		for(int i = 0; i < 16; i++)
			q[i] = m[i] + p[i] & 0xff;
		
		for(int i = 1; i < 16; i+=2) {
			q[i] = q[i] + q[i-1] & 0xff;
			q[i-1] = q[i-1] + i & 0xff;
		}
		
		return toString(q);
	}
	
	private final static boolean verifySecurityCode(String w) {
		
		int c[] = {Integer.parseInt(w.substring(0, 2), 16), Integer.parseInt(w.substring(2, 4), 16), Integer.parseInt(w.substring(4, 6), 16), Integer.parseInt(w.substring(6, 8), 16), Integer.parseInt(w.substring(9, 11), 16), Integer.parseInt(w.substring(11, 13), 16), Integer.parseInt(w.substring(14, 16), 16), Integer.parseInt(w.substring(16, 18), 16), Integer.parseInt(w.substring(19, 21), 16), Integer.parseInt(w.substring(21, 23), 16), Integer.parseInt(w.substring(24, 26), 16), Integer.parseInt(w.substring(26, 28), 16), Integer.parseInt(w.substring(28, 30), 16), Integer.parseInt(w.substring(30, 32), 16), Integer.parseInt(w.substring(32, 34), 16), Integer.parseInt(w.substring(34, 36), 16)};

		int q[] = new int[16];
		for(int j = 1; j < 16; j+=2) {
			q[j-1] = c[j-1] - j & 0xff;
			q[j] = c[j] - q[j-1] & 0xff;
		}

		for(int l = 0; l < 16; l++) {
			q[l] = q[l] - p[l] & 0xff;
		}
		
		return Arrays.equals(q, m);
	}
	
	private final static String toString(int[] t) {
		
		StringBuilder r = new StringBuilder();
		
		for(int l = 0; l < 16; l++) {
			if(l == 3 || l == 5 || l == 7 || l == 9)
				r.append(Integer.toHexString(t[l]) + "-");
			else
				r.append(Integer.toHexString(t[l]));
		}
		
		return r.toString();
	}
	
	public static void main(String[] args) {
		
		long startTime=System.nanoTime();
		
		//System.out.println("Player UUID:   "+uuid);
		String c = encryptMasterUUID();
		Boolean b = verifySecurityCode(c);
		System.out.println("Security Code: "+c);
		System.out.println("Is Verified:   " + b);
		
		long endTime=System.nanoTime();

		//System.out.println("Time: "+(endTime-startTime)/1000+"¦Ìs");
		
	}

}
