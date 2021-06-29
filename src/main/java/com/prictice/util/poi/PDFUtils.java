package com.prictice.util.poi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

/**
 *  PDF 工具类
 */
public class PDFUtils {
	
    /**
     * 创建PDF文件
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
    public static void createPDF(String content,OutputStream out) throws DocumentException, IOException {
			// step 1
			Document document = new Document();
			// step 2
			PdfWriter writer = PdfWriter.getInstance(document, out);
			// step 3
			document.open();
			// step 4
			// CSS
			CSSResolver cssResolver = new StyleAttrCSSResolver();
			CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream("body {font-family:tsc fming s tt}".getBytes()));
			cssResolver.addCss(cssFile);
			// 将ChunkCssApplier 设置为自定义的， 解决中文问题
			CssAppliers cssAppliers = new CssAppliersImpl();
			cssAppliers.setChunkCssAplier(new MyChunkCssApplier());
			HtmlPipelineContext htmlContext = new HtmlPipelineContext(
					cssAppliers);
			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

			// Pipelines
			PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
			HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
			CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
			// XML Worker
			XMLWorker worker = new XMLWorker(css, true);
			XMLParser p = new XMLParser(worker);
			p.parse(IOUtils.toInputStream(content));
			// step 5
			document.close();
    }
    /**
     * Main method
     */
    public static void main(String[] args) throws IOException, DocumentException {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new ParserHTML().createPdf(DEST);
    	FileOutputStream out = new FileOutputStream(new File("d://2.pdf"));
    	PDFUtils.createPDF("<p>你好世界,hello world</p>", out);
    }
}
