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
    // ���̺�&�÷�
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
    
    //�˻�� �Է��� �ؽ�Ʈ �ʵ�� �˻� ��ư�� �����ϱ� ���� ������ ��ü ����
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
    //�� ����� ����Ǿ� �ִ� ���Ͽ��� ������ �о���� �޼ҵ�
  	private void basketListRead() {
  		lineCount=0;
  		list.clear();
  		File file = new File("basketList.txt");
  		tmp=null;
  		try{
  			//��ĳ�ʷ� ���� �б� (������ ������ ���پ� ���๮�ڸ� �������� �о��)
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
			System.out.println("���� ã�� �� ����");
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
    
    //���ο� ��ǰ�� �˻��ϰ��� '�˻�'��ư Ŭ���� ���� �ڵ鷯
    public void handleSearchBtnAction(ActionEvent e) {
    	init(); //������ ����Ǿ� �ִ� ����Ʈ�� ����� �ʱ�ȭ ��Ŵ
    	
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
        site.setSortable(false); //����ƮĮ���� ���ı���� ����
        detailUrl.setCellValueFactory(cellData -> cellData.getValue().getDetailUrl());
        imageUrl.setCellValueFactory(cellData -> cellData.getValue().getImageUrl());
        image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));
        image.setSortable(false); //�̹��� Į���� ���ı���� ����
        
        table.setItems(TableRowModel.list);
    }
    
    //------------------------���� ��ư���� ���� �ڵ鷯---------------------//
  	//'����'��ư ���ý� UI_005�� �ҷ����� ���� ���� �ڵ鷯
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
  	
  	//'�Ϲݰ˻�'��ư ���ý� UI_002�� �ҷ����� ���� ���� �ڵ鷯
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
  	
  	//'������'��ư ���ý� UI_005�� �ҷ����� ���� ���� �ڵ鷯
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
  	
  	//'�� ���' ��ư ���� �� UI_004�� �ҷ����� ���� ���� �ڵ鷯
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
  	
  	//���̺� ��ư�� �߰��ϴ� �޼ҵ�
  	private void addButtonToTable() {
	    Callback<TableColumn<TableRowModel, Button>, TableCell<TableRowModel, Button>> cellFactory = new Callback<TableColumn<TableRowModel, Button>, TableCell<TableRowModel, Button>>() {
            public TableCell<TableRowModel, Button> call(final TableColumn<TableRowModel, Button> param) {
                final TableCell<TableRowModel,Button> cell = new TableCell<TableRowModel, Button>() {

                    private final Button btn = new Button("�� �ϱ�");
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
                    			alert.setContentText("�� �Ͽ��� �ִ� 50������ ���� �����մϴ�.");
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
                        			alert.setHeaderText("�� ��� �߰� �Ϸ�");
                        			alert.showAndWait();
                                }catch (IOException e) {
                                    System.out.println(e);
                                }catch(NullPointerException e) {
                                	System.out.println("������ �� ����");
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
  	
    //ó�� UI_002�� UI_001���� �˻�� �޾� ���� �Ǿ������� ����  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	init(); //������ ����Ǿ� �ִ� ����Ʈ�� ����� �ʱ�ȭ ��Ŵ
    	
        //ó�� UI_002�� ���� ���� ��� UI_001DumyController�� ����� searchWord�� �����
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
        site.setSortable(false); //����ƮĮ���� ���ı���� ����
        detailUrl.setCellValueFactory(cellData -> cellData.getValue().getDetailUrl());
        imageUrl.setCellValueFactory(cellData -> cellData.getValue().getImageUrl());
        image.setCellValueFactory(new PropertyValueFactory<TableRowModel, ImageView>("imageView"));
        image.setSortable(false); //�̹��� Į���� ���ı���� ����
        
        
        addButtonToTable();
        
        table.setItems(TableRowModel.list);
        
        //��ǰ ����� ������ ���̺��� �ϳ��� �����ϸ� ���۵Ǵ� �̺�Ʈ
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TableRowModel>() {
            @Override
            public void changed(ObservableValue<? extends TableRowModel> observable, TableRowModel oldValue, TableRowModel newValue) {
                TableRowModel model = table.getSelectionModel().getSelectedItem();
                
                //��ǰ��� ���콺 ��Ŭ���� ���ͳ� �������� �ش� Url�� ȣ����, �������� ����� ��ǻ�Ϳ� �����Ǿ� �ִ� �⺻ ������
                try {
                	if(model.getSite().getValue().equals("����")) {
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
        
        //���ο� �˻�� �Է��Ͽ� �ٸ� ��ǰ�� ã������ �Ҷ� ���, �˻�â�� �˻��� �Է� �� '�˻�'��ư Ŭ���� �ڵ鷯 ȣ��
        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				handleSearchBtnAction(event);
			}
		});
		
        //-----------������ ȭ�� �̵� ��ư��-------------//
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

    //���̺� ǥ�õǴ� list�� �ʱ�ȭ ��Ű�� �޼ҵ�
    public void init() {
    	TableRowModel.list.clear();
        // ����Ʈ�� ���� �ڵ鸵 �� ���� �� �κ��� �ʿ�
        table.setItems(TableRowModel.list);
    }
}