package common_interface;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;

/*
 * @author mkyong
 *
 */
public class ImageResize {

	private static final int IMG_WIDTH = 150;
	private static final int IMG_HEIGHT = 150;
	
	
	public static void image_re(String name, String image_path){

		try{

			BufferedImage originalImage = ImageIO.read(new File(image_path));
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

			BufferedImage resizeImageJpg = resizeImage(originalImage, type);
			//		ImageIO.write(resizeImageJpg, "jpg", new File(".\\dp\\"+name+".jpg")); 
			ImageIO.write(resizeImageJpg, "jpg", new File("dp\\"+name+".jpg")); 

		}catch(IOException e){
			System.out.println(e.getMessage());
		}

	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}

	private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){

		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();	
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}	
} 