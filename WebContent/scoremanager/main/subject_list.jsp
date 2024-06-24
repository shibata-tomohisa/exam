<%-- 科目一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
			<div class="my-2 text-end px-4">
				<a href="SubjectCreate.action">新規登録</a>
			</div>
	<%-- 機能追加１　科目検索のためのコード追加/修正　-----ここから----- --%>
			<c:import url="subject_list_filter.jsp" />
			<% String f1 = (String)request.getAttribute("f1");
			   String f2 = (String)request.getAttribute("f2");
			%>
			 <c:if test="${!empty f1 or !empty f2}">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded">
				<div>【検索条件】　　科目コード：<%=f1 %>　　科目名：<%=f2 %>　　　検索結果：${subjects.size()}件</div>
				</div>
			</c:if>

			<c:choose>
				<c:when test="${subjects.size()>0}">

					<table class="table table-hover">
						<tr>
							<th>科目コード</th>
							<th>科目名</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="subject" items="${subjects}">
							<tr>
								<td>${subject.cd}</td>
								<td>${subject.name}</td>
								<td><a href="SubjectUpdate.action?cd=${subject.cd}&name=${subject.name}">変更</a></td>
								<td><a href="SubjectDelete.action?cd=${subject.cd}&name=${subject.name}">削除</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>条件に合致する科目はありません</div>
				</c:otherwise>
			</c:choose>
	<%-- -----ここまで----- --%>

		</section>
	</c:param>
</c:import>

