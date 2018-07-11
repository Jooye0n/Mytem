package com.example.user.mytem.singleton;

public class Post {

    private String title;//제품명
    private String contents;//상세설명
    private int price;//첫 게시 가격
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

    public String getUrl() {
        return url;
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


    public Post( String title, int number, int price, String contents, String url, String detail) {
       // this.author = author;
        this.title = title;
        this.contents = contents;
        this.number = number;
        this.url = url;
        this.price = price;
        this.detail = detail;
    }

//    private String timeStamp() {
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy. MM. dd.", Locale.KOREA);
//
//        return dateFormat.format(date);
//    }

    public static Post newPost( String title, int number, int price, String contents, String url, String detail) {
        return new Post(title, number, price, contents, url, detail);
    }






}
