package vo;

public class User {
	private int userId; // 유저 고유번호
	private String regDate; // 가입일
	private String userLoginId; // 아이디
	private String userLoginPw; // 비밀번호
	private String userName; // 유저 이름
	private boolean userState = true; // 유저 상태
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getLoginId() {
		return userLoginId;
	}

	public void setLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String setLoginPw() {
		return userLoginPw;
	}

	public void setUserLoginPw(String userLoginPw) {
		this.userLoginPw = userLoginPw;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public boolean getUserState() {
		return userState;
	}

	public void setUserState(boolean userState) {
		this.userState = userState;
	}
}
