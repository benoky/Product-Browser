package application;

import java.net.URL;
import java.util.ResourceBundle;

import dataModel.BasketRowModel;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UI_004Controller_unLogin implements Initializable{
	// 테이블&컬럼
    @FXML private TableView<BasketRowModel> table=new TableView<>();
    @FXML private TableColumn<BasketRowModel, String> name=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> price=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> rating=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> shippingCharge=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, ImageView> image=new TableColumn<BasketRowModel, ImageView>();
    @FXML private TableColumn<BasketRowModel, Button> delete=new TableColumn<BasketRowModel, Button>();
    

    //상품명, 가격, 평점, 상세 페이지 주소, 배송비, 이미지 주소 //db에서데이터 가져와 add해야함
   //BasketRowModel.list.add(new TableRowModel("옥션",conName.text(),conPrice.text(),tmpRating,detailUrlStr,tmpCharge,new ImageView(new Image(img,200, 200, false, false))));
    
    
    public void handleDeleteBtnAction(ActionEvent event) {
    	System.out.println("삭제");
    	BasketRowModel.list.remove(0);
    	
    	table.setItems(BasketRowModel.list);
	}
    
	public void initialize(URL location, ResourceBundle resources) {
		//기능을 확인하기 위한 테스트 데이터 삽입
		BasketRowModel.list.add(new BasketRowModel(0,"1","1","1","1","1","1",new ImageView(new Image("file:\\C:\\Users\\czmn\\OneDrive\\바탕 화면\\cgv.png",200,200,false,false))));
		BasketRowModel.list.add(new BasketRowModel(1,"2","2","2","2","2","2",new ImageView(new Image("file:\\C:\\Users\\czmn\\OneDrive\\바탕 화면\\cgv.png",200,200,false,false))));
		BasketRowModel.list.add(new BasketRowModel(2,"3","3","3","3","3","3",new ImageView(new Image("file:\\C:\\Users\\czmn\\OneDrive\\바탕 화면\\cgv.png",200,200,false,false))));
		
		name.setCellValueFactory(cellData -> cellData.getValue().getName());
	    price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
	    rating.setCellValueFactory(cellData -> cellData.getValue().getRating());
	    shippingCharge.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
	    image.setCellValueFactory(new PropertyValueFactory<BasketRowModel, ImageView>("imageView"));
	    image.setSortable(false); //이미지 칼럼의 정렬기능을 막음
	    
	    //delete.setCellValueFactory(new PropertyValueFactory<BasketRowModel, Button>("button"));
	    delete.setSortable(false); //딜리트(버튼) 칼럼의 정렬기능을 막음
	    
	    addButtonToTable();
	    table.setItems(BasketRowModel.list);
	}

	    private void addButtonToTable() {
	    Callback<TableColumn<BasketRowModel, Button>, TableCell<BasketRowModel, Button>> cellFactory = new Callback<TableColumn<BasketRowModel, Button>, TableCell<BasketRowModel, Button>>() {
            public TableCell<BasketRowModel, Button> call(final TableColumn<BasketRowModel, Button> param) {
                final TableCell<BasketRowModel,Button> cell = new TableCell<BasketRowModel, Button>() {

                    private final Button btn = new Button("삭제");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	//BasketRowModel data = getTableView().getItems().get(getIndex());
                        	System.out.println("삭제"+getIndex());
                        	BasketRowModel.list.remove(getIndex());
                        	
                        	table.setItems(BasketRowModel.list);
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
}