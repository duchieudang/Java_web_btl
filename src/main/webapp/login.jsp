<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta content="width=device-width, initial-scale=1.0" name="viewport">
	<title>Đăng nhập</title>
	<meta content="" name="description">
	<meta content="" name="keywords">
	<%@ include file="fragments/css_include.jsp" %>
</head>

<body>
	<div class="container-fluid min-vh-100 d-flex justify-content-center">
		<div class="row align-items-center justify-content-center">
			<div class="col-12 col-sm-10 col-md-8 col-lg-7 col-xxl-6" style="min-width: 576px;">

				<div class="d-flex justify-content-center py-4">
					<a href="index.html" class="logo d-flex align-items-center w-auto">
						<img src="assets/img/logo.png" alt=""> <span>Logo</span>
					</a>
				</div>

				<div class="card card-body">

					<div class="pt-4 pb-2">
						<h5 class="card-title text-center pb-0 fs-4">Đăng nhập</h5>
					</div>

					<form id="thatForm" class="row g-3 needs-validation" method="post" action="${pageContext.request.contextPath}/login">

						<div class="col-12">
							<label for="username" class="form-label">Tài khoản</label>
							<div class="input-group has-validation">
								<span class="input-group-text" id="inputGroupPrepend">@</span> 
								<input type="text" id="username" name="username" class="form-control" autofocus>
<!-- 								<div class="invalid-feedback">Không được để trống tài khoản.</div> -->
							</div>
						</div>

						<div class="col-12">
							<label for="password" class="form-label">Mật khẩu</label> 
							<input type="password" id="password" name="password" class="form-control">
<!-- 							<div class="invalid-feedback">Không được để trống mật khẩu.</div> -->
						</div>
						<!-- <div class="col-12">
							<div class="form-check">
								<input class="form-check-input" type="checkbox" name="remember" value="true"
									id="rememberMe">
								<label class="form-check-label" for="rememberMe">Lưu thông tin đăng
									nhập</label>
							</div>
						</div> -->
						
						<div class="col-12">
<%-- 							<span>${param.err}</span> --%>
							<span>${message}</span>
							 
						</div>
						
						<div class="col-12"></div>
						<div class="col-12">
							<button class="btn btn-primary w-100" type="submit">Đăng nhập</button>
						</div>
<!-- 						<div class="col-12"> -->
<%-- 							<p class="small mb-0">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a></p> --%>
<!-- 						</div> -->
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="fragments/js_include.jsp" %>
</body>

</html>