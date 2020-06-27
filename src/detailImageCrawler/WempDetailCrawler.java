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
	
	public WempDetailCrawler(String url){
		conn=Jsoup.connect(url); //매개변수로 가져온 url을 이용해 웹 사이트와 연결
		try {
			html=conn.get(); //가져온 html소스를 필드에 저장
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void imageCrawling() {
		Elements con=html.select("div.deal_detailimg");
		Elements conImage=null;
		String imageUrl=null;
		
		int i=0;
		while(true) {
			conImage=con.select("img");
			
			try {
				imageUrl=conImage.get(i).attr("data-lazy-src");
			}catch(IndexOutOfBoundsException e) {
				return;
			}
			//만약 위의 태그와 다른 방식으로 html작성된 페이지면 iamgeUrl에 아무것도 저장되지 않고 이를 비교하여 저장되지 않았으면 다른 방식으로 크롤링함
			if(imageUrl.equals("")) {
				try {
					imageUrl=conImage.get(i).attr("src");
				}catch(IndexOutOfBoundsException e) {
					return;
				}
			}

			DetailListRowModel.list.add(new DetailListRowModel(new ImageView(new Image(imageUrl))));
			i++;
		}
	}
	
	/*public void reviewCrawling() {
		//Elements conR=html.select("div.review_list"); //리뷰에 대한 정보가 담긴 html소스를 가져옴
		Elements conR=html.select("div.tab_conts.tab_review");
		Elements tmpId=conR.select("button.name");
		Elements tmpRating=conR.select("String.grade");
		Elements tmpTime=conR.select("span.time");
		Elements tmpProduct=conR.select("span.opt");
		Elements tmpPost=conR.select("p.post");
		
		String conId=null;
		String conRating=null;
		String conTime=null;
		String conProduct=null;
		String conPost=null;
		System.out.println(conR.outerHtml());
		System.out.println("리뷰 크롤링 시작");
		
		int i=0;
		while(true) {
			
			
			try {
				conId=tmpId.get(i).text();
			}catch(IndexOutOfBoundsException e) {
				return;
			}
			
			conRating=tmpRating.get(i).text();
			conTime=tmpTime.get(i).text();
			conProduct=tmpProduct.get(i).text();
			conPost=tmpPost.get(i).text();
			
			System.out.println("아아디 : "+conId);
			System.out.println(conRating);
			System.out.println(conTime);
			System.out.println(conProduct);
			System.out.println(conPost);
			
			System.out.println("리뷰 크롤링 완료");
			i++;
		}
		
		
	}*/
}