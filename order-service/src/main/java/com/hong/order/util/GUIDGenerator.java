package com.hong.order.util;

import com.hong.order.exception.CodeException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author wanghong
 * @date 2020/03/29 17:17
 **/
public final class GUIDGenerator {

    private static final char[] CHARACTER_TABLE = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static final int LEN_16 = 16;
    public static final int LEN_20 = 20;
    public static final int LEN_25 = 25;
    public static final int LEN_27 = 27;

    private GUIDGenerator() {
    }

    public static String genGUID(){
        String guid = "";
        try {
            genTxNo(27);
        } catch (CodeException e) {
            e.printStackTrace();
        }
        return guid;
    }

    public static String genTxNo(int length) throws CodeException {
        String txNo = null;
        switch(length) {
            case 16:
                txNo = genTxNo(16, false);
                break;
            case 20:
                txNo = genTxNo(20, false);
                break;
            case 25:
                txNo = genTxNo(25, false);
                break;
            case 27:
                txNo = genTxNo(27, false);
                break;
            default:
                throw new CodeException("", "don't support length[" + length + "]");
        }

        return txNo;
    }

    public static String genTxNo(int length, boolean characterContained) throws CodeException {
        String txNo = null;
        switch(length) {
            case 16:
                txNo = genTxNo4Len16(characterContained);
                break;
            case 20:
                txNo = genTxNo4Len20(characterContained);
                break;
            case 25:
                txNo = genTxNo4Len25(characterContained);
                break;
            case 27:
                txNo = genTxNo4Len27(characterContained);
                break;
            default:
                throw new CodeException("", "don't support length[" + length + "]");
        }

        return txNo;
    }

    private static String genTxNo4Len27(boolean characterContained) throws CodeException {
        if (characterContained) {
            throw new CodeException("", "length [27] don't support generate txNo with charactes");
        } else {
            String txNo = getTxNo27();
            return txNo;
        }
    }

    private static String genTxNo4Len25(boolean characterContained) throws CodeException {
        if (characterContained) {
            throw new CodeException("", "length [25] don't support generate txNo with charactes");
        } else {
            String txNo = getTxNo25();
            return txNo;
        }
    }

    private static String genTxNo4Len20(boolean characterContained) throws CodeException {
        String txNo;
        if (characterContained) {
            txNo = getTxNoV2();
        } else {
            txNo = getTxNo();
        }

        return txNo;
    }

    private static String genTxNo4Len16(boolean characterContained) throws CodeException {
        String txNo;
        if (characterContained) {
            txNo = getTxNo16V2();
        } else {
            txNo = getTxNo16();
        }

        return txNo;
    }

    public static String getTxNo() {
        String timeString = (new SimpleDateFormat("yyMMddHHmm")).format(Calendar.getInstance().getTime());
        return timeString + RandomNumberGenerator.getRandomNumber(10);
    }

    public static String getTxNo16() {
        String timeString = (new SimpleDateFormat("yyMMdd")).format(Calendar.getInstance().getTime());
        return timeString + RandomNumberGenerator.getRandomNumber(10);
    }

    private static String getTxNoV2() throws CodeException {
        String time = (new SimpleDateFormat("yyyyMMddHHmmss")).format(Calendar.getInstance().getTime());
        StringBuilder timeCoverted = new StringBuilder();
        timeCoverted.append(time.substring(0, 4)).append(convert(time.substring(4))).append(RandomNumberGenerator.getRandomCharAndNumber(11, true));
        return timeCoverted.toString();
    }

    private static String getTxNo25() {
        String timeString = (new SimpleDateFormat("yyMMddHHmmssSSS")).format(Calendar.getInstance().getTime());
        return timeString + RandomNumberGenerator.getRandomNumber(10);
    }

    private static String getTxNo27() {
        String timeString = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(Calendar.getInstance().getTime());
        return timeString + RandomNumberGenerator.getRandomNumber(10);
    }

    private static String getTxNo16V2() throws CodeException {
        String time = (new SimpleDateFormat("yyMMddHHmmss")).format(Calendar.getInstance().getTime());
        StringBuilder timeCoverted = new StringBuilder();
        timeCoverted.append(time.substring(0, 2)).append(convert(time.substring(2))).append(RandomNumberGenerator.getRandomCharAndNumber(9, true));
        return timeCoverted.toString();
    }

    public static String convert(String time) throws CodeException {
        StringBuilder timeConverted = new StringBuilder();

        for(int index = 0; index < time.length(); index += 2) {
            int num = Integer.parseInt(time.substring(index, index + 2));
            timeConverted.append(convertNumber2Character(num));
        }

        return timeConverted.toString();
    }

    private static char convertNumber2Character(int num) throws CodeException {
        if (num > 61) {
            throw new CodeException("", "don't supprot digit larger than 61");
        } else {
            return CHARACTER_TABLE[num];
        }
    }

}
