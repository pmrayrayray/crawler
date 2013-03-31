
package net.raysear.crawler;

import net.raysear.crawler.http.Page;


public interface Downloader {

    public Page get(String url);

}
