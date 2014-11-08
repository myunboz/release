package com.chen.release;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Releasecontent implements Runnable {

	Readtxt rt = new Readtxt();
	String ip,releaseContent = "",dtaid;
	long timeoutAt;
	int count;

	public Releasecontent(String ip,String releaseContent,String dtaid,long timeoutAt,int count){
		this.ip = ip;
		this.releaseContent = releaseContent;
		this.timeoutAt = timeoutAt;
		this.dtaid = dtaid;
		this.count = count;
	}
	public void run() {
		// TODO Auto-generated method stub
		timeoutAt = System.currentTimeMillis()+ timeoutAt * 60 * 60 * 1000;
		System.out.println("System.currentTimeMillis:"+ System.currentTimeMillis());
		System.out.println("timeoutAt:"+timeoutAt);
		String[] rct = rt.readTxtFile(releaseContent).split("\r\n");
		System.out.println("内容数据行数为："+rct.length);
		do{
			if(rct.length == 1 && rct[0].trim().length() == 0){
				break;
			}
			if(count > 0){
				count--;
			}
		for (int y = 0; y < rct.length; y++) {
			String folderid = rct[y].split("#")[0];
			String[] cids = rct[y].split("#")[1].split(",");
			String[] itemids = rct[y].split("#")[2].split(",");
			/* 栏目上内容单独发布 */
			String pageids = folderid, ItemStr = null, Item1Str = null, aidstr = null, aid1str = null, curl = "";
			for (int z = 0; z < cids.length; z++) {
				ItemStr = itemids[z];
				aidstr = cids[z];
				if ((z + 1) < cids.length) {
					Item1Str = itemids[z + 1];
					aid1str = cids[z + 1];
					curl = "http://"+ip+"/clps/rest/publish?pageIds="
							+ pageids
							+ "&targetSystems="
							+ dtaid
							+ "&contentSetItems="
							+ ItemStr
							+ "&contentSetItems="
							+ Item1Str
							+ "&assetIdArray="
							+ aidstr
							+ "&assetIdArray="
							+ aid1str;
				} else {
					curl = "http://"+ip+"/clps/rest/publish?pageIds="
							+ pageids
							+ "&targetSystems="
							+ dtaid
							+ "&contentSetItems="
							+ ItemStr + "&assetIdArray=" + aidstr;
				}

				System.out.println(curl);
				Fbtest ff = new Fbtest(curl, "GET");
				ff.runpost();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				z++;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			}
		}
		}while(System.currentTimeMillis()<timeoutAt&&rct.length>0&&count!=0);
		System.out.println("time:"+System.currentTimeMillis());
		System.out.println("timeoutAt:"+timeoutAt);
	}

}
