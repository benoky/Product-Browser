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
 
public class UI_002Controller_unLogin implements Initializable {
    // 테이블&컬럼
    @FXML private TableView<TableRowModel> table=new TableView<>();
    @FXML private TableColumn<TableRowModel, String> name=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> price=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> rating=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> shippingCharge=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, ImageView> image=new TableColumn<TableRowModel, ImageView>();
    
    // 로그인 관련 객체
    @FXML private Button signup_btn;
	@FXML private Button login_btn;
	@FXML private TextField login_id;
	@FXML private PasswordField login_pw;
    
    //검색어를 입력할 텍스트 필드와 검색 버튼을 생성하기 위해 각각의 객체 생성
    @FXML TextField searchWordField=new TextField();
    @FXML Button searchBtn=new Button();
    @FXML private Button basketBtn=new Button();
    
    String searchWord;
    
    Thread aThread;
    Thread wThread;
    
	//--------------------- 회원가입 창 로드 ---------------------
	public void handleUI_001SignUpBtnAction(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SIGNUP_001.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();

			stage.setTitle("회원가입");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//--------------------- 로그인 창 ---------------------
	public void handleUI_001LoginBtnAction(ActionEvent event) {
		String uId = login_id.getText(); // 아이디 필드에 입력된 값 가져오기
		String uPw = login_pw.getText(); // 패스워드 필드에 입력된 값 가져오기
		
		boolean result = LoginDAO.login(uId, uPw);
		
		if (result == true) {
			try {
				// 로그인 성공 후 메인화면 로드
				Parent members = FXMLLoader.load(getClass().getResource("UI_002_Login.fxml"));
				Scene scene = new Scene(members);
				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("로그인 실패");
			alert.setContentText("아이디 혹은 패스워드를 확인해주세요.");
			alert.showAndWait();
		}
	}
	
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
    
    //새로운 제품을 검색하고자 '검색'버튼 클릭시 동작 핸들러
    public void handleSearchBtnAction(ActionEvent e) {
    	init(); //기존에 저장되어 있던 리스트의 목록을 초기화 시킴
    	
    	searchWord = searchWordField.getText();
    	
    	wThread=new WempCrawler("https://search.wemakeprice.com/search?search_cate=top&keyword="+searchWord+
        		"&isRec=1&_service=5&_type=3");
    	aThread=new AuctionCrawler("http://browse.auction.co.kr/search?keyword="+searchWord+
        		"&itemno=&nickname=&frm=hometab&dom=auction&isSuggestion=No&retry=&"
        		+ "Fwk=%ec%8b%9d%eb%b9%b5&acode=SRP_SU_0100&arraycategory=&encKeyword=%ec%8b%9d%eb%b9%b5&k=32&p=1");
        
        wThread.start();
        aThread.start();
        
        name.setCellValueFactory(cellData -> cellData.getValue().getName());
        price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
        rating.setCellValueFactory(cellData -> cellData.getValue().getRating());
        shippingCharge.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
        image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));
        
        table.setItems(TableRowModel.list);
    }
    
    //처음 UI_002가 UI_001에서 검색어를 받아 실행 되었을떄의 동작  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	init(); //기존에 저장되어 있던 리스트의 목록을 초기화 시킴
    	
        //처음 UI_002를 실행 했을 경우 UI_001DumyController의 멤버인 searchWord를 사용함
    	wThread=new WempCrawler("https://search.wemakeprice.com/search?search_cate=top&keyword="+UI_001Controller_unLogin.searchWord+
        		"&isRec=1&_service=5&_type=3");
    	aThread=new AuctionCrawler("http://browse.auction.co.kr/search?keyword="+UI_001Controller_unLogin.searchWord+
        		"&itemno=&nickname=&frm=hometab&dom=auction&isSuggestion=No&retry=&"
        		+ "Fwk=%ec%8b%9d%eb%b9%b5&acode=SRP_SU_0100&arraycategory=&encKeyword=%ec%8b%9d%eb%b9%b5&k=32&p=1");
        
    	wThread.start();   
        aThread.start();

        name.setCellValueFactory(cellData -> cellData.getValue().getName());
        price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
        rating.setCellValueFactory(cellData -> cellData.getValue().getRating());
        shippingCharge.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
        image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));
        image.setSortable(false); //이미지 칼럼의 정렬기능을 막음

        table.setItems(TableRowModel.list);
        
        //상품 목록이 나오는 테이블중 하나를 선택하면 동작되는 이벤트
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableRowModel>() {
            @Override
            public void changed(ObservableValue<? extends TableRowModel> observable, TableRowModel oldValue, TableRowModel newValue) {
                TableRowModel model = table.getSelectionModel().getSelectedItem();
                try {
                	//선택한 상품의 정보를 UI_003에게 넘김
                	UI_003Contorller_unLogin.site=model.getSite();
                	UI_003Contorller_unLogin.name=model.getName();
                	UI_003Contorller_unLogin.price=model.getPrice();
                	UI_003Contorller_unLogin.rating=model.getRating();
                	UI_003Contorller_unLogin.shippingCharge=model.getShippingCharge();
                	UI_003Contorller_unLogin.detailUrl=model.getDetailUrl();
                	UI_003Contorller_unLogin.imageView=model.getImageView();
                	
                	//UI_003호출
                	Parent UI_003 =FXMLLoader.load(getClass().getResource("UI_003_unLogin.fxml"));
                    Scene scene = new Scene(UI_003);
                    Stage primaryStage =(Stage)table.getScene().getWindow();
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
        //새로운 검색어를 입력하여 다른 제품을 찾으려고 할때 사용, 검색창에 검색어 입력 후 '검색'버튼 클릭시 핸들러 호출
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
		
		basketBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleUI_004BtnAction(event);
			}
		});
    }
    
    //테이블에 표시되는 list를 초기화 시키는 메소드
    public void init() {
    	TableRowModel.list.clear();
        // 리스트를 직접 핸들링 한 경우는 이 부분이 필요
        table.setItems(TableRowModel.list);
    }
}