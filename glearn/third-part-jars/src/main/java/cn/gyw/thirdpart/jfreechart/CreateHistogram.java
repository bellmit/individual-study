package cn.gyw.thirdpart.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

@SuppressWarnings("restriction")
public class CreateHistogram {

	private static JFreeChart chart;

	private static CategoryDataset dataset;

	// 柱状图最大值
	private static Double leftMaxValue;

	public static void create(List<CharSet> lists, String title, String path,
			int weight, int height, String yName, boolean isShowNumber) throws Exception {
		getDataset(lists);
		createChart(title, yName, isShowNumber);
		FileOutputStream out = new FileOutputStream(path);
		ChartUtilities.writeChartAsPNG(out, chart, weight, height);
		out.flush();
		out.close();
	}

	public static String create(List<CharSet> lists, String title,
			int weight, int height, String yName, boolean isShowNumber) throws Exception {
		getDataset(lists);
		createChart(title, yName, isShowNumber);
		PipedInputStream in = null;
		PipedOutputStream out = null;
		try {
			in = new PipedInputStream(50000);
			out = new PipedOutputStream(in);
			ChartUtilities.writeChartAsPNG(out, chart, weight, height);
			return getImageBySystemOut(in);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null)
				in.close();
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	@SuppressWarnings("all")
	private static String getImageBySystemOut(PipedInputStream in) {
		byte[] data = null;
		// 读取图片字节数组
		try {
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(data);// 返回Base64编码过的字节数组字符串
	}

	private static void getDataset(List<CharSet> lists) {
		leftMaxValue = 0D;
		DefaultCategoryDataset dcd = new DefaultCategoryDataset();
		for(CharSet cs : lists) {
			dcd.addValue(cs.getValue(), cs.getRowKey(), cs.getColumKey());
			if(leftMaxValue <= cs.getValue())
				leftMaxValue = cs.getValue();
		}
		dataset = dcd;
	}

	private static void createChart(String title, String yName, boolean isShowNumber) {
		chart = ChartFactory.createBarChart(title, null, null,
					dataset, PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.WHITE);
		setChartFont(yName);
		CategoryPlot cp = chart.getCategoryPlot();

		ValueAxis va = cp.getRangeAxis();
		// 设置最高的一个 Item 与图片顶端的距离
		va.setUpperMargin(0.25);
		// 设置最低的一个 Item 与图片底端的距离
		va.setLowerMargin(0.10);
		// 数据为0
		if(leftMaxValue == 0) {
			// 一系列设置
			va.setLowerBound(0);
			va.setUpperBound(5);;
		}
		cp.setRangeAxis(va);
		cp.setForegroundAlpha(1.0f);
		// 背景色 透明度
		cp.setBackgroundAlpha(0.5f);
		cp.setRangeGridlinesVisible(true);
		cp.setRangeGridlinePaint(Color.BLUE);
		cp.setBackgroundPaint(new Color(255, 255, 204));
		//设置bar显示, 如果加入则自动变为2D图
		BarRenderer renderer = new BarRenderer();
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue,
				0.0F, 0.0F, new Color(0, 0, 64)); // 设定特定颜色,三种:蓝色,绿色,红色
		GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F,
				Color.green, 0.0F, 0.0F, new Color(0, 64, 0));
		GradientPaint gradientpaint2 = new GradientPaint(0.0F, 0.0F, Color.red,
				0.0F, 0.0F, new Color(64, 0, 0));
		GradientPaint gradientpaint3 = new GradientPaint(0.0F, 0.0F, Color.orange,
				0.0F, 0.0F, new Color(64, 0, 0));
		// 把颜色加上去
		renderer.setSeriesPaint(0, gradientpaint); // 给series1 Bar
		renderer.setSeriesPaint(1, gradientpaint1); // 给series2 Bar
		renderer.setSeriesPaint(2, gradientpaint2); // 给series3 Bar
		renderer.setSeriesPaint(3, gradientpaint3); // 给series4 Bar
		// 设置平行柱之间距离
		renderer.setItemMargin(0.1);
		cp.setRenderer(renderer);
		//显示每个柱的数值, 并修改该数值的字体属性
		if(isShowNumber) {
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setBaseItemLabelsVisible(true);
			renderer.setItemLabelAnchorOffset(10D);// 设置柱形图上的文字偏离值
			cp.setForegroundAlpha((float) 0.85);
		}
	}

	/**
	 * 图标乱码解决
	 */
	private static void setChartFont(String yName) {
		Font title = new Font("宋体", Font.PLAIN, 15);
		Font font = new Font("宋体", Font.PLAIN, 13);
		// 标题
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(title);
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis ca = plot.getDomainAxis();
		// 设置X轴坐标上的文字
		ca.setTickLabelFont(font);
		// 设置X轴的标题文字
		ca.setLabelFont(font);
		ValueAxis va = plot.getRangeAxis();
		// 设置Y轴坐标上的文字
		va.setTickLabelFont(font);
		// 设置Y轴的标题文字
		va.setLabelFont(font);
		if(yName != null)
			va.setLabel(yName);
		chart.getLegend().setItemFont(font);
	}

	public static void main(String[] args) throws Exception {
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String,Object> rowMap = new HashMap<String, Object>();
		rowMap.put("type", "客户");
		rowMap.put("time", "2015-01");
		rowMap.put("showData", 220000);

		Map<String,Object> rowMap2= new HashMap<String, Object>();
		rowMap2.put("type", "客户");
		rowMap2.put("showData", 440000);
		rowMap2.put("time", "2015-02");

		Map<String,Object> rowMap3 = new HashMap<String, Object>();
		rowMap3.put("type", "客户222");
		rowMap3.put("time", "2015-01");
		rowMap3.put("showData", 21225);
		datas.add(rowMap);
		datas.add(rowMap2);
		datas.add(rowMap3);

		List<CharSet> lists = new ArrayList<CharSet>();
		for(Map<String,Object> m:datas){
			CharSet rowSet = new CharSet();
			rowSet.setColumKey(m.get("time").toString());
			rowSet.setValue(Double.valueOf(m.get("showData").toString()));
			rowSet.setRowKey(m.get("type").toString());
			lists.add(rowSet);
		}
		 create(lists,"测试柱状图","Z:/bb.png",600,400,"比率",true);
		 System.out.println(create(lists,"测试折线图",600,400,"比率",true));
	}
}