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
	// ���̺�&�÷�
    @FXML private TableView<BasketRowModel> table=new TableView<>();
    @FXML private TableColumn<BasketRowModel, String> name=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> price=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> rating=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, String> shippingCharge=new TableColumn<>();
    @FXML private TableColumn<BasketRowModel, ImageView> image=new TableColumn<BasketRowModel, ImageView>();
    @FXML private TableColumn<BasketRowModel, Button> delete=new TableColumn<BasketRowModel, Button>();
    

    //��ǰ��, ����, ����, �� ������ �ּ�, ��ۺ�, �̹��� �ּ� //db���������� ������ add�ؾ���
   //BasketRowModel.list.add(new TableRowModel("����",conName.text(),conPrice.text(),tmpRating,detailUrlStr,tmpCharge,new ImageView(new Image(img,200, 200, false, false))));
    
    
    public void handleDeleteBtnAction(ActionEvent event) {
    	System.out.println("����");
    	BasketRowModel.list.remove(0);
    	
    	table.setItems(BasketRowModel.list);
	}
    
	public void initialize(URL location, ResourceBundle resources) {
		//����� Ȯ���ϱ� ���� �׽�Ʈ ������ ����
		BasketRowModel.list.add(new BasketRowModel(0,"1","1","1","1","1","1",new ImageView(new Image("file:\\C:\\Users\\czmn\\OneDrive\\���� ȭ��\\cgv.png",200,200,false,false))));
		BasketRowModel.list.add(new BasketRowModel(1,"2","2","2","2","2","2",new ImageView(new Image("file:\\C:\\Users\\czmn\\OneDrive\\���� ȭ��\\cgv.png",200,200,false,false))));
		BasketRowModel.list.add(new BasketRowModel(2,"3","3","3","3","3","3",new ImageView(new Image("file:\\C:\\Users\\czmn\\OneDrive\\���� ȭ��\\cgv.png",200,200,false,false))));
		
		name.setCellValueFactory(cellData -> cellData.getValue().getName());
	    price.setCellValueFactory(cellData -> cellData.getValue().getPrice());
	    rating.setCellValueFactory(cellData -> cellData.getValue().getRating());
	    shippingCharge.setCellValueFactory(cellData -> cellData.getValue().getShippingCharge());
	    image.setCellValueFactory(new PropertyValueFactory<BasketRowModel, ImageView>("imageView"));
	    image.setSortable(false); //�̹��� Į���� ���ı���� ����
	    
	    //delete.setCellValueFactory(new PropertyValueFactory<BasketRowModel, Button>("button"));
	    delete.setSortable(false); //����Ʈ(��ư) Į���� ���ı���� ����
	    
	    addButtonToTable();
	    table.setItems(BasketRowModel.list);
	}

	    private void addButtonToTable() {
	    Callback<TableColumn<BasketRowModel, Button>, TableCell<BasketRowModel, Button>> cellFactory = new Callback<TableColumn<BasketRowModel, Button>, TableCell<BasketRowModel, Button>>() {
            public TableCell<BasketRowModel, Button> call(final TableColumn<BasketRowModel, Button> param) {
                final TableCell<BasketRowModel,Button> cell = new TableCell<BasketRowModel, Button>() {

                    private final Button btn = new Button("����");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	//BasketRowModel data = getTableView().getItems().get(getIndex());
                        	System.out.println("����"+getIndex());
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