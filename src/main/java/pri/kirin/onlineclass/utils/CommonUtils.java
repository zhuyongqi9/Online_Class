package pri.kirin.onlineclass.Utils;

import java.security.MessageDigest;
import java.util.UUID;

public class CommonUtils {

    /**
     * MD5加密
     *
     * @param data
     * @return
     */
    public static String geneMD5(String data) {
        try {
            java.security.MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString().toUpperCase();
        } catch (Exception exception) {
        }
        return null;
    }


    public static String geneUUID(){
        String uuid = UUID.randomUUID().toString()
                .replaceAll("-","").substring(0,32);
        return uuid;
    }

}
