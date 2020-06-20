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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.LoginDAO;

public class UI_005Controller_unLogin implements Initializable{
	// ���̺�&�÷�
    @FXML private TableView<TableRowModel> table=new TableView<>();
    @FXML private TableColumn<TableRowModel, String> name=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> price=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> rating=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> shippingCharge=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, ImageView> image=new TableColumn<TableRowModel, ImageView>();
    
    // �α��� ���� ��ü
    @FXML private Button signup_btn;
	@FXML private Button login_btn;
	@FXML private TextField login_id;
	@FXML private PasswordField login_pw;
    
    //�˻�� �Է��� �ؽ�Ʈ �ʵ�� �˻� ��ư�� �����ϱ� ���� ������ ��ü ����
    @FXML TextField searchWordField=new TextField();
    @FXML Button searchBtn=new Button();
    String searchWord;
    
    Thread wThread; //������ ũ�ѷ��� ������� �۵���Ű�� ���� ��ü
    Thread aThread; //���� ũ�ѷ��� ������� �۵���Ű�� ���� ��ü

	//--------------------- ȸ������ â ---------------------
	public void handleUI_001SignUpBtnAction(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SIGNUP_001.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();

			stage.setTitle("ȸ������");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//--------------------- �α��� â ---------------------
	public void handleUI_001LoginBtnAction(ActionEvent event) {
		String uId = login_id.getText(); // ���̵� �ʵ忡 �Էµ� �� ��������
		String uPw = login_pw.getText(); // �н����� �ʵ忡 �Էµ� �� ��������
		
		boolean result = LoginDAO.login(uId, uPw);
		
		if (result == true) {
			try {
				// �α��� ���� �� ����ȭ�� �ε�
				Parent members = FXMLLoader.load(getClass().getResource("UI_005_Login.fxml"));
				Scene scene = new Scene(members);
				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("�α��� ����");
			alert.setContentText("���̵� Ȥ�� �н����带 Ȯ�����ּ���.");
			alert.showAndWait();
		}
		
	} 
   
    //���ο� ��ǰ�� �˻��ϰ��� '�˻�'��ư Ŭ���� ���� �ڵ鷯
    public void handleSearchBtnAction(ActionEvent e) {
    	init(); //������ ����Ǿ� �ִ� ����Ʈ�� ����� �ʱ�ȭ ��Ŵ
    	searchWord = searchWordField.getText();
    	
    	wThread=new FreeWempCrawler("https://search.wemakeprice.com/search?search_cate=top&keyword="+searchWord+
        		"&isRec=1&_service=5&_type=3");
    	aThread=new FreeAuctionCrawler("http://browse.auction.co.kr/search?keyword="+searchWord+
        		"&itemno=&nickname=&frm=hometab&dom=auction&isSuggestion=No&retry=&"
        		+ "Fwk=%ec%8b%9d%eb%b9%b5&acode=SRP_SU_0100&arraycategory=&encKeyword=%ec%8b%9d%eb%b9%b5&k=32&p=1");
        
        wThread.start(); 
        aThread.start();;
           
        name.setCellValueFactory(cellData -> cellData.getValue().getName());
        price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
        rating.setCellValueFactory(cellData -> cellData.getValue().getRating());
        shippingCharge.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
        image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));
        image.setSortable(false); //�̹��� Į���� ���ı���� ����

        table.setItems(TableRowModel.list);
    }
    
    //ó�� UI_002�� UI_001���� �˻�� �޾� ���� �Ǿ������� ����  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableRowModel>() {
            @Override
            public void changed(ObservableValue<? extends TableRowModel> observable, TableRowModel oldValue, TableRowModel newValue) {
                TableRowModel model = table.getSelectionModel().getSelectedItem();
                Parent UI_003;
				try {
					UI_003 = FXMLLoader.load(getClass().getResource("UI_003_unLogin.fxml"));
					Scene scene = new Scene(UI_003);
	                Stage primaryStage =(Stage)table.getScene().getWindow();
	                primaryStage.setScene(scene);
	                primaryStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
        });
        
        //���ο� �˻�� �Է��Ͽ� �ٸ� ��ǰ�� ã������ �Ҷ� ���, �˻�â�� �˻��� �Է� �� '�˻�'��ư Ŭ���� �ڵ鷯 ȣ��
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleSearchBtnAction(event);
			}
		});
        
        signup_btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_001SignUpBtnAction(event);
			}
		});
		
		login_btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_001LoginBtnAction(event);
			}
		});
    }
    
    //���̺� ǥ�õǴ� list�� �ʱ�ȭ ��Ű�� �޼ҵ�
    public void init() {
    	TableRowModel.list.clear();
        // ����Ʈ�� ���� �ڵ鸵 �� ���� �� �κ��� �ʿ�
        table.setItems(TableRowModel.list);
    }
}