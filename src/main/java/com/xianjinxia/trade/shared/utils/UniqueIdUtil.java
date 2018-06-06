package com.xianjinxia.trade.shared.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * uuid工具类
 * @author hym
 *
 */
@SuppressWarnings("all")
public class UniqueIdUtil {



    private enum SequenceType {
        LOAN_ORDER("lo"),
        PAY("pay"),
        TRACE_NO("tn"),
        ACTIVITY_NO("an"),
        PET_NO("pt");


        SequenceType(String value) {
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }

		public void setValue(String value) {
            this.value = value;
        }
    }



    /**
     * 产生唯一ID
     */
    private static final DatetimeUniqueId idmaker;
    
    private static final WordGenerator wordGenerator = new WordGenerator("abcdefghijklmnopqrstuvwxyz1234567890");

    static {
    	idmaker = new DatetimeUniqueId(wordGenerator.getWord(2));
    }

    /**
     * 获取全局唯一的带日期时间的ID
     * @param type 区别不同的用途，不要太长，控制在3位
     */
    private static String getIdWithDatetime(SequenceType type) {
        return idmaker.getId(type.getValue());
    }

    public static String getLoanOrderUniqueId(){
        return getIdWithDatetime(SequenceType.LOAN_ORDER);
    }

    public static String getPaymentOrderUniqueId(){
        return getIdWithDatetime(SequenceType.PAY);
    }

    public static String getTraceNoUniqueId(){
        return getIdWithDatetime(SequenceType.TRACE_NO);
    }

    public static String getPetTradeNoUniqueId(){
        return getIdWithDatetime(SequenceType.PET_NO);
    }

    public static String getActivityTradeNoUniqueId(){
        return getIdWithDatetime(SequenceType.ACTIVITY_NO);
    }


    private static  class WordGenerator {

        private char[] acceptedChars;

        private Random random = new SecureRandom();

        public WordGenerator(String acceptedChars) {
            this.acceptedChars =
                    (acceptedChars == null || acceptedChars.trim().length() == 0) ? "1234567890".toCharArray()
                            : acceptedChars.toCharArray();
        }

        public String getWord(int length) {
            int charLength = length <= 0 ? 6 : length;
            StringBuffer word = new StringBuffer(charLength);
            for (int i = 0; i < charLength; i++) {
                word.append(acceptedChars[random.nextInt(acceptedChars.length)]);
            }

            return word.toString();
        }
    }


    /**
     * 产生全球唯一的字符串ID
     * 基本算法：类型+randomId+时间+索引（冲突时才有） 例如：SMS000120170313234816377
     * @author hym
     */

    private static class DatetimeUniqueId {
        private String prefix = "SN";
        private String randomId;
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        private String previousTime = "";
        private long index = 0;

        public DatetimeUniqueId(String randomId) {
            this.randomId = randomId;
        }

        /**
         *
         * @param type 自定义类型，如SMS
         * @param serverId 服务器ID，保证分布式情况下，ID唯一，暂定4位，如0001
         */
        public DatetimeUniqueId(String type, String randomId) {
            this.prefix = type;
            this.randomId = randomId;
        }

        /**
         * 获取全局唯一ID
         */
        public String getId() {
            return getId(prefix);
        }

        /**
         * 获取全局唯一ID
         */
        public synchronized String getId(String prefix) {
            StringBuilder sb = new StringBuilder();
            String time = sdf.format(new Date());
            if (time.equals(previousTime)) {
                sb.append(prefix).append(time).append(randomId).append(index++);
                return sb.toString();
            } else {
                index = 0;
                previousTime = time;
                sb.append(prefix).append(time).append(randomId);
                return sb.toString();
            }
        }

    }

}
