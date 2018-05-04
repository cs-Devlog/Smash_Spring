<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/add.css">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">

<script src="<c:url value="/resources/js/jquery-3.2.1.min.js" /> "></script>
<script src="<c:url value="/resources/js/bootstrap.js" /> "></script>
<script src="<c:url value="/resources/js/bootstrap.bundle.js" /> "></script>
<title>SMASH CARE MOVEMENT</title>
</head>
<body>
	<div class="wrap" style="width: 100%; height: 100%">

		<%@include file="medic_nav.jsp"%>

		<div class="content mx-auto">
			<div class="container my-auto">
				<!-- Insert Head -->
				<div class="row content_head">
					<!-- 제목 -->
					<div class="col-lg-3 content_title">
						<p>프로그램 등록</p>
					</div>
				</div>
				<!-- 입력 폼 -->
				<form method="post" action="ProgramAddOK">
					<div class="row content_body">
						<div class="col-lg-5">
							<label for="patient_num">*프로그램 번호</label>
							<!-- 환자 번호 입력 -->
							<input type="text" class="form-control" name="programNumber">
						</div>
						<div class="col-lg-5">
							<label for="patient_name">*프로그램 이름</label>
							<!-- 환자 이름 입력 -->
							<input type="text" class="form-control" name="programName">
						</div>
					</div>
					<div class="row">
						<div class="col-lg-10">
							<label for="patient_disease">*프로그램 설명</label>
							<!-- 병명 입력 -->
							<textarea class="form-control" rows="5" name="programContent"></textarea>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-10">
							<label for="patient_status">*관련 질병</label>
							<!-- 환자 상태 입력 -->
							<input type="text" class="form-control" name="programDisease">
						</div>
					</div>
					<div class="row">
						<div class="offset-10 col-lg-2">
							<!-- 제출하기 -->
							<input type="submit" class="submit" value="+ 등록하기">
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>
</body>
</html>