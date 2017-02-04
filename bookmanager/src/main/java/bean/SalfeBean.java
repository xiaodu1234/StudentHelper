package bean;

/**
 * Created by duchaoqiang on 2016/12/29.
 * 售书表的bean
 */
public class SalfeBean {
    private int barcode;
    private int num;
    private String date;
    private String author;
    private int price;
    private String booName;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBooName() {
        return booName;
    }

    public void setBooName(String booName) {
        this.booName = booName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
