package application;

import java.net.URL;
import java.util.ResourceBundle;

import dataModel.DetailListRowModel;
import dataModel.TableRowModel;
import detailImageCrawler.WempDetailCrawler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class UI_003Contorller_unLogin implements Initializable{
	@FXML private TableView<DetailListRowModel> detailImageView=new TableView<>();
	@FXML private TableColumn<DetailListRowModel, ImageView> detailImage=new TableColumn<DetailListRowModel, ImageView>();

	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		WempDetailCrawler c=new WempDetailCrawler("https://front.wemakeprice.com/deal/600180985?search_keyword=%EB%B3%BC%ED%8E%9C&_no=2&_service=5");
		
		c.crawling();
		detailImage.setCellValueFactory(new PropertyValueFactory<DetailListRowModel, ImageView>("imageView"));
		detailImage.setSortable(false); //이미지 칼럼의 정렬기능을 막음
		detailImage.setMinWidth(950);
		
		detailImageView.setItems(DetailListRowModel.list);
		
	}
}
