package cn.gyw.thirdpart.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.*;
@SuppressWarnings("restriction")
public class CreateLineChart {

	private static JFreeChart chart;

	private static CategoryDataset dataset;

	// 折线图Y轴右边最大值
	private static Double rightMaxValue;
	/***
	 * 返回一个base64的图片字符串
	 * @param lists
	 * @param title
	 * @param path
	 * @param weight
	 * @param height
	 * @param yName
	 * @param isShowNumber
	 * @throws Exception
	 */
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

	public static void create(List<CharSet> lists, String title, String path,
			int weight, int height, String yName, boolean isShowNumber) throws Exception {
		getDataset(lists);
		createChart(title, yName, isShowNumber);
		FileOutputStream out = new FileOutputStream(path);
		ChartUtilities.writeChartAsPNG(out, chart, weight, height);
	}

	private static void createChart(String title, String yName,
			boolean isShowNumber) throws Exception {

		   chart = ChartFactory.createLineChart(title, null, null,
				dataset, PlotOrientation.VERTICAL, true, true, false);
		   CategoryPlot cp =  chart.getCategoryPlot();
			chart.setBackgroundPaint(Color.WHITE);
			setChartFont(yName);
			cp.setBackgroundPaint(Color.LIGHT_GRAY);
			cp.setRangeGridlinePaint(Color.BLUE);// 背景底部横虚线
			cp.setOutlinePaint(Color.RED);// 边界线
			// 背景色 透明度
			cp.setBackgroundAlpha(0.5f);
			cp.setRangeGridlinesVisible(true);
			cp.setRangeGridlinePaint(Color.BLUE);
			cp.setBackgroundPaint(new Color(255, 255, 204));
			ValueAxis rangeAxis = cp.getRangeAxis();
			// 设置最高的一个 Item 与图片顶端的距离
			rangeAxis.setUpperMargin(0.15);
			// 设置最低的一个 Item 与图片底端的距离
			rangeAxis.setLowerMargin(0.15);


			/*------设置Y轴----*/
			// 右边Y轴, 相关属性设置
			NumberAxis numberaxisY = new NumberAxis();
			// 数据为0
			double size = 1D;
			if(rightMaxValue <= 0) {
				numberaxisY.setRange(0, 100);//刻度范围
				size = Math.floor(20);//刻度的长度
			} else {
				size = rightMaxValue / 5;
				numberaxisY.setRange(0.0, rightMaxValue); // 刻度范围
			}
			NumberTickUnit tickUnit = new NumberTickUnit(size);// 刻度的长度
			numberaxisY.setTickUnit(tickUnit);
			numberaxisY.setLabel(yName);
			if(rightMaxValue<=0){
		      cp.setRangeAxis(0, numberaxisY);
			  cp.setDataset(1, dataset);
			}else
				cp.setRangeAxis(rangeAxis);
			// 获显示线条对象
	        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) cp.getRenderer();
	        lineandshaperenderer.setBaseShapesVisible(true);
	        lineandshaperenderer.setDrawOutlines(true);
	        lineandshaperenderer.setUseFillPaint(true);
	        lineandshaperenderer.setBaseFillPaint(Color.white);
	        // 设置折线拐点
	        lineandshaperenderer.setSeriesShape(0,
	                new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
	 		// 显示数值
	        if(isShowNumber) {
	        	lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	        	lineandshaperenderer.setBaseItemLabelsVisible(true);
	        	cp.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);// 设置折线图显示在柱形图上面
	        	cp.setRenderer(1, lineandshaperenderer);
	        }

	}

	private static void getDataset(List<CharSet> lists) {
	   DefaultCategoryDataset dcd = new DefaultCategoryDataset();
	/*		for(CharSet cs : lists) {
			dcd.addValue(cs.getValue(), cs.getRowKey(), cs.getColumKey());
		}
		dataset = dcd;
		*/
		rightMaxValue = 0D;
		for(CharSet cs : lists) {
			dcd.addValue(cs.getValue(), cs.getRowKey(), cs.getColumKey());
			if(rightMaxValue < cs.getValue())
				rightMaxValue = cs.getValue();
		}
//		rightMaxValue = rightMaxValue * 1.2; //最大值的1.2倍
		dataset = dcd;
	}

	/**
	 * 图标乱码解决
	 */
	private static void setChartFont(String yName) {
		Font title = new Font("宋体", Font.PLAIN, 15);
		Font font = new Font("宋体", Font.PLAIN, 13);
		// 标题
		TextTitle tt = chart.getTitle();
		tt.setFont(title);
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

	public static void main(String[] args) throws Exception {
		/*
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String,Object> rowMap = new HashMap<String, Object>();
		rowMap.put("type", "客户");
		rowMap.put("time", "2015-01");
		rowMap.put("showData", 222200000);
		Map<String,Object> rowMap2= new HashMap<String, Object>();
		rowMap2.put("type", "客户");
		rowMap2.put("showData", 0);
		rowMap2.put("time", "2015-02");
		datas.add(rowMap);
		datas.add(rowMap2);

		List<CharSet> lists = new ArrayList<CharSet>();
		for(Map<String,Object> m:datas){
			CharSet rowSet = new CharSet();
			rowSet.setColumKey(m.get("time").toString());
			rowSet.setValue(Double.valueOf(m.get("showData").toString()));
			rowSet.setRowKey(m.get("type").toString());
			lists.add(rowSet);
		}
		 create(lists,"测试折线图","Z:/aa.png",600,400,"比率",true);
		 System.out.println(create(lists,"测试折线图",600,400,"比率",true));
		 */

		 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	     Map<String,Object> rowRecord = new HashMap<String, Object>();
	     rowRecord.put("lineId","CMNET-BQ-B-MAS-200262");
	     rowRecord.put("date","2016-03-04");
	     rowRecord.put("avg",1.35);

	     Map<String,Object> rowRecord2 = new HashMap<String, Object>();
	     rowRecord2.put("lineId","CMNET-BQ-B-MAS-200262");
	     rowRecord2.put("date","2016-03-03");
	     rowRecord2.put("avg",75.56);



	     Map<String,Object> rowRecord4 = new HashMap<String, Object>();
	     rowRecord4.put("lineId","CMNET-BQ-B-MAS-200262");
	     rowRecord4.put("date","2016-03-01");
	     rowRecord4.put("avg",98.25);


	     list.add(rowRecord);
	     list.add(rowRecord2);

	     list.add(rowRecord4);


	     List<CharSet> charList = new ArrayList<CharSet>();
		 for(Map<String,Object> m:list){
			CharSet rowSet = new CharSet();
			rowSet.setColumKey(m.get("date").toString());
			rowSet.setValue(Double.valueOf(m.get("avg").toString()));
			rowSet.setRowKey("丢包率");
			charList.add(rowSet);
		 }
			 create(charList,"CMNET-BQ-B-MAS-200262","D:/rate.png",600,400,"单位(%)",true);
	}
}