package crawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dataModel.TableRowModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//옥션에서 상목 목록을 가져오는 클래스
public class AuctionCrawler extends Thread{
	private Document html; //웹 사이트에서 가져온 html소스를 저장하
	private Connection conn; //데이터를 크롤링 해올 웹사이트를 연결해줄 객체
	
	Elements conName; //파싱한 상품명을 저장
	Elements conPrice; //파싱한 상품의 가격을 저장
	Elements conRating; //파싱한 상품의 평점을 저장
	Elements conUrl; //상품의 상세정보가 나오는 url을 저장
	Elements conCharge; //파싱한 배송비용을 저장
	Elements conImage; //상품의 이미지 주소를 저장
	
	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";

	
	//생성자로 url을 받아들임
	public AuctionCrawler(String url) {
		//conn=Jsoup.connect(url); //매개변수로 가져온 url을 이용해 웹 사이트와 연결
		
		//Connection conn = Jsoup.connect(url) .header("Content-Type", "application/json;charset=UTF-8") .userAgent(USER_AGENT) .method(Connection.Method.GET) .ignoreContentType(true);

		Connection conn = Jsoup.connect(url).userAgent(USER_AGENT);
		try {
			html=conn.get(); //가져온 html소스를 필드에 저장
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		product();
	}
	
	public synchronized void product() {
		Elements con=html.select("div.section--itemcard_info"); //div태그중 section--itemcard클래스를 가진 html소스를 파싱한다.

		Elements con2=html.getElementsByTag("img"); //html소스중 img태그를 가진 개체들을 파싱하여 저장한다.
		int i=0;
		while(true){
			//상품의 이름을 가져와 파싱하는 영역
			//또한 해당 웹 사이트에서 더이상 가져올 데이터가 없을 경우 예외를 발생되며 예외 발생시 메소드 종료
			try {
				conName=con.get(i).select("span.text--title");
			}catch(IndexOutOfBoundsException e) {
				return;
			}
			//가격을 가져와 파싱하는 영역
			conPrice=con.get(i).select("strong.text--price_seller");
			
			//평점을 가져와 파싱하는 영역
			conRating=con.get(i).select("li.item.awards").select("span.for-a11y");
			String tmpRating=conRating.text();
			tmpRating=tmpRating.replace("후기평점 ","");
			tmpRating=tmpRating.replace("점","");
			
			//상품의 상세정보 페이지의 Url을 가져와 파싱하는 영역
			conUrl=con.get(i).select("span.text--itemcard_title.ellipsis").select("a.link--itemcard");
			String detailUrlStr=conUrl.attr("href");
			
			//배송비를 가져와 파싱하는 영역
			conCharge=con.get(i).select("span.text--addinfo");
			String tmpCharge=conCharge.text();
			tmpCharge=tmpCharge.replace("배송비 ","");
			
			//상품목록에서 상품의 썸네일 이미지를 가져오는 영역
			conImage=con2.select("img.image--itemcard");
			String img;
			//System.out.println(con2.outerHtml());
			//System.out.println(conImage.outerHtml());
			try {
				img=conImage.get(i).attr("src");
			}catch(IndexOutOfBoundsException e) {
				img="file:\\C:\\Users\\czmn\\OneDrive\\바탕 화면\\cgv.png";
			}
			
			//상품명, 가격, 평점, 상세 페이지 주소, 배송비, 이미지 주소
			TableRowModel.list.add(new TableRowModel("옥션",conName.text(),conPrice.text(),tmpRating,detailUrlStr,tmpCharge,new ImageView(new Image(img,200, 200, false, false))));

			i++;
		}
	}	
}