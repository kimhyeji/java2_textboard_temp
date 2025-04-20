package java2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import util.CurrentDateTime;
import vo.Article;
import vo.User;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// 게시글 목록 저장소
		List<Article> articleList = new ArrayList<Article>();

		// 게시글 목록 저장소
		// 자동 import 단축키 : Ctrl + Shift + O(알파벳)
		List<User> userList = new ArrayList<>();
		
		// 로그인 유저의 상태를 유지시키기위한 변수 생성(세션)
		User userSession = null; // 이게 null값이면 현재 로그아웃 상태

		int articleLastId = 0;
		int userLastId = 0;

		System.out.println("텍스트 게시판 시작");

		while (true) {
			System.out.print("명령어 : ");

			String request = sc.next();
			
			/******************** 유저 시작 *************************/
			if (request.startsWith("/user/logout")) { // 로그아웃
				// userSession이 null이 아닐 때 라는 말이 결국 다른 회원의 값이 들어 있는 것이기 때문에 로그인이 되어있다는 전재가 된다.				// 
				if ( userSession != null ) {
					// 다시 userSesstion을 null로 만들어 주면서 다시 빈 값으로 돌아가고 로그아웃 처리가 된다.
					userSession = null;
					System.out.println("로그아웃 되었습니다.");
				}
				
			}
			else if (request.startsWith("/user/login")) { // 로그인
				
				// userSession이 null이 아니라는 것은 현재 회원이 들어 있다는 것
				if (userSession != null ) {
					System.out.println("이미 로그인된 상태 입니다.");
					continue;
				}
				
				System.out.print("사용자 아이디 : "); // 사용자 아이디 입력
				String userLoginId = sc.next();
				
				System.out.print("사용자 비밀번호 : "); // 사용자 비밀번호 입력
				String userLoginPw = sc.next();
				
				User findByUser = null; // 유저 정보를 처음에는 null로 넣어주면서 아직 아무도 추가가 안됨
				
				// userList 돌면서 한 명 한 명 꺼내와서 조건문이 일치하는지 확인 해본다.
				for ( User user : userList ) {
					// 기존 회원의 로그인 아이디와 비밀번호가 현재 입력한 값이랑 같은지 체크.
					// && 연산자를 통해 양쪽 모두가 참이어야 findByUser에 값을 추가한다.
					if ( user.getUserLoginId().equals(userLoginId) && user.getUserLoginPw().equals(userLoginPw) ) {
						findByUser = user;
					}
				}
				
				// 만약 여기까지 왔는데 findByUser가 아직도 여전히 null이라면 존재하지 않은 회원이라는 문구를 출력.
				if ( findByUser == null ) {
					System.out.println("존재하지 않은 회원입니다. 비밀번호 또는 아이디를 다시 확인해주세요.");
				} else {
					// 로그인 완료 + 로그인 문구
					userSession = findByUser;
					// 반복문에서 찾아온 회원을 세션에 넣어 줌으로써 현재 로그인이 되었다는 것처럼 비슷한 기능 구현
					// 진짜 세션은 아니고 세션 비슷하게 흉내만 내는 것
					System.out.println("로그인이 완료 되었습니다.");
				}
			}
			else if (request.startsWith("/user/join")) { // 회원가입
				
				String userLoginId = null;

				while (true) {
				    System.out.print("사용자 아이디 : ");
				    userLoginId = sc.next(); // 로그인 아이디 입력

				    /* 유저 로그인 아이디 중복 체크 시작 */
				    // 현재 입력한 아이디 값이 중복된 값인지를 확인하기 위해 만든 상태값 저장 변수
				    // 기본값은 false값이며 아이디가 중복이 되었을 땐 true 값으로 바뀐다.
				    boolean isDuplicate = false;

				    // userList에서 한명씩 꺼내와서 반복문을 돌며 조건을 체크한다.
				    for (User user : userList) {
				    	// 조건 : userList에 있는 회원의 아이디와 현재 입력한 회원 아이디가 똑같은지 비교한다.
				    	// 만약 조건이 맞다면 기존에 있던 아이디 라고 하는 것이기 때문에 isDuplicate 변수에 있던 상태 값을 true로 전환하면서
				    	// break문을 통해 반복문을 빠져나간다.
				        if (user.getUserLoginId().equals(userLoginId)) {
				            isDuplicate = true;
				            break;
				        }
				    }
				    
				    // isDuplicate 변수가 처음에는 false였지만 기존에 쓰던 회원이 존재하여 true로 바뀌게 되면
				    // 아래 조건문이 실행 되면서 사용자에게 이미 사용중인 아이디라는 문구를 남겨준다.
				    if (isDuplicate) {
				        System.out.printf("%s(은)는 이미 사용중인 아이디입니다.\n", userLoginId);
				    } else {
				    	// 만약 여전히 isDuplicate 변수가 false값이라면 아직 해당 아이가 존재한 이력이 없는 것이기 때문에
				    	// 이쪽 조건이 실행 되면서 사용 가능 문구를 띄워준 후 while에서 계속 돌던 반복문을 break로 종료 시킨다.
				        System.out.printf("%s(은)는 사용가능한 아이디입니다.\n", userLoginId);
				        break;
				        // 종료하게되면 자연스럽게 비밀번호를 입력할 수 있는 곳으로 넘어간다.
				    }
				}
				
				// ** 비밀번호도 while문으로 묶어서 틀렸을때 여기서부터 다시 실행 될 수 있도록 만들어보기**
				System.out.print("사용자 비밀번호 : ");
				String userLoginPw = sc.next(); // 비밀번호 입력
				
				// 비밀번호가 5자리 이상 입력이 될 수 있도록 체크
				if (userLoginPw.length() < 5) {
					System.out.println("비밀번호는 최소 5자리 이상 입력해주세요.");
					continue;
				}
				
				System.out.print("사용자 비밀번호 확인 : ");
				String userLoginPwConfirm = sc.next(); // 비밀번호를 정말 맞게 입력 했는지 확인
				
				// 위에 입력한 비밀번호와 비밀번호 확인 란의 내용이 다르다면 아래 if문 실행
				if (!userLoginPw.equals(userLoginPwConfirm)) {
					System.out.println("비밀번호가 맞지 않습니다. 다시 확인해주세요.");
					continue;
				}
				
				System.out.print("사용자 이름 : ");
				String userName = sc.next(); // 이름 입력
				
				User user = new User();
				// 객체로 묶어서 관리
				
				userLastId++;
				user.setUserId(userLastId);
				user.setUserLoginId(userLoginId); // 로그인 아이디 세팅
				user.setUserLoginPw(userLoginPw); // 로그인 비밀번호 세팅
				user.setUserName(userName); // 로그인 이름 세팅
				user.setRegDate(CurrentDateTime.now()); // 현재 날짜로 저장
				
				userList.add(user); // 유저 리스트에 회원 정보를 객체로 묶어서 저장

				System.out.printf("%s님 회원가입이 완료되었습니다. 환영합니다^^!\n", userName);
				// 센스있는 한 마디^^
			}
			/******************** 유저 끝 *************************/
			else if (request.startsWith("/post/update/article?articleId=")) { // 게시글 수정
				
				if ( userSession == null ) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}

				/* 유효성 검사 및 예외 처리 시작 */
				String[] params = request.split("=");

				if (params.length < 2) {
					System.out.println("게시글 번호를 입력해주세요.");
					continue;
				}

				int articleId = 0;

				try {
					articleId = Integer.parseInt(params[1]); // ----> 사용자가 입력한 값

				} catch (NumberFormatException e) {
					System.out.println("정수를 입력해주세요.");
					continue;
				}

				/* 유효성 검사 및 예외 처리 끝 */

				Article findByArticle = null; // 게시글 찾았을 때 담을 임시 Article

				for (Article article : articleList) {
					if (article.getArticleId() == articleId) {
						findByArticle = article; // 게시글을 찾았다면 Article 을 통째로 임시 Article 에 넣어준다.
					}
				}

				// 여기서 최종적으로 findByArticle 의 값을 체크
				if (findByArticle == null) {
					System.out.println(articleId + "번 게시글은 존재하지 않습니다.");

				} else {

					sc.nextLine();

					System.out.print("수정할 게시글 제목 : ");
					String newTitle = sc.nextLine();
					findByArticle.setTitle(newTitle); // 만들어 두었던 setter 메서드로 값을 수정 (새로 세팅)

					System.out.print("수정할 게시글 내용 : ");
					String newBody = sc.nextLine();
					findByArticle.setBody(newBody);

					System.out.println(articleId + "번 게시글 수정이 완료되었습니다.");
				}

			}

			else if (request.startsWith("/get/search/article")) {
				
				if ( userSession == null ) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}
				
				// \\이스케이프 를 쓰는 이유는 자바에서 물음표(?) 자체를 문자로 인식하지 않기 때문에 문자 자체를 알려주기 위함.
				String[] path = request.split("\\?");
				// 이렇게 자르게 되면 path 에는 [ "/get/search/article", "title=title&body=body" ] 이런식으로
				// 들어있게 된다.
				// 해당 기능은 title 파라메터 혹은 body 파라메터 둘 다 필요로 하기 때문에 하나씩 쪼개는 작업이 필요함.
				// 우선 크게 & 를 잘라야 됨. request.split("&");
				// String params = request.split("&"); --> params 에는 & 를 기준으로 잘랐기 때문에 1개가
				// 들어있을수도, 2개가 들어있을수도있음.
				// 동적으로 계속 변할수도 있기 때문에 for 문을 쓰면 됨.

				// 파라메터 저장 Map
				Map<String, String> paramsMap = new HashMap<>();

				for (String params : path[1].split("&")) {
					// 마지막으로 한 번 더 split 을 해줘야 함
					// params 에는 title=title 혹은 body=body 의 단순 문자열 형태가 들어가 있는데
					// 추후 값을 꺼내고 관리하기 위해서 이 문자열을 Map 객체 안에다가 넣어줘야 됨 .
					String[] keyValues = params.split("=");

					// --> 이렇게 하면 추후 어떤 파라메터가 들어오든지 전부 다 쓰기 편한 Map 형태로 만들어주기 때문에 다른 곳에다가 응용해도 됨
					paramsMap.put(keyValues[0], keyValues[1]);
				}

				// 여기까지 통과가 됐다면, 현재 paramsMap 안에는 사용자가 쓴 파라메터 값들이 Map 형태로 정렬 되어있다는 뜻.

				// 입력된 값을 새로운 변수 안에다가 넣어준다.
				// 이걸 하려고 위에서부터 문자열 split 을 진행한 것
				String title = paramsMap.get("title");
				String body = paramsMap.get("body");

				// 이제 for 문을 돌면서 해당 값들을 찾아야 함 .
				// 우선 조건이 3개가 존재한다.
				// 1. title 과 body 가 둘 다 존재할 때
				// 2. title 하나만 있을 때
				// 3. body 하나만 있을 때

				List<Article> findByArticles = new ArrayList<>();

//				버전 1
				for (Article article : articleList) {
					if (title != null && body != null) { // title 과 body 가 둘 다 존재할 때

						// title 과 body 가 전부 비교해서 해당 값과 맞는 게시글 찾아서 임시 리스트 변수에 추가
						if (article.getTitle().contains(title) && article.getBody().contains(body)) {
							findByArticles.add(article);
						}

					} else if (title != null) { // title 만 존재할 때

						// title 을 비교해서 해당 값과 맞는 게시글 찾아서 임시 리스트 변수에 추가
						if (article.getTitle().contains(title)) {
							findByArticles.add(article);
						}

					} else if (body != null) { // body 만 존재할 때

						// body 을 비교해서 해당 값과 맞는 게시글 찾아서 임시 리스트 변수에 추가
						if (article.getBody().contains(body)) {
							findByArticles.add(article);
						}
					}
				}

				// 여기서 최종적으로 findByArticles 에 값이 있는지 없는지 체크
				if (findByArticles.size() == 0) {
					System.out.println("찾는 게시글이 존재하지 않습니다.");

				} else {
					System.out.println("==== 검색된 게시글 ====");

					for (Article article : findByArticles) {
						System.out.println("====" + article.getArticleId() + "번 게시글 ====");
						System.out.println("작성일 : " + article.getRegDate());
						System.out.println("작성자 : " + article.getUserName());
						System.out.println("제목 : " + article.getTitle());
						System.out.println("내용 : " + article.getBody());
					}
				}

			}

			// equals 로 하면 동적으로 변하는 게시글 번호값을 가져올 수 없음.
			// 그래서 contains 아니면 startsWith 메서드를 써야 함.
			else if (request.startsWith("/get/article?articleId=")) {
				
				// 세션에 있는 정보를 사용하여 현재 회원이 로그인 되었는지 판단 후 안쪽으로 들어갈 수 있게 구현
				if ( userSession == null ) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}
				
				// = 를 기준으로 쪼개기.
				// 조각이 생기기 때문에 [배열]이 리턴되는 것
				// ["/get/article?articleId", "특정정수값"]
				String[] params = request.split("=");

				// split 을 통한 배열의 길이 값이 2보다 작다면,
				// 사용자가 값을 입력하지 않은 것..
				// = 를 기준으로 잘랐기때문에 값을 입력했다면 무조건 배열의 길이 값은 2개 이상임.
				if (params.length < 2) {
					System.out.println("게시글 번호를 입력해주세요.");
					continue;
				}

				int articleId = 0; // try 안으로 변수가 들어가기 때문에 위에 더 넓은 코드 블록에서 활용하기 위한 변수 선언

				try { // ---> 정수화를 해야 하는데 사용자자가 문자정수열이 아닌 일반 문자를 입력했을때를 위한 처리

					// params[1] 에는 특정 정수값이 들어있는데 현재는 문자열
					// 그래서 Integer.parseInt 메서드로 정수화 진행
					// 곧 비교해야 할 articleList 안에 들어있는 게시글 번호는 정수이기때문에..
					articleId = Integer.parseInt(params[1]); // ----> 사용자가 입력한 값

				} catch (NumberFormatException e) { // 어차피 정수가 아니면 parseInt 메서드에서 에러가 뜨는데 해당 에러 Exception 명시
					System.out.println("정수를 입력해주세요.");
					continue;
				}

				// 찾은 게시글 하나를 담을 임시 Article 클래스
				Article article = null;

				for (int i = 0; i < articleList.size(); i++) {
					// 이미 게시글에 등록된 번호랑 사용자가 입력한 값이랑 비교
					if (articleList.get(i).getArticleId() == articleId && articleList.get(i).getUserId() == userSession.getUserId()) {
						// 여기를 통과하면 값을 찾은 것임.
						// 찾은 articleList 안에 있는 article 객체를
						// 위에 임시 Article 클래스 변수 안에다가 할당
						article = articleList.get(i);
					}
				}

				// for 문의 끝나고 최종적으로 article 안에 값이 null 이라면,
				// 입력했던 번호의 게시글을 찾지 못한 것.
				if (article == null) {
					System.out.println(articleId + "번 게시글은 존재하지 않거나, 권한이 없습니다.");

				} else {
					System.out.println("====" + article.getArticleId() + "번 게시글 ====");
					System.out.println("작성일 : " + article.getRegDate());
					System.out.println("작성자 : " + article.getUserName());
					System.out.println("제목 : " + article.getTitle());
					System.out.println("내용 : " + article.getBody());
				}

			}

			else if (request.startsWith("/post/removeAll/article")) {
				if (articleList.size() == 0) {
					System.out.println("게시글이 존재하지 않습니다.");
					continue;
				}

				articleList.clear();
				System.out.println("모든 게시글이 삭제되었습니다.");
			}

			else if (request.startsWith("/post/remove/article?articleId=")) {
				
				if ( userSession == null ) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}

				/* 유효성 검사 및 예외 처리 시작 */
				String[] params = request.split("=");

				if (params.length < 2) {
					System.out.println("게시글 번호를 입력해주세요.");
					continue;
				}

				int articleId = 0;

				try {
					articleId = Integer.parseInt(params[1]); // ----> 사용자가 입력한 값

				} catch (NumberFormatException e) {
					System.out.println("정수를 입력해주세요.");
					continue;
				}

				/* 유효성 검사 및 예외 처리 끝 */

				/* 게시글이 삭제 되었는지 여부를 알기 위한 임시 변수 */
				boolean isRemoveArticle = false;

				for (int i = 0; i < articleList.size(); i++) {
					// 비교를 할 때 이젠 작성자 까지 같이 비교를 해야함.
					if (articleList.get(i).getArticleId() == articleId && articleList.get(i).getUserId() == userSession.getUserId()) {
						articleList.remove(i); // 현재 찾은 게시글의 인덱스 삭제 즉, 찾은 게시글 게시글 삭제 처리
						isRemoveArticle = true;
						break; // 찾았다면 더 지체하지말고 for 문 브레이크.
					}
				}

				if (isRemoveArticle) {
					System.out.println(articleId + "번 게시글을 삭제하였습니다.");

				} else {
					System.out.println(articleId + "번 게시글은 존재하지 않거나, 권한이 없습니다.");
				}

			}

			else if (request.startsWith("/get/article?sort=")) {
				
				if ( userSession == null ) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}
				
				String[] params = request.split("=");

				if (params.length < 2) {
					System.out.println("정렬 값을 입력해주세요. (desc, asc)");
					continue;
				}

				String sort = params[1]; // 이 값은 desc, asc 혹은 의미 없는 값이 리턴 됨.

				if (sort.equals("desc")) { // 내림차순 (최신)

					Collections.reverse(articleList); // 컬렉션 관련 유용한 유틸에서 reverse 메서드 사용

					System.out.printf("======= %s님이 작성한 게시글 리스트 =======\n", userSession.getUserName());
					for (Article article : articleList) {
						// 게시글에 등록된 작성자 고유 번호와 현재 세션에 로그인된 고유 번호를 비교한다.
						// 아래 조건식을 통과 해야지만 리스트 출력이 가능하다.
						if ( article.getUserId() == userSession.getUserId() ) {
							System.out.println("번호 : " + article.getArticleId());
							System.out.println("작성자 : " + article.getUserName());
							System.out.println("제목 : " + article.getTitle());
							System.out.println("내용 : " + article.getBody());
							System.out.println();
						}
					}

					Collections.reverse(articleList); // 객체 값들은 계속 공유 되고 있기 때문에 거꾸로 돌렸다면, 다시 되돌려주는 작업 적용

				} else if (sort.equals("asc")) { // 오름차순 (과거)

					System.out.printf("======= %s님이 작성한 게시글 리스트 =======\n", userSession.getUserName());
					
					for (Article article : articleList) {
						if ( article.getUserId() == userSession.getUserId() ) {
							System.out.println("번호 : " + article.getArticleId());
							System.out.println("작성자 : " + article.getUserName());
							System.out.println("제목 : " + article.getTitle());
							System.out.println("내용 : " + article.getBody());
							System.out.println();
						}
					}

				} else {
					System.out.println("유효한 정렬 값을 입력해주세요. (desc, asc)");
				}

			}

			else if (request.equals("/post/article")) {
				
				if ( userSession == null ) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}

				sc.nextLine(); // 버퍼 비우기

				System.out.print("제목 : ");
				String title = sc.nextLine(); // 공백 및 엔터를 가져감.

				System.out.print("내용 : ");
				String body = sc.nextLine();

				// 하나의 게시글 클래스
				Article article = new Article();
				articleLastId++;
				article.setArticleId(articleLastId);
				// 현재시간 객체안의 메서드 호출 (static 이라서 이렇게 가능)
				article.setRegDate(CurrentDateTime.now());
				article.setTitle(title);
				article.setBody(body);
				article.setUserName(userSession.getUserName()); // 세션에서 유저 이름 꺼낸 후 아티클에 있는 변수에 저장
				article.setUserId(userSession.getUserId()); // 세션에서 유저 번호 꺼낸 후 아티클에 있는 변수에 저장

				// 게시글 목록에 게시글 저장
				articleList.add(article);

				System.out.println("게시글이 작성되었습니다.");
			}

			else if (request.equals("/get/help")) {

				System.out.println("=== 도움말 ===");
				System.out.println("/exit - 프로그램 종료");
				System.out.println("/get/help - 도움말 출력");
				System.out.println("/get/article?articleId= - 게시글 상세 출력");

				System.out.println("== 게시글과 관련된 기능 ==");
				System.out.println("/post/article -> 게시글 작성");
				System.out.println("/post/update/article?articleId= -> 게시글 수정");
				System.out.println("/post/remove/article?articleId= -> 게시글 삭제");
				System.out.println("/post/removeAll/article -> 게시글 전부 삭제");
				System.out.println("/get/article?sort={sort} -> 게시글 출력(desc, asc)");
				
				System.out.println("== 회원과 관련된 기능 ==");
				System.out.println("/user/join-> 회원가입");
				System.out.println("/user/login-> 로그인");
				System.out.println("/user/logout-> 로그아웃");

			}

			else if (request.equals("/exit")) {
				System.out.println("프로그램 종료");
				sc.close();
				break;

			}

			else if (request.equals(".")) {
				for (int i = 1; i <= 10; i++) {
					// 하나의 게시글 클래스
					Article article = new Article();
					articleLastId++;
					article.setArticleId(articleLastId);
					// 현재시간 객체안의 메서드 호출 (static 이라서 이렇게 가능)
					article.setRegDate(CurrentDateTime.now());
					article.setTitle("제목" + i);
					article.setBody("내용" + i);

					// 게시글 목록에 게시글 저장
					articleList.add(article);
				}

				System.out.println("테스트 게시글이 추가되었습니다.");
			}

			else {
				System.out.println("존재하지 않는 명령어 입니다.");

			}
		}
	}
}
