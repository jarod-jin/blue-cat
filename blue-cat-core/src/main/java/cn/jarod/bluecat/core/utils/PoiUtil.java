package cn.jarod.bluecat.core.utils;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


/**
 * @auther jarod.jin 2019/12/6
 */
@Slf4j
public class PoiUtil {

    public static List<String> readWord(InputStream is) {
        List<String> contextList = Lists.newArrayList();
        HWPFDocument document = null;
        WordExtractor extractor = null;
        try {
            document = new HWPFDocument(is);
            extractor = new WordExtractor(document);
            String[] contextArray = extractor.getParagraphText();
            Arrays.asList(contextArray).forEach(context -> contextList.add(CharMatcher.whitespace().removeFrom(context)));
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (null != extractor)try {
                extractor.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            if (null != document) try {
                document.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return contextList;
    }


    public static List<String> readWordX(InputStream is){
        List<String> contextList = Lists.newArrayList();
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(is).getXWPFDocument();
            List<XWPFParagraph> paragraphList = document.getParagraphs();
            paragraphList.forEach(paragraph -> contextList.add(CharMatcher.whitespace().removeFrom(paragraph.getParagraphText())));
            document.close();
        }  catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (null != document) try {
                document.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return contextList;
    }

}
