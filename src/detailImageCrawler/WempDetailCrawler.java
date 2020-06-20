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
	
	Elements conImage;
	
	public WempDetailCrawler(String url){
		conn=Jsoup.connect(url); //�Ű������� ������ url�� �̿��� �� ����Ʈ�� ����
		try {
			html=conn.get(); //������ html�ҽ��� �ʵ忡 ����
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
			
			System.out.println("ũ�ѷ� ���� �Ϸ�");
			
			DetailListRowModel.list.add(new DetailListRowModel(new ImageView(new Image(imageUrl))));
			i++;
		}
	}
	
}
