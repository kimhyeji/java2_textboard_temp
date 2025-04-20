package vo; // vo --> Value Object 의 약자 (데이터를 담아 전달하는 목적으로 보통 생성)

// 게시글을 담당하는 클래스 
public class Article {

	// Article 클래스 안의 게시글의 속성들 (변수)
	// 캡슐화를 통한 private 접근제어자로 작성
	private int articleId;
	private String regDate;
	private String title;
	private String body;
	
	// 유저
	private int userId; // 유저 고유 번호(로그인 아이디x)
	private String userName; // 유저 이름

	// 위의 필드 변수가 private 이기 때문에, 값을 세팅하고 가져오려면
	// 아래 코드처럼 getter, setter 메서드를 만들어줘야 한다.
	// 게터세터 쉽게 만들기 -> (오른쪽 클릭 -> source -> Generate Getter and Setter 클릭 -> select all -> generate)
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
