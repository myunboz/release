package com.chen.release;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Releasefolder implements Runnable {

	Readtxt rt = new Readtxt();
	Startpost sp = new Startpost();
	String incrementFlag = "1", includeChildrenFlag = "0", isPublishTemp = "0";
	String ip,releasepfolder = "",dtaid;
	int count;
	public long timeoutAt ;

	public Releasefolder(String ip,String releasepfolder,String dtaid,long timeoutAt,int count){
		this.ip = ip;
		this.releasepfolder = releasepfolder;
		this.timeoutAt = timeoutAt;
		this.dtaid = dtaid;
		this.count = count;
	}
	public void run() {
		// TODO Auto-generated method stub
		timeoutAt = System.currentTimeMillis()+ timeoutAt * 60 * 60 * 1000;
		System.out.println("System.currentTimeMillis:"+ System.currentTimeMillis());
		System.out.println("timeoutAt:"+timeoutAt);
		String[] rpf = rt.readTxtFile(releasepfolder).split(",");
		System.out.println("栏目数据个数为："+rpf.length);
		do{
			if(rpf.length == 1 && rpf[0].trim().length() == 0){
				break;
			}
			if(count > 0){
				count--;
			}
		for (int x = 0; x < rpf.length; x++) {
			if (sp.rdno() <= 2) {
				incrementFlag = "undefined";
			} else {
				incrementFlag = "1";
			}
			if (sp.rdno() <= 2) {
				includeChildrenFlag = "1";
			} else {
				includeChildrenFlag = "0";
			}
			if (sp.rdno() <= 2) {
				isPublishTemp = "1";
			} else {
				isPublishTemp = "0";
			}
			/* 栏目发布 */
			String furl = "http://"+ip+"/clps/rest/publish/folderidreplace/page?isPublishTemp="
					+ isPublishTemp
					+ "&targetIds="
					+ dtaid
					+ "&incrementFlag="
					+ incrementFlag
					+ "&includeChildrenFlag="
					+ includeChildrenFlag
					+ "&isPublishServiceSystem=0&ipsGroupType=0";
			furl = furl.replaceAll("folderidreplace", rpf[x]);
			System.out.println(furl);
			Fbtest ff = new Fbtest(furl);
			ff.runpost();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		}
	}while(System.currentTimeMillis()<timeoutAt&&rpf.length>0&&count!=0);
		System.out.println("time:"+System.currentTimeMillis());
		System.out.println("timeoutAt:"+timeoutAt);
	}

}
