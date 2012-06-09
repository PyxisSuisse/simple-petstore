package test.support.org.testinfected.petstore.web;

import org.mozilla.universalchardet.CharsetListener;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.IOException;

public class CharsetDetector {

    public static String detectCharset(byte[] data) throws IOException {
        UniversalDetector charsetDetector = new UniversalDetector(new CharsetListener() {
            public void report(String charset) {
            }
        });
        charsetDetector.handleData(data, 0, data.length);
        charsetDetector.dataEnd();
        return charsetDetector.getDetectedCharset();
    }

    private CharsetDetector() {}
}