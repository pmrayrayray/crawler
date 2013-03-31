package net.raysear.crawler.scheduler;

import net.raysear.crawler.filter.UrlFilter;
import net.raysear.crawler.http.Url;

public interface Scheduler {

    boolean needToVisit(Url url);
    
    void setMaxDepth(int maxDepth);
    
    boolean reachMaxDepth(Url url);
    
    void setDomainName(String url);
    
    boolean isDomainLimited();
    
    void enableDomainLimit();
    
    void disableDomainLimit();
    
    void setUrlFilter(UrlFilter urlFilter);
}