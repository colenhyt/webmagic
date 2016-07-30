package us.codecraft.webmagic.proxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author code4crafter@gmail.com <br>
 * @since 0.4.0
 */
public class ProxyDataGetProcessor implements PageProcessor {

    private Site site;
    List<String> proxyUrls = new ArrayList<String>();
    private List<ProxyDesc> proxyList = new ArrayList<ProxyDesc>();

    public ProxyDataGetProcessor(String userAgent){
    	String startUrl = "http://www.xicidaili.com/nn/";
    	
    	proxyUrls.add("http://www.proxy360.cn");
    	
    	site = new Site();
    	if (userAgent!=null)
    		site.addHeader("User-Agent", userAgent);
		Set<Integer> codes = new HashSet<Integer>();
		codes.add(200);
		codes.add(404);
		site.setAcceptStatCode(codes);
		site.addStartUrl(startUrl);
    }
    
    public List<ProxyDesc> getProxyList(){
    	return proxyList;
    }
    
    public void process1(Document doc){
		Elements els = doc.select("table").select("[id=ip_list]");
		Elements trs = els.select("tr");
		for (int i=1;i<trs.size();i++){
			Elements tds = trs.get(i).select("td");
			ProxyDesc desc = new ProxyDesc();
			desc.setIp(tds.get(1).text().trim());
			desc.setPort(tds.get(2).text().trim());
			desc.setProtocol(tds.get(5).text().trim());
			proxyList.add(desc);
		}
		System.out.println("找到代理IP: "+proxyList.size());
    }
    
    @Override
    public void process(Page page) {
    	String url = page.getRequest().getUrl();
    	Document doc = page.getHtml().getDocument();
    	
    	if (url.indexOf("xicidaili.com")>0){
    		process1(doc);
    	}else if (url.indexOf("proxy360")>0){
    		Elements els = doc.select("div").select("[class=proxylistitem]");
    		for (Element ee:els){
    			ProxyDesc desc = new ProxyDesc();
        		Elements tds = ee.select("span").select("[class=tbBottomLine]");
        		desc.setIp(tds.get(0).text().trim());
        		desc.setPort(tds.get(1).text().trim());
        		proxyList.add(desc);
    		}
    		System.out.println(url+"找到代理IP: "+proxyList.size());
    	}
    	
    	page.addTargetRequests(proxyUrls);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
    	String agent = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)";
        Spider spider = Spider.create(new ProxyDataGetProcessor(agent)).thread(1);
        spider.run();
        spider.close();
    }
}
