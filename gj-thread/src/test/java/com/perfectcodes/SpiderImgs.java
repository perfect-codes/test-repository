package com.perfectcodes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 2017-5-17 爬取指定URL的图片
 *
 * @author tom
 *
 */
public class SpiderImgs {

	public void crawlImgs(String url) throws Exception {
		//获取工具类返回的html,并用Jsoup解析
		String result = AbstractSpider.getResult(url);
		Document document = Jsoup.parse(result);
		document.setBaseUri(url);
		//获取所有的img元素
		Elements elements = document.select("img");
		for (Element e : elements) {
			//获取每个src的绝对路径
			String src = e.absUrl("src");
			URL urlSource = new URL(src);
			URLConnection urlConnection = urlSource.openConnection();
			String imageName = src.substring(src.lastIndexOf("/") + 1, src.length());
			System.out.println(e.absUrl("src"));

			//通过URLConnection得到一个流，将图片写到流中，并且新建文件保存
			InputStream in = urlConnection.getInputStream();
			OutputStream out = new FileOutputStream(new File("D:\\java\\workSpace\\spiderImg\\image", imageName));
			byte[] buf = new byte[1024];
			int l = 0;
			while ((l = in.read(buf)) != -1) {
				out.write(buf, 0, l);
			}
		}
	}
}
