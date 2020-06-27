package crawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dataModel.TableRowModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//���ǿ��� ��� ����� �������� Ŭ����
public class AuctionCrawler extends Thread{
	private Document html; //�� ����Ʈ���� ������ html�ҽ��� ������
	private Connection conn; //�����͸� ũ�Ѹ� �ؿ� ������Ʈ�� �������� ��ü
	
	Elements conName; //�Ľ��� ��ǰ���� ����
	Elements conPrice; //�Ľ��� ��ǰ�� ������ ����
	Elements conRating; //�Ľ��� ��ǰ�� ������ ����
	Elements conUrl; //��ǰ�� �������� ������ url�� ����
	Elements conCharge; //�Ľ��� ��ۺ���� ����
	Elements conImage; //��ǰ�� �̹��� �ּҸ� ����
	
	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";

	
	//�����ڷ� url�� �޾Ƶ���
	public AuctionCrawler(String url) {
		//conn=Jsoup.connect(url); //�Ű������� ������ url�� �̿��� �� ����Ʈ�� ����
		
		//Connection conn = Jsoup.connect(url) .header("Content-Type", "application/json;charset=UTF-8") .userAgent(USER_AGENT) .method(Connection.Method.GET) .ignoreContentType(true);

		Connection conn = Jsoup.connect(url).userAgent(USER_AGENT);
		try {
			html=conn.get(); //������ html�ҽ��� �ʵ忡 ����
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		product();
	}
	
	public synchronized void product() {
		Elements con=html.select("div.section--itemcard_info"); //div�±��� section--itemcardŬ������ ���� html�ҽ��� �Ľ��Ѵ�.

		Elements con2=html.getElementsByTag("img"); //html�ҽ��� img�±׸� ���� ��ü���� �Ľ��Ͽ� �����Ѵ�.
		int i=0;
		while(true){
			//��ǰ�� �̸��� ������ �Ľ��ϴ� ����
			//���� �ش� �� ����Ʈ���� ���̻� ������ �����Ͱ� ���� ��� ���ܸ� �߻��Ǹ� ���� �߻��� �޼ҵ� ����
			try {
				conName=con.get(i).select("span.text--title");
			}catch(IndexOutOfBoundsException e) {
				return;
			}
			//������ ������ �Ľ��ϴ� ����
			conPrice=con.get(i).select("strong.text--price_seller");
			
			//������ ������ �Ľ��ϴ� ����
			conRating=con.get(i).select("li.item.awards").select("span.for-a11y");
			String tmpRating=conRating.text();
			tmpRating=tmpRating.replace("�ı����� ","");
			tmpRating=tmpRating.replace("��","");
			
			//��ǰ�� ������ �������� Url�� ������ �Ľ��ϴ� ����
			conUrl=con.get(i).select("span.text--itemcard_title.ellipsis").select("a.link--itemcard");
			String detailUrlStr=conUrl.attr("href");
			
			//��ۺ� ������ �Ľ��ϴ� ����
			conCharge=con.get(i).select("span.text--addinfo");
			String tmpCharge=conCharge.text();
			tmpCharge=tmpCharge.replace("��ۺ� ","");
			
			//��ǰ��Ͽ��� ��ǰ�� ����� �̹����� �������� ����
			conImage=con2.select("img.image--itemcard");
			String img;
			//System.out.println(con2.outerHtml());
			//System.out.println(conImage.outerHtml());
			try {
				img=conImage.get(i).attr("src");
			}catch(IndexOutOfBoundsException e) {
				img="file:\\C:\\Users\\czmn\\OneDrive\\���� ȭ��\\cgv.png";
			}
			
			//��ǰ��, ����, ����, �� ������ �ּ�, ��ۺ�, �̹��� �ּ�
			TableRowModel.list.add(new TableRowModel("����",conName.text(),conPrice.text(),tmpRating,detailUrlStr,tmpCharge,new ImageView(new Image(img,200, 200, false, false))));

			i++;
		}
	}	
}