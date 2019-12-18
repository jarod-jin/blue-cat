package cn.jarod.bluecat.core.utils;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author jarod.jin 2019/12/6
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
            contextList.addAll(Arrays.stream(contextArray)
                    .map(context->CharMatcher.whitespace().removeFrom(context))
                    .filter(StringUtils::hasText)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (null != extractor){
                try {
                    extractor.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (null != document) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
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
            contextList.addAll(paragraphList.stream()
                    .map(paragraph -> CharMatcher.whitespace().removeFrom(paragraph.getParagraphText()))
                    .filter(StringUtils::hasText)
                    .collect(Collectors.toList()));
            document.close();
        }  catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (null != document) {
                try {
                    document.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return contextList;
    }

}
