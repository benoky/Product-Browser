package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class UI_002Controller_unLogin implements Initializable {
    // 테이블&컬럼
    @FXML private TableView<TableRowModel> table=new TableView<>();
    @FXML private TableColumn<TableRowModel, String> name=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> price=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> rating=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> shippingCharge=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> site=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, String> detailUrl=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, ImageView> image=new TableColumn<TableRowModel, ImageView>();
    @FXML private TableColumn<TableRowModel, String> imageUrl=new TableColumn<>();
    @FXML private TableColumn<TableRowModel, Button> basketAdd=new TableColumn<TableRowModel, Button>();
    
    //검색어를 입력할 텍스트 필드와 검색 버튼을 생성하기 위해 각각의 객체 생성
    @FXML TextField searchWordField=new TextField();
    
    @FXML Button searchBtn=new Button();
    
    @FXML private Button mainBtn=new Button();
	@FXML private Button nomalSearchBtn=new Button();
	@FXML private Button basketBtn=new Button();
	@FXML private Button freeDeliveryBtn=new Button();
    
    String searchWord;
    
    Thread aThread;
    Thread wThread;
    
    List<String> list=new ArrayList<String>();
    
    String[] tmpArr;
    
    String tmp;
    
    int lineCount=0;
    //찜 목록이 저장되어 있는 파일에서 내용을 읽어오는 메소드
  	private void basketListRead() {
  		lineCount=0;
  		list.clear();
  		File file = new File("basketList.txt");
  		tmp=null;
  		try{
  			//스캐너로 파일 읽기 (파일의 내용을 한줄씩 개행문자를 기준으로 읽어옴)
  			//Scanner scan = new Scanner(new FileInputStream(file), "euc-kr");
  			BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(file),"euc-kr"));
  			int i=0;
			while((tmp=br.readLine())!=null){
				list.add(tmp);
				tmpArr=tmp.split("#");
				i++;
				lineCount++;
				//BasketRowModel.list.add(new BasketRowModel(Integer.parseInt(tmpArr[0]),tmpArr[1],tmpArr[2],tmpArr[3],tmpArr[4],tmpArr[5],tmpArr[6],tmpArr[7],new ImageView(new Image(tmpArr[7].toString(),200,200,false,false))));
			}
			br.close();
		}catch (FileNotFoundException e) {
			System.out.println("파일 찾을 수 없음");
		}catch (NoSuchElementException e) {
			return;
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (NumberFormatException e) {
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
        site.setCellValueFactory(cellData -> cellData.getValue().getSite());
        site.setSortable(false); //사이트칼럼의 정렬기능을 막음
        detailUrl.setCellValueFactory(cellData -> cellData.getValue().getDetailUrl());
        imageUrl.setCellValueFactory(cellData -> cellData.getValue().getImageUrl());
        image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));
        image.setSortable(false); //이미지 칼럼의 정렬기능을 막음
        
        table.setItems(TableRowModel.list);
    }
    
    //------------------------좌측 버튼들의 동작 핸들러---------------------//
  	//'메인'버튼 선택시 UI_005를 불러오기 위한 동작 핸들러
  	public void handleMainBtnBtnAction(ActionEvent event) {
  		try {
  			Parent UI_001 = FXMLLoader.load(Class.forName("application.UI_001Controller_unLogin").getResource("UI_001_unLogin.fxml"));
  			Scene scene=new Scene(UI_001);
  			Stage primaryStage=(Stage)freeDeliveryBtn.getScene().getWindow();
  			primaryStage.setScene(scene);
  		} catch (IOException | ClassNotFoundException e) {
  			e.printStackTrace();
  		}
  	}
  	
  	//'일반검색'버튼 선택시 UI_002를 불러오기 위한 동작 핸들러
  	public void handleNomalSearchBtnAction(ActionEvent event) {
  		try {
  			Parent UI_002 = FXMLLoader.load(Class.forName("application.UI_002Controller_unLogin").getResource("UI_002_unLogin.fxml"));
  			Scene scene=new Scene(UI_002);
  			Stage primaryStage=(Stage)freeDeliveryBtn.getScene().getWindow();
  			primaryStage.setScene(scene);
  		} catch (IOException | ClassNotFoundException e) {
  			e.printStackTrace();
  		}
  	}
  	
  	//'무료배송'버튼 선택시 UI_005를 불러오기 위한 동작 핸들러
  	public void handlefreeDeliveryBtnAction(ActionEvent event) {
  		try {
  			Parent UI_005 = FXMLLoader.load(Class.forName("application.UI_005Controller_unLogin").getResource("UI_005_unLogin.fxml"));
  			Scene scene=new Scene(UI_005);
  			Stage primaryStage=(Stage)freeDeliveryBtn.getScene().getWindow();
  			primaryStage.setScene(scene);
  		} catch (IOException | ClassNotFoundException e) {
  			e.printStackTrace();
  		}
  	}
  	
  	//'찜 목록' 버튼 선택 시 UI_004를 불러오기 위한 동작 핸들러
  	public void handlebasketBtnAction(ActionEvent event) {
  		try {
  			Parent UI_004 = FXMLLoader.load(Class.forName("application.UI_004Controller_unLogin").getResource("UI_004_unLogin.fxml"));
  			Scene scene=new Scene(UI_004);
  			Stage primaryStage=(Stage)basketBtn.getScene().getWindow();
  			primaryStage.setScene(scene);
  		} catch (IOException | ClassNotFoundException e) {
  			e.printStackTrace();
  		}
  	}
  	
  	//테이블에 버튼을 추가하는 메소드
  	private void addButtonToTable() {
	    Callback<TableColumn<TableRowModel, Button>, TableCell<TableRowModel, Button>> cellFactory = new Callback<TableColumn<TableRowModel, Button>, TableCell<TableRowModel, Button>>() {
            public TableCell<TableRowModel, Button> call(final TableColumn<TableRowModel, Button> param) {
                final TableCell<TableRowModel,Button> cell = new TableCell<TableRowModel, Button>() {

                    private final Button btn = new Button("찜 하기");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	basketListRead();
                        	
                        	table.setItems(TableRowModel.list);
                        	
                        	BufferedWriter bufferedWriter = null;
                        	
                        	int i=0;
                        	i=lineCount;
                        	if(i>=50) {
                        		Alert alert = new Alert(AlertType.WARNING);
                    			alert.setTitle(" ");
                    			alert.setContentText("찜 록옥은 최대 50개까지 저장 가능합니다.");
                    			alert.showAndWait();
                        	}else {
                        		try {
                        			System.out.println(lineCount);
    								//bufferedWriter = new BufferedWriter(new FileWriter(file));
                            		FileOutputStream output=new FileOutputStream("basketList.txt",false);
                                    OutputStreamWriter writer=new OutputStreamWriter(output,"euc-kr");
                                    bufferedWriter=new BufferedWriter(writer);
                                    
                                    list.add(i+"#"+site.getCellData(getIndex())+"#"+name.getCellData(getIndex())+"#"+price.getCellData(getIndex())
                                    	+"#"+rating.getCellData(getIndex())+"#"+detailUrl.getCellData(getIndex())+"#"
                                    		+shippingCharge.getCellData(getIndex())+"#"+imageUrl.getCellData(getIndex()));
                                    
                                    String tmp=list.toString();
                                    tmp=tmp.replace(", ","\n");
                                    tmp=tmp.replace("[","");
                                    tmp=tmp.replace("]","");
                                    bufferedWriter.write(tmp);
                                    
                                    bufferedWriter.close();
                                    
                                    Alert alert = new Alert(AlertType.WARNING);
                        			alert.setTitle(" ");
                        			alert.setHeaderText("찜 목록 추가 완료");
                        			alert.showAndWait();
                                }catch (IOException e) {
                                    System.out.println(e);
                                }catch(NullPointerException e) {
                                	System.out.println("데이터 더 없음");
                                	try {
    									bufferedWriter.close();
    								} catch (IOException e1) {
    									e1.printStackTrace();
    								}
                                }
                        	}
                    		
                        });
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        basketAdd.setCellFactory(cellFactory);

        table.getColumns().add(basketAdd);
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
        site.setCellValueFactory(cellData -> cellData.getValue().getSite());
        site.setSortable(false); //사이트칼럼의 정렬기능을 막음
        detailUrl.setCellValueFactory(cellData -> cellData.getValue().getDetailUrl());
        imageUrl.setCellValueFactory(cellData -> cellData.getValue().getImageUrl());
        image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));
        image.setSortable(false); //이미지 칼럼의 정렬기능을 막음
        
        
        addButtonToTable();
        
        table.setItems(TableRowModel.list);
        
        //상품 목록이 나오는 테이블중 하나를 선택하면 동작되는 이벤트
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableRowModel>() {
            @Override
            public void changed(ObservableValue<? extends TableRowModel> observable, TableRowModel oldValue, TableRowModel newValue) {
                TableRowModel model = table.getSelectionModel().getSelectedItem();
                
                //상품목록 마우스 좌클릭시 인터넷 브라우저로 해당 Url을 호출함, 브라우저는 사용자 컴퓨터에 설정되어 있는 기본 브라우저
                try {
                	if(model.getSite().getValue().equals("옥션")) {
                    	Desktop.getDesktop().browse(new URI(model.getDetailUrl().getValue()));
                    }else {
                    	Desktop.getDesktop().browse(new URI("https:"+model.getDetailUrl().getValue()));
                    }
				}catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
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
		
        //-----------좌측의 화면 이동 버튼들-------------//
      	mainBtn.setOnAction(new EventHandler<ActionEvent>() {
      		public void handle(ActionEvent event) {
      			handleMainBtnBtnAction(event);
      		}
      	});
      	
      	nomalSearchBtn.setOnAction(new EventHandler<ActionEvent>() {
      		public void handle(ActionEvent event) {
      			handleNomalSearchBtnAction(event);
      		}
      	});
      	
      	freeDeliveryBtn.setOnAction(new EventHandler<ActionEvent>() {
      		public void handle(ActionEvent event) {
      			handlefreeDeliveryBtnAction(event);
      		}
      	});
      	
      	basketBtn.setOnAction(new EventHandler<ActionEvent>() {
      		public void handle(ActionEvent event) {
      			handlebasketBtnAction(event);
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