package dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
 
public class TableRowModel {
    private StringProperty name;
    private StringProperty price;
    private StringProperty rating;
    private StringProperty detailUrl;
    private StringProperty shippingCharge;
    private ImageView imageView;
    
    static public ObservableList<TableRowModel> list = FXCollections.observableArrayList(); //���̺� ǥ���� �����͸� ��� ����Ʈ ��ü
    
	// ������ (Alt + Shift + S �� ���� ���(Constructor))
    public TableRowModel(String name, String price,String rating,String detailUrl,String shippingCharge,ImageView imageView) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.rating=new SimpleStringProperty(rating);
        this.detailUrl=new SimpleStringProperty(detailUrl);
        this.shippingCharge=new SimpleStringProperty(shippingCharge);
        this.imageView=imageView;
    }

	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	public StringProperty getShippingCharge() {
		return shippingCharge;
	}
	public void setShippingCharge(StringProperty shippingCharge) {
		this.shippingCharge = shippingCharge;
	}
	// ���ͼ��� (Alt + Shift + S �� ���� ���)
    public StringProperty getName() {
        return name;
    }
    public void setName(StringProperty name) {
        this.name = name;
    }
    public StringProperty getPrice() {
        return price;
    }
    public void setPrice(StringProperty price) {
        this.price = price;
    }
    public StringProperty getRating() {
		return rating;
	}
	public void setRating(StringProperty rating) {
		this.rating = rating;
	}
	public StringProperty getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(StringProperty detailUrl) {
		this.detailUrl = detailUrl;
	}
}