package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dataModel.DetailListRowModel;
import detailImageCrawler.AuctionDetailCrawler;
import detailImageCrawler.WempDetailCrawler;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UI_003Contorller_unLogin implements Initializable{
	@FXML private TableView<DetailListRowModel> detailImageView=new TableView<>();
	@FXML private TableColumn<DetailListRowModel, ImageView> detailImage=new TableColumn<DetailListRowModel, ImageView>();
	
	@FXML private Button openUrlBtn=new Button();
	@FXML private Button basketBtn=new Button();
	
	//UI_002또는 UI_005에서 선택된 상품의 정보들을 받아옴
	static StringProperty site;
	static StringProperty name;
	static StringProperty price;
	static StringProperty rating;
	static StringProperty shippingCharge;
	static StringProperty detailUrl;
	static ImageView imageView;
	
	//웹 사이트로 이동 버튼을 눌러 해당 상품을 판매하는 인터넷 페이지로 이동 하는동작, 실행 웹 브라우저는 크롬
	public void handleOpenUrlBtnAction(ActionEvent event) {
		Runtime runtime=Runtime.getRuntime();
		try {
			if(site.getValue().equals("옥션")) {
				runtime.exec("C:/Program Files (x86)/Google\\Chrome/Application/chrome.exe "+detailUrl.getValue()+"/");
			}else {
				runtime.exec("C:/Program Files (x86)/Google\\Chrome/Application/chrome.exe https:"+detailUrl.getValue()+"/");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//'찜 목록' 버튼 선택 시 UI_004를 불러오기 위한 동작 핸들러
	public void handleUI_004BtnAction(ActionEvent event) {
		try {
			System.out.println("UI_004실행0");
			Parent UI_004 = FXMLLoader.load(getClass().getResource("UI_004_unLogin.fxml"));
			System.out.println("UI_004실행1");
			Scene scene=new Scene(UI_004);
			Stage primaryStage=(Stage)basketBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//선택한 상품을 판매하는 사이트가 옥션인지 위메프인지 검사하고 그에 맞는 크롤러 동작시킴
		if(site.getValue().equals("옥션")) {
			System.out.println("가져온 상세 주소(옥션) : "+detailUrl.getValue());
			System.out.println("상세 크롤링 시작");
			AuctionDetailCrawler auctionDetailCrawler=new AuctionDetailCrawler(detailUrl.getValue());
			auctionDetailCrawler.imageCrawling();
			System.out.println("상세 크롤링 끝");
		}else {
			WempDetailCrawler wempDetailCrawle=new WempDetailCrawler("https:"+detailUrl.getValue());
			wempDetailCrawle.imageCrawling();
		}
		
		detailImage.setCellValueFactory(new PropertyValueFactory<DetailListRowModel, ImageView>("imageView"));
		detailImage.setSortable(false); //이미지 칼럼의 정렬기능을 막음
		detailImage.setMinWidth(950);
		
		detailImageView.setItems(DetailListRowModel.list);
		
		//해당 상품을 파는 웹 사이트로 이동
		openUrlBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleOpenUrlBtnAction(event);
			}
		});
		
		basketBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_004BtnAction(event);
			}
		});
	}
}