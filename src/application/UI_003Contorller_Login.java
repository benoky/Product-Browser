package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dataModel.DetailListRowModel;
import dataModel.TableRowModel;
import detailImageCrawler.AuctionDetailCrawler;
import detailImageCrawler.WempDetailCrawler;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class UI_003Contorller_Login implements Initializable{
	@FXML private TableView<DetailListRowModel> detailImageView=new TableView<>();
	@FXML private TableColumn<DetailListRowModel, ImageView> detailImage=new TableColumn<DetailListRowModel, ImageView>();
	
	@FXML private Button openUrlBtn=new Button();
	@FXML private Button basketBtn=new Button();
	
	//UI_002�Ǵ� UI_005���� ���õ� ��ǰ�� �������� �޾ƿ�
	static StringProperty site;
	static StringProperty name;
	static StringProperty price;
	static StringProperty rating;
	static StringProperty shippingCharge;
	static StringProperty detailUrl;
	static ImageView imageView;
	
	//�� ����Ʈ�� �̵� ��ư�� ���� �ش� ��ǰ�� �Ǹ��ϴ� ���ͳ� �������� �̵� �ϴµ���, ���� �� �������� ũ��
	 public void handleOpenUrlBtnAction(ActionEvent event) {
		Runtime runtime=Runtime.getRuntime();
		try {
			if(site.getValue().equals("����")) {
				runtime.exec("C:/Program Files (x86)/Google\\Chrome/Application/chrome.exe "+detailUrl.getValue()+"/");
			}else {
				runtime.exec("C:/Program Files (x86)/Google\\Chrome/Application/chrome.exe https:"+detailUrl.getValue()+"/");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void handleUI_004BtnAction(ActionEvent event) {
		try {
			System.out.println("UI_004����0");
			Parent UI_004 = FXMLLoader.load(getClass().getResource("UI_004_Login.fxml"));
			System.out.println("UI_004����1");
			Scene scene=new Scene(UI_004);
			Stage primaryStage=(Stage)basketBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//������ ��ǰ�� �Ǹ��ϴ� ����Ʈ�� �������� ���������� �˻��ϰ� �׿� �´� ũ�ѷ� ���۽�Ŵ
		if(site.getValue().equals("����")) {
			AuctionDetailCrawler auctionDetailCrawler=new AuctionDetailCrawler(detailUrl.getValue());
			auctionDetailCrawler.imageCrawling();
		}else {
			WempDetailCrawler wempDetailCrawle=new WempDetailCrawler("https:"+detailUrl.getValue());
			wempDetailCrawle.imageCrawling();
		}
		
		detailImage.setCellValueFactory(new PropertyValueFactory<DetailListRowModel, ImageView>("imageView"));
		detailImage.setSortable(false); //�̹��� Į���� ���ı���� ����
		detailImage.setMinWidth(950);
		
		detailImageView.setItems(DetailListRowModel.list);
		
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