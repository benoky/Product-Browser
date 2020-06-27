package dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

//찜 목록에 저장할 데이터들을 행 형태로 저장하는 클래스
public class BasketRowModel {
	private StringProperty site;
    private StringProperty name;
    private StringProperty price;
    private StringProperty rating;
    private StringProperty detailUrl;
    private StringProperty shippingCharge;
    private ImageView imageView;
    private Button button;
    public static int index;
    
	static public ObservableList<BasketRowModel> list = FXCollections.observableArrayList(); //테이블에 표시할 데이터를 담는 리스트 객체
    
    public BasketRowModel(int index,String site, String name, String price,String rating,String detailUrl,String shippingCharge,ImageView imageView) {
    	this.site=new SimpleStringProperty(site);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
        this.rating=new SimpleStringProperty(rating);
        this.detailUrl=new SimpleStringProperty(detailUrl);
        this.shippingCharge=new SimpleStringProperty(shippingCharge);
        this.imageView=imageView;

        this.index=index;
    }

    public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		this.button = button;
	}
	public StringProperty getSite() {
		return site;
	}
	public void setSite(StringProperty site) {
		this.site = site;
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
