package face;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
/**
 * @author Hanzhi Zhou
 * implementations of some common conversions
 * */
public class Conversions {
    private static OpenCVFrameConverter toMat = new OpenCVFrameConverter.ToMat();
    private static Java2DFrameConverter toBuffImg = new Java2DFrameConverter();

    public static opencv_core.Mat frameToMat(Frame frame) {
        return toMat.convertToMat(frame);
    }

    public static BufferedImage frameToBuff(Frame frame) {
        return toBuffImg.convert(frame);
    }

    public static Frame matToFrame(opencv_core.Mat mat) {
        return toMat.convert(mat);
    }

    public static byte[] buffedImgToBytes(BufferedImage img){
        byte[] barr = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            barr = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return barr;
    }

    public static byte[] imageToByte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }
}
