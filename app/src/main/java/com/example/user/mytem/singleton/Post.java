package com.example.user.mytem.singleton;

public class Post {

    private String title;//제품명
    private String contents;//상세설명
    private int price;//일반소비자가
    private int price2;//일반소비자가
    private int priceA;//일반소비자가
    private int priceB;//일반소비자가
    private int number;//재고
    private String AuthorUid = "관리자";
    private String detail;
    private int count;//장바구니에서 사용하는 장바구니에 담은 수량
    private String category;
    private String brand;
    private String production;//제조사
    private String origin;//원산지
    private String delivery1;//배송정보1
    private String delivery2;//배송정보2


    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public String getProduction() {
        return production;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDelivery1() {
        return delivery1;
    }

    public String getDelivery2() {
        return delivery2;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public int getPrice() {
        return price;
    }

    public int getPrice2() {
        return price2;
    }

    public int getPriceA() {
        return priceA;
    }

    public int getPriceB() {
        return priceB;
    }

    public int getNumber() {
        return number;
    }

    public String getAuthorUid() {
        return AuthorUid;
    }

    public String getDetail() {
        return detail;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public Post( String title, int number, int price, int price2, int priceA, int priceB, String contents, String url, String detail,
                 String category, String production, String origin, String brand, String delivery1, String delivery2) {

        this.title = title;
        this.contents = contents;
        this.number = number;
        this.price = price;
        this.price2 = price2;
        this.priceA = priceA;
        this.priceB = priceB;
        this.detail = detail;
        this.category = category;
        this.production = production;
        this.origin = origin;
        this.brand = brand;
        this.delivery1 = delivery1;
        this.delivery2 = delivery2;
    }

    public Post( String title, int number, int price, int price2, int priceA, int priceB, String contents, int count, String detail,
                 String category, String production, String origin, String brand, String delivery1, String delivery2) {

        this.title = title;
        this.contents = contents;
        this.number = number;
        this.price = price;
        this.price2 = price2;
        this.priceA = priceA;
        this.priceB = priceB;
        this.detail = detail;
        this.count = count;
        this.category = category;
        this.production = production;
        this.origin = origin;
        this.brand = brand;
        this.delivery1 = delivery1;
        this.delivery2 = delivery2;
    }

    public static Post newPost( String title, int number, int price, int price2, int priceA, int priceB, String contents, String url, String detail,
                                String category, String production, String origin, String brand, String delivery1, String delivery2) {
        return new Post(title, number, price, price2, priceA, priceB, contents, url, detail,category, production, origin, brand, delivery1, delivery2 );
    }

    public static Post newCart( String title, int number, int price, int price2, int priceA, int priceB, String contents, int count, String detail,
                                String category, String production, String origin, String brand, String delivery1, String delivery2) {
        return new Post(title, number, price, price2, priceA, priceB, contents, count, detail,category, production, origin, brand, delivery1, delivery2);
    }


}
