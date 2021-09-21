package cn.gyw.thirdpart.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class CreatePieChart {

	private static final String CHART_PATH = "D:/test/chart/";

	private static JFreeChart chart;

	private static PieDataset dataset;

	private static String[] pieLegendKeys;

	/***
	 * 返回一个base64的图片字符串
	 * @param list 饼图数据
	 * @param title 标题
	 * @param weight 宽度
	 * @param height 高度
	 * @throws Exception
	 */
	public static String create(List<Map<String,Object>> list,
			String title,int weight, int height) throws Exception {
		getPieDataSet(list);
		createChart(dataset,title,pieLegendKeys);
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

	// 饼状图 数据集
	private static void getPieDataSet(List<Map<String,Object>> list) {
	    DefaultPieDataset dpd = new DefaultPieDataset();
	    String[] legendKeys = new String[list.size()];
	    int count = 0 ;
	    for(Map<String,Object> map : list){
	        dpd.setValue(map.get("GZ_REASON").toString(), Integer.parseInt(map.get("GZ_CNT").toString()));
	        legendKeys[count++] = map.get("GZ_REASON").toString();
	    }
	    dataset = dpd;
	    pieLegendKeys = legendKeys;
	}

	@SuppressWarnings("restriction")
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

	/**
	 * 饼状图
	 *
	 * @param dataset
	 *            数据集
	 * @param chartTitle
	 *            图标题
	 * @param charName
	 *            生成图的名字
	 * @param pieKeys
	 *            分饼的名字集
	 * @return
	 */
	private static void createChart1(PieDataset dataset, String chartTitle,
			String[] pieKeys) {
		chart = ChartFactory.createPieChart(chartTitle, // chart title
				dataset, // data
				true, // include legend
				true,
				false);
		// 使下说明标签字体清晰,去锯齿类似于
		// chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);的效果
		chart.setTextAntiAlias(false);
		// 图片背景色
		chart.setBackgroundPaint(Color.white);
		// 设置图标题的字体重新设置title
//		Font font = new Font("隶书", Font.BOLD, 25);
		Font font = new Font("宋体", Font.PLAIN, 15);
		TextTitle title = new TextTitle(chartTitle);
		title.setFont(font);
		chart.setTitle(title);
		//加个副标题
//		chart.addSubtitle(new TextTitle("2010年度"));
		PiePlot plot = (PiePlot) chart.getPlot();
//		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		// 图片中显示百分比:默认方式

		// 指定饼图轮廓线的颜色
		// plot.setBaseSectionOutlinePaint(Color.BLACK);
		// plot.setBaseSectionPaint(Color.BLACK);

		// 设置无数据时的信息
		plot.setNoDataMessage("无对应的数据，请重新查询。");
		// 设置无数据时的信息显示颜色
		plot.setNoDataMessagePaint(Color.red);

		plot.setIgnoreNullValues(true);// 设置不显示空位
		plot.setIgnoreZeroValues(true);// 设置不显示负值或零值

		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}({2})", NumberFormat.getNumberInstance(),
				NumberFormat.getPercentInstance()));
		// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}({2})"));

		plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 11));

		// 指定图片的透明度，0.5F为半透明，1为不透明，0为全透明
		plot.setForegroundAlpha(1f);

		// 指定显示的饼图上圆形(false)还椭圆形(true)
		plot.setCircular(false, true);

		// 设置第一个 饼块section 的开始位置，默认是12点钟方向
//		plot.setStartAngle(120D);
		plot.setStartAngle(90);

		//设置方向为"顺时针方向"
		plot.setDirection(Rotation.CLOCKWISE);

		//第一个参数是key,第二个参数是突出显示的大小（可以自己调整一下看看效果就明白了）
		plot.setExplodePercent("黑心矿难",0.23);

		// 设置分饼颜色
		plot.setSectionPaint(pieKeys[0], new Color(244, 194, 144));
		plot.setSectionPaint(pieKeys[1], new Color(144, 233, 144));

//		ChartPanel panel = new ChartPanel(chart, true);
        chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 18));
        chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

        //指定目录生成饼图图片文件
        FileOutputStream fos_jpg = null;
		try {
			// 文件夹不存在则创建
//			isChartPathExist(CHART_PATH);
			String chartName = CHART_PATH + "test.png";
			fos_jpg = new FileOutputStream(chartName);
			// 高宽的设置影响椭圆饼图的形状
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 230);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos_jpg.close();
				System.out.println("create pie-chart.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private static void createChart(PieDataset dataset, String chartTitle,
			String[] pieKeys) {
		chart = ChartFactory.createPieChart(chartTitle, // chart title
				dataset, // data
				true, // include legend
				true,
				false);
		chart.setTextAntiAlias(false);
		// 图片背景色
		chart.setBackgroundPaint(Color.white);
		// 设置图标题的字体重新设置title
//		Font font = new Font("宋体", Font.PLAIN, 15);
//		TextTitle title = new TextTitle(chartTitle);
//		title.setFont(font);
//		chart.setTitle(title);

		PiePlot plot = (PiePlot) chart.getPlot();
		// 设置无数据时的信息
		plot.setNoDataMessage("无对应的数据，请重新查询。");
		// 设置无数据时的信息显示颜色
		plot.setNoDataMessagePaint(Color.red);

		plot.setIgnoreNullValues(true);// 设置不显示空位
		plot.setIgnoreZeroValues(true);// 设置不显示负值或零值
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}次({2})", NumberFormat.getNumberInstance(),
		        NumberFormat.getPercentInstance()));
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}"));
		plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 11));
		// 指定图片的透明度，0.5F为半透明，1为不透明，0为全透明
		plot.setForegroundAlpha(0.8f);
		// 指定显示的饼图上圆形(false)还椭圆形(true)
		plot.setCircular(false, true);
		// 设置第一个 饼块section 的开始位置，默认是12点钟方向
		plot.setStartAngle(90);
		//设置方向为"顺时针方向"
		plot.setDirection(Rotation.CLOCKWISE);
		// 设置分饼颜色
//		plot.setSectionPaint(pieKeys[0], new Color(244, 194, 144));
//		plot.setSectionPaint(pieKeys[1], new Color(144, 233, 144));
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
        chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 12));

        //指定目录生成饼图图片文件
        FileOutputStream fos_jpg = null;
		try {
			// 文件夹不存在则创建
//			isChartPathExist(CHART_PATH);
			String chartName = CHART_PATH + "Pie_chart_test.png";
			fos_jpg = new FileOutputStream(chartName);
			// 高宽的设置影响椭圆饼图的形状
			ChartUtilities.writeChartAsPNG(fos_jpg, chart, 500, 230);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos_jpg.close();
				System.out.println("create pie-chart.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String args[]) {
		DefaultPieDataset dpd = new DefaultPieDataset();
		dpd.setValue("黑心矿难", 720);
		dpd.setValue("醉酒驾驶", 530);
		dpd.setValue("城管强拆", 210);
		dpd.setValue("医疗事故", 91);
		dpd.setValue("其他", 66);

		String[] pieKeys = {"黑心矿难", "醉酒驾驶","城管强拆","医疗事故","其他"};

		createChart(dpd, "饼状图", pieKeys);
	}
}
