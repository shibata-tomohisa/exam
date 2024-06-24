<%-- 機能追加１　科目検索のための追加 --%>
<%-- 科目一覧フィルターコンポーネント --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.textbox {
	display:block;width:100%;padding:.375rem 2.25rem .375rem .75rem;-moz-padding-start:calc(0.75rem - 3px);
    font-size:1rem;font-weight:400;line-height:0.5;color:#212529;background-color:#fff;
    border-radius:.375rem; border:solid 1px #d3d3d3;
    }
.textbox:focus{border-color:#86b7fe;outline:0;box-shadow:0 0 0 .25rem rgba(13,110,253,.25)}
</style>

<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
	<form method="get" action="SubjectList.action">
		<div class="row align-items-center mx-3 border-bottom">
			<div class="col-2">科目検索</div>
			<div class="col-2">

				<label class="form-label" for="test-f1-select">科目コード</label>
				<input type="text" pattern="^[a-zA-Z0-9]+$" class="textbox" id="subject_search_f1" name="f1">

			</div>
			<div class="col-2">

				<label class="form-label" for="test-f2-select">科目名</label>
				<input type="text" class="textbox" id="subject_search_f2" name="f2">

			</div>
			<div class="col-2 form-check text-center">
				<label class="form-check-label" for="subject-f3-check">完全一致
					<%-- パラメーターf3が存在している場合checkedを追記 --%>
					<input class="form-check-input" type="checkbox"
					id="subject-f3-check" name="f3" value="t"
					<c:if test="${!empty f3}">checked</c:if> />
				</label>
			</div>



			<div class="col-2 text-center">
				<!-- 科目情報から成績を表示するための識別コード -->
				<!-- <input type="hidden" name="f" value="sj"> -->
				<button class="btn btn-secondary" id="filter-subject-button">検索</button>
			</div>
			<div class="mt-2 text-warning">${errors.get("filter")}</div>
		</div>
	</form>

</div>