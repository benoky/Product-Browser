package application;

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
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import dataModel.BasketRowModel;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UI_004Controller_unLogin implements Initializable{
	// ���̺�&�÷�
    @FXML private TableView<BasketRowModel> table=new TableView<>();
    @FXML private TableColumn<BasketRowModel, String> name=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> price=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> rating=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> shippingCharge=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> site=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> detailUrl=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, ImageView> image=new TableColumn<BasketRowModel, ImageView>();
    @FXML private TableColumn<BasketRowModel, String> imageUrl=new TableColumn<BasketRowModel, String>();
    @FXML private TableColumn<BasketRowModel, Button> delete=new TableColumn<BasketRowModel, Button>();
    

    //��ǰ��, ����, ����, �� ������ �ּ�, ��ۺ�, �̹��� �ּ� //db���������� ������ add�ؾ���
   //BasketRowModel.list.add(new TableRowModel("����",conName.text(),conPrice.text(),tmpRating,detailUrlStr,tmpCharge,new ImageView(new Image(img,200, 200, false, false))));
    
    @FXML private Button mainBtn=new Button();
   	@FXML private Button nomalSearchBtn=new Button();
   	@FXML private Button basketBtn=new Button();
   	@FXML private Button freeDeliveryBtn=new Button();
   	
   	//���� Ŭ�������� ���� basketList������ ������ ���޹޾� �ӽ÷� �����ϴ� �ʵ�
	StringProperty siteTmp;
	StringProperty nameTmp;
	StringProperty priceTmp;
	StringProperty ratingTmp;
	StringProperty shippingChargeTmp;
	StringProperty detailUrlTmp;
	ImageView imageViewTmp;
   	
	BufferedReader br;
    BufferedWriter bufferedWriter;
    
    String tmp;
	
   	//�� ����� ����Ǿ� �ִ� ���Ͽ��� ������ �о���� �޼ҵ�
  	private void basketListRead() {
  		File file = new File("basketList.txt");
  		init();
  		tmp=null;
  		try{
  			//���۷� ���� �б�
  			br= new BufferedReader(new InputStreamReader(new FileInputStream(file),"euc-kr"));
  			
  			//bufferedWriter = new BufferedWriter(new FileWriter(file));
  			FileOutputStream output=new FileOutputStream("basketList.txt",true);

  		    OutputStreamWriter writer=new OutputStreamWriter(output,"euc-kr");
            bufferedWriter=new BufferedWriter(writer);
            
  			int i=0;
			while((tmp=br.readLine())!=null){
				String[] tmpArr=tmp.split("#");
				
				BasketRowModel.list.add(new BasketRowModel(i,tmpArr[1],tmpArr[2],tmpArr[3],tmpArr[4],tmpArr[5],tmpArr[6],tmpArr[7],new ImageView(new Image(tmpArr[7].toString(),200,200,false,false))));

                i++;	 
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (NoSuchElementException e) {
			try {
				bufferedWriter.close();

		        br.close();
			}catch(IOException e1){
				
			}
			return;
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
  	}
    
	public void handleDeleteBtnAction(ActionEvent event) {
    	BasketRowModel.list.remove(0);
    	
    	table.setItems(BasketRowModel.list);
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
    
	public void initialize(URL location, ResourceBundle resources) {
		basketListRead();
		
		name.setCellValueFactory(cellData -> cellData.getValue().getName());
	    price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
	    rating.setCellValueFactory(cellData -> cellData.getValue().getRating());
	    shippingCharge.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
	    site.setCellValueFactory(cellData -> cellData.getValue().getSite());
	    detailUrl.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
	    imageUrl.setCellValueFactory(cellData -> cellData.getValue().getImageUrl());
	    image.setCellValueFactory(new PropertyValueFactory<BasketRowModel, ImageView>("imageView"));
	    image.setSortable(false); //�̹��� Į���� ���ı���� ����
	    
	    //delete.setCellValueFactory(new PropertyValueFactory<BasketRowModel, Button>("button"));
	    delete.setSortable(false); //����Ʈ(��ư) Į���� ���ı���� ����
	    
	    addButtonToTable();
	    
	    table.setItems(BasketRowModel.list);
	    
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

	    private void addButtonToTable() {
			
	    Callback<TableColumn<BasketRowModel, Button>, TableCell<BasketRowModel, Button>> cellFactory = new Callback<TableColumn<BasketRowModel, Button>, TableCell<BasketRowModel, Button>>() {
            public TableCell<BasketRowModel, Button> call(final TableColumn<BasketRowModel, Button> param) {
                final TableCell<BasketRowModel,Button> cell = new TableCell<BasketRowModel, Button>() {

                    private final Button btn = new Button("����");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	int i=0;
                        	
                        	BasketRowModel.list.remove(getIndex());
                        	System.out.println(BasketRowModel.index);

                        	table.setItems(BasketRowModel.list);
                        	BufferedWriter bufferedWriter = null;
                        	try {
                        		FileOutputStream output=new FileOutputStream("basketList.txt",false);
                                OutputStreamWriter writer=new OutputStreamWriter(output,"euc-kr");
                                bufferedWriter=new BufferedWriter(writer);
							
                                while(name.getCellData(i)!=null) {
                                	bufferedWriter.write(i+"#"+site.getCellData(i)+"#"+name.getCellData(i)+"#"+price.getCellData(i)
                                	+"#"+rating.getCellData(i)+"#"+detailUrl.getCellData(i)+"#"
                                		+shippingCharge.getCellData(i)+"#"+imageUrl.getCellData(i)+"\r\n");
                                	
                                   	i++;	
                                }
                                bufferedWriter.close();
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
        delete.setCellFactory(cellFactory);

        table.getColumns().add(delete);
	}
	//���̺� ǥ�õǴ� list�� �ʱ�ȭ ��Ű�� �޼ҵ�
	public void init() {
		BasketRowModel.list.clear();
		// ����Ʈ�� ���� �ڵ鸵 �� ���� �� �κ��� �ʿ�
		table.setItems(BasketRowModel.list);
	}
}