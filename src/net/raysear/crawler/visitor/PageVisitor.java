package net.raysear.crawler.visitor;

import net.raysear.crawler.http.Page;
import net.raysear.crawler.http.Status;
import net.raysear.crawler.http.Url;


public interface PageVisitor {

	void visit(Page page);

	void onError(Url errorUrl, Status statusError);

}
