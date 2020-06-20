package crawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dataModel.TableRowModel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//���������� ��ǰ ����� �������� Ŭ���� (��ǰ �˻��� �ϴ��� '�Ϲݻ�ǰ'����� �������� ����)
public class WempCrawler extends Thread{
	private Document html; //�� ����Ʈ���� ������ html�ҽ��� ������
	private Connection conn; //�����͸� ũ�Ѹ� �ؿ� ������Ʈ�� �������� ��ü
	
	Elements conName; //�Ľ��� ��ǰ���� ����
	Elements conPrice; //�Ľ��� ��ǰ�� ������ ����
	Elements conRating; //�Ľ��� ��ǰ�� ������ ����
	Elements conUrl; //��ǰ�� �������� ������ url�� ����
	Elements conCharge; //�Ľ��� ��ۺ���� ����
	Elements conImage; //��ǰ�� �̹��� �ּҸ� ����
	
	//�����ڷ� url�� �޾Ƶ���
	public WempCrawler(String url) {
		conn=Jsoup.connect(url); //�Ű������� ������ url�� �̿��� �� ����Ʈ�� ����
		try {
			html=conn.get(); //������ html�ҽ��� �ʵ忡 ����
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		product();
	}
	
	public  synchronized void product() {
		//�̹��� �ּҸ� ������������ �Ľ� �ҽ�
		Elements conI=html.select("div.search_box_listwrap.tab_cont");
		//��ǰ�� ���õ� ������ �������� ���� �Ľ� �ҽ�
		Elements conT=html.select("div.search_box_listwrap.tab_cont").select("div.item_cont");
		
		int i=0;
		while(true) {
			//��ǰ�� �̸��� ������ �Ľ��ϴ� ����
			//���� �ش� �� ����Ʈ���� ���̻� ������ �����Ͱ� ���� ��� ���ܸ� �߻��Ǹ� ���� �߻��� �޼ҵ� ����
			try {
				conName=conT.get(i).select("p.text");
			}catch(IndexOutOfBoundsException e) {
				return;
			}
			//������ ������ �Ľ��ϴ� ����
			conPrice=conT.get(i).select("em.num");
			//////
			//������ ������ �Ľ��ϴ� ����
			conRating=conT.get(i).select("span.grade");
					
			//��ǰ�� ������ �������� Url�� ������ �Ľ��ϴ� ����
			conUrl=conI.select("a");
			String detailUrlStr=conUrl.get(i).attr("href");
			
			//��ۺ� ������ �Ľ��ϴ� ����
			conCharge=conT.get(i).select("span.txt_noti");
			String tmpCharge=conCharge.text();
			if(!tmpCharge.equals("������")) {
				tmpCharge="2500";
			}
			
			//��ǰ��Ͽ��� ��ǰ�� ����� �̹����� �������� ����
			conImage=conI.select("img");
			String tmpImage=conImage.get(i).attr("data-lazy-src");
			
			/*System.out.println("��ǰ�� : "+conName.text());
			System.out.println("���� : "+conPrice.text());
			System.out.println("���� : "+conRating.text());
			System.out.println("�� �ּ� : "+detailUrlStr);
			System.out.println("��ۺ� : "+tmpCharge);
			System.out.println("==============================");*/
			
			//��ǰ��, ����, ����, �� ������ �ּ�, ��ۺ�, �̹��� �ּ�
			TableRowModel.list.add(new TableRowModel(conName.text(),conPrice.text(),conRating.text(),detailUrlStr,tmpCharge,new ImageView(new Image(tmpImage,200, 200, false, false))));
			
			i++;
		}
	}
}
