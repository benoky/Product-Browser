package dataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

public class DetailListRowModel {
	private ImageView imageView;
	
	static public ObservableList<DetailListRowModel> list = FXCollections.observableArrayList(); //리스트에 표시할 데이터를 담는 리스트 객체
	
	public DetailListRowModel(ImageView imageView) {
		this.imageView=imageView;
	}


	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
}
