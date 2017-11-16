package com.edgardeng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream; 

import android.content.Context; 
import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtils {

    private File cacheDir;
    final static String cahe_path = "CloudSensor/cache";
    public static String APPNAME = "CloudSensor";
 	public static final String  LOCAL_MENU_PATH = "CloudSensor/menu_local";
 	
    public static final String img_address = "CloudSensor/image";
    
    public FileUtils(Context context) {

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),cahe_path);
        else
            cacheDir = context.getCacheDir();
         if (!cacheDir.exists())
               cacheDir.mkdirs();
        }

    public File getFile(String url) {
        //
        String filename = String.valueOf(url.hashCode());
                // Another possible solution
                // String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

   }

	public File createCacheFile(String url) throws IOException {
		File file = new File(cacheDir, url);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		return file;
	}

    public static void deleteFile(String filename) {
       
        File f = new File(Environment.getExternalStorageDirectory()+File.separator+filename);
        if(f.exists())
        	f.delete();

   }

   public void clear() {
         File[] files = cacheDir.listFiles();
         if (files == null)
                 return;
         for (File f : files)
               f.delete();
   }

   
   public static boolean saveImage2SDCard(Bitmap img,String folder,String name){
	   
	  if(img == null) return false;
	   boolean result = false;
	   
	   String f_path = Environment.getExternalStorageDirectory()+File.separator +folder;
	   
	   File filefolder = new File(f_path);
	   String img_path = f_path+File.separator+name;

	   FileOutputStream fos = null;
	   try {
		   if(!filefolder.exists())
			   filefolder.mkdirs();

			  fos = new FileOutputStream(img_path);
			  img.compress(Bitmap.CompressFormat.JPEG,60,fos);			
			  result = true;
		} catch (IOException e) {			
			e.printStackTrace();
			 result = false;
		} finally{
			try {
				if(fos!=null)
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					
				}
		}
	   return result;
   }
   

	public static boolean haveSdcard(){
		return   Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * ��ȡ�ڴ濨 ��Ŀ¼
	 * @return
	 */
   public static String SDCardPath(){
       if(haveSdcard())
           return  Environment.getExternalStorageDirectory().getPath();
       else
        return null;
   }
	public static File writeToSDfromInput(String path,String fileName,InputStream inputStream){

		File folder = new File(SDCardPath()+path);
		if(!folder.exists()) 
			folder.mkdirs();
	    File file = null;
			try {
				file = File.createTempFile(fileName,".jpg",folder);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	         OutputStream outStream=null;

	         try {

	             outStream= new FileOutputStream(file);

	             byte[] buffer=new byte[4*1024];

	             while(inputStream.read(buffer)!=-1){

	                 outStream.write(buffer);

	             }

	             outStream.flush();

	         } catch (FileNotFoundException e) {

	             e.printStackTrace();

	         } catch (IOException e) {

	             e.printStackTrace();

	         }finally{

	             try {

	                 outStream.close();

	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }
	         return file;
	     }
	/**
	 * 
	 * @param path
	 * @param fileName
	 * @param data
	 * @return
	 */
		public static boolean writeToJsonFile(String path,String fileName,String data){
			String flod_path = SDCardPath()+File.separator+APPNAME+File.separator+path;
			File folder = new File(flod_path);
			if(!folder.exists()) 
				folder.mkdirs();//���ڴ濨��Ŀ¼�£��½�һ��Ŀ¼�ļ�
		    File file = new File(flod_path+File.separator+fileName);
			try {
				//file = File.createTempFile(fileName.replace(":", "").substring(0, 17),".json",folder);
				if(!file.exists()) file.createNewFile();
			} catch (IOException e1) {
					e1.printStackTrace();
					return false;
			}
			OutputStream outStream=null;

	         try {
	             outStream= new FileOutputStream(file);
	             byte[] buffer= data.getBytes();
	             outStream.write(buffer);

	         } catch (IOException e) {
	             e.printStackTrace();
	             return false;
	         }finally{
	             try {
	                 outStream.close();
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }	
	         return true;
		}
		/** * @return
		 */
		public static boolean deleteJsonFile(String path,String fileName){
			String flod_path = SDCardPath()+File.separator+APPNAME+File.separator+path;
			File folder = new File(flod_path);
			if(!folder.exists())
				return false;
		    File file = new File(flod_path+File.separator+fileName);
			if(!file.exists()) return false;
			file.delete();
	         return true;
		}
		
		
		public static String readJsonFile(File file){

			InputStream in = null;
			byte [] buffer = null;
			try {
				in = new FileInputStream(file);
				int length = in.available();       
				buffer = new byte[length];        
				in.read(buffer); 
				
				   
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch(IOException e1){
				e1.printStackTrace();
				
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
			return new String(buffer);
			
		}
		
		  /**
		  * ��ȡȨ�� 
		  * @param permission
		  * @param path
		  */
		  public static void chmod(String permission, String path) {
		  try {
			  String command = "chmod " + permission + " " + path;
			  Runtime runtime = Runtime.getRuntime();
			  runtime.exec(command);
			  } catch (IOException e) {
				  e.printStackTrace();
			  }
		  }

	
}
