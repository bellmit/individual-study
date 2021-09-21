package cn.gyw.platform.crawler.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.gyw.platform.crawler.dao.ArticleDao;
import cn.gyw.platform.crawler.entity.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.gyw.platform.crawler.interfaces.IWebCrawler;

/**
 * web fetch by jsoup
 *
 */
public class JsoupCrawler implements IWebCrawler {

	// 爬取目标https://www.iyiou.com/kcjiedu/1.html
	private final String url = "https://www.iyiou.com/kcjiedu/1.html";
	
	private static final String STORE_DIR = "/home/guanyw/temp/articles/";
	private static final String FILE_SUFFIX = ".txt";

	@Override
	public void fetchWeb() {
		try {
			Document document = Jsoup.connect(url).get();
			//因为分页，所以我们获取他最后一页的数字
			int end = Integer.parseInt(document.select(".end").text());
			System.out.println("总页数：" + end);
			// TODO 避免文件过多，只爬取2页
			end = 2;
			for (int i = 1 ; i <= end ; i++) {
				String replacedUrl = url.replaceAll("1", String.valueOf(i));
				crawlingArticle(replacedUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void crawlingArticle(String url) throws Exception {
		Document doc = Jsoup.connect(url).get();
		// 查找class为newestArticleList的div 并从里面获取a标签属性
		Elements select = doc.select(".newestArticleList a");
		// 有两个href地址的，有一个图片链接和一个文本链接 所以我们弄一个丢一个即可
		int i = 0;
		Article article = null;
		List<Article> articles = new ArrayList<>();
		for (Element element : select) {
			i++;
			if (i % 2 == 0) {
				continue;
			}
			String articleUrl = element.attr("href");
			// 开始正式爬取单个链接
			article = getArticle(articleUrl);
			if (Objects.nonNull(article)) {
				articles.add(article);
			}
		}
		saveToDB(articles);
	}

	private Article getArticle(String url) throws Exception {
		if (!url.contains("/p/")) { // only fetch article
			System.out.println("非文章链接跳过：" + url);
			return null;
		}
		Article article = new Article();
		int fromIdx = url.lastIndexOf("/") + 1;
		int toIdx = url.lastIndexOf(".");
		String id = url.substring(fromIdx, toIdx);
		article.setId(Integer.valueOf(id));
		System.out.println("开始爬取文章标题：" + url);
		Document doc = Jsoup.connect(url).get();
		// 获取标题
		Elements select_title = doc.select("#post_title");
		// 直接把非文件名字符替换
		String textTitle = select_title.text().replaceAll("[\\s\\\\/:\\*\\?\\\"<>\\|]", "");
		System.out.println("文章的标题：" + textTitle);
		article.setTitle(textTitle);
		// 创建txt文件以文章标题为文件名
		String path = STORE_DIR + textTitle + FILE_SUFFIX;
		// 创建文件
		//File file = new File(path);
		//if (!file.exists()) {
		//	file.createNewFile();
		//}
		// 获取内容
		Elements select_description = doc.select("#post_description");
		String text_description = select_description.text();
		article.setContent(text_description);
		// 下载文章
		//loadStr(path, text_description);
		return article;
	}

	private void loadStr(String path, String text_description) throws Exception {
		// 要写入文件所以创建BufferedWriter
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
		bw.write(text_description);
		// 关闭流
		if (bw != null) {
			bw.close();
		}
	}

	private void saveToDB(List<Article> articles) {
		new ArticleDao().batchInsert(articles);
	}
}
