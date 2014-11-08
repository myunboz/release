package com.chen.release;
import java.util.Random;

public class Startpost {
public static String session = "";
String a,b,c,d,e,f;

	public Startpost(){
	}

	public Startpost(String str1,String str2,String str3,String str4,String str5,String str6){
    a=str1;
    b=str2;
    c=str3;
    d=str4;
    e=str5;
    f=str6;
	}
	public int rdno() {
		return new Random().nextInt(10) + 1;
	}

	public void runpost(){
		if(f==null){
			f = "0";
		}
		Fbtest xx = new Fbtest("http://"+a+"/clps/","GET");
		session = xx.clpssession();
		Fbtest xxx = new Fbtest("{loginName:\"admin\",password:\"1\"}","http://"+a+"/clps/rest/login","PUT");
		xxx.runpost();
		if(!b.equals("null")){
		Releasefolder rf = new Releasefolder(a,b,d,Long.parseLong(e),Integer.valueOf(f));
		Thread rff =  new Thread(rf,"rf1");
		rff.start();
		}else{
			System.out.println("输入的路径无效，将不会发布栏目数据");
		}
		if(!c.equals("null")){
		Releasecontent rc = new Releasecontent(a,c,d,Long.parseLong(e),Integer.valueOf(f));
		Thread rcc =  new Thread(rc,"rc1");
		rcc.start();
		}else{
			System.out.println("输入的路径无效，将不会发布资产数据");
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
		Startpost sp = new Startpost(args[0],args[1],args[2],args[3],args[4],args[5]);
		System.out.println("ip&&port:"+args[0]);
		System.out.println("fileo:"+args[1]);
		System.out.println("filet:"+args[2]);
		System.out.println("dtaId:"+args[3]);
		System.out.println("time(h):"+args[4]);
		System.out.println("count:"+args[5]);
		sp.runpost();
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("请按照 {ip:port,栏目数据文件路径,资产数据文件路径,dtaId,运行时间,运行次数} 的顺序带入参数");
		}
	}

}
