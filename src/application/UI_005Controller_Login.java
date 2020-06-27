package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dataModel.TableRowModel;
import freeCrawler.FreeAuctionCrawler;
import freeCrawler.FreeWempCrawler;
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

public class UI_005Controller_Login implements Initializable {
	// ���̺�&�÷�
	@FXML
	private TableView<TableRowModel> table = new TableView<>();
	@FXML
	private TableColumn<TableRowModel, String> name = new TableColumn<>();
	@FXML
	private TableColumn<TableRowModel, String> price = new TableColumn<>();
	@FXML
	private TableColumn<TableRowModel, String> rating = new TableColumn<>();
	@FXML
	private TableColumn<TableRowModel, String> shippingCharge = new TableColumn<>();
	@FXML
	private TableColumn<TableRowModel, ImageView> image = new TableColumn<TableRowModel, ImageView>();

	// �˻�� �Է��� �ؽ�Ʈ �ʵ�� �˻� ��ư�� �����ϱ� ���� ������ ��ü ����
	@FXML
	TextField searchWordField = new TextField();
	@FXML Button searchBtn = new Button();
	@FXML private Button basketBtn=new Button();
	String searchWord;

	@FXML
	private Button logout_btn = new Button();

	Thread wThread; // ������ ũ�ѷ��� ������� �۵���Ű�� ���� ��ü
	Thread aThread; // ���� ũ�ѷ��� ������� �۵���Ű�� ���� ��ü

	// �α׾ƿ� ��ư Ŭ�� �� �ʱ�ȭ������
	public void handleUI_001unLoginBtnAction(ActionEvent event) {
		try {
			Parent members = FXMLLoader.load(getClass().getResource("UI_005_unLogin.fxml"));
			Scene scene = new Scene(members);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ���ο� ��ǰ�� �˻��ϰ��� '�˻�'��ư Ŭ���� ���� �ڵ鷯
	public void handleSearchBtnAction(ActionEvent e) {
		init(); // ������ ����Ǿ� �ִ� ����Ʈ�� ����� �ʱ�ȭ ��Ŵ
		searchWord = searchWordField.getText();

		wThread = new FreeWempCrawler("https://search.wemakeprice.com/search?search_cate=top&keyword=" + searchWord
				+ "&isRec=1&_service=5&_type=3");
		aThread = new FreeAuctionCrawler("http://browse.auction.co.kr/search?keyword=" + searchWord
				+ "&itemno=&nickname=&frm=hometab&dom=auction&isSuggestion=No&retry=&"
				+ "Fwk=%ec%8b%9d%eb%b9%b5&acode=SRP_SU_0100&arraycategory=&encKeyword=%ec%8b%9d%eb%b9%b5&k=32&p=1");

		wThread.start();
		aThread.start();

		name.setCellValueFactory(cellData -> cellData.getValue().getName());
		price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
		rating.setCellValueFactory(cellData -> cellData.getValue().getRating());
		shippingCharge.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
		image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));
		image.setSortable(false); // �̹��� Į���� ���ı���� ����

		table.setItems(TableRowModel.list);
	}
	
	//'�� ���' ��ư ���� �� UI_004�� �ҷ����� ���� ���� �ڵ鷯
	public void handleUI_004BtnAction(ActionEvent event) {
		try {
			Parent UI_004 = FXMLLoader.load(getClass().getResource("UI_004_Login.fxml"));
			Scene scene=new Scene(UI_004);
			Stage primaryStage=(Stage)basketBtn.getScene().getWindow();
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//ó�� UI_002�� UI_001���� �˻�� �޾� ���� �Ǿ������� ����
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//��ǰ ����� ������ ���̺��� �ϳ��� �����ϸ� ���۵Ǵ� �̺�Ʈ
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableRowModel>() {
            @Override
            public void changed(ObservableValue<? extends TableRowModel> observable, TableRowModel oldValue, TableRowModel newValue) {
                TableRowModel model = table.getSelectionModel().getSelectedItem();
                try {
                	//������ ��ǰ�� ������ UI_003���� �ѱ�
                	UI_003Contorller_Login.site=model.getSite();
                	UI_003Contorller_Login.name=model.getName();
                	UI_003Contorller_Login.price=model.getPrice();
                	UI_003Contorller_Login.rating=model.getRating();
                	UI_003Contorller_Login.shippingCharge=model.getShippingCharge();
                	UI_003Contorller_Login.detailUrl=model.getDetailUrl();
                	UI_003Contorller_Login.imageView=model.getImageView();
                	
                	//UI_003ȣ��
                	Parent UI_003 =FXMLLoader.load(getClass().getResource("UI_003_Login.fxml"));
                    Scene scene = new Scene(UI_003);
                    Stage primaryStage =(Stage)table.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

		// ���ο� �˻�� �Է��Ͽ� �ٸ� ��ǰ�� ã������ �Ҷ� ���, �˻�â�� �˻��� �Է� �� '�˻�'��ư Ŭ���� �ڵ鷯 ȣ��
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleSearchBtnAction(event);
			}
		});
		
		basketBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_004BtnAction(event);
			}
		});

	}

	// ���̺� ǥ�õǴ� list�� �ʱ�ȭ ��Ű�� �޼ҵ�
	public void init() {
		TableRowModel.list.clear();
		// ����Ʈ�� ���� �ڵ鸵 �� ���� �� �κ��� �ʿ�
		table.setItems(TableRowModel.list);
	}
}