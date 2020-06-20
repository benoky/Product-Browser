package crawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dataModel.TableRowModel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//위메프에서 상품 목록을 가져오는 클래스 (제품 검색후 하단의 '일반상품'목록을 가져오는 것임)
public class WempCrawler extends Thread{
	private Document html; //웹 사이트에서 가져온 html소스를 저장하
	private Connection conn; //데이터를 크롤링 해올 웹사이트를 연결해줄 객체
	
	Elements conName; //파싱한 상품명을 저장
	Elements conPrice; //파싱한 상품의 가격을 저장
	Elements conRating; //파싱한 상품의 평점을 저장
	Elements conUrl; //상품의 상세정보가 나오는 url을 저장
	Elements conCharge; //파싱한 배송비용을 저장
	Elements conImage; //상품의 이미지 주소를 저장
	
	//생성자로 url을 받아들임
	public WempCrawler(String url) {
		conn=Jsoup.connect(url); //매개변수로 가져온 url을 이용해 웹 사이트와 연결
		try {
			html=conn.get(); //가져온 html소스를 필드에 저장
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		product();
	}
	
	public  synchronized void product() {
		//이미지 주소를 가져오기위한 파싱 소스
		Elements conI=html.select("div.search_box_listwrap.tab_cont");
		//상품과 관련된 정보를 가져오기 위한 파싱 소스
		Elements conT=html.select("div.search_box_listwrap.tab_cont").select("div.item_cont");
		
		int i=0;
		while(true) {
			//상품의 이름을 가져와 파싱하는 영역
			//또한 해당 웹 사이트에서 더이상 가져올 데이터가 없을 경우 예외를 발생되며 예외 발생시 메소드 종료
			try {
				conName=conT.get(i).select("p.text");
			}catch(IndexOutOfBoundsException e) {
				return;
			}
			//가격을 가져와 파싱하는 영역
			conPrice=conT.get(i).select("em.num");
			//////
			//평점을 가져와 파싱하는 영역
			conRating=conT.get(i).select("span.grade");
					
			//상품의 상세정보 페이지의 Url을 가져와 파싱하는 영역
			conUrl=conI.select("a");
			String detailUrlStr=conUrl.get(i).attr("href");
			
			//배송비를 가져와 파싱하는 영역
			conCharge=conT.get(i).select("span.txt_noti");
			String tmpCharge=conCharge.text();
			if(!tmpCharge.equals("무료배송")) {
				tmpCharge="2500";
			}
			
			//상품목록에서 상품의 썸네일 이미지를 가져오는 영역
			conImage=conI.select("img");
			String tmpImage=conImage.get(i).attr("data-lazy-src");
			
			/*System.out.println("상품명 : "+conName.text());
			System.out.println("가격 : "+conPrice.text());
			System.out.println("평점 : "+conRating.text());
			System.out.println("상세 주소 : "+detailUrlStr);
			System.out.println("배송비 : "+tmpCharge);
			System.out.println("==============================");*/
			
			//상품명, 가격, 평점, 상세 페이지 주소, 배송비, 이미지 주소
			TableRowModel.list.add(new TableRowModel(conName.text(),conPrice.text(),conRating.text(),detailUrlStr,tmpCharge,new ImageView(new Image(tmpImage,200, 200, false, false))));
			
			i++;
		}
	}
}
