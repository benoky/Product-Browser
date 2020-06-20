package detailImageCrawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dataModel.DetailListRowModel;
import dataModel.TableRowModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WempDetailCrawler {
	
	private Document html; //웹 사이트에서 가져온 html소스를 저장하
	private Connection conn; //데이터를 크롤링 해올 웹사이트를 연결해줄 객체
	
	Elements conImage;
	
	public WempDetailCrawler(String url){
		conn=Jsoup.connect(url); //매개변수로 가져온 url을 이용해 웹 사이트와 연결
		try {
			html=conn.get(); //가져온 html소스를 필드에 저장
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void crawling() {
		Elements con=html.select("div.deal_detailimg");
		String imageUrl=null;
		int i=0;
		while(true) {
			conImage=con.select("img");
			try {
				imageUrl=conImage.get(i).attr("src");
				System.out.println(imageUrl);
			}catch(IndexOutOfBoundsException e) {
				return;
			}
			
			System.out.println("크롤러 실행 완료");
			
			DetailListRowModel.list.add(new DetailListRowModel(new ImageView(new Image(imageUrl))));
			i++;
		}
	}
	
}
