package entity;

public class GraphicsCard implements ComputerComponent{

    int price;
    int cudaCores;
    String name;
    String clockSpeed;
    String turboClock;
    String articleNumber;
    String vRam;
    String height;
    String width;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCudaCores() {
        return cudaCores;
    }

    public void setCudaCores(int cudaCores) {
        this.cudaCores = cudaCores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClockSpeed() {
        return clockSpeed;
    }

    public void setClockSpeed(String clockSpeed) {
        this.clockSpeed = clockSpeed;
    }

    public String getTurboClock() {
        return turboClock;
    }

    public void setTurboClock(String turboClock) {
        this.turboClock = turboClock;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getvRam() {
        return vRam;
    }

    public void setvRam(String vRam) {
        this.vRam = vRam;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
