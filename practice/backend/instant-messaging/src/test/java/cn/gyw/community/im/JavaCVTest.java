//package cn.gyw.backend.im;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.nio.ShortBuffer;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//import javax.sound.sampled.AudioFileFormat;
//import javax.sound.sampled.AudioFormat;
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.DataLine;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.Mixer;
//import javax.sound.sampled.TargetDataLine;
//
//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.javacv.FFmpegFrameRecorder;
//import org.bytedeco.javacv.FFmpegLogCallback;
//import org.bytedeco.javacv.FrameRecorder.Exception;
//import org.junit.Test;
//
///**
// * 录音功能
// * 
// * 参考：https://github.com/bytedeco/javacv/blob/master/samples/WebcamAndMicrophoneCapture.java
// */
//public class JavaCVTest {
//	
//	/**
//	 * JDK api 录音
//	 * 
//	 * failed
//	 * @throws LineUnavailableException
//	 * @throws IOException
//	 */
//	@Test
//    public void testAduio() throws LineUnavailableException, IOException {
//        TargetDataLine line;
//        //定义声音格式，具体需要根据录音设备支持程度来定义
//        //我使用的设备
//        AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false); 
//        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
//        line = (TargetDataLine) AudioSystem.getLine(info);
//        line.open(audioFormat);
//        line.start(); //开始录音
//
//        File wavFile = new File("D:\\Temp\\audio\\testAudio.wav"); //要录制的文件
//
//        AudioInputStream ais = new AudioInputStream(line);
//        System.out.println("Start recording...");
////        try {
//////			TimeUnit.SECONDS.sleep(10L);
////		} catch (InterruptedException e) {
////			e.printStackTrace();
////		}
//        System.out.println("10s after...");
//        // start recording
//        // 阻塞
//        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, wavFile); //指定录制的音频格式
//        System.out.println(">>>>");
//        ais.close();
//    }
//
//
//	@Test
//	public void testAduioByJavaCV() throws Exception {
//		FFmpegLogCallback.set();
//		
//		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("D:\\Temp\\audio\\test.mp3", 1);
//		recorder.setAudioOption("crf", "0");
//		// Highest quality
//		recorder.setAudioQuality(0);
//		// 16 Kbps
//		recorder.setAudioBitrate(16000);
//		// 44.1MHZ
//		recorder.setSampleRate(44100);
//		// 1 channel
//		recorder.setAudioChannels(1);
//		// mp3
//		recorder.setAudioCodec(avcodec.AV_CODEC_ID_MP3);
////		recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
//		recorder.start();
//
//		AudioFormat audioFormat = new AudioFormat(44100.0F, 16, 1, true, false);
//
//		// 得到所有Mixer信息，通俗的说就是声音设备信息
//		Mixer.Info[] minfoSet = AudioSystem.getMixerInfo();
//		Mixer mixer = AudioSystem.getMixer(minfoSet[3]);
//		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
//
//		try {
//			// 初始化TargeLine，与使用JDK一样
//
//			// TargetDataLine line = (TargetDataLine)mixer.getLine(dataLineInfo);
//			// //可以使用声音设备索引来录制音频
//			TargetDataLine line = (TargetDataLine) AudioSystem.getLine(dataLineInfo);// 这个就是查找默认可用的录音设备，没有特殊指定
//			line.open(audioFormat);
//			line.start();
//
//			int sampleRate = (int) audioFormat.getSampleRate();
//			int numChannels = audioFormat.getChannels();
//
//			// Let's initialize our audio buffer...
//			int audioBufferSize = sampleRate * numChannels;
//			byte[] audioBytes = new byte[audioBufferSize];
//
//			// Using a ScheduledThreadPoolExecutor vs a while loop with
//			// a Thread.sleep will allow
//			// us to get around some OS specific timing issues, and keep
//			// to a more precise
//			// clock as the fixed rate accounts for garbage collection
//			// time, etc
//			// a similar approach could be used for the webcam capture
//			// as well, if you wish
//			ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
//			exec.scheduleAtFixedRate(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						// Read from the line... non-blocking
//						int nBytesRead = 0;
//						while (nBytesRead == 0) {
//							nBytesRead = line.read(audioBytes, 0, line.available());
//						}
//
//						// Since we specified 16 bits in the AudioFormat,
//						// we need to convert our read byte[] to short[]
//						// (see source from FFmpegFrameRecorder.recordSamples for AV_SAMPLE_FMT_S16)
//						// Let's initialize our short[] array
//						int nSamplesRead = nBytesRead / 2;
//						short[] samples = new short[nSamplesRead];
//
//						// Let's wrap our short[] into a ShortBuffer and
//						// pass it to recordSamples
//						ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(samples);
//						ShortBuffer sBuff = ShortBuffer.wrap(samples, 0, nSamplesRead);
//
//						// recorder is instance of
//						// org.bytedeco.javacv.FFmpegFrameRecorder
//						recorder.recordSamples(sampleRate, numChannels, sBuff);
//						System.out.println("recorder samples size:" + nSamplesRead);
//					} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}, 0, (long) 1000 / 1, TimeUnit.MILLISECONDS);
//		} catch (LineUnavailableException e1) {
//			e1.printStackTrace();
//		}
//
//		// 仅用于测试，有点低级，仅测试功能，实际项目中需要通过标志来控制线程
//		for (;;) {
//		}
//	}
//
//}
