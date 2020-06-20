package dataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

public class DetailListRowModel {
	private ImageView imageView;
	
	static public ObservableList<DetailListRowModel> list = FXCollections.observableArrayList(); //����Ʈ�� ǥ���� �����͸� ��� ����Ʈ ��ü
	
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
