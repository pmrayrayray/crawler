package net.raysear.crawler.demo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.raysear.crawler.PageCrawler;
import net.raysear.crawler.config.CrawlerConfiguration;
import net.raysear.crawler.exception.CrawlerException;
import net.raysear.crawler.filter.UrlFilter;
import net.raysear.crawler.http.Page;
import net.raysear.crawler.http.Status;
import net.raysear.crawler.http.Url;
import net.raysear.crawler.visitor.PageVisitor;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class CrawlerTest {
	
	public static void main(String[] args) {
		DOMConfigurator.configure("log4j.xml");
		Logger log = Logger.getLogger(CrawlerTest.class);
		String beginUrl = "http://baseUrl/";
		log.info("Crawler start.");
		CrawlerConfiguration cfg = new CrawlerConfiguration();
		PageCrawler crawler = new PageCrawler(cfg);
		crawler.enableDomainLimit();
		crawler.setMaxdepth(10);
		crawler.crawl(beginUrl, new ImageGrabber(), new NoDocFilter());
		log.info("Crawler stop.");
	}
}
class NoDocFilter implements UrlFilter {
	
	public boolean isLinkNeedToSearch(String url) {
		if (url.endsWith(".pdf")) return false;
		if (url.endsWith(".doc") || url.endsWith(".docx")) return false;
		if (url.endsWith(".ppt") || url.endsWith(".pptx")) return false;
		if (url.endsWith(".ps")) return false;
		if (url.endsWith(".rar")) return false;
		if (url.endsWith(".xml")) return false;
		return true;
	}
}

class ImageGrabber implements PageVisitor {
	
	private static Logger log = Logger.getLogger(ImageGrabber.class);
	File dir;
	public ImageGrabber() {
		dir = new File("images");
		if (!dir.exists()) dir.mkdir();
	}
	public boolean isValid(String urlStr) {
		if (urlStr.endsWith(".jpg")|| urlStr.endsWith(".png") || urlStr.endsWith(".gif"))
			return true;
		return false;
	}
    public void onError(Url url, final Status statusError) {
    	log.info(url + " --Failed.");
    }

    public void visit(Page page) {
    	String mimeType = page.getMimeType();
    	if (mimeType.startsWith("image") && isValid(page.getUrl())) 
    		storeImagesToPath(page);
    }
    private void storeImagesToPath(Page page) {
    	String url = page.getUrl();
    	log.info("Downloading " + url);
    	byte[] data = page.getByteData();
    	String imageFileName = url.replace('/', '_');
		try {
			FileOutputStream out = new FileOutputStream(new File(dir, imageFileName));
			out.write(data);
			out.close();
			log.info("Download " + url + " done." );
		} catch (IOException e) {
			throw new CrawlerException("Could not create file: " + imageFileName, e);
		}
    }
}
