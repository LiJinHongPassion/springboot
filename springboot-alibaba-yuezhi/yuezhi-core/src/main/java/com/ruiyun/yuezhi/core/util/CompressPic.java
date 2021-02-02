package com.ruiyun.yuezhi.core.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * 图片处理
 * 
 * @author Jiangbo
 *
 */
public class CompressPic {

	/**
	 * 图片压缩
	 * 
	 * @param inputDir
	 * @param outputDir
	 * @param inputFileName
	 * @param outputFileName
	 * @param width
	 * @param height
	 * @param isProportion
	 *            :是否等比例
	 * @return
	 */
	public static boolean compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height,
			boolean isProportion) {
		FileOutputStream out = null;

		try {
			// 获得源文件
			File file = new File(inputDir + inputFileName);
			if (!file.exists()) {
				System.out.println("没有找到源文件");
				return false;
			}

			Image img = null;
			try {
				img = ImageIO.read(file);
			} catch (Exception e) {
				System.out.println("CMYK格式图片，直接使用源图！");
				// 将源图直接复制
				FileUtil.copyFile(inputDir + inputFileName, outputDir + outputFileName, true);
				return true;
			}

			// 判断图片格式是否正确
			if (img.getWidth(null) <= width) {
				// 本身图片比要求的要小，直接复制
				FileUtil.copyFile(inputDir + inputFileName, outputDir + outputFileName, true);
				return true;
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (isProportion == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;
					double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = width; // 输出的图片宽度
					newHeight = height; // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
				File f = new File(outputDir);
				if (!f.exists()) {
					f.mkdirs();
				}

				out = new FileOutputStream(outputDir + outputFileName);
				// JPEGImageEncoder可适用于其他图片类型的转换
				// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				// encoder.encode(tag);
				ImageIO.write(tag, outputFileName.substring(outputFileName.indexOf(".") + 1), out);
				out.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// 压缩过程中出现异常，直接复制
			FileUtil.copyFile(inputDir + inputFileName, outputDir + outputFileName, true);
		} finally {
			if (null != out) {
				try {
					out.flush();
					out.close();
				} catch (Exception e) {
					System.out.println("关闭文件流失败！");
				}
			}
		}

		return true;
	}

	public static void main(String[] args) {
		CompressPic.compressPic("d:\\", "d:\\", "1469023324047_6219.jpg", "s_" + "1469023324047_6219.jpg", 800, 800, true);
	}
}
