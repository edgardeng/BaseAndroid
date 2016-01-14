package com.edgardeng.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

import android.widget.Toast;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class FormatUtils {
	
	/**  */
	private static String genText(int count,int max) {
	    StringBuilder sb = new StringBuilder();
	    int pre = 0;
	    while (count > 0) {
	        int x = (int) (Math.random() * max + 1);
	        if (Math.abs(x - pre) > 0) {
	            sb.append(x);
	            --count;
	            pre = x;
	        }
	    }
	    return sb.toString();
	}
	

	public static String encode(String str){
		try {
			return URLEncoder.encode(str,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}
	

	public static String decode(String str){
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
	}
	


	public static boolean isPasswordFormat(String value){
		
		String regExp = "^[a-zA-Z0-9]\\S{5,17}$";	//	^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(value);
		return m.find();
	}
	
	public static boolean isPhoneNumber(String value){
		// 134,135 136 137 138 139 150 151 157(TD) 158 159 183,187 188
		//130 131 132 152 155 156 185 186
		// 133 153 180 189 139

		String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$";	//	^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(value);
		return m.find();
	}
	
	public static boolean isEmail(String value){
		//edgardeng@qq.com

		String regExp = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";	//	^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(value);
		return m.find();
	}
	
	public static boolean isPersonID(String value){
		//340124199001262714

		String regExp = "^\\d{16}([1,2])([X,0-9])$";	//	^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(value);
		return m.find();
	}
	
	/**  year- month-day after month 0-11*/
	public static String[] datesAfter(int year,int month,int day,int after){
		
		String[] days =  new String[after];
		int[] counts = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};
		int monCount = counts[month];
		
		if(month == 1 && isLeapYear(year)){
			monCount = 29;
		}
		
		for(int j =0;j<after;j++){
			StringBuilder sb = new StringBuilder();
			int dd = day+j;
			int d =  dd>monCount?dd-monCount:dd;
			int mm = dd>monCount? month+2:month+1;
			int m = mm>12? mm-12:mm;

			sb.append(mm>12? year+1:year);
			sb.append('-');
			if(m<10)
				sb.append('0');
			sb.append(m);
			sb.append('-');
			if(d<10)
				sb.append('0');
			sb.append(d);
 
			days[j] = sb.toString();
		}
		
		
		return days;
	}
	
	/**   */
	public static boolean isLeapYear(int year ){
		 if(year%100 == 0){

			 if(year%400== 0)
				 return true;
			 else
				 return false;
		 }else{
			 if(year%4 == 0)
				 return true;
			 else
				 return false;
		 }
	}
	
	/**  */
	public static boolean isTime(String s){
		
		boolean status = false;
		String[] array =s.split(":");
		if(array ==null || array.length<2){
			return status;
		}else{
			if(array[0].compareTo("23")>0 && array[0].compareTo("00")<=0){
				return status;
			}else if(array[1].compareTo("59")>0 && array[1].compareTo("00")<=0){
				return status;
			}else{
				status = true;
			}
		}
		
		return status;
	}
	
	
	  
	/**
	 *  xml  hashmap
	 * @param inStream
	 * @return
	 * @throws Exception
	 * @date 2014-7-7

	public static HashMap<String, String> parseXml(InputStream inStream) throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        
        //
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(inStream);

        Element root = document.getDocumentElement();
        
        //��������ӽڵ�
        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++)
        {
            //�����ӽڵ�
            Node childNode = (Node) childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element childElement = (Element) childNode;
                if (VERSION.equals(childElement.getNodeName()))
                {
                    hashMap.put("version",childElement.getFirstChild().getNodeValue());
                }
                else if ((VERSION_NAME.equals(childElement.getNodeName())))
                {
                    hashMap.put(VERSION_NAME,childElement.getFirstChild().getNodeValue());
                }
                else if ((NAME.equals(childElement.getNodeName())))
                {
                    hashMap.put(NAME,childElement.getFirstChild().getNodeValue());
                }
                else if ((DESCRIPTION_CN.equals(childElement.getNodeName())))
                {
                    hashMap.put(DESCRIPTION_CN,childElement.getFirstChild().getNodeValue());
                }
                else if ((DESCRIPTION_EN.equals(childElement.getNodeName())))
                {
                    hashMap.put(DESCRIPTION_EN,childElement.getFirstChild().getNodeValue());
                }
                else if ((URL.equals(childElement.getNodeName())))
                {
                    hashMap.put(URL,childElement.getFirstChild().getNodeValue());
                }

                
            }
        }
        return hashMap;
    }
	

	public static HashMap<String, String> Xml2HashMap(InputStream inStream) throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        
        // ʵ��һ���ĵ�����������
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // ͨ���ĵ�������������ȡһ���ĵ�������
        DocumentBuilder builder = factory.newDocumentBuilder();
        // ͨ���ĵ�ͨ���ĵ�����������һ���ĵ�ʵ��
        Document document = builder.parse(inStream);
        //��ȡXML�ļ���ڵ�
        Element root = document.getDocumentElement();
        
        //��������ӽڵ�
        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++)
        {
            //�����ӽڵ�
            Node childNode = (Node) childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element childElement = (Element) childNode;
                hashMap.put(childElement.getNodeName(), childElement.getFirstChild().getNodeValue());
            }
        }
        return hashMap;
    }
	*/

	
}
