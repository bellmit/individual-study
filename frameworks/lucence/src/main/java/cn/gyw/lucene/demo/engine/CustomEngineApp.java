package cn.gyw.lucene.demo.engine;

/**
 * 简单实现的搜索引擎
 */
public class CustomEngineApp {

	public static void main(String[] args) throws Exception {
		ArticleService articleService = new ArticleService();
		// 获取数据源
		/*List<Article> datas = articleService.getDatas();
		// 创建lucene document
		List<Document> documentList = articleService.getDocuments(datas);
		// 创建倒排索引
		articleService.createIndex(documentList);*/

		articleService.searchDocumentByIndex();

	}
}
