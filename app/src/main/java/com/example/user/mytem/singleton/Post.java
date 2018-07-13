package com.example.user.mytem.singleton;

public class Post {

    private String title;//제품명
    private String contents;//상세설명
    private int price;//일반소비자가
    private int price2;//일반소비자가
    private int priceA;//일반소비자가
    private int priceB;//일반소비자가
    private String url;//사진
    private int number;//재고
    private String AuthorUid = "관리자";
    private String detail;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
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


    public Post( String title, int number, int price, int price2, int priceA, int priceB, String contents, String url, String detail) {
       // this.author = author;
        this.title = title;
        this.contents = contents;
        this.number = number;
        this.url = url;
        this.price = price;
        this.price2 = price2;
        this.priceA = priceA;
        this.priceB = priceB;
        this.detail = detail;
    }

//    private String timeStamp() {
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd.", Locale.KOREA);
//
//        return dateFormat.format(date);
//    }

    public static Post newPost( String title, int number, int price, int price2, int priceA, int priceB, String contents, String url, String detail) {
        return new Post(title, number, price, price2, priceA, priceB, contents, url, detail);
    }






}
