package com.bridge.pdf.utils;

import com.bridge.enums.UtilsEnums;
import com.bridge.pdf.model.PdfBoxData;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bridge
 * @Date 2022/05/08/18:14
 */
@Slf4j
public class PDFBoxUtils {

    public static void main(String[] args) throws IOException {
        String savePath = "C:\\Users\\Administrator\\Desktop\\tmp\\pdf\\添加书签-" + System.currentTimeMillis() + ".pdf";
        File file = new File("C:\\Users\\Administrator\\Desktop\\tmp\\k8s尚硅谷\\03_尚硅谷大数据技术之实时项目-需求一日活.pdf");
        PDDocument pdDocument = PDFBoxUtils.load(file);
        if (pdDocument == null) {
            return;
        }
        List<PdfBoxData> allBookList = new ArrayList<>();
        int numberOfPages = pdDocument.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            allBookList.addAll(PDFBoxUtils.getPdfBoxTextList(pdDocument, i));
        }
        addMarkBook(pdDocument,allBookList, savePath);
        PDFBoxUtils.close(pdDocument);
    }

    public static void addMarkBook(PDDocument document, List<PdfBoxData> allBookList, String savePath) throws IOException {
        for (int i = 0; i < 10; i++) {
            document.addPage(new PDPage());
        }

        PDDocumentOutline documentOutline = new PDDocumentOutline();
        document.getDocumentCatalog().setDocumentOutline(documentOutline);
        PDOutlineItem pagesOutline = new PDOutlineItem();
        pagesOutline.setTitle("All Pages");
        documentOutline.addLast(pagesOutline);

        for (PdfBoxData pdfBoxData : allBookList) {
            PDPageDestination pageDestination = new PDPageFitWidthDestination();
            pageDestination.setPage(document.getPage(pdfBoxData.getPage()-1));
            PDOutlineItem bookmark = new PDOutlineItem();
            bookmark.setDestination(pageDestination);
            bookmark.setTitle(pdfBoxData.getTitle());
            pagesOutline.addLast(bookmark);
        }
        pagesOutline.openNode();
        documentOutline.openNode();
        document.getDocumentCatalog().setPageMode(PageMode.USE_OUTLINES);
        document.save(savePath);
    }

    public static List<PdfBoxData> getPdfBoxTextList(PDDocument document, int page) throws IOException {
        //文本剥离器
        PDFTextStripper stripper = new PDFTextStripper();
        //按页进行读取，页码从1开始
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        //按位置进行排序
        stripper.setSortByPosition(true);
        //获取文本
        String text = stripper.getText(document);
        String[] dataArr = text.split("\r\n");
        List<PdfBoxData> pdfBoxDataList = new ArrayList<>();
        for (String data : dataArr) {
            if (data.matches(UtilsEnums.CHAPTER_TITLE_REGEX.getCode()) ||
                    data.matches(UtilsEnums.FIRST_TITLE_REGEX.getCode())) {
                pdfBoxDataList.add(new PdfBoxData(data, page));
            }

        }
        return pdfBoxDataList;
    }


    /**
     * 从文件中加载pdf
     *
     * @param file 文件
     * @return
     * @throws IOException
     */
    public static PDDocument load(File file) throws IOException {
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        return PDDocument.load(file);
    }

    /**
     * 从文件流中加载pdf
     *
     * @param inputStream 文件输入流
     * @return
     * @throws IOException
     */
    public static PDDocument load(InputStream inputStream) throws IOException {
        if (inputStream == null || inputStream.available() == 0) {
            return null;
        }
        return PDDocument.load(inputStream);
    }

    /**
     * 创建一个单页的PDF空文档
     *
     * @param outputFile
     * @return
     * @throws IOException
     */
    public static PDDocument getBlankPDF(File outputFile) throws IOException {
        //首先创建pdf文档类
        PDDocument pdf = null;
        pdf = new PDDocument();
        //实例化pdf页对象
        PDPage blankPage = new PDPage();
        //插入文档类
        pdf.addPage(blankPage);
        //保存
        pdf.save(outputFile);
        return pdf;
    }

    /**
     * 获取pdf总页数
     *
     * @param pdf
     * @return
     */
    public static int pageCount(PDDocument pdf) {
        return pdf.getNumberOfPages();
    }

    /**
     * 获取pdf文档的所有分页对象
     *
     * @param pdf
     * @return 返回的list集合
     */
    public static List<PDPage> getPageList(PDDocument pdf) {
        int count = pageCount(pdf);
        List<PDPage> pages = new ArrayList<>(64);
        PDPageTree pdPages = pdf.getPages();
        for (int i = 0; i < count; i++) {
            PDPage pdPage = pdPages.get(i);
            pages.add(pdPage);
        }
        return pages;
    }


    /**
     * 给整个PDF文件分页，形成多个pdf单页文件
     *
     * @param inputStream  pdf文件流
     * @param outputParent 输出文件的父目录
     * @throws IOException
     */
    public static Integer pageSpilt(InputStream inputStream, File outputParent) throws IOException {
        if (!outputParent.exists() || !outputParent.isDirectory()) {
            throw new RuntimeException("输出文件的父目录不存在");
        }

        PDDocument pdf = load(inputStream);
        try {
            int numberOfPages = pageCount(pdf);
            for (int i = 0; i < numberOfPages; i++) {
                PDDocument document = new PDDocument();
                document.addPage(pdf.getPage(i));
                document.save(new File(outputParent, i + 1 + ".pdf"));
                close(document);
            }
            return numberOfPages;
        } finally {
            close(pdf);
            close(inputStream);
        }
    }


    /**
     * 合并多个单页PDF文件，输出一个合并后的PDF文档
     *
     * @param inputParent
     * @param outputFile
     * @param sortor
     * @throws IOException
     */
    public static void combine(File inputParent, String outputFile, FileSortor sortor) throws IOException {
        if (!inputParent.exists() || !inputParent.isDirectory()) {
            throw new RuntimeException("输入文件的父目录不存在");
        }
        if (new File(outputFile).exists()) {
            throw new RuntimeException("输出文件已存在");
        }
        File[] files = inputParent.listFiles();
        if (sortor != null) {
            sortor.sort(files);
        }
        PDFMergerUtility merger = new PDFMergerUtility();
        //输出目标路径
        merger.setDestinationFileName(outputFile);
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().toLowerCase().endsWith(".pdf")) {
                merger.addSource(files[i]);
            }
        }
        merger.mergeDocuments(null);
    }

    /**
     * 获取pdf单页分辨率
     *
     * @param page
     * @return
     */
    public static String getResolution(PDPage page) {
        PDRectangle rectangle = page.getArtBox();
        double width = Math.ceil(rectangle.getWidth());
        double height = Math.ceil(rectangle.getHeight());
        return (int) width + "*" + (int) height;
    }

    /**
     * 图片转PDF
     *
     * @param inputFile  图片路径
     * @param outputFile 生成pdf的文件路径
     * @throws IOException
     */
    public static void convertImgToPDF(String inputFile, String outputFile) throws IOException {
        if (!new File(inputFile).exists()) {
            throw new RuntimeException("输入文件不存在");
        }
        if (!outputFile.toLowerCase().endsWith(".pdf")) {
            throw new RuntimeException("只能转成pdf文件");
        }
        PDDocument document = new PDDocument();
        InputStream inputStream = new FileInputStream(inputFile);
        BufferedImage bimg = ImageIO.read(inputStream);
        float width = bimg.getWidth();
        float height = bimg.getHeight();
        PDPage page = new PDPage(new PDRectangle(width, height));
        document.addPage(page);
        PDImageXObject img = PDImageXObject.createFromFile(inputFile, document);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(img, 0, 0, width, height);
        contentStream.close();
        close(inputStream);
        document.save(outputFile);
        close(document);
    }


    public static void close(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void close(PDDocument pdf) {
        try {
            if (pdf != null) {
                pdf.close();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 文件排序器
     */
    public interface FileSortor {
        /**
         * 源文件组
         *
         * @param sources
         */
        void sort(File[] sources);
    }
}
