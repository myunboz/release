package com.chen.release;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class Fbtest {

	String xmladi, urlStr, httptype;

	public Fbtest(String xmladi, String urlStr, String httptype) {
		this.xmladi = xmladi;
		this.urlStr = urlStr;
		this.httptype = httptype;
	}

	public Fbtest(String urlStr, String httptype) {
		this.urlStr = urlStr;
		this.httptype = httptype;
		this.xmladi = "";
	}

	public Fbtest(String urlStr) {
		this.urlStr = urlStr;
		this.httptype = "POST";
		this.xmladi = "";
	}

	public void runpost() {
		// TODO Auto-generated method stub
		String returnStr = "";
		System.out.println(xmladi);
		byte[] xmlData = xmladi.trim().getBytes();
		DataInputStream input = null;
		java.io.ByteArrayOutputStream out = null;
		try {
			// 获得到位置服务的链接
			URL url = new URL(urlStr);
			URLConnection urlCon = url.openConnection();
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setUseCaches(false);
			// 将xml数据发送到位置服务
			urlCon.setRequestProperty("Accept", "*/*");
			urlCon.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
			urlCon.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			urlCon.setRequestProperty("Connection", "keep-alive");
			urlCon.setRequestProperty("Content-Length", "0");
			urlCon.setRequestProperty("Content-Type", "application/json");
			urlCon.setRequestProperty("Cookie", Startpost.session);
			urlCon.setRequestProperty("Host", "10.223.138.55:8180");
			urlCon.setRequestProperty("Origin", "http://10.223.138.55:8180");
			urlCon.setRequestProperty("OW-Referer", "pages/page/page_publish");
			urlCon.setRequestProperty("OW-Request", httptype);
			urlCon.setRequestProperty("Referer",
					"http://10.223.138.55:8180/clps/");
			urlCon.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
			urlCon.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			DataOutputStream printout = new DataOutputStream(
					urlCon.getOutputStream());
			printout.write(xmlData);
			printout.flush();
			printout.close();
			input = new DataInputStream(urlCon.getInputStream());
			byte[] rResult;
			out = new java.io.ByteArrayOutputStream();
			byte[] bufferByte = new byte[256];
			int l = -1;
			while ((l = input.read(bufferByte)) > -1) {
				out.write(bufferByte, 0, l);
				out.flush();
			}
			rResult = out.toByteArray();
			returnStr = new String(rResult, "ISO-8859-1");
			System.out.println(returnStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				input.close();
			} catch (Exception ex) {
			}
		}
		// System.out.println(returnStr);
	}

	public String clpssession() {
		// TODO Auto-generated method stub
		System.out.println("urlStr:"+urlStr);
		String returnStr = "";
		try {
			// 获得到位置服务的链接
			URL url = new URL(urlStr);
			URLConnection urlCon = url.openConnection();
			Map<String, List<String>> map = urlCon.getHeaderFields();
			returnStr = map.get("Set-Cookie").toString().split(";")[0]
					.substring(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(returnStr==null)
			System.out.println("get session failure!!!");
		else
			System.out.println("get session success:"+returnStr);
		return returnStr;
	}
}