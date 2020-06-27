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
	private Document html; //�� ����Ʈ���� ������ html�ҽ��� ������
	private Connection conn; //�����͸� ũ�Ѹ� �ؿ� ������Ʈ�� �������� ��ü
	
	public WempDetailCrawler(String url){
		conn=Jsoup.connect(url); //�Ű������� ������ url�� �̿��� �� ����Ʈ�� ����
		try {
			html=conn.get(); //������ html�ҽ��� �ʵ忡 ����
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
			//���� ���� �±׿� �ٸ� ������� html�ۼ��� �������� iamgeUrl�� �ƹ��͵� ������� �ʰ� �̸� ���Ͽ� ������� �ʾ����� �ٸ� ������� ũ�Ѹ���
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
		//Elements conR=html.select("div.review_list"); //���信 ���� ������ ��� html�ҽ��� ������
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
		System.out.println("���� ũ�Ѹ� ����");
		
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
			
			System.out.println("�ƾƵ� : "+conId);
			System.out.println(conRating);
			System.out.println(conTime);
			System.out.println(conProduct);
			System.out.println(conPost);
			
			System.out.println("���� ũ�Ѹ� �Ϸ�");
			i++;
		}
		
		
	}*/
}