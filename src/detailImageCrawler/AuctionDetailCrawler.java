package detailImageCrawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dataModel.DetailListRowModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AuctionDetailCrawler {
	
	private Document html; //웹 사이트에서 가져온 html소스를 저장하
	private Connection conn; //데이터를 크롤링 해올 웹사이트를 연결해줄 객체
	
	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.34";

	
	public AuctionDetailCrawler(String url){
		Connection conn = Jsoup.connect(url).userAgent(USER_AGENT);
		try {
			html=conn.get(); //가져온 html소스를 필드에 저장
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void imageCrawling() {
		System.out.println("크롤링 진입");
		Elements con=html.select("div.seller_area");
		System.out.println("가져온 이미지 주소 : "+html.outerHtml());
		Elements conImage=null;
		String imageUrl=null;
		int i=0;
		while(true) {
			conImage=con.select("iframe");
			try {
				imageUrl=conImage.get(i).attr("src");
				
				if(imageUrl.equals("about:blank")) {
					imageUrl=null;
				}else {
					
				}
				
				DetailListRowModel.list.add(new DetailListRowModel(new ImageView(new Image(imageUrl))));
			}catch(IndexOutOfBoundsException e) {
				return;
			}
			i++;
		}
	}
}
