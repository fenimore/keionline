package org.keionline.keionline.feedlib;

import java.util.List;

public abstract class SimpleRss2ParserCallback {

	public abstract void onFeedParsed(List<RSSItem> items);

	public abstract void onError(Exception ex);
}
