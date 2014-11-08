package com.chen.release;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Readtxt {
	public String readTxtFile(String filePath){
		String rtstr = "";
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	rtstr = rtstr + lineTxt + "\r\n";
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return rtstr.trim();
    }
	
	public static void main(String[] args) {
			Readtxt rt = new Readtxt();
	        String filePath = "F:\\workspace\\mypro\\release\\hbrw.txt";
	        String ss = rt.readTxtFile(filePath);
	        String[] allrw = ss.split("\r\n");
	        String filePatht = "F:\\workspace\\mypro\\release\\fbobj.txt";
	        String[] fbojbstr = rt.readTxtFile(filePatht).split("\r\n");

	        Map<String,Rwobj> map = new HashMap<String,Rwobj>();
	        for(int y = 0;y < fbojbstr.length; y++){
		        Rwobj rj = new Rwobj();
	        	rj.setSeq(fbojbstr[y].split("	")[0]);
	        	rj.setJobid(fbojbstr[y].split("	")[1]);
	        	rj.setTag(fbojbstr[y].split("	")[2]);
	        	rj.setSynctime(fbojbstr[y].split("	")[3]);
	        	rj.setAynctime(fbojbstr[y].split("	")[4]);
		        for(int x = 0;x < allrw.length;x++){
		        	if(fbojbstr[y].split("	")[1].equals(allrw[x].split(" ")[0])){
		        		rj.setSubjobc(allrw[x].split(" ")[1].split(",").length);
		        	}
		        }
		        map.put(String.valueOf(y), rj);
	        }
	        for(int z =0 ; z< map.size(); z++){
	        	System.out.print(map.get(String.valueOf(z)).getSeq());
	        	System.out.print(",");
	        	System.out.print(map.get(String.valueOf(z)).getJobid());
	        	System.out.print(",");
	        	System.out.print(map.get(String.valueOf(z)).getTag());
	        	System.out.print(",");
	        	System.out.print(map.get(String.valueOf(z)).getSubjobc());
	        	System.out.print(",");
	        	System.out.print(map.get(String.valueOf(z)).getSynctime());
	        	System.out.print(",");
	        	System.out.println(map.get(String.valueOf(z)).getAynctime());
	        }

	    }
}
