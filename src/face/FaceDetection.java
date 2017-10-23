package face;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacpp.opencv_objdetect.*;
import org.bytedeco.javacv.Frame;

import static face.Conversions.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import static org.bytedeco.javacpp.opencv_core.FONT_HERSHEY_PLAIN;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.putText;

public class FaceDetection {
    public static final String HAAR_FULL_BODY = "haarcascade_fullbody.xml";
    public static final String HAAR_UPPER_BODY = "haarcascade_upperbody.xml";
    public static final String HAAR_FRONTAL_FACE = "haarcascade_frontalface_default.xml";
    public static final String LBP_FRONTAL_FACE = "lbpcascade_frontalface_improved.xml";
    public static final String HAAR_EYE_EYEGLASSES = "haarcascade_eye_tree_eyeglasses.xml";
    private FaceRecognition fr = new FaceRecognition();
    private CascadeClassifier cascade;

    public FaceDetection(CascadeClassifier cascade) {
        this.cascade = cascade;
    }

    public FaceDetection(String cascadeName) {
        this.cascade = loadCascade(cascadeName);
    }

    public static CascadeClassifier loadCascade(String name) {
        File classifierFile = null;
        try {
            classifierFile = Loader.extractResource(name, null, "classifier", ".xml");
            if (classifierFile == null || classifierFile.length() <= 0) {
                throw new IOException("Could not extract \"" + name + "\" from Java resources.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // Preload the opencv_objdetect module to work around a known bug.
        Loader.load(opencv_objdetect.class);
        CascadeClassifier c = new CascadeClassifier(classifierFile.getAbsolutePath());
        if (c.empty())
            throw new IllegalArgumentException("Invalid Cascade Classifier!");
        return c;
    }

    public boolean detect(Frame frame, boolean addRectangle) {
        Mat frameMat = frameToMat(frame);
        return detect(frameMat, addRectangle);
    }

    public boolean detect(Mat frameMat, boolean addRectangle) {
        Mat frameGray = new Mat();
        // Convert the current frame to grayscale:
        cvtColor(frameMat, frameGray, COLOR_BGRA2GRAY);
        equalizeHist(frameGray, frameGray);
        RectVector faces = new RectVector();
        cascade.detectMultiScale(frameGray, faces);
        if (addRectangle) {
            for (int i = 0; i < faces.size(); i++) {
                Rect face_i = faces.get(i);
                rectangle(frameMat, face_i, new Scalar(0, 255, 0, 1));
                String box_text = "Caught!";
                int pos_x = Math.max(face_i.tl().x() - 10, 0);
                int pos_y = Math.max(face_i.tl().y() - 10, 0);
                putText(frameMat, box_text, new Point(pos_x, pos_y),
                        FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0, 2.0));
            }
        }
        return faces.size() > 0;
    }

    public boolean detect(Mat frameMat, boolean addRectangle, String text) {
        Mat frameGray = new Mat();
        // Convert the current frame to grayscale:
        cvtColor(frameMat, frameGray, COLOR_BGRA2GRAY);
        equalizeHist(frameGray, frameGray);
        RectVector faces = new RectVector();
        cascade.detectMultiScale(frameGray, faces);
        if (addRectangle) {
            for (int i = 0; i < faces.size(); i++) {
                Rect face_i = faces.get(i);
                rectangle(frameMat, face_i, new Scalar(0, 255, 0, 1));
                int pos_x = Math.max(face_i.tl().x() - 10, 0);
                int pos_y = Math.max(face_i.tl().y() - 10, 0);
                putText(frameMat, text, new Point(pos_x, pos_y),
                        FONT_HERSHEY_PLAIN, 2.0, new Scalar(0, 255, 0, 2.0));
            }
        }
        return faces.size() > 0;
    }
}
