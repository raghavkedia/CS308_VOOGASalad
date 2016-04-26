package utility;

/**
 * @author austinwu
 * Based on some code from https://www.javacodegeeks.com/2011/02/xuggler-tutorial-frames-capture-video.html
 */
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

import engine.frontend.overall.EngineView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

public class GameCapture implements IGameCapture {

	public static final String DEFAULT_RESOURCE = "utility/gamecapture";
	private ResourceBundle myResources;

	private EngineView myEngineView;
	private IMediaWriter fileWriter;

	private boolean capture;
	private long startTime;

	private String fileName;
	private File saveLocation;
	private String imageFormat;
	private ICodec.ID videoFormat;

	private File lastSavedFile;
	private int fps;

	/*
	 * Todos:
	 * Get dimensions to match the game and not the whole screen
	 * Write explanation for the "export file"
	 */
	
	public GameCapture(EngineView ev) {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE);
		myEngineView = ev;
		fileName = myResources.getString("DefaultName");
		saveLocation = new File(myResources.getString("DefaultSaveLocation"));
		imageFormat = myResources.getString("DefaultImageFormat");
		videoFormat = ICodec.ID.valueOf(myResources.getString("DefaultVideoFormat"));
		fps = Integer.parseInt(myResources.getString("DefaultFrameRate"));
	}

	@Override
	public void startCapture() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
				Rectangle captureSize = new Rectangle(bounds);
				lastSavedFile = new File(saveLocation + File.separator + fileName + System.currentTimeMillis()
						+ myResources.getString("DefaultVideoExtension"));
				fileWriter = ToolFactory.makeWriter(lastSavedFile.toString());
				fileWriter.addVideoStream(0, 0, videoFormat, bounds.width / 2, (int) bounds.height / 2);
				capture = true;
				try {
					Robot robot = new Robot();
					while (capture) {
						takeAFrame(robot, captureSize);
					}
					fileWriter.close();
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});
		thread.start();
	}

	private void takeAFrame(Robot robot, Rectangle captureSize) {
		BufferedImage screen = robot.createScreenCapture(captureSize);
		BufferedImage bgrScreen = convertToType(screen, BufferedImage.TYPE_3BYTE_BGR);
		fileWriter.encodeVideo(0, bgrScreen, System.currentTimeMillis() - startTime, TimeUnit.MILLISECONDS);
		
		try {
			Thread.sleep(1000 / fps);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void endCapture() {
		capture = false;
	}

	@Override
	public void takeScreenshot(Node n) {
		String outputFileName = saveLocation + File.separator + fileName + System.currentTimeMillis() + "." + imageFormat;
		WritableImage image = n.snapshot(new SnapshotParameters(), null);
		BufferedImage bi = SwingFXUtils.fromFXImage(image, null);
		BufferedImage convertedImg = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
	    convertedImg.getGraphics().drawImage(bi, 0, 0, null);
		
	    try {
			ImageIO.write(convertedImg, imageFormat, new File(outputFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public File exportFile(Event exportEvent) {
		return lastSavedFile;
	}

	@Override
	public void setImageFileType(String imageFileType) {
		imageFormat = imageFileType;
	}

	@Override
	public String getImageFileType() {
		return imageFormat;
	}

	@Override
	public void setFramesPerSecond(int numFramesPerSecond) {
		fps = numFramesPerSecond;
	}
	
	@Override
	public int getFramesPerSecond() {
		return fps;
	}

	@Override
	public void setSaveLocation(File f) {
		saveLocation = f;
	}

	@Override
	public File getSaveLocation() {
		return saveLocation;
	}

	@Override
	public void setFileName(String f) {
		fileName = f;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	private BufferedImage convertToType(BufferedImage sourceImage, int targetType) {

		BufferedImage image;

		// if the source image is already the target type, return the source
		// image
		if (sourceImage.getType() == targetType) {
			image = sourceImage;
		}
		// otherwise create a new image of the target type and draw the new
		// image
		else {
			image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}

		return image;
	}

}
