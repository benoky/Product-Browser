package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import crawler.AuctionCrawler;
import crawler.WempCrawler;
import dataModel.TableRowModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UI_002Controller_Login implements Initializable {
	// 테이블&컬럼
	@FXML private TableView<TableRowModel> table = new TableView<>();
	@FXML private TableColumn<TableRowModel, String> name = new TableColumn<>();
	@FXML private TableColumn<TableRowModel, String> price = new TableColumn<>();
	@FXML private TableColumn<TableRowModel, String> rating = new TableColumn<>();
	@FXML private TableColumn<TableRowModel, String> shippingCharge = new TableColumn<>();
	@FXML private TableColumn<TableRowModel, ImageView> image = new TableColumn<TableRowModel, ImageView>();

	// 검색어를 입력할 텍스트 필드와 검색 버튼을 생성하기 위해 각각의 객체 생성
	@FXML TextField searchWordField = new TextField();
	@FXML Button searchBtn = new Button();
	String searchWord;
	
	// 로그아웃 버튼 캑체 생성
	@FXML private Button logout_btn = new Button();

	Thread aThread;
	Thread wThread;

	// 로그아웃 버튼 클릭 시 초기화면으로
	public void handleUI_001unLoginBtnAction(ActionEvent event) {
		try {
			Parent members = FXMLLoader.load(getClass().getResource("UI_002_unLogin.fxml"));
			Scene scene = new Scene(members);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 테이블의 상품목록(행)을 클릭했을 때 UI_003을 가져오는 동작 핸들러
	public void handleUI_003SelectAction(ActionEvent event) {
		try {
			Parent UI_003 = FXMLLoader.load(getClass().getResource("UI_003_unLogin.fxml"));
			Scene scene = new Scene(UI_003);
			Stage primaryStage = (Stage) table.getScene().getWindow();

			primaryStage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 새로운 제품을 검색하고자 '검색'버튼 클릭시 동작 핸들러
	public void handleSearchBtnAction(ActionEvent e) {
		init(); // 기존에 저장되어 있던 리스트의 목록을 초기화 시킴

		searchWord = searchWordField.getText();

		wThread = new WempCrawler("https://search.wemakeprice.com/search?search_cate=top&keyword=" + searchWord
				+ "&isRec=1&_service=5&_type=3");
		aThread = new AuctionCrawler("http://browse.auction.co.kr/search?keyword=" + searchWord
				+ "&itemno=&nickname=&frm=hometab&dom=auction&isSuggestion=No&retry=&"
				+ "Fwk=%ec%8b%9d%eb%b9%b5&acode=SRP_SU_0100&arraycategory=&encKeyword=%ec%8b%9d%eb%b9%b5&k=32&p=1");

		wThread.start();
		aThread.start();

		name.setCellValueFactory(cellData -> cellData.getValue().getName());
		price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
		rating.setCellValueFactory(cellData -> cellData.getValue().getRating());
		shippingCharge.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
		image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));

		// image.setMaxWidth(200);
		table.setItems(TableRowModel.list);
	}

	// 처음 UI_002가 UI_001에서 검색어를 받아 실행 되었을떄의 동작
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init(); // 기존에 저장되어 있던 리스트의 목록을 초기화 시킴

		// 처음 UI_002를 실행 했을 경우 UI_001DumyController의 멤버인 searchWord를 사용함
		wThread = new WempCrawler("https://search.wemakeprice.com/search?search_cate=top&keyword="
				+ UI_001Controller_Login.searchWord + "&isRec=1&_service=5&_type=3");
		aThread = new AuctionCrawler("http://browse.auction.co.kr/search?keyword=" + UI_001Controller_Login.searchWord
				+ "&itemno=&nickname=&frm=hometab&dom=auction&isSuggestion=No&retry=&"
				+ "Fwk=%ec%8b%9d%eb%b9%b5&acode=SRP_SU_0100&arraycategory=&encKeyword=%ec%8b%9d%eb%b9%b5&k=32&p=1");

		wThread.start();
		aThread.start();

		name.setCellValueFactory(cellData -> cellData.getValue().getName());
		price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
		rating.setCellValueFactory(cellData -> cellData.getValue().getRating());
		shippingCharge.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
		image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));
		image.setSortable(false); // 이미지 칼럼의 정렬기능을 막음

		table.setItems(TableRowModel.list);

		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableRowModel>() {
			@Override
			public void changed(ObservableValue<? extends TableRowModel> observable, TableRowModel oldValue,
					TableRowModel newValue) {
				TableRowModel model = table.getSelectionModel().getSelectedItem();
				// System.out.println("name : " + model.getName());
				// System.out.println("price : " + model.getPrice());
				try {
					Parent UI_003 = FXMLLoader.load(getClass().getResource("UI_003Dumy.fxml"));
					Scene scene = new Scene(UI_003);
					Stage primaryStage = (Stage) table.getScene().getWindow();
					primaryStage.setScene(scene);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		// 새로운 검색어를 입력하여 다른 제품을 찾으려고 할때 사용, 검색창에 검색어 입력 후 '검색'버튼 클릭시 핸들러 호출
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleSearchBtnAction(event);
			}
		});
		
		// 로그아웃 버튼 클릭 시 핸들러 호출
		logout_btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_001unLoginBtnAction(event);
			}
		});
	}

	// 테이블에 표시되는 list를 초기화 시키는 메소드
	public void init() {
		TableRowModel.list.clear();
		// 리스트를 직접 핸들링 한 경우는 이 부분이 필요
		table.setItems(TableRowModel.list);
	}
}