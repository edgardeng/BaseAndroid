package com.edgardeng.net.api;/**
 * Created by dengxixi on 16/1/14.
 */

import java.util.HashMap;

/**
 * @author Edgar Deng (http:weibo.com/edgardeng)
 * @date 16/1/14
 */
public class BaseApi {

    /**
     *  Base information of  network Api
     *  update 0;
     system 	1; 	shop 	2; 		queue 	3;
     reserve 4;	user   	5；		vip 	6；
     dish 	7；	merchant 8 		bill 	9;
     push 	10 	operation 11	service 12;
     table	13   reward 14      statistic 15;
     ticket  16   account 17     recommend 18
     emember 19   publicize 20   experience 21
     * @author EdgarDeng
     * @date 2015-7-7
     * @version
     */


        //----------------  BASE--------------------
        private static final String ADMAIN 		=	"www.zhangshare.com";
        public static final String HOST_IP 		=	"121.199.28.166";
        public static final String HTTP_IP 		=	"http://"+HOST_IP;

        private static final String HTTP_PATH_SEGMENT = "index.php";
        private static final String HTTP_PATH_SEGMENT_RELEASE = "release/index.php";

        private static final String CONTROL 	= "c";
        private static final String METHOD 		= "a";

        /** ----------------------------------- Json Back ------------------------------  */
        public static String SUCCESS = "success";
        public static String FAIL = "fail";
        public static int ERROR_ID  = -1;

        private static boolean isRelease ;




        /** ----------------------------------- Same HashMap From Prarameter  ------------------------------  */

        /** hashmap with controller  */
        public static HashMap<String,String> mapFromControler(String control) {
            HashMap<String,String> hash =  new HashMap<String,String>();
            if(control!=null)
                hash.put(CONTROL, control);
            return hash;
        }

        /** hashmap with controller & method */
        public static HashMap<String,String> hashmapFromControlerAndMethod(String Control,String method) {
            HashMap<String,String> hash =  mapFromControler(Control);
            if(method !=null)
                hash.put(METHOD, method);
            return hash;
        }
        /** hashmap with controller & method */
        public static HashMap<String,String> controlerAndMethod(String Control,String method) {
            HashMap<String,String> hash =  mapFromControler(Control);
            if(method != null)
                hash.put(METHOD, method);
            return hash;
        }

        /** http://121.199.28.166/index.php 没有问号  */
        public static String getHttpBase(){
            //	http://121.199.28.166/release/index.php 或  http://121.199.28.166/index.php
            return    HTTP_IP +"/" + getHttpPathSegment();
        }
        public static String host(){
            return   isRelease? HTTP_IP +"/release" : HTTP_IP;
        }


        public static String getAdmain(){
            return  ADMAIN;
        }

        public static String getAdmainBase(){
            return  ADMAIN +"/" + getHttpPathSegment();
        }


        public static String getHttpIP(){
            return  isRelease ? HTTP_IP+"/release" :HTTP_IP;
        }
        public static String getHostIP(){
            return HOST_IP;
        }

        public static String getHttpPathSegment(){
            return  isRelease ? HTTP_PATH_SEGMENT_RELEASE:HTTP_PATH_SEGMENT;
        }




        public static String urlForImage(String subpath){
            if(subpath ==null)
                return null;
            if(subpath.length()<6){
                return null;
            }
            return  getHttpIP()  + subpath.substring(1,subpath.length());
        }

        public static void  setRelease(boolean relesae){
            isRelease = relesae;
        }

        public static boolean  isRelease( ){
            return  isRelease;
        }


}
