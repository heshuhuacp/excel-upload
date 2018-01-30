package com.example.demo.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author heshuhua343
 * @date
 */
public enum ClazzEnum {

    INTEGER("class java.lang.Integer", Integer.class), STRING("class java.lang.String", String.class), 
    BOOLEAN("class java.lang.Boolean", Boolean.class), BIGDECIMAL("class java.math.BigDecimal",BigDecimal.class),
    DATE("class java.util.Date", Date.class);

    private String attributeType;

    @SuppressWarnings("rawtypes")
    private Class clazz;

    @SuppressWarnings("rawtypes")
    private ClazzEnum(String attributeType, Class clazz) {
        this.clazz = clazz;
        this.attributeType = attributeType;
    }

    public String getAttributeType() {
        return attributeType;
    }

    @SuppressWarnings("rawtypes")
    public Class getClazz() {
        return clazz;
    }

    @SuppressWarnings("rawtypes")
    public static Class getClazzByType(String type){

        ClazzEnum[] clazzEnums = ClazzEnum.values();
        for (int i = 0; i < clazzEnums.length; i++) {

            ClazzEnum clazzEnum = clazzEnums[i];
            if(type.equals(clazzEnum.getAttributeType())){
                return clazzEnum.getClazz();
            }
        }
        return null;
    }

    /**
     * 
     * 功能描述: 将数据转换成
     * 
     * @author heshuhua343
     * @date
     */
    @SuppressWarnings("rawtypes")
    public static Object getOriginTypeValueByType(String origin,Class clazz){

        if(INTEGER.getAttributeType().equals(clazz.toString())){

            return Integer.parseInt(origin);
        }
        if(BOOLEAN.getAttributeType().equals(clazz.toString())){

            return Boolean.valueOf(origin);
        }
        if(DATE.getAttributeType().equals(clazz.toString())){

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf.parse(origin);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        if(BIGDECIMAL.getAttributeType().equals(clazz.toString())){

            return new BigDecimal(origin);
        }

        return origin;

    }

}
