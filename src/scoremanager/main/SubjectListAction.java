package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao sDao = new SubjectDao();//科目Daoを初期化
		HttpSession session = req.getSession(true);//セッションを取得
		Teacher teacher = (Teacher) session.getAttribute("user");//ログインしているユーザーを取得

	// 機能追加１　科目検索のための追加コード　-----ここから-----
		String codeStr= "" ;// 入力された科目コード
		String subName = "" ;//入力された科目名
		String sameAllstr = "";//完全一致のチェック
		boolean sameAll = false;//完全一致フラグ
		List<Subject> subjects = null;//検索後の科目一覧

		//リクエストパラメータ―の取得 2
		codeStr = req.getParameter("f1");
		subName = req.getParameter("f2");
		sameAllstr = req.getParameter("f3");
		if (sameAllstr != null) {
			// 完全一致検索のフラグを立てる
			sameAll = true;
		}

		//DBからデータ取得 3
		// 分岐：科目コードor科目名に検索条件の入力がある(nullでない)時は、科目内検索フィルタを実行
		//　　　 どちらの検索条件も入力なしの時は学校コードフィルタを実行し全件表示
		if ( codeStr != null || subName != null){
			subjects = sDao.filter_search(teacher.getSchool(), codeStr, subName, sameAll);
		} else {
			subjects = sDao.filter(teacher.getSchool());
		}

		//ビジネスロジック 4
		//なし
		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		// 検索条件を表示するためjspへ送る変数をセット
		req.setAttribute("f1", codeStr);
		req.setAttribute("f2", subName);
	// -----ここまで-----

		req.setAttribute("subjects", subjects);
		//JSPへフォワード 7
		//リクエストにデータをセット
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}
