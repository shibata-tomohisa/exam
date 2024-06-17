package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;//バグ対応３　test_list_filter.jspを読み込む際のリストセット不足　必要コードを追加
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;//バグ対応３　test_list_filter.jspを読み込む際のリストセット不足　必要コードを追加
import dao.TestListStudentDao;
import tool.Action;

public class StudentTestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// ローカル変数の初期化 1
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		SubjectDao sDao = new SubjectDao();// 科目Daoを初期化
		HttpSession session = req.getSession(true);// セッションを取得
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得

//バグ対応３　test_list_filter.jspを読み込む際のリストセット不足　必要コードを追加
//SubjectTestListActionからコピペして追加　--- ここから ---
		int entYear = 0; // 入学年度
		String entYearStr = null; // 入学年度文字列
		String classNum = null; // クラス番号
		String subjectCd = null; //科目番号
		SubjectDao subjectDao = new SubjectDao(); //科目Dao
		Subject subject = null; //科目
// --- ここまで ---


		List<Integer> entYearSet = new ArrayList<>();// 入学年度のリストを初期化
		List<Integer> numSet = new ArrayList<>();// テストの回数リストを初期化
		Map<String, String> errors = new HashMap<>(); //エラーメッセージ文字列
		Student student = null;
		List<TestListStudent> tests = new ArrayList<>();

		// リクエストパラメータから値を取得
		String f = req.getParameter("f");
		String studentNo = req.getParameter("f4");

		// リクエストにセットされたエラーメッセージを取得
		errors = (HashMap<String, String>)req.getAttribute("errors");

		// DBへデータ保存 5
		// なし

		// DBからデータ取得 3
//バグ対応３　test_list_filter.jspを読み込む際のリストセット不足　必要コードを追加
// SubjectTestListActionからコピペして追加　--- ここから ---
		List<String> list = cNumDao.filter(teacher.getSchool());//クラス番号の一覧を取得
		List<Subject> subjects = sDao.filter(teacher.getSchool());//科目の一覧を取得

		// ビジネスロジック 4
		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}
		// 全2回分のテスト回数をリストに追加
		for (int i = 1; i <= 2; i++) {
			numSet.add(i);
		}
		// レスポンス値をセット 6
		req.setAttribute("f1", entYear);
		// クラス番号
		req.setAttribute("f2", classNum);
		// 科目コード
		req.setAttribute("f3", subjectCd);
		req.setAttribute("class_num_set", list);//クラス番号の一覧をセット
		req.setAttribute("ent_year_set", entYearSet);//入学年度のリストをセット
		req.setAttribute("subjects", subjects);//科目の一覧をセット
		req.setAttribute("num_set", numSet);//テストの回数リストをセット
// --- ここまで ---


		// 状況によって分岐
		if (studentNo != null) {
			// リクエストパラメーターが存在している場合学生番号から学生情報を取得
			StudentDao studentDao = new StudentDao();
			student = studentDao.get(studentNo);

			// 学生情報が存在していた場合、その学生の成績を取得
			if (student != null) {
				// 学生成績表示用Daoを初期化
				TestListStudentDao testDao = new TestListStudentDao();
				tests = testDao.filter(student);
			}
		} else {
			errors.put("filter", "入学年度とクラスと科目を選択してください");
			req.setAttribute("errors", errors);
			//バグ対応１　error.jspへのリンクミス　URLを絶対アドレスへ変更(error.jsp⇒/error.jsp)
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
		// リクエストに学生をセット
		req.setAttribute("student", student);
		// リクエストに成績をセット
		req.setAttribute("tests", tests);

		// フォワード 7
		// 学生成績一覧画面に遷移
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);

	}

	/**
	 * 引数に指定された項目がnullではいか、および0ではないかをチェックして結果を返却する
	 *
	 * @param year 年度(数値)
	 * @param yearStr 年度(文字列)
	 * @param classNum クラス番号
	 * @param subjectCd 科目コード
	 *
	 * @return 指定された引数が全てOKだった場合はtrue。その他の場合はfalse
	 * @throws Exception
	 */
	private boolean existsAllParam(int entYear, String entYearStr, String classNum, String subjectCd) {

		boolean result = false;

		if (entYearStr != null || classNum != null || subjectCd != null) {
			// 全てのパラメーターが存在している場合
			if (entYear != 0 && !classNum.equals("0") && !subjectCd.equals("0")) {
				result = true;
			}
		}
		return result;
	}
}
