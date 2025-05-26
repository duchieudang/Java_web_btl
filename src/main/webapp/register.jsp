<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


	<!DOCTYPE html>
	<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta content="width=device-width, initial-scale=1.0" name="viewport">

		<title>Đăng ký</title>
		<%@ include file="fragments/css_include.jsp" %>
		<script>
        async function submitForm(event) {
            event.preventDefault();
            const form = document.getElementById('thatForm');
            const formData = new URLSearchParams(new FormData(form));
            formData.append('action', 'register');

            try {
                const response = await fetch('account', {
                    method: 'POST',
                    body: formData
                });

                if (response.ok) {
                    const data = await response.json();
                    alert(data.message);
                } else {
                    const error = await response.json();
                    alert(error.message);
                }
            } catch (err) {
                console.error(err);
                alert('Internal server error. Please try again later.');
            }
        }
    </script>
	</head>

	<body>
		<div class="container-fluid min-vh-100 d-flex justify-content-center">
			<div class="row align-items-center justify-content-center">
				<div class="col-12 col-sm-10 col-md-12 col-lg-10">

					<div class="d-flex justify-content-center py-4">
						<a href="index.html" class="logo d-flex align-items-center w-auto">
							<img src="assets/img/logo.png" alt="">
							<span>Logo</span>
						</a>
					</div><!-- End Logo -->

					<div class="card mb-3">

						<div class="card-body">

							<div class="pt-4 pb-2">
								<h5 class="card-title text-center pb-0 fs-4">Đăng ký</h5>
								<p class="text-center small">Nhập tên tài khoản và mật khẩu mong muốn.</p>
							</div>

							<form id="thatForm" class="row g-3 needs-validation" onsubmit="submitForm(event)">

								<div class="col-12 col-md-6">
									<div class="col-12">
										<label for="username" class="form-label">Tài khoản:</label>
										<input type="text" name="username" id="username" class="form-control" required>
										<div class="invalid-feedback">Tài khoản không thể trống!</div>
									</div>

									<div class="col-12">
										<label for="password" class="form-label">Mật khẩu:</label>
										<input type="password" name="password" id="password" class="form-control" required>
										<div class="invalid-feedback">Mật khẩu không thể trống!</div>
									</div>

									<div class="col-12">
										<label for="role" class="form-label">Vai trò:</label>
										<select name="role" id="role" class="form-select" aria-label="Default select example">
											<option value="1" selected>Nhân viên</option>
											<option value="2">Quản trị</option>
										</select>
									</div>

									<div class="col-12">
										<label for="fullname" class="form-label">Họ tên:</label>
										<input type="text" name="fullname" id="fullname" class="form-control">
									</div>

									<div class="col-12">
										<label for="phone" class="form-label">Điện thoại:</label>
										<input type="text" name="phone" id="phone" class="form-control">
									</div>
									</div>



									<div class="col-12 col-md-6">
										<div class="col-12">
											<label for="email" class="form-label">Email:</label>
											<input type="text" name="email" id="email" class="form-control">
										</div>

										<div class="col-12">
											<label for="address" class="form-label">Địa chỉ:</label>
											<input type="text" name="address" id="address" class="form-control">
										</div>

										<div class="col-12">
											<label for="birthday" class="form-label">Ngày sinh:</label>
											<input type="date" name="birthday" id="birthday" class="form-control">
										</div>

										<div class="col-12">
											<label for="gender" class="form-label">Giới tính:</label>
											<input type="text" name="gender" id="gender" class="form-control">
										</div>

										<div class="col-12">
											<label for="note" class="form-label">Ghi chú:</label>
											<textarea name="note" class="form-control" id="note" style="height: 100px"></textarea>
										</div>


									</div>

									<!-- 										<div class="col-12"> -->
									<!-- 											<div class="form-check"> -->
									<!-- 												<input class="form-check-input" name="terms" type="checkbox" value="" -->
									<!-- 													id="acceptTerms" required> -->
									<!-- 												<label class="form-check-label" for="acceptTerms">I agree and accept the -->
									<!-- 													<a href="#">terms and conditions</a></label> -->
									<!-- 												<div class="invalid-feedback">You must agree before submitting.</div> -->
									<!-- 											</div> -->
									<!-- 										</div> -->

									<div class="col-12">
										<button class="btn btn-primary w-100" type="submit">Đăng ký</button>
									</div>

									<div class="col-12">
										<p class="small mb-0">Đã có tài khoản?
											<a href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
										</p>
									</div>
							</form>

						</div>
					</div>

					<!-- <div class="credits">
          Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
        </div> -->

				</div>
			</div>

		</div>
		<%@ include file="fragments/js_include.jsp" %>
	</body>

	</html>